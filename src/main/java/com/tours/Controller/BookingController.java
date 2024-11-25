//package com.tours.Controller;
//
//import com.tours.Entities.Booking;
//import com.tours.Entities.Tour;
//import com.tours.Entities.Users;
//import com.tours.Service.BookingService;
//import com.tours.Service.TourService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.servlet.http.HttpSession;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/customer")
//@CrossOrigin(origins = "*")
//public class BookingController {
//
//    @Autowired
//    private TourService tourService;
//
//    @Autowired
//    private BookingService bookingService;
//
//
//    @GetMapping("/tours")
//    public ResponseEntity<?> getAllTours(HttpSession session) {
//        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
//
//        if (loggedInUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
//        }
//
//        List<Tour> tours = tourService.getAllToursWithDetails();
//
//        // Create a structured response
//        String message = "User: " + loggedInUser.getName() + " is viewing the tours.";
//        return ResponseEntity.ok(Map.of(
//                "message", message,
//                "availableTours", tours
//        ));
//    }
//
//
//    @GetMapping("/tours/{id}")
//    public ResponseEntity<?> getTourById(HttpSession session, @PathVariable Long id) {
//        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
//
//        if (loggedInUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("message", "User is not logged in."));
//        }
//
//        return tourService.getTourById(id)
//                .map(tour -> ResponseEntity.ok(Map.of(
//                        "message", "User: " + loggedInUser.getName() + " is viewing the tour.",
//                        "tourDetails", tour
//                )))
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(Map.of("message", "Tour not found with ID: " + id)));
//    }
//
//
//    // API to create a booking
//    @PostMapping("/addBooking/{tourId}")
//    public ResponseEntity<?> createBooking(HttpSession session, @PathVariable Long tourId, @RequestBody Booking book) {
//        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("User is not logged in.");
//        }
//
//        try {
//            // Creating booking using only the tourId
//            Booking booking = bookingService.createBooking(loggedInUser, tourId, book.getNumberOfTickets());
//            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Booking created successfully", "booking", booking));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
//        }
//    }
//
//
//
//    @PostMapping("/confirmBooking/{bookingId}")
//    public ResponseEntity<?> confirmBooking(HttpSession session,@PathVariable Long bookingId, @RequestParam String paymentTransactionId) {
//        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("User is not logged in.");
//        }
//        try {
//            Booking confirmedBooking = bookingService.confirmBooking(bookingId, paymentTransactionId);
//            return ResponseEntity.ok(Map.of("message", "Booking confirmed successfully", "booking", confirmedBooking));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
//        }
//    }
//
//}
//
//




package com.tours.Controller;

import com.tours.Entities.Booking;
import com.tours.Entities.Tour;
import com.tours.Entities.Users;
import com.tours.Repo.UserRepo;
import com.tours.Service.BookingService;
import com.tours.Service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private TourService tourService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepo userRepo;  // Assuming you have a repository for Users

    // Common method to get the logged-in user
    private Users getLoggedInUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails == null) {
            return null;
        }
        return userRepo.getUserByEmail(userDetails.getUsername());
    }

    // Get all available tours for the logged-in customer
    @GetMapping("/tours")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAllTours() {
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        List<Tour> tours = tourService.getAllToursWithDetails();
        return ResponseEntity.ok(Map.of(
                "message", "User: " + loggedInUser.getEmail() + " is viewing the tours.",
                "availableTours", tours
        ));
    }

    // Get a specific tour by ID for the logged-in customer
    @GetMapping("/tours/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getTourById(@PathVariable Long id) {
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "User is not logged in."));
        }

        return tourService.getTourById(id)
                .map(tour -> ResponseEntity.ok(Map.of(
                        "message", "User: " + loggedInUser.getEmail() + " is viewing the tour.",
                        "tourDetails", tour
                )))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Tour not found with ID: " + id)));
    }

    // Create a booking for a customer
    @PostMapping("/addBooking/{tourId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createBooking(@PathVariable Long tourId, @RequestBody Booking book) {
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        try {
            // Create the booking using the persisted user and tourId
            Booking booking = bookingService.createBooking(loggedInUser, tourId, book.getNumberOfTickets());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Booking created successfully", "booking", booking));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    // Confirm a booking with the payment transaction ID
    @PostMapping("/confirmBooking/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> confirmBooking(@PathVariable Long bookingId, @RequestParam String paymentTransactionId) {
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        try {
            Booking confirmedBooking = bookingService.confirmBooking(bookingId, paymentTransactionId);
            return ResponseEntity.ok(Map.of("message", "Booking confirmed successfully", "booking", confirmedBooking));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
}
