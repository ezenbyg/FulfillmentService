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
		String sql = "insert into p_order(soldAdminId, soldId, soldName, soldPrice, soldTotalPrice, soldQuantity, soldDate)"
				+ " values(?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sDto.getSoldAdminId());
			pstmt.setInt(2, sDto.getSoldId());
			pstmt.setString(3, sDto.getSoldName());
			pstmt.setInt(4, sDto.getSoldPrice());
			pstmt.setInt(5, sDto.getSoldTotalPrice());
			pstmt.setInt(6, sDto.getSoldQuantity());
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
	
	// 월별 + 항목별 상품 조회
	public ArrayList<SoldProductDTO> getMonthlySoldProducts(int category, int page, String date) {
		int offset = 0;
		String sql = null;
		ArrayList<SoldProductDTO> soldList = new ArrayList<SoldProductDTO>();
		conn = DBManager.getConnection();
		
		if (page == 0) {
			sql = "select soldAdminId, soldId, soldName, soldPrice, soldTotalPrice, soldQuantity, soldDate"
					+ " from soldproduct where soldAdminId=? AND soldDate=?;";
		} else {
			sql = "select soldAdminId, soldId, soldName, soldPrice, soldTotalPrice, soldQuantity, soldDate"
					+ " from soldproduct where soldAdminId=? AND soldDate=? order by soldDate desc limit ?, 10;"; // ? 시작점, 10은 가져올 갯수
			offset = (page - 1) * 10;
		}
		try {
			pstmt = conn.prepareStatement(sql);
			if (page == 0) {
				pstmt.setInt(1, category);
				pstmt.setString(2, date);
			} else if (page != 0) {
				pstmt.setInt(1, category);
				pstmt.setString(2, date);
				pstmt.setInt(3, offset);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) { 
				SoldProductDTO sDto = new SoldProductDTO();
				sDto.setSoldAdminId(rs.getInt(1));
				sDto.setSoldId(rs.getInt(2));
				sDto.setSoldName(rs.getString(3));
				sDto.setSoldPrice(rs.getInt(4));
				sDto.setSoldTotalPrice(rs.getInt(5));
				sDto.setSoldQuantity(rs.getInt(6));
				sDto.setSoldDate(rs.getString(7));
				LOG.trace(sDto.toString());
				soldList.add(sDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getMonthlySoldProducts(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return soldList;
	}
	
	public int getEachAdminIdCount(int category) {
		String sql = "select count(*) from soldproduct where soldAdminId=?;";
		conn = DBManager.getConnection();
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info(e.getMessage());
			LOG.info("getEachAdminIdCount(): Error Code : {}", e.getErrorCode());
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
	
	// 월별 전체조회
	public ArrayList<SoldProductDTO> getMonthlySoldProducts(int page, String date) {
		int offset = 0;
		String sql = null;
		ArrayList<SoldProductDTO> soldList = new ArrayList<SoldProductDTO>();
		conn = DBManager.getConnection();
		
		if (page == 0) {
			sql = "select soldAdminId, soldId, soldName, soldPrice, soldTotalPrice, soldQuantity, soldDate"
					+ " from soldproduct where soldDate=?;";
		} else {
			sql = "select soldAdminId, soldId, soldName, soldPrice, soldTotalPrice, soldQuantity, soldDate"
					+ " from soldproduct where soldDate=? order by soldDate desc limit ?, 10;"; // ? 시작점, 10은 가져올 갯수
			offset = (page - 1) * 10;
		}
		try {
			pstmt = conn.prepareStatement(sql);
			if (page == 0) {
				pstmt.setString(1, date);
			} else if (page != 0) {
				pstmt.setString(1, date);
				pstmt.setInt(2, offset);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) { 
				SoldProductDTO sDto = new SoldProductDTO();
				sDto.setSoldAdminId(rs.getInt(1));
				sDto.setSoldId(rs.getInt(2));
				sDto.setSoldName(rs.getString(3));
				sDto.setSoldPrice(rs.getInt(4));
				sDto.setSoldTotalPrice(rs.getInt(5));
				sDto.setSoldQuantity(rs.getInt(6));
				sDto.setSoldDate(rs.getString(7));
				LOG.trace(sDto.toString());
				soldList.add(sDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getMonthlySoldProducts(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return soldList;
	}
	
	public int getAllAdminIdCount() {
		String sql = "select count(*) from soldproduct;" ;
		conn = DBManager.getConnection();
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info(e.getMessage());
			LOG.info("getAllAdminIdCount(): Error Code : {}", e.getErrorCode());
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
