package com.chronotemp.ChronoTemp.service;

import com.chronotemp.ChronoTemp.dto.*;
import com.chronotemp.ChronoTemp.repository.DayTemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TemperatureService {

    private final String BASE_URL = "https://archive-api.open-meteo.com";

    private final WebClient webClient;
    private final DayTemperatureRepository dayTemperatureRepository;

    @Autowired
    public TemperatureService(DayTemperatureRepository dayTemperatureRepository){
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("User-Agent", "ChronoTemp/0.1 (diegofjda2@gmail.com)")
                .build();
        this.dayTemperatureRepository = dayTemperatureRepository;
    }


    public TemperatureResponseDTO getTemperatureByCoordinates(TemperatureRequestDTO temperatureRequestDTO){

        OpenMeteoResponseDTO openMeteoResponseDTO = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/archive")
                        .queryParam("latitude", temperatureRequestDTO.getCoordinatesDTO().getLatitude())
                        .queryParam("longitude", temperatureRequestDTO.getCoordinatesDTO().getLongitude())
                        .queryParam("start_date", temperatureRequestDTO.getStartDate())
                        .queryParam("end_date", temperatureRequestDTO.getEndDate())
                        .queryParam("daily", "temperature_2m_max,temperature_2m_min")
                        .queryParam("timezone", "Europe/Berlin")
                        .build())
                .retrieve()
                .bodyToMono(OpenMeteoResponseDTO.class).block();

        return mapToTemperatureResponseDTO(openMeteoResponseDTO, temperatureRequestDTO);
    }

    public TemperatureResponseDTO getTemperaturesByCityAndCountryAndRange(String city, String countryCode, DateRangeDTO dateRangeDTO){

        // Calculate number of day between the startDate and the endDate
        long totalDays = ChronoUnit.DAYS.between(dateRangeDTO.getStartDate(), dateRangeDTO.getEndDate()) + 1;

        // Request the database
        List<DayTemperatureDTO> results = dayTemperatureRepository.findByCityAndCountryAndDateBetweenOrderByDateDesc(
                        city,
                        countryCode,
                        dateRangeDTO.getStartDate(),
                        dateRangeDTO.getEndDate()
                )
                .stream()
                .map(entity -> {
                    DayTemperatureDTO dto = new DayTemperatureDTO();
                    dto.setCity(entity.getCity());
                    dto.setCountry(entity.getCountry());
                    dto.setDate(entity.getDate());
                    dto.setMaxTemperature(entity.getMaxTemperature());
                    dto.setMinTemperature(entity.getMinTemperature());
                    return dto;
                })
                .collect(Collectors.toList());

        if (results.size() == totalDays) {
            return new TemperatureResponseDTO(city, countryCode, results);
        } else {
            return null;
        }

    }



    /**
     * This method transforms the OpenMeteoResponseDTO into a TemperatureResponseDTO that contains many DayTemperatureDTO
     * @param openMeteoResponseDTO OpenMeteoResponseDTO object
     * @param temperatureRequestDTO TemperatureResponseDTO object with the city and countryCode
     * @return TemperatureResponseDTO with all the temperatures correctly set
     */
    private TemperatureResponseDTO mapToTemperatureResponseDTO(OpenMeteoResponseDTO openMeteoResponseDTO,
                                                               TemperatureRequestDTO temperatureRequestDTO)
    {
        DailyOpenMeteoDTO dailyOpenMeteoDTO = openMeteoResponseDTO.getDailyOpenMeteoDTO();

        List<DayTemperatureDTO> temperatures =
                IntStream.range(0, dailyOpenMeteoDTO.getDate().size())
                        .mapToObj(i -> new DayTemperatureDTO(
                                temperatureRequestDTO.getCity(),
                                temperatureRequestDTO.getCountry(),
                                LocalDate.parse(dailyOpenMeteoDTO.getDate().get(i)),
                                dailyOpenMeteoDTO.getTemperatureMax().get(i),
                                dailyOpenMeteoDTO.getTemperatureMin().get(i)
                        ))
                        .toList();

        saveTemperaturesToDatabase(temperatures, temperatureRequestDTO);

        return new TemperatureResponseDTO(
                temperatureRequestDTO.getCity(),
                temperatureRequestDTO.getCountry(),
                temperatures
        );
    }

    private void saveTemperaturesToDatabase(List<DayTemperatureDTO> dayTemperatureDTOS, TemperatureRequestDTO temperatureRequestDTO){
        for(DayTemperatureDTO dto : dayTemperatureDTOS){
            dayTemperatureRepository.insertIgnoreDuplicate(
                    dto.getCity(),
                    dto.getCountry(),
                    temperatureRequestDTO.getCoordinatesDTO().getLatitude(),
                    temperatureRequestDTO.getCoordinatesDTO().getLongitude(),
                    dto.getDate(),
                    dto.getMaxTemperature(),
                    dto.getMinTemperature()
            );
        }
    }

}
