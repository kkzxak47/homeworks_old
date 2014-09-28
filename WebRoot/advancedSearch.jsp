<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ page contentType="text/html; charset=gbk"%>
<% request.setCharacterEncoding("gbk"); %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>searchLocal</title>

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
	margin-left: 10%;
	margin-right: 10%;
	border: 2px dotted black;
	padding: 10px 10px 10px 10px;
	font-family: Verdana;
}
</style>

<script type="text/javascript"> 
function check_check(checkname){ 
var flag; 
var checks = document.getElementsByName(checkname); 
for(var i=0;i<checks.length;i++){ 
if(checks[i].checked==true){ 
flag = true; 
return true; 
break; 
} 
} 
if(!flag){ 
alert("请至少选择一项"); 
return false; 
} 
} 
</script>
</head>

<body>
	<br>
	<form action="AdvancedSearchServlet" method="POST">
		field: 
		<select name="field">
			<option  value="title">题名</option>
			<option  value="creator">创建者</option>
			<option  value="subject">主题</option>
			<option  value="description">描述</option>
			<option  value="publisher">出版者</option>
			<option  value="date">日期</option>
			<option  value="type">类型</option>
			<option  value="source">来源</option>
			<option  value="language">语种</option>
			<option  value="rights">权限</option>
			<option  value="filename">文件名</option>
			<option  value="content">内容</option>
			<option  value="fullpath">路径</option>
		</select>
		pageSize:<input type="text" name="pageSize" value="10" size="1" />
		keyword:<input	type="text" name="keyword" /><br/>
		搜索方式：
		通配符检索<input type="radio" name="searchMethod" value="WildCard" />
		模糊检索<input type="radio" name="searchMethod" value="Fuzzy" />
		前缀检索<input type="radio" name="searchMethod" value="Prefix" />
		<input type="hidden" name="targetPage" value="1" />
		<input type="submit" onclick="return check_check('searchMethod')"/>
		<input type="reset"	/><br/>
	</form><br/>
	<form action="AdvancedSearchServlet" method="POST">
		
		field: 
		<select name="field">
			<option  value="title">题名</option>
			<option  value="creator">创建者</option>
			<option  value="subject">主题</option>
			<option  value="description">描述</option>
			<option  value="publisher">出版者</option>
			<option  value="date">日期</option>
			<option  value="type">类型</option>
			<option  value="source">来源</option>
			<option  value="language">语种</option>
			<option  value="rights">权限</option>
			<option  value="filename">文件名</option>
			<option  value="content">内容</option>
			<option  value="fullpath">路径</option>
		</select>
		pageSize:<input type="text" name="pageSize" value="10" size="1" />
		<!-- keyword:<input	type="text" name="keyword" /> -->
		start:<input type="text" name="start" />
		end:<input type="text" name="end" /><br/>
		搜索方式：
		字符范围检索<input type="radio" name="searchMethod" value="TermByRange" />
		数字范围检索<input type="radio" name="searchMethod" value="NumericRange" />
		<input type="hidden" name="targetPage" value="1" />
		<input type="submit" onclick="return check_check('searchMethod')"/>
		<input type="reset"	/><br/>
	</form>
</body>
</html>
