package control;

import java.io.IOException;
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

import bank.BankDAO;
import bank.BankDTO;
import charge.ChargeDAO;
import charge.ChargeDTO;

@WebServlet("/control/chargeServlet")
public class ChargeProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ChargeProc.class);      
    public ChargeProc() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		int curPage = 1;
		
		ChargeDAO gDao = null;
		ChargeDTO charge = null;
		BankDAO bDao = null;
		BankDTO bank = null;
		
		int gAdminId = 0;
		int gInvoiceId = 0;
		int gPrice = 0;
		int gQuantity = 0;
		String gProductName = null;
		
		String url = null;
		String message = null;
		String action = request.getParameter("action");
		List<String> pageList = new ArrayList<String>();
		List<ChargeDTO> gList = null;
		
		switch(action) {
		case "list" : // 청구 리스트
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			gDao = new ChargeDAO();
			int count = gDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentChargePage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			String page = null;
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=chargeServlet?action=list&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			gAdminId = Integer.parseInt((String) session.getAttribute("sessionAdminId"));
			gList = gDao.selectJoinAll(curPage, gAdminId);
			request.setAttribute("chargeList", gList);
			request.setAttribute("chargePageList", pageList);
			rd = request.getRequestDispatcher("XXX.jsp");
	        rd.forward(request, response);
	        LOG.trace("");
			break;
			
		case "charge" : // 청구
			gAdminId = Integer.parseInt(request.getParameter("gAdminId"));
			gInvoiceId = Integer.parseInt(request.getParameter("gInvoiceId"));
			gProductName = request.getParameter("gProductName");
			gQuantity = Integer.parseInt(request.getParameter("gQuantity"));
			gPrice = Integer.parseInt(request.getParameter("gPrice"));
			
			SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			String chargeTime = format.format(time);
			
			bDao = new BankDAO();
			bank = bDao.getOneBankList(gAdminId);
		
			charge = new ChargeDTO(gAdminId, gInvoiceId, bank.getbId(), gProductName, gQuantity, gPrice, chargeTime);
			gDao = new ChargeDAO();
			gDao.addChargeList(charge);
			
			charge = gDao.getOneChargeList(gInvoiceId);
			message = "청구번호는 " + charge.getgId() + " 입니다.";
			request.setAttribute("message", message);
			url = "XXX.jsp";
			request.setAttribute("url", url);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			rd.forward(request, response);
			break;
			
		case "pay" : // 지급(쇼핑몰 -> 창고)
			gAdminId = Integer.parseInt(request.getParameter("gAdminId"));
			gInvoiceId = Integer.parseInt(request.getParameter("gInvoiceId"));
			gPrice = Integer.parseInt(request.getParameter("gPrice"));
			
			gDao = new ChargeDAO();
			bDao = new BankDAO();
			
			// 창고 잔고 증가
			bank = bDao.getOneBankList(10001);
			bDao.updateBank(bank, gPrice, "+");
			
			// 쇼핑몰 잔고 감소
			bank = bDao.getOneBankList(gAdminId);
			bDao.updateBank(bank, gPrice, "-");
			
			// 상태 업데이트(청구 -> 지급완료)
			gDao.updateChargeState("지급완료", gInvoiceId);
			rd = request.getRequestDispatcher("/control/chargeServlet?action=list&page=1");
			rd.forward(request, response);
			break;
			
		default : break;
		}
	}
}
