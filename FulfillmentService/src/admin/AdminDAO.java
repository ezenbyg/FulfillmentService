package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager; 

public class AdminDAO {
	private static final Logger LOG = LoggerFactory.getLogger(AdminDAO.class);
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public static final int ID_PASSWORD_MATCH = 1;
	public static final int ID_DOES_NOT_EXIST = 2;
	public static final int PASSWORD_IS_WRONG = 3;
	public static final int DATABASE_ERROR = -1;
	public static final int 창고관리자 = 10001;
	public static final int JH쇼핑몰 = 20001;
	public static final int SW쇼핑몰 = 20002;
	public static final int GJ쇼핑몰 = 20003;
	public static final int 무신사 = 30001;
	public static final int 와구와구 = 30002;
	public static final int 하이마트 = 30003;
	public static final int 언더아머 = 30004;
	public static final int 이케아 = 30005;
	public static final int 경기물류 = 40001;
	public static final int 중부물류 = 40002;
	public static final int 영남물류 = 40003;
	public static final int 서부물류 = 40004;

	public int verifyIdPassword(String id, String password) {
		return ID_PASSWORD_MATCH;
	}
	
	public ArrayList<AdminDTO> getAllAdmins() {
		ArrayList<AdminDTO> aList = new ArrayList<AdminDTO>();
		conn = DBManager.getConnection();
		String sql = "select * from admins;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				AdminDTO aDto = new AdminDTO();
				aDto.setaId(rs.getInt(1));
				aDto.setaUserId(rs.getString(2));
				aDto.setaName(rs.getString(3));
				aDto.setaPassword(rs.getString(4));
				LOG.trace(aDto.toString());
				aList.add(aDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getAllAdmins(): Error Code : {}", e.getErrorCode());
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
		return aList;
	}
	
	public String getLastId() {
		String aUserId = null;
		conn = DBManager.getConnection();
		String sql = "select a_userId from admins order by aUserId desc limit 1;";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				aUserId = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getLastId(): Error Code : {}", e.getErrorCode());
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
		return aUserId;
	}
	
	public AdminDTO getOneAdmin(String aUserId) {
		AdminDTO aDto = new AdminDTO();
		conn = DBManager.getConnection();
		String sql = "select * from admins where aUserId=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aUserId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				aDto.setaId(rs.getInt(1));
				aDto.setaUserId(aUserId);
				aDto.setaName(rs.getString(3));
				aDto.setaPassword(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneCustomer(): Error Code : {}", e.getErrorCode());
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
		return aDto;
	}
	
	public AdminDTO getOneAdminByName(String aName) {
		AdminDTO aDto = new AdminDTO();
		conn = DBManager.getConnection();
		String sql = "select * from admins where aName=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aName);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				aDto.setaId(rs.getInt(1));
				aDto.setaUserId(rs.getString(2));
				aDto.setaName(aName);
				aDto.setaPassword(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.info("getOneAdminByName(): Error Code : {}", e.getErrorCode());
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
		return aDto;
	}
}
