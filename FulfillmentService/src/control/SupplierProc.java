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
import util.DateController;

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
		
		int count = 0;
		int pageNo = 0;
		int curPage = 1;
		int oAdminId = 0;
		
		DateController dc = null;
		String action = request.getParameter("action");
		ArrayList<String> pageList = new ArrayList<String>();
		
		switch(action) {
		case "orderHistory" : // 발주 내역 조회 페이지
			oAdminId = (Integer)session.getAttribute("sessionAdminId");
			dc = new DateController();
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("dateOrder") != null) {
				date = request.getParameter("dateOrder"); 
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
			
			rList = rDao.selectdailyToRelease(curPage, date);
			for (ReleaseDTO rDto: rList)
				LOG.debug("RDTO : " + rDto.toString());
			
			request.setAttribute("rList", rList);
			request.setAttribute("transportHistoryPageList", pageList);
			rd = request.getRequestDispatcher("/view/transport/transportHistory.jsp");
	        rd.forward(request, response);
			break; 
		}
	}

}
