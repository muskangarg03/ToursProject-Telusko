package com.tours.Service;

import com.tours.Entities.Transport;
import com.tours.Repo.TransportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportService {

    @Autowired
    private TransportRepo transportRepository;

    public Transport addTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    public Optional<Transport> getTransportById(Long id) {
        return transportRepository.findById(id);
    }

    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    public Transport updateTransport(Long id, Transport transportDetails) {
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transport not found"));
        transport.setTransportName(transportDetails.getTransportName());
        transport.setTransportType(transportDetails.getTransportType());
        transport.setDescription(transportDetails.getDescription());
        transport.setEstimatedTravelTime(transportDetails.getEstimatedTravelTime());
        return transportRepository.save(transport);
    }

    public void deleteTransport(Long id) {
        transportRepository.deleteById(id);
    }
}

