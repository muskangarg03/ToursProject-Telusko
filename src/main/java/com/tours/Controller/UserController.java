//package com.tours.Controller;
//
//import com.tours.Entities.Users;
//import com.tours.Service.UserService;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//@CrossOrigin(origins = "*")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
////    @PostMapping("/signup")
////    public ResponseEntity<String> registerUser(@RequestBody Users user) {
////        userService.register(user);
////        return ResponseEntity.ok("User registered successfully!");
////    }
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<String> registerUser(
//            @Valid @RequestBody Users user, BindingResult result) throws Exception {
//
//        // Check if user has agreed to terms
//        if (!user.isEnabled()) { // Assuming `enabled` is a boolean field in the Users class
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Please agree to the terms and conditions");
//        }
//
//        // Check for validation errors
//        if (result.hasErrors()) {
//            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
//            result.getFieldErrors().forEach(error ->
//                    errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; "));
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
//        }
//
//        // Call the user registration service
//        userService.register(user);
//        return ResponseEntity.ok("User registered successfully!");
//    }
//
//
//
//    //    @PostMapping("/login")
////    public ResponseEntity<String> loginUser(@RequestBody Users loginUser) {
////        Users authenticatedUser = userService.login(loginUser.getEmail(), loginUser.getPassword());
////        if (authenticatedUser != null) {
////            return ResponseEntity.ok("Login successful! Welcome " + authenticatedUser.getName());
////        } else {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
////        }
////    }
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody Users loginUser, HttpSession session) {
//        Users authenticatedUser = userService.login(loginUser.getEmail(), loginUser.getPassword());
//
//        if (authenticatedUser != null) {
//            session.setAttribute("loggedInUser", authenticatedUser); // Store user in session
//            return ResponseEntity.ok("Login successful!" + authenticatedUser.getName());
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logoutUser(HttpSession session) {
//        session.invalidate(); // Invalidate session to log the user out
//        return ResponseEntity.ok("Logged out successfully.");
//    }
//
//    @GetMapping("/admin/dashboard")
//    public ResponseEntity<String> adminDashboard(HttpSession session) {
//        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
//
//        // Check if a user is logged in
//        if (loggedInUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in to access this resource.");
//        }
//
//        // Check if the logged-in user is an admin
//        if ("ADMIN".equalsIgnoreCase(loggedInUser.getRole())) { // Assuming `role` is a field in the Users class
//            return ResponseEntity.ok("Welcome to the Admin Dashboard, " + loggedInUser.getName());
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Admins only.");
//        }
//    }
//
//    @GetMapping("/customer/dashboard")
//    public ResponseEntity<String> customerDashboard(HttpSession session) {
//        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
//
//        // Check if a user is logged in
//        if (loggedInUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in to access this resource.");
//        }
//
//        // Check if the logged-in user is a customer
//        if ("CUSTOMER".equalsIgnoreCase(loggedInUser.getRole())) { // Assuming `role` is a field in the Users class
//            return ResponseEntity.ok("Welcome to the Customer Dashboard, " + loggedInUser.getName());
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Customers only.");
//        }
//    }
//
//}





package com.tours.Controller;

import com.tours.Config.JwtFilter;
import com.tours.Entities.Users;
import com.tours.Service.JwtService;
import com.tours.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // Signup endpoint remains unchanged
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody Users user) throws Exception {
        if (!user.isEnabled()) { // Assuming `enabled` is a boolean field in the Users class
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Please agree to the terms and conditions");
        }
        userService.register(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // Login endpoint using Spring Security and JWT Token Generation
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Users loginUser) {
        // Authenticate the user with the authentication manager
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));

        // Check if authentication was successful
        if (authentication.isAuthenticated()) {
            // Generate JWT token
            String token = jwtService.generateToken(loginUser.getEmail());
            return ResponseEntity.ok(token); // Return the token to the client
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        }
    }



    // Admin Dashboard with Role-Based Authorization
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getUsername(authentication);
        return ResponseEntity.ok("Welcome to the Admin Dashboard, " + username);
    }


    // Customer Dashboard with Role-Based Authorization
    @GetMapping("/customer/dashboard")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> customerDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getUsername(authentication);
        return ResponseEntity.ok("Welcome to the Customer Dashboard, " + username);
    }

    private String getUsername(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "Unknown User";
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logoutUser(HttpSession session) {
//        session.invalidate(); // Invalidate session to log the user out
//        return ResponseEntity.ok("Logged out successfully.");
//    }
}

