package com.chronotemp.ChronoTemp.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@Builder
public class DayTemperatureDTO {

    private String city;
    private String country;

    private LocalDate date;

    private double maxTemperature;
    private double minTemperature;

    public DayTemperatureDTO(String city, String country, LocalDate date, double maxTemperature, double minTemperature) {
        this.city = city;
        this.country = country;
        this.date = date;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }
}
