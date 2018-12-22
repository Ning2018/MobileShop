package myservlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.RequestDispatcher;

import mybean.Login;

public class HandleDelete extends HttpServlet {
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("gb2312");
		String delete = request.getParameter("delete");
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try{
			loginBean=(Login)session.getAttribute("loginBean");
			boolean b = loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
				response.sendRedirect("login.jsp");
			LinkedList<String> car = loginBean.getCar();
			car.remove(delete);
		}catch(Exception exp){
			response.sendRedirect("login.jsp");
		}
	request.getRequestDispatcher("lookShoppingCar.jsp").forward(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}

}
