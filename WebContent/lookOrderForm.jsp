<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<jsp:useBean id="loginBean" class="mybean.Login" scope="session" />
<%@page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head.txt"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<%
			if (loginBean == null) {
				response.sendRedirect("login.jsp");
			} else {
				boolean b = loginBean.getLogname() == null || loginBean.getLogname().length() == 0;
				if (b) {
					response.sendRedirect("login.jsp");
				}
			}
			Connection con;
			Statement sql;
			ResultSet rs;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (Exception e) {
			}
			try {
				String url = "jdbc:mysql://127.0.0.1/mobileshop?user=root&password=root";

				con = DriverManager.getConnection(url);
				sql = con.createStatement();
				String cdn = "SELECT order_id,mess,sum FROM orderform where logname='" + loginBean.getLogname() + "'";

				rs = sql.executeQuery(cdn);
				out.print("<table border=2>");
				out.print("<tr>");
				out.print("<th width=50>" + "订单号");
				out.print("<th width=500>" + "信息");
				out.print("<th width=50>" + "价格");
				out.print("</TR>");
				while (rs.next()) {
					out.print("<tr>");
					out.print("<td>" + rs.getInt(1) + "</td>");
					out.print("<td>" + rs.getString(2) + "</td>");
					out.print("<td>" + rs.getString(3) + "</td>");
					out.print("</tr>");
				}
				out.print("</table>");
				con.close();
			} catch (SQLException e) {
				out.print(e);
			}
		%>

	</div>
</body>
</html>