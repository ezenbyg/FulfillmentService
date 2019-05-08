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
				gDto.setgProductName(rs.getString(4));
				gDto.setgQuantity(rs.getInt(5));
				gDto.setgPrice(rs.getInt(6));
				gDto.setgDate(rs.getString(7));
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
		String sql = "insert into charge(gAdminId, gInvoiceId, gProductName, gQuantity, gPrice, gDate) values(?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gDto.getgAdminId());
			pstmt.setInt(2, gDto.getgInvoiceId());
			pstmt.setString(3, gDto.getgProductName());
			pstmt.setInt(4, gDto.getgQuantity());
			pstmt.setInt(5, gDto.getgPrice());
			pstmt.setString(6, gDto.getgDate());
			
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
	
	public ChargeDTO getOneChargeList(String gInvoiceId) { // 송장 번호 하나에 해당하는 컬럼 값 출력
		ChargeDTO gDto = new ChargeDTO();
		conn = DBManager.getConnection();
		String sql = "select * from charge where gInvoiceId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, gInvoiceId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gDto.setgAdminId(rs.getInt(1));
				gDto.setgInvoiceId(rs.getInt(2));
				gDto.setgProductName(rs.getString(3));
				gDto.setgQuantity(rs.getInt(4));
				gDto.setgPrice(rs.getInt(5));
				gDto.setgDate(rs.getString(6));
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
}
