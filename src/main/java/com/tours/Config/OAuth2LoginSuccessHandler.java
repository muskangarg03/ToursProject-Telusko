
package com.tours.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tours.Entities.Users;
import com.tours.Repo.UserRepo;
import com.tours.Service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String contactNumber = oauth2User.getAttribute("contactNumber");

        // Find or create user
        Users user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    Users newUser = new Users();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setContactNumber(contactNumber);
                    newUser.setRole("ROLE_CUSTOMER"); // Default role
                    newUser.setEnabled(true);

                    // Generate password: 01in + username (encoded)
                    String username = name.replaceAll("\\s+", "").toLowerCase();
                    String rawPassword = "01in" + username;
                    newUser.setPassword(passwordEncoder.encode(rawPassword));

                    return userRepository.save(newUser);
                });

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        // Prepare response body with token and role
//        Map<String, String> responseBody = new HashMap<>();
//        responseBody.put("token", token);
//
//        // Set response headers
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setHeader("Authorization", "Bearer " + token);
//
//        // Write JSON response body
//        response.getWriter().write(objectMapper.writeValueAsString(responseBody));


        String redirectUrl;
        if (user.getRole().equals("ROLE_ADMIN")) {
            redirectUrl = "http://localhost:5173/admin/dashboard";
        } else {
            redirectUrl = "http://localhost:5173/customer/dashboard";
        }


        // Redirect to appropriate dashboard
        //getRedirectStrategy().sendRedirect(request, response, redirectUrl);

        // Build URL with token
        String finalRedirectUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("token", token)
                .build().toUriString();

        // Redirect to appropriate dashboard
        getRedirectStrategy().sendRedirect(request, response, finalRedirectUrl);
    }
}