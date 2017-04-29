package com.igordubrovin.tfsmsg.utils;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Игорь on 29.04.2017.
 */

public class DateHelper {

    private Calendar calendar;

    public DateHelper(){
        calendar = Calendar.getInstance();
    }

    public String getCurrentDate(){
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(calendar.getTime());
    }

    public String getCurrentTime(){
        DateFormat dateFormat = DateFormat.getTimeInstance();
        return dateFormat.format(calendar.getTime());
    }
}