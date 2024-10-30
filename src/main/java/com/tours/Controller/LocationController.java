package com.tours.Controller;


import com.tours.Dto.LocationDto;
import com.tours.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationService.addLocation(locationDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable Long id, @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationService.updateLocation(id, locationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok("Location deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }
}
