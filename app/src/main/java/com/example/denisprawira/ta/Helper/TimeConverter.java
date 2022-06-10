package com.example.denisprawira.ta.Helper;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeConverter {

    public static String dateTimeToDayMonth(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertDateTimeToDate(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertDateMonthToDate(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String dateTimeToHour(String textTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertDateTimeToDay(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertMonth(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }


    public static String convertDateFromDateTime(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy ", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertTimeFromDateTime(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }



    public static String convertToYear(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertToDayOfWeek(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convert24HoursTo12Hours(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static int convertToIntegerYear(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format);
    }

    public static int convertToIntegerDay(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format);
    }

    public static int convertToIntegerMonth(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format)-1;
    }

    public static Integer convertToIntegerHour(String textTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format);
    }

    public static Integer convertToIntegerMinute(String textTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return Integer.parseInt(format);
    }



    public static String convertDateTimeToMonth(String textDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }

    public static String convertDateTimeToTime(String textTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = sdf.parse(textTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format = dateFormat.format(date);
        return format;
    }
}
