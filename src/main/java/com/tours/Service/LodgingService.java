package com.tours.Service;

import com.tours.Entities.Lodging;
import com.tours.Exception.LodgingNotFoundException;
import com.tours.Repo.LodgingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LodgingService {

    @Autowired
    private LodgingRepo lodgingRepository;

    // Add a new Lodging
    public Lodging addLodging(Lodging lodging) {
        return lodgingRepository.save(lodging);
    }

    // Get Lodging by ID
    public Lodging getLodgingById(Long id) {
        return lodgingRepository.findById(id)
                .orElseThrow(() -> new LodgingNotFoundException("Lodging not found with id " + id));
    }

    // Get all Lodgings
    public List<Lodging> getAllLodgings() {
        return lodgingRepository.findAll();
    }

    // Update Lodging by ID
    public Lodging updateLodging(Long id, Lodging lodgingDetails) {
        // Check if the Lodging exists before updating
        Lodging lodging = lodgingRepository.findById(id)
                .orElseThrow(() -> new LodgingNotFoundException("Lodging not found with id " + id));

        // Update Lodging details
        lodging.setLodgingName(lodgingDetails.getLodgingName());
        lodging.setLodgingType(lodgingDetails.getLodgingType());
        lodging.setLodgingDescription(lodgingDetails.getLodgingDescription());
        lodging.setAddress(lodgingDetails.getAddress());
        lodging.setRating(lodgingDetails.getRating());

        return lodgingRepository.save(lodging);
    }

    // Delete Lodging by ID
    public void deleteLodging(Long id) {
        // Check if the Lodging exists before deleting
        lodgingRepository.findById(id)
                .orElseThrow(() -> new LodgingNotFoundException("Lodging not found with id " + id));

        lodgingRepository.deleteById(id);
    }
}
