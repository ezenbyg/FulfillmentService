package utilTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.BankAccount;

public class BankAccountTest {
	private static final Logger LOG = LoggerFactory.getLogger(BankAccountTest.class);
	private BankAccount ba = null;
	
	@Before
	public void beforeTest() {
		ba = new BankAccount();
		LOG.debug("beforeTest()");
	}
	
	@Test
	public void getAccountTest() {
		LOG.debug("getAccountTest()");
		List<String> result = new ArrayList<String>();
		result.add("352-1142-3666-63"); assertEquals(result.get(0), ba.getAccount("ezen"));
		result.add("110-1958-1241-68"); assertEquals(result.get(1), ba.getAccount("JH쇼핑몰"));
		result.add("220-2258-5461-90"); assertEquals(result.get(2), ba.getAccount("SW쇼핑몰"));
		result.add("151-7654-1289-81"); assertEquals(result.get(3), ba.getAccount("GJ쇼핑몰"));
		result.add("430-1158-3498-68"); assertEquals(result.get(4), ba.getAccount("무신사"));
		result.add("111-1113-1234-13"); assertEquals(result.get(5), ba.getAccount("와구와구"));
		result.add("112-1234-1233-10"); assertEquals(result.get(6), ba.getAccount("하이마트"));
		result.add("113-1312-1657-65"); assertEquals(result.get(7), ba.getAccount("언더아머"));
		result.add("110-1951-4651-43"); assertEquals(result.get(8), ba.getAccount("이케아"));
		result.add("110-1952-6761-68"); assertEquals(result.get(9), ba.getAccount("경기물류"));
		result.add("110-1953-1433-98"); assertEquals(result.get(10), ba.getAccount("중부물류"));
		result.add("110-1954-1109-37"); assertEquals(result.get(11), ba.getAccount("영남물류"));
		result.add("110-1955-9839-83"); assertEquals(result.get(12), ba.getAccount("서부물류"));
	}
}
