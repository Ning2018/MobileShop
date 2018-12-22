package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.Login;

/**
 * Servlet implementation class PutGoodsToCar
 */
@WebServlet("/PutGoodsToCar")
public class PutGoodsToCar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PutGoodsToCar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("GB2312");
		String goods=request.getParameter("java");
		System.out.println(goods);
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try{
			loginBean = (Login)session.getAttribute("loginBean");
			boolean b=loginBean.getLogname().length()==0;
			if(b)
				response.sendRedirect("login.jsp");
			LinkedList<String> car = loginBean.getCar();
			car.add(goods);
			speakSomeMess(request, response, goods);
		}
		catch(Exception e){
			response.sendRedirect("login.jsp");
		}
	}

	private void speakSomeMess(HttpServletRequest request, HttpServletResponse response, String goods) {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=GB2312");
		try{
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+goods+"放入购物车</h2>");
			out.println("查看购物车或返回<br>");
			out.println("<a href= lookShoppingCar.jsp>查看购物车</a>");
			out.println("<br><a href=byPageShow.jsp>主页</a>");
			out.println("</body></html>");
		}
		catch(IOException e){}
	}

}
