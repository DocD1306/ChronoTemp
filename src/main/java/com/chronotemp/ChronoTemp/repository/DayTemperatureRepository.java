package com.chronotemp.ChronoTemp.repository;

import com.chronotemp.ChronoTemp.model.DayTemperature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * This interface is used to connect to the database and provides all the functionalities for the most common
 * operations or queries
 */
public interface DayTemperatureRepository extends JpaRepository<DayTemperature, Long> {

    // This like the name describes, retrieves a DayTemperature for the input passed.
    Optional<DayTemperature> findByCityAndCountryAndDate(String city, String country, LocalDate date);

}
