<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="loginBean" class="mybean.Login" scope="session" />
<html>
<head>
<%@ include file="head.txt"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>
<body bgcolor=pink>
	<font size=2>
		<div align="center">
				<form action="loginServlet" Method="post">
			<table border=2>
				<tr>
					<th>登录</th>
				</tr>
				<tr>
					<td>登录名称：<input type=text name="logname"></td>
				</tr>
				<tr>
					<td>输入密码：<input type=password name="password"></td>
				</tr>
			</table>

			<input type=submit name="submit" value="提交">
			</form>
		</div>
		<div align ="center">
		登录反馈信息：<br>
		<jsp:getProperty name="loginBean" property="backNews" />
		<br>登录名称：<br><jsp:getProperty name="loginBean" property="logname" />
		</div>
	</font>
</body>
</html>