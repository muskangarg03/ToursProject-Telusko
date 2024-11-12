
package com.tours.Service;

import com.tours.Entities.Tour;
import com.tours.Entities.Location;
import com.tours.Entities.Lodging;
import com.tours.Entities.Transport;
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
                .orElseThrow(() -> new RuntimeException("Location not found with id " + locationId));
        Lodging lodging = lodgingRepo.findById(lodgingId)
                .orElseThrow(() -> new RuntimeException("Lodging not found with id " + lodgingId));
        Transport transport = transportRepo.findById(transportId)
                .orElseThrow(() -> new RuntimeException("Transport not found with id " + transportId));

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
        return tourRepo.findById(id);
    }


    // Delete a tour by its ID with associated location, lodging anf transport
    @Transactional
    public void deleteTour(Long id) {
        Tour tour = tourRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found with id " + id));

        // First delete associated entities using their respective services
        if (tour.getLocation() != null) {
            // Get the location ID before deletion
            Long locationId = tour.getLocation().getId();
            // Remove the reference from tour
            tour.setLocation(null);
            // Delete the location using the injected service
            locationService.deleteLocation(locationId);
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




//    @Transactional
//    public Tour updateTourWithAssociations(Long tourId, Tour updatedTour) {
//        Tour existingTour = tourRepo.findById(tourId)
//                .orElseThrow(() -> new RuntimeException("Tour not found with id " + tourId));
//
//        // Update Location
//        Location existingLocation = existingTour.getLocation();
//        Location updatedLocation = updatedTour.getLocation();
//        if (existingLocation != null && updatedLocation != null) {
//            existingLocation.setFromLocation(updatedLocation.getFromLocation());
//            existingLocation.setToLocation(updatedLocation.getToLocation());
//            existingLocation.setDistance(updatedLocation.getDistance());
//            existingLocation.setDescription(updatedLocation.getDescription());
//            existingLocation.setEstimatedTravelTime(updatedLocation.getEstimatedTravelTime());
//            locationRepo.save(existingLocation);
//        }
//
//        // Update Lodging
//        Lodging existingLodging = existingTour.getLodging();
//        Lodging updatedLodging = updatedTour.getLodging();
//        if (existingLodging != null && updatedLodging != null) {
//            existingLodging.setLodgingName(updatedLodging.getLodgingName());
//            existingLodging.setLodgingType(updatedLodging.getLodgingType());
//            existingLodging.setDescription(updatedLodging.getDescription());
//            existingLodging.setAddress(updatedLodging.getAddress());
//            existingLodging.setRating(updatedLodging.getRating());
//            lodgingRepo.save(existingLodging);
//        }
//
//        // Update Transport
//        Transport existingTransport = existingTour.getTransport();
//        Transport updatedTransport = updatedTour.getTransport();
//        if (existingTransport != null && updatedTransport != null) {
//            existingTransport.setTransportName(updatedTransport.getTransportName());
//            existingTransport.setTransportType(updatedTransport.getTransportType());
//            existingTransport.setEstimatedTravelTime(updatedTransport.getEstimatedTravelTime());
//            existingTransport.setDescription(updatedTransport.getDescription());
//            transportRepo.save(existingTransport);
//        }
//
//        // Update Tour details
//        existingTour.setTourName(updatedTour.getTourName());
//        existingTour.setTourDescription(updatedTour.getTourDescription());
//        existingTour.setTourGuide(updatedTour.getTourGuide());
//        existingTour.setStartDate(updatedTour.getStartDate());
//        existingTour.setEndDate(updatedTour.getEndDate());
//        existingTour.setMeals(updatedTour.getMeals());
//        existingTour.setActivities(updatedTour.getActivities());
//        existingTour.setPrice(updatedTour.getPrice());
//        existingTour.setTicketsAvailable(updatedTour.getTicketsAvailable());
//        existingTour.setTourImages(updatedTour.getTourImages());
//
//        return tourRepo.save(existingTour);
//    }
@Transactional
public Tour updateTourWithAssociations(Long tourId, Tour updatedTour) {
    Tour existingTour = tourRepo.findById(tourId)
            .orElseThrow(() -> new RuntimeException("Tour not found with id " + tourId));

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
