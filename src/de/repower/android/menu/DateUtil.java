package de.repower.android.menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateFormat;

public class DateUtil {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static String formatDate(Date date) {
        return DateFormat.format(DATE_PATTERN, date).toString();
    }

    public static Date parseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

}
