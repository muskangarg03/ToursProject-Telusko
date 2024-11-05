
//package com.tours.Controller;
//
//import com.tours.Dto.ToursDto;
//import com.tours.Entities.Tours;
//import com.tours.Service.ToursService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
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
//    // Create a new tour
//    @PostMapping
//    public ResponseEntity<ToursDto> createTour(@RequestBody ToursDto tourDto) {
//        Tours tour = convertToEntity(tourDto);
//        Tours savedTour = toursService.createTour(tour);
//        return new ResponseEntity<>(convertToDto(savedTour), HttpStatus.CREATED);
//    }
//
//    // Get all tours
//    @GetMapping
//    public ResponseEntity<List<ToursDto>> getAllTours() {
//        List<Tours> toursList = toursService.getAllTours();
//        List<ToursDto> toursDtoList = toursList.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(toursDtoList, HttpStatus.OK);
//    }
//
//    // Get a tour by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<ToursDto> getTourById(@PathVariable Long id) {
//        Optional<Tours> tour = toursService.getTourById(id);
//        return tour.map(value -> new ResponseEntity<>(convertToDto(value), HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    // Update an existing tour by ID
//    @PutMapping("/{id}")
//    public ResponseEntity<ToursDto> updateTour(@PathVariable Long id, @RequestBody ToursDto updatedTourDto) {
//        Tours updatedTour = convertToEntity(updatedTourDto);
//        Tours savedTour = toursService.updateTour(id, updatedTour);
//        return new ResponseEntity<>(convertToDto(savedTour), HttpStatus.OK);
//    }
//
//    // Delete a tour by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
//        toursService.deleteTour(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    // Helper method to convert DTO to entity
//    private Tours convertToEntity(ToursDto dto) {
//        Tours tour = new Tours();
//        tour.setId(dto.getId());
//        tour.setTourName(dto.getTourName());
//        tour.setTourDescription(dto.getTourDescription());
//        tour.setTourGuide(dto.getTourGuide());
//        tour.setStartDate(dto.getStartDate());
//        tour.setEndDate(dto.getEndDate());
//        tour.setPrice(dto.getPrice());
//        tour.setTicketsAvailable(dto.getTicketsAvailable());
//        tour.setMeals(dto.getMeals());
//        tour.setActivities(dto.getActivities());
//        tour.setTourImages(dto.getTourImages());
//
//        // Location details
//        tour.setFromLocation(dto.getFromLocation());
//        tour.setToLocation(dto.getToLocation());
//        tour.setDistance(dto.getDistance());
//        tour.setLocationDescription(dto.getLocationDescription());
//        tour.setEstimatedTravelTime(dto.getEstimatedTravelTime());
//
//        // Lodging details
//        tour.setLodgingName(dto.getLodgingName());
//        tour.setLodgingType(dto.getLodgingType());
//        tour.setLodgingDescription(dto.getLodgingDescription());
//        tour.setLodgingAddress(dto.getLodgingAddress());
//        tour.setLodgingRating(dto.getLodgingRating());
//
//        // Transport details
//        tour.setTransportName(dto.getTransportName());
//        tour.setTransportType(dto.getTransportType());
//        tour.setTransportEstimatedTravelTime(dto.getTransportEstimatedTravelTime());
//        tour.setTransportDescription(dto.getTransportDescription());
//
//        return tour;
//    }
//
//    // Helper method to convert entity to DTO
//    private ToursDto convertToDto(Tours tour) {
//        ToursDto dto = new ToursDto();
//        dto.setId(tour.getId());
//        dto.setTourName(tour.getTourName());
//        dto.setTourDescription(tour.getTourDescription());
//        dto.setTourGuide(tour.getTourGuide());
//        dto.setStartDate(tour.getStartDate());
//        dto.setEndDate(tour.getEndDate());
//        dto.setPrice(tour.getPrice());
//        dto.setTicketsAvailable(tour.getTicketsAvailable());
//        dto.setMeals(tour.getMeals());
//        dto.setActivities(tour.getActivities());
//        dto.setTourImages(tour.getTourImages());
//
//        // Location details
//        dto.setFromLocation(tour.getFromLocation());
//        dto.setToLocation(tour.getToLocation());
//        dto.setDistance(tour.getDistance());
//        dto.setLocationDescription(tour.getLocationDescription());
//        dto.setEstimatedTravelTime(tour.getEstimatedTravelTime());
//
//        // Lodging details
//        dto.setLodgingName(tour.getLodgingName());
//        dto.setLodgingType(tour.getLodgingType());
//        dto.setLodgingDescription(tour.getLodgingDescription());
//        dto.setLodgingAddress(tour.getLodgingAddress());
//        dto.setLodgingRating(tour.getLodgingRating());
//
//        // Transport details
//        dto.setTransportName(tour.getTransportName());
//        dto.setTransportType(tour.getTransportType());
//        dto.setTransportEstimatedTravelTime(tour.getTransportEstimatedTravelTime());
//        dto.setTransportDescription(tour.getTransportDescription());
//
//        return dto;
//    }
//}


package com.tours.Controller;

import com.tours.Entities.Tours;
import com.tours.Service.ToursService;
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
    public ResponseEntity<Tours> createTour(@RequestBody Tours tour) {
        Tours createdTour = toursService.createTour(tour);
        return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
    }

    // Get all Tours
    @GetMapping
    public ResponseEntity<List<Tours>> getAllTours() {
        List<Tours> toursList = toursService.getAllTours();
        return new ResponseEntity<>(toursList, HttpStatus.OK);
    }

    // Get a Tour by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tours> getTourById(@PathVariable Long id) {
        return toursService.getTourById(id)
                .map(tour -> new ResponseEntity<>(tour, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing Tour
    @PutMapping("/{id}")
    public ResponseEntity<Tours> updateTour(@PathVariable Long id, @RequestBody Tours updatedTour) {
        try {
            Tours tour = toursService.updateTour(id, updatedTour);
            return new ResponseEntity<>(tour, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Tour by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        try {
            toursService.deleteTour(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
