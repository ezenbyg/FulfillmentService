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

import charge.ChargeDAO;
import charge.ChargeDTO;
import invoice.InvoiceDAO;
import invoice.InvoiceDTO;
import invoice.InvoiceProductDTO;
import state.ChargeState;
import util.ChargeHandler;
import util.DateHandler;

@WebServlet("/control/shopServlet")
public class ShoppingProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ShoppingProc.class);   
    public ShoppingProc() {
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
		String page = null;
		String date = null;
		String gBankId = null;
		String gState = null;
		String gDate = null;
		String message = null;
		String[] dateFormat = null;
		int curPage = 1;
		int count = 0;
		int pageNo = 0;
		int gAdminId = 0;
		int vAdminId = 0;
		int gId = 0;
		int gTotalPrice = 0;
		ChargeDAO gDao = null;
		InvoiceDAO vDao = null;
		ChargeHandler cc = null;
		DateHandler dc = null;
		String action = request.getParameter("action");
		List<ChargeDTO> gList = null;
		List<InvoiceDTO> vList = null;
		ArrayList<String> pageList = new ArrayList<String>();
		
		switch(action) {
		case "chargeHistory" : // 청구요청 처리를 위한 페이지 
			dc = new DateHandler();
			gAdminId = (Integer)session.getAttribute("sessionAdminId");
			LOG.debug("session : " + String.valueOf(gAdminId));
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthCharge") != null) {
				date = request.getParameter("monthCharge"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			gDao = new ChargeDAO();
			LOG.debug("gDao.getCount() : " + gDao.getCount()); 
			count = gDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("shopChargeHistoryPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/transportServlet?action=chargeHistory&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			gList = gDao.selectJoinAllbyId(curPage, gAdminId, date);
			for (ChargeDTO gDto: gList)
				LOG.debug("GDTO : " + gDto.toString());
			
			request.setAttribute("gList", gList);
			request.setAttribute("shopChargeHistoryPageList", pageList);
			rd = request.getRequestDispatcher("/view/shopping/shoppingChargeList.jsp");
	        rd.forward(request, response);
			break;
			
		case "pay" : // 청구완료 처리
			cc = new ChargeHandler();
			gId = Integer.parseInt(request.getParameter("gId"));
			gBankId = request.getParameter("gBankId");
			gAdminId = Integer.parseInt(request.getParameter("gAdminId"));
			gTotalPrice = Integer.parseInt(request.getParameter("gTotalPrice"));
			gState = request.getParameter("gState");
			gDate = request.getParameter("gDate");
			if(!(gState.equals(String.valueOf(ChargeState.청구요청)))) {
				message = "아직 지급을 누를 수 없습니다!!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/FulfillmentService/control/shopServlet?action=chargeHistory&page=1");
				rd = request.getRequestDispatcher("../view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			cc.processConfirmCharge(gId, gAdminId, gBankId, gTotalPrice, gDate);
			rd = request.getRequestDispatcher("/control/shopServlet?action=chargeHistory&page=1");
	        rd.forward(request, response);
			break;
			
		case "monthlyInvoiceList" : // 월 단위 송장 내역 조회
			dc = new DateHandler();
			vAdminId = (Integer)session.getAttribute("sessionAdminId");
			LOG.debug("page : " + page);
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthInvoice") != null) {
				date = request.getParameter("monthInvoice"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			vDao = new InvoiceDAO();
			// LOG.debug("vDao.getCount() : " + vDao.getCount()); 
			count = vDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("monthlyInvoicePage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=monthlyInvoiceList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			vList = vDao.selectJoinAll(curPage, vAdminId, date);
			for (InvoiceDTO vDto: vList)
				LOG.debug("IVTO : " + vDto.toString());
			request.setAttribute("invoiceList", vList);
			request.setAttribute("invoicePageList", pageList);
			rd = request.getRequestDispatcher("/view/shopping/shoppingInvoiceDetail.jsp");
	        rd.forward(request, response);
			break; 
		}
	}
}
