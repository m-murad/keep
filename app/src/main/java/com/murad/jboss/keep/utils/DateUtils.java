package com.murad.jboss.keep.utils;

import java.util.Calendar;

/**
 * Created by murad on 06/01/18.
 */

public class DateUtils {

    public static String getDate(String dateString) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(dateString));
        return String.valueOf(calendar.getTime());
    }
}
