package com.smita.engineeraitest.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    public static String getFormattedDate(String actualDate){
        String convertedDate = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.'SSS'Z'", Locale.getDefault());
        DateFormat convertTo = new SimpleDateFormat("dd-MMM-yyyy hh:mm a",Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(actualDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        convertedDate = convertTo.format(date);
        return convertedDate;
    }
}
