package com.tours.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tourName;
    private String tourDescription;
    private String tourGuide;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double price;
    private Integer ticketsAvailable;

    @ElementCollection
    @CollectionTable(name = "tour_meals", joinColumns = @JoinColumn(name = "tour_id"))
    @Column(name = "meal")
    private List<String> meals;

    @ElementCollection
    @CollectionTable(name = "tour_activities", joinColumns = @JoinColumn(name = "tour_id"))
    @Column(name = "activity")
    private List<String> activities;

    @ElementCollection
    @CollectionTable(name = "tour_images", joinColumns = @JoinColumn(name = "tour_id"))
    @Column(name = "image")
    private List<String> tourImages;

    // Embedded Location information
    private String fromLocation;
    private String toLocation;
    private Double distance;
    private String locationDescription;
    private String estimatedTravelTime;

    // Embedded Lodging information
    private String lodgingName;
    private String lodgingType;
    private String lodgingDescription;
    private String lodgingAddress;
    private Double lodgingRating;

    // Embedded Transport information
    private String transportName;
    private String transportType;
    private String transportEstimatedTravelTime;
    private String transportDescription;
}