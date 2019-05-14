package control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.AdminDAO;
import admin.AdminDTO;
import order.OrderDAO;
import order.OrderDTO;
import storage.StorageDAO;
import storage.StorageDTO;

/**
 * Servlet implementation class OrderProc
 */
@WebServlet("/control/orderServlet")
public class OrderProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(OrderProc.class);   

    public OrderProc() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		int curPage = 1;
		
		Date releaseDate = null;
		Date time = null;
		SimpleDateFormat format = null;
		OrderDAO oDao = null;
		OrderDTO order = null;
		AdminDAO aDao = null;
		AdminDTO admins = null;
		StorageDAO pDao = null;
		StorageDTO product = null;
		
		int oId = 0;
		int oAdminId = 0;
		int oProductId = 0;
		int oQuantity = 0;
		int oPrice = 0;
		int oTotalPrice = 0;
		int count = 0;
		int pageNo = 0;
		
		String year = null;
		String month = null;
		String date = null;
		Date conDateR1 = null;
		Date conDateR2 = null;
		
		String oDate = null;
		String oState = null;
		String page = null;
		String message = null;
		String action = request.getParameter("action");
		List<String> pageList = new ArrayList<String>();
		List<OrderDTO> oList = null;
		
		switch(action) {
		case "supplierOrderList" : // 구매처 발주 내역 조회
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			oDao = new OrderDAO();
			count = oDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentSupplierOrderPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/orderServlet?action=supplierOrderList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			// 로그인한 구매처 관리자 세션ID로 구분짓기
			oAdminId = Integer.parseInt((String) session.getAttribute("sessionAdminId"));
			oList = oDao.selectJoinAllbyId(curPage, oAdminId);
			request.setAttribute("orderList", oList);
			request.setAttribute("orderPageList", pageList);
			rd = request.getRequestDispatcher("/control/orderServlet?action=supplierOrderList&page=1");
	        rd.forward(request, response);
			
			break;
			
		case "storageOrderList" : // 창고관리자 발주 내역 조회
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			oDao = new OrderDAO();
			count = oDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentStorageOrderPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/orderServlet?action=storageOrderList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			// 드롭다운으로 선택한 구매처 아이디로 구분 짓기
			// oAdminId 파라미터 값이 Zero(0)일 경우 전체 페이지 출력
			oAdminId = Integer.parseInt(request.getParameter("oAdminId"));
			if(oAdminId == 0) { // 전체 출력
				oList = oDao.selectJoinAll(curPage);
				request.setAttribute("orderList", oList);
				request.setAttribute("orderPageList", pageList);
				rd = request.getRequestDispatcher("/control/orderServlet?action=supplierOrderList&page=1");
		        rd.forward(request, response);
			} else { // 각 구매처에 대한 발주 내역 출력
				oList = oDao.selectJoinAllbyId(curPage, oAdminId);
				request.setAttribute("orderList", oList);
				request.setAttribute("orderPageList", pageList);
				rd = request.getRequestDispatcher("/control/orderServlet?action=supplierOrderList&page=1");
		        rd.forward(request, response);
			}
			break;
			
		case "order" : // 발주(창고 -> 구매처), 발주 상태 : 구매요청, 공급실행, 구매확인요청, 구매확정
			oProductId = Integer.parseInt(request.getParameter("oProductId"));
			oQuantity = Integer.parseInt(request.getParameter("oQuantity"));
			oPrice = Integer.parseInt(request.getParameter("oPrice"));
			oTotalPrice = Integer.parseInt(request.getParameter("oTotalPrice"));
			
			aDao = new AdminDAO();
			admins = aDao.getOneAdminByName(request.getParameter("oName"));
			pDao = new StorageDAO();
			product = pDao.getOneProductById(oProductId);
		
			// 제품 상태가 P이면 발주 불가능, (재고부족, 재고부족예상 인 경우에 가능)
			if(product.getpState().equals("P")) {
				message = "재고가 많습니다.";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/storage/storageStocktaking.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
			}
			
			format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			time = new Date();
			String orderTime = format.format(time);
			
			order = new OrderDTO(admins.getaId(), oProductId, oQuantity, oPrice, oTotalPrice, orderTime);
			oDao = new OrderDAO();
			oDao.addOrderProducts(order);
			
			order = oDao.getOneOrderList(admins.getaId(), oProductId);
			message = "발주번호는 " + order.getoId() + " 입니다.";
			request.setAttribute("message", message);
			
			LOG.debug("/view/storage/storageStocktaking.jsp");
			rd = request.getRequestDispatcher("/view/storage/storageStocktaking.jsp");
	        rd.forward(request, response);
			break;
			
		case "startSupply" : // 공급실행 버튼은 구매요청인 상태에서만 클릭 가능
			oId = Integer.parseInt(request.getParameter("oId"));
			oState = request.getParameter("oState");
		
			if(!oState.equals("구매요청")) {
				message = "잘못된 접근 입니다!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/supplier/supplierOrderHistory.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
			}
			
			oDao = new OrderDAO();
			oDao.updateOrderState("공급실행", oId);
			rd = request.getRequestDispatcher("/control/orderServlet?action=supplierOrderList&page=1");
	        rd.forward(request, response);
			break;
			
		case "confirmRequest" : // 구매확인요청 버튼은 공급실행 상태에서만 클릭 가능
			// 구매확인요청은 (납품)이므로 발주 다음날 오전 10시에 가능
			oId = Integer.parseInt(request.getParameter("oId"));
			oState = request.getParameter("oState");
			oDate = request.getParameter("oDate");
			
			year = oDate.substring(0, 4);
			month = oDate.substring(5, 7);
			date = oDate.substring(8, 10);
			int addDate = Integer.parseInt(date) + 1;
			
			format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			time = new Date();
			String releaseTime = format.format(time);
			
			try {
				releaseDate = format.parse(releaseTime); // 납품할 현재 시간
				conDateR1 = format.parse(year+"-"+month+"-"+String.valueOf(addDate)+" "+"10"+":"+"00"); 
				conDateR2 = format.parse(year+"-"+month+"-"+String.valueOf(addDate)+" "+"11"+":"+"00"); 
			} catch (ParseException e) {
				LOG.trace(e.getMessage());
				e.printStackTrace();
			}
			
			// 다음날 10시 ~ 11시 사이에 납품 가능
			if((releaseDate.getTime() < conDateR1.getTime()) || (releaseDate.getTime() >= conDateR2.getTime())) {
				message = "납품 시간이 아직 안됬습니다!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/supplier/supplierOrderHistory.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
			}
			
			if(!oState.equals("공급실행")) {
				message = "잘못된 접근 입니다!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/supplier/supplierOrderHistory.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
			} 
			
			oDao = new OrderDAO();
			oDao.updateOrderState("구매확인요청", oId);
			rd = request.getRequestDispatcher("/control/orderServlet?action=supplierOrderList&page=1");
	        rd.forward(request, response);
			break;
			
		case "purchaseConfirm" : // 구매확정 버튼은 구매확인요청 상태에서만 클릭 가능
			oId = Integer.parseInt(request.getParameter("oId"));
			oProductId = Integer.parseInt(request.getParameter("oProductId"));
			oState = request.getParameter("oState");
			oQuantity = Integer.parseInt(request.getParameter("oQuantity"));
			
			if(!oState.equals("구매확인요청")) {
				message = "잘못된 접근 입니다!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/storage/storageOrderHistory.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
			}
			pDao = new StorageDAO();
			product = pDao.getOneProductById(oProductId);
			
			oDao = new OrderDAO();
			oDao.updateOrderState("구매확정", oId);
			
			// 구매확정 후에 창고 재고수량 & 재고상태 변경
			pDao = new StorageDAO();
			pDao.updateStorage(product.getpQuantity()+oQuantity, "P", oProductId);
			rd = request.getRequestDispatcher("/control/orderServlet?action=storageOrderList&page=1");
	        rd.forward(request, response);
			break;
			
		default : break;
		}
	}
}
