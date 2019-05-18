package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateController {
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
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	return cTime.format(dtf);
	}
	
	// 현재 날짜
	public String getToday() {
		LocalDateTime today = LocalDateTime.now();	
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	return today.format(dtf);
	}
}
