<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>searchWeb</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type='text/css'>
.b {
	background-color: purple;
}

.red {
	color: red;
}

.blue {
	color: blue;
}

.right {
	text-align: right;
	color: green;
}

.center {
	text-align: left;
	height: 50%;
	width: 50%;
}

body {
	background-color: #FFFFFF;
	margin-left: 20%;
	margin-right: 20%;
	border: 2px dotted black;
	padding: 10px 10px 10px 10px;
	font-family: Verdana;
}
</style>
</head>

<body>
	<br>
	<form action="SearchWebServlet" method="POST">
		fields: content<input type="hidden" name="field" value="title" >
		title<input type="hidden" name="field" value="content" >
		
		<br />
		pageSize:<input type="text" name="pageSize" value="10" size="1" style="width: 26px; ">
		keyword:<input	type="text" name="keyword" >
		<input type="hidden" name="targetPage" value="1" >
		<input type="submit" value="submit">
		<input type="reset"	value="reset"><br>
	</form>
</body>
</html>
