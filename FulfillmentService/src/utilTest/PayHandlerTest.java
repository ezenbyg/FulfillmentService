package utilTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.PayHandler;

public class PayHandlerTest {
	private static final Logger LOG = LoggerFactory.getLogger(PayHandlerTest.class);
	private PayHandler pc = null;
	
	@Before
	public void beforeTest() {
		LOG.debug("beforeTest()");
		pc = new PayHandler();
	}
	
	@Test
	public void isPossiblePayByDateTest() {
		LOG.debug("isPossiblePayByDateTest()");
		assertEquals(true, pc.isPossiblePayByDate("2019-04-20 18:00"));
		assertEquals(false, pc.isPossiblePayByDate("2019-05-01 18:00"));
	}
}
