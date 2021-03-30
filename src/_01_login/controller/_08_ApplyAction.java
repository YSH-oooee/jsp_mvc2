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

@WebServlet("/applyAction.do")
public class _08_ApplyAction extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	public void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//로그인 되어있는 ID를 받아옴
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		//07_apply.jsp에서 입력받은 추가정보를 입사지원 DAO로 전송하여 DB에 저장(ID, 분야, 전공, 기술)
		String field = request.getParameter("field");
		String major = request.getParameter("major");

		String[] temp = request.getParameterValues("skill");
		String skill = "";
		for (int i=0; i<temp.length; i++) {
			skill += temp[i];
			if (i != temp.length - 1) {
				skill += ",";
			}
		}
		
		MemberDAO.getInstance().apply(id, field, skill, major);
		
		RequestDispatcher dis = request.getRequestDispatcher("_01_login/08_applyAction.jsp");
		dis.forward(request, response);
		
	}

}
