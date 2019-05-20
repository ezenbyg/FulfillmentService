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
		String sql = "select v.vId, v.vShopName, v.vName, v.vTel, v.vAddress, v.vDate, v.vPrice, vState, a.aName, a.aId, p.ipProductId, p.ipQuantity, s.pName, s.pState "
				+ "from invoice as v " 
				+ "inner join invoiceproduct as p "
				+ "on v.vId=p.pInvoiceId "
				+ "inner join storage as s "
				+ "on s.pId=p.ipProductId " 
				+ "inner join admins as a "
				+ "on a.aId =v.vlogisId;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setvId(rs.getString(1));
				vDto.setvShopName(rs.getString(2));
				vDto.setvName(rs.getString(3));
				vDto.setvTel(rs.getString(4));
				vDto.setvAddress(rs.getString(5));
				vDto.setvDate(rs.getString(6));
				vDto.setvPrice(rs.getInt(7));
				vDto.setvState(rs.getString(8));
				vDto.setvTransportName(rs.getString(9));
				vDto.setVlogisId(rs.getInt(10));
				vDto.setvProductId(rs.getInt(11));
				vDto.setvQuantity(rs.getInt(12));
				vDto.setvProductName(rs.getString(13));
				vDto.setvProductState(rs.getString(14));
				vList.add(vDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllInvoiceLists(): Error Code : {}", e.getErrorCode());
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
	
	// 출고 메인 페이지에 출력할 내용
	public ArrayList<InvoiceDTO> getInvoiceListsForRelease(String date) {
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		conn = DBManager.getConnection();
		String sql = "select distinct v.vId, v.vShopName, v.vName, v.vTel, v.vAddress, v.vDate, v.vState, a.aName, v.vlogisId "
				+ "from invoice as v " 
				+ "inner join invoiceproduct as p "
				+ "on v.vId=p.pInvoiceId "
				+ "inner join storage as s "
				+ "on s.pId=p.ipProductId " 
				+ "inner join admins as a "
				+ "on a.aId =v.vlogisId " 
				+ "where date_format(v.vDate, '%Y-%m-%d')=? "
				+ "order by v.vDate, v.vState;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setvId(rs.getString(1));
				vDto.setvShopName(rs.getString(2));
				vDto.setvName(rs.getString(3));
				vDto.setvTel(rs.getString(4));
				vDto.setvAddress(rs.getString(5));
				vDto.setvDate(rs.getString(6));
				vDto.setvState(rs.getString(7));
				vDto.setvTransportName(rs.getString(8));
				vDto.setVlogisId(rs.getInt(9));
				vList.add(vDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getInvoiceListsForRelease(): Error Code : {}", e.getErrorCode());
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
	
	// 송장 아이디를 통한 list 추출 
	public ArrayList<InvoiceDTO> getAllInvoiceListsById(String vId) {
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		conn = DBManager.getConnection();
		String sql = "select v.vId, v.vAdminId, v.vShopName, v.vName, v.vTel, v.vAddress, v.vDate, v.vPrice, vState, a.aName, a.aId, p.ipProductId, p.ipQuantity, s.pName, s.pState "
				+ "from invoice as v " 
				+ "inner join invoiceproduct as p "
				+ "on v.vId=p.pInvoiceId "
				+ "inner join storage as s "
				+ "on s.pId=p.ipProductId " 
				+ "inner join admins as a "
				+ "on a.aId=v.vlogisId "
				+ "where v.vId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vId);
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
				vDto.setvTransportName(rs.getString(10));
				vDto.setVlogisId(rs.getInt(11));
				vDto.setvProductId(rs.getInt(12));
				vDto.setvQuantity(rs.getInt(13));
				vDto.setvProductName(rs.getString(14));
				vDto.setvProductState(rs.getString(15));
				vList.add(vDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllInvoiceListsById(): Error Code : {}", e.getErrorCode());
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
	
	// 송장에 있는 제품번호와 수량을 주어서 제품상태 변경
	public void changeProductState(int pId, int pQuantity) {
		StorageDAO sDao = new StorageDAO();
		StorageDTO sDto = new StorageDTO();
		sDto = sDao.getOneProductById(pId);
		if(sDto.getpQuantity() < pQuantity) {
			sDao.updateProductState("재고부족", pId);
		} else if((sDto.getpQuantity() >= pQuantity) && (sDto.getpQuantity()-pQuantity < 10)) {
			sDao.updateProductState("재고부족예상", pId);
		} else sDao.updateProductState("P", pId);
	}
	
	// Invoice
	public void addInvoice(InvoiceDTO invoice) {
		conn = DBManager.getConnection();
		String sql = "insert into invoice(vId, vAdminId, vShopName, vName, vTel, vAddress, vDate, vPrice, vlogisId) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
			pstmt.setInt(9, invoice.getVlogisId());
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
		String sql = "insert into invoiceproduct(pInvoiceId, ipProductId, ipQuantity) values(?, ?, ?);";
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
	
	public InvoiceDTO getInvoiceById(String vId) { // 송장 번호에 해당하는 컬럼값 얻어오기
		InvoiceDTO vDto = new InvoiceDTO();
		conn = DBManager.getConnection();
		String sql = "select v.vId, v.vShopName, v.vName, a.aName, v.vState, p.ipQuantity, v.vPrice "
				+ "from invoice as v " 
				+ "inner join invoiceproduct as p "
				+ "on v.vId=p.pInvoiceId "
				+ "inner join admins as a "
				+ "on a.aId=v.vlogisId "
				+ "where v.vId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vDto.setvId(rs.getString(1));
				vDto.setvShopName(rs.getString(2));
				vDto.setvName(rs.getString(3));
				vDto.setvTransportName(rs.getString(4));
				vDto.setvState(rs.getString(5));
				vDto.setvQuantity(rs.getInt(6));
				vDto.setvPrice(rs.getInt(7));
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
	
	public void updateInvoiceState(String vState, String vId) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update invoice set vState=? where vId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, vState);
			pStmt.setString(2, vId);
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
	
	public void updateInvoicePrice(String vId, int totalPrice) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update invoice set vPrice=? where vId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, totalPrice);
			pStmt.setString(2, vId);
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateInvoicePrice() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 송장에 해당하는 제품들 가져오기 
	public ArrayList<InvoiceProductDTO> getProductByInvoiceId() {
		ArrayList<InvoiceProductDTO> pList = new ArrayList<InvoiceProductDTO>();
		conn = DBManager.getConnection();
		String sql = null;
		sql = "select p.pInvoiceId, p.ipQuantity, s.pName, s.pState "
				+ "from invoiceproduct as p " 
				+ "inner join storage as s "
				+ "on p.ipProductId=s.pId "
				+ "inner join invoice as v "
				+ "on v.vId=p.pInvoiceId;";
		try {
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, vId);
			LOG.trace(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {	
				InvoiceProductDTO pDto = new InvoiceProductDTO();
				pDto.setpInvoiceId(rs.getString(1));
				pDto.setIpQuantity(rs.getInt(2));
				pDto.setIpProductName(rs.getString(3));

				pList.add(pDto);
				LOG.debug(pDto.toString());
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
		return pList;
	}
	
	public ArrayList<InvoiceDTO> selectJoinAll(int page) {
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select distinct v.vId, v.vShopName, v.vName, v.vTel, v.vAddress, v.vDate, v.vState "
					+ "from invoice as v " 
					+ "inner join invoiceproduct as p "
					+ "on v.vId=p.pInvoiceId "
					+ "order by v.vDate desc;";
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select distinct v.vId, v.vShopName, v.vName, v.vTel, v.vAddress, v.vDate, v.vState "
					+ "from invoice as v " 
					+ "inner join invoiceproduct as p "
					+ "on v.vId=p.pInvoiceId "
					+ "order by v.vDate desc limit ?, 10;";
			offset = (page - 1) * 10;
		}
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