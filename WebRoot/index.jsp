<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
.white {
	color: white;
}
.right {
	text-align: right;
}
.center {
	text-align: center;
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
    <base href="<%=basePath%>">
    
    <title>Goodbye cruel world.</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    
       <br>
    <a href="searchLocal.jsp">searchLocal</a><br>
    <a href="searchWeb.jsp">searchWeb</a><br>
    <a href="showDoc.jsp">showDoc</a><br>
    <a href="addIndex.jsp">addIndex</a><br>
    <a href="multiFieldSearch.jsp">multiFieldSearch</a><br>
    <a href="advancedSearch.jsp">advancedSearch</a><br>
    
  </body>
</html>
