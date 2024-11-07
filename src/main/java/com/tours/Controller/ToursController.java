//
//package com.tours.Controller;
//
//import com.tours.Entities.Tours;
//import com.tours.Service.ToursService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/tours")
//public class ToursController {
//
//    private final ToursService toursService;
//
//    @Autowired
//    public ToursController(ToursService toursService) {
//        this.toursService = toursService;
//    }
//
//    // Create a new Tour
//    @PostMapping
//    @Operation(summary = "Create a new Tour", description = "Adds a new tour to the system.")
//    public ResponseEntity<Tours> createTour(@RequestBody Tours tour) {
//        Tours createdTour = toursService.createTour(tour);
//        return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
//    }
//
//    // Get all Tours
//    @GetMapping
//    @Operation(summary = "Get all Tours", description = "Fetches a list of all tours.")
//    public ResponseEntity<List<Tours>> getAllTours() {
//        List<Tours> toursList = toursService.getAllTours();
//        return new ResponseEntity<>(toursList, HttpStatus.OK);
//    }
//
//    // Get a Tour by ID
//    @GetMapping("/{id}")
//    @Operation(summary = "Get a Tour by ID", description = "Retrieves details of a tour by its ID.")
//    public ResponseEntity<Tours> getTourById(@PathVariable Long id) {
//        return toursService.getTourById(id)
//                .map(tour -> new ResponseEntity<>(tour, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    // Update an existing Tour
//    @PutMapping("/{id}")
//    @Operation(summary = "Update a Tour", description = "Updates the details of an existing tour.")
//    public ResponseEntity<Tours> updateTour(@PathVariable Long id, @RequestBody Tours updatedTour) {
//        try {
//            Tours tour = toursService.updateTour(id, updatedTour);
//            return new ResponseEntity<>(tour, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Delete a Tour by ID
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Delete a Tour", description = "Deletes a tour by its ID.")
//    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
//        try {
//            toursService.deleteTour(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}



package com.tours.Controller;

import com.tours.Entities.Tours;
import com.tours.Exception.TourNotFoundException;
import com.tours.Service.ToursService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class ToursController {

    private final ToursService toursService;

    @Autowired
    public ToursController(ToursService toursService) {
        this.toursService = toursService;
    }

    // Create a new Tour
    @PostMapping
    @Operation(summary = "Create a new Tour", description = "Adds a new tour to the system.")
    public ResponseEntity<Tours> createTour(@RequestBody Tours tour) {
        Tours createdTour = toursService.createTour(tour);
        return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
    }

    // Get all Tours
    @GetMapping
    @Operation(summary = "Get all Tours", description = "Fetches a list of all tours.")
    public ResponseEntity<List<Tours>> getAllTours() {
        List<Tours> toursList = toursService.getAllTours();
        return new ResponseEntity<>(toursList, HttpStatus.OK);
    }

    // Get a Tour by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get a Tour by ID", description = "Retrieves details of a tour by its ID.")
    public ResponseEntity<Tours> getTourById(@PathVariable Long id) {
        try {
            Tours tour = toursService.getTourById(id);
            return new ResponseEntity<>(tour, HttpStatus.OK);
        } catch (TourNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing Tour
    @PutMapping("/{id}")
    @Operation(summary = "Update a Tour", description = "Updates the details of an existing tour.")
    public ResponseEntity<Tours> updateTour(@PathVariable Long id, @RequestBody Tours updatedTour) {
        try {
            Tours tour = toursService.updateTour(id, updatedTour);
            return new ResponseEntity<>(tour, HttpStatus.OK);
        } catch (TourNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Tour by ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Tour", description = "Deletes a tour by its ID.")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        try {
            toursService.deleteTour(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TourNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
