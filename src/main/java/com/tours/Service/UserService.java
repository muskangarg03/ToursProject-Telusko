package com.tours.Service;

import com.tours.Entities.Users;
import com.tours.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

    public void register(Users user) throws Exception {
        user.setRole("CUSTOMER"); // Default role for new user
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use. Please use a different email.");
        }
        userRepository.save(user);
    }

    public Users login(String email, String password) {
        Users user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("Email not found. Please check the email address and try again.");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password. Please try again.");
        }

        return user;
    }

}
