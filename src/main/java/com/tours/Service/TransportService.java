package com.tours.Service;

import com.tours.Entities.Transport;
import com.tours.Exception.TransportNotFoundException;
import com.tours.Repo.TransportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService {

    @Autowired
    private TransportRepo transportRepository;

    // Add a new transport
    public Transport addTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    // Get transport by ID
    public Transport getTransportById(Long id) {
        return transportRepository.findById(id)
                .orElseThrow(() -> new TransportNotFoundException("Transport not found with id " + id));
    }

    // Get all transports
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    // Update transport
    public Transport updateTransport(Long id, Transport transportDetails) {
        // Fetch the existing transport or throw TransportNotFoundException
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new TransportNotFoundException("Transport not found with id " + id));

        // Update transport details
        transport.setTransportName(transportDetails.getTransportName());
        transport.setTransportType(transportDetails.getTransportType());
        transport.setTransportDescription(transportDetails.getTransportDescription());
        transport.setEstimatedTravelTime(transportDetails.getEstimatedTravelTime());

        return transportRepository.save(transport);
    }

    // Delete transport
    public void deleteTransport(Long id) {
        // Check if the Transport exists before deleting
        transportRepository.findById(id)
                .orElseThrow(() -> new TransportNotFoundException("Transport not found with id " + id));

        transportRepository.deleteById(id);
    }
}
