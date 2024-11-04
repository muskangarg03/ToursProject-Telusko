//
//package com.tours.Entities;
//
//import jakarta.persistence.*;
//import java.time.LocalDate;
//import java.util.List;
//
//@Entity
//public class Tours {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String tourName;
//
//    @Column(nullable = false, length = 2000)
//    private String tourDescription;
//
//    private String tourGuide;
//
//    private LocalDate startDate;
//
//    private LocalDate endDate;
//
//    @ElementCollection
//    private List<String> meals;
//
//    @ElementCollection
//    private List<String> activities;
//
//    private double price;
//
//    private int ticketsAvailable;
//
//    @ElementCollection
//    private List<String> tourImages; // Store paths or URLs of images
//
//    // Default Constructor
//    public Tours() {
//    }
//
//    // Parameterized Constructor
//    public Tours(Long id, String tourName, String tourDescription, String tourGuide, LocalDate startDate, LocalDate endDate, List<String> meals, List<String> activities, double price, int ticketsAvailable, List<String> tourImages) {
//        this.id = id;
//        this.tourName = tourName;
//        this.tourDescription = tourDescription;
//        this.tourGuide = tourGuide;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.meals = meals;
//        this.activities = activities;
//        this.price = price;
//        this.ticketsAvailable = ticketsAvailable;
//        this.tourImages = tourImages;
//    }
//
//    // Getters and Setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTourName() {
//        return tourName;
//    }
//
//    public void setTourName(String tourName) {
//        this.tourName = tourName;
//    }
//
//    public String getTourDescription() {
//        return tourDescription;
//    }
//
//    public void setTourDescription(String tourDescription) {
//        this.tourDescription = tourDescription;
//    }
//
//    public String getTourGuide() {
//        return tourGuide;
//    }
//
//    public void setTourGuide(String tourGuide) {
//        this.tourGuide = tourGuide;
//    }
//
//    public LocalDate getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    public LocalDate getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }
//
//    public List<String> getMeals() {
//        return meals;
//    }
//
//    public void setMeals(List<String> meals) {
//        this.meals = meals;
//    }
//
//    public List<String> getActivities() {
//        return activities;
//    }
//
//    public void setActivities(List<String> activities) {
//        this.activities = activities;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public int getTicketsAvailable() {
//        return ticketsAvailable;
//    }
//
//    public void setTicketsAvailable(int ticketsAvailable) {
//        this.ticketsAvailable = ticketsAvailable;
//    }
//
//    public List<String> getTourImages() {
//        return tourImages;
//    }
//
//    public void setTourImages(List<String> tourImages) {
//        this.tourImages = tourImages;
//    }
//}


package com.tours.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Tours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tourName;

    @Column(nullable = false, length = 2000)
    private String tourDescription;

    private String tourGuide;

    private LocalDate startDate;

    private LocalDate endDate;

    @ElementCollection
    private List<String> meals;

    @ElementCollection
    private List<String> activities;

    private double price;

    private int ticketsAvailable;

    @ElementCollection
    private List<String> tourImages; // Store paths or URLs of images

    // Define one-to-many relationships
    @OneToMany(mappedBy = "tours", cascade = CascadeType.ALL)
    private List<Location> locations;

    @OneToMany(mappedBy = "tours", cascade = CascadeType.ALL)
    private List<Lodging> lodgings;

    @OneToMany(mappedBy = "tours", cascade = CascadeType.ALL)
    private List<Transport> transports;

    // Default Constructor
    public Tours() {
    }

    // Parameterized Constructor
    public Tours(Long id, String tourName, String tourDescription, String tourGuide, LocalDate startDate, LocalDate endDate, List<String> meals, List<String> activities, double price, int ticketsAvailable, List<String> tourImages, List<Location> locations, List<Lodging> lodgings, List<Transport> transports) {
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
        this.locations = locations;
        this.lodgings = lodgings;
        this.transports = transports;
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

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Lodging> getLodgings() {
        return lodgings;
    }

    public void setLodgings(List<Lodging> lodgings) {
        this.lodgings = lodgings;
    }

    public List<Transport> getTransports() {
        return transports;
    }

    public void setTransports(List<Transport> transports) {
        this.transports = transports;
    }
}
