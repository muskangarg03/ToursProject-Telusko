//package com.tours.Dto;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class ToursDto {
//    private Long id;
//    private String tourName;
//    private String tourDescription;
//    private String tourGuide;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private List<String> meals;
//    private List<String> activities;
//    private double price;
//    private int ticketsAvailable;
//    private List<String> tourImages;
//
//
//    public ToursDto(Long id, String tourName, String tourDescription, String tourGuide, LocalDate startDate, LocalDate endDate, List<String> meals, List<String> activities, double price, int ticketsAvailable, List<String> tourImages) {
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
//    public ToursDto() {
//
//    }
//
//    // Getters and Setters
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







//
//package com.tours.Dto;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Objects;
//
//public class ToursDto {
//
//    private Long id;
//    private String tourName;
//    private String tourDescription;
//    private String tourGuide;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private Double price;
//    private Integer ticketsAvailable;
//
//    private List<String> meals;
//    private List<String> activities;
//    private List<String> tourImages;
//
//    // Location fields
//    private String fromLocation;
//    private String toLocation;
//    private Double distance;
//    private String locationDescription;
//    private String estimatedTravelTime;
//
//    // Lodging fields
//    private String lodgingName;
//    private String lodgingType;
//    private String lodgingDescription;
//    private String lodgingAddress;
//    private Double lodgingRating;
//
//    // Transport fields
//    private String transportName;
//    private String transportType;
//    private String transportEstimatedTravelTime;
//    private String transportDescription;
//
//    // Default constructor
//    public ToursDto() {
//    }
//
//    // Parameterized constructor
//    public ToursDto(Long id, String tourName, String tourDescription, String tourGuide, LocalDate startDate,
//                    LocalDate endDate, Double price, Integer ticketsAvailable, List<String> meals,
//                    List<String> activities, List<String> tourImages, String fromLocation, String toLocation,
//                    Double distance, String locationDescription, String estimatedTravelTime, String lodgingName,
//                    String lodgingType, String lodgingDescription, String lodgingAddress, Double lodgingRating,
//                    String transportName, String transportType, String transportEstimatedTravelTime,
//                    String transportDescription) {
//        this.id = id;
//        this.tourName = tourName;
//        this.tourDescription = tourDescription;
//        this.tourGuide = tourGuide;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.price = price;
//        this.ticketsAvailable = ticketsAvailable;
//        this.meals = meals;
//        this.activities = activities;
//        this.tourImages = tourImages;
//        this.fromLocation = fromLocation;
//        this.toLocation = toLocation;
//        this.distance = distance;
//        this.locationDescription = locationDescription;
//        this.estimatedTravelTime = estimatedTravelTime;
//        this.lodgingName = lodgingName;
//        this.lodgingType = lodgingType;
//        this.lodgingDescription = lodgingDescription;
//        this.lodgingAddress = lodgingAddress;
//        this.lodgingRating = lodgingRating;
//        this.transportName = transportName;
//        this.transportType = transportType;
//        this.transportEstimatedTravelTime = transportEstimatedTravelTime;
//        this.transportDescription = transportDescription;
//    }
//
//    // Getters and Setters
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
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public Integer getTicketsAvailable() {
//        return ticketsAvailable;
//    }
//
//    public void setTicketsAvailable(Integer ticketsAvailable) {
//        this.ticketsAvailable = ticketsAvailable;
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
//    public List<String> getTourImages() {
//        return tourImages;
//    }
//
//    public void setTourImages(List<String> tourImages) {
//        this.tourImages = tourImages;
//    }
//
//    public String getFromLocation() {
//        return fromLocation;
//    }
//
//    public void setFromLocation(String fromLocation) {
//        this.fromLocation = fromLocation;
//    }
//
//    public String getToLocation() {
//        return toLocation;
//    }
//
//    public void setToLocation(String toLocation) {
//        this.toLocation = toLocation;
//    }
//
//    public Double getDistance() {
//        return distance;
//    }
//
//    public void setDistance(Double distance) {
//        this.distance = distance;
//    }
//
//    public String getLocationDescription() {
//        return locationDescription;
//    }
//
//    public void setLocationDescription(String locationDescription) {
//        this.locationDescription = locationDescription;
//    }
//
//    public String getEstimatedTravelTime() {
//        return estimatedTravelTime;
//    }
//
//    public void setEstimatedTravelTime(String estimatedTravelTime) {
//        this.estimatedTravelTime = estimatedTravelTime;
//    }
//
//    public String getLodgingName() {
//        return lodgingName;
//    }
//
//    public void setLodgingName(String lodgingName) {
//        this.lodgingName = lodgingName;
//    }
//
//    public String getLodgingType() {
//        return lodgingType;
//    }
//
//    public void setLodgingType(String lodgingType) {
//        this.lodgingType = lodgingType;
//    }
//
//    public String getLodgingDescription() {
//        return lodgingDescription;
//    }
//
//    public void setLodgingDescription(String lodgingDescription) {
//        this.lodgingDescription = lodgingDescription;
//    }
//
//    public String getLodgingAddress() {
//        return lodgingAddress;
//    }
//
//    public void setLodgingAddress(String lodgingAddress) {
//        this.lodgingAddress = lodgingAddress;
//    }
//
//    public Double getLodgingRating() {
//        return lodgingRating;
//    }
//
//    public void setLodgingRating(Double lodgingRating) {
//        this.lodgingRating = lodgingRating;
//    }
//
//    public String getTransportName() {
//        return transportName;
//    }
//
//    public void setTransportName(String transportName) {
//        this.transportName = transportName;
//    }
//
//    public String getTransportType() {
//        return transportType;
//    }
//
//    public void setTransportType(String transportType) {
//        this.transportType = transportType;
//    }
//
//    public String getTransportEstimatedTravelTime() {
//        return transportEstimatedTravelTime;
//    }
//
//    public void setTransportEstimatedTravelTime(String transportEstimatedTravelTime) {
//        this.transportEstimatedTravelTime = transportEstimatedTravelTime;
//    }
//
//    public String getTransportDescription() {
//        return transportDescription;
//    }
//
//    public void setTransportDescription(String transportDescription) {
//        this.transportDescription = transportDescription;
//    }
//
//    // equals, hashCode, and toString methods
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ToursDto toursDto = (ToursDto) o;
//        return Objects.equals(id, toursDto.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//
//    @Override
//    public String toString() {
//        return "ToursDto{" +
//                "id=" + id +
//                ", tourName='" + tourName + '\'' +
//                ", tourDescription='" + tourDescription + '\'' +
//                ", tourGuide='" + tourGuide + '\'' +
//                ", startDate=" + startDate +
//                ", endDate=" + endDate +
//                ", price=" + price +
//                ", ticketsAvailable=" + ticketsAvailable +
//                ", meals=" + meals +
//                ", activities=" + activities +
//                ", tourImages=" + tourImages +
//                ", fromLocation='" + fromLocation + '\'' +
//                ", toLocation='" + toLocation + '\'' +
//                ", distance=" + distance +
//                ", locationDescription='" + locationDescription + '\'' +
//                ", estimatedTravelTime='" + estimatedTravelTime + '\'' +
//                ", lodgingName='" + lodgingName + '\'' +
//                ", lodgingType='" + lodgingType + '\'' +
//                ", lodgingDescription='" + lodgingDescription + '\'' +
//                ", lodgingAddress='" + lodgingAddress + '\'' +
//                ", lodgingRating=" + lodgingRating +
//                ", transportName='" + transportName + '\'' +
//                ", transportType='" + transportType + '\'' +
//                ", transportEstimatedTravelTime='" + transportEstimatedTravelTime + '\'' +
//                ", transportDescription='" + transportDescription + '\'' +
//                '}';
//    }
//}
