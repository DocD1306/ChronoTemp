package com.chronotemp.ChronoTemp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * This class is the DTO that stores the information related with the temperatures
 * for a given location
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureResponseDTO {

    private String country;
    private String city;

    private List<DayTemperatureDTO> temperatures;

}
