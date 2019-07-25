package com.xsdzq.mm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getStandardDate(Date date) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		String standardString = sFormat.format(date);
		return standardString;
	}
	
	public static String getPreDay() {
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();
        
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		String standardString = sFormat.format(d);
		return standardString;
	}

}
