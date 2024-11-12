package com.tours.Controller;


import com.tours.Entities.Lodging;
import com.tours.Service.LodgingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lodgings")
public class LodgingController {

    @Autowired
    private LodgingService lodgingService;

    @PostMapping
    public ResponseEntity<Lodging> addLodging(@RequestBody Lodging lodging) {
        return ResponseEntity.ok(lodgingService.addLodging(lodging));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lodging> getLodgingById(@PathVariable Long id) {
        Optional<Lodging> lodging = lodgingService.getLodgingById(id);
        return lodging.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Lodging>> getAllLodgings() {
        return ResponseEntity.ok(lodgingService.getAllLodgings());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lodging> updateLodging(@PathVariable Long id, @RequestBody Lodging lodgingDetails) {
        return ResponseEntity.ok(lodgingService.updateLodging(id, lodgingDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLodging(@PathVariable Long id) {
        lodgingService.deleteLodging(id);
        return ResponseEntity.noContent().build();
    }
}
