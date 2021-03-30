package _01_login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
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
	
	// 1. 회원가입
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
	
	// 2. 로그인
	public boolean loginMember(MemberDTO mdto) {
		
		boolean isLogin = false;
		
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
	
	// 3. 회원정보 수정 DAO
    public void updateMember(String id, MemberDTO mdto) {
        
    	try {
    		
            conn = getConnection();
            //받아온 ID의 정보를 수정
            pstmt = conn.prepareStatement("UPDATE MEMBER SET PASSWD=?, NAME=?, TEL=?, EMAIL=?, FIELD=?, SKILL=?, MAJOR=? WHERE ID=?");
            pstmt.setString(1, mdto.getPasswd());
            pstmt.setString(2, mdto.getName());
            pstmt.setString(3, mdto.getTel());
            pstmt.setString(4, mdto.getEmail());
            pstmt.setString(5, mdto.getField());
            pstmt.setString(6, mdto.getSkill());
            pstmt.setString(7, mdto.getMajor());
            pstmt.setString(8, id);
            
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null)  {try {conn.close();} catch (SQLException e) {}}
        }
    }    
    
    // 4. 한명의 회원의 정보 조회 DAO
    public MemberDTO getOneMemberInfo(String id) {
        
    	MemberDTO mdto = null;
        
    	try {
    		
            conn = getConnection();
            //받아온 ID의 DB정보를 호출
            pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID=?");
            pstmt.setString(1, id);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	
            	mdto = new MemberDTO();
            	
            	mdto.setId(rs.getString("id"));
            	mdto.setPasswd(rs.getString("passwd"));;
            	mdto.setName(rs.getString("name"));
            	mdto.setTel(rs.getString("tel"));
            	mdto.setEmail(rs.getString("email"));
            	mdto.setField(rs.getString("field"));
            	mdto.setSkill(rs.getString("skill"));
            	mdto.setMajor(rs.getString("major"));
            	
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	if(rs != null) 	  {try {rs.close();}    catch (SQLException e) {}}            
        	if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null)  {try {conn.close();}  catch (SQLException e) {}}
        }
    	
        return mdto;
    
    }    
    
    // 5. 회원 탈퇴 DAO
    public void deleteMember(String id) {
    	
    	try {
    		
    		conn = getConnection();
    		//해당 ID의 정보를 MEMBER 테이블에서 삭제
    		pstmt = conn.prepareStatement("DELETE FROM MEMBER WHERE ID=?");
    		pstmt.setString(1, id);
    		
    		pstmt.executeUpdate();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
    		if (conn != null) {try {conn.close();} catch (SQLException e) {}}
    	}
    	
    }    
    
    // 6. 입사지원 DAO
    public void apply(String id, String field, String skill, String major) {
 
        try {
            
            conn = getConnection();
            //해당 ID의 지원분야, 기술, 전공 정보를 DB에 저장 = 입사지원
            pstmt = conn.prepareStatement("UPDATE MEMBER SET FIELD=?, SKILL=?, MAJOR=? WHERE ID=?");
            pstmt.setString(1, field);
            pstmt.setString(2, skill);
            pstmt.setString(3, major);
            pstmt.setString(4, id);
            
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
            if(conn != null)  {try {conn.close();} catch (SQLException e) {}}
        }
        
    }

}
