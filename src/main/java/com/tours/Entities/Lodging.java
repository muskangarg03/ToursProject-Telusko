package com.tours.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Lodging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lodgingName;

    @Column(nullable = false)
    private String lodgingType; // e.g., Hotel, Hostel, Guesthouse

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double rating; // Assuming rating is on a scale of 1-5

    // Constructors
    public Lodging() {
    }

    public Lodging(String lodgingName, String lodgingType, String description, String address, double rating) {
        this.lodgingName = lodgingName;
        this.lodgingType = lodgingType;
        this.description = description;
        this.address = address;
        this.rating = rating;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLodgingName() {
        return lodgingName;
    }

    public void setLodgingName(String lodgingName) {
        this.lodgingName = lodgingName;
    }

    public String getLodgingType() {
        return lodgingType;
    }

    public void setLodgingType(String lodgingType) {
        this.lodgingType = lodgingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
