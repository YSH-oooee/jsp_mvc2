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

@WebServlet("/update.do")
public class _10_Update extends HttpServlet {
	
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
		
		//해당 ID의 회원 정보 호출
		MemberDTO mdto = MemberDAO.getInstance().getOneMemberInfo(id);
		
		//저장된 지원 분야가 있다면 >> 입사지원 후, 수정
		if (mdto.getField() != null) {
			//기존 정보의 기술을 저장
			String[] skills = mdto.getSkill().split(",");
			//기존에 저장된 기술에 true
			for (String skill : skills) {
				if (skill.equals("html")) 		request.setAttribute("html", true);
				if (skill.equals("css")) 		request.setAttribute("css", true);
				if (skill.equals("javascript")) request.setAttribute("javascript", true);
				if (skill.equals("java")) 		request.setAttribute("java", true);
				if (skill.equals("jsp")) 		request.setAttribute("jsp", true);
				if (skill.equals("spring")) 	request.setAttribute("spring", true);
			}
			
			request.setAttribute("mdto", mdto);
			request.setAttribute("isFirstApply", false);	//처음 지원 아님
			
		}
		else {
			request.setAttribute("isFirstApply", true);		//처음 지원
		}
		
		//회원 정보와 체크박스 유무, 지원여부를 10_update.jsp로 전송
		RequestDispatcher dis = request.getRequestDispatcher("_01_login/10_update.jsp");
		dis.forward(request, response);
		
	}

}
