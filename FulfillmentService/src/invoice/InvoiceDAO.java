package invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import storage.StorageDAO;
import storage.StorageDTO;
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
				vDto.setvPrice(rs.getInt(8));
				vDto.setvState(rs.getString(9));
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
	public void addInvoice(InvoiceDTO invoice) {
		conn = DBManager.getConnection();
		String sql = "insert into invoice(vId, vAdminId, vShopName, vName, vTel, vAddress, vDate, vPrice) values(?, ?, ?, ?, ?, ?, ?, ?)";
		LOG.trace("addInvoice(): " + invoice.toString());
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, invoice.getvId());
			pstmt.setInt(2, invoice.getvAdminId());
			pstmt.setString(3, invoice.getvShopName());
			pstmt.setString(4, invoice.getvName());
			pstmt.setString(5, invoice.getvTel());
			pstmt.setString(6, invoice.getvAddress());
			pstmt.setString(7, invoice.getvDate());
			pstmt.setInt(8, invoice.getvPrice());
			
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
	
	// InvoiceProduct
	public void addInvoiceProduct(InvoiceProductDTO product) {
		conn = DBManager.getConnection();
		String sql = "insert into invoiceproduct(pInvoiceId, ipProductId, ipQuantity) values(?, ?, ?)";
		LOG.trace("addInvoiceProduct(): " + product.toString());
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getpInvoiceId());
			pstmt.setInt(2, product.getIpProductId());
			pstmt.setInt(3, product.getIpQuantity());
			
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
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
		ArrayList<InvoiceDTO> invoiceList = new ArrayList<InvoiceDTO>();
		ArrayList<InvoiceProductDTO> productList = new ArrayList<InvoiceProductDTO>();
		ArrayList<String> fullFileName = new ArrayList<String>();
		ArrayList<String> splitFileName = new ArrayList<String>();
		ArrayList<String> filePath = new ArrayList<String>();
		StorageDAO pDao = new StorageDAO();
		StorageDTO pDto = new StorageDTO();
		AdminDAO aDao = new AdminDAO();
		AdminDTO admin = new AdminDTO();
		InvoiceDTO invoice = new InvoiceDTO();
		
		// 해당 디렉터리에 있는 파일 목록 읽기 
		String path = "C:\\Temp\\shop\\";
		File dir = new File(path);
		File[] fileList = dir.listFiles();

		int number = 0; 
		for(File file : fileList) {
			if(file.isFile()) {
				fullFileName.add(file.getName());
				filePath.add(path+fullFileName.get(number));
				StringTokenizer st = new StringTokenizer(fullFileName.get(number), ".");
				splitFileName.add(st.nextToken()); 
				admin = aDao.getOneAdminByName(splitFileName.get(number).substring(12));
				LOG.debug(splitFileName.get(number).substring(0, 12));
				LOG.debug(splitFileName.get(number));
				invoice.setvId(createInvoiceNumber(splitFileName.get(number)));
				LOG.debug(createInvoiceNumber(splitFileName.get(number)));
				invoice.setvAdminId(admin.getaId());
				invoice.setvShopName(splitFileName.get(number).substring(12));
				String year = splitFileName.get(number).substring(0, 4);
				String month = splitFileName.get(number).substring(4, 6);
				String date = splitFileName.get(number).substring(6, 8);
				String hour = splitFileName.get(number).substring(8, 10);
				String minute = splitFileName.get(number).substring(10, 12);
				LOG.debug(year+"-"+month+"-"+date+" "+hour+":"+minute);
				invoice.setvDate(year+"-"+month+"-"+date+" "+hour+":"+minute);
				LOG.debug(splitFileName.get(number).substring(0, 12));
			}
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
	                    new FileInputStream(filePath.get(number)), "euc-kr"));
	            String line = "";
	            int count = 0;
	            
	            while ((line = br.readLine()) != null) { // 파일 읽기
	            	count++;
	            	InvoiceProductDTO product = new InvoiceProductDTO();
	            	product.setpInvoiceId(invoice.getvId());
	                String[] token = line.split(",", -1);
	                for(int p=0; p<5; p++) {
	                	if((token[p] != null) && !(token[p].equals(" ")) && !(token[p].equals(""))) {
	                		LOG.debug(token[p] + " ");
	                		System.out.println(token[p] + " ");
							if(p==0)invoice.setvName(token[p]);
							if(p==1)invoice.setvTel(token[p]);
							if(p==2)invoice.setvAddress(token[p]);
							if(p==3) {
								product.setIpProductId(Integer.parseInt(token[p]));
								pDto = pDao.getOneProductById(Integer.parseInt(token[p]));
								invoice.setvPrice(pDto.getpPrice());
							}
							if(p==4)product.setIpQuantity(Integer.parseInt(token[p]));
	                	}
	                }
	                if(count==1) addInvoice(invoice);
	                addInvoiceProduct(product);
	            }
				moveDirectory(); // 읽은 파일들을 폴더 이동 시킨다.
				number++;
	            br.close();
			}
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public void moveDirectory() {
		File folder1 = new File("C:\\Temp\\complete");
		File folder2 = new File("C:\\Temp\\shop");
		InvoiceDAO.copy(folder1, folder2);
		InvoiceDAO.delete(folder1.toString());
	}
	
	public static void copy(File sourceF, File targetF){
		File[] target_file = sourceF.listFiles();
		for (File file : target_file) {
			File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
			if(file.isDirectory()){
				temp.mkdir();
				copy(file, temp);
			} else {
			        FileInputStream fis = null;
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(temp) ;
					byte[] b = new byte[4096];
					int cnt = 0;
					while((cnt=fis.read(b)) != -1){
						fos.write(b, 0, cnt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					try {
						fis.close();
						fos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
						
				}
			}
		   }
	    }
		
    public static void delete(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()){
			    File[] folder_list = folder.listFiles();
					
			    for (int i = 0; i < folder_list.length; i++) {
				if(folder_list[i].isFile()) {
					folder_list[i].delete();
				}else {
					delete(folder_list[i].getPath());
				}
				folder_list[i].delete();
			    }
			}
		} catch (Exception e) {
			e.getStackTrace();
		}  
	}
	
	// 송장 번호 생성
	public String createInvoiceNumber(String fileName) {
		AdminDAO aDao = new AdminDAO();
		AdminDTO admin = new AdminDTO();
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		InvoiceDAO vDao = new InvoiceDAO();
		String date = fileName.substring(0, 8);
		String name = fileName.substring(12);
		admin = aDao.getOneAdminByName(name);
		LOG.debug(String.valueOf(admin.getaId()).substring(0, 2));
		String idNum = String.valueOf(admin.getaId()).substring(0, 2);
		String count = String.valueOf((int)((Math.random()*89)+10));
		String invoiceNumber = date + idNum + count;
		vList = vDao.getAllInvoiceLists();
		boolean complete = true;
		
		LOG.debug(vList.toString());
		// 중복 방지
		while(complete) {
			if(vList.isEmpty()) { complete = false; break; }
			for(InvoiceDTO invoice : vList) {
				if(invoice.getvId().equals(invoiceNumber)) {
					invoiceNumber = date + String.valueOf((int)((Math.random()*89)+10)) + idNum;
				} else {
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
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			LOG.trace(sql);
			while (rs.next()) {				
				count = rs.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getCount(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
