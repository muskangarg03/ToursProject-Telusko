package com.tours.Service;

import com.tours.Dto.LocationDto;
import com.tours.Entities.Location;
import com.tours.Repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepo locationRepository;

    public LocationDto addLocation(LocationDto locationDto) {
        Location location = mapToEntity(locationDto);
        Location savedLocation = locationRepository.save(location);
        return mapToDto(savedLocation);
    }

    public LocationDto updateLocation(Long id, LocationDto locationDto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        location.setFromLocation(locationDto.getFromLocation());
        location.setToLocation(locationDto.getToLocation());
        location.setDistance(locationDto.getDistance());
        location.setDescription(locationDto.getDescription());
        location.setEstimatedTravelTime(locationDto.getEstimatedTravelTime());
        Location updatedLocation = locationRepository.save(location);
        return mapToDto(updatedLocation);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public LocationDto getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        return mapToDto(location);
    }

    private LocationDto mapToDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getFromLocation(),
                location.getToLocation(),
                location.getDistance(),
                location.getDescription(),
                location.getEstimatedTravelTime()
        );
    }

    private Location mapToEntity(LocationDto locationDto) {
        return new Location(
                locationDto.getFromLocation(),
                locationDto.getToLocation(),
                locationDto.getDistance(),
                locationDto.getDescription(),
                locationDto.getEstimatedTravelTime()
        );
    }
}
