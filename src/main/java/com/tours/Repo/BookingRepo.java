package com.tours.Repo;


import com.tours.Entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    // Count tickets sold for a specific tour
    @Query("SELECT SUM(b.numberOfTickets) FROM Booking b WHERE b.tour.id = :tourId")
    Integer countTicketsSoldForTour(@Param("tourId") Long tourId);

    // Fetch all bookings for a specific tour
    List<Booking> findByTourId(Long tourId);

    @Query("SELECT SUM(b.numberOfTickets) FROM Booking b WHERE b.tour.id = :tourId AND b.paymentStatus = :status")
    Integer countTicketsSoldForTourWithSuccessfulPayment(
            @Param("tourId") Long tourId,
            @Param("status") Booking.PaymentStatus status
    );

    // Fetch bookings for a specific tour with successful payment status
    List<Booking> findByTourIdAndPaymentStatus(Long tourId, Booking.PaymentStatus paymentStatus);
}