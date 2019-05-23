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

import release.ReleaseDAO;
import release.ReleaseDTO;
import state.ReleaseState;
import util.DateHandler;

@WebServlet("/control/transportServlet")
public class TransportProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TransportProc.class);
    public TransportProc() {
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
		
		String rState = null;
		String rInvoiceId = null;
		String message = null;
		String date = null;
		String page = null;
		String[] dateFormat = null;
		int count = 0;
		int pageNo = 0;
		int curPage = 1;
		
		ReleaseDAO rDao = null;
		DateHandler dc = null;
		String action = request.getParameter("action");
		List<ReleaseDTO> rList = null;
		ArrayList<String> pageList = new ArrayList<String>();
		
		switch(action) {
		case "transportHistory" : // 운송내역조회 페이지
			dc = new DateHandler();
		
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthRelease") != null) {
				date = request.getParameter("monthRelease"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			rDao = new ReleaseDAO();
			LOG.debug("vDao.getCount() : " + rDao.getCount()); 
			count = rDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("transportHistoryPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/transportServlet?action=transportHistory&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			rList = rDao.selectMonthlyToRelease(curPage, date);
			for (ReleaseDTO rDto: rList)
				LOG.debug("RDTO : " + rDto.toString());
			
			request.setAttribute("rList", rList);
			request.setAttribute("transportHistoryPageList", pageList);
			rd = request.getRequestDispatcher("/view/transport/transportHistory.jsp");
	        rd.forward(request, response);
			break; 
		
		case "startDelivery" : // 배송실행 버튼 클릭 시
			rState = request.getParameter("rState");
			rInvoiceId = request.getParameter("rInvoiceId");
			if(!(rState.equals(String.valueOf(ReleaseState.배송요청)))) {
				message = "아직 배송실행을 누를 수 없습니다!!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/FulfillmentService/control/transportServlet?action=transportHistory&page=1");
				rd = request.getRequestDispatcher("../view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			rDao = new ReleaseDAO();
			rDao.updateReleaseState(String.valueOf(ReleaseState.배송실행), rInvoiceId);
			rd = request.getRequestDispatcher("/control/transportServlet?action=transportHistory&page=1");
	        rd.forward(request, response);
			break;
			
		case "requestConfirm" : // 배송확인요청 버튼 클릭 시
			rState = request.getParameter("rState");
			rInvoiceId = request.getParameter("rInvoiceId");
			if(!(rState.equals(String.valueOf(ReleaseState.배송실행)))) {
				message = "아직 배송확인요청을 누를 수 없습니다!!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/FulfillmentService/control/transportServlet?action=transportHistory&page=1");
				rd = request.getRequestDispatcher("../view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			rDao = new ReleaseDAO();
			rDao.updateReleaseState(String.valueOf(ReleaseState.배송확인요청), rInvoiceId);
			rd = request.getRequestDispatcher("/control/transportServlet?action=transportHistory&page=1");
	        rd.forward(request, response);
			break;
			
		default : break;
		}
	}
}
