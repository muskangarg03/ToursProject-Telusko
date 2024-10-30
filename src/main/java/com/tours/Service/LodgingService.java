package com.tours.Service;

import com.tours.Entities.Lodging;
import com.tours.Dto.LodgingDto;
import com.tours.Repo.LodgingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LodgingService {
    @Autowired
    private LodgingRepo lodgingRepository;

    public List<LodgingDto> getAllLodgings() {
        return lodgingRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public LodgingDto getLodgingById(Long id) {
        Lodging lodging = lodgingRepository.findById(id).orElse(null);
        return lodging != null ? convertToDto(lodging) : null;
    }

    public LodgingDto addLodging(LodgingDto lodgingDto) {
        Lodging lodging = convertToEntity(lodgingDto);
        return convertToDto(lodgingRepository.save(lodging));
    }

    public LodgingDto updateLodging(Long id, LodgingDto lodgingDto) {
        Lodging lodging = lodgingRepository.findById(id).orElse(null);
        if (lodging != null) {
            lodging.setLodgingName(lodgingDto.getLodgingName());
            lodging.setLodgingType(lodgingDto.getLodgingType());
            lodging.setDescription(lodgingDto.getDescription());
            lodging.setAddress(lodgingDto.getAddress());
            lodging.setRating(lodgingDto.getRating());
            return convertToDto(lodgingRepository.save(lodging));
        }
        return null;
    }

    public void deleteLodging(Long id) {
        lodgingRepository.deleteById(id);
    }

    // Convert Lodging entity to LodgingDto
    private LodgingDto convertToDto(Lodging lodging) {
        return new LodgingDto(lodging.getId(), lodging.getLodgingName(), lodging.getLodgingType(),
                lodging.getDescription(), lodging.getAddress(), lodging.getRating());
    }

    // Convert LodgingDto to Lodging entity
    private Lodging convertToEntity(LodgingDto lodgingDto) {
        return new Lodging(lodgingDto.getLodgingName(), lodgingDto.getLodgingType(),
                lodgingDto.getDescription(), lodgingDto.getAddress(), lodgingDto.getRating());
    }
}
