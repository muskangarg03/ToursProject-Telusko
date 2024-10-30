package com.tours.Service;

import com.tours.Dto.TransportDto;
import com.tours.Entities.Transport;
import com.tours.Repo.TransportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportService {

    @Autowired
    private TransportRepo transportRepository;

    public TransportDto addTransport(TransportDto transportDto) {
        Transport transport = mapToEntity(transportDto);
        Transport savedTransport = transportRepository.save(transport);
        return mapToDto(savedTransport);
    }

    public TransportDto updateTransport(Long id, TransportDto transportDto) {
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transport not found"));
        transport.setTransportName(transportDto.getTransportName());
        transport.setTransportType(transportDto.getTransportType());
        transport.setEstimatedTravelTime(transportDto.getEstimatedTravelTime());
        transport.setDescription(transportDto.getDescription());
        Transport updatedTransport = transportRepository.save(transport);
        return mapToDto(updatedTransport);
    }

    public void deleteTransport(Long id) {
        transportRepository.deleteById(id);
    }

    public List<TransportDto> getAllTransports() {
        return transportRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TransportDto getTransportById(Long id) {
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transport not found"));
        return mapToDto(transport);
    }

    private TransportDto mapToDto(Transport transport) {
        return new TransportDto(
                transport.getId(),
                transport.getTransportName(),
                transport.getTransportType(),
                transport.getEstimatedTravelTime(),
                transport.getDescription()
        );
    }

    private Transport mapToEntity(TransportDto transportDto) {
        return new Transport(
                transportDto.getTransportName(),
                transportDto.getTransportType(),
                transportDto.getEstimatedTravelTime(),
                transportDto.getDescription()
        );
    }
}
