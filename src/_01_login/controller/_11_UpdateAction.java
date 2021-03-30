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

@WebServlet("/updateAction.do")
public class _11_UpdateAction extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	public void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		//10_update.jsp에서 전송받은 수정된 회원정보를 회원정보수정DAO로 보내어 기존 정보에 덮어씀
		String id     = (String)session.getAttribute("memId");
        String passwd = request.getParameter("passwd");
        String name   = request.getParameter("name");
        String tel    = request.getParameter("tel");
        String email  = request.getParameter("email");
        String field  = request.getParameter("field");
        String[] temp = request.getParameterValues("skill");
        String major  = request.getParameter("major");
        String skill  = "";
        
        for (int i=0; i<temp.length; i++) {
        	skill += temp[i];
        	if (i != temp.length - 1) {
        		skill += ",";
        	}
        }

        MemberDTO mdto = new MemberDTO();
        
        mdto.setPasswd(passwd);;
        mdto.setName(name);
        mdto.setTel(tel);
        mdto.setEmail(email);
        mdto.setField(field);
        mdto.setSkill(skill);
        mdto.setMajor(major);
        
		MemberDAO.getInstance().updateMember(id, mdto);
		
		//회원정보 수정 완료 후, 11_updateAction.jsp로 이동
		RequestDispatcher dis = request.getRequestDispatcher("_01_login/11_updateAction.jsp");
		dis.forward(request, response);
		
	}

}
