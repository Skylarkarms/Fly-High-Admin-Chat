package com.example.flyhighadminchat.date_package;


import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

public class DateUtils {

    private static final String TAG = "DateUtils";

    public DateUtils() {
    }

    public long getCurrentLocalLong() {
        return System.currentTimeMillis();
    }

    /**Instant --> Local Full String*/
    public String getLocalFullInstantString(Instant instant) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.LONG )
//                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        return formatter.format(instant);
    }

    public LocalDate fromString2Date (String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }

    public int getYearFromDateString (String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.getYear();
    }

    public String getMonthFromDateString (String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.parse(date, formatter);
        String month = localDate.getMonth().toString();
        return toLowerCase(month);
    }

    public String getDayOfWeekFromDateString (String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.parse(date, formatter);
        String dayOfWeek = localDate.getDayOfWeek().toString();
        return toLowerCase(dayOfWeek);
    }

    public int getDayOfMonthFromDateString (String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.getDayOfMonth();
    }

    public LocalDateTime fromLongTimeStamp2LocalDateTime (long timeStamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault());
    }

    /**Everything down here is FUCKING LOCAL NEVER FORGET*/
    public int getYearFromLongTimeStamp (long timeStamp) {
        LocalDateTime localDateTime = fromLongTimeStamp2LocalDateTime(timeStamp);
        return localDateTime.getYear();
    }

    public String getMonthFromLongTimeStamp (long timeStamp) {
        LocalDateTime localDateTime = fromLongTimeStamp2LocalDateTime(timeStamp);
        String month = localDateTime.getMonth().toString();
        return toLowerCase(month);
    }

    public String getDayOfWeekFromLongTimeStamp (long timeStamp) {
        LocalDateTime localDateTime = fromLongTimeStamp2LocalDateTime(timeStamp);
        String dayOfWeek = localDateTime.getDayOfWeek().toString();
        return toLowerCase(dayOfWeek);
    }

    public int getDayOfMonthFromLongTimeStamp (long timeStamp) {
        LocalDateTime localDateTime = fromLongTimeStamp2LocalDateTime(timeStamp);
        return localDateTime.getDayOfMonth();
    }

    public int getHoursFromLongTimeStamp (long timeStamp) {
        LocalDateTime localDateTime = fromLongTimeStamp2LocalDateTime(timeStamp);
        return localDateTime.getHour();
    }

    public int getMinutesFromLongTimeStamp (long timeStamp) {
        LocalDateTime localDateTime = fromLongTimeStamp2LocalDateTime(timeStamp);
        return localDateTime.getMinute();
    }

    public int getSecondsFromLongTimeStamp (long timeStamp) {
        LocalDateTime localDateTime = fromLongTimeStamp2LocalDateTime(timeStamp);
        return localDateTime.getSecond();
    }

    private String toLowerCase(String s) {
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }


}
