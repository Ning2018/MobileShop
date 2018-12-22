<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="userBean" class="mybean.Register" scope="request" />
<html>
<head>
<%@ include file="head.txt" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
</head>
<body bgcolor=pink>
	<font size=2>
		<div align="center">
			<form action="registerServlet" method="post" name=form>
				<table>
					用户名由字母，数字，下划线构成，*注释的项必须填写。
					<tr>
						<td>*用户名称：</td>
						<td><Input type=text name="logname"></td>
						<td>*用户密码：</td>
						<td><input type=password name="password"></td>
					<tr>
					<tr>
						<td>*重复密码：</td>
						<td><Input type=password name="again_password"></td>
						<td>联系电话：</td>
						<td><Input type=text name="phone"></td>
					</tr>
					<tr>
						<td>邮寄地址：</td>
						<td><Input type=text name="address"></td>
						<td>真实姓名：</td>
						<td><Input type=text name="realname"></td>
						<td><Input type=submit name="g" value="提交"></td>
					</tr>
				</table>
			</form>
		</div>

		<div align="center">
			<p>
				注册反馈：
				<jsp:getProperty property="backNews" name="userBean" />
			<table border=3>
				<tr>
					<td>会员名称：</td>
					<td><jsp:getProperty name="userBean" property="logname" />
					</td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><jsp:getProperty name="userBean" property="realname" />
					</td>
				</tr>
				<tr>
					<td>地址：</td>
					<td><jsp:getProperty name="userBean" property="address" />
					</td>
				</tr>
				<tr>
					<td>电话：</td>
					<td><jsp:getProperty name="userBean" property="phone" />
					</td>
				</tr>

			</table>
		</div>
	</font>
</body>
</html>