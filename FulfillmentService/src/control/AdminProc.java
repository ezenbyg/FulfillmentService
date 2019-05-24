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
import bank.BankDAO;
import bank.BankDTO;
import charge.ChargeDAO;
import charge.ChargeDTO;
import invoice.InvoiceDAO;
import invoice.InvoiceDTO;
import invoice.InvoiceProductDTO;
import order.OrderDAO;
import order.OrderDTO;
import pay.PayDAO;
import pay.PayDTO;
import release.ReleaseDAO;
import release.ReleaseDTO;
import state.AdminName;
import state.ChargeState;
import state.OrderState;
import state.ProductState;
import state.ReleaseState;
import storage.SoldProductDAO;
import storage.SoldProductDTO;
import storage.StorageDAO;
import storage.StorageDTO;
import util.ChargeHandler;
import util.DateHandler;
import util.InvoiceHandler;
import util.OrderHandler;
import util.PayHandler;
import util.ReleaseHandler;

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
		PayDAO yDao = null;
		PayDTO yDto = null;
		OrderDAO oDao = null;
		OrderDTO order = null;
		AdminDAO aDao = null;
		AdminDTO admins = null;
		BankDAO bDao = null;
		BankDTO bank = null;
		InvoiceDTO invoice = null;
		InvoiceDAO vDao = null;
		ReleaseDAO rDao = null;
		ReleaseDTO release = null;
		ChargeDAO gDao = null;
		ChargeDTO charge = null;
		SoldProductDAO spDao = null;
		SoldProductDTO soldProduct = null;
		RequestDispatcher rd = null;
		
		DateHandler dc = null;
		ReleaseHandler rc = null;
		OrderHandler oc = null;
		ChargeHandler cc = null;
		PayHandler pc = null;
		
		String name = null;
		String title = null;
		String page = null;
		String message = null;
		String date = null;
		String oState = null;
		String oBankId = null;
		String soldBankId = null;
		String soldDate = null;
		String soldInvId = null;
		
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
		int aId = 0;
		int oProductId = 0;
		int gAdminId = 0;
		int oAdminId = 0;
		int total = 0;
		int category = 0;
		int gTotalPrice = 0;
		int soldTransportId = 0;
		
		String shopName = null;
		String account = null;
		String gShopName = null;
		String gDate = null;
		String oDate = null;
		String rInvoiceId = null;
		String rState = null;
		String pState = null;
		String vId = null;
		String[] dateFormat = null;
		String action = request.getParameter("action");
		List<ChargeDTO> gList = null;
		List<InvoiceDTO> vList = null;
		List<ReleaseDTO> rList = null;
		List<StorageDTO> pList = null;
		List<OrderDTO> oList = null;
		List<OrderDTO> osList = null;
		List<SoldProductDTO> spList = null;
		List<SoldProductDTO> sList = null;
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
			
		case "category": // ★ 재고 조사
			name = request.getParameter("name");
			categoryNum = Integer.parseInt(request.getParameter("categoryNum")); // 목록에서 category 제공할 것
			System.out.println(categoryNum);
			switch (categoryNum) {
			case AdminDAO.무신사:
				title = "무신사 Store";
				break;
			case AdminDAO.와구와구:
				title = "와구와구 Store";
				break;
			case AdminDAO.하이마트:
				title = "하이마트 Store";
				break;
			case AdminDAO.언더아머:
				title = "언더아머 Store";
				break;
			case AdminDAO.이케아:
				title = "이케아 Store";
				break;
			}

			LOG.debug("130-title" + title);
			pathNum = Integer.parseInt(request.getParameter("pathNum"));
			if (pathNum == 1) {
				request.setAttribute("title", title);
				rd = request.getRequestDispatcher("/control/adminServlet?action=productList&page=1&categoryNum="
						+categoryNum+"&name="+name);
				rd.forward(request, response);
			} else if (pathNum == 2) {
				request.setAttribute("title", title);
				rd = request.getRequestDispatcher(
						"/control/adminServlet?action=supplierSearch&page=1&categoryNum="+categoryNum);
				LOG.debug("/control/adminServlet?action=supplierSearch&page=1&categoryNum="+categoryNum);
				rd.forward(request, response);
			} else if (pathNum == 3) {
				String compareId = request.getParameter("compareId");
				request.setAttribute("title", title);
				rd = request.getRequestDispatcher(
						"/control/adminServlet?action=stockList&page=1&categoryNum="+categoryNum);
				rd.forward(request, response);
			}
			break;

		case "stockList": // ★ 재고 조사
			LOG.debug("535-여기");
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
					page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=stockList&page=" + i + ">" + i
							+ "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);

			int compareId = Integer.parseInt(request.getParameter("compareId"));
			LOG.debug("175-compareId: " + compareId);
			if (compareId == 1) {
				ArrayList<StorageDTO> stockList = pDao.getAllProducts();
				request.setAttribute("stockList", stockList);
			} else if (compareId == 2) {
				categoryNum = Integer.parseInt(request.getParameter("categoryNum"));
				ArrayList<StorageDTO> stockList = pDao.getTitleProducts(categoryNum, curPage);
				request.setAttribute("stockList", stockList);
			}

			request.setAttribute("pageList", pageList);
			LOG.debug("574-/view/storage/storageStocktaking.jsp");
			rd = request.getRequestDispatcher("/view/storage/storageStocktaking.jsp");
			rd.forward(request, response);
			break;

		case "search": // 상품 이름 일부 검색 리스트 출력 // ★ 재고 조사
			String word = request.getParameter("searchWord");
			LOG.debug("653-word: " + word);
			pDao = new StorageDAO();
			ArrayList<StorageDTO> stockList = pDao.getSearchProduct(word);
			request.setAttribute("stockList", stockList);

			rd = request.getRequestDispatcher("/view/storage/storageStocktaking.jsp");
			rd.forward(request, response);
			break;
		case "supplierSearch": // 구매처별 리스트 출력 // ★ 재고 조사
			pDao = new StorageDAO();
			categoryNum = Integer.parseInt(request.getParameter("categoryNum"));
			title = (String) request.getAttribute("title");
			LOG.debug("categoryNum:" + categoryNum + "  title : " + title);
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			pDao = new StorageDAO();
			LOG.debug(title);
			LOG.debug("categoryNum: " + String.valueOf(categoryNum));
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
					page = "&nbsp;<a href=/FulfillmentService/control/productServlet?action=supplierSearch&page=" + i
							+ ">" + i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);

			ArrayList<StorageDTO> supplierSearchList = pDao.getTitleProducts(categoryNum, curPage);
			request.setAttribute("supplierSearchList", supplierSearchList);
			request.setAttribute("pageList", pageList);
			LOG.debug("/view/storage/storageStocktaking.jsp");
			rd = request.getRequestDispatcher("/view/storage/storageStocktaking.jsp");
			rd.forward(request, response);
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
			InvoiceHandler ic = new InvoiceHandler();
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
			oc = new OrderHandler();
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
			dc = new DateHandler();
			
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
				dc = new DateHandler();
				date = dc.getToday();
			}
			rd = request.getRequestDispatcher("/control/adminServlet?action=releasePage&date="+date);
	        rd.forward(request, response);
			break;
			
		case "release" : // 출고 버튼 클릭 시
			dc = new DateHandler();
			rc = new ReleaseHandler();
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
			
			dc = new DateHandler();
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
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=transportHistory&page=" + i + ">" + i + "</a>&nbsp;";
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
			rd = request.getRequestDispatcher("/view/storage/storageTransportHistory.jsp");
	        rd.forward(request, response);
			break; 
			
		case "orderHistory" : // 월 단위 발주 내역 조회
			dc = new DateHandler();
			
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
			
			oList = oDao.selectJoinAll(curPage, date);
			
			for (OrderDTO oDto: oList)
				LOG.debug("ODTO : " + oDto.toString());
			
			request.setAttribute("orderList", oList);
			request.setAttribute("orderPageList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storageOrderHistory.jsp");
	        rd.forward(request, response);
			break; 
			
		case "purchaseConfirm" : // 구매 확정 버튼 클릭 시
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
			
			dc = new DateHandler();
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
			
		case "chargePage" : // 청구를 위한 페이지
			dc = new DateHandler();
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthCharge") != null) {
				date = request.getParameter("monthCharge"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			spDao = new SoldProductDAO();
			LOG.debug("gDao.getCount() : " + spDao.getCount()); 
			count = spDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("chargePageList", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=chargePage&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			spList = spDao.selectAllForCharge(curPage, date.substring(0, 7));
			
			for (SoldProductDTO spDto: spList)
				LOG.debug("SPDTO : " + spDto.toString());
			
			request.setAttribute("spList", spList);
			request.setAttribute("chargePageList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storageCharge.jsp");
	        rd.forward(request, response);
			break; 
			
		case "charge" : // 청구 버튼 클릭 시
			spDao = new SoldProductDAO();
			cc = new ChargeHandler();
			spList = spDao.selectAllLists();
			for(SoldProductDTO spDto : spList) {
				cc.processRequestCharge(spDto.getSoldShopId(), spDto.getSoldDate());
			}
			rd = request.getRequestDispatcher("/control/adminServlet?action=orderHistory&page=1");
	        rd.forward(request, response);
			break;
			
		case "monthSales" : // 월 단위 판매내역 조회
			dc = new DateHandler();
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthSoldSales") != null) {
				date = request.getParameter("monthSoldSales"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			spDao = new SoldProductDAO();
			LOG.debug("gDao.getCount() : " + spDao.getCount()); 
			count = spDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("monthSalesList", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=monthSales&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			spList = spDao.selectAllSoldLists(curPage, date.substring(0, 7));
			
			for (SoldProductDTO spDto: spList)
				LOG.debug("SPDTO : " + spDto.toString());
			
			request.setAttribute("spList", spList);
			request.setAttribute("monthSalesList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storageSalesHistory.jsp");
	        rd.forward(request, response);
			break; 
			
		case "chargeHistory" : // 월 단위 청구 내역 조회 
			dc = new DateHandler();

			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthChargeList") != null) {
				date = request.getParameter("monthChargeList"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			gDao = new ChargeDAO();
			LOG.debug("gDao.getCount() : " + gDao.getCount()); 
			count = gDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("chargeHistoryPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=chargeHistory&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			gList = gDao.selectJoinAllLists(curPage, date);
			for (ChargeDTO gDto: gList)
				LOG.debug("GDTO : " + gDto.toString());
			
			request.setAttribute("gList", gList);
			request.setAttribute("chargeHistoryPageList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storageChargeHistory.jsp");
	        rd.forward(request, response);
			break;
			
		case "supplierPayPage" : // 구매처에게 지급을 하기 위한 페이지
			dc = new DateHandler();
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthPaySupplier") != null) {
				date = request.getParameter("monthPaySupplier"); 
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
			session.setAttribute("supplierPayPageList", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=supplierPayPage&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			oList = oDao.selectJoinAllbyState(curPage, date.substring(0, 7));
			osList = oDao.getPayForSupplier(date.substring(0, 7));
			
			for (OrderDTO oDto: oList)
				LOG.debug("ODTO : " + oDto.toString());
			
			request.setAttribute("orderList", oList);
			request.setAttribute("osList", osList);
			request.setAttribute("orderPageList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storagePay_S.jsp");
	        rd.forward(request, response);
			break; 
			
		case "payForSupplier" : // 구매처에서 지급 버튼 클릭 시
			pc = new PayHandler();
			oAdminId = Integer.parseInt(request.getParameter("oAdminId"));
			oBankId = request.getParameter("oBankId");
			total = Integer.parseInt(request.getParameter("total"));
			oDate = request.getParameter("oDate");
			pc.processPayForSupplier(oBankId, oAdminId, total, oDate);
			rd = request.getRequestDispatcher("/control/adminServlet?action=supplierPayPage&page=1");
	        rd.forward(request, response);
			break;
			
		case "transportPayPage" : // 운송회사에게 지급을 하기 위한 페이지
			dc = new DateHandler();
			
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.debug("curPage : " + curPage);
			}
			
			// 네비에서 타고올때는 초기값이 null
			if(request.getParameter("monthPayTransport") != null) {
				date = request.getParameter("monthPayTransport"); 
				LOG.debug(String.valueOf(date.length()));
				if(date.length() > 10) {
					dateFormat = date.split(" ");
					date = dateFormat[0];
					LOG.debug(date);
				}
			} else date = dc.getToday();
			
			spDao = new SoldProductDAO();
			LOG.debug("oDao.getCount() : " + spDao.getCount()); 
			count = spDao.getCount();
			if (count == 0)			// 데이터가 없을 때 대비
				count = 1;
			pageNo = (int)Math.ceil(count/10.0);
			if (curPage > pageNo)	// 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("transportPayPageList", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i=1; i<=pageNo; i++) {
				page = "&nbsp;<a href=/FulfillmentService/control/adminServlet?action=transportPayPage&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
				LOG.trace("");
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			spList = spDao.selectAllForPay(curPage, date.substring(0, 7));
			sList = spDao.getPayForTransport(date.substring(0, 7));
			for (SoldProductDTO spDto: spList)
				LOG.debug("SPDTO : " + spDto.toString());
			
			request.setAttribute("spList", spList);
			request.setAttribute("sList", sList);
			request.setAttribute("transportPayPageList", pageList);
			rd = request.getRequestDispatcher("/view/storage/storagePay_T.jsp");
	        rd.forward(request, response);
			break; 
			
		case "payForTransport" : // 운송회사에서 지급 버튼 클릭 시
			pc = new PayHandler();
			soldTransportId = Integer.parseInt(request.getParameter("soldTransportId"));
			soldBankId = request.getParameter("soldBankId");
			total = Integer.parseInt(request.getParameter("total"));
			soldDate = request.getParameter("soldDate");
			soldInvId = request.getParameter("soldInvId");
			pc.processPayForTransport(soldBankId, soldTransportId, total, soldDate, soldInvId);
			rd = request.getRequestDispatcher("/control/adminServlet?action=transportPayPage&page=1");
	        rd.forward(request, response);
			break;
			
		default : break;
		}
	}
}
