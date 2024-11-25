//package com.tours.Service;
//
//import com.tours.Entities.Users;
//import com.tours.Repo.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.logging.Logger;
//
//@Service
//public class UserService {
//
//    private static final Logger logger = Logger.getLogger(UserService.class.getName());
//
//    @Autowired
//    private UserRepo userRepository;
//
//    // Register a new user
//    public void register(Users user) throws Exception {
//        logger.info("Trying to register user with email: " + user.getEmail());
//
//        if (user.getRole() == null || user.getRole().isEmpty()) {
//            user.setRole("CUSTOMER");   //default role for new user
//        }
//
//        // Check if email already exists
//        if (userRepository.existsByEmail(user.getEmail())) {
//            logger.warning("Email already in use: " + user.getEmail());
//            throw new RuntimeException("Email is already in use. Please use a different email.");
//        }
//
//        // Save new user
//        userRepository.save(user);
//        logger.info("User successfully registered with email: " + user.getEmail());
//    }
//
//    // Login user
//    public Users login(String email, String password) {
//        logger.info("Attempting to login user with email: " + email);
//
//        // Check if user exists
//        Users user = userRepository.getUserByEmail(email);
//        if (user == null) {
//            logger.warning("Email not found: " + email);
//            throw new RuntimeException("Email not found. Please check the email address and try again.");
//        }
//
//        // Check if password matches
//        if (!user.getPassword().equals(password)) {
//            logger.warning("Invalid password attempt for email: " + email);
//            throw new RuntimeException("Invalid password. Please try again.");
//        }
//
//        logger.info("User logged in successfully with email: " + email);
//        return user;
//    }
//}




package com.tours.Service;

import com.tours.Entities.Users;
import com.tours.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(Users user) throws Exception {
        logger.info("Attempting to register user with email: " + user.getEmail());

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_CUSTOMER");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warning("Email already in use: " + user.getEmail());
            throw new RuntimeException("Email is already in use. Please use a different email.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("User successfully registered with email: " + user.getEmail());
    }

    public Users login(String email, String password) {
        logger.info("Attempting to login user with email: " + email);

        Users user = userRepository.getUserByEmail(email);
        if (user == null) {
            logger.warning("Email not found: " + email);
            throw new RuntimeException("Invalid email or password.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warning("Invalid password for email: " + email);
            throw new RuntimeException("Invalid email or password.");
        }

        logger.info("User successfully logged in with email: " + email);
        return user;
    }
}

