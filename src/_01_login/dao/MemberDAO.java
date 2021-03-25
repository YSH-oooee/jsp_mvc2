package _01_login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import _01_login.dto.MemberDTO;

public class MemberDAO {
	
	private MemberDAO() {}
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public Connection getConnection() {
		
		try {
			
			Context initCtx = new InitialContext();
			
			// lookup 메소드를 통헤 server.xml 파일에 접근하여 자바 환경 코드를 검색
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			// <Context>태그 안의 <Resource>환경 설정의 name이 jdbc/pool인 것을 검색
			DataSource ds = (DataSource)envCtx.lookup("jdbc/pool");
			
			conn = ds.getConnection();		//빨간줄 발생 시, catch를 Exception으로 변경
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	//회원가입
	public boolean joinMember(MemberDTO mdto) {
		
		boolean isJoin = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from member where id=?");
			pstmt.setString(1, mdto.getId());
			
			rs = pstmt.executeQuery();
			
			if (!rs.next()) {
				
				pstmt = conn.prepareStatement("insert into member(id, passwd, name, tel, email) values(?, ?, ?, ?, ?)");
				pstmt.setString(1, mdto.getId());
				pstmt.setString(2, mdto.getPasswd());
				pstmt.setString(3, mdto.getName());
				pstmt.setString(4, mdto.getTel());
				pstmt.setString(5, mdto.getEmail());
				
				pstmt.executeUpdate();
						
				isJoin = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) {} }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) {} }
			if (conn != null) { try { conn.close(); } catch (SQLException e) {} }
		}
		
		return isJoin;
		
	}
	
	//로그인
	public boolean loginMember(MemberDTO mdto) {
		
		boolean isLogin = false;
		System.out.println(mdto.getId() + "/" + mdto.getPasswd());
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from member where id=? and passwd=?");
			pstmt.setString(1, mdto.getId());
			pstmt.setString(2, mdto.getPasswd());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				isLogin = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) {} }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) {} }
			if (conn != null) { try { conn.close(); } catch (SQLException e) {} }
		}
		
		return isLogin;
		
	}

}
