package com.chronotemp.ChronoTemp.controller;

import com.chronotemp.ChronoTemp.dto.TemperatureRequestDTO;
import com.chronotemp.ChronoTemp.dto.TemperatureResponseDTO;
import com.chronotemp.ChronoTemp.service.CoordinatesService;
import com.chronotemp.ChronoTemp.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temperatures")
public class DayTemperatureController {

    private final CoordinatesService coordinatesService;
    private final TemperatureService temperatureService;

    @Autowired
    public DayTemperatureController(CoordinatesService coordinatesService, TemperatureService temperatureService){
        this.coordinatesService = new CoordinatesService();
        this.temperatureService = new TemperatureService();
    }

    @PostMapping("/by-city")
    public TemperatureResponseDTO getTemperature(@RequestBody TemperatureRequestDTO temperatureRequestDTO){
        return temperatureService.getTemperatureByCoordinates(12,13);

    }

}
