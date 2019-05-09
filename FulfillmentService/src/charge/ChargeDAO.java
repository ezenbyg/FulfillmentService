package charge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class ChargeDAO { // 송장에서 얻은 정보에 가격을 포함해서 청구테이블에 저장
	private static final Logger LOG = LoggerFactory.getLogger(ChargeDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	public ArrayList<ChargeDTO> getAllChargeLists() {
		ArrayList<ChargeDTO> gList = new ArrayList<ChargeDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from charge;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ChargeDTO gDto = new ChargeDTO();
				gDto.setgId(rs.getInt(1));
				gDto.setgAdminId(rs.getInt(2));
				gDto.setgInvoiceId(rs.getInt(3)); 
				gDto.setgBankId(rs.getString(4));
				gDto.setgProductName(rs.getString(5));
				gDto.setgQuantity(rs.getInt(6));
				gDto.setgPrice(rs.getInt(7));
				gDto.setgDate(rs.getString(8));
				gDto.setgState(rs.getString(9));
				gList.add(gDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllChargeLists(): Error Code : {}", e.getErrorCode());
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
		return gList; 
	}
	
	public void addChargeList(ChargeDTO gDto) {
		LOG.trace("addChargeList(): " + gDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into charge(gAdminId, gInvoiceId, gBankId, gProductName, gQuantity, gPrice, gDate) values(?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gDto.getgAdminId());
			pstmt.setInt(2, gDto.getgInvoiceId());
			pstmt.setString(3, gDto.getgBankId());
			pstmt.setString(4, gDto.getgProductName());
			pstmt.setInt(5, gDto.getgQuantity());
			pstmt.setInt(6, gDto.getgPrice());
			pstmt.setString(7, gDto.getgDate());
			
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
	
	public ChargeDTO getOneChargeList(int gInvoiceId) { // 송장 번호 하나에 해당하는 컬럼 값 출력
		ChargeDTO gDto = new ChargeDTO();
		conn = DBManager.getConnection();
		String sql = "select * from charge where gInvoiceId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gInvoiceId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gDto.setgAdminId(rs.getInt(1));
				gDto.setgInvoiceId(rs.getInt(2));
				gDto.setgBankId(rs.getString(3));
				gDto.setgProductName(rs.getString(4));
				gDto.setgQuantity(rs.getInt(5));
				gDto.setgPrice(rs.getInt(6));
				gDto.setgDate(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneChargeList (): Error Code : {}", e.getErrorCode());
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
		return gDto;
	}
	
	public void updateChargeState(String gState, int gInvoiceId) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update charge set gState=? where gInvoiceId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, gState);
			pStmt.setInt(2, gInvoiceId);
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateChargeState() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public int getCount() {
    	conn = DBManager.getConnection();
		String sql = "select count(*) from charge;";
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
    
	public ArrayList<ChargeDTO> selectJoinAll(int page, int gAdminId) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select gId, gAdminId, gInvoiceId, gBankId, gProductName, gQuantity, gPrice, gDate, gState"
					+ " from charge "
					+ "where gAdminId=?"
					+ " order by id desc;"; 
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select gId, gAdminId, gInvoiceId, gBankId, gProductName, gQuantity, gPrice, gDate, gState"
					+ " from charge where gAdminId=?"
					+ " order by id desc limit ?, 10;";  
			offset = (page - 1) * 10;
		}
		ArrayList<ChargeDTO> gList = new ArrayList<ChargeDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page == 0) {
				pstmt.setInt(1, gAdminId);
			} else if(page != 0) {
				pstmt.setInt(1, gAdminId);
				pstmt.setInt(2, offset);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				ChargeDTO gDto = new ChargeDTO();
				gDto.setgId(rs.getInt(1));
				gDto.setgAdminId(rs.getInt(2));
				gDto.setgInvoiceId(rs.getInt(3)); 
				gDto.setgBankId(rs.getString(4));
				gDto.setgProductName(rs.getString(5));
				gDto.setgQuantity(rs.getInt(6));
				gDto.setgPrice(rs.getInt(7));
				gDto.setgDate(rs.getString(8));
				gDto.setgState(rs.getString(9));
				gList.add(gDto);
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
		return gList;
	}
}
