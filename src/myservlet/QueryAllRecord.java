package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

import mybean.DataByPage;

/**
 * Servlet implementation class QueryAllRecord
 */
@WebServlet("/QueryAllRecord")
public class QueryAllRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CachedRowSetImpl rowSet = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryAllRecord() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e){}
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
		String idNumber = request.getParameter("fenleiNumber");
		if(idNumber==null) idNumber="0";
		int id=Integer.parseInt(idNumber);
		HttpSession session = request.getSession(true);
		DataByPage dataBean = null;
		try{
			dataBean = (DataByPage) session.getAttribute("dataBean");
			if(dataBean==null){
				dataBean = new DataByPage();
				session.setAttribute("dataBean", dataBean);
			}
		}
		catch(Exception exp){
			dataBean = new DataByPage();
			session.setAttribute("dataBean", dataBean);
		}
		
		String uri="jdbc:mysql:///mobileshop?user=root&password=root";
		Connection con;
		PreparedStatement sql;
		
		try{
			con= DriverManager.getConnection(uri);
			String condition = "select* from mobileForm where classify_id='"+id+"'";
			sql=con.prepareStatement(condition);
			
			ResultSet rs = sql.executeQuery();
			
			rowSet = new CachedRowSetImpl();
			rowSet.populate(rs);
			System.out.println(rowSet);
			dataBean.setRowSet(rowSet);
			con.close();
			response.sendRedirect("byPageShow.jsp");
	}
		catch(SQLException e){}
	}
}
