package myservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybean.Register;

/**
 * Servlet implementation class HandleRegister
 */
@WebServlet("/HandleRegister")
public class HandleRegister extends HttpServlet {
//	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleRegister() {
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
		Register userBean = new Register();
		request.setAttribute("userBean", userBean);
		
		String logname=request.getParameter("logname").trim();
		String password=request.getParameter("password").trim();
		String again_password=request.getParameter("again_password").trim();
		String phone=request.getParameter("phone").trim();
		String address=request.getParameter("address").trim();
		String realname=request.getParameter("realname").trim();
		
		if(logname==null)
			logname="";
		if(password==null)
			password="";
		if(!password.equals(again_password)){
			userBean.setBackNews("�������벻ͬ��ע��ʧ�ܡ�");
			request.getRequestDispatcher("inputRegisterMess.jsp").forward(request, response);
			return;
		}
		
		boolean isLD=true;
		for(int i=0; i<logname.length(); i++){
			char c= logname.charAt(i);
			if(!((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0')))
				isLD=false;
		}
		
		boolean boo = logname.length()>0&&password.length()>0&&isLD;
		String backNews = "";
		try{
			con = DriverManager.getConnection(uri);
			String insertCondition = "INSERT INTO user VALUES(?,?,?,?,?)";
			sql=con.prepareStatement(insertCondition);
			if(boo){
				sql.setString(1, handleString(logname));
				sql.setString(2, handleString(password));
				sql.setString(3, handleString(phone));
				sql.setString(4, handleString(address));
				sql.setString(5, handleString(realname));
				int m=sql.executeUpdate();
				if(m!=0){
					backNews = "ע��ɹ�";
							userBean.setBackNews(backNews);
							userBean.setLogname(logname);
							userBean.setPhone(phone);
					userBean.setAddress(handleString(address));
					userBean.setRealname(handleString(realname));
				}
			} else {
				backNews="��Ϣ��д���������������зǷ��ַ�";
				userBean.setBackNews(backNews);
			}
			con.close();
		}
		catch(SQLException exp){
			backNews="�û�Ա���ѱ�ʹ�ã������������"+exp;
			userBean.setBackNews(backNews);
		}
		request.getRequestDispatcher("inputRegisterMess.jsp").forward(request, response);
		
	}

}
