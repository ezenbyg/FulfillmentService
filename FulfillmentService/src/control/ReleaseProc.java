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

import invoice.InvoiceDAO;
import invoice.InvoiceDTO;
import release.ReleaseDAO;
import release.ReleaseDTO;
import storage.StorageDAO;
import storage.StorageDTO;

/**
 * Servlet implementation class ReleaseProc
 */
@WebServlet("/control/releaseServlet")
public class ReleaseProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ReleaseProc.class);      
	
    public ReleaseProc() {
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
		Date invoiceDate = null;
		Date conDateR1 = null;
		Date conDateR2 = null;
		Date conDateR3 = null;
		Date conDateR4 = null;
		Date conDateI1 = null;
		Date conDateI2 = null;
		Date conDateI3 = null;
		
		SimpleDateFormat mySdf = null;
		Date today = null;
		
		String page = null;
		int pageNo = 0;
		int count = 0;
		
		ReleaseDAO rDao = null;
		ReleaseDTO release = null;
		StorageDAO pDao = null;
		StorageDTO product = null;
		InvoiceDAO vDao = null;
		List<ReleaseDTO> rList = null;
		List<InvoiceDTO> vList = null;
		int rTransportId = 0;
		int rShoppingId = 0;
		int rInvoiceId = 0;
		int rPrice = 0;
		String rName = null;
		String rTel = null;
		String rAddress = null;
		String rProductName = null;
		String pState = null;
		String rDate = null;
		String year = null;
		String month = null;
		String date = null;
		int rQuantity = 0;
		int spare = 0;
		String url = null;
		String message = null;
		String action = request.getParameter("action");
		List<String> pageList = new ArrayList<String>();
		
		switch(action) {
		case "transportList" : // 운송 내역 조회
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			rDao = new ReleaseDAO();
			count = rDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentTransportListPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=releaseServlet?action=transportList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			rTransportId = Integer.parseInt((String) session.getAttribute("sessionAdminId"));
			rList = rDao.selectJoinAll(curPage, rTransportId);
			request.setAttribute("transportList", rList);
			request.setAttribute("transportPageList", pageList);
			rd = request.getRequestDispatcher("XXX.jsp");
	        rd.forward(request, response);
	        LOG.trace("");
			break;
			
		case "releaseList" : // 출고 대상 리스트 조회(일별)
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			rDao = new ReleaseDAO();
			count = rDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentReleasePage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=releaseServlet?action=releaseList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			// 일별 조회
			date = request.getParameter("dateCharge");
			if (date == null) {
				mySdf = new SimpleDateFormat("yyyy-MM-dd");
				today = new Date();
				date = mySdf.format(today);
			}
			
			// 운송회사 구분 짓기
			
			vList = rDao.selectdailyToRelease(curPage, date);
			request.setAttribute("releaseList", vList);
			request.setAttribute("releasePageList", pageList);
			rd = request.getRequestDispatcher("XXX.jsp");
	        rd.forward(request, response);
	        LOG.trace("");
			break;
			
		case "hold" : // 출고 보류
			rProductName = request.getParameter("rProductName");
			rInvoiceId = Integer.parseInt(request.getParameter("rInvoiceId"));
			
			pDao = new StorageDAO();
			product = pDao.getOneProductByName(rProductName);
	
			if(!product.getpState().equals("재고부족")) {
				message = "출고보류 대상이 아닙니다!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/storage/XXX.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			rDao = new ReleaseDAO();
			rDao.updateReleaseState("출고보류", rInvoiceId);
			break;
			
		case "release" : // 출고(창고 -> 운송회사)
			rTransportId = Integer.parseInt(request.getParameter("rTransportId"));
			rShoppingId = Integer.parseInt(request.getParameter("rShoppingId"));
			rInvoiceId = Integer.parseInt(request.getParameter("rInvoiceId"));
			rName = request.getParameter("rName");
			rTel = request.getParameter("rTel");
			rAddress = request.getParameter("rAddress");
			rProductName = request.getParameter("rProductName");
			pState = request.getParameter("pState");
			rQuantity = Integer.parseInt(request.getParameter("rQuantity"));
			rPrice = Integer.parseInt(request.getParameter("rPrice"));
			
			pDao = new StorageDAO();
			product = pDao.getOneProductByName(rProductName);
			
			// 출고 후 남아있는 예상 제품 수량
			spare = product.getpQuantity() - rQuantity;
			
			// pState가 재고부족이면 출고 불가능
			if(product.getpState().equals("재고부족")) {
				message = "재고가 부족합니다.";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/storage/XXX.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			// 재고부족예상인 경우 경고메시지 띄우기
			} else if(product.getpState().equals("재고부족예상")) {
				message = "출고 후 발주가 필요합니다!!";
				request.setAttribute("message", message);
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
			}
			
			// 시간 비교
			SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			String releaseTime = format.format(time);
			
			year = releaseTime.substring(0, 4);
			month = releaseTime.substring(5, 7);
			date = releaseTime.substring(8, 10);
			int subDate = Integer.parseInt(date) - 1;
			
			try {
				releaseDate = format.parse(releaseTime);
				invoiceDate = format.parse(rDate);
				conDateR1 = format.parse(year+"-"+month+"-"+date+" "+"09"+":"+"00"); 
				conDateR2 = format.parse(year+"-"+month+"-"+date+" "+"10"+":"+"00"); 
				conDateR3 = format.parse(year+"-"+month+"-"+date+" "+"18"+":"+"00"); 
				conDateR4 = format.parse(year+"-"+month+"-"+date+" "+"19"+":"+"00"); 
				conDateI1 = format.parse(year+"-"+month+"-"+String.valueOf(subDate)+" "+"18"+":"+"00"); 
				conDateI2 = format.parse(year+"-"+month+"-"+date+" "+"09"+":"+"00"); 
				conDateI3 = format.parse(year+"-"+month+"-"+date+" "+"18"+":"+"00"); 
			} catch (ParseException e) {
				LOG.trace(e.getMessage());
				e.printStackTrace();
			}
			
			// 출고 가능 시간 
			// 오전 9시 ~ 오전 10시 || 오후 18시 ~ 오후 19시 
			if((releaseDate.getTime() >= conDateR1.getTime()) && (releaseDate.getTime() < conDateR2.getTime())) {
				if((invoiceDate.getTime() >= conDateI1.getTime()) && (invoiceDate.getTime() <= conDateI2.getTime())) {
					// 출고
					release = new ReleaseDTO(rTransportId, rShoppingId, rInvoiceId, rName, rTel, rAddress,
							rProductName, rQuantity, releaseTime, rPrice);
					rDao = new ReleaseDAO();
					rDao.addReleaseList(release);
					
					// 제품 상태 및 수량 변경
					if(spare < 10) pDao.updateStorage(spare, "재고부족예상", product.getpId());
					else if(spare >= 10) pDao.updateStorage(spare, "P", product.getpId());
					
					// 송장 상태 변경
					vDao = new InvoiceDAO();
					vDao.updateInvoiceState("출고완료", rInvoiceId);
				}
			} else if((releaseDate.getTime() >= conDateR3.getTime()) && (releaseDate.getTime() < conDateR4.getTime())) {
				if((invoiceDate.getTime() >= conDateI2.getTime()) && (invoiceDate.getTime() <= conDateI3.getTime())) {
					// 출고
					release = new ReleaseDTO(rTransportId, rShoppingId, rInvoiceId, rName, rTel, rAddress,
							rProductName, rQuantity, releaseTime, rPrice);
					rDao = new ReleaseDAO();
					rDao.addReleaseList(release);
					
					// 제품 상태 및 수량 변경
					if(spare < 10) pDao.updateStorage(spare, "재고부족예상", product.getpId());
					else if(spare >= 10) pDao.updateStorage(spare, "P", product.getpId());
					
					// 송장 상태 변경
					vDao = new InvoiceDAO();
					vDao.updateInvoiceState("출고완료", rInvoiceId);
				}
			} else {
				message = "출고 가능 시간이 아닙니다!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/storage/XXX.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			rd = request.getRequestDispatcher("/control/orderServlet?action=supplierOrderList&page=1");
			rd.forward(request, response);
			break;
			
		default : break;
		}
	}
}
