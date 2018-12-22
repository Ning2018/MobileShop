package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;
import com.sun.rowset.CachedRowSetImpl;

import mybean.DataByPage;

public class SearchByCondition extends HttpServlet {
	
	CachedRowSetImpl rowSet = null;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.setCharacterEncoding("gb2312");
		String searchMess = request.getParameter("searchMess");
		String radioMess=request.getParameter("radio");
		if(searchMess==null||searchMess.length()==0){
			fail(request,response,"没有查询信息，无法查询");
			return;
		}
		String condition="";
		if(radioMess.equals("mobile_version")){
			condition="SELECT * FROM mobileForm where mobile_version ='"+searchMess+"'";
		}
		else if(radioMess.equals("mobile_name")){
			condition="SELECT * FROM mobileForm where mobile_name LIKE '%"+searchMess+"%'";
		}
		else if(radioMess.equals("mobile_price")){
			double max=0,min=0;
			String regex="[^0123456789.]";
			String[] priceMess=searchMess.split(regex);
			if(priceMess.length==1){
				max=min=Double.parseDouble(priceMess[0]);
			}
			else if(priceMess.length==2){
				min=Double.parseDouble(priceMess[0]);
				max=Double.parseDouble(priceMess[1]);
				if(max<min){
					double t=max;
					max=min;
					min=t;
				}
			}
			else{
				fail(request,response,"输入的价格格式有错误");
				return;
			}
			condition="SELECT * FROM mobileForm where mobile_price<="+max+"AND mobile_price>="+min;
		}
		HttpSession session = request.getSession(true);
		Connection con=null;
		DataByPage dataBean=null;
		try{
			dataBean=(DataByPage) session.getAttribute("dataBean");
			if(dataBean==null){
				dataBean= new DataByPage();
				session.setAttribute("dataBean", dataBean);
			}
		}catch(Exception exp){
			dataBean = new DataByPage();
			session.setAttribute("dataBean", dataBean);
		}
		String url="jdbc:mysql://127.0.0.1/mobileshop?user=root&password=root";
		try{
			con=DriverManager.getConnection(url);
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = sql.executeQuery(condition);
			rowSet=new CachedRowSetImpl();
			System.out.println("rowSet,"+rowSet);
			rowSet.populate(rs);
			dataBean.setRowSet(rowSet);
			con.close();
			response.sendRedirect("byPageShow.jsp");
		}catch(SQLException exp){
		}
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}
	
	
	private void fail(HttpServletRequest request, HttpServletResponse response, String backNews) {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=GB2312");
		try{
			PrintWriter out=response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("返回");
			out.println("<a href=searchMobile.jsp>查询手机</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}
	
}
