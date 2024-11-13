package com.tours.Service;

import com.tours.Entities.User;
import com.tours.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

    public void register(User user) {
        userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
