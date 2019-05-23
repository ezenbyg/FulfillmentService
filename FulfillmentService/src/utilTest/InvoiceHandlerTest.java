package utilTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.InvoiceHandler;

public class InvoiceHandlerTest {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceHandlerTest.class);
	private InvoiceHandler ic = null;
	
	@Before
	public void beforeTest() {
		LOG.debug("beforeTest()");
		ic = new InvoiceHandler();
	}
	
	@Test
	public void selectLogisTest() {
		LOG.debug("selectLogisTest()");
		assertEquals(40001, ic.selectLogis("서울"));
		assertEquals(40002, ic.selectLogis("대전"));
	}
}
