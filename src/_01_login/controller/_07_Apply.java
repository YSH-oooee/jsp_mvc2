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
import _01_login.dto.MemberDTO;

@WebServlet("/apply.do")
public class _07_Apply extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	public void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//로그인 되어있는 ID를 받아옴
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		//로그인이 되어있다면, 해당 회원 정보를 불러와서 request에 저장
		if (id != null) {
			MemberDTO mdto = MemberDAO.getInstance().getOneMemberInfo(id);
			request.setAttribute("mdto", mdto);
		} 
		
		//request에 저장된 회원 정보를 07_apply.jsp로 전송
		RequestDispatcher dis = request.getRequestDispatcher("_01_login/07_apply.jsp");
		dis.forward(request, response);
		
	}

}
