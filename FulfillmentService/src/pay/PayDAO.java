package pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storage.StorageDTO;
import util.DBManager;

public class PayDAO {
	public static final int KK = 40001;
	public static final int JB = 40002;
	public static final int YN = 40003;
	public static final int SB = 40004;
	private static final Logger LOG = LoggerFactory.getLogger(PayDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	PayDTO yDto;

	// 운송회사 출고List
	public ArrayList<PayDTO> getTransportList() {
		String sql = "select p_release.rTransportId, p_release.rDate, pay.yState " + "from p_release inner join pay "
				+ "on p_release.rTransportId=pay.yAdminId order by rTransportId desc;";

		pstmt = null;
		ArrayList<PayDTO> payList = new ArrayList<PayDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				yDto = new PayDTO();
				yDto.setrTransportId(rs.getInt(1));
				yDto.setyDate(rs.getString(2));
				yDto.setyState(rs.getString(3));
				payList.add(yDto);
			}
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException se) {
				LOG.debug(se.getMessage());
			}
		}
		return payList;
	}

	// 검색된 운송회사의 리스트 수
	public int listCount(int rTransportId) {
		String sql = "select count(?) from p_release";
		pstmt = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rTransportId);

			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("listCount(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	// 검색된 운송회사 출고List
	public ArrayList<PayDTO> searchTransprotList(int searchId) {
		String sql = "select p_release.rTransportId, p_release.rDate, pay.yState " + "from p_release inner join pay "
				+ "on p_release.rTransportId=pay.yAdminId " + "where p_release.rTransportId = ?"
				+ "order by rTransportId desc;";

		pstmt = null;
		ArrayList<PayDTO> searchPayList = new ArrayList<PayDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, searchId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				yDto = new PayDTO();
				yDto.setrTransportId(rs.getInt(1));
				yDto.setyDate(rs.getString(2));
				yDto.setyState(rs.getString(3));
				searchPayList.add(yDto);
			}
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException se) {
				LOG.debug(se.getMessage());
			}
		}
		return searchPayList;
	}

	// 지급 버튼 눌렀을 때 값들이 pay 테이블에 삽입
	public void addPayList(PayDTO yDto) {
		LOG.trace("addPayList(): " + yDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into pay(yBankId, yAdminId, yPrice, yDate, yState) values(?, ?, ?, ?, ?);";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, yDto.getyBankId());
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
	//bank 잔액 조회
	public PayDTO getBank(int rTransportId) {
		conn = DBManager.getConnection();
		String sql = "select bId, bBalance from bAdminId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,rTransportId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PayDTO yDto = new PayDTO();
				yDto.setbId(rs.getString(1));
				yDto.setbBalance(rs.getInt(2));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getBank(): Error Code : {}", e.getErrorCode());
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
		return yDto;
	}
	
	// 지급 버튼 눌렀을 때 bank 테이블에서 돈 계산
	public void updateBank(int totalPrice,int rTransportId) {
		LOG.debug("");
		pstmt = null;
		conn = DBManager.getConnection();
		String sql = "update bank set bBalance=? where bAdminId=?;";
		pstmt = null;
		
		yDto = getBank(rTransportId);
		int balance= yDto.getbBalance();
		int cal = balance - totalPrice;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cal);
			pstmt.setInt(2, rTransportId);
			pstmt.executeUpdate();
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
	
	// pay 테이블 전체 조회
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
				yDto.setyBankId(rs.getString(2));
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

	public int getCount() {
		String sql = "select count(*) from storage;";
		conn = DBManager.getConnection();
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getCount(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

}