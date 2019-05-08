package pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class PayDAO {
	private static final Logger LOG = LoggerFactory.getLogger(PayDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	public void addPayList(PayDTO yDto) {
		LOG.trace("addPayList(): " + yDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into pay(yBankId, yAdminId, yPrice, yDate, yState) values(?, ?, ?, ?, ?);";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, yDto.getyBankId());
			pstmt.setInt(2, yDto.getyAdminId());
			pstmt.setInt(3, yDto.getyPrice());
			pstmt.setString(4, yDto.getyDate());
			pstmt.setString(5, yDto.getyState());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("addPayList() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<PayDTO> getAllPayLists() {
		ArrayList<PayDTO> yList = new ArrayList<PayDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from pay;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PayDTO yDto = new PayDTO();
				yDto.setyId(rs.getInt(1));
				yDto.setyBankId(rs.getInt(2));
				yDto.setyAdminId(rs.getInt(3));
				yDto.setyPrice(rs.getInt(4));
				yDto.setyDate(rs.getString(5));
				yDto.setyState(rs.getString(6));
				yList.add(yDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllPayLists(): Error Code : {}", e.getErrorCode());
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
		return yList; 
	}
	
	public void updatePayState(PayDTO yDto) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update pay set yState=? where yBankId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, yDto.getyState());
			pStmt.setInt(2, yDto.getyBankId());
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updatePayState() Error Code : {}", e.getErrorCode());
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
