


package com.tours.Controller;

import com.tours.Entities.Tour;
import com.tours.Repo.LocationRepo;
import com.tours.Repo.LodgingRepo;
import com.tours.Repo.TransportRepo;
import com.tours.Service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourService tourService;
    @Autowired
    private LocationRepo locationRepository;

    @Autowired
    private LodgingRepo lodgingRepository;

    @Autowired
    private TransportRepo transportRepository;

    @PostMapping
    public ResponseEntity<Tour> addTour(@RequestBody Tour tour) {
        // Fetch the last added Location, Lodging, and Transport IDs
        Long locationId = locationRepository.findTopByOrderByIdDesc().getId(); // Last added location
        Long lodgingId = lodgingRepository.findTopByOrderByIdDesc().getId();   // Last added lodging
        Long transportId = transportRepository.findTopByOrderByIdDesc().getId(); // Last added transport

        // Save the tour with the fetched IDs
        Tour savedTour = tourService.saveTour(tour, locationId, lodgingId, transportId);

        return ResponseEntity.ok(savedTour);
    }


    // Get all tours with their associated details (location, lodging, transport)
    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours() {
        List<Tour> tours = tourService.getAllToursWithDetails();
        return ResponseEntity.ok(tours);
    }


    // Get a specific tour by its ID with associated details
    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) {
        return tourService.getTourById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Update tour with associations
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(
            @PathVariable Long id,
            @RequestBody Tour updatedTour) {
        try {
            Tour tour = tourService.updateTourWithAssociations(id, updatedTour);
            return ResponseEntity.ok(tour);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // Delete a tour by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return ResponseEntity.noContent().build();
    }
}
