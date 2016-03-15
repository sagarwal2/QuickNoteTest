package com.cognizant.quicknote.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Utility {

    /**
     * Converts a datetime string to Date
     * @param dateTimeStr Datetime string to parse
     * @return Date object
     * @throws ParseException
     */
    public static Date getDateFromString(String dateTimeStr) throws ParseException {
        return DateFormat.getDateTimeInstance().parse(dateTimeStr);
    }
}
