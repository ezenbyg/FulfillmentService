package charge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class ChargeDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ChargeDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// 송장 테이블에서 정보 빼와서 어디에 저장?
	public ArrayList<ChargeDTO> getAllChargeList() {
		ArrayList<ChargeDTO> gList = new ArrayList<ChargeDTO>();
		conn = DBManager.getConnection();
		String sql = "select vId,vProductName,vQuantity,vDate from invoice;";
		// 송장 아이디, 상품이름, 상품양, 송장 테이블에서 가져옴
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// rs.get은 위에서 불러들인 송장테이블을 가져오는 것. 그걸 charge테이블에 저장
				ChargeDTO gDto = new ChargeDTO();
				gDto.setgInvoiceId(rs.getInt(1)); // 송장아이디
				gDto.setgProductName(rs.getString(2));// 상품이름
				gDto.setgQuantity(rs.getInt(3));// 상품수량
				gDto.setgPrice(rs.getInt(4));// 상품 개당 가격
				gDto.setgDate(rs.getString(5));// 송장 보낸 시간
				gList.add(gDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllChargeList(): Error Code : {}", e.getErrorCode());
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
		return gList; // invoice에 빼온 정보 chargeDTO타입으로 출력
		//ChargeProc에서 생성후
	}
}
