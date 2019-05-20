package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class StorageDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	private static final Logger LOG = LoggerFactory.getLogger(StorageDAO.class);

	// 전체 상품 조회 (재고조사)
	public ArrayList<StorageDTO> getAllProducts() {
		String sql = "select pId, pName, pPrice, pQuantity, pAdminId, pState from storage;";
		ArrayList<StorageDTO> storageList = new ArrayList<StorageDTO>();
		conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				StorageDTO pDto = new StorageDTO();
				pDto.setpId(rs.getInt(1));
				pDto.setpName(rs.getString(2));
				pDto.setpPrice(rs.getInt(3));
				pDto.setpQuantity(rs.getInt(4));
				pDto.setpAdminId(rs.getInt(5));
				pDto.setpState(rs.getString(6));
				LOG.trace(pDto.toString());
				storageList.add(pDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllProducts(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return storageList;
	}

	// 상품 ID로 하나 검색
	public StorageDTO getOneProductById(int pId) {
		StorageDTO pDto = new StorageDTO();
		conn = DBManager.getConnection();
		String sql = "select * from storage where pId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pDto.setpId(rs.getInt(1));
				pDto.setpName(rs.getString(2));
				pDto.setpImgName(rs.getString(3));
				pDto.setpPrice(rs.getInt(4));
				pDto.setpQuantity(rs.getInt(5));
				pDto.setpAdminId(rs.getInt(6));
				pDto.setpState(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneProductById: Error Code : {}", e.getErrorCode());
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
		return pDto;
	}
	
	// 상품 이름으로 하나 검색
	public StorageDTO getOneProductByName(String pName) {
		StorageDTO pDto = new StorageDTO();
		conn = DBManager.getConnection();
		String sql = "select * from storage where pName=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pDto.setpId(rs.getInt(1));
				pDto.setpName(rs.getString(2));
				pDto.setpImgName(rs.getString(3));
				pDto.setpPrice(rs.getInt(4));
				pDto.setpQuantity(rs.getInt(5));
				pDto.setpAdminId(rs.getInt(6));
				pDto.setpState(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneProductByName(): Error Code : {}", e.getErrorCode());
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
		return pDto;
	}
	
	// 재고 수량 변경
	public void updateStorage(int pQuantity, String pState, int pId) {
		LOG.debug("");
		conn = DBManager.getConnection();
		String sql = "update storage set pQuantity=?, pState=? where pId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pQuantity);
			pstmt.setString(2, pState);
			pstmt.setInt(3, pId);
			pstmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateStorage() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 재고 상태 변경
	public void updateProductState(String pState, int pId) {
		LOG.debug("");
		conn = DBManager.getConnection();
		String sql = "update storage set pState=? where pId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pState);
			pstmt.setInt(2, pId);
			pstmt.executeUpdate();
			LOG.trace(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("updateProductState() Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 항목별 상품 조회
	public ArrayList<StorageDTO> getTitleProducts(int category, int page) {
		int offset = 0;
		String sql = null;
		ArrayList<StorageDTO> productList = new ArrayList<StorageDTO>();
		conn = DBManager.getConnection();
		
		if (page == 0) {
			sql = "select pId, pName, pImgName, pPrice, pQuantity, pState from storage where pAdminId=?;";
		} else {
			sql = "select pId, pName, pImgName, pPrice, pQuantity, pState from storage where pAdminId=? limit ?, 8;"; // ? 시작점, 10은 가져올 갯수
			offset = (page - 1) * 8;
		}
		try {
			pstmt = conn.prepareStatement(sql);
			if (page == 0) {
				pstmt.setInt(1, category);
			} else if (page != 0) {
				pstmt.setInt(1, category);
				pstmt.setInt(2, offset);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) { // 쿼리문 넣고, 읽어드리기
				StorageDTO pDto = new StorageDTO();
				pDto.setpId(rs.getInt(1));
				pDto.setpName(rs.getString(2));
				pDto.setpImgName(rs.getString(3));
				pDto.setpPrice(rs.getInt(4));
				pDto.setpQuantity(rs.getInt(5));
				pDto.setpState(rs.getString(6));
				LOG.trace(pDto.toString());
				productList.add(pDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getTitleProducts(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productList;
	}
	
	// 발주를 위한 페이지
	public ArrayList<StorageDTO> getAllProductsForOrder(int page, String pState) {
		int offset = 0;
		String sql = null;
		ArrayList<StorageDTO> productList = new ArrayList<StorageDTO>();
		conn = DBManager.getConnection();
		
		if (page == 0) {
			sql = "select p.pId, p.pName, s.sAdminId, a.aName, p.pQuantity, p.pPrice, p.pState "
					+ "from storage as p " 
					+ "inner join supplier as s "
					+ "on p.pId=s.sProductId "
					+ "inner join admins as a "
					+ "on a.aId=s.sAdminId "
					+ "where p.pState=? "
					+ "order by p.pId desc;";
		} else {
			sql = "select p.pId, p.pName, s.sAdminId, a.aName, p.pQuantity, p.pPrice, p.pState "
					+ "from storage as p " 
					+ "inner join supplier as s "
					+ "on p.pId=s.sProductId "
					+ "inner join admins as a "
					+ "on a.aId=s.sAdminId "
					+ "where p.pState=? "
					+ "order by p.pId desc limit ?, 10;";
			offset = (page - 1) * 10;
		}
		try {
			pstmt = conn.prepareStatement(sql);
			if (page != 0) {
				pstmt.setInt(1, offset);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) { // 쿼리문 넣고, 읽어드리기
				StorageDTO pDto = new StorageDTO();
				pDto.setpId(rs.getInt(1));
				pDto.setpName(rs.getString(2));
				pDto.setpSupplierId(rs.getInt(3));
				pDto.setpSupplierName(rs.getString(4));
				pDto.setpQuantity(rs.getInt(5));
				pDto.setpPrice(rs.getInt(6));
				pDto.setpState(rs.getString(7));
				LOG.trace(pDto.toString());
				productList.add(pDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllProductsForOrder(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productList;
	}

	// 각 물품(구매처)에 따른 창고 리스트 카운트
	public int getEachAdminIdCount(int category) {
		String sql = "select count(*) from storage where pAdminId=?;";
		conn = DBManager.getConnection();
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info(e.getMessage());
			LOG.info("getEachAdminIdCount(): Error Code : {}", e.getErrorCode());
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
	
	// 모든 창고 리스트 카운트
	public int getCount() {
		String sql = "select count(*) from storage;" ;
		conn = DBManager.getConnection();
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info(e.getMessage());
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
	
	// 재고 검색, 일부 단어 검색 시 여러개 검색될 우려에 리턴값 ArrayList으로 줌
	public ArrayList<StorageDTO> getSearchProduct(String word) {
		ArrayList<StorageDTO> stockList = new ArrayList<StorageDTO>();
		conn = DBManager.getConnection();
		String sql = "select pId, pName, pPrice, pQuantity, pState from storage where pName like '%?%';";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, word);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				StorageDTO pDto = new StorageDTO();
				pDto.setpId(rs.getInt(1));
				pDto.setpName(rs.getString(2));
				pDto.setpPrice(rs.getInt(3));
				pDto.setpQuantity(rs.getInt(4));
				pDto.setpState(rs.getString(5));
				LOG.trace(pDto.toString());
				stockList.add(pDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getSearchProduct(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return stockList;
	}
	
	// modal 세부사항 출력
	public StorageDTO getModal(int pId) {
		StorageDTO modalView = new StorageDTO();
		conn = DBManager.getConnection();
		String sql = "select pId, pName, pPrice, pQuantity from storage where pId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				modalView.setpId(rs.getInt(1));
				modalView.setpName(rs.getString(2));
				modalView.setpPrice(rs.getInt(3));
				modalView.setpQuantity(rs.getInt(4));
				LOG.trace(modalView.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getModal(): Error Code : {}", e.getErrorCode());
		} finally {
			try {
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return modalView;
	}
}