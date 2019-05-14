package invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class InvoiceProductDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceProductDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public InvoiceProductDTO getOneInvoice(int pInvoiceId) { // 송장 번호에 해당하는 컬럼값 얻어오기
		InvoiceProductDTO ipDto = new InvoiceProductDTO();
		conn = DBManager.getConnection();
		String sql = "select * from invoiceproduct where pInvoiceId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pInvoiceId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ipDto.setpInvoiceId(rs.getInt(1));
				ipDto.setIpProductId(rs.getInt(2));
				ipDto.setIpProductName(rs.getString(3));
				ipDto.setIpQuantity(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneInvoice(): Error Code : {}", e.getErrorCode());
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
		return ipDto;
	}
}