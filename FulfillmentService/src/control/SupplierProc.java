package control;

import java.io.IOException;
import java.util.ArrayList;
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
import release.ReleaseDAO;
import release.ReleaseDTO;
import state.OrderState;
import util.DateHandler;
import util.OrderHandler;

@WebServlet("/control/supplierServlet")
public class SupplierProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SupplierProc.class);
       
    public SupplierProc() {
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
		
		String message = null;
		String date = null;
		String page = null;
		String[] dateFormat = null;
		String oState = null;
		String oDate = null;
		
		OrderDAO oDao = null;
		OrderDTO order = null;
		
		int oId = 0;
		int count = 0;
		int pageNo = 0;
		int curPage = 1;
		int oAdminId = 0;
		
		OrderHandler oc = null;
		DateHandler dc = null;
		String action = request.getParameter("action");
		ArrayList<String> pageList = new ArrayList<String>();
		List<OrderDTO> oList = null;
		
		switch(action) {
		case "releasePage" : // 구매처에서 납품을 위한 페이지
			oAdminId = (Integer)session.getAttribute("sessionAdminId");
			LOG.debug("session : " + String.valueOf(oAdminId));
			dc = new DateHandler();
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("dateRelease") != null) {
				date = request.getParameter("dateRelease"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			oDao = new OrderDAO();
			LOG.debug("oDao.getCount() : " + oDao.getCount(oAdminId)); 
			count = oDao.getCount(oAdminId);
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("forReleasePage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/supplierServlet?action=releasePage&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			oList = oDao.selectJoinAllbyId(curPage, oAdminId, date);
			
			for (OrderDTO oDto: oList)
				LOG.debug("ODTO : " + oDto.toString());
			
			request.setAttribute("oList", oList);
			request.setAttribute("supplierReleasePageList", pageList);
			rd = request.getRequestDispatcher("/view/supplier/supplierRelease.jsp");
	        rd.forward(request, response);
			break; 
			
		case "requestConfirm" : // 구매확인요청 버튼 클릭 시
			dc = new DateHandler();
			oState = request.getParameter("oState");
			oId = Integer.parseInt(request.getParameter("oId"));
			oDate = request.getParameter("oDate").substring(0, 10);
			LOG.debug(oDate);
			if(!(oState.equals(String.valueOf(OrderState.공급실행)))) {
				message = "아직 구매확인요청을 누를 수 없습니다!!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/FulfillmentService/control/supplierServlet?action=releasePage&page=1&date="+oDate);
				rd = request.getRequestDispatcher("../view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			oDao = new OrderDAO();
			oDao.updateOrderState(String.valueOf(OrderState.구매확인요청), oId, dc.currentTime());
			rd = request.getRequestDispatcher("/control/supplierServlet?action=releasePage&page=1&date="+oDate);
	        rd.forward(request, response);
			break;
			
		case "release" : // 납품 버튼 클릭 시
			dc = new DateHandler();
			oc = new OrderHandler();
			oDao = new OrderDAO();
			oAdminId = (Integer)session.getAttribute("sessionAdminId");
			oDate = dc.yesterday();
			oc.processRelease(oAdminId, oDate);
			rd = request.getRequestDispatcher("/control/supplierServlet?action=releasePage&page=1&date="+oDate);
	        rd.forward(request, response);
			break;
			
		default : break;
		}
	}

}
