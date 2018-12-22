<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<%@ page import="mybean.DataByPage"%>
<%@ page import="com.sun.rowset.*"%>
<<jsp:useBean id="dataBean" class="mybean.DataByPage" scope="session" />
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head.txt"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor=PINK><Center>
	<BR>当前显示的内容是：
		<table border=2>
			<tr>
				<th>手机标识号</th>
				<th>手机名称</th>
				<th>手机制造商</th>
				<th>手机价格</th>
				<th>查看详情</th>
				<td><font color=blue>添加到购物车</font></td>
			</tr>
			<jsp:setProperty name="dataBean" property="pageSize" param="pageSize" />
			<jsp:setProperty name="dataBean" property="currentPage"
				param="currentPage" />
			<%
				CachedRowSetImpl rowSet = dataBean.getRowSet();
				if (rowSet == null) {
					out.print("没有任何查询信息，无法浏览");
					return;
				}
				rowSet.last();
				int totalRecord = rowSet.getRow();
				out.println("全部记载数" + totalRecord);
				int pageSize = dataBean.getPageSize();
				int totalPages = dataBean.getTotalPages();
				if (totalRecord % pageSize == 0)
					totalPages = totalRecord / pageSize;
				else
					totalPages = totalRecord / pageSize + 1;
				dataBean.setPageSize(pageSize);
				dataBean.setTotalPages(totalPages);
				if (totalPages >= 1) {
					if (dataBean.getCurrentPage() < 1)
						dataBean.setCurrentPage(dataBean.getTotalPages());
					if (dataBean.getCurrentPage() > dataBean.getTotalPages())
						dataBean.setCurrentPage(1);
					int index = (dataBean.getCurrentPage() - 1) * pageSize + 1;
					rowSet.absolute(index);
					boolean boo = true;
					for (int i = 1; i <= pageSize && boo; i++) {
						String number = rowSet.getString(1);
						String name = rowSet.getString(2);
						String maker = rowSet.getString(3);
						String price = rowSet.getString(4);
						String goods = "(" + number + "," + name + "," + maker + "," + price + ")#" + price;
						goods = goods.replaceAll("\\p{Blank}", "");
						String button = "<form action = 'putGoodsServlet' method = 'post'>"
								+ "<input type = 'hidden' name='java' value = " + goods + ">"
								+ "<input type = 'submit' value='放入购物车' ></form>";
						String detail = "<form action = 'showDetail.jsp' method='post'>"
								+ "<input type='hidden' name='xijie' value=" + number + ">"
								+ "<input type='submit' value='查看细节'></form>";
						out.print("<tr>");
						out.print("<td>" + number + "</td>");
						out.print("<td>" + name + "</td>");
						out.print("<td>" + maker + "</td>");
						out.print("<td>" + price + "</td>");
						out.print("<td>" + detail + "</td>");
						out.print("<td>" + button + "</td>");
						out.print("</tr>");
						boo = rowSet.next();
					}
				}
			%>
		</table> <BR>每页最多显示<jsp:getProperty name="dataBean" property="pageSize" />条信息
		<BR>当前显示第<Font color=blue> <jsp:getProperty
				name="dataBean" property="currentPage" />
	</Font>页， 共有 <Font color=blue><jsp:getProperty name="dataBean"
				property="totalPages" /> </Font>页。
		<Table>
			<tr>
				<td><FORM action="" method=post>
						<Input type=hidden name="currentPage"
							value="<%=dataBean.getCurrentPage()-1 %>"> 
						<Input type=submit name="submit" value="上一页">
					</FORM></td>
				<td><FORM action="" method=post>
						<Input type=hidden name="currentPage"
							value="<%=dataBean.getCurrentPage() + 1 %>"> 
						<Input type=submit name="submit" value="下一页">
					</FORM></td>
			</tr>
			<tr>
				<td><FORM action="" method=post>
						每页显示<Input type=text name="pageSize" value=2 size=3> 
						条记录<Input type=submit name="submit" value="确定">
					</FORM>
				</td>
				<td><FORM action="" method=post>
						输入页码： <Input type=text name="currentPage" size=2> 
						       <Input type=submit name="submit" value="提交">
					</FORM>
				</td>
			</tr>
		</Table>
	</Center>
</body>
</html>