package invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class InvoiceHumanDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceHumanDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public InvoiceHumanDTO getOneInvoice(int hInvoiceId) { // 송장 번호에 해당하는 컬럼값 얻어오기
		InvoiceHumanDTO hDto = new InvoiceHumanDTO();
		conn = DBManager.getConnection();
		String sql = "select * from invoicehuman where hInvoiceId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hInvoiceId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				hDto.sethInvoiceId(rs.getInt(1));
				hDto.sethName(rs.getString(2));
				hDto.sethTel(rs.getString(3));
				hDto.sethAddress(rs.getString(4));
				hDto.sethDate(rs.getString(5));
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
		return hDto;
	}
}
