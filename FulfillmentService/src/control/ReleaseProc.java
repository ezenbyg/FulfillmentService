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
		
		ReleaseDAO rDao = null;
		ReleaseDTO release = null;
		StorageDAO pDao = null;
		StorageDTO product = null;
		List<ReleaseDTO> rList = null;
		int rTransportId = 0;
		int rShoppingId = 0;
		int rInvoiceId = 0;
		int rPrice = 0;
		String rName = null;
		String rTel = null;
		String rAddress = null;
		String rProductName = null;
		String rDate = null;
		int rQuantity = 0;
		String url = null;
		String message = null;
		String action = request.getParameter("action");
		List<String> pageList = new ArrayList<String>();
		
		switch(action) {
		case "list" : // 운송 내역 조회
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			rDao = new ReleaseDAO();
			int count = rDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			int pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentReleasePage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			String page = null;
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=releaseServlet?action=list&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			rTransportId = Integer.parseInt((String) session.getAttribute("sessionTransportId"));
			rList = rDao.selectJoinAll(curPage, rTransportId);
			request.setAttribute("releaseList", rList);
			request.setAttribute("releasePageList", pageList);
			rd = request.getRequestDispatcher("XXX.jsp");
	        rd.forward(request, response);
	        LOG.trace("");
			break;
			
		case "release" : // 출고(창고 -> 운송회사)
			rPrice = Integer.parseInt(request.getParameter("rPrice"));
			rTransportId = Integer.parseInt(request.getParameter("rTransportId"));
			rShoppingId = Integer.parseInt(request.getParameter("rShoppingId"));
			rInvoiceId = Integer.parseInt(request.getParameter("rInvoiceId"));
			rName = request.getParameter("rName");
			rTel = request.getParameter("rTel");
			rAddress = request.getParameter("rAddress");
			rProductName = request.getParameter("rProductName");
			rQuantity = Integer.parseInt(request.getParameter("rQuantity"));
			rDate = request.getParameter("rDate");
			
			pDao = new StorageDAO();
			product = pDao.getOneProductByName(rProductName);
			
			// 배송물품수량 > 재고수량 : 출고 불가능 -> 발주
			if(rQuantity > product.getpQuantity()) {
				response.sendRedirect("XXX.jsp");
				break;
			}
			
			// 시간 비교
			SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			String releaseTime = format.format(time);
			
			String year = releaseTime.substring(0, 4);
			String month = releaseTime.substring(5, 7);
			String date = releaseTime.substring(8, 10);
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
				}
			} else if((releaseDate.getTime() >= conDateR3.getTime()) && (releaseDate.getTime() < conDateR4.getTime())) {
				if((invoiceDate.getTime() >= conDateI2.getTime()) && (invoiceDate.getTime() <= conDateI3.getTime())) {
					// 출고
					release = new ReleaseDTO(rTransportId, rShoppingId, rInvoiceId, rName, rTel, rAddress,
							rProductName, rQuantity, releaseTime, rPrice);
					rDao = new ReleaseDAO();
					rDao.addReleaseList(release);
				}
			} else {
				message = "출고 가능한 시간이 아닙니다!!";
				request.setAttribute("message", message);
				url = "XXX.jsp";
				request.setAttribute("url", url);
				rd = request.getRequestDispatcher("alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			// 송장 상태 변경(N: 미처리 -> Y: 처리완료)
			InvoiceDAO vDao = new InvoiceDAO();
			vDao.updateInvoiceState("Y", rInvoiceId);
			
			// 창고 테이블 제품 수량 감소 
			pDao.updateStorage(product.getpQuantity()-rQuantity, product.getpId());
			
			release = rDao.getOneReleaseList(rInvoiceId);
			message = "출고번호는 " + release.getrId() + " 입니다.";
			request.setAttribute("message", message);
			url = "XXX.jsp";
			request.setAttribute("url", url);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			rd.forward(request, response);
			break;
			
		case "stateUpdate" : // 운송회사에서 운송상태 변경
			int stateNum = Integer.parseInt(request.getParameter("state"));
			rInvoiceId = Integer.parseInt(request.getParameter("rInvoiceId"));
			rDao = new ReleaseDAO();
			String state = null;
			switch (stateNum) {
			case ReleaseDAO.출고 :
				state = "출고";
				rDao.updateReleaseState(state, rInvoiceId);
				break;
			case ReleaseDAO.배송전 :
				state = "배송전";
				rDao.updateReleaseState(state, rInvoiceId);
				break;
			case ReleaseDAO.배송중 :
				state = "배송중";
				rDao.updateReleaseState(state, rInvoiceId);
				break;
			case ReleaseDAO.배송완료 :
				state = "배송완료";
				rDao.updateReleaseState(state, rInvoiceId);
				break;
			default : break;
			}
			request.setAttribute("state", state);
			rd = request.getRequestDispatcher("/control/releaseServlet?action=list&page=1");
			rd.forward(request, response);
			break;
			
		default : break;
		}
	}
}
