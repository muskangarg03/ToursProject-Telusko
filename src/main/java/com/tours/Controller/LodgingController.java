package com.tours.Controller;

import com.tours.Dto.LodgingDto;
import com.tours.Service.LodgingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lodgings")
public class LodgingController {
    @Autowired
    private LodgingService lodgingService;

    @GetMapping
    public List<LodgingDto> getAllLodgings() {
        return lodgingService.getAllLodgings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LodgingDto> getLodgingById(@PathVariable Long id) {
        LodgingDto lodging = lodgingService.getLodgingById(id);
        return lodging != null ? ResponseEntity.ok(lodging) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public LodgingDto addLodging(@RequestBody LodgingDto lodgingDto) {
        return lodgingService.addLodging(lodgingDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LodgingDto> updateLodging(@PathVariable Long id, @RequestBody LodgingDto lodgingDto) {
        LodgingDto updatedLodging = lodgingService.updateLodging(id, lodgingDto);
        return updatedLodging != null ? ResponseEntity.ok(updatedLodging) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLodging(@PathVariable Long id) {
        lodgingService.deleteLodging(id);
        return ResponseEntity.noContent().build();
    }
}
