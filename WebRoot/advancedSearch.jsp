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
alert("������ѡ��һ��"); 
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
			<option  value="title">����</option>
			<option  value="creator">������</option>
			<option  value="subject">����</option>
			<option  value="description">����</option>
			<option  value="publisher">������</option>
			<option  value="date">����</option>
			<option  value="type">����</option>
			<option  value="source">��Դ</option>
			<option  value="language">����</option>
			<option  value="rights">Ȩ��</option>
			<option  value="filename">�ļ���</option>
			<option  value="content">����</option>
			<option  value="fullpath">·��</option>
		</select>
		pageSize:<input type="text" name="pageSize" value="10" size="1" />
		keyword:<input	type="text" name="keyword" /><br/>
		������ʽ��
		ͨ�������<input type="radio" name="searchMethod" value="WildCard" />
		ģ������<input type="radio" name="searchMethod" value="Fuzzy" />
		ǰ׺����<input type="radio" name="searchMethod" value="Prefix" />
		<input type="hidden" name="targetPage" value="1" />
		<input type="submit" onclick="return check_check('searchMethod')"/>
		<input type="reset"	/><br/>
	</form><br/>
	<form action="AdvancedSearchServlet" method="POST">
		
		field: 
		<select name="field">
			<option  value="title">����</option>
			<option  value="creator">������</option>
			<option  value="subject">����</option>
			<option  value="description">����</option>
			<option  value="publisher">������</option>
			<option  value="date">����</option>
			<option  value="type">����</option>
			<option  value="source">��Դ</option>
			<option  value="language">����</option>
			<option  value="rights">Ȩ��</option>
			<option  value="filename">�ļ���</option>
			<option  value="content">����</option>
			<option  value="fullpath">·��</option>
		</select>
		pageSize:<input type="text" name="pageSize" value="10" size="1" />
		<!-- keyword:<input	type="text" name="keyword" /> -->
		start:<input type="text" name="start" />
		end:<input type="text" name="end" /><br/>
		������ʽ��
		�ַ���Χ����<input type="radio" name="searchMethod" value="TermByRange" />
		���ַ�Χ����<input type="radio" name="searchMethod" value="NumericRange" />
		<input type="hidden" name="targetPage" value="1" />
		<input type="submit" onclick="return check_check('searchMethod')"/>
		<input type="reset"	/><br/>
	</form>
</body>
</html>
