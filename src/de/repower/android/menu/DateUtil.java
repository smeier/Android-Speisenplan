package de.repower.android.menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateFormat;

public class DateUtil {

    static final int ONE_HOUR = 3600 * 1000;
    static final int ONE_DAY = 24 * ONE_HOUR;
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String BEAUTIFUL_DATE_PATTERN = "EEE dd.MM.yyyy";

    public static String formatDateForDB(Date date) {
        return DateFormat.format(DATE_PATTERN, date).toString();
    }

    public static String beautifyDate(Date date) {
        return DateFormat.format(BEAUTIFUL_DATE_PATTERN, date).toString();
    }

    public static Date parseDBDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

    public static Date nextDate(Date date) {
        return new Date(date.getTime() + ONE_DAY);
    }

    public static Date previousDate(Date date) {
        return new Date(date.getTime() - ONE_DAY);
    }

    public static Date today() {
        return normalizeDate(new Date());
    }

    private static Date normalizeDate(Date date) {
        return parseDBDate(formatDateForDB(date));
    }

    public static boolean olderThan(Date date, long maxAgeInCache) {
        return System.currentTimeMillis() - date.getTime() > maxAgeInCache;
    }

}
