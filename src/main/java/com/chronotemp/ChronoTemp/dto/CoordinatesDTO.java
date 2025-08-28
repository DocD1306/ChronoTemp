package com.chronotemp.ChronoTemp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoordinatesDTO {

    @JsonProperty("lat") // This defines the field in the response JSON that must be saved into the variable beneath
    private double latitude;
    @JsonProperty("lon")
    private double longitude;

}
