package invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;


public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public ArrayList<InvoiceDTO> getAllInvoiceLists() {
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		conn = DBManager.getConnection();
		String sql = "select vId, vAdminId, vShopName, vName, vTel, vAddress, vDate, vPrice, vState, p.ipQuantity, p.ipProductId "
				+ "from invoice as v " 
				+ "inner join invoiceproduct as p "
				+ "on v.vId=p.pInvoiceId;";
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
				vDto.setvQuantity(rs.getInt(10));
				vDto.setvProductId(rs.getString(11));
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
			sql = "select v.vId, v.vShopName, v.vName, v.vTel, v.vAddress, v.vDate, v.vState, p.ipQuantity "
					+ "from invoice as v " 
					+ "inner join invoiceproduct as p "
					+ "on v.vId=p.pInvoiceId "
					+ "order by v.vDate desc;";
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select v.vId, v.vShopName, v.vName, v.vTel, v.vAddress, v.vDate, v.vState, p.ipQuantity "
					+ "from invoice as v " 
					+ "inner join invoiceproduct as p "
					+ "on v.vId=p.pInvoiceId "
					+ "order by v.vDate desc limit ?, 10;";
			offset = (page - 1) * 10;
		}
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page != 0) {
				pstmt.setInt(1, offset);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {	
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setvId(rs.getString(1));
				vDto.setvShopName(rs.getString(2));
				vDto.setvName(rs.getString(3));
				vDto.setvTel(rs.getString(4));
				vDto.setvAddress(rs.getString(5));
				vDto.setvDate(rs.getString(6));
				vDto.setvState(rs.getString(7));
				vDto.setvQuantity(rs.getInt(8));
				vList.add(vDto);
				LOG.debug(vDto.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectJoinAll(): Error Code : {}", e.getErrorCode());
			return null; 
		} finally { 
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
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
