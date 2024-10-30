package com.tours.Dto;

public class TransportDto {
    private Long id;
    private String transportName;
    private String transportType;
    private String estimatedTravelTime;
    private String description;

    // Constructors
    public TransportDto() {
    }

    public TransportDto(Long id, String transportName, String transportType, String estimatedTravelTime, String description) {
        this.id = id;
        this.transportName = transportName;
        this.transportType = transportType;
        this.estimatedTravelTime = estimatedTravelTime;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getEstimatedTravelTime() {
        return estimatedTravelTime;
    }

    public void setEstimatedTravelTime(String estimatedTravelTime) {
        this.estimatedTravelTime = estimatedTravelTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
