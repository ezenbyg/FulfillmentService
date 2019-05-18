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

import admin.AdminDAO;
import admin.AdminDTO;
import invoice.InvoiceDAO;
import invoice.InvoiceDTO;
import invoice.InvoiceProductDTO;
import order.OrderDAO;
import order.OrderDTO;
import release.ReleaseDAO;
import release.ReleaseDTO;
import storage.StorageDAO;
import storage.StorageDTO;

/**
 * Servlet implementation class AdminProc
 */
@WebServlet("/control/adminServlet")
public class AdminProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AdminProc.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProc() {
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
		
		StorageDAO pDao = null;
		StorageDTO product = null;
		OrderDAO oDao = null;
		OrderDTO order = null;
		AdminDAO aDao = null;
		AdminDTO admins = null;
		InvoiceDTO invoice = null;
		InvoiceDAO vDao = null;
		ReleaseDAO rDao = null;
		ReleaseDTO release = null;
		RequestDispatcher rd = null;

		String name = null;
		String title = null;
		String page = null;
		String message = null;
		
		int curPage = 1;
		int categoryNum = 0;
		int count = 0;
		int pageNo = 0;
		int pathNum = 0;
		int vAdminId = 0;
		int oId = 0;
		int oAdminId = 0;
		int oProductId = 0;
		int oQuantity = 0;
		int oPrice = 0;
		int oTotalPrice = 0;
	
		int rInvoiceId = 0;
		String rTransportName = null;
	
		String pState = null;
		String rDate = null;
		String vId = null;
		
		String action = request.getParameter("action");
		List<InvoiceDTO> vList = null;
		List<InvoiceDTO> vDetailList = null;
		List<OrderDTO> oList = null;
		List<InvoiceProductDTO> ipList = null;
		ArrayList<String> pageList = new ArrayList<String>();
		
		switch (action) {
		case "productList" : // 상품목록 
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			
			name = request.getParameter("name");
			categoryNum = Integer.parseInt(request.getParameter("categoryNum"));
			System.out.println(name);
			
			pDao = new StorageDAO();
			count = pDao.getEachAdminIdCount(categoryNum);
			System.out.println(count);
			if (count == 0) // 데이터가 없을 때 대비
				count = 1;
			pageNo = (int) Math.ceil(count / 8.0);
			if (curPage > pageNo) // 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentProductPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=productList&page=" + i +
						"&categoryNum="+categoryNum+"&name="+name+">"+ i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);

			System.out.println("categoryNum = " + categoryNum);
			ArrayList<StorageDTO> storageList = pDao.getTitleProducts(categoryNum,curPage);
			
			for(StorageDTO st : storageList) {
				System.out.println(st.toString());
			}
		
			// 해당 구매처의 내용만 출력과 함께 페이지 출력을 위해 parameter값을 카테고리, 현재페이지를 줌
			request.setAttribute("storageList", storageList);
			request.setAttribute("pageList", pageList);
			
			LOG.debug("/view/main/"+name+".jsp");
			rd = request.getRequestDispatcher("/view/main/"+name+".jsp");
	        rd.forward(request, response);
			break;
			
		case "category" : // 상품 목록 출력을 위한 카테고리 구분
			name = request.getParameter("name");
			categoryNum = Integer.parseInt(request.getParameter("categoryNum")); // 목록에서 category 제공할 것
			
			switch (categoryNum) {
			case AdminDAO.무신사 :
				title = "무신사 Store";
				break;
			case AdminDAO.와구와구 :
				title = "와구와구 Store";
				break;
			case AdminDAO.하이마트 :
				title = "하이마트 Store";
				break;
			case AdminDAO.언더아머 :
				title = "언더아머 Store";
				break;
			case AdminDAO.이케아 :
				title = "이케아 Store";
				break;
			}
			
			System.out.println(title);
			pathNum = Integer.parseInt(request.getParameter("pathNum"));
			if(pathNum == 1) {
				request.setAttribute("title", title);
				rd = request.getRequestDispatcher("/control/adminServlet?action=productList&page=1&categoryNum="+categoryNum+"&name="+name);
			} else if(pathNum == 2) {
				request.setAttribute("title", title);
				rd = request.getRequestDispatcher("/control/adminServlet?action=supplierSearch&page=1&categoryNum="+categoryNum);
			} else if(pathNum ==3) {
				request.setAttribute("title", title);
				rd = request.getRequestDispatcher("/control/adminServlet?action=modal");
			}
	        rd.forward(request, response);
			break;
			
		case "stockList" : // 재고조사 
			LOG.debug("여기");
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			pDao = new StorageDAO();
			count = pDao.getAllAdminIdCount(); // 모든 리스트 카운트.
			if (count == 0) // 데이터가 없을 때 대비
				count = 1;
			pageNo = (int) Math.ceil(count / 10.0);
			if (curPage > pageNo) // 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentStockPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = null;
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=stockList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			ArrayList<StorageDTO> stockList = pDao.getAllProducts();
			request.setAttribute("stockList", stockList);
			request.setAttribute("pageList", pageList);
			LOG.debug("/view/storage/storageStocktaking.jsp");
			rd = request.getRequestDispatcher("/view/storage/storageStocktaking.jsp");
			rd.forward(request, response);
			break;
			
		case "search" : // 상품 이름 일부 검색 리스트 출력
			String word = request.getParameter("searchWord");
			pDao = new StorageDAO();
			ArrayList<StorageDTO> searchList = pDao.getSearchProduct(word);
			request.setAttribute("searchList", searchList);
			
			rd = request.getRequestDispatcher("/view/storage/storageStocktaking.jsp");
			rd.forward(request, response);
			break;
			
		case "supplierSearch" : // 구매처별 리스트 출력 // ★case 추가 5.14
			pDao = new StorageDAO();
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			pDao = new StorageDAO();
			LOG.debug(String.valueOf(categoryNum));
			count = pDao.getEachAdminIdCount(categoryNum); // 해당 관리자의 잔고 리스트 카운트.
			if (count == 0) // 데이터가 없을 때 대비
				count = 1;
			pageNo = (int) Math.ceil(count / 10.0);
			if (curPage > pageNo) // 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentStockPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = null;
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=supplierSearch&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			ArrayList<StorageDTO> supplierSearchList = pDao.getTitleProducts(categoryNum,curPage);
			request.setAttribute("supplierSearchList", supplierSearchList);
			request.setAttribute("pageList", pageList);
			LOG.debug("/view/storage/storageStocktaking.jsp");
			rd = request.getRequestDispatcher("/view/storage/storageStocktaking.jsp");
			rd.forward(request, response);
			break;
			
		case "modal" : // 상품id 클릭시 세부사항 출력 // ★case 추가 5.14
			pDao = new StorageDAO();
			StorageDTO modal = new StorageDTO();
			
			int pId = Integer.parseInt(request.getParameter("pId"));
			modal = pDao.getModal(pId); // pId, pName, pPrice, pQuantity  get
			LOG.debug(title);
			request.setAttribute("modal", modal);
			break;
		
		case "invoiceList" : // 송장목록출력
			LOG.debug("page : " + page);
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			vDao = new InvoiceDAO();
			LOG.debug("vDao.getCount() : " + vDao.getCount()); 
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
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=invoiceList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			vList = vDao.selectJoinAll(curPage);
			for (InvoiceDTO vDto: vList)
				LOG.debug("IVTO : " + vDto.toString());
			ipList = vDao.getProductByInvoiceId();
			request.setAttribute("ipList", ipList);
			request.setAttribute("invoiceList", vList);
			request.setAttribute("invoicePageList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storageShipping.jsp");
	        rd.forward(request, response);
			break; 
			
		case "invoiceDetail" : // 송장 상세 조회
			if (!request.getParameter("vId").equals("")) {
				vId = request.getParameter("vId");
			}
			vDao = new InvoiceDAO();
			vList = vDao.getAllInvoiceListsById(vId);
			invoice = vDao.getInvoiceById(vId);
			request.setAttribute("ivto", invoice);
			request.setAttribute("vList", vList);
			rd = request.getRequestDispatcher("/view/storage/storageInvoiceDetail.jsp");
			rd.forward(request, response);
			break;
			
		case "download" : // CSV 파일 다운로드
			InvoiceController ic = new InvoiceController();
			ic.readCSV(); 
			ic.moveFile();
			response.sendRedirect("/FulfillmentService/control/adminServlet?action=invoiceList&page=1");
	        break;
	        
		case "order" : // 발주(창고 -> 구매처), 발주 상태 : 구매요청, 공급실행, 구매확인요청, 구매확정
			vDao = new InvoiceDAO();
			oProductId = Integer.parseInt(request.getParameter("oProductId"));
			oQuantity = Integer.parseInt(request.getParameter("oQuantity"));
			oPrice = Integer.parseInt(request.getParameter("oPrice"));
			oTotalPrice = Integer.parseInt(request.getParameter("oTotalPrice"));
			
			aDao = new AdminDAO();
			admins = aDao.getOneAdminByName(request.getParameter("oName"));
			pDao = new StorageDAO();
			product = pDao.getOneProductById(oProductId);
		
			// 제품 상태가 P이면 발주 불가능, (재고부족, 재고부족예상 인 경우에 가능)
			if(product.getpState().equals("P")) {
				message = "재고가 많습니다.";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/storage/storageStocktaking.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			order = new OrderDTO(admins.getaId(), oProductId, oQuantity, oPrice, oTotalPrice, vDao.currentTime());
			oDao = new OrderDAO();
			oDao.addOrderProducts(order);
			
			order = oDao.getOneOrderList(admins.getaId(), oProductId);
			message = "발주번호는 " + order.getoId() + " 입니다.";
			request.setAttribute("message", message);
			
			LOG.debug("/view/storage/storageStocktaking.jsp");
			rd = request.getRequestDispatcher("/view/storage/storageStocktaking.jsp");
	        rd.forward(request, response);
			break;
			
		case "releasePage" : // 출고를 위한 페이지
			vDao = new InvoiceDAO();
			// 송장에 있는 수량에 따라 제품 상태를 변경
			List<InvoiceDTO> testList = vDao.getAllInvoiceLists();
			for(InvoiceDTO ivto : testList) {
				vDao.changeProductState(ivto.getvProductId(), ivto.getvQuantity());
			}
			// 변경한 상태와 정보를 화면에 뿌림
			vList = vDao.getInvoiceListsForRelease();
			vDetailList = vDao.getAllInvoiceLists();
			request.setAttribute("vList", vList);
			request.setAttribute("vDetailList", vDetailList);
			rd = request.getRequestDispatcher("/view/storage/storageRelease.jsp");
	        rd.forward(request, response);
			break;
			
		case "release" : // 출고 버튼 클릭 시
			rInvoiceId = Integer.parseInt(request.getParameter("rInvoiceId")); // 송장번호
			rTransportName = request.getParameter("rTransportName"); // 운송회사 이름
			
			rDao = new ReleaseDAO();
			
			if(rDao.isPossibleRelease(pState) == false) {
				message = "출고할 수 없습니다!!!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/view/storage/XXX.jsp");
				rd = request.getRequestDispatcher("/view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			} 
			
			
	        
		default : break;
		}
	}
}
