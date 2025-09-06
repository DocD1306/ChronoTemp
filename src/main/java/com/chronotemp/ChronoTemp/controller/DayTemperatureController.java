package com.chronotemp.ChronoTemp.controller;

import com.chronotemp.ChronoTemp.dto.CoordinatesDTO;
import com.chronotemp.ChronoTemp.dto.DateRangeDTO;
import com.chronotemp.ChronoTemp.dto.TemperatureRequestDTO;
import com.chronotemp.ChronoTemp.dto.TemperatureResponseDTO;
import com.chronotemp.ChronoTemp.service.CoordinatesService;
import com.chronotemp.ChronoTemp.service.TemperatureService;
import com.chronotemp.ChronoTemp.service.DateRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Class that contains the main endpoint of the API that receives and returns the information
 */
@RestController
@RequestMapping("/temperatures")
public class DayTemperatureController {

    private final CoordinatesService coordinatesService;
    private final TemperatureService temperatureService;
    private final DateRangeService dateRangeService;

    @Autowired
    public DayTemperatureController(CoordinatesService coordinatesService, TemperatureService temperatureService, DateRangeService dateRangeService){
        this.coordinatesService = coordinatesService;
        this.temperatureService = temperatureService;
        this.dateRangeService = dateRangeService;
    }

    /**
     * This method creates an endpoint that returns all the max and min temperatures of the current year
     * @param city
     * @param country
     * @param startYear
     * @param endYear
     * @return
     */
    @GetMapping
    public TemperatureResponseDTO getTemperature(
            @RequestParam String city,
            @RequestParam(defaultValue = "") String country,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear
    ){

        // Service that sets the correct range of dates for the weather data
        DateRangeDTO dateRangeDTO = dateRangeService.buildDateRange(startYear, endYear);

        // Object to be returned
        TemperatureResponseDTO temperatureResponseDTO;

        // We access the database to search for the information
        temperatureResponseDTO = temperatureService.getTemperaturesByCityAndCountryAndRange(city, country, dateRangeDTO);

        if (temperatureResponseDTO == null){ // If
            // Service that obtains the coordinates of the city
            CoordinatesDTO coordinatesDTO = coordinatesService.getCoordinates(city, country).block();

            // Create an object to pass al the arguments to the TemperatureService
            TemperatureRequestDTO temperatureRequestDTO = new TemperatureRequestDTO(city, coordinatesDTO.getCountry(), coordinatesDTO, dateRangeDTO.getStartDate(), dateRangeDTO.getEndDate());

            //Response obtained with the temperatureService
            return temperatureService.getTemperatureByCoordinates(temperatureRequestDTO);

        } else {
            return  temperatureResponseDTO;
        }

    }

}
