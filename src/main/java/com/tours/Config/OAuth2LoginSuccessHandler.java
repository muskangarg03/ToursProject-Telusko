package com.tours.Config;

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

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String contactNumber =oauth2User.getAttribute("contactNumber");

        // Find or create user
        Users user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    Users newUser = new Users();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setContactNumber(contactNumber);
                    newUser.setRole("ROLE_CUSTOMER");
                    newUser.setEnabled(true);

                    // Generate password: 01in + username (encoded)
                    String username = name.replaceAll("\\s+", "").toLowerCase(); // Remove spaces and convert to lowercase
                    String rawPassword = "01in" + username;
                    newUser.setPassword(passwordEncoder.encode(rawPassword));

                    return userRepository.save(newUser);
                });

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail());

        // Set token in response header
        response.addHeader("Authorization", "Bearer " + token);
        response.setStatus(HttpServletResponse.SC_OK);

        // Optional: Send the token in the response body for Postman
        response.getWriter().write("{\"token\": \"Bearer " + token + "\"}");
    }
}
