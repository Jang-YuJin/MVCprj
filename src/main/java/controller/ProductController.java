package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProductDTO;
import service.ProductService;

@WebServlet("/shop/*")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductService service;
       
	public void init(ServletConfig config) throws ServletException {
		service = new ProductService();
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
			if (action.equals("/main.do")) {
				List<ProductDTO> list = service.getAllProductList();
				List<String> upper = service.getUpperCode(list);
				List<String> sub = service.getSubCode(list);
				List<String> num = service.getNumCode(list);
				request.setAttribute("product", list);
				request.setAttribute("upper", upper);
				request.setAttribute("sub", sub);
				request.setAttribute("num", num);
				
				nextPage = "/index.jsp";
				
			}else if(action.equals("/list.do")) {
				String code = request.getParameter("code");
				List<ProductDTO> list = service.getCodeProductList(code);
				List<String> upper = service.getUpperCode(list);
				List<String> sub = service.getSubCode(list);
				List<String> num = service.getNumCode(list);
				request.setAttribute("product", list);
				request.setAttribute("upper", upper);
				request.setAttribute("sub", sub);
				request.setAttribute("num", num);
//				페이징 구현하기
//				int lastPage = list.size() % 12 == 0 ? list.size() / 12 : list.size() / 12 + 1;
//				int position = lastPage % 5 == 0 ? lastPage / 5 : lastPage / 5 + 1;//이전과 다음 사이에 나오는 페이지 수를 5개로 함
//				int begin = (position - 1) * 5 + 1;
//				int end = position * 5;
				
				nextPage = "/shoplist.jsp";
				
			}else if(action.equals("/detail.do")) {
				String code = request.getParameter("code");
				List<ProductDTO> list = service.getCodeProductList(code);
				List<String> upper = service.getUpperCode(list);
				List<String> sub = service.getSubCode(list);
				List<String> num = service.getNumCode(list);
				request.setAttribute("product", list);
				request.setAttribute("upper", upper);
				request.setAttribute("sub", sub);
				request.setAttribute("num", num);
				
				nextPage = "/shopdetail.jsp";
				
			}
			else {
				nextPage = "/err.jsp";
			}
			
			System.out.println("nextPage: " + nextPage);
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
//	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("doHandle 들어옴");
//		String next = "";
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=UTF-8");
//		String path = request.getPathInfo();
//		
//		if(path.equals("/main.do")) {
//			List<ProductDTO> list = service.getAllProductList();
//			List<String> upper = service.getUpperCode(list);
//			List<String> sub = service.getSubCode(list);
//			List<String> num = service.getNumCode(list);
//			
//			System.out.println(list.size());
//			System.out.println(upper.size());
//			System.out.println(sub.size());
//			System.out.println(num.size());
//			
//			request.setAttribute("list", list);
//			request.setAttribute("upper", upper);
//			request.setAttribute("sub", sub);
//			request.setAttribute("num", num);
//			
//			next = "/index.jsp";
//		}
//		
//		System.out.println("next : " + next);
//		RequestDispatcher dispatch = request.getRequestDispatcher(next);
//		dispatch.forward(request, response);
//		System.out.println("doHandle 나감");
//	}

}
