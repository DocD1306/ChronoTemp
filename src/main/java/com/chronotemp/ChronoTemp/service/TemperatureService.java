package com.chronotemp.ChronoTemp.service;

import com.chronotemp.ChronoTemp.dto.TemperatureResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TemperatureService {

    private final String exampleRequest = "https://historical-forecast-api.open-meteo.com/v1/forecast?latitude=38.2653307&longitude=-0.6988391&start_date=2025-08-26&end_date=2025-08-26&daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FBerlin";

    public TemperatureResponseDTO getTemperatureByCoordinates(double latitude, double longitude){
        double minTemperature = 15;
        double maxTemperature = 30;

        LocalDate exampleDate = LocalDate.of(2025, 8, 28);

        return new TemperatureResponseDTO("ES", "Madrid", exampleDate, maxTemperature, minTemperature);
    }

}
