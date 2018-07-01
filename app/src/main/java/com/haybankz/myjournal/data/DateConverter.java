package com.haybankz.myjournal.data;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateConverter {



    public static Date toDate(Long timeInMillis){
        return  timeInMillis == null ? null : new Date(timeInMillis);
    }



    public static Long toTimeInMillis(Date date){
        return date == null ? null : date.getTime();
    }

    public static String getDateString(long dateInTimeMillis) {
        Date date = new Date(dateInTimeMillis);
//        String dateString = "";

        String format = "dd MMM, yyyy hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        // System.err.format("%40s %s\n", format, dateFormat.format(date));


        dateFormat.setTimeZone(TimeZone.getDefault());

        return String.format("%4s %s", "", dateFormat.format(date));
    }
}
