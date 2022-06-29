package com.doubleclick.marktinhome.Views.storyview.utils;

import java.util.Date;

public class Utils {

    public static String getDurationBetweenDates(long d1, long d2) {

        long diff = d1 - d2;
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        StringBuilder formattedDiff = new StringBuilder();
        if (days != 0) {
            return formattedDiff.append(Math.abs(days)).append("day").toString();
        }
        if (hours != 0) {
            return formattedDiff.append(Math.abs(hours)).append("hours").toString();
        }
        if (minutes != 0) {
            return formattedDiff.append(Math.abs(minutes)).append("minutes").toString();
        }
        if (seconds != 0) {
            return formattedDiff.append(Math.abs(seconds)).append("second").toString();
        }

        return "";
    }

}
