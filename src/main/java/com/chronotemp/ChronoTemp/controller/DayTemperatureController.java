package com.chronotemp.ChronoTemp.controller;

import com.chronotemp.ChronoTemp.dto.CoordinatesDTO;
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

        CoordinatesDTO coordinatesDTO = coordinatesService.getCoordinates(temperatureRequestDTO.getCityName(),
                temperatureRequestDTO.getCountryCode()).block();
        TemperatureResponseDTO temperatureResponseDTO = temperatureService.getTemperatureByCoordinates(
                coordinatesDTO.getLatitude(),
                coordinatesDTO.getLongitude(),
                temperatureRequestDTO.getCityName(),
                temperatureRequestDTO.getCountryCode());
        System.out.println(temperatureResponseDTO);
        return temperatureResponseDTO;
    }

    /*

        This is a reactive alternative to the method above that doesn't block the thread

        @PostMapping("/by-city")
        public Mono<TemperatureResponseDTO> getTemperature(@RequestBody TemperatureRequestDTO temperatureRequestDTO) {
        return coordinatesService.getCoordinates(
                temperatureRequestDTO.getCityName(),
                temperatureRequestDTO.getCountryCode()
            )
            .flatMap(coordinatesDTO ->
                Mono.just(temperatureService.getTemperatureByCoordinates(
                    coordinatesDTO.getLatitude(),
                    coordinatesDTO.getLongitude()
                ))
            );
        }
     */

}
