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

	private static final Logger LOG = LoggerFactory.getLogger(PayDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	PayDTO yDto;

	// 운송회사 출고List
	public ArrayList<PayDTO> getTransportList() {
		conn = DBManager.getConnection();
		String sql = "select p_release.rTransportId, p_release.rDate, pay.yState " + "from p_release inner join pay "
				+ "on p_release.rTransportId=pay.yAdminId order by rTransportId desc;";

		pstmt = null;
		ArrayList<PayDTO> transportPayList = new ArrayList<PayDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				yDto = new PayDTO();
				yDto.setrTransportId(rs.getInt(1));
				yDto.setyDate(rs.getString(2));
				yDto.setyState(rs.getString(3));
				transportPayList.add(yDto);
			}
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return transportPayList;
	}

	//total 가격 메소드
	public int calcuTotalPrice(ArrayList<PayDTO> payList) {
		int totalPrice = 0;
		for(PayDTO pay : payList) {
			totalPrice += pay.getyPrice();
		}
		return totalPrice;
	}
	
	// payList 구매처 운송회사 리스트 출력
	public ArrayList<PayDTO> getPayList(int firstAdminId) {
		conn = DBManager.getConnection();
		String sql = "select pay.yId, pay.yBankId, pay.yAdminId, pay.yPrice, pay.yDate, pay.yState, admins.aName  " 
				+ " from pay " 
				+ " inner join admins " 
				+ " on pay.yAdminId = admins.aId;";

		LOG.debug("61-fristAdminId :" + firstAdminId);
		ArrayList<PayDTO>  payList = new ArrayList<PayDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (firstAdminId == 3) {
				while (rs.next()) {
					yDto = new PayDTO();
					yDto.setyId(rs.getInt(1));
					yDto.setyBankId(rs.getString(2));
					yDto.setyAdminId(rs.getInt(3));
					yDto.setyPrice(rs.getInt(4));
					yDto.setyDate(rs.getString(5));
					yDto.setyState(rs.getString(6));
					yDto.setaName(rs.getString(7));
					
					String id = String.valueOf(rs.getInt(3));
					String firstAdminIdString = String.valueOf(firstAdminId);
					LOG.debug("74- id.charAt(0):" + id.charAt(0) + " , " + firstAdminIdString);
					if (!firstAdminIdString.equals(id.substring(0, 1))) {
						LOG.debug("78- check");
						continue; // firstAdminId랑 다른 값이 올 때 저장 아니면 다시 위로
					} else {
						LOG.debug("81- check");
						payList.add(yDto);
					}
				}
			} else if (firstAdminId == 4) {
				while (rs.next()) {
					yDto = new PayDTO();
					yDto.setyId(rs.getInt(1));
					yDto.setyBankId(rs.getString(2));
					yDto.setyAdminId(rs.getInt(3));
					yDto.setyPrice(rs.getInt(4));
					yDto.setyDate(rs.getString(5));
					yDto.setyState(rs.getString(6));
					yDto.setaName(rs.getString(7));
					
					String id = String.valueOf(rs.getInt(3));
					String firstAdminIdString = String.valueOf(firstAdminId);
					LOG.debug("74- id.charAt(0):" + id.charAt(0) + " , " + firstAdminIdString);
					if (!firstAdminIdString.equals(id.substring(0, 1))) {
						LOG.debug("115- check");
						continue; // firstAdminId랑 다른 값이 올 때 저장 아니면 다시 위로
					} else {
						LOG.debug("118- check");
						payList.add(yDto);
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("에러!");
			LOG.debug(e.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return payList;
	}

	// 검색된 운송회사의 지급 총액
	public int totalTransportPay(int rTransportId) {
		conn = DBManager.getConnection();
		String sql = "select count(?) from p_release";
		pstmt = null;
		int transportFee = 5000;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rTransportId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("totalTransportPay(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count * transportFee;
	}

	// 검색된 구매처의 지급 총액
	public int totalSupplierPay(int yAdminId) {
		conn = DBManager.getConnection();
		String sql = "select sum(yPrice) from pay where yAdminId = ?";
		int totalPrice = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, yAdminId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				totalPrice = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("totalSupplierPay(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return totalPrice;
	}

	// 검색된 운송회사 출고List
	public ArrayList<PayDTO> searchTransprotList(int searchId) {
		conn = DBManager.getConnection();
		String sql = "select p_release.rTransportId, p_release.rDate, pay.yState " + "from p_release inner join pay "
				+ "on p_release.rTransportId=pay.yAdminId " + "where p_release.rTransportId = ?"
				+ "order by rTransportId desc;";

		pstmt = null;
		ArrayList<PayDTO> searchTransportPayList = new ArrayList<PayDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, searchId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				yDto = new PayDTO();
				yDto.setrTransportId(rs.getInt(1));
				yDto.setyDate(rs.getString(2));
				yDto.setyState(rs.getString(3));
				searchTransportPayList.add(yDto);
			}
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return searchTransportPayList;
	}

	// 검색된 구매처 출고List
	public ArrayList<PayDTO> searchPurchasingList(int searchId) {
		conn = DBManager.getConnection();
		String sql = "select p_order.oAdminId, p_order.oDate, pay.yState " + "from p_release inner join pay "
				+ "on p_order.oAdminId=pay.yAdminId " + "where p_order.oAdminId = ?" + "order by oAdminId desc;";

		pstmt = null;
		ArrayList<PayDTO> searchPurchasingPayList = new ArrayList<PayDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, searchId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				yDto = new PayDTO();
				yDto.setrTransportId(rs.getInt(1));
				yDto.setyDate(rs.getString(2));
				yDto.setyState(rs.getString(3));
				searchPurchasingPayList.add(yDto);
			}
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return searchPurchasingPayList;
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
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// bank 잔액 조회
	public PayDTO getBank(int rTransportId) {
		conn = DBManager.getConnection();
		String sql = "select bId, bBalance from bAdminId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rTransportId);
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
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return yDto;
	}

	// 지급 버튼 눌렀을 때 bank 테이블에서 돈 계산
	public void updateBank(int totalPrice, int rTransportId) {
		LOG.debug("");
		pstmt = null;
		conn = DBManager.getConnection();
		String sql = "update bank set bBalance=? where bAdminId=?;";
		pstmt = null;

		yDto = getBank(rTransportId);
		int balance = yDto.getbBalance();
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
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
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
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return yList;
	}

	public int getCount() {
		String sql = "select count(*) from pay;";
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
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public PayDTO getTotalPrice(int adminId) {
		conn = DBManager.getConnection();
		String sql = "select bank.bId, sum(invoice.vPrice) " + 
				"from bank " + 
				"inner join invoice " + 
				"on bank.bAdminId=invoice.vAdminId " + 
				"where bank.bAdminId = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, adminId);
			rs = pstmt.executeQuery();
				while (rs.next()) {
					PayDTO yDto = new PayDTO();
					yDto.setyBankId(rs.getString(1));
					yDto.setSum(rs.getInt(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				LOG.info("getTotalPrice(): Error Code : {}", e.getErrorCode());
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
			return yDto;
		}
	}