package com.tours.Dto;

public class LodgingDto {
    private Long id;
    private String lodgingName;
    private String lodgingType;
    private String description;
    private String address;
    private double rating;

    // Constructors
    public LodgingDto() {
    }

    public LodgingDto(Long id, String lodgingName, String lodgingType, String description, String address, double rating) {
        this.id = id;
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
