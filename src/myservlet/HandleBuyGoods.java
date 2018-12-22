package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;

import mybean.Login;

public class HandleBuyGoods extends HttpServlet {
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("gb2312");
		String buyGoodsMess=request.getParameter("buy");		
		if(buyGoodsMess==null||buyGoodsMess.length()==0){
			fail(request,response,"���ﳵû����Ʒ���޷����ɶ���");
			return;
		}
		String price = request.getParameter("priceSum");
		System.out.println("in HandleBuyGoods,priceSum"+price);
		if(price==null||price.length()==0){
			fail(request,response,"û�м���۸�ͣ��޷����ɶ���");
			return;
		}
		float sum = Float.parseFloat(price);
		Login loginBean =  null;
		HttpSession session = request.getSession(true);
		try{
			loginBean = (Login)session.getAttribute("loginBean");
			boolean b = loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
				response.sendRedirect("login.jsp");
		}catch(Exception exp){
			response.sendRedirect("login.jsp");
		}
		
		String url="jdbc:mysql://127.0.0.1/mobileshop?user=root&password=root";
		Connection con;
		PreparedStatement sql;
		try{
			con=DriverManager.getConnection(url);
			String insertCondition = "INSERT INTO orderform VALUES(?,?,?,?)";
			sql=(PreparedStatement) con.prepareStatement(insertCondition);
			sql.setInt(1, 0);
			sql.setString(2, loginBean.getLogname());
			sql.setString(3, buyGoodsMess);
			sql.setFloat(4, sum);
			sql.executeUpdate();
			LinkedList<String> car = loginBean.getCar();
			car.clear();
			success(request,response, "���ɶ����ɹ�");
			
			
		}catch(SQLException exp){
			fail(request,response,"���ɶ���ʧ��"+exp);
		}
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}

	private void success(HttpServletRequest request, HttpServletResponse response, String backNews) {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=GB2312");
		try{
			PrintWriter out=response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("������ҳ");
			out.println("<a href=index.jsp>��ҳ</a>");
			out.println("<br>�鿴����");
			out.println("<a href=lookOrderForm.jsp>�鿴����</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}

	private void fail(HttpServletRequest request, HttpServletResponse response, String backNews) {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=GB2312");
		try{
			PrintWriter out=response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("������ҳ");
			out.println("<a href=index.jsp>��ҳ</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}

}
