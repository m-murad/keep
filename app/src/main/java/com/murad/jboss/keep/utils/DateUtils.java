package com.murad.jboss.keep.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by murad on 06/01/18.
 */

public class DateUtils {

    public static String getDate(String dateString) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(dateString));

        SimpleDateFormat format = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);

        return format.format(calendar.getTime());
    }
}
