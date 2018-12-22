<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<%@page import="mybean.Login"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="loginBean" class="mybean.Login" scope="session" />
<html>
<head>
<%@ include file="head.txt"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<font size=2>
		<div align="center">
			<%
				if (loginBean == null) {
					response.sendRedirect("login.jsp");
				} else {
					boolean b = loginBean.getLogname() == null || loginBean.getLogname().length() == 0;
					if (b)
						response.sendRedirect("login.jsp");
				}
				LinkedList car = loginBean.getCar();
				if (car == null)
					out.print("<h2>购物车没有物品</h2>");
				else {
					Iterator<String> iterator = car.iterator();
					StringBuffer buyGoods = new StringBuffer();
					int n = 0;
					double priceSum = 0;
					out.print("购物车中的物品:<table border=2>");
					while (iterator.hasNext()) {
						String goods = iterator.next();
						String showGoods = "";
						n++;
						//购物车物品的后缀是
						int index = goods.lastIndexOf("#");
						if (index != -1) {
							priceSum += Double.parseDouble(goods.substring(index + 1));
							showGoods = goods.substring(0, index);
							System.out.println("showGoods:"+showGoods);
						}
						buyGoods.append(n + ":" + showGoods);
						System.out.println("buyGoods:"+buyGoods);
						String del = "<form action='deleteServlet' method='post'>"
								+ "<input type='hidden' name='delete' value=" + goods + ">"
								+ "<input type='submit' value='删除'></form>";
						out.print("<tr><td>" + showGoods + "</td>");
						out.print("<td>" + del + "</td></tr>");
					}
					out.print("</table>");
					String orderForm = "<form action='buyServlet' method='post'>" + "<input type='hidden' name='buy' value="
							+ buyGoods + ">"+"<input type='hidden' name='priceSum' value="+priceSum+">" 
							+ "<input type='submit' value='生成订单'></form>";
					out.print(orderForm);
				}
			%>
		</div>
	</font>
</body>
</html>