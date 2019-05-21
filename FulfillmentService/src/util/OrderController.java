package util;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import order.OrderDAO;
import order.OrderDTO;
import state.OrderState;
import state.ProductState;

public class OrderController {
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	DateController dc = new DateController();
	OrderDAO oDao = new OrderDAO();
	OrderDTO order = new OrderDTO();
	ArrayList<OrderDTO> oList = new ArrayList<OrderDTO>();
	
	// 제품 상태에 따른 발주 가능 여부 판단 메소드
	public boolean isPossibleOrderByState(String pState) {
		ProductState state = ProductState.valueOf(pState);
		LOG.debug(String.valueOf(state));
		switch(state) {
		case P : 
			return false;
		case 재고부족 : 
			return true;
		case 재고부족예상 :
			return true;
		}
		return false;
	}
	
	// 납품 가능시간 여부 판단 메소드
	public boolean isPossibleReleaseByDate(String date) {
		String strDate = dc.getReleaseTime(date.substring(0, 10));
		if(date.compareTo(strDate) >= 0) return false;
		else return true;
	}
	
	// 발주 
	public void processOrder(int pSupplierId, int pId, int oQuantity, int oTotalPrice, String pState) {
		if(isPossibleOrderByState(pState) == true) {
			order = new OrderDTO(pSupplierId, pId, oQuantity, oTotalPrice, dc.currentTime());
			LOG.debug(order.toString());
			oDao.addOrderProducts(order);
		}
	}
	
	// 납품
	public void processRelease(int oAdminId, String date) {
		oList = oDao.getAllOrderLists(oAdminId, date);
		for(OrderDTO order : oList) {
			if(isPossibleReleaseByDate(order.getoDate())) {
				oDao.updateOrderState(String.valueOf(OrderState.공급실행), order.getoId(), dc.currentTime());
			}
		}
	}
}
