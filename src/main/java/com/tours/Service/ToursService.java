package com.tours.Service;


import com.tours.Dto.ToursDto;
import com.tours.Entities.Tours;
import com.tours.Repo.ToursRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToursService {

    private final ToursRepo toursRepository;

    public ToursService(ToursRepo toursRepository) {
        this.toursRepository = toursRepository;
    }

    // Convert entity to DTO
    private ToursDto convertToDto(Tours tour) {
        ToursDto dto = new ToursDto();
        dto.setId(tour.getId());
        dto.setTourName(tour.getTourName());
        dto.setTourDescription(tour.getTourDescription());
        dto.setTourGuide(tour.getTourGuide());
        dto.setStartDate(tour.getStartDate());
        dto.setEndDate(tour.getEndDate());
        dto.setMeals(tour.getMeals());
        dto.setActivities(tour.getActivities());
        dto.setPrice(tour.getPrice());
        dto.setTicketsAvailable(tour.getTicketsAvailable());
        dto.setTourImages(tour.getTourImages());
        return dto;
    }

    // Convert DTO to entity
    private Tours convertToEntity(ToursDto dto) {
        Tours tour = new Tours();
        tour.setTourName(dto.getTourName());
        tour.setTourDescription(dto.getTourDescription());
        tour.setTourGuide(dto.getTourGuide());
        tour.setStartDate(dto.getStartDate());
        tour.setEndDate(dto.getEndDate());
        tour.setMeals(dto.getMeals());
        tour.setActivities(dto.getActivities());
        tour.setPrice(dto.getPrice());
        tour.setTicketsAvailable(dto.getTicketsAvailable());
        tour.setTourImages(dto.getTourImages());
        return tour;
    }

    public ToursDto addTour(ToursDto dto) {
        Tours tour = convertToEntity(dto);
        Tours savedTour = toursRepository.save(tour);
        return convertToDto(savedTour);
    }

    public ToursDto updateTour(Long id, ToursDto dto) {
        Tours tour = toursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with ID: " + id));
        tour.setTourName(dto.getTourName());
        tour.setTourDescription(dto.getTourDescription());
        tour.setTourGuide(dto.getTourGuide());
        tour.setStartDate(dto.getStartDate());
        tour.setEndDate(dto.getEndDate());
        tour.setMeals(dto.getMeals());
        tour.setActivities(dto.getActivities());
        tour.setPrice(dto.getPrice());
        tour.setTicketsAvailable(dto.getTicketsAvailable());
        tour.setTourImages(dto.getTourImages());
        Tours updatedTour = toursRepository.save(tour);
        return convertToDto(updatedTour);
    }

    public void deleteTour(Long id) {
        if (!toursRepository.existsById(id)) {
            throw new EntityNotFoundException("Tour not found with ID: " + id);
        }
        toursRepository.deleteById(id);
    }

    public ToursDto getTourById(Long id) {
        Tours tour = toursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found with ID: " + id));
        return convertToDto(tour);
    }

    public List<ToursDto> getAllTours() {
        return toursRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
