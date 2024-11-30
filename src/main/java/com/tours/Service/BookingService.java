package com.tours.Service;

import com.tours.Entities.Booking;
import com.tours.Entities.Tour;
import com.tours.Entities.Users;
import com.tours.Repo.BookingRepo;
import com.tours.Repo.TourRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepository;

    @Autowired
    private TourRepo tourRepository;


    // Method to create a booking
    public Booking createBooking(Users customer, Long tourId, int numberOfTickets) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);
        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();

            // Ensure that there are enough tickets available
            if (tour.getTicketsAvailable() >= numberOfTickets) {

                double totalPrice = tour.getPrice() * numberOfTickets;

                Booking booking = Booking.builder()
                        .customer(customer)
                        .tour(tour)
                        .numberOfTickets(numberOfTickets)
                        .totalPrice(totalPrice)
                        .paymentStatus(Booking.PaymentStatus.PENDING) // Set to PENDING initially
                        .bookingDate(new java.util.Date())
                        .isBookingConfirmed(false)
                        .build();
                return bookingRepository.save(booking);
            } else {
                throw new RuntimeException("Not enough tickets available.");
            }
        } else {
            throw new RuntimeException("Tour not found.");
        }
    }


    // Confirm booking after payment
    public Booking confirmBooking(Long bookingId, String paymentTransactionId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            Tour tour = booking.getTour();

            // Check payment status and update availability
            if (booking.getPaymentStatus() == Booking.PaymentStatus.PENDING) {
                // Simulate payment success (you may replace this with actual gateway integration)
                booking.setPaymentStatus(Booking.PaymentStatus.SUCCESS);
                booking.confirmBooking();
                booking.setPaymentTransactionId(paymentTransactionId);

                // Update ticket availability
                if (booking.getPaymentStatus() == Booking.PaymentStatus.SUCCESS && booking.isBookingConfirmed()) {
                    int ticketsAvailable = tour.getTicketsAvailable();

                    tourRepository.save(tour); // Save updated tour
                    } else {
                        throw new RuntimeException("Not enough tickets available.");
                    }
                }

                // Save the booking confirmation
                return bookingRepository.save(booking);
            } else {
                throw new RuntimeException("Payment not successful or already processed.");
            }
        }


//    // Get ticket summary per tour
//    public List<Map<String, Object>> getTicketSummaryPerTour() {
//        List<Tour> tours = tourRepository.findAll();
//
//        List<Map<String, Object>> summary = tours.stream().map(tour -> {
//            int ticketsSold = bookingRepository.countTicketsSoldForTour(tour.getId());
//            Map<String, Object> tourSummary = new HashMap<>(); // Use HashMap
//            tourSummary.put("tourId", tour.getId());
//            tourSummary.put("tourName", tour.getTourName());
//            tourSummary.put("ticketsSold", ticketsSold);
//            tourSummary.put("ticketsAvailable", tour.getTicketsAvailable());
//            tourSummary.put("totalRevenue", ticketsSold * tour.getPrice());
//            return tourSummary;
//        }).toList();
//
//        return summary;
//    }
//
//
//    // Get detailed booking and customer data for a specific tour
//    public Map<String, Object> getTourDetailsWithBookings(Long tourId) {
//        Optional<Tour> optionalTour = tourRepository.findById(tourId);
//        if (optionalTour.isPresent()) {
//            Tour tour = optionalTour.get();
//            List<Booking> bookings = bookingRepository.findByTourId(tourId);
//            int ticketsSold = bookingRepository.countTicketsSoldForTour(tour.getId());
//
//            List<Map<String, Object>> bookingDetails = bookings.stream().map(booking -> {
//                Map<String, Object> bookingInfo = new HashMap<>(); // Use HashMap
//                bookingInfo.put("bookingId", booking.getBookingId());
//                bookingInfo.put("customerName", booking.getCustomer().getName());
//                bookingInfo.put("customerEmail", booking.getCustomer().getEmail());
//                bookingInfo.put("numberOfTickets", booking.getNumberOfTickets());
//                bookingInfo.put("totalPrice", booking.getTotalPrice());
//                bookingInfo.put("bookingDate", booking.getBookingDate());
//                bookingInfo.put("paymentStatus", booking.getPaymentStatus());
//                return bookingInfo;
//            }).toList();
//
//            Map<String, Object> tourDetails = new HashMap<>(); // Use HashMap
//            tourDetails.put("tourId", tour.getId());
//            tourDetails.put("tourName", tour.getTourName());
//            tourDetails.put("tourDescription", tour.getTourDescription());
//            tourDetails.put("ticketsSold", ticketsSold);
//            tourDetails.put("bookings", bookingDetails);
//
//            return tourDetails;
//        } else {
//            return null;
//        }
//    }

    // Get ticket summary per tour (only considering successful payments)
    public List<Map<String, Object>> getTicketSummaryPerTour() {
        List<Tour> tours = tourRepository.findAll();

        List<Map<String, Object>> summary = tours.stream().map(tour -> {
            // Count tickets only for bookings with successful payment
            int ticketsSold = bookingRepository.countTicketsSoldForTourWithSuccessfulPayment(
                    tour.getId(),
                    Booking.PaymentStatus.SUCCESS
            );

            Map<String, Object> tourSummary = new HashMap<>();
            tourSummary.put("tourId", tour.getId());
            tourSummary.put("tourName", tour.getTourName());
            tourSummary.put("ticketsSold", ticketsSold);
            tourSummary.put("ticketsAvailable", tour.getTicketsAvailable());
            tourSummary.put("totalRevenue", ticketsSold * tour.getPrice());
            return tourSummary;
        }).toList();

        return summary;
    }

    // Updated method to get detailed booking and customer data for a specific tour
    public Map<String, Object> getTourDetailsWithBookings(Long tourId) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);
        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();

            // Fetch only bookings with successful payment
            List<Booking> bookings = bookingRepository.findByTourIdAndPaymentStatus(
                    tourId,
                    Booking.PaymentStatus.SUCCESS
            );

            // Count tickets only for successful bookings
            int ticketsSold = bookingRepository.countTicketsSoldForTourWithSuccessfulPayment(
                    tour.getId(),
                    Booking.PaymentStatus.SUCCESS
            );

            List<Map<String, Object>> bookingDetails = bookings.stream().map(booking -> {
                Map<String, Object> bookingInfo = new HashMap<>();
                bookingInfo.put("bookingId", booking.getBookingId());
                bookingInfo.put("customerName", booking.getCustomer().getName());
                bookingInfo.put("customerEmail", booking.getCustomer().getEmail());
                bookingInfo.put("numberOfTickets", booking.getNumberOfTickets());
                bookingInfo.put("totalPrice", booking.getTotalPrice());
                bookingInfo.put("bookingDate", booking.getBookingDate());
                bookingInfo.put("paymentStatus", booking.getPaymentStatus());
                return bookingInfo;
            }).toList();

            Map<String, Object> tourDetails = new HashMap<>();
            tourDetails.put("tourId", tour.getId());
            tourDetails.put("tourName", tour.getTourName());
            tourDetails.put("tourDescription", tour.getTourDescription());
            tourDetails.put("ticketsSold", ticketsSold);
            tourDetails.put("bookings", bookingDetails);

            return tourDetails;
        } else {
            return null;
        }
    }

}



