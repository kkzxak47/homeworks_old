<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��������</title>
    
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
    <form action="AddIndexServlet" method="post">
    	<table>
    		<tr>
    			<td>�ļ���</td>
    			<td><input type="text" name="filepath" size="100"></td>
    		</tr>
    		<tr>
    			<td>������</td>
    			<td><input type="text" name="title" size="100"> </td>
   			</tr>
   			<tr>
   				<td>�����ߣ�</td>
    			<td><input type="text" name="creator" size="100"> </td>
   			</tr>
   			<tr>
   				<td>���⣺</td>
    			<td><input type="text" name="subject" size="100"> </td>
   			</tr>
   			<tr>
   				<td>������</td>
    			<td><input type="text" name="description" size="100"> </td>
   			</tr>
   			<tr>
   				<td>�����ߣ�</td>
    			<td><input type="text" name="publisher" size="100"> </td>
   			</tr>
   			<tr>
   				<td>���ڣ�</td>
    			<td><input type="text" name="date" size="100"> </td>
   			</tr>
   			<tr>
   				<td>���ͣ�</td>
    			<td><input type="text" name="type" size="100"> </td>
   			</tr>
   			<tr>
   				<td>��Դ��</td>
    			<td><input type="text" name="source" size="100"> </td>
   			</tr>
   			<tr>
   				<td>���֣�</td>
    			<td><input type="text" name="language" size="100"> </td>
   			</tr>
   			<tr>
   				<td>Ȩ�ޣ�</td>
    			<td><input type="text" name="rights" size="100"> </td>
   			</tr>
   			<tr>
   				<td><input type="submit"></td>
   				<td><input type="reset"></td>
   			</tr>
    	</table>
    </form>
  </body>
</html>
