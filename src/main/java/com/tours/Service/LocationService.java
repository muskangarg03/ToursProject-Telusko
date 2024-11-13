package com.tours.Service;

import com.tours.Entities.Location;
import com.tours.Exception.LocationNotFoundException;
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
        return Optional.ofNullable(locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found with id " + id)));
    }

    // Get all locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Update location
    @Transactional
    public Location updateLocation(Long id, Location locationDetails) {
        // Fetch the existing location or throw LocationNotFoundException
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found with id " + id));

        // Update location details
        location.setFromLocation(locationDetails.getFromLocation());
        location.setToLocation(locationDetails.getToLocation());
        location.setDistance(locationDetails.getDistance());
        location.setDescription(locationDetails.getDescription());
        location.setEstimatedTravelTime(locationDetails.getEstimatedTravelTime());

        return locationRepository.save(location);
    }

    // Delete location
    public void deleteLocation(Long id) {
        // Check if the Location exists before deleting
        locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found with id " + id));

        locationRepository.deleteById(id);
    }
}
