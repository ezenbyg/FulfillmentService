package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import charge.ChargeDAO;
import charge.ChargeDTO;
@WebServlet("/control/chargeServlet")
public class ChargeProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ChargeProc() {
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		ArrayList<ChargeDTO> chargeList;
		ChargeDAO gDao=null;
		RequestDispatcher rd = null;
		String url=null;
		
		switch(action) {
			case "charge" : //청구리스트 set하고  페이지로 넘김
				chargeList=gDao.getAllChargeLists();
				request.setAttribute("chargeList", chargeList);
				url = "/FulfillmentService/WebContent/view/index.jsp"; // 수정  창고관리자 페이지로 이동
				request.setAttribute("url", url);
				rd = request.getRequestDispatcher("alertMsg.jsp");
				rd.forward(request, response);
		
		}
	}
}
