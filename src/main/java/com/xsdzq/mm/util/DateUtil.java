package com.xsdzq.mm.util;

import java.text.ParseException;
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
//获得当前时间的前一天 返回日期类型
	public static Date getPreDayAs() {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.DATE, -1);
	        Date start = c.getTime();
	        String qyt= format.format(start);//前一天
	        Date dq = null;
	        try {
				 dq =  format.parse(qyt);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return dq;
	}
}
