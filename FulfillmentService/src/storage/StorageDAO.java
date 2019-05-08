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
	public static final int MUSINSA = 30001;
	public static final int WHAGUWHAGU = 30002;
	public static final int HIMART = 30003;
	public static final int UNDERARMOUR = 30004;
	public static final int IKEA = 30005;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	private static final Logger LOG = LoggerFactory.getLogger(StorageDAO.class);
	
	public ArrayList<StorageDTO> getProducts(int category) { // 카테고리는 각 권리자 아이디로 초기화 시켜준다. 
		ArrayList<StorageDTO> productList = new ArrayList<StorageDTO>();
		conn = DBManager.getConnection();
		String sql = "select  *  from storage where pAdminId= ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
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
			LOG.info("getProducts(): Error Code : {}", e.getErrorCode());
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
	
	public StorageDTO getOneProductList(int pId) { 
		StorageDTO pDto = new StorageDTO();
		conn = DBManager.getConnection();
		String sql = "select * from storage where pId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
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
	
	public void updateStorage(int pQuantity, int pId) {
		LOG.debug("");
		PreparedStatement pStmt = null;
		conn = DBManager.getConnection();
		String sql = "update storage set pQuantity=? where pId=?;";
		pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, pQuantity);
			pStmt.setInt(2, pId);
			pStmt.executeUpdate();
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
}