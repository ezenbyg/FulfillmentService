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
	
	public void orderProducts(OrderDTO oDto) { // 발주(창고 -> 구매처)
		LOG.trace("orderProducts(): " + oDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into p_order(oAdminId, oProductId, oQuantity, oPrice, oTotalPrice) values(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oDto.getoAdminId());
			pstmt.setInt(2, oDto.getoProductId());
			pstmt.setInt(3, oDto.getoQuantity());
			pstmt.setInt(4, oDto.getoPrice());
			pstmt.setInt(5, oDto.getoTotalPrice());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("addCustomer() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<OrderDTO> getAllOrderLists() {
		ArrayList<OrderDTO> oList = new ArrayList<OrderDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from p_order;";
		try {
			pstmt = conn.prepareStatement(sql);
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
}
