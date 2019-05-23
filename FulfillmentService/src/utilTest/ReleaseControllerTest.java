package utilTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import state.InvoiceState;
import state.ProductState;
import util.ReleaseController;

public class ReleaseControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(ReleaseControllerTest.class);
	private ReleaseController rc = null;
	
	@Before
	public void beforeTest() {
		LOG.debug("beforeTest()");
		rc = new ReleaseController();
	}
	
	@Test
	public void isPossibleReleaseByProductStateTest() {
		LOG.debug("isPossibleReleaseByProductStateTest()");
		assertEquals(true, rc.isPossibleReleaseByProductState(String.valueOf(ProductState.P)));
		assertEquals(false, rc.isPossibleReleaseByProductState(String.valueOf(ProductState.재고부족)));
		assertEquals(true, rc.isPossibleReleaseByProductState(String.valueOf(ProductState.재고부족예상)));
	}
	
	@Test
	public void isPossibleReleaseByDateTest() {
		LOG.debug("isPossibleReleaseByDateTest()");
		assertEquals(true, rc.isPossibleReleaseByDate("2019-05-23 08:00"));
		assertEquals(false, rc.isPossibleReleaseByDate("2019-05-23 19:00"));
	}
	
	@Test
	public void isPossibleReleaseByInvoiceStateTest() {
		LOG.debug("isPossibleReleaseByInvoiceStateTest()");
		assertEquals(true, rc.isPossibleReleaseByInvoiceState(String.valueOf(InvoiceState.출고대기)));
		assertEquals(true, rc.isPossibleReleaseByInvoiceState(String.valueOf(InvoiceState.출고보류)));
		assertEquals(false, rc.isPossibleReleaseByInvoiceState(String.valueOf(InvoiceState.출고완료)));
	}
}
