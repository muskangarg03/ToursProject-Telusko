package com.tours.Service;

import com.tours.Entities.Users;
import com.tours.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Extract user details from OAuth2 provider
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String contactNumber =oauth2User.getAttribute("contactNumber");

        // Check if user exists, if not create a new user
        Optional<Users> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setContactNumber(contactNumber);
            newUser.setRole("ROLE_CUSTOMER"); // Default role
            newUser.setEnabled(true);

            userRepository.save(newUser);
        }

        return oauth2User;
    }
}