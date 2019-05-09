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
				vDto.setvName(rs.getString(2));
				vDto.setvTel(rs.getString(3));
				vDto.setvAddress(rs.getString(4));
				vDto.setvProductId(rs.getInt(5));
				vDto.setvProductName(rs.getString(6));
				vDto.setvQuantity(rs.getInt(7));
				vDto.setvDate(rs.getString(8));
				vDto.setvAdminId(rs.getInt(9));
				vDto.setvState(rs.getString(10));
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
	
	public void addInvoice(InvoiceDTO vDto) { // 쇼핑몰에서 송장 보내기 클릭하면 DB로 입력
		LOG.trace("addInvoice(): " + vDto.toString());
		conn = DBManager.getConnection();
		String sql = "insert into invoice(vName, vTel, vAddress, vProductId, vProductName, vQuantity, vDate, vAdminId) values(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vDto.getvName());
			pstmt.setString(2, vDto.getvTel());
			pstmt.setString(3, vDto.getvAddress());
			pstmt.setInt(4, vDto.getvProductId());
			pstmt.setString(5, vDto.getvProductName());
			pstmt.setInt(6, vDto.getvQuantity());
			pstmt.setString(7, vDto.getvDate());
			pstmt.setInt(8, vDto.getvAdminId());
			
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
				vDto.setvName(rs.getString(2));
				vDto.setvTel(rs.getString(3));
				vDto.setvAddress(rs.getString(4));
				vDto.setvProductId(rs.getInt(5));
				vDto.setvProductName(rs.getString(6));
				vDto.setvQuantity(rs.getInt(7));
				vDto.setvDate(rs.getString(8));
				vDto.setvAdminId(rs.getInt(9));
				vDto.setvState(rs.getString(10));
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
}
