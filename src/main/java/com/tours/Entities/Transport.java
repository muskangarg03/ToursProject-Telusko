package com.tours.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "transports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transportName;
    private String transportType;
    private String estimatedTravelTime;
    private String transportDescription;

    //Getters and Setters
}
