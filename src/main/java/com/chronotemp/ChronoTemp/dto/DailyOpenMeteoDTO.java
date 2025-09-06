package com.chronotemp.ChronoTemp.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * DTO that maps the "daily" object from Open-Meteo response
 */
@Data
public class DailyOpenMeteoDTO {

    @JsonProperty("time")
    private List<String> date;

    @JsonProperty("temperature_2m_max")
    private List<Double> temperatureMax;

    @JsonProperty("temperature_2m_min")
    private List<Double> temperatureMin;
}
