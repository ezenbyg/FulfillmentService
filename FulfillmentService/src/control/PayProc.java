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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		switch(action) {
		case "list" : // 운송회사 클릭 시 배송 내역 출력
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
			session.setAttribute("currentPayPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/control/payServlet?action=list&page=" + i + ">" + i + "</a>&nbsp;";
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
			
		case "searchList" : // 검색 할 때 
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
			session.setAttribute("currentPaySearchPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/control/payServlet?action=searchList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			int searchId = Integer.parseInt(request.getParameter("searchId")); //rTransportId 운송회사 id 검색
			ArrayList<PayDTO> searchList = yDao.searchTransprotList(searchId);
			int listCount = yDao.listCount(searchId);
			int totalPrice = listCount * 5000;
			
			request.setAttribute("totalPrice", totalPrice);
			request.setAttribute("listCount", listCount);
			request.setAttribute("searchList", searchList);
			request.setAttribute("pageList", pageList);
			request.setAttribute("searchId", searchId);
			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
			break;
			
		case "category" :
			int category = Integer.parseInt(request.getParameter("category")); // 목록에서 category 제공할 것
			String title = null;
			
			switch (category) {
			case PayDAO.KK:
				title = "경기 물류";
				break;
			case PayDAO.JB:
				title = "중부 물류";
				break;
			case PayDAO.YN:
				title = "영남 물류";
				break;
			case PayDAO.SB:
				title = "서부 물류";
				break;
			}
			
			request.setAttribute("title", title);
			rd = request.getRequestDispatcher("/control/payServlet?action=searchList&page=1");
			rd.forward(request, response);
			break;
				
		case "pay" : // 지급 버튼 눌렀을 때
			yDao= new PayDAO();
			//잔고 계산
			int rTransportId = Integer.parseInt(request.getParameter("searchId")); //rTransportId 운송회사 id 검색
			listCount = yDao.listCount(rTransportId);
			totalPrice = listCount * 5000; // 총 지불 가격
			yDao.updateBank(totalPrice, rTransportId);
			
			//pay테이블 데이터 삽입
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			String payTime = format.format(time);//지급 시각
			yDao = new PayDAO();
			
			BankDAO bDao = new BankDAO(); // 계좌번호
			BankDTO bDto = bDao.getOneBankList(rTransportId);
			String account = bDto.getbId();
			
			yDto = new PayDTO(account, rTransportId, totalPrice, payTime, "지급");
			yDao.addPayList(yDto);
		}
	}
}