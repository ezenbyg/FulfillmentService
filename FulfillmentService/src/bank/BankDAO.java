package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class BankDAO {
	private static final Logger LOG = LoggerFactory.getLogger(BankDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public ArrayList<BankDTO> getAllBankLists() {
		ArrayList<BankDTO> bList = new ArrayList<BankDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from bank;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BankDTO bDto = new BankDTO();
				bDto.setbId(rs.getString(1));
				bDto.setbAdminId(rs.getInt(2));
				bDto.setbBalance(rs.getInt(3)); 
				bList.add(bDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllBankLists(): Error Code : {}", e.getErrorCode());
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
		return bList; 
	}
	
	public void updateBank(BankDTO bDto, int balance) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update bank set balance=? where bId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, bDto.getbBalance() + balance);
			pStmt.setString(2, bDto.getbId());
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateBank() Error Code : {}", e.getErrorCode());
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
