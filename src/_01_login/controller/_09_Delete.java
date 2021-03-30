package _01_login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _01_login.dao.MemberDAO;

@WebServlet("/delete.do")
public class _09_Delete extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	public void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//로그인 되어있는 ID를 받아와서 회원탈퇴DAO로 보내어 DB에서 회원 정보 삭제
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		MemberDAO.getInstance().deleteMember(id);
		
		//회원 삭제 후, 세션 초기화
		session.invalidate();
		
		RequestDispatcher dis = request.getRequestDispatcher("_01_login/09_delete.jsp");
		dis.forward(request, response);
		
	}

}
