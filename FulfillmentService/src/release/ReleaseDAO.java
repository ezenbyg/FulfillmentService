package release;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class ReleaseDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ReleaseDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public ArrayList<ReleaseDTO> getAllReleaseLists() {
		ArrayList<ReleaseDTO> rList = new ArrayList<ReleaseDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from p_release;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReleaseDTO rDto = new ReleaseDTO();
				rDto.setrId(rs.getInt(1));
				rDto.setrTransportId(rs.getInt(2));
				rDto.setrShoppingId(rs.getInt(3));
				rDto.setrInvoiceId(rs.getInt(4));
				rDto.setrName(rs.getString(5));
				rDto.setrTel(rs.getString(6));
				rDto.setrAddress(rs.getString(7));
				rDto.setrProductName(rs.getString(8));
				rDto.setrQuantity(rs.getInt(9));
				rDto.setrDate(rs.getString(10));
				rDto.setrPrice(rs.getInt(11));
				rDto.setrState(rs.getString(12));
				rList.add(rDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllReleaseLists(): Error Code : {}", e.getErrorCode());
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
		return rList; 
	}
	
	public void addReleaseList(ReleaseDTO rDto) {
		LOG.trace("addReleaseList(): " + rDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into p_release(rTransportId, rShoppingId, rInvoiceId, rName, rTel, rAddress, rProductName, rQuantity, rDate, rPrice)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rDto.getrTransportId());
			pstmt.setInt(2, rDto.getrShoppingId());
			pstmt.setInt(3, rDto.getrInvoiceId());
			pstmt.setString(4, rDto.getrName());
			pstmt.setString(5, rDto.getrTel());
			pstmt.setString(6, rDto.getrAddress());
			pstmt.setString(7, rDto.getrProductName());
			pstmt.setInt(8, rDto.getrQuantity());
			pstmt.setString(9, rDto.getrDate());
			pstmt.setInt(10, rDto.getrPrice());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("addChargeList() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ReleaseDTO getOneReleaseList(int rTransportId) { // 운송 번호에 해당하는 배송비 등 조회
		ReleaseDTO rDto = new ReleaseDTO();
		conn = DBManager.getConnection();
		String sql = "select * from p_release where rTransportId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rTransportId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				rDto.setrId(rs.getInt(1));
				rDto.setrTransportId(rs.getInt(2));
				rDto.setrShoppingId(rs.getInt(3));
				rDto.setrInvoiceId(rs.getInt(4));
				rDto.setrName(rs.getString(5));
				rDto.setrTel(rs.getString(6));
				rDto.setrAddress(rs.getString(7));
				rDto.setrProductName(rs.getString(8));
				rDto.setrQuantity(rs.getInt(9));
				rDto.setrDate(rs.getString(10));
				rDto.setrPrice(rs.getInt(11));
				rDto.setrState(rs.getString(12));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneReleaseList (): Error Code : {}", e.getErrorCode());
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
		return rDto;
	}
	
	public void updateReleaseState(ReleaseDTO rDto) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update p_release set rState=? where rTransportId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, rDto.getrState());
			pStmt.setInt(2, rDto.getrTransportId());
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateReleaseState() Error Code : {}", e.getErrorCode());
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
