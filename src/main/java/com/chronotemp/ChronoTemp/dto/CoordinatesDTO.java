package com.chronotemp.ChronoTemp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoordinatesDTO {

    @JsonProperty("lat") // This defines the field in the response JSON that must be saved into the variable beneath
    private double latitude;
    @JsonProperty("lon")
    private double longitude;
    @JsonProperty("display_name")
    private String country;

    public void setCountry(String country){
        String[] parts = country.split(",");
        String countryName = parts[parts.length - 1];
        countryName = countryName.trim();
        this.country = countryName;
    }
}
