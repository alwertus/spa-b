package com.tretsoft.spa.service.utility;

import java.util.Calendar;
import java.util.TimeZone;

public class DateService {
    public static Calendar getCalendarByMillis(Long millis) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(millis);
        return calendar;
    }
}
