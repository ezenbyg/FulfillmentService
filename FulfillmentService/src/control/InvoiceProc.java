package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import invoice.InvoiceDAO;
import invoice.InvoiceDTO;

@WebServlet("/control/invoiceServlet")
@MultipartConfig
// servlet class에서 request를 javax.servlet.http.Part 타입으로 받을 수 있도록
// @MultipartConfig 어노테이션 추가.
public class InvoiceProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceProc.class);

	public InvoiceProc() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		RequestDispatcher rd = null;
		String action = request.getParameter("action");
		InvoiceDAO iDao;
		InvoiceDTO iDto;
		String message = null;
		switch (action) {
		case "upload": //관리자 페이지 쪽에 업로드 

			/*
			 * <form action="#" method="post" enctype="multipart/form-data"> <input
			 * type="file" name="userFile"><br> <input type="submit" value="전송">
			 * 
			 * </form>
			 */
			// 이런형식으로 action

			int sizeLimit = 1024 * 12024 * 15;
			String savePath = request.getServletContext().getRealPath("Upload");
			System.out.println(savePath);
			MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8",
					new DefaultFileRenamePolicy());
			String fileName = multi.getFilesystemName("fileName");
			String FullPath = savePath + "/" + fileName; // db에 저정하기 위함

			iDao = new InvoiceDAO();
			iDao.uploadInvoice(FullPath);

			rd = request.getRequestDispatcher("xxx.jsp");
			rd.forward(request, response);
			message = "fileUload를 완료하였습니다..";
			request.setAttribute("message", message);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			break;
			
		default:
			message = "fileUload를 실패하였습니다..";
			request.setAttribute("message", message);
			rd = request.getRequestDispatcher("alertMsg.jsp");
			break;
			
		case "download":
			fileName = request.getParameter("filename"); // filename 
			savePath = "Upload";
			ServletContext context = getServletContext();// 업로드 폴더 위치와 업로드 폴더 이름을 알아야함.
			
			String sDownPath = context.getRealPath(savePath);
			System.out.println("다운 위치: " + sDownPath); // 원하는 다운로드 위치 지정해준다. 창고관리자 페이지 쪽으로 위치할 것.
			String sFilePath = sDownPath +"\\" + fileName;
			
			File oFile = new File(sFilePath); // 위 문자열을 파일로 인식.
			
			byte[] b = new byte[5*1024*1024];
			
			FileInputStream in = new FileInputStream(oFile);
			
			String sMineType = getServletContext().getMimeType(sFilePath);// 유형 확인 - 읽어올 경로의 파일의 유형 -> 페이지 생성할 대 타입을 설정해야 한다.
			
			System.out.println("유형: " + sMineType);
			
			// 파일 다운 시작
			response.setContentType(sMineType);
			
			// 업로드 파일의 제목이 깨질 수 있다. URLEncode
			// String sEncoding = new String(fileName.getByte("utf-8"),"8859-1")
			String A = new String(fileName.getBytes("euc-kr"),"8859_1");
			String B = "utf-8";
			String sEncoding = URLEncoder.encode(A,B);
			
			// 기타 내용을 헤더에 올려야함.
			// 기타 내용을 보고 브라우저에서 다운로드 시 화면에 출력 시켜줌
			String AA = "Content-Disposition";
			String BB = "attachment; fimename = " + sEncoding;
			
			// 브라우저에 쓰기
			ServletOutputStream out2 = response.getOutputStream();
			
			int numRead = 0;
			
			// 바이트 배열 b의 0번부터 numRead번가지 브라우저에 출력
			while((numRead = in.read(b,0,b.length))!=-1) {
				out2.write(b,0,numRead);
			}
			out2.flush();
			out2.close();
			in.close();
		}
	}
}
