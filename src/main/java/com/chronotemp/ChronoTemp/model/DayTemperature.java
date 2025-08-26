package com.chronotemp.ChronoTemp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class stores all the information related to a specific day of the year and a place.
 */
@Entity
@Table(name = "DayTemperature")
@Data
public class DayTemperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ciudad;

    private String pais;

    private double latitud;
    private double longitud;

    private LocalDate fecha;

    private Double temperaturaMax;
    private Double temperaturaMin;
    private Double temperaturaMedia;

    private LocalDateTime ultimaActualizacion;

}
