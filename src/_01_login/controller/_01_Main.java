package _01_login.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/main.do")
public class _01_Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 로그인 상태에 따라 다른 메인 페이지가 출력 되어야하므로,
		// 어딘가에서 session.setAttribute 되어있는 id값을 불러와 01_main.jsp로 전송
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("menuId");
		
		request.setAttribute("id", id);
		
		RequestDispatcher dis = request.getRequestDispatcher("_01_login/01_main.jsp");
		dis.forward(request, response);
		
	}

}
