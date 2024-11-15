package com.tours.Service;

import com.tours.Entities.Booking;
import com.tours.Entities.Tour;
import com.tours.Entities.Users;
import com.tours.Repo.BookingRepo;
import com.tours.Repo.TourRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if (tour.getTicketsAvailable() > numberOfTickets) {

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

    // Method to confirm booking after payment
//    public Booking confirmBooking(Long bookingId, String paymentTransactionId) {
//        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
//        if (optionalBooking.isPresent()) {
//            Booking booking = optionalBooking.get();
//            if (booking.getPaymentStatus() == Booking.PaymentStatus.SUCCESS) {
//                booking.confirmBooking(); // Confirm booking if payment is successful
//                booking.setPaymentTransactionId(paymentTransactionId);
//                return bookingRepository.save(booking);
//            } else {
//                throw new RuntimeException("Payment not successful.");
//            }
//        } else {
//            throw new RuntimeException("Booking not found.");
//        }
//    }

    public Booking confirmBooking(Long bookingId, String paymentTransactionId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            if (booking.getPaymentStatus() == Booking.PaymentStatus.PENDING) {
                // Simulating payment success
                booking.setPaymentStatus(Booking.PaymentStatus.SUCCESS);
                booking.confirmBooking(); // Confirm booking if payment is successful
                booking.setPaymentTransactionId(paymentTransactionId);
                return bookingRepository.save(booking);
            } else {
                throw new RuntimeException("Payment not successful or already processed.");
            }
        } else {
            throw new RuntimeException("Booking not found.");
        }
    }


}
