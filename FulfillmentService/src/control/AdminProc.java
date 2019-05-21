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
import state.OrderState;
import state.ProductState;
import state.ReleaseState;
import storage.SoldProductDAO;
import storage.SoldProductDTO;
import storage.StorageDAO;
import storage.StorageDTO;
import util.DateController;
import util.InvoiceController;
import util.OrderController;
import util.ReleaseController;

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
		SoldProductDAO spDao = null;
		SoldProductDTO soldProduct = null;
		RequestDispatcher rd = null;
		
		DateController dc = null;
		ReleaseController rc = null;
		OrderController oc = null;
		
		String name = null;
		String title = null;
		String page = null;
		String message = null;
		String date = null;
		String oState = null;
		
		int curPage = 1;
		int categoryNum = 0;
		int count = 0;
		int pageNo = 0;
		int pathNum = 0;
		int pId = 0;
		int pSupplierId = 0;
		int oQuantity = 0;
		int pPrice = 0;
		int oTotalPrice = 0;
		int oId = 0;
		int oProductId = 0;
	
		String rInvoiceId = null;
		String rState = null;
		String pState = null;
		String vId = null;
		String[] dateFormat = null;
		String action = request.getParameter("action");
		List<InvoiceDTO> vList = null;
		List<InvoiceProductDTO> ipList = null;
		List<ReleaseDTO> rList = null;
		List<StorageDTO> pList = null;
		List<OrderDTO> oList = null;
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
			count = pDao.getCount(); // 모든 리스트 카운트.
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
			
			pId = Integer.parseInt(request.getParameter("pId"));
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
			// LOG.debug("vDao.getCount() : " + vDao.getCount()); 
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
	        
		case "orderPage" : // 발주를 위한 페이지
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			// 드롭다운으로 상태 선택
			if(request.getParameter("state") != null) {
				pState = request.getParameter("state");
			} else pState = "P";
			
			LOG.debug(pState);
			
			pDao = new StorageDAO();
			// LOG.debug("vDao.getCount() : " + vDao.getCount()); 
			count = pDao.getCountByState(pState);
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentOrderPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=orderPage&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			pList = pDao.getAllProductsForOrder(curPage, pState);
			for (StorageDTO pDto: pList) 
				LOG.debug("pDto : " + pDto.toString());

			request.setAttribute("pList", pList);
			request.setAttribute("orderPageList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storageOrder.jsp");
	        rd.forward(request, response);
	        break;
	        
		case "order" : // 발주 버튼 클릭 시
			oc = new OrderController();
			vDao = new InvoiceDAO();
			oQuantity = Integer.parseInt(request.getParameter("oQuantity"));
			pId = Integer.parseInt(request.getParameter("pId"));
			pPrice = Integer.parseInt(request.getParameter("pPrice"));
			pSupplierId = Integer.parseInt(request.getParameter("pSupplierId"));
			pState = request.getParameter("pState");
			oTotalPrice = oQuantity * pPrice;
			if(pState.equals("P")) { 
				message = "제품 수량이 충분 합니다!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/FulfillmentService/control/adminServlet?action=orderPage&page=1");
				rd = request.getRequestDispatcher("../view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			oc.processOrder(pSupplierId, pId, oQuantity, oTotalPrice, pState);
			rd = request.getRequestDispatcher("/control/adminServlet?action=orderPage&page=1&state="+pState);
	        rd.forward(request, response);
			break;
			
		case "releasePage" : // 출고를 위한 페이지
			vDao = new InvoiceDAO();
			dc = new DateController();
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("date") != null) {
				date = request.getParameter("date"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
		
			vList = vDao.getInvoiceListsForRelease(date); // 일별 출력
	
			// 송장에 있는 수량에 따라 제품 상태를 변경
			List<InvoiceDTO> testList = vDao.getAllInvoiceLists();
			for(InvoiceDTO ivto : testList) {
				vDao.changeProductState(ivto.getvProductId(), ivto.getvQuantity());
			}
			
			// 송장번호에 해당하는 제품 출력
			if (request.getParameter("vId") != null) {
				vId = request.getParameter("vId");
			} 
			List<InvoiceDTO> invList = vDao.getAllInvoiceListsById(vId);
			
			request.setAttribute("invList", invList);
			request.setAttribute("vList", vList);
			rd = request.getRequestDispatcher("/view/storage/storageRelease.jsp");
	        rd.forward(request, response);
			break;
			
		case "invoiceDaily" : // 출고 페이지에서 송장 내역을 일별로 정리
			date = request.getParameter("dateInvoice");
			if (date == null) {
				dc = new DateController();
				date = dc.getToday();
			}
			rd = request.getRequestDispatcher("/control/adminServlet?action=releasePage&date="+date);
	        rd.forward(request, response);
			break;
			
		case "release" : // 출고 버튼 클릭 시
			dc = new DateController();
			rc = new ReleaseController();
			rc.processRelease(dc.getToday());
			rd = request.getRequestDispatcher("/control/adminServlet?action=releasePage&date="+dc.getToday());
	        rd.forward(request, response);
			break;
			
		case "completeDelivery" : // 배송확정 버튼 클릭 시
			rState = request.getParameter("rState");
			rInvoiceId = request.getParameter("rInvoiceId");
			if(!(rState.equals(String.valueOf(ReleaseState.배송확인요청)))) {
				message = "아직 배송확정을 누를 수 없습니다!!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/FulfillmentService/control/adminServlet?action=transportHistory&page=1");
				rd = request.getRequestDispatcher("../view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			dc = new DateController();
			rDao = new ReleaseDAO();
			rDao.updateReleaseState(String.valueOf(ReleaseState.배송확정), rInvoiceId);
			
			// soldProduct 테이블에 판매된 정보를 삽입
			vDao = new InvoiceDAO();
			pDao = new StorageDAO();
			spDao = new SoldProductDAO();
			product = new StorageDTO();
			vList = vDao.getAllInvoiceListsById(rInvoiceId);
			for(InvoiceDTO ivto : vList) {
				soldProduct = new SoldProductDTO(rInvoiceId, ivto.getvAdminId(), ivto.getVlogisId(), 
						ivto.getvProductId(), ivto.getvQuantity(), ivto.getvPrice(), dc.currentTime());
				spDao.addSoldProducts(soldProduct);
			}
			
			rd = request.getRequestDispatcher("/control/adminServlet?action=transportHistory&page=1");
	        rd.forward(request, response);
			break;
			
		case "transportHistory" : // 운송내역조회 페이지
			dc = new DateController();
		
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("dateRelease") != null) {
				date = request.getParameter("dateRelease"); 
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
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=transportHistory&page=" + i + ">" + i + "</a>&nbsp;";
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
			rd = request.getRequestDispatcher("/view/storage/storageTransportHistory.jsp");
	        rd.forward(request, response);
			break; 
			
		case "orderHistory" : // 월 단위 발주 내역 조회
			dc = new DateController();
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthOrder") != null) {
				date = request.getParameter("monthOrder"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			oDao = new OrderDAO();
			LOG.debug("oDao.getCount() : " + oDao.getCount()); 
			count = oDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("orderHistoryPageList", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=orderHistory&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			oList = oDao.selectJoinAllbyId(curPage, date);
			
			for (OrderDTO oDto: oList)
				LOG.debug("ODTO : " + oDto.toString());
			
			request.setAttribute("orderList", oList);
			request.setAttribute("orderPageList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storageOrderHistory.jsp");
	        rd.forward(request, response);
			break; 
			
		case "purchaseConfirm" : 
			oState = request.getParameter("oState");
			oId = Integer.parseInt(request.getParameter("oId"));
			oProductId = Integer.parseInt(request.getParameter("oProductId"));
			oQuantity = Integer.parseInt(request.getParameter("oQuantity"));
			
			if(!(oState.equals(String.valueOf(OrderState.구매확인요청)))) {
				message = "아직 구매확정을 누를 수 없습니다!!";
				request.setAttribute("message", message);
				request.setAttribute("url", "/FulfillmentService/control/adminServlet?action=orderHistory&page=1");
				rd = request.getRequestDispatcher("../view/alertMsg.jsp");
				rd.forward(request, response);
				break;
			}
			
			dc = new DateController();
			oDao = new OrderDAO();
			oDao.updateOrderState(String.valueOf(OrderState.구매확정), oId, dc.currentTime());
			
			// storage 테이블 업데이트(재고수량, 재고상태)
			pDao = new StorageDAO();
			product = new StorageDTO();
			product = pDao.getOneProductById(oProductId);
			pDao.updateStorage(oQuantity+product.getpQuantity(), String.valueOf(ProductState.P), oProductId);
			
			rd = request.getRequestDispatcher("/control/adminServlet?action=orderHistory&page=1");
	        rd.forward(request, response);
			break;

		default : break;
		}
	}
}
