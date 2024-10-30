package com.tours.Dto;

import java.time.LocalDate;
import java.util.List;

public class ToursDto {
    private Long id;
    private String tourName;
    private String tourDescription;
    private String tourGuide;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> meals;
    private List<String> activities;
    private double price;
    private int ticketsAvailable;
    private List<String> tourImages;


    public ToursDto(Long id, String tourName, String tourDescription, String tourGuide, LocalDate startDate, LocalDate endDate, List<String> meals, List<String> activities, double price, int ticketsAvailable, List<String> tourImages) {
        this.id = id;
        this.tourName = tourName;
        this.tourDescription = tourDescription;
        this.tourGuide = tourGuide;
        this.startDate = startDate;
        this.endDate = endDate;
        this.meals = meals;
        this.activities = activities;
        this.price = price;
        this.ticketsAvailable = ticketsAvailable;
        this.tourImages = tourImages;
    }

    public ToursDto() {

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    public String getTourGuide() {
        return tourGuide;
    }

    public void setTourGuide(String tourGuide) {
        this.tourGuide = tourGuide;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getMeals() {
        return meals;
    }

    public void setMeals(List<String> meals) {
        this.meals = meals;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public List<String> getTourImages() {
        return tourImages;
    }

    public void setTourImages(List<String> tourImages) {
        this.tourImages = tourImages;
    }
}
