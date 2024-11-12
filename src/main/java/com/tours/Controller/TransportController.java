package com.tours.Controller;

import com.tours.Entities.Transport;
import com.tours.Service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transports")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @PostMapping
    public ResponseEntity<Transport> addTransport(@RequestBody Transport transport) {
        return ResponseEntity.ok(transportService.addTransport(transport));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transport> getTransportById(@PathVariable Long id) {
        Optional<Transport> transport = transportService.getTransportById(id);
        return transport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Transport>> getAllTransports() {
        return ResponseEntity.ok(transportService.getAllTransports());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transport> updateTransport(@PathVariable Long id, @RequestBody Transport transportDetails) {
        return ResponseEntity.ok(transportService.updateTransport(id, transportDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        transportService.deleteTransport(id);
        return ResponseEntity.noContent().build();
    }
}
