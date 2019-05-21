package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBManager;

public class SoldProductDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	private static final Logger LOG = LoggerFactory.getLogger(SoldProductDAO.class);
	
	public void addSoldProducts(SoldProductDTO sDto) { 
		LOG.trace("addSoldProducts(): " + sDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into soldproduct(soldInvId, soldShopId, soldTransportId, soldId, soldQuantity, soldTotalPrice, soldDate)"
				+ " values(?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sDto.getSoldInvId());
			pstmt.setInt(2, sDto.getSoldShopId());
			pstmt.setInt(3, sDto.getSoldTransportId());
			pstmt.setInt(4, sDto.getSoldId());
			pstmt.setInt(5, sDto.getSoldQuantity());
			pstmt.setInt(6, sDto.getSoldTotalPrice());
			pstmt.setString(7, sDto.getSoldDate());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("addSoldProducts( Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 계좌번호 구하기
	public String getBankId(int soldShopId) {
		conn = DBManager.getConnection();
		String sql = null;
		sql = "select distinct b.bId "
				+ "from bank as b "
				+ "inner join soldproduct as s "
				+ "on b.bAdminId=s.soldShopId;";
		String bankId = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			LOG.trace(sql);
			while (rs.next()) {				
				bankId = rs.getString(1);
			}
			return bankId;
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getBankId(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 쇼핑몰 아이디에 해당하는 데이터 전부다 가져오기
	public ArrayList<SoldProductDTO> selectAllListsByShopId(int soldShopId) {
		conn = DBManager.getConnection();
		String sql = null;
		sql = "select distinct s.soldInvId, s.soldShopId, v.vShopName, s.soldTotalPrice, s.soldDate, s.chargeState "
				+ "from soldproduct as s "
				+ "inner join invoice as v "
				+ "on s.soldInvId=v.vId "
				+ "where s.chargeState='미청구' AND s.soldShopId=? "
				+ "order by soldDate desc;"; 
		ArrayList<SoldProductDTO> sList = new ArrayList<SoldProductDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, soldShopId);
			LOG.trace(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				SoldProductDTO sDto = new SoldProductDTO();
				sDto.setSoldInvId(rs.getString(1));
				sDto.setSoldShopId(rs.getInt(2));
				sDto.setSoldShopName(rs.getString(3));
				sDto.setSoldTotalPrice(rs.getInt(4));
				sDto.setSoldDate(rs.getString(5));
				sDto.setChargeState(rs.getString(6));
				sList.add(sDto);
				LOG.trace(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectAllLists(): Error Code : {}", e.getErrorCode());
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
		return sList;
	}
	
	// 전부다 가져오기
	public ArrayList<SoldProductDTO> selectAllLists() {
		conn = DBManager.getConnection();
		String sql = null;
		sql = "select distinct s.soldInvId, s.soldShopId, v.vShopName, s.soldTotalPrice, s.soldDate, s.chargeState "
				+ "from soldproduct as s "
				+ "inner join invoice as v "
				+ "on s.soldInvId=v.vId "
				+ "where s.chargeState='미청구' "
				+ "order by soldDate desc;"; 
		ArrayList<SoldProductDTO> sList = new ArrayList<SoldProductDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				SoldProductDTO sDto = new SoldProductDTO();
				sDto.setSoldInvId(rs.getString(1));
				sDto.setSoldShopId(rs.getInt(2));
				sDto.setSoldShopName(rs.getString(3));
				sDto.setSoldTotalPrice(rs.getInt(4));
				sDto.setSoldDate(rs.getString(5));
				sDto.setChargeState(rs.getString(6));
				sList.add(sDto);
				LOG.trace(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectAllLists(): Error Code : {}", e.getErrorCode());
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
		return sList;
	}
	
	// 청구 페이지에 출력하기 위한 메소드
	public ArrayList<SoldProductDTO> selectAllForCharge(int page, String date) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select distinct s.soldInvId, s.soldShopId, v.vShopName, s.soldTotalPrice, s.soldDate, s.chargeState "
					+ "from soldproduct as s "
					+ "inner join invoice as v "
					+ "on s.soldInvId=v.vId "
					+ "where date_format(soldDate, '%Y-%m')=? AND s.chargeState='미청구' "
					+ "order by soldDate desc;"; 
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select distinct s.soldInvId, s.soldShopId, v.vShopName, s.soldTotalPrice, s.soldDate, s.chargeState "
					+ "from soldproduct as s "
					+ "inner join invoice as v "
					+ "on s.soldInvId=v.vId "
					+ "where date_format(soldDate, '%Y-%m')=? AND s.chargeState='미청구' "
					+ "order by soldDate desc limit ?, 10;";  
			offset = (page - 1) * 10;
		}
		ArrayList<SoldProductDTO> sList = new ArrayList<SoldProductDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page == 0) {
				pstmt.setString(1, date);
			} else if(page != 0) {
				pstmt.setString(1, date);
				pstmt.setInt(2, offset);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				SoldProductDTO sDto = new SoldProductDTO();
				sDto.setSoldInvId(rs.getString(1));
				sDto.setSoldShopId(rs.getInt(2));
				sDto.setSoldShopName(rs.getString(3));
				sDto.setSoldTotalPrice(rs.getInt(4));
				sDto.setSoldDate(rs.getString(5));
				sDto.setChargeState(rs.getString(6));
				sList.add(sDto);
				LOG.trace(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectJoinAllForCharge(): Error Code : {}", e.getErrorCode());
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
		return sList;
	}
	
	public int getCount() {
    	conn = DBManager.getConnection();
		String sql = "select count(distinct soldInvId) from soldproduct;";
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
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
	
	public void updateSoldProductState(String chargeState, String soldInvId) {
		LOG.debug("");
		conn = DBManager.getConnection();
		String sql = "update soldproduct set chargeState=? where soldInvId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chargeState);
			pstmt.setString(2, soldInvId);
			pstmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateSoldProductState() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
