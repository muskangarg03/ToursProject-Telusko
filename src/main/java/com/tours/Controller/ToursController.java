package com.tours.Controller;


import com.tours.Dto.ToursDto;
import com.tours.Service.ToursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class ToursController {

    @Autowired
    private ToursService toursService;

    @PostMapping
    public ResponseEntity<ToursDto> addTour(@RequestBody ToursDto tourDto) {
        return ResponseEntity.ok(toursService.addTour(tourDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToursDto> updateTour(@PathVariable Long id, @RequestBody ToursDto tourDto) {
        return ResponseEntity.ok(toursService.updateTour(id, tourDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        toursService.deleteTour(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToursDto> getTourById(@PathVariable Long id) {
        return ResponseEntity.ok(toursService.getTourById(id));
    }

    @GetMapping
    public ResponseEntity<List<ToursDto>> getAllTours() {
        return ResponseEntity.ok(toursService.getAllTours());
    }
}
