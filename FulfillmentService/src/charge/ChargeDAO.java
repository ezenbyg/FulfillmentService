package charge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import invoice.InvoiceDTO;
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
				gDto.setgBankId(rs.getString(3));
				gDto.setgTotalPrice(rs.getInt(4));
				gDto.setgDate(rs.getString(5));
				gDto.setgState(rs.getString(6));
				gList.add(gDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllChargeLists(): Error Code : {}", e.getErrorCode());
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
		return gList; 
	}
	
	public ArrayList<ChargeDTO> selectJoinAllbyId(int page, int gAdminId, String date) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = "select g.gId, g.gAdminId, g.gBankId, g.gTotalPrice, g.gDate, g.gState, a.aName "
					+ "from charge as g "
					+ "inner join bank as b "
					+ "on g.gBankId=b.bId "
					+ "inner join admins as a "
					+ "on a.aId=b.bAdminId "
					+ "where g.gAdminId=? AND date_format(g.gDate, '%Y-%m')=? "
					+ "order by g.gDate desc;"; 
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select g.gId, g.gAdminId, g.gBankId, g.gTotalPrice, g.gDate, g.gState, a.aName "
					+ "from charge as g "
					+ "inner join bank as b "
					+ "on g.gBankId=b.bId "
					+ "inner join admins as a "
					+ "on a.aId=b.bAdminId "
					+ "where g.gAdminId=? AND date_format(g.gDate, '%Y-%m')=? "
					+ "order by g.gDate desc limit ?, 10;"; 
			offset = (page - 1) * 10;
		}
		ArrayList<ChargeDTO> gList = new ArrayList<ChargeDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page == 0) {
				pstmt.setInt(1, gAdminId);
				pstmt.setString(2, date);
			} else if(page != 0) {
				pstmt.setInt(1, gAdminId);
				pstmt.setString(2, date);
				pstmt.setInt(3, offset);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				ChargeDTO gDto = new ChargeDTO();
				gDto.setgId(rs.getInt(1));
				gDto.setgAdminId(rs.getInt(2));
				gDto.setgBankId(rs.getString(3));
				gDto.setgTotalPrice(rs.getInt(4));
				gDto.setgDate(rs.getString(5));
				gDto.setgState(rs.getString(6));
				gDto.setStorageAdminName(rs.getString(7));
				gList.add(gDto);
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
		return gList;
	}
	
	public void addChargeList(ChargeDTO gDto) {
		LOG.trace("addChargeList(): " + gDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into charge(gAdminId, gBankId, gTotalPrice, gDate) values(?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gDto.getgAdminId());
			pstmt.setString(2, gDto.getgBankId());
			pstmt.setInt(3, gDto.getgTotalPrice());
			pstmt.setString(4, gDto.getgDate());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("addChargeList() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ChargeDTO getOneChargeList(int gAdminId, String gDate) { 
		ChargeDTO gDto = new ChargeDTO();
		conn = DBManager.getConnection();
		String sql = "select * from charge where gAdminId=? AND gDate=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gAdminId);
			pstmt.setString(2, gDate);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gDto.setgId(rs.getInt(1));
				gDto.setgAdminId(rs.getInt(2));
				gDto.setgBankId(rs.getString(3));
				gDto.setgTotalPrice(rs.getInt(4));
				gDto.setgDate(rs.getString(5));
				gDto.setgState(rs.getString(6));
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
	
	public void updateChargeState(String gState, int gId) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update charge set gState=? where gId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, gState);
			pStmt.setInt(2, gId);
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateChargeState() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public int getCount() {
    	conn = DBManager.getConnection();
		String sql = "select count(*) from charge;";
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
	
	public ArrayList<InvoiceDTO> selectMotnthToCharge(int page, int category, String date) {
		conn = DBManager.getConnection();
		int offset = 0;
		String sql = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			sql = 	"select v.vAdminId, v.vId, v.vProductName, s.pPrice, v.vDate "
					+ "from invoice as v " + 
					"inner join storage as s "
					+ "on v.vProductId=s.pId "
					+ "where v.vAdminId=? AND v.vDate=? "
					+ "order by v.vId desc;";
			
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			sql = "select v.vAdminId, v.vId, v.vProductName, s.pPrice, v.vDate "
					+ "from invoice as v " + 
					"inner join storage as s "
					+ "on v.vProductId=s.pId "
					+ "where v.vAdminId=? AND v.vDate=? "
					+ "order by v.vId desc limit ?, 10;"; 
			offset = (page - 1) * 10;
		}
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			LOG.trace(sql);
			if (page == 0) {
				pstmt.setInt(1, category);
				pstmt.setString(2, date);
			} else if(page != 0) {
				pstmt.setInt(1, category);
				pstmt.setString(2, date);
				pstmt.setInt(3, offset);
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {	
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setvAdminId(rs.getInt(1));
				vDto.setvId(rs.getString(2));
				vDto.setvProductName(rs.getString(3));
				vDto.setvPrice(rs.getInt(4));
				vDto.setvDate(rs.getString(5));
				vList.add(vDto);
				LOG.trace(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("selectJoinAllToCharge(): Error Code : {}", e.getErrorCode());
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
		return vList;
	}
}
