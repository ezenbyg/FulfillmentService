package invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;


public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public ArrayList<InvoiceDTO> getAllInvoiceLists() {
		ArrayList<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from invoice;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setvId(rs.getInt(1));
				vDto.setvAdminId(rs.getInt(2));
				vDto.setvDate(rs.getString(3));
				vDto.setvState(rs.getString(14));
				vList.add(vDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllInvoiceLists(): Error Code : {}", e.getErrorCode());
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
		return vList;
	}
	
	public void addInvoice(InvoiceDTO vDto) {
		LOG.trace("addInvoice(): " + vDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into invoice(vId, vAdminId, vDate) values(?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vDto.getvId());
			pstmt.setInt(2, vDto.getvAdminId());
			pstmt.setString(3, vDto.getvDate());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("addInvoice() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public InvoiceDTO getOneInvoice(int vId) { // 송장 번호에 해당하는 컬럼값 얻어오기
		InvoiceDTO vDto = new InvoiceDTO();
		conn = DBManager.getConnection();
		String sql = "select * from invoice where vId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vDto.setvId(rs.getInt(1));
				vDto.setvAdminId(rs.getInt(2));
				vDto.setvDate(rs.getString(3));
				vDto.setvState(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneInvoice (): Error Code : {}", e.getErrorCode());
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
		return vDto;
	}
	
	public void updateInvoiceState(String vState, int vId) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update invoice set vState=? where vId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, vState);
			pStmt.setInt(2, vId);
			pStmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateInvoiceState() Error Code : {}", e.getErrorCode());
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
