package com.tours.Controller;

import com.tours.Entities.Transport;
import com.tours.Service.TransportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transports")
@CrossOrigin(origins = "*") // Allow CORS for all origins
public class TransportController {

    @Autowired
    private TransportService transportService;

    @Operation(summary = "Add a new transport")
    @PostMapping
    public ResponseEntity<Transport> addTransport(@RequestBody Transport transport) {
        return ResponseEntity.ok(transportService.addTransport(transport));
    }

    @Operation(summary = "Get transport by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Transport> getTransportById(@PathVariable Long id) {
        Optional<Transport> transport = Optional.ofNullable(transportService.getTransportById(id));
        return transport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all transports")
    @GetMapping
    public ResponseEntity<List<Transport>> getAllTransports() {
        return ResponseEntity.ok(transportService.getAllTransports());
    }

    @Operation(summary = "Update a transport")
    @PutMapping("/{id}")
    public ResponseEntity<Transport> updateTransport(@PathVariable Long id, @RequestBody Transport transportDetails) {
        return ResponseEntity.ok(transportService.updateTransport(id, transportDetails));
    }

    @Operation(summary = "Delete a transport")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        transportService.deleteTransport(id);
        return ResponseEntity.noContent().build();
    }
}
