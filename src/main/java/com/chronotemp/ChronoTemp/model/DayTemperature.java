package com.chronotemp.ChronoTemp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class stores all the information related to a specific day of the year and a place.
 */
@Entity
@Table(
        name = "daytemperature",
        uniqueConstraints = @UniqueConstraint(columnNames = {"city", "country", "date"})
)
@Data
public class DayTemperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String country;

    private double latitude;
    private double longitude;

    // Date of the temperatures recorded
    private LocalDate date;

    private Double maxTemperature;
    private Double minTemperature;

}
