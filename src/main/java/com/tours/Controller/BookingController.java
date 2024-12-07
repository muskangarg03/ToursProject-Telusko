package com.tours.Controller;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.tours.Entities.Booking;
import com.tours.Entities.Tour;
import com.tours.Entities.Users;
import com.tours.Repo.TourRepo;
import com.tours.Repo.UserRepo;
import com.tours.Service.BookingService;
import com.tours.Service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class BookingController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Autowired
    private TourService tourService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TourRepo tourRepository;

    // Initialize Stripe with the secret key
    @Autowired
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    // Common method to get the logged-in user
    private Users getLoggedInUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails == null) {
            return null;
        }
        return userRepo.getUserByEmail(userDetails.getUsername());
    }

    // Get all available tours for the logged-in customer
    @GetMapping("customer/tours")
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
    @GetMapping("customer/tours/{id}")
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

     //Create a booking for a customer
//    @PostMapping("customer/addBooking/{tourId}")
//    @PreAuthorize("hasRole('CUSTOMER')")
//    public ResponseEntity<?> createBooking(@PathVariable Long tourId, @RequestBody Booking book) {
//        Users loggedInUser = getLoggedInUser();
//        if (loggedInUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("User is not logged in.");
//        }
//
//        try {
//            // Create the booking using the persisted user and tourId
//            Booking booking = bookingService.createBooking(loggedInUser, tourId, book.getNumberOfTickets());
//            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Booking created successfully", "booking", booking));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
//        }
//    }
//
//    //Confirm a booking with the payment transaction ID
//    @PostMapping("customer/confirmBooking/{bookingId}")
//    @PreAuthorize("hasRole('CUSTOMER')")
//    public ResponseEntity<?> confirmBooking(@PathVariable Long bookingId, @RequestParam String paymentTransactionId) {
//        Users loggedInUser = getLoggedInUser();
//        if (loggedInUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("User is not logged in.");
//        }
//
//        try {
//            Booking confirmedBooking = bookingService.confirmBooking(bookingId, paymentTransactionId);
//            return ResponseEntity.ok(Map.of("message", "Booking confirmed successfully", "booking", confirmedBooking));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
//        }
//    }



    //Filter Tours Based on Country, LodgingType, TransportType, MixPrice and MaxPrice
    @GetMapping("customer/filterTours")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> filterTours(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String lodgingType,
            @RequestParam(required = false) String transportType,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        // Fetch logged-in user (ensure this method is defined properly)
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        // Call the bookingService's filterTours method
        List<Tour> filteredTours = bookingService.filterTours(country, lodgingType, transportType, minPrice, maxPrice);

        // Check if any tours are found
        if (filteredTours.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No tours found matching the specified criteria."));
        }

        // Return the filtered tours in the response
        return ResponseEntity.ok(Map.of(
                "message", "Filtered tours fetched successfully.",
                "filteredTours", filteredTours
        ));
    }

    // Search Tours Endpoint
//    @GetMapping("customer/searchTours")
//    @PreAuthorize("hasRole('CUSTOMER')")
//    public List<Tour> searchTours(String searchTerm) {
//        System.out.println("Search Term: " + searchTerm); // Debugging print
//        if (searchTerm == null || searchTerm.trim().isEmpty()) {
//            return tourRepository.findAll();
//        }
//        List<Tour> searchResults = bookingService.searchTours(searchTerm.trim());
//        System.out.println("Number of results: " + searchResults.size()); // Debugging print
//        return searchResults;
//    }






    // Endpoint to get ticket sales summary per tour
    @GetMapping("admin/tourTicketSummary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTicketSummaryPerTour() {
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        try {
            List<Map<String, Object>> ticketSummary = bookingService.getTicketSummaryPerTour();
            return ResponseEntity.ok(Map.of(
                    "message", "Admin: " + loggedInUser.getEmail() + " is viewing ticket summary.",
                    "summary", ticketSummary
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    // Endpoint to get customer and booking details for a specific tour
    @GetMapping("admin/tourDetails/{tourId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTourDetailsWithBookings(@PathVariable Long tourId) {
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        try {
            Map<String, Object> tourDetails = bookingService.getTourDetailsWithBookings(tourId);
            if (tourDetails != null) {
                return ResponseEntity.ok(Map.of(
                        "message", "Admin: " + loggedInUser.getEmail() + " is viewing tour details.",
                        "details", tourDetails
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "message", "Tour not found with ID: " + tourId
                ));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }












    // Create Payment Intent for a booking
    @PostMapping("customer/create-payment-intent/{tourId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createPaymentIntent(
            @PathVariable Long tourId,
            @RequestParam int numberOfTickets) {

        // Get logged-in user
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        try {
            // First create a preliminary booking
            Booking preliminaryBooking = bookingService.createBooking(loggedInUser, tourId, numberOfTickets);

            // Create Stripe Payment Intent
            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setCurrency("usd")
                    .setAmount(preliminaryBooking.getTotalPrice().longValue() * 100) // Convert to cents
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            // Return payment intent client secret and booking details
            return ResponseEntity.ok(Map.of(
                    "paymentIntentId", paymentIntent.getId(),
                    "bookingId", preliminaryBooking.getBookingId(),
                    "totalAmount", preliminaryBooking.getTotalPrice()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Confirm Booking after Successful Payment
    @PostMapping("customer/confirm-payment/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> confirmPayment(
            @PathVariable Long bookingId,
            @RequestParam String paymentIntentId) {

        // Get logged-in user
        Users loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User is not logged in.");
        }

        try {
            // Confirm booking with payment transaction ID
            Booking confirmedBooking = bookingService.confirmBooking(
                    bookingId,
                    paymentIntentId
            );

            return ResponseEntity.ok(Map.of(
                    "message", "Booking confirmed successfully",
                    "booking", confirmedBooking
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
