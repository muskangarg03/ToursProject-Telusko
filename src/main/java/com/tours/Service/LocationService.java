package com.tours.Service;

import com.tours.Entities.Location;
import com.tours.Repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepo locationRepository;

    // Add a new location
    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    // Get location by ID
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    // Get all locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Update location
    @Transactional
    public Location updateLocation(Long id, Location locationDetails) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        location.setFromLocation(locationDetails.getFromLocation());
        location.setToLocation(locationDetails.getToLocation());
        location.setDistance(locationDetails.getDistance());
        location.setDescription(locationDetails.getDescription());
        location.setEstimatedTravelTime(locationDetails.getEstimatedTravelTime());
        return locationRepository.save(location);
    }

    // Delete location
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
