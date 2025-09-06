package com.chronotemp.ChronoTemp.service;

import com.chronotemp.ChronoTemp.dto.DateRangeDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DateRangeService {

    public DateRangeDTO buildDateRange(Integer startYear, Integer endYear){

        // Get the current year if the parameters are null
        if(startYear == null){
            startYear = java.time.Year.now().getValue();
        }
        if(endYear == null || endYear > java.time.Year.now().getValue()){
            endYear = java.time.Year.now().getValue();
        }

        // The startDate is always on the first of January
        LocalDate startDate = LocalDate.of(startYear, 1, 1);

        // We leave the endDate empty to put the correct one based on the criteria
        LocalDate endDate;

        // We use the current date to decide the endDate
        LocalDate currentDate = LocalDate.now();

        // If the endYear is this year or if it's the first of January
        if(currentDate.equals(LocalDate.of(endYear, 1, 1)) ||
                currentDate.equals(LocalDate.of(endYear, 1, 2)) ){

            endDate = LocalDate.of(endYear, 1, 1);

        } else if (endYear == java.time.Year.now().getValue()){
            endDate = currentDate.minusDays(2); // Take the endDate from two days ago
        } else {
            endDate = LocalDate.of(endYear, 12, 31); // Take the last day of the year
        }

        return new DateRangeDTO(startDate, endDate);

    }


}
