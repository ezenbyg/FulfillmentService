package control;

import java.io.IOException;
import java.util.ArrayList;

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

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-9");

		int curPage = 1;
		int category = 0;
		int count = 0;
		int pageNo = 0;
		String page = null;
		StorageDAO pDao = null;
		RequestDispatcher rd = null;
		ArrayList<String> pageList = new ArrayList<String>();
		HttpSession session = request.getSession();
		String action = request.getParameter("action");

		switch (action) {
		case "productList" :
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
				LOG.trace("");
			}
			pDao = new StorageDAO();
			count = pDao.getCount();
			if (count == 0) // 데이터가 없을 때 대비
				count = 1;
			pageNo = (int) Math.ceil(count / 10.0);
			if (curPage > pageNo) // 경계선에 걸렸을 때 대비
				curPage--;
			session.setAttribute("currentProductPage", curPage);
			// 리스트 페이지의 하단 페이지 데이터 만들어 주기
			
			page = "<a href=#>&laquo;</a>&nbsp;";
			pageList.add(page);
			for (int i = 1; i <= pageNo; i++) {
				if (curPage == i)
					page = "&nbsp;" + i + "&nbsp;";
				else
					page = "&nbsp;<a href=/control/productServlet?action=productList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);

			category = Integer.parseInt(request.getParameter("category"));
			ArrayList<StorageDTO> storageList = pDao.getTitleProducts(category,curPage);
			// 해당 구매처의 내용만 출력과 함께 페이지 출력을 위해 parameter값을 카테고리, 현재페이지를 줌
			request.setAttribute("storageList", storageList);
			request.setAttribute("pageList", pageList);
			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
			break;
			
		case "category" :
			category = Integer.parseInt(request.getParameter("category")); // 목록에서 category 제공할 것
			String title = null;
			
			switch (category) {
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
			request.setAttribute("title", title);
			rd = request.getRequestDispatcher("/control/productServlet?action=productList&page=1");
			rd.forward(request, response);
			
		case "stockList" :
			if (!request.getParameter("page").equals("")) {
				curPage = Integer.parseInt(request.getParameter("page"));
			}
			pDao = new StorageDAO();
			count = pDao.getCount();
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
					page = "&nbsp;<a href=/control/productServlet?action=stockList&page=" + i + ">" + i + "</a>&nbsp;";
				pageList.add(page);
			}
			page = "&nbsp;<a href=#>&raquo;</a>";
			pageList.add(page);
			
			ArrayList<StorageDTO> stockList = pDao.getAllProducts();
			request.setAttribute("stockList", stockList);
			request.setAttribute("pageList", pageList);

			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
			break;
			
		case "search" :
			String word = request.getParameter("searchWord");
			pDao = new StorageDAO();
			ArrayList<StorageDTO> searchList = pDao.getSearchProduct(word);
			request.setAttribute("searchList", searchList);
			
			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
		}
	}
}
