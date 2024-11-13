package com.tours.Controller;

import com.tours.Entities.Lodging;
import com.tours.Service.LodgingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lodgings")
@CrossOrigin(origins = "*") // Allow CORS for all origins
public class LodgingController {

    @Autowired
    private LodgingService lodgingService;

    @Operation(summary = "Add a new lodging")
    @PostMapping
    public ResponseEntity<Lodging> addLodging(@RequestBody Lodging lodging) {
        return ResponseEntity.ok(lodgingService.addLodging(lodging));
    }

    @Operation(summary = "Get lodging by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Lodging> getLodgingById(@PathVariable Long id) {
        Optional<Lodging> lodging = Optional.ofNullable(lodgingService.getLodgingById(id));
        return lodging.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all lodgings")
    @GetMapping
    public ResponseEntity<List<Lodging>> getAllLodgings() {
        return ResponseEntity.ok(lodgingService.getAllLodgings());
    }

    @Operation(summary = "Update a lodging")
    @PutMapping("/{id}")
    public ResponseEntity<Lodging> updateLodging(@PathVariable Long id, @RequestBody Lodging lodgingDetails) {
        return ResponseEntity.ok(lodgingService.updateLodging(id, lodgingDetails));
    }

    @Operation(summary = "Delete a lodging")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLodging(@PathVariable Long id) {
        lodgingService.deleteLodging(id);
        return ResponseEntity.noContent().build();
    }
}
