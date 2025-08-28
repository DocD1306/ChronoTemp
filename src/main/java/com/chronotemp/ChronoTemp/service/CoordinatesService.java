package com.chronotemp.ChronoTemp.service;

import com.chronotemp.ChronoTemp.dto.CoordinatesDTO;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * This service class is in charge of creating, sending and receiving the requests to the Nominatim API
 */
@Service
@Data
public class CoordinatesService {

    private static final String BASE_URL = "https://nominatim.openstreetmap.org";
    private static final String CITY_REQUEST_PART = "q=%s&format=json&limit1";

    private final WebClient webClient;

    public CoordinatesService(){
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("User-Agent", "ChronoTemp/0.1 (diegofjda2@gmail.com)")
                .build();
    }

    public Mono<CoordinatesDTO> getCoordinates(String city, String countryCode){
        String query = city + ",\s" + countryCode;

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", query)
                        .queryParam("format", "json")
                        .queryParam("limit", 1)
                        .build())
                .retrieve()
                .bodyToMono(CoordinatesDTO[].class)
                .map(results -> {
                    if(results.length > 0){
                        return results[0];
                    } else {
                        return null;
                    }
                });
    }

}
