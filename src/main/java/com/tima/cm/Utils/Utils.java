package com.tima.cm.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private final static Logger LOGGER = LoggerFactory.getLogger(Utils.class);


    public static String getCurrentTime(){
        String dateTime;
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTime = sdf.format(d);
        return dateTime;
    }
}
