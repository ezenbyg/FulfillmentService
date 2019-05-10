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

import admin.AdminDAO;
import bank.BankDAO;
import bank.BankDTO;
import charge.ChargeDAO;
import charge.ChargeDTO;
import invoice.InvoiceDTO;

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
		
		String page = null;
		int pageNo = 0;
		int count = 0;
		int gAdminId = 0;
		int category = 0;
		int gTotalPrice = 0;
		String shopName = null;
		String account = null;
		String gShopName = null;
		String gDate = null;
		String date = null;
		
		SimpleDateFormat mySdf = null;
		Date today = null;
		
		String url = null;
		String message = null;
		String action = request.getParameter("action");
		List<String> pageList = new ArrayList<String>();
		List<ChargeDTO> gList = null;
		List<InvoiceDTO> vList = null;
		
		switch(action) {
		case "payList" : // 쇼핑몰의 청구조회
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			gDao = new ChargeDAO();
			count = gDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentShopPayPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=chargeServlet?action=payList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			gAdminId = Integer.parseInt((String) session.getAttribute("sessionAdminId"));
			gList = gDao.selectJoinAllToPay(curPage, gAdminId);
			request.setAttribute("payList", gList);
			request.setAttribute("payPageList", pageList);
			rd = request.getRequestDispatcher("XXX.jsp");
	        rd.forward(request, response);
	        LOG.trace("");
			break;
			
		case "monthChargeList" : // 창고의 청구(월 단위로 출력)
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			gDao = new ChargeDAO();
			count = gDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentChargePage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=chargeServlet?action=monthChargeList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			// 월별 조회
			date = request.getParameter("dateCharge");
			if (date == null) {
				mySdf = new SimpleDateFormat("yyyy-MM");
				today = new Date();
				date = mySdf.format(today);
			}
			
			category = Integer.parseInt(request.getParameter("category"));
			switch(category) {
			case AdminDAO.JH쇼핑몰 :
				shopName = "JH쇼핑몰";
				account = "110-1958-1241-68";
				vList = gDao.selectMotnthToCharge(curPage, category, date);
				break;
			case AdminDAO.SW쇼핑몰 :
				shopName = "SW쇼핑몰";
				account = "220-2258-5461-90";
				vList = gDao.selectMotnthToCharge(curPage, category, date);
				break;
			case AdminDAO.GJ쇼핑몰 :
				shopName = "GJ쇼핑몰";
				account = "151-7654-1289-81";
				vList = gDao.selectMotnthToCharge(curPage, category, date);
				break;
			default : break;
			}
			
			for(InvoiceDTO invoice : vList) {
				gTotalPrice += invoice.getvPrice();
			}
			
			// 하단 (쇼핑몰 ID, 쇼핑몰 이름, 계좌번호, 청구가격)
			request.setAttribute("shopName", shopName);
			request.setAttribute("account", account);
			request.setAttribute("gTotalPrice", gTotalPrice);
			
			request.setAttribute("chargeList", vList);
			request.setAttribute("chargePageList", pageList);
			rd = request.getRequestDispatcher("XXX.jsp");
	        rd.forward(request, response);
	        LOG.trace("");
			break;
			
		case "charge" : // 청구
			gAdminId = Integer.parseInt(request.getParameter("gAdminId"));
			gShopName = request.getParameter("gShopName");
			account = request.getParameter("account");
			gTotalPrice = Integer.parseInt(request.getParameter("gTotalPrice"));
			
			SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			String chargeTime = format.format(time);
		
			charge = new ChargeDTO(gAdminId, gShopName, account, gTotalPrice, chargeTime);
			gDao = new ChargeDAO();
			gDao.addChargeList(charge);
			
			charge = gDao.getOneChargeList(gAdminId, chargeTime);
			message = "청구번호는 " + charge.getgId() + " 입니다.";
			request.setAttribute("message", message);
			url = "/control/chargeServlet?action=monthChargeList&page=1";
			request.setAttribute("url", url);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			rd.forward(request, response);
			break;
			
		case "pay" : // 지급(쇼핑몰 -> 창고)
			gAdminId = Integer.parseInt(request.getParameter("gAdminId"));
			gTotalPrice = Integer.parseInt(request.getParameter("gTotalPrice"));
			gDate = request.getParameter("gDate");
			
			gDao = new ChargeDAO(); 
			bDao = new BankDAO();
			charge = gDao.getOneChargeList(gAdminId, gDate);
			
			// 창고 잔고 증가
			bank = bDao.getOneBankList(10001);
			bDao.updateBank(bank, gTotalPrice, "+");
			
			// 쇼핑몰 잔고 감소
			bank = bDao.getOneBankList(gAdminId);
			bDao.updateBank(bank, gTotalPrice, "-");
			
			// 상태 업데이트(청구 -> 지급완료)
			gDao.updateChargeState("지급완료", charge.getgId());
			rd = request.getRequestDispatcher("/control/chargeServlet?action=payList&page=1");
			rd.forward(request, response);
			break;
			
		default : break;
		}
	}
}
