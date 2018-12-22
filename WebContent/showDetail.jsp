<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<%@ page import="mybean.Login"%>
<%@ page import="java.sql.*"%>
<<jsp:useBean id="loginBean" class="mybean.Login" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head.txt"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor=PINK>
	<CENTER>
		<%
			if (loginBean == null) {
				response.sendRedirect("login.jsp");
			} else {
				boolean b = loginBean.getLogname() == null || loginBean.getLogname().length() == 0;
				if (b)
					response.sendRedirect("login.jsp");
			}
			String mobileID = request.getParameter("xijie");
			out.print("<th>产品号" + mobileID);
			if (mobileID == null) {
				out.print("没有产品号，无法查看细节");
				return;
			}
			Connection con;
			PreparedStatement sql;
			ResultSet rs;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (Exception e) {
			}
			String uri = "jdbc:mysql:///mobileshop";
			try {
				con = DriverManager.getConnection(uri, "root", "root");
				String condition = "SELECT * FROM mobileForm WHERE mobile_version='" + mobileID + "'";
				sql = con.prepareStatement(condition);
				rs = sql.executeQuery();
				out.print("<table border=2>");
				out.print("<tr>");
				out.print("<th>产品号");
				out.print("<th>名称");
				out.print("<th>制造商");
				out.print("<th>价格");
				out.print("<th><font color=blue>放入购物车</font>");
				out.print("</TR>");
				String picture = "welcome.jpg";
				String detailMess = "";
				while (rs.next()) {
					String number = rs.getString(1);
					String name = rs.getString(2);
					String maker = rs.getString(3);
					String price = rs.getString(4);
					detailMess = rs.getString(5);
					picture = rs.getString(6);
					String goods = "(" + number + "," + name + "," + maker + "," + price + ")#" + price;
					goods = goods.replaceAll("\\p{Blank}", "");
					String button = "<form action = 'putGoddsServlet' method='post'>"
							+ "<input type = 'hidden' name='java' value = " + goods + ">"
							+ "<input type = 'submit' value='放入购物车' ></form>";
					out.print("<tr>");
					out.print("<td>" + number + "</td>");
					out.print("<td>" + name + "</td>");
					out.print("<td>" + maker + "</td>");
					out.print("<td>" + price + "</td>");
					out.print("<td>" + button + "</td>");
					out.print("</tr>");

				}
				out.print("</table>");
				out.print("产品详情：<br>");
				out.print("<div align=center>" + detailMess + "</div>");
				String pic = "<img src='images/"+picture+"'wdith=260 height=200></img>";
				out.print(pic);
				con.close();
			} catch (SQLException e) {
			}
		%>
	</CENTER>

</body>
</html>