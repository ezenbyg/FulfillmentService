package control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import pay.PayDAO;
import pay.PayDTO;

@WebServlet("/control/payServlet")
public class PayProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(PayProc.class);

	public PayProc() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-9");
		RequestDispatcher rd = null;
		PayDAO yDao = null;
		PayDTO yDto = null;
		ArrayList<String> pageList = new ArrayList<String>();
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		int curPage = 1;
		int count = 0;
		int pageNo = 0;

		String page = null;

		switch (action) {
		case "transportList": // 운송회사 클릭 시 배송 내역 출력
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			yDao = new PayDAO();
			count = yDao.getCount();
			if (count == 0) // 데이터가 없을 때 대비
				count = 1;
			pageNo = (int) Math.ceil(count / 10.0);
			if (curPage > pageNo) // 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentTransportPayPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기

			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/control/payServlet?action=transportList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);

			ArrayList<PayDTO> payList = yDao.getTransportList();
			request.setAttribute("pasList", payList);
			request.setAttribute("pageList", pageList);
			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
			break;

		case "purchasingList": // 구매처 클릭 시 구매 내역 출력
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			yDao = new PayDAO();
			count = yDao.getCount();
			if (count == 0) // 데이터가 없을 때 대비
				count = 1;
			pageNo = (int) Math.ceil(count / 10.0);
			if (curPage > pageNo) // 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentPurchasingPayPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기

			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/control/payServlet?action=purchasingList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);

			ArrayList<PayDTO> PurchasingPayList = yDao.getTransportList();
			request.setAttribute("pasList", PurchasingPayList);
			request.setAttribute("pageList", pageList);
			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
			break;

		case "transportSearchList": // 운송회사 검색 할 때
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			yDao = new PayDAO();
			count = yDao.getCount();
			if (count == 0) // 데이터가 없을 때 대비
				count = 1;
			pageNo = (int) Math.ceil(count / 10.0);
			if (curPage > pageNo) // 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentTransportPaySearchPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기

			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/control/payServlet?action=transportSearchList&page=" + i + ">" + i
							+ "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);

			int transportSearchId = Integer.parseInt(request.getParameter("transportSearchId")); // rTransportId 운송회사 id
																									// 검색
			ArrayList<PayDTO> transportSearchList = yDao.searchTransprotList(transportSearchId);

			int totalTransportPay = yDao.totalTransportPay(transportSearchId); // 운송 비용 값.( 운송장 수 * 5000 )
			BankDAO bDao = new BankDAO();
			BankDTO bDto = bDao.getOneBankList(transportSearchId); // 검색한 운송사 id에 따른 정보 얻음

			request.setAttribute("totalTransportPay", totalTransportPay);
			request.setAttribute("transportSearchList", transportSearchList);
			request.setAttribute("pageList", pageList);
			request.setAttribute("transportSearchId", transportSearchId);
			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
			break;

		case "purchasingSearchList": // 구매처 검색 할 때
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			yDao = new PayDAO();
			count = yDao.getCount();
			if (count == 0) // 데이터가 없을 때 대비
				count = 1;
			pageNo = (int) Math.ceil(count / 10.0);
			if (curPage > pageNo) // 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentPurchasingPaySearchPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기

			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/control/payServlet?action=purchasingSearchList&page=" + i + ">" + i
							+ "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);

			int purchasingSearchId = Integer.parseInt(request.getParameter("purchasingSearchId")); // 구매처 id 검색
			ArrayList<PayDTO> purchasingSearchList = yDao.searchTransprotList(purchasingSearchId);

			int oAdminId = (Integer) session.getAttribute("sessionCustomerId"); // 로그인한 구매처 id
			int totalPurchasingPay = yDao.totalPurchasingPay("oTotalPrice", oAdminId); // 검색한 구매처 총 지불값
			bDao = new BankDAO();
			bDto = bDao.getOneBankList(oAdminId); // 검색한 구매처 id에 따른 정보 얻음

			request.setAttribute("totalPurchasingPay", totalPurchasingPay);
			request.setAttribute("purchasingSearchList", purchasingSearchList);
			request.setAttribute("pageList", pageList);
			request.setAttribute("purchasingSearchId", purchasingSearchId);
			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
			break;

		case "transportCategory":
			int category = Integer.parseInt(request.getParameter("category")); // 목록에서 category 제공할 것
			String title = null;

			switch (category) {
			case AdminDAO.경기물류:
				title = "경기 물류";
				break;
			case AdminDAO.중부물류:
				title = "중부 물류";
				break;
			case AdminDAO.영남물류:
				title = "영남 물류";
				break;
			case AdminDAO.서부물류:
				title = "서부 물류";
				break;
			}

			request.setAttribute("title", title);
			rd = request.getRequestDispatcher("/control/payServlet?action=transportSearchList&page=1");
			rd.forward(request, response);
			break;

		case "purchasingCategory":
			category = Integer.parseInt(request.getParameter("category")); // 목록에서 category 제공할 것
			title = null;

			switch (category) {
			case AdminDAO.무신사:
				title = "무신사";
				break;
			case AdminDAO.와구와구:
				title = "와구와구";
				break;
			case AdminDAO.하이마트:
				title = "하이마트";
				break;
			case AdminDAO.언더아머:
				title = "언더아머";
				break;
			case AdminDAO.이케아:
				title = "이케아";
				break;
			}

			request.setAttribute("title", title);
			rd = request.getRequestDispatcher("/control/payServlet?action=purchasingSearchList&page=1");
			rd.forward(request, response);
			break;

		case "transportPay": // 지급(운송회사) 버튼 눌렀을 때
			yDao = new PayDAO();
			// 잔고 계산
			transportSearchId = Integer.parseInt(request.getParameter("transportSearchId")); // rTransportId 운송회사 id 검색
			totalTransportPay = yDao.totalTransportPay(transportSearchId); // 운송 비용 값.( 운송장 수 * 5000 )
			bDao = new BankDAO();
			bDto = bDao.getOneBankList(transportSearchId); // 검색한 운송사 id에 따른 정보 얻음
			bDao.updateBank(bDto, totalTransportPay, "-");

			// pay테이블 데이터 삽입
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			String rPayTime = format.format(time);// 지급 시각
			yDao = new PayDAO();
			String rAccount = bDto.getbId();

			yDto = new PayDTO(rAccount, transportSearchId, totalTransportPay, rPayTime, "지급");
			yDao.addPayList(yDto);

		case "purchasingPay": // 지급(구매처) 버튼 눌렀을 때
			yDao = new PayDAO();
			// 잔고 계산
			oAdminId = (Integer) session.getAttribute("sessionCustomerId"); // 로그인한 구매처 id
			totalPurchasingPay = yDao.totalPurchasingPay("oTotalPrice", oAdminId); // 검색한 구매처 총 지불값
			bDao = new BankDAO();
			bDto = bDao.getOneBankList(oAdminId); // 검색한 구매처 id에 따른 정보 얻음
			bDao.updateBank(bDto, totalPurchasingPay, "-");

			// pay테이블 데이터 삽입
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time = new Date();
			String oPayTime = format.format(time);// 지급 시각
			yDao = new PayDAO();
			String oAccount = bDto.getbId();

			yDto = new PayDTO(oAccount, oAdminId, totalPurchasingPay, oPayTime, "지급");
			yDao.addPayList(yDto);

		}
	}
}