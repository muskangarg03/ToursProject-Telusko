package com.tours.Controller;

import com.tours.Entities.Users;
import com.tours.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/signup")
//    public ResponseEntity<String> registerUser(@RequestBody Users user) {
//        userService.register(user);
//        return ResponseEntity.ok("User registered successfully!");
//    }


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody Users user, BindingResult result) throws Exception {

        // Check if user has agreed to terms
        if (!user.isEnabled()) { // Assuming `enabled` is a boolean field in the Users class
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please agree to the terms and conditions");
        }

        // Check for validation errors
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            result.getFieldErrors().forEach(error ->
                    errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }

        // Call the user registration service
        userService.register(user);
        return ResponseEntity.ok("User registered successfully!");
    }



//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody Users loginUser) {
//        Users authenticatedUser = userService.login(loginUser.getEmail(), loginUser.getPassword());
//        if (authenticatedUser != null) {
//            return ResponseEntity.ok("Login successful! Welcome " + authenticatedUser.getName());
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }
@PostMapping("/login")
public ResponseEntity<String> loginUser(@RequestBody Users loginUser, HttpSession session) {
    Users authenticatedUser = userService.login(loginUser.getEmail(), loginUser.getPassword());

    if (authenticatedUser != null) {
        session.setAttribute("loggedInUser", authenticatedUser); // Store user in session
        return ResponseEntity.ok("Login successful!" + authenticatedUser.getName());
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.invalidate(); // Invalidate session to log the user out
        return ResponseEntity.ok("Logged out successfully.");
    }
}
