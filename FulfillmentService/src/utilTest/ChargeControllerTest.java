package utilTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import state.ChargeState;
import util.ChargeController;

public class ChargeControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(ChargeControllerTest.class);
	private ChargeController cc = null;
	
	@Before
	public void beforeTest() {
		LOG.debug("beforeTest()");
		cc = new ChargeController();
	}
	
	@Test
	public void isPossibleChargeByStateTest() {
		LOG.debug("isPossibleChargeByStateTest()");
		assertEquals(true, cc.isPossibleChargeByState(String.valueOf(ChargeState.미청구)));
		assertEquals(false, cc.isPossibleChargeByState(String.valueOf(ChargeState.청구요청)));
		assertEquals(false, cc.isPossibleChargeByState(String.valueOf(ChargeState.청구완료)));
	}
	
	@Test
	public void isPossibleChargeByDateTest() {
		LOG.debug("isPossibleChargeByDateTest()");
		assertEquals(true, cc.isPossibleChargeByDate("2019-04-23 17:30"));
		assertEquals(false, cc.isPossibleChargeByDate("2019-05-23 17:30"));
	}
}
