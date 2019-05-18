package util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateController {
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date cpDate = null;
		try {
			cpDate = sdf.parse(date);
		} catch(Exception e) {}
		return cpDate;
	}
	
	// 날짜를 문자열로 변환
	public String transDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = sdf.format(date);
		return time;
	}
	
	// 시간 비교 
	public boolean compareDate(Date date) {
		if(date)
		return true;
	}
	
}
