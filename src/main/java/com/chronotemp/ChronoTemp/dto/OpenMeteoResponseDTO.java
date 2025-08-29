package com.chronotemp.ChronoTemp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * This class is the DTO that stores the information related with the temperatures
 * for a given location
 */
@Data
@AllArgsConstructor
public class OpenMeteoResponseDTO {

    // This is a temporary structure, in the future it will be changed so that the object stores an array of DayTemperatures
    private String country;
    private String city;

    private LocalDate date;

    @JsonProperty("daily")
    private DailyOpenMeteoDTO dailyOpenMeteoDTO;

}
