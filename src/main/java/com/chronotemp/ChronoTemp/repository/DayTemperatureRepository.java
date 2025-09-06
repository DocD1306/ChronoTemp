package com.chronotemp.ChronoTemp.repository;

import com.chronotemp.ChronoTemp.model.DayTemperature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * This interface is used to connect to the database and provides all the functionalities for the most common
 * operations or queries
 */
public interface DayTemperatureRepository extends JpaRepository<DayTemperature, Long> {

    // This like the name describes, retrieves a DayTemperature by the input passed.
    Optional<DayTemperature> findByCityAndCountryAndDate(String city, String country, LocalDate date);

    // This like the name describes, retrieves a DayTemperature by the input passed.
    Optional<DayTemperature> findByCityAndCountry(String city, String country);

    List<DayTemperature> findByCityAndCountryAndDateBetweenOrderByDateDesc(
            String city, String country, LocalDate startDate, LocalDate endDate);

    @Modifying
    @Transactional
    @Query(
            value = "INSERT IGNORE INTO daytemperature (city, country, latitude, longitude, date, max_temperature, min_temperature) " +
                    "VALUES (:city, :country, :latitude, :longitude, :date, :maxTemperature, :minTemperature)",
            nativeQuery = true
    )
    void insertIgnoreDuplicate(
            String city,
            String country,
            double latitude,
            double longitude,
            LocalDate date,
            Double maxTemperature,
            Double minTemperature
    );

}
