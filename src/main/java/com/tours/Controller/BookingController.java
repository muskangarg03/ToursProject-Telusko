package com.tours.Controller;

import com.tours.Entities.Booking;
import com.tours.Entities.Tour;
import com.tours.Entities.Users;
import com.tours.Service.BookingService;
import com.tours.Service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class BookingController {

    @Autowired
    private TourService tourService;

    @Autowired
    private BookingService bookingService;


    @GetMapping("/tours")
    public ResponseEntity<?> getAllTours(HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        List<Tour> tours = tourService.getAllToursWithDetails();

        // Create a structured response
        String message = "User: " + loggedInUser.getName() + " is viewing the tours.";
        return ResponseEntity.ok(Map.of(
                "message", message,
                "availableTours", tours
        ));
    }


    @GetMapping("/tours/{id}")
    public ResponseEntity<?> getTourById(HttpSession session, @PathVariable Long id) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "User is not logged in."));
        }

        return tourService.getTourById(id)
                .map(tour -> ResponseEntity.ok(Map.of(
                        "message", "User: " + loggedInUser.getName() + " is viewing the tour.",
                        "tourDetails", tour
                )))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Tour not found with ID: " + id)));
    }


    // API to create a booking
    @PostMapping("/addBooking/{tourId}")
    public ResponseEntity<?> createBooking(HttpSession session, @PathVariable Long tourId, @RequestBody Booking book) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        try {
            // Creating booking using only the tourId
            Booking booking = bookingService.createBooking(loggedInUser, tourId, book.getNumberOfTickets());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Booking created successfully", "booking", booking));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }



    @PostMapping("/confirmBooking/{bookingId}")
    public ResponseEntity<?> confirmBooking(HttpSession session,@PathVariable Long bookingId, @RequestParam String paymentTransactionId) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
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


