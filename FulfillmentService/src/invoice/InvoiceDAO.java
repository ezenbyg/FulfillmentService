package invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.AdminDAO;
import admin.AdminDTO;
import util.DBManager;


public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public ArrayList<InvoiceDTO> getAllInvoiceLists() {
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from invoice;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setvId(rs.getInt(1));
				vDto.setvAdminId(rs.getInt(2));
				vDto.setvDate(rs.getString(3));
				vDto.setvState(rs.getString(14));
				vList.add(vDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllInvoiceLists(): Error Code : {}", e.getErrorCode());
			return null;
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vList;
	}
	
	// Invoice
	public void addInvoice(ArrayList<InvoiceDTO> invoiceList) {
		conn = DBManager.getConnection();
		InvoiceDTO vDto = new InvoiceDTO();
		for(int i=0; i<invoiceList.size(); i++) {
			String sql = "insert into invoice(vId, vAdminId, vDate) values(?, ?, ?)";
			vDto = invoiceList.get(i);
			LOG.trace("addInvoice(): " + vDto.toString());
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vDto.getvId());
				pstmt.setInt(2, vDto.getvAdminId());
				pstmt.setString(3, vDto.getvDate());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				LOG.info("addInvoice() Error Code : {}", e.getErrorCode());
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// InvoiceHuman
	public void addInvoiceHuman(ArrayList<InvoiceHumanDTO> humanList) {
		conn = DBManager.getConnection();
		InvoiceHumanDTO hDto = new InvoiceHumanDTO();
		for(int i=0; i<humanList.size(); i++) {
			String sql = "insert into invoicehuman(hInvoiceId, hName, hTel, hAddress, hDate) values(?, ?, ?, ?, ?)";
			hDto = humanList.get(i);
			LOG.trace("addInvoiceHuman(): " + hDto.toString());
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, hDto.gethInvoiceId());
				pstmt.setString(2, hDto.gethName());
				pstmt.setString(3, hDto.gethTel());
				pstmt.setString(4, hDto.gethAddress());
				pstmt.setString(5, hDto.gethDate());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				LOG.info("addInvoiceHuman() Error Code : {}", e.getErrorCode());
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// InvoiceProduct
	public void addInvoiceProduct(ArrayList<InvoiceProductDTO> productList) {
		conn = DBManager.getConnection();
		InvoiceProductDTO ipDto = new InvoiceProductDTO();
		for(int i=0; i<productList.size(); i++) {
			String sql = "insert into invoice(pInvoiceId, ipProductId, ipProductName, ipQuantity) values(?, ?, ?, ?)";
			ipDto = productList.get(i);
			LOG.trace("addInvoiceProduct(): " + ipDto.toString());
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ipDto.getpInvoiceId());
				pstmt.setInt(2, ipDto.getIpProductId());
				pstmt.setString(3, ipDto.getIpProductName());
				pstmt.setInt(2, ipDto.getIpQuantity());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				LOG.info("addInvoiceProduct() Error Code : {}", e.getErrorCode());
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public InvoiceDTO getOneInvoice(int vId) { // 송장 번호에 해당하는 컬럼값 얻어오기
		InvoiceDTO vDto = new InvoiceDTO();
		conn = DBManager.getConnection();
		String sql = "select * from invoice where vId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vDto.setvId(rs.getInt(1));
				vDto.setvAdminId(rs.getInt(2));
				vDto.setvDate(rs.getString(3));
				vDto.setvState(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneInvoice (): Error Code : {}", e.getErrorCode());
			return null;
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vDto;
	}
	
	public void updateInvoiceState(String vState, int vId) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update invoice set vState=? where vId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, vState);
			pStmt.setInt(2, vId);
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateInvoiceState() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 송장 읽고 DB에 넣기
	public void readCSV() {
		ArrayList<InvoiceDTO> invoiceList = new ArrayList<InvoiceDTO>();
		ArrayList<InvoiceHumanDTO> humanList = new ArrayList<InvoiceHumanDTO>();
		ArrayList<InvoiceProductDTO> productList = new ArrayList<InvoiceProductDTO>();
		AdminDAO aDao = new AdminDAO();
		AdminDTO admin = new AdminDTO();
		InvoiceHumanDTO human = new InvoiceHumanDTO();
		InvoiceProductDTO product = new InvoiceProductDTO();
		InvoiceDTO invoice = new InvoiceDTO();
		
		ArrayList<String> fileName = new ArrayList<String>();
		ArrayList<String> filePath = new ArrayList<String>();
		String str = null;
		
		// 해당 디렉터리에 있는 파일 목록 읽기 
		String path = "C:\\Temp\\shop\\";
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		
		int number = 0;
		for(File file : fileList) {
			if(file.isFile()) {
				fileName.add(file.getName());
				filePath.add(path+fileName.get(number));
				
				// InvoiceDTO에 값 세팅하여 List에 저장
				admin = aDao.getOneAdminByName(fileName.get(number).substring(8));
				invoice.setvId(createInvoiceNumber(filePath.get(number)));
				invoice.setvAdminId(admin.getaId());
				invoice.setvDate(fileName.get(number).substring(0, 8));
				invoiceList.add(invoice);
				number++;
			}
		}

		// CSV 파일 읽는 작업
		for(int i=0; i<filePath.size(); i++) {
			try {	
				BufferedReader br = new BufferedReader(new FileReader(filePath.get(i)));
				while(true) {
					if((str=br.readLine()) == null) break;
					StringTokenizer tokens = new StringTokenizer(str,",");
					
					while(tokens.hasMoreElements()) {
						if((tokens.nextToken() != null) || (tokens.nextToken() != "")) {
							for(InvoiceDTO vDto : invoiceList) {
								human.sethInvoiceId(vDto.getvId());
							}
							human.sethName(tokens.nextToken());
							human.sethTel(tokens.nextToken());
							human.sethAddress(tokens.nextToken());
							product.setIpProductId(Integer.parseInt(tokens.nextToken()));
							product.setIpProductName(tokens.nextToken());
							product.setIpQuantity(Integer.parseInt(tokens.nextToken()));
							human.sethDate(tokens.nextToken());
						}
						humanList.add(human);
						productList.add(product);
					}
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// DB에 값 넣기
		addInvoice(invoiceList);
		addInvoiceHuman(humanList);
		addInvoiceProduct(productList);
	}
	
	// 송장 번호 생성
	public int createInvoiceNumber(String fileName) {
		AdminDAO aDao = new AdminDAO();
		AdminDTO admin = new AdminDTO();
		
		String date = fileName.substring(0, 8);
		String name = fileName.substring(8);
		admin = aDao.getOneAdminByName(name);
		String idNum = String.valueOf(admin.getaId()).substring(0, 2);
		int count = (int) (Math.random()+10);
		
		int invoiceNumber = Integer.parseInt(date+idNum)+count;
		
		return invoiceNumber;
	}
	
	// 어제 날짜
	public String yesterday() {
		LocalDateTime yesterday = LocalDateTime.now();
		yesterday = yesterday.minusDays(1);
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
    	return yesterday.format(dtf);
	}
	
	// 현재 시간
	public String currentTime() {
		LocalDateTime cTime = LocalDateTime.now();	
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	return cTime.format(dtf);
	}
}
