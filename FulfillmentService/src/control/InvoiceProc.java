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

import invoice.InvoiceDAO;
import invoice.InvoiceDTO;

@WebServlet("/control/invoiceServlet")

public class InvoiceProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceProc.class);

	public InvoiceProc() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		InvoiceDTO invoice = null;
		InvoiceDAO vDao = null;
		int curPage = 1;
		int count = 0;
		int pageNo = 0;
		int vAdminId = 0;
		String page = null;
		
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		String action = request.getParameter("action");
		List<String> pageList = new ArrayList<String>();
		List<InvoiceDTO> vList = null;
		
		switch(action) {
		case "invoiceList" :
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			vDao = new InvoiceDAO();
			count = vDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentInvoicePage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/invoiceServlet?action=invoiceList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			// 드롭다운으로 선택한 쇼핑몰 아이디로 구분 짓기
			// vAdminId 파라미터 값이 Zero(0)일 경우 전체 페이지 출력
			vAdminId = Integer.parseInt(request.getParameter("vAdminId"));
			if(vAdminId == 0) { // 전체 출력
				vList = vDao.selectJoinAll(curPage);
				request.setAttribute("invoiceList", vList);
				request.setAttribute("invoicePageList", pageList);
				rd = request.getRequestDispatcher("/control/invoiceServlet?action=invoiceList&page=1");
		        rd.forward(request, response);
			} else { // 각 쇼핑몰에 대한 송장 내역 출력
				vList = vDao.selectJoinAllbyId(curPage, vAdminId);
				request.setAttribute("invoiceList", vList);
				request.setAttribute("invoicePageList", pageList);
				rd = request.getRequestDispatcher("/control/invoiceServlet?action=invoiceList&page=1");
		        rd.forward(request, response);
			}
		case "download" : // CSV 파일 다운로드
			FileController fc = new FileController();
			fc.readCSV();
			rd = request.getRequestDispatcher("/control/invoiceServlet?action=invoiceList&page=1");
	        rd.forward(request, response);
			
		}
		
	}
}
