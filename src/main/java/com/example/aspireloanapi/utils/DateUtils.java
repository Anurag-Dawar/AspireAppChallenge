package com.example.aspireloanapi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String convertTimestampToDate(long timestamp) {
        // Convert the timestamp to a LocalDate
        LocalDate date = LocalDate.ofEpochDay(timestamp / (24 * 60 * 60 * 1000));

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Format the date using the formatter
        return date.format(formatter);
    }

    public static Long addWeekToTimeStamp(long timestamp) {

        //count millis in one week
        long millisInOneWeek = (7 * 24 * 60 * 60 * 1000 );

        //return after adding one week millis to input timestamp
        return timestamp + millisInOneWeek;
    }

}
