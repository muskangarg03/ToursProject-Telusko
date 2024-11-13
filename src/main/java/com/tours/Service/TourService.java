package com.tours.Service;

import com.tours.Entities.Tour;
import com.tours.Entities.Location;
import com.tours.Entities.Lodging;
import com.tours.Entities.Transport;
import com.tours.Exception.TourNotFoundException;
import com.tours.Exception.LocationNotFoundException;
import com.tours.Exception.LodgingNotFoundException;
import com.tours.Exception.TransportNotFoundException;
import com.tours.Repo.TourRepo;
import com.tours.Repo.LocationRepo;
import com.tours.Repo.LodgingRepo;
import com.tours.Repo.TransportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    @Autowired
    private TourRepo tourRepo;

    @Autowired
    private LocationRepo locationRepo;

    @Autowired
    private LodgingRepo lodgingRepo;

    @Autowired
    private TransportRepo transportRepo;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LodgingService lodgingService;

    @Autowired
    private TransportService transportService;

    // Save a new tour
    public Tour saveTour(Tour tour, Long locationId, Long lodgingId, Long transportId) {
        // Fetch Location, Lodging, and Transport by their IDs
        Location location = locationRepo.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException("Location not found with id " + locationId));
        Lodging lodging = lodgingRepo.findById(lodgingId)
                .orElseThrow(() -> new LodgingNotFoundException("Lodging not found with id " + lodgingId));
        Transport transport = transportRepo.findById(transportId)
                .orElseThrow(() -> new TransportNotFoundException("Transport not found with id " + transportId));

        // Set the fetched entities in the Tour object
        tour.setLocation(location);
        tour.setLodging(lodging);
        tour.setTransport(transport);

        // Save the Tour with the related entities
        return tourRepo.save(tour);
    }

    // Get all tours with their details
    public List<Tour> getAllToursWithDetails() {
        return tourRepo.findAllToursWithDetails();
    }

    // Get a tour by its ID
    public Optional<Tour> getTourById(Long id) {
        return Optional.ofNullable(tourRepo.findById(id)
                .orElseThrow(() -> new TourNotFoundException("Tour not found with id " + id)));
    }

    // Delete a tour by its ID with associated location, lodging, and transport
    @Transactional
    public void deleteTour(Long id) {
        Tour tour = tourRepo.findById(id)
                .orElseThrow(() -> new TourNotFoundException("Tour not found with id " + id));

        // First delete associated entities using their respective services
        if (tour.getLocation() != null) {
            Long locationId = tour.getLocation().getId();
            tour.setLocation(null); // Remove the reference from tour
            locationService.deleteLocation(locationId); // Delete location
        }

        if (tour.getLodging() != null) {
            Long lodgingId = tour.getLodging().getId();
            tour.setLodging(null);
            lodgingService.deleteLodging(lodgingId);
        }

        if (tour.getTransport() != null) {
            Long transportId = tour.getTransport().getId();
            tour.setTransport(null);
            transportService.deleteTransport(transportId);
        }

        // Finally delete the tour
        tourRepo.delete(tour);
    }

    // Update a tour with associations
    @Transactional
    public Tour updateTourWithAssociations(Long tourId, Tour updatedTour) {
        Tour existingTour = tourRepo.findById(tourId)
                .orElseThrow(() -> new TourNotFoundException("Tour not found with id " + tourId));

        // Update Location
        Location updatedLocation = updatedTour.getLocation();
        if (existingTour.getLocation() != null && updatedLocation != null) {
            locationService.updateLocation(existingTour.getLocation().getId(), updatedLocation);
        }

        // Update Lodging
        Lodging updatedLodging = updatedTour.getLodging();
        if (existingTour.getLodging() != null && updatedLodging != null) {
            lodgingService.updateLodging(existingTour.getLodging().getId(), updatedLodging);
        }

        // Update Transport
        Transport updatedTransport = updatedTour.getTransport();
        if (existingTour.getTransport() != null && updatedTransport != null) {
            transportService.updateTransport(existingTour.getTransport().getId(), updatedTransport);
        }

        // Update Tour details
        existingTour.setTourName(updatedTour.getTourName());
        existingTour.setTourDescription(updatedTour.getTourDescription());
        existingTour.setTourGuide(updatedTour.getTourGuide());
        existingTour.setStartDate(updatedTour.getStartDate());
        existingTour.setEndDate(updatedTour.getEndDate());
        existingTour.setMeals(updatedTour.getMeals());
        existingTour.setActivities(updatedTour.getActivities());
        existingTour.setPrice(updatedTour.getPrice());
        existingTour.setTicketsAvailable(updatedTour.getTicketsAvailable());
        existingTour.setTourImages(updatedTour.getTourImages());

        return tourRepo.save(existingTour);
    }
}
