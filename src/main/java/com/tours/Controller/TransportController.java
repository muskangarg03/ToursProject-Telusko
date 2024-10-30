package com.tours.Controller;

import com.tours.Dto.TransportDto;
import com.tours.Service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transports")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @GetMapping
    public ResponseEntity<List<TransportDto>> getAllTransports() {
        List<TransportDto> transports = transportService.getAllTransports();
        return new ResponseEntity<>(transports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportDto> getTransportById(@PathVariable Long id) {
        TransportDto transport = transportService.getTransportById(id);
        return new ResponseEntity<>(transport, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransportDto> addTransport(@RequestBody TransportDto transportDto) {
        TransportDto createdTransport = transportService.addTransport(transportDto);
        return new ResponseEntity<>(createdTransport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportDto> updateTransport(@PathVariable Long id, @RequestBody TransportDto transportDto) {
        TransportDto updatedTransport = transportService.updateTransport(id, transportDto);
        return new ResponseEntity<>(updatedTransport, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        transportService.deleteTransport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
