package invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
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
				vDto.setvId(rs.getString(1));
				vDto.setvAdminId(rs.getInt(2));
				vDto.setvShopName(rs.getString(3));
				vDto.setvName(rs.getString(4));
				vDto.setvTel(rs.getString(5));
				vDto.setvAddress(rs.getString(6));
				vDto.setvDate(rs.getString(7));
				vDto.setvState(rs.getString(8));
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
			String sql = "insert into invoice(vId, vAdminId, vShopName, vName, vTel, vAddress, vDate) values(?, ?, ?, ?, ?, ?, ?)";
			vDto = invoiceList.get(i);
			LOG.trace("addInvoice(): " + vDto.toString());
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vDto.getvId());
				pstmt.setInt(2, vDto.getvAdminId());
				pstmt.setString(3, vDto.getvShopName());
				pstmt.setString(4, vDto.getvName());
				pstmt.setString(5, vDto.getvTel());
				pstmt.setString(6, vDto.getvAddress());
				pstmt.setString(7, vDto.getvDate());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				LOG.info("addInvoice() Error Code : {}", e.getErrorCode());
			} finally {
				try {
					pstmt.close();
					//conn.close();
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
			String sql = "insert into invoiceproduct(pInvoiceId, ipProductId, ipProductName, ipQuantity, ipDate) values(?, ?, ?, ?, ?)";
			ipDto = productList.get(i);
			LOG.trace("addInvoiceProduct(): " + ipDto.toString());
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, ipDto.getpInvoiceId());
				pstmt.setInt(2, ipDto.getIpProductId());
				pstmt.setString(3, ipDto.getIpProductName());
				pstmt.setInt(4, ipDto.getIpQuantity());
				pstmt.setString(5, ipDto.getIpDate());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				LOG.info("addInvoiceProduct() Error Code : {}", e.getErrorCode());
			} finally {
				try {
					pstmt.close();
					//conn.close();
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
				vDto.setvId(rs.getString(1));
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
		conn = DBManager.getConnection();
		ArrayList<TempDTO> tempList = new ArrayList<TempDTO>();
		ArrayList<InvoiceDTO> invoiceList = new ArrayList<InvoiceDTO>();
		ArrayList<InvoiceProductDTO> productList = new ArrayList<InvoiceProductDTO>();
		AdminDAO aDao = new AdminDAO();
		AdminDTO admin = new AdminDTO();
		InvoiceProductDTO product = new InvoiceProductDTO();
		InvoiceDTO invoice = new InvoiceDTO();
		TempDTO temp = new TempDTO();
		int i = 0;
		
		ArrayList<String> fullFileName = new ArrayList<String>();
		ArrayList<String> splitFileName = new ArrayList<String>();
		ArrayList<String> filePath = new ArrayList<String>();
		
		// 해당 디렉터리에 있는 파일 목록 읽기 
		String path = "C:\\Temp\\shop\\";
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		
		int number = 0;
		for(File file : fileList) {
			if(file.isFile()) {
				fullFileName.add(file.getName());
				filePath.add(path+fullFileName.get(number));
				
				LOG.debug(filePath.get(number));
				LOG.debug(fullFileName.get(number));
				
				StringTokenizer st = new StringTokenizer(fullFileName.get(number), ".");
				splitFileName.add(st.nextToken()); 
				LOG.debug(splitFileName.get(number));
				admin = aDao.getOneAdminByName(splitFileName.get(number).substring(8));
				LOG.debug(splitFileName.get(number).substring(8));
				
				// TempDTO에 값 세팅하여 List에 저장
				temp.settId(createInvoiceNumber(splitFileName.get(number)));
				temp.settAdminId(admin.getaId());
				temp.settShopName(splitFileName.get(number).substring(8));
				temp.settDate(splitFileName.get(number).substring(0, 8));
				tempList.add(temp);
				number++;
			}
		}
		
		try {
			for(TempDTO tDto : tempList) { 
				invoice.setvId(tDto.gettId());
				LOG.debug("Id : " + String.valueOf(tDto.gettId()));
				invoice.setvAdminId(tDto.gettAdminId());
				LOG.debug("AdminId : " + String.valueOf(tDto.gettAdminId()));
				invoice.setvShopName(tDto.gettShopName());
				LOG.debug("ShopName : " + tDto.gettShopName());
				invoice.setvDate(tDto.gettDate());
				LOG.debug("Date : " + tDto.gettDate());
				
				BufferedReader br = new BufferedReader(new InputStreamReader(
	                    new FileInputStream(filePath.get(i)), "euc-kr"));
	            String line = "";
	 
	            while ((line = br.readLine()) != null) {
	                String[] token = line.split(",");
	                for(int p=0; p<7; p++) {
	                	if((token[p] != null) && !(token[p].equals(" ")) && !(token[p].equals(""))) {
	                		LOG.debug(token[p] + " ");
	                		System.out.println(token[p] + " ");
							if(p==0)invoice.setvName(token[p]);
							if(p==1)invoice.setvTel(token[p]);
							if(p==2)invoice.setvAddress(token[p]);
							product.setpInvoiceId(tDto.gettId());
							if(p==3)product.setIpProductId(Integer.parseInt(token[p]));
							if(p==4)product.setIpProductName(token[p]);
							if(p==5)product.setIpQuantity(Integer.parseInt(token[p]));
							if(p==6)product.setIpDate(token[p]);
	                	}
						System.out.println("");
	                }
                	invoiceList.add(invoice);
					productList.add(product);
	            }
	            br.close();
	        } 
		}
        catch (Exception e) {
            e.printStackTrace();
        } 
		addInvoice(invoiceList);
		addInvoiceProduct(productList);
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// 송장 번호 생성
	public String createInvoiceNumber(String fileName) {
		AdminDAO aDao = new AdminDAO();
		AdminDTO admin = new AdminDTO();
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		InvoiceDAO vDao = new InvoiceDAO();
		String date = fileName.substring(0, 8);
		String name = fileName.substring(8);
		admin = aDao.getOneAdminByName(name);
		String idNum = String.valueOf(admin.getaId()).substring(0, 2);
		String count = String.valueOf((int)((Math.random()*89)+10));
		String invoiceNumber = date + idNum + count;
		vList = vDao.getAllInvoiceLists();
		boolean complete = true;
		
		// 중복 방지
		while(complete) {
			for(InvoiceDTO invoice : vList) {
				if(invoice.getvId().equals(invoiceNumber)) {
					invoiceNumber = date + idNum + String.valueOf((int)((Math.random()*89)+10));
				} else if(!invoice.getvId().equals(invoiceNumber)) {
					complete = false;
					break;
				}
			}
		}
		LOG.debug(String.valueOf(invoiceNumber));
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
	
	public ArrayList<InvoiceDTO> selectJoinAll(int page) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select vId, vShopName, vName, vTel, vAddress, vDate, vState "
					+ "from invoice " 
					+ "order by vDate desc;";
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select vId, vShopName, vName, vTel, vAddress, vDate, vState "
					+ "from invoice " 
					+ "order by vDate desc limit ?, 10;";
			offset = (page - 1) * 10;
		}
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page != 0) {
				pstmt.setInt(1, offset);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setvId(rs.getString(1));
				vDto.setvShopName(rs.getString(2));
				vDto.setvName(rs.getString(3));
				vDto.setvTel(rs.getString(4));
				vDto.setvAddress(rs.getString(5));
				vDto.setvDate(rs.getString(6));
				vDto.setvState(rs.getString(7));
				vList.add(vDto);
				LOG.trace(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectJoinAll(): Error Code : {}", e.getErrorCode());
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
	
	public ArrayList<InvoiceDTO> selectJoinAllbyId(int page, int vAdminId) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select vId, vShopName, vName, vTel, vAddress, vDate, vState "
					+ "from invoice " 
					+ "where vAdminId=? "
					+ "order by vDate desc;";
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select vId, vShopName, vName, vTel, vAddress, vDate, vState "
					+ "from invoice " 
					+ "where vAdminId=? "
					+ " order by id desc limit ?, 10;";  
			offset = (page - 1) * 10;
		}
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page == 0) {
				pstmt.setInt(1, vAdminId);
			} else if(page != 0) {
				pstmt.setInt(1, vAdminId);
				pstmt.setInt(2, offset);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setvId(rs.getString(1));
				vDto.setvShopName(rs.getString(2));
				vDto.setvName(rs.getString(3));
				vDto.setvTel(rs.getString(4));
				vDto.setvAddress(rs.getString(5));
				vDto.setvDate(rs.getString(6));
				vDto.setvState(rs.getString(7));
				vList.add(vDto);
				LOG.trace(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectJoinAllbyId(): Error Code : {}", e.getErrorCode());
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
	
    public int getCount() {
    	conn = DBManager.getConnection();
		String sql = "select count(*) from invoice;";
		PreparedStatement pStmt = null;
		int count = 0;
		try {
			pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			LOG.trace(sql);
			while (rs.next()) {				
				count = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getCount(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
}
