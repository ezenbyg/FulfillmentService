package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.AdminDAO;
import storage.StorageDAO;
import storage.StorageDTO;

@WebServlet("/control/productServlet")
public class ProductProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ProductProc.class);   
	public ProductProc() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	public void init(ServletConfig config) throws ServletException {  
	    super.init(config);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		int curPage = 1;
		int categoryNum = 0;
		int count = 0;
		int pageNo = 0;
		int pathNum = 0;
		String page = null;
		StorageDAO pDao = null;
		RequestDispatcher rd = null;
		ArrayList<String> pageList = new ArrayList<String>();
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String name = null;
		String title = null;

		switch (action) {
		case "productList" :
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
				page = "&nbsp;<a href=/FulfillmentService/control/productServlet?action=productList&page=" + i +
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
			
		case "category" :
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
				rd = request.getRequestDispatcher("/control/productServlet?action=productList&page=1&categoryNum="+categoryNum+"&name="+name);
			} else if (pathNum == 2) {
				request.setAttribute("title", title);
				rd = request.getRequestDispatcher("/control/productServlet?action=supplierSearch&page=1&categoryNum="+categoryNum);
			}
	        rd.forward(request, response);
			break;
			
		case "stockList" : // ★전체적 수정 5.14
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
					page = "&nbsp;<a href=/FulfillmentService/control/productServlet?action=stockList&page=" + i + ">" + i + "</a>&nbsp;";
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
					page = "&nbsp;<a href=/FulfillmentService/control/productServlet?action=supplierSearch&page=" + i + ">" + i + "</a>&nbsp;";
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
			rd.forward(request, response);
		}
	}
}
