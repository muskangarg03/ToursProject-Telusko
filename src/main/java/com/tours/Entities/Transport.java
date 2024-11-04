package com.tours.Entities;

import jakarta.persistence.*;

@Entity
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transportName;

    @Column(nullable = false)
    private String transportType;

    private String estimatedTravelTime;

    private String description;

    @ManyToOne
    @JoinColumn(name = "tours_id") // Foreign key to the Tours entity
    private Tours tours;

    // Constructors
    public Transport() {
    }

    public Transport(String transportName, String transportType, String estimatedTravelTime, String description) {
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

    public Tours getTours() {
        return tours;
    }

    public void setTours(Tours tours) {
        this.tours = tours;
    }
}
