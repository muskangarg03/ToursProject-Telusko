
package com.tours.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tours.Entities.Tour;
import com.tours.Repo.LocationRepo;
import com.tours.Repo.LodgingRepo;
import com.tours.Repo.TransportRepo;
import com.tours.Service.ImageService;
import com.tours.Service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
@CrossOrigin(origins = "*")
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private LocationRepo locationRepository;

    @Autowired
    private LodgingRepo lodgingRepository;

    @Autowired
    private TransportRepo transportRepository;

    @Autowired
    private ImageService imageService;


    //API to add a new tour plan. Location, Loadging, Transport will be added automatically based on the last added in the table
    @Operation(
            summary = "Add a new tour",
            description = "Creates a new tour with details about location, lodging, and transport. " +
                    "Automatically assigns the latest location, lodging, and transport records to the tour."
    )
    @PostMapping
    public ResponseEntity<Tour> addTourWithImages(@RequestParam("tour") String tourJson,
                                                  @RequestParam("image1") MultipartFile image1,
                                                  @RequestParam("image2") MultipartFile image2) throws JsonProcessingException {
        // Parse the tour JSON string to create a Tour object
        Tour tour = new ObjectMapper().readValue(tourJson, Tour.class);

        // Handle image uploads and get the URLs
        String image1Url = imageService.uploadImage(image1);
        String image2Url = imageService.uploadImage(image2);

        // Add image URLs to the tour
        tour.setTourImages(List.of(image1Url, image2Url));

        // Automatically assign the latest location, lodging, and transport
        Long locationId = locationRepository.findTopByOrderByIdDesc().getId();
        Long lodgingId = lodgingRepository.findTopByOrderByIdDesc().getId();
        Long transportId = transportRepository.findTopByOrderByIdDesc().getId();

        // Save the tour
        Tour savedTour = tourService.saveTour(tour, locationId, lodgingId, transportId);
        return ResponseEntity.ok(savedTour);
    }


    //API to retrieve all tours along with associated location, lodging, transport details
    @Operation(
            summary = "Retrieve all tours",
            description = "Fetches all tours in the system, along with their associated location, lodging, and transport details. " +
                    "This endpoint returns a list of tours with full associated information for each tour."
    )
    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours() {
        List<Tour> tours = tourService.getAllToursWithDetails();
        return ResponseEntity.ok(tours);
    }


    //API to retrieve a tour based on given id along with associated location, lodging, transport details
    @Operation(
            summary = "Retrieve a tour by ID",
            description = "Fetches details of a specific tour by its ID, including associated information about location, lodging, and transport. " +
                    "If the tour ID does not exist, a 404 Not Found status is returned."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) {
        return tourService.getTourById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    //API to update a tour based on given id along with associated location, lodging, transport details
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id,
                                           @RequestParam("tour") String updatedTourJson,
                                           @RequestParam(value = "image1", required = false) MultipartFile image1,
                                           @RequestParam(value = "image2", required = false) MultipartFile image2) {
        try {
            Tour updatedTour = new ObjectMapper().readValue(updatedTourJson, Tour.class);
            List<String> currentImages = updatedTour.getTourImages();

            // Handle first image update
            if (image1 != null && !currentImages.isEmpty()) {
                try {
                    String newImage1 = imageService.updateImage(currentImages.get(0), image1);
                    currentImages.set(0, newImage1);
                } catch (RuntimeException e) {
                }
            }

            // Handle second image update
            if (image2 != null && currentImages.size() > 1) {
                try {
                    String newImage2 = imageService.updateImage(currentImages.get(1), image2);
                    currentImages.set(1, newImage2);
                } catch (RuntimeException e) {

                }
            }

            Tour tour = tourService.updateTourWithAssociations(id, updatedTour);
            return ResponseEntity.ok(tour);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    //API to delete a tour based on given id along with associated location, lodging, transport details
    @Operation(
            summary = "Delete a tour",
            description = "Deletes a specific tour by its ID along with associated images."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        try {
            // Retrieve the tour to get image URLs before deletion
            Tour tour = tourService.getTourById(id).orElseThrow(() -> new RuntimeException("Tour not found"));

            // Delete associated images
            for (String imageUrl : tour.getTourImages()) {
                imageService.deleteImage(imageUrl);
            }

            // Delete the tour
            tourService.deleteTour(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
