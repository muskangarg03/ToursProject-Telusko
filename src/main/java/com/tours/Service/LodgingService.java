package com.tours.Service;

import com.tours.Entities.Lodging;
import com.tours.Repo.LodgingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LodgingService {

    @Autowired
    private LodgingRepo lodgingRepository;

    public Lodging addLodging(Lodging lodging) {
        return lodgingRepository.save(lodging);
    }

    public Optional<Lodging> getLodgingById(Long id) {
        return lodgingRepository.findById(id);
    }

    public List<Lodging> getAllLodgings() {
        return lodgingRepository.findAll();
    }

    public Lodging updateLodging(Long id, Lodging lodgingDetails) {
        Lodging lodging = lodgingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lodging not found"));
        lodging.setLodgingName(lodgingDetails.getLodgingName());
        lodging.setLodgingType(lodgingDetails.getLodgingType());
        lodging.setDescription(lodgingDetails.getDescription());
        lodging.setAddress(lodgingDetails.getAddress());
        lodging.setRating(lodgingDetails.getRating());
        return lodgingRepository.save(lodging);
    }

    public void deleteLodging(Long id) {
        lodgingRepository.deleteById(id);
    }
}
