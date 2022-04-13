package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MemberDTO;
import service.MemberService;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberService service;
       
	public void init(ServletConfig config) throws ServletException {
		service = new MemberService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("action : " + action);
		
		try {
			if (action.equals("/join.do")) {
				nextPage = "/join.jsp";
			}else if(action.equals("/joinConfirm.do")){
				MemberDTO dto = new MemberDTO();
				String name = request.getParameter("name");
				String id = request.getParameter("id");
				String password = request.getParameter("pw");
				String passwordC = request.getParameter("pw_ch");
				String tel = request.getParameter("tel");
				String address = request.getParameter("address");
				String[] birth = request.getParameterValues("birth");
				String birthIn = birth[0] + "-" + birth[1] + "-" + birth[2];
				
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				if("".equals(name) || name == null ||
						"".equals(id) || id == null ||
						"".equals(password) || password == null ||
						"".equals(passwordC) || passwordC == null ||
						"".equals(tel) || tel == null ||
						"".equals(address) || address == null ||
						birth == null || "".equals(birth[0]) || "".equals(birth[1]) || "".equals(birth[2])) {
					out.println("<script>alert('모든 내용을 입력해주세요.'); location.href='/mpro/member/join.do';</script>");
					out.close();
//					nextPage = "/join.do";
				}else {
					if(password.equals(passwordC)) {
						System.out.println(service.isIdUnique(id));
						if(service.isIdUnique(id)) {
							out.println("<script>alert('아이디가 중복됩니다.'); location.href='/mpro/member/join.do';</script>");
							out.close();
							System.out.println("아이디 중복 여기로 오니?");
//							nextPage = "/join.do";
						}else {
							dto.setName(name);
							dto.setId(id);
							dto.setPassword(passwordC);
							dto.setTel(tel);
							dto.setAddress(address);
							dto.setBirth(Date.valueOf(birthIn));
							int result = service.setMemberInfo(dto);
							System.out.println("setMemberInfo result : " + result);
							nextPage = "/shop/main.do";
						}
					}else{
						out.println("<script>alert('비밀번호와 비밀번호 확인이 다릅니다.'); location.href='/mpro/member/join.do';</script>");
						out.close();
//						nextPage = "/join.do";
					}
				}
				
			}else {
				nextPage = "/err.jsp";
			}
			
			System.out.println("nextPage: " + nextPage);
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
