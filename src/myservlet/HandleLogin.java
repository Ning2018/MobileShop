package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.Login;

/**
 * Servlet implementation class HandleLogin
 */
@WebServlet("/HandleLogin")
public class HandleLogin extends HttpServlet {

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e){}
	}

	public String handleString(String s){
		try{byte bb[] = s.getBytes("iso-8859-1");
		s= new String(bb);
		}
		catch(Exception e){}
		return s;
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
		String uri="jdbc:mysql:///mobileshop?user=root&password=root";
		Connection con;
		PreparedStatement sql;
		
		String logname=request.getParameter("logname").trim();
		String password=request.getParameter("password").trim();
		
		logname=handleString(logname);
		password=handleString(password);
		
		boolean boo=(logname.length()>0)&&(password.length()>0);
		try{
			con= DriverManager.getConnection(uri);
			String condition = "select* from user where logname='"+logname+"'and password='"+password+"'";
			sql=con.prepareStatement(condition);
			if(boo){
				ResultSet rs= sql.executeQuery();
				boolean m = rs.next();
				if(m==true){
					success(request,response,logname,password);
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				else{
					String backNews="你输入的用户名不存在，或密码不匹配";
					fail(request,response,logname,backNews);
				}
			}
			else{
				String backNews="请输入用户名和密码";
				fail(request,response,logname,backNews);				
			}
			con.close();
		}
		catch(Exception e){
			String backNews=""+e;
			fail(request,response,logname,backNews);				
		}
	}

	public void success(HttpServletRequest request, HttpServletResponse response, String logname, String password){
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try{
			loginBean = (Login)session.getAttribute("loginBean");
			if(loginBean == null){
				loginBean = new Login();
				session.setAttribute("loginBean", loginBean);
			}
			loginBean=(Login) session.getAttribute("loginBean");
			String name=loginBean.getLogname();
			if(name.equals(logname)){
				loginBean.setBackNews(logname+"已经登陆了");
			}
			else{
				loginBean.setBackNews(logname+"登陆成功");
				loginBean.setLogname(logname);
			}
		}
		catch(Exception e){
		    String backNews=""+e;
		    loginBean.setBackNews(backNews);
		}
	}
	
	public void fail(HttpServletRequest request, HttpServletResponse response, String logname, String backNews){
		response.setContentType("text/html;charset=GB2312");
		try{
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+logname+"登录反馈结果<br>"+backNews+"</h2>");
			out.println("返回登录页面或主页<br>");
			out.println("<a href = login.jsp>登录页面</a>");
			out.println("<br><a href=../index.jsp>主页</a>");
			out.println("</body></html>");
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
}
