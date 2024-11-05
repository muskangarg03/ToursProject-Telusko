
package com.tours.Service;

import com.tours.Repo.ToursRepo;
import com.tours.Entities.Tours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToursService {

    private final ToursRepo tourRepository;

    @Autowired
    public ToursService(ToursRepo tourRepository) {
        this.tourRepository = tourRepository;
    }

    // Create a new Tour
    public Tours createTour(Tours tour) {
        return tourRepository.save(tour);
    }

    // Get all Tours
    public List<Tours> getAllTours() {
        return tourRepository.findAll();
    }

    // Get a Tour by ID
    public Optional<Tours> getTourById(Long id) {
        return tourRepository.findById(id);
    }

    // Update an existing Tour
    public Tours updateTour(Long id, Tours updatedTour) {
        return tourRepository.findById(id).map(tour -> {
            tour.setTourName(updatedTour.getTourName());
            tour.setTourDescription(updatedTour.getTourDescription());
            tour.setTourGuide(updatedTour.getTourGuide());
            tour.setStartDate(updatedTour.getStartDate());
            tour.setEndDate(updatedTour.getEndDate());
            tour.setPrice(updatedTour.getPrice());
            tour.setTicketsAvailable(updatedTour.getTicketsAvailable());
            tour.setMeals(updatedTour.getMeals());
            tour.setActivities(updatedTour.getActivities());
            tour.setTourImages(updatedTour.getTourImages());

            // Location details
            tour.setFromLocation(updatedTour.getFromLocation());
            tour.setToLocation(updatedTour.getToLocation());
            tour.setDistance(updatedTour.getDistance());
            tour.setLocationDescription(updatedTour.getLocationDescription());
            tour.setEstimatedTravelTime(updatedTour.getEstimatedTravelTime());

            // Lodging details
            tour.setLodgingName(updatedTour.getLodgingName());
            tour.setLodgingType(updatedTour.getLodgingType());
            tour.setLodgingDescription(updatedTour.getLodgingDescription());
            tour.setLodgingAddress(updatedTour.getLodgingAddress());
            tour.setLodgingRating(updatedTour.getLodgingRating());

            // Transport details
            tour.setTransportName(updatedTour.getTransportName());
            tour.setTransportType(updatedTour.getTransportType());
            tour.setTransportEstimatedTravelTime(updatedTour.getTransportEstimatedTravelTime());
            tour.setTransportDescription(updatedTour.getTransportDescription());

            return tourRepository.save(tour);
        }).orElseThrow(() -> new RuntimeException("Tour not found with id " + id));
    }

    // Delete a Tour by ID
    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }
}
