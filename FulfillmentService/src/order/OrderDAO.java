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
	
	public void addOrderProducts(OrderDTO oDto) { 
		LOG.trace("orderProducts(): " + oDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into p_order(oAdminId, oProductId, oQuantity, oTotalPrice, oDate) values(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oDto.getoAdminId());
			pstmt.setInt(2, oDto.getoProductId());
			pstmt.setInt(3, oDto.getoQuantity());
			pstmt.setInt(4, oDto.getoTotalPrice());
			pstmt.setString(5, oDto.getoDate());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("addOrderProducts() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<OrderDTO> getAllOrderLists(int oAdminId, String date) {
		ArrayList<OrderDTO> oList = new ArrayList<OrderDTO>();
		conn = DBManager.getConnection();
		String sql = "select o.oId, o.oAdminId, o.oProductId, o.oQuantity, o.oTotalPrice, o.oDate, o.oState, a.aName, p.pName "
				+ "from p_order as o "
				+ "inner join admins as a "
				+ "on o.oAdminId=a.aId "
				+ "inner join storage as p "
				+ "on o.oProductId=p.pId "
				+ "where oAdminId=? AND date_format(oDate, '%Y-%m-%d')=? "
				+ "order by o.oDate desc;"; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oAdminId);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO oDto = new OrderDTO();
				oDto.setoId(rs.getInt(1));
				oDto.setoAdminId(rs.getInt(2));
				oDto.setoProductId(rs.getInt(3));
				oDto.setoQuantity(rs.getInt(4));
				oDto.setoTotalPrice(rs.getInt(5));
				oDto.setoDate(rs.getString(6));
				oDto.setoState(rs.getString(7));
				oDto.setoAdminName(rs.getString(8));
				oDto.setoProductName(rs.getString(9));
				oList.add(oDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllOrderLists(): Error Code : {}", e.getErrorCode());
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
		return oList;
	}
	
	public ArrayList<OrderDTO> getAllOrderLists(String date) {
		ArrayList<OrderDTO> oList = new ArrayList<OrderDTO>();
		conn = DBManager.getConnection();
		String sql = "select o.oId, o.oAdminId, o.oProductId, o.oQuantity, o.oTotalPrice, o.oDate, o.oState, a.aName, p.pName "
				+ "from p_order as o "
				+ "inner join admins as a "
				+ "on o.oAdminId=a.aId "
				+ "inner join storage as p "
				+ "on o.oProductId=p.pId "
				+ "where date_format(oDate, '%Y-%m')=? "
				+ "order by o.oDate desc;"; 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO oDto = new OrderDTO();
				oDto.setoId(rs.getInt(1));
				oDto.setoAdminId(rs.getInt(2));
				oDto.setoProductId(rs.getInt(3));
				oDto.setoQuantity(rs.getInt(4));
				oDto.setoTotalPrice(rs.getInt(5));
				oDto.setoDate(rs.getString(6));
				oDto.setoState(rs.getString(7));
				oDto.setoAdminName(rs.getString(8));
				oDto.setoProductName(rs.getString(9));
				oList.add(oDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllOrderLists(): Error Code : {}", e.getErrorCode());
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
				oDto.setoTotalPrice(rs.getInt(5));
				oDto.setoDate(rs.getString(6));
				oDto.setoState(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneOrderList(): Error Code : {}", e.getErrorCode());
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
		return oDto;
	}
	
    public int getCount(int oAdminId) {
    	conn = DBManager.getConnection();
		String sql = "select count(*) from p_order where oAdminId=?;";
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oAdminId);
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
    
    public int getCount() {
    	conn = DBManager.getConnection();
		String sql = "select count(*) from p_order;";
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
    
	public ArrayList<OrderDTO> selectJoinAllbyId(int page, int oAdminId, String date) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select o.oId, o.oAdminId, o.oProductId, o.oQuantity, o.oTotalPrice, o.oDate, o.oState, a.aName, p.pName "
					+ "from p_order as o "
					+ "inner join admins as a "
					+ "on o.oAdminId=a.aId "
					+ "inner join storage as p "
					+ "on o.oProductId=p.pId "
					+ "where oAdminId=? AND date_format(oDate, '%Y-%m-%d')=? "
					+ "order by o.oDate desc;"; 
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select o.oId, o.oAdminId, o.oProductId, o.oQuantity, o.oTotalPrice, o.oDate, o.oState, a.aName, p.pName "
					+ "from p_order as o "
					+ "inner join admins as a "
					+ "on o.oAdminId=a.aId "
					+ "inner join storage as p "
					+ "on o.oProductId=p.pId "
					+ "where oAdminId=? AND date_format(oDate, '%Y-%m-%d')=? "
					+ "order by o.oDate desc limit ?, 10;"; 
			offset = (page - 1) * 10;
		}
		ArrayList<OrderDTO> oList = new ArrayList<OrderDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page == 0) {
				pstmt.setInt(1, oAdminId);
				pstmt.setString(2, date);
			} else if(page != 0) {
				pstmt.setInt(1, oAdminId);
				pstmt.setString(2, date);
				pstmt.setInt(3, offset);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				OrderDTO oDto = new OrderDTO();
				oDto.setoId(rs.getInt(1));
				oDto.setoAdminId(rs.getInt(2));
				oDto.setoProductId(rs.getInt(3));
				oDto.setoQuantity(rs.getInt(4));
				oDto.setoTotalPrice(rs.getInt(5));
				oDto.setoDate(rs.getString(6));
				oDto.setoState(rs.getString(7));
				oDto.setoAdminName(rs.getString(8));
				oDto.setoProductName(rs.getString(9));
				oList.add(oDto);
				LOG.trace(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectJoinAllbyId(): Error Code : {}", e.getErrorCode());
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
		return oList;
	}
	
	public ArrayList<OrderDTO> selectJoinAllbyId(int page, String date) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select o.oId, o.oAdminId, o.oProductId, o.oQuantity, o.oTotalPrice, o.oDate, o.oState, a.aName, p.pName "
					+ "from p_order as o "
					+ "inner join admins as a "
					+ "on o.oAdminId=a.aId "
					+ "inner join storage as p "
					+ "on o.oProductId=p.pId "
					+ "where date_format(oDate, '%Y-%m')=? "
					+ "order by o.oDate desc;"; 
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select o.oId, o.oAdminId, o.oProductId, o.oQuantity, o.oTotalPrice, o.oDate, o.oState, a.aName, p.pName "
					+ "from p_order as o "
					+ "inner join admins as a "
					+ "on o.oAdminId=a.aId "
					+ "inner join storage as p "
					+ "on o.oProductId=p.pId "
					+ "where date_format(oDate, '%Y-%m')=? "
					+ "order by o.oDate desc limit ?, 10;"; 
			offset = (page - 1) * 10;
		}
		ArrayList<OrderDTO> oList = new ArrayList<OrderDTO>();
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
				OrderDTO oDto = new OrderDTO();
				oDto.setoId(rs.getInt(1));
				oDto.setoAdminId(rs.getInt(2));
				oDto.setoProductId(rs.getInt(3));
				oDto.setoQuantity(rs.getInt(4));
				oDto.setoTotalPrice(rs.getInt(5));
				oDto.setoDate(rs.getString(6));
				oDto.setoState(rs.getString(7));
				oDto.setoAdminName(rs.getString(8));
				oDto.setoProductName(rs.getString(9));
				oList.add(oDto);
				LOG.trace(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectJoinAllbyId(): Error Code : {}", e.getErrorCode());
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
		return oList;
	}
	
	public void updateOrderState(String oState, int oId, String date) {
		LOG.debug("");
		conn = DBManager.getConnection();
		String sql = "update p_order set oState=?, oDate=? where oId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, oState);
			pstmt.setString(2, date);
			pstmt.setInt(3, oId);
			pstmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateOrderState() Error Code : {}", e.getErrorCode());
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
