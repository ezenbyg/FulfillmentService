package utilTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import state.OrderState;
import state.ProductState;
import util.OrderHandler;

public class OrderHandlerTest {
	private static final Logger LOG = LoggerFactory.getLogger(OrderHandlerTest.class);
	private OrderHandler oc = null;
	
	@Before
	public void beforeTest() {
		LOG.debug("beforeTest()");
		oc = new OrderHandler();
	}
	
	@Test
	public void isPossibleOrderByProductStateTest() {
		LOG.debug("isPossibleOrderByProductStateTest()");
		assertEquals(false, oc.isPossibleOrderByProductState(String.valueOf(ProductState.P)));
		assertEquals(true, oc.isPossibleOrderByProductState(String.valueOf(ProductState.재고부족)));
		assertEquals(true, oc.isPossibleOrderByProductState(String.valueOf(ProductState.재고부족예상)));
	}
	
	@Test
	public void isPossibleOrderByOrderStateTest() {
		LOG.debug("isPossibleOrderByOrderStateTest()");
		assertEquals(true, oc.isPossibleOrderByOrderState(String.valueOf(OrderState.구매요청)));
		assertEquals(false, oc.isPossibleOrderByOrderState(String.valueOf(OrderState.공급실행)));
		assertEquals(false, oc.isPossibleOrderByOrderState(String.valueOf(OrderState.구매확인요청)));
		assertEquals(false, oc.isPossibleOrderByOrderState(String.valueOf(OrderState.구매확정)));
	}
	
	@Test
	public void isPossibleReleaseByDateTest() {
		LOG.debug("isPossibleReleaseByDateTest()");
		assertEquals(true, oc.isPossibleReleaseByDate("2019-05-22 17:30"));
		assertEquals(false, oc.isPossibleReleaseByDate("2019-05-23 10:00"));
	}
}
