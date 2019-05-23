package utilTest;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DateHandler;

public class DateHandlerTest {
	private static final Logger LOG = LoggerFactory.getLogger(DateHandlerTest.class);
	private DateHandler dc = null;
	
	@Before
	public void beforeTest() {
		dc = new DateHandler();
		LOG.debug("beforeTest()");
	}
	
	@Test
	public void beforeTimeTest() {
		LOG.debug("beforeTimeTest()");
		assertEquals("2019-05-22 10:09", dc.beforeTime());
	}
	
	@Test
	public void beforeDayTest() {
		LOG.debug("beforeDayTest()");
		assertEquals("2019-05-22", dc.beforeDay());
	}
	
	@Test
	public void beforeMonthTest() {
		LOG.debug("beforeMonthTest()");
		assertEquals("2019-04", dc.beforeMonth());
	}
	
	@Test
	public void yesterdayTest() {
		LOG.debug("yesterdayTest()");
		assertEquals("2019-05-22", dc.yesterday());
	}
	
	@Test
	public void currentTimeTest() {
		LOG.debug("currentTimeTest()");
		assertEquals("2019-05-23 10:12", dc.currentTime());
	}
	
	@Test
	public void getTodayTest() {
		LOG.debug("getTodayTest()");
		assertEquals("2019-05-23", dc.getToday());
	}
	
	@Test
	public void getCurrentMonthTest() {
		LOG.debug("getCurrentMonthTest()");
		assertEquals("2019-05", dc.getCurrentMonth());
	}
	
	@Test 
	public void transStringToDateTest() {
		LOG.debug("transStringToDateTest()");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = "2019-05-23 10:20";
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			LOG.debug("transStringToDateTest() ERROR");
		}
		assertEquals(date, dc.transStringToDate(time));
	}
	
	@Test
	public void transStringToDateNotHoursTest() {
		LOG.debug("transStringToDateNotHoursTest()");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = "2019-05-23";
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			LOG.debug("transStringToDateNotHoursTest() ERROR");
		}
		assertEquals(date, dc.transStringToDateNotHours(time));
	}
	
	@Test
	public void transDateToStringTest() {
		LOG.debug("transDateToStringTest()");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = "2019-05-23 10:22";
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			LOG.debug("transDateToStringTest() ERROR");
		}
		assertEquals(time, dc.transDateToString(date));
	}
	
	@Test
	public void transDateToStringNotHoursTest() {
		LOG.debug("transDateToStringNotHoursTest()");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = "2019-05-23";
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			LOG.debug("transDateToStringNotHoursTest() ERROR");
		}
		assertEquals(time, dc.transDateToStringNotHours(date));
	}
	
	@Test
	public void getAmPmTest() {
		LOG.debug("getAmPmTest()");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String am = "2019-05-23 11:59";
		String pm = "2019-05-23 12:00";
		Date amDate = null;
		Date pmDate = null;
		try {
			amDate = sdf.parse(am);
			pmDate = sdf.parse(pm);
		} catch (ParseException e) {
			LOG.debug("getAmPmTest() ERROR");
		}
		assertEquals("am", dc.getAmPm(amDate));
		assertEquals("pm", dc.getAmPm(pmDate));
	}
	
	@Test
	public void getNumericTimeTest() {
		LOG.debug("getNumericTimeTest()");
		assertEquals(" 09:00:00", dc.getNumericTime("am"));
		assertEquals(" 18:00:00", dc.getNumericTime("pm"));
	}
	
	@Test
	public void getReleaseTimeTest() {
		LOG.debug("getReleaseTimeTest()");
		assertEquals("2019-05-23 10:00:00", dc.getReleaseTime("2019-05-22"));
	}
	
	@Test
	public void getChargeTimeTest() {
		LOG.debug("getChargeTimeTest()");
		assertEquals("2019-05-01", dc.getChargeTime("2019-04"));
	}
	
	@Test
	public void getPayTimeTest() {
		LOG.debug("getPayTimeTest()");
		assertEquals("2019-05-01", dc.getChargeTime("2019-04"));
	}
}
