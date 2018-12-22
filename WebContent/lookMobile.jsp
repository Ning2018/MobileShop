<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head.txt"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor=PINK>
	<font size=2>
		<div align="center">
			<%
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (Exception e) {
				}
				String uri = "jdbc:mysql:///mobileshop?user=root&password=root";
				Connection con;
				PreparedStatement sql;
				ResultSet rs;
				try {
					con = DriverManager.getConnection(uri);
					String condition = "SELECT * FROM mobileClassify";
					sql = con.prepareStatement(condition);
					rs = sql.executeQuery();
					out.print("<form action = 'queryServlet' method='post'>");
					out.print("<select name='fenleiNumber'>");
					out.print("<option selected>请选择手机类型</option>");
					while (rs.next()) {
						int id = rs.getInt(1);
						String mobileCategory = rs.getString(2);
						out.print("<option value =" + id + ">" + mobileCategory + "</option>");
					}
					out.print("</select>");
					out.print("<input type='submit' value='提交'>");
					out.print("</form>");
					con.close();
				} catch (SQLException e) {
					out.print(e);
				}
			%>
		</div>
	</font>
</body>
</html>