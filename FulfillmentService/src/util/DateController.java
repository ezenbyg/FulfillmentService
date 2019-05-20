package util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateController {
	private static final Logger LOG = LoggerFactory.getLogger(DateController.class);
	// 어제 시간
	public String beforeTime() {
		LocalDateTime yTime = LocalDateTime.now();
		yTime = yTime.minusDays(1);
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");	
    	return yTime.format(dtf);
	}
	
	// 어제 날짜
	public String yesterday() {
		LocalDateTime yesterday = LocalDateTime.now();
		yesterday = yesterday.minusDays(1);
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
    	return yesterday.format(dtf);
	}
	
	// 현재 시간
	public String currentTime() {
		LocalDateTime cTime = LocalDateTime.now();	
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	return cTime.format(dtf);
	}
	
	// 현재 날짜
	public String getToday() {
		LocalDateTime today = LocalDateTime.now();	
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	return today.format(dtf);
	}
	
	// 문자열을 날짜로 변환
	public Date transStringToDate(String date) {
		LOG.debug(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date cpDate = null;
		try {
			cpDate = sdf.parse(date);
			LOG.debug("cpDate : " + String.valueOf(cpDate));
		} catch(Exception e) {}
		return cpDate;
	}
	
	// 날짜를 문자열로 변환
	public String transDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(date);
		return time;
	}
	
	// 오전 오후 구하기 
	public String getAmPm(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		LOG.debug("getAmPm() : " + String.valueOf(cal.getTime()));
		if (cal.get(Calendar.AM_PM) == Calendar.AM)
			return "am";
		else if(cal.get(Calendar.AM_PM) == Calendar.PM)
			return "pm";
		return null;
	}
	
	// 오전 오후 시간
	public String getNumericTime(String amPm) {
		String timeStr = null;
		if (amPm.equals("am"))
			timeStr = " 09:00:00";
		if (amPm.equals("pm"))
			timeStr = " 18:00:00";
		return timeStr;
	}
}