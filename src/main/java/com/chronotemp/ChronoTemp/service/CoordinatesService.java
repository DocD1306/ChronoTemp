package com.chronotemp.ChronoTemp.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This service class is in charge of creating, sending and receiving the requests to the Nominatim API
 */
@Service
@Data
public class CoordinatesService {

    private static final String BASE_API_URL = "https://nominatim.openstreetmap.org/search?";
    private static final String CITY_REQUEST_PART = "q=%s&format=json&limit1";

    /**
     * This method creates a GET request to the Nominatim API to retrieve the coordinates
     * of the city name that is passed through as a parameter and returns them in a List.
     * @param city Name of the city which coordinates are wanted.
     * @return List with the coordinates of the city as a Double
     */
    public static List<Double> requestCoordinates(String city){
        List<Double> coordinates = new ArrayList<>();

        return coordinates;
    }


}
