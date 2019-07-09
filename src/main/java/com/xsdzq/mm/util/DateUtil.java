package com.xsdzq.mm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getStandardDate(Date date) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		String standardString = sFormat.format(date);
		return standardString;
	}

}
