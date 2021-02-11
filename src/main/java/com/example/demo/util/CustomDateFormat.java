package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomDateFormat {
	
	
	public static String dateNowString() {
        Date date = Calendar.getInstance().getTime();  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
        return strDate;
	}
	
	public static Date stringToDate(String date) throws ParseException {
	    Date date1=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(date);  
	    return date1;
	}
	
	

}
