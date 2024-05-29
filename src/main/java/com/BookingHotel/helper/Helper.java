package com.BookingHotel.helper;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Helper {

    public static String formatRupiah(Double value){
        Locale indonesia = new Locale("id", "ID");
        String indoFormat = NumberFormat.getCurrencyInstance(indonesia).format(value);
        return indoFormat;
    }

    public static String indoDateFormat(LocalDate date) {
        Locale indo = new Locale("id", "ID");
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", indo));

        return formattedDate;
    }

    public static Integer countDay(LocalDate checkIn, LocalDate checkOut){

        Integer totalDay = Period.between(checkIn, checkOut).getDays();;

        if (totalDay == 0){
            return totalDay + 1;
        }else {
            return totalDay;
        }
    }
}
