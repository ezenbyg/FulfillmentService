package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class OrderDAO {
	private static final Logger LOG = LoggerFactory.getLogger(OrderDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void addOrderProducts(OrderDTO oDto) { // 발주(창고 -> 구매처)
		LOG.trace("orderProducts(): " + oDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into p_order(oAdminId, oProductId, oQuantity, oPrice, oTotalPrice, oDate) values(?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oDto.getoAdminId());
			pstmt.setInt(2, oDto.getoProductId());
			pstmt.setInt(3, oDto.getoQuantity());
			pstmt.setInt(4, oDto.getoPrice());
			pstmt.setInt(5, oDto.getoTotalPrice());
			pstmt.setString(6, oDto.getoDate());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("addOrderProducts() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<OrderDTO> getAllOrderLists(int oAdminId) {
		ArrayList<OrderDTO> oList = new ArrayList<OrderDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from p_order where oAdminId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oAdminId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO oDto = new OrderDTO();
				oDto.setoId(rs.getInt(1));
				oDto.setoAdminId(rs.getInt(2));
				oDto.setoProductId(rs.getInt(3));
				oDto.setoQuantity(rs.getInt(4));
				oDto.setoPrice(rs.getInt(5));
				oDto.setoTotalPrice(rs.getInt(6));
				oDto.setoDate(rs.getString(7));
				oList.add(oDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllOrderLists(): Error Code : {}", e.getErrorCode());
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
		return oList;
	}
	
	public OrderDTO getOneOrderList(int oAdminId, int oProductId) { 
		OrderDTO oDto = new OrderDTO();
		conn = DBManager.getConnection();
		String sql = "select * from p_order where oAdminId=? AND oProductId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oAdminId);
			pstmt.setInt(2, oProductId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				oDto.setoId(rs.getInt(1));
				oDto.setoAdminId(rs.getInt(2));
				oDto.setoProductId(rs.getInt(3));
				oDto.setoQuantity(rs.getInt(4));
				oDto.setoPrice(rs.getInt(5));
				oDto.setoTotalPrice(rs.getInt(6));
				oDto.setoDate(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneOrderList(): Error Code : {}", e.getErrorCode());
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
		return oDto;
	}
	
    public int getCount() {
    	conn = DBManager.getConnection();
		String sql = "select count(*) from p_order;";
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
		} catch (Exception e) {
			e.printStackTrace();
			LOG.trace(e.getMessage());
		} finally {
			try {
				if (pStmt != null && !pStmt.isClosed()) 
					pStmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
				LOG.trace(se.getMessage());
			}
		}
		return count;
	}
    
	public ArrayList<OrderDTO> selectJoinAll(int page, int oAdminId) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select oId, oAdminId, oPrdouctId, oQuantity, oPrice, oTotalPrice, oDate"
					+ " from p_order "
					+ "where oAdminId=?"
					+ " order by id desc;"; 
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select oId, oAdminId, oPrdouctId, oQuantity, oPrice, oTotalPrice, oDate"
					+ " from member where oAdminid=?"
					+ " order by id desc limit ?, 10;";  
			offset = (page - 1) * 10;
		}
		ArrayList<OrderDTO> oList = new ArrayList<OrderDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page != 0) {
				pstmt.setInt(1, oAdminId);
				pstmt.setInt(2, offset);
			} else if(page == 0) {
				pstmt.setInt(1, oAdminId);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				OrderDTO oDto = new OrderDTO();
				oDto.setoId(rs.getInt(1));
				oDto.setoAdminId(rs.getInt(2));
				oDto.setoProductId(rs.getInt(3));
				oDto.setoQuantity(rs.getInt(4));
				oDto.setoPrice(rs.getInt(5));
				oDto.setoTotalPrice(rs.getInt(6));
				oDto.setoDate(rs.getString(7));
				oList.add(oDto);
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
		return oList;
	}
	
	public void updateOrderState(String oState, int oId) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update p_order set oState=? where oId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, oState);
			pStmt.setInt(2, oId);
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateOrderState() Error Code : {}", e.getErrorCode());
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
