//package com.tours.Service;
//
//import com.tours.Dto.TransportDto;
//import com.tours.Entities.Transport;
//import com.tours.Repo.TransportRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class TransportService {
//
//    @Autowired
//    private TransportRepo transportRepository;
//
//    public TransportDto addTransport(TransportDto transportDto) {
//        Transport transport = mapToEntity(transportDto);
//        Transport savedTransport = transportRepository.save(transport);
//        return mapToDto(savedTransport);
//    }
//
//    public TransportDto updateTransport(Long id, TransportDto transportDto) {
//        Transport transport = transportRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transport not found"));
//        transport.setTransportName(transportDto.getTransportName());
//        transport.setTransportType(transportDto.getTransportType());
//        transport.setEstimatedTravelTime(transportDto.getEstimatedTravelTime());
//        transport.setDescription(transportDto.getDescription());
//        Transport updatedTransport = transportRepository.save(transport);
//        return mapToDto(updatedTransport);
//    }
//
//    public void deleteTransport(Long id) {
//        transportRepository.deleteById(id);
//    }
//
//    public List<TransportDto> getAllTransports() {
//        return transportRepository.findAll()
//                .stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    public TransportDto getTransportById(Long id) {
//        Transport transport = transportRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transport not found"));
//        return mapToDto(transport);
//    }
//
//    private TransportDto mapToDto(Transport transport) {
//        return new TransportDto(
//                transport.getId(),
//                transport.getTransportName(),
//                transport.getTransportType(),
//                transport.getEstimatedTravelTime(),
//                transport.getDescription()
//        );
//    }
//
//    private Transport mapToEntity(TransportDto transportDto) {
//        return new Transport(
//                transportDto.getTransportName(),
//                transportDto.getTransportType(),
//                transportDto.getEstimatedTravelTime(),
//                transportDto.getDescription()
//        );
//    }
//}









package com.tours.Service;

import com.tours.Dto.LocationDto;
import com.tours.Dto.LodgingDto;
import com.tours.Dto.ToursDto;
import com.tours.Dto.TransportDto;
import com.tours.Entities.Location;
import com.tours.Entities.Lodging;
import com.tours.Entities.Tours; // Assuming this is your Tour entity
import com.tours.Entities.Transport;
import com.tours.Repo.ToursRepo; // Assuming this is your repository interface
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToursService {

    @Autowired
    private ToursRepo tourRepository;

    // Create or Update Tour
    public ToursDto saveTour(ToursDto toursDto) {
        Tours tour = new Tours();
        tour.setId(toursDto.getId());
        tour.setTourName(toursDto.getTourName());
        tour.setTourDescription(toursDto.getTourDescription());
        tour.setTourGuide(toursDto.getTourGuide());
        tour.setStartDate(toursDto.getStartDate());
        tour.setEndDate(toursDto.getEndDate());
        tour.setMeals(toursDto.getMeals());
        tour.setActivities(toursDto.getActivities());
        tour.setPrice(toursDto.getPrice());
        tour.setTicketsAvailable(toursDto.getTicketsAvailable());
        tour.setTourImages(toursDto.getTourImages());

        // Save associated entities
        // Assuming you have methods to convert DTOs to Entities for locations, lodgings, and transports
        tour.setLocations(toursDto.getLocations().stream().map(this::convertToEntity).collect(Collectors.toList()));
        tour.setLodgings(toursDto.getLodgings().stream().map(this::convertToEntity).collect(Collectors.toList()));
        tour.setTransports(toursDto.getTransports().stream().map(this::convertToEntity).collect(Collectors.toList()));

        Tours savedTour = tourRepository.save(tour);
        return convertToDto(savedTour);
    }

    // Retrieve a Tour by ID
    public Optional<ToursDto> getTourById(Long id) {
        return tourRepository.findById(id)
                .map(this::convertToDto);
    }

    // Retrieve all Tours
    public List<ToursDto> getAllTours() {
        return tourRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Delete a Tour by ID
    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }

    // Helper method to convert Tour entity to ToursDto
    private ToursDto convertToDto(Tours
                                          tour) {
        ToursDto toursDto = new ToursDto();
        toursDto.setId(tour.getId());
        toursDto.setTourName(tour.getTourName());
        toursDto.setTourDescription(tour.getTourDescription());
        toursDto.setTourGuide(tour.getTourGuide());
        toursDto.setStartDate(tour.getStartDate());
        toursDto.setEndDate(tour.getEndDate());
        toursDto.setMeals(tour.getMeals());
        toursDto.setActivities(tour.getActivities());
        toursDto.setPrice(tour.getPrice());
        toursDto.setTicketsAvailable(tour.getTicketsAvailable());
        toursDto.setTourImages(tour.getTourImages());
        toursDto.setLocations(tour.getLocations().stream().map(this::convertToDto).collect(Collectors.toList()));
        toursDto.setLodgings(tour.getLodgings().stream().map(this::convertToDto).collect(Collectors.toList()));
        toursDto.setTransports(tour.getTransports().stream().map(this::convertToDto).collect(Collectors.toList()));
        return toursDto;
    }

    private LocationDto convertToDto(Location location) {
        if (location == null) {
            return null;
        }
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setFromLocation(location.getFromLocation());
        locationDto.setToLocation(location.getToLocation());
        locationDto.setDistance(location.getDistance());
        locationDto.setDescription(location.getDescription());
        locationDto.setEstimatedTravelTime(location.getEstimatedTravelTime());
        return locationDto;
    }

    private LodgingDto convertToDto(Lodging lodging) {
        if (lodging == null) {
            return null;
        }
        LodgingDto lodgingDto = new LodgingDto();
        lodgingDto.setId(lodging.getId());
        lodgingDto.setLodgingName(lodging.getLodgingName());
        lodgingDto.setLodgingType(lodging.getLodgingType());
        lodgingDto.setDescription(lodging.getDescription());
        lodgingDto.setAddress(lodging.getAddress());
        lodgingDto.setRating(lodging.getRating());
        return lodgingDto;
    }

    private TransportDto convertToDto(Transport transport) {
        if (transport == null) {
            return null;
        }
        TransportDto transportDto = new TransportDto();
        transportDto.setId(transport.getId());
        transportDto.setTransportName(transport.getTransportName());
        transportDto.setTransportType(transport.getTransportType());
        transportDto.setEstimatedTravelTime(transport.getEstimatedTravelTime());
        transportDto.setDescription(transport.getDescription());
        return transportDto;
    }

    private Location convertToEntity(LocationDto locationDto) {
        if (locationDto == null) {
            return null;
        }
        Location location = new Location();
        location.setId(locationDto.getId());
        location.setFromLocation(locationDto.getFromLocation());
        location.setToLocation(locationDto.getToLocation());
        location.setDistance(locationDto.getDistance());
        location.setDescription(locationDto.getDescription());
        location.setEstimatedTravelTime(locationDto.getEstimatedTravelTime());
        return location;
    }

    private Lodging convertToEntity(LodgingDto lodgingDto) {
        if (lodgingDto == null) {
            return null;
        }
        Lodging lodging = new Lodging();
        lodging.setId(lodgingDto.getId());
        lodging.setLodgingName(lodgingDto.getLodgingName());
        lodging.setLodgingType(lodgingDto.getLodgingType());
        lodging.setDescription(lodgingDto.getDescription());
        lodging.setAddress(lodgingDto.getAddress());
        lodging.setRating(lodgingDto.getRating());
        return lodging;
    }

    private Transport convertToEntity(TransportDto transportDto) {
        if (transportDto == null) {
            return null;
        }
        Transport transport = new Transport();
        transport.setId(transportDto.getId());
        transport.setTransportName(transportDto.getTransportName());
        transport.setTransportType(transportDto.getTransportType());
        transport.setEstimatedTravelTime(transportDto.getEstimatedTravelTime());
        transport.setDescription(transportDto.getDescription());
        return transport;
    }

}
