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
		Date orderDate = null;
		long diff = 0;
		long sec = 0;
		
		OrderDAO oDao = null;
		OrderDTO order = null;
		int oId = 0;
		int oAdminId = 0;
		int oProductId = 0;
		int oQuantity = 0;
		int oPrice = 0;
		int oTotalPrice = 0;
		String oDate = null;
		List<OrderDTO> oList = null;
		String url = null;
		String message = null;
		String action = request.getParameter("action");
		List<String> pageList = new ArrayList<String>();
		
		switch(action) {
		case "list" : // 발주 내역 조회
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			oDao = new OrderDAO();
			int count = oDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentOrderPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			String page = null;
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=orderServlet?action=list&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			oAdminId = Integer.parseInt((String) session.getAttribute("sessionAdminId"));
			oList = oDao.selectJoinAll(curPage, oAdminId);
			request.setAttribute("orderList", oList);
			request.setAttribute("orderPageList", pageList);
			rd = request.getRequestDispatcher("XXX.jsp");
	        rd.forward(request, response);
	        LOG.trace("");
			break;
			
		case "order" : // 발주(창고 -> 구매처)
			oAdminId = Integer.parseInt(request.getParameter("oAdminId"));
			oProductId = Integer.parseInt(request.getParameter("oProductId"));
			oQuantity = Integer.parseInt(request.getParameter("oQuantity"));
			oPrice = Integer.parseInt(request.getParameter("oPrice"));
			oTotalPrice = Integer.parseInt(request.getParameter("oTotalPrice"));
			
			// 발주 가능 : (재고수량 <= 10) || (배송물품수량 > 재고수량)
			if(oQuantity > 10) {
				message = "재고수량이 10개를 초과합니다!!";
				request.setAttribute("message", message);
				url = "XXX.jsp";
				request.setAttribute("url", url);
				rd = request.getRequestDispatcher("alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			String orderTime = format.format(time);
			
			order = new OrderDTO(oAdminId, oProductId, oQuantity, oPrice, oTotalPrice, orderTime);
			oDao = new OrderDAO();
			oDao.addOrderProducts(order);
			
			order = oDao.getOneOrderList(oAdminId, oProductId);
			message = "발주번호는 " + order.getoId() + " 입니다.";
			request.setAttribute("message", message);
			url = "XXX.jsp";
			request.setAttribute("url", url);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			rd.forward(request, response);
			break;
			
		case "release" : // 구매처에서 납품
			oId = Integer.parseInt(request.getParameter("oId"));
			oProductId = Integer.parseInt(request.getParameter("oProductId"));
			oQuantity = Integer.parseInt(request.getParameter("oQuantity"));
			oDate = request.getParameter("oDate");
			
			SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date time1 = new Date();
			String releaseTime = format1.format(time1);
			
			try {
				releaseDate = format1.parse(releaseTime);
				orderDate = format1.parse(oDate);
				diff = releaseDate.getTime() - orderDate.getTime();
				sec = diff / 1000;
			} catch (ParseException e) {
				LOG.trace(e.getMessage());
				e.printStackTrace();
			}
			
			// 납품은 발주 다음날 오전 10시에 처리 가능 (테스트를 위해 발주 하고 2분지난 시점에서 납품 가능)
			if(sec < 120) {
				message = "발주 가능한 시간이 아닙니다!!";
				request.setAttribute("message", message);
				url = "XXX.jsp";
				request.setAttribute("url", url);
				rd = request.getRequestDispatcher("alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			// 상품 재고 Update
			StorageDAO pDao = new StorageDAO();
			StorageDTO product = new StorageDTO();
			
			product = pDao.getOneProductList(oProductId);
			pDao.updateStorage(oQuantity+product.getpQuantity(), oProductId);
			
			// 상태 : 발주 -> 발주완료
			oDao = new OrderDAO();
			oDao.updateOrderState("발주완료", oId);
			response.sendRedirect("orderServlet?action=list&page=1");
			break;
			
		default : break;
		}
	}
}
