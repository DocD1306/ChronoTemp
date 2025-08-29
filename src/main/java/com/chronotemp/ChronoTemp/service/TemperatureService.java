package com.chronotemp.ChronoTemp.service;

import com.chronotemp.ChronoTemp.dto.OpenMeteoResponseDTO;
import com.chronotemp.ChronoTemp.dto.TemperatureResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class TemperatureService {

    private final String BASE_URL = "https://historical-forecast-api.open-meteo.com";
    private final String anotherPart = "/v1/forecast?latitude=38.2653307&longitude=-0.6988391&start_date=2025-08-26&end_date=2025-08-26&daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FBerlin";

    private final WebClient webClient;

    public TemperatureService(){
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("User-Agent", "ChronoTemp/0.1 (diegofjda2@gmail.com)")
                .build();
    }

    /**
     * Method that receives coordinates for a location and request the temperature information of those coordinates to
     * the Open-Meteo API
     * @param latitude Double Latitude
     * @param longitude Double Longitude
     * @return TemperatureResponseDTO
     */
    public TemperatureResponseDTO getTemperatureByCoordinates(double latitude, double longitude, String cityName, String countryCode){

        LocalDate date = LocalDate.of(2025, 8, 1);

        OpenMeteoResponseDTO openMeteoResponseDTO = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("start_date", date)
                        .queryParam("end_date", date)
                        .queryParam("daily", "temperature_2m_max,temperature_2m_min")
                        .queryParam("timezone", "Europe/Berlin")
                        .build())
                .retrieve()
                .bodyToMono(OpenMeteoResponseDTO.class).block();

        TemperatureResponseDTO temperatureResponseDTO = new TemperatureResponseDTO(
                cityName,
                countryCode,
                date,
                openMeteoResponseDTO.getDailyOpenMeteoDTO().getTemperatureMax().get(0),
                openMeteoResponseDTO.getDailyOpenMeteoDTO().getTemperatureMin().get(0)
                );
        return temperatureResponseDTO;
    }

}
