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
		String sql = "select pId, pName, pQuantity from storage;";
		ArrayList<StorageDTO> storageList = new ArrayList<StorageDTO>();
		conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				StorageDTO pDto = new StorageDTO();
				pDto.setpId(rs.getInt(1));
				pDto.setpName(rs.getString(2));
				pDto.setpQuantity(rs.getInt(3));
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
	public StorageDTO getOneProductList(int pId) {
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneProductList(): Error Code : {}", e.getErrorCode());
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
	public void updateStorage(int pQuantity, int pId) {
		LOG.debug("");
		conn = DBManager.getConnection();
		String sql = "update storage set pQuantity=? where pId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pQuantity);
			pstmt.setInt(2, pId);
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

	// 항목별 상품 조회
	public ArrayList<StorageDTO> getTitleProducts(int category, int page) {
		int offset = 0;
		String sql = null;
		ArrayList<StorageDTO> productList = new ArrayList<StorageDTO>();
		conn = DBManager.getConnection();
		
		if (page == 0) {
			sql = "select * from storage where pAdminId=?;";
		} else {
			sql = "select * from storage where pAdminId=? limit ?, 8;"; // ? 시작점, 10은 가져올 갯수
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
				pDto.setpAdminId(rs.getInt(6));
				LOG.trace(pDto.toString());
				productList.add(pDto);
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
		return productList;
	}

	public int getCount(int category) {
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
		String sql = "select pId, pName, pQuantity from storage where pName like '%?%';";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, word);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				StorageDTO pDto = new StorageDTO();
				pDto.setpId(rs.getInt(1));
				pDto.setpName(rs.getString(2));
				pDto.setpQuantity(rs.getInt(3));
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
}