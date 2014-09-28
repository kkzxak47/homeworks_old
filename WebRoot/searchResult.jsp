<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page contentType="text/html; charset=utf-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<TITLE>SearchLocal Result</TITLE>
<META http-equiv='Content-Type' content='text/html' ; charset='utf-8'>
<style type='text/css'>
.b {
	background-color: purple;
}

.red {
	color: red;
}
.green {
	color: green;
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
	margin-left: 10%;
	margin-right: 10%;
	border: 2px dotted black;
	padding: 10px 10px 10px 10px;
	font-family: Verdana;
}
</style>
	<script type="text/javascript">
		function validate1()
		{
			var previousPage = document.getElementById("previousPage").value;
			
			//alert("previousPage: " + previousPage);
			if(previousPage < 1) {
				alert("page requested out of bounds");
				return false;
			}
			return true;
		}
		function validate2()
		{
			var nextPage = document.getElementById("nextPage").value;
			var totalHits = document.getElementById("totalHits").value;
			var pageSize = document.getElementById("pageSize").value;
			//alert("nextPage: " + nextPage +", " + "Math.ceil(totalHits/pageSize): " + Math.ceil(totalHits/pageSize));
			
			if(nextPage > Math.ceil(totalHits / pageSize)) {
				alert("page requested out of bounds");
				return false;
			}
			return true;
		}
		function validate3_1()
		{
			var targetPage1 = document.getElementById("targetPage1").value;
			var totalHits = document.getElementById("totalHits").value;
			var pageSize = document.getElementById("pageSize").value;
			//alert("nextPage: " + nextPage +", " + "Math.ceil(totalHits/pageSize): " + Math.ceil(totalHits/pageSize));
			if(targetPage1 < 1 || targetPage1 > Math.ceil(totalHits / pageSize)) {
				alert("page requested out of bounds");
				return false;
			}
			return true;
		}
		function validate3_2()
		{
			var targetPage2 = document.getElementById("targetPage2").value;
			var totalHits = document.getElementById("totalHits").value;
			var pageSize = document.getElementById("pageSize").value;
			//alert("nextPage: " + nextPage +", " + "Math.ceil(totalHits/pageSize): " + Math.ceil(totalHits/pageSize));
			if(targetPage2 < 1 || targetPage2 > Math.ceil(totalHits / pageSize)) {
				alert("page requested out of bounds");
				return false;
			}
			return true;
		}
	</script>
</head>

<body>
<table border="0" width="70%" align="center">
<%  int showingResult = (Integer)request.getAttribute("showingResult"); 
	int totalHits = (Integer)request.getAttribute("totalHits");
	int pageSize = Integer.parseInt(request.getParameter("pageSize"));
	String keyword = request.getParameter("keyword");
	String field = request.getParameter("field");
	int currentPage = Integer.parseInt(request.getParameter("targetPage"));
	%>
	<p><a href="searchLocal.jsp">searchLocal</a>&nbsp;&nbsp;&nbsp;<a href="searchWeb.jsp">searchWeb</a></p>
	<form action="SearchServlet" method="POST">
		field:filename content<br>
		<input type="hidden" name="field" value="filename" size="7">
		<input type="hidden" name="field" value="content" size="7">
		<!-- <input type="text" name="field" value="content" size="7"> -->  
		pageSize:<input type="text" name="pageSize" value="<%=pageSize %>" size="1">
		keyword:<input	type="text" name="keyword" value="<%=keyword%>">
		<input type="hidden" name="targetPage" value="1" >
		<input type="submit" value="submit"> 
		<input type="reset"	value="reset"><br>
	</form>
	<!-- <p>field:&nbsp;<font color="blue"><%=field %></font></p> -->
	<p>totalHits:&nbsp;<font color="blue"><%=totalHits %></font></p>
	 <!-- keyword:&nbsp;<span class="red"><%=keyword %></span> -->

	<p>第<%=currentPage%>页, 共<%=(int)(Math.ceil((double)totalHits/pageSize)) %>页  showing&nbsp;<font color="blue"><%=showingResult %></font>&nbsp;results, from <%=pageSize*(currentPage-1)+1 %> to <%=pageSize*(currentPage-1)+showingResult %></p>
	
	<form action="SearchServlet" method="POST">
		
		<input type="hidden" name="field" value="filename" size="7">
		<input type="hidden" name="field" value="content" size="7">
		<!-- <input type="text" name="field" value="content" size="7"> --> 
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		<input type="hidden" name="keyword" value="<%=keyword %>">
		<input type="hidden" name="targetPage" id="previousPage" value="<%=currentPage-1 %>">
		<input type="hidden" name="totalHits" value="<%=totalHits %>">
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		<input type="submit" value="上一页" onclick="return validate1();">
	</form>
	<form action="SearchServlet" method="POST">
		
		<input type="hidden" name="field" value="filename" size="7">
		<input type="hidden" name="field" value="content" size="7">
		<!-- <input type="text" name="field" value="content" size="7"> --> 
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		<input type="hidden" name="keyword" value="<%=keyword %>">
		<input type="hidden" name="targetPage" id="nextPage" value="<%=currentPage+1 %>">
		<input type="hidden" name="totalHits" id="totalHits" value="<%=totalHits %>">
		<input type="hidden" name="pageSize" id="pageSize" value="<%=pageSize %>">
		<input type="submit" value="下一页" onclick="return validate2();">
	</form>
	<form action="SearchServlet" method="POST">
		<input type="hidden" name="field" value="filename" size="7">
		<input type="hidden" name="field" value="content" size="7">
		<!-- <input type="text" name="field" value="content" size="7"> --> 
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		<input type="hidden" name="keyword" value="<%=keyword %>">
		<input type="text" size="3" name="targetPage" id="targetPage1">
		<input type="hidden" name="totalHits" value="<%=totalHits %>">
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		
		<input type="submit" value="转至" onclick="return validate3_1();">
		<!-- 第<%=currentPage%>页,共<%=(int)(totalHits/pageSize)+1 %>页 -->
	</form><br>
	<!-- 显示结果 -->
	<% List<String> filenameList = (ArrayList<String>) request.getAttribute("filenameList");%>
	<% List<String> pathList = (ArrayList<String>) request.getAttribute("pathList");%>
	<% List<String> excerptList = (ArrayList<String>) request.getAttribute("excerptList");	%>
	
	<%
		for (int i = 0; i < showingResult; i++) {
	%>
	<br>
	<a class="blue" href="<%=pathList.get(i) %>"><%=i+1+pageSize*(currentPage-1) %>. <%=filenameList.get(i)%> <br></a>
	<span class="green"> <%=pathList.get(i)%></span>
	<p><%=excerptList.get(i)%>...</p>
	<%
		}
	%>
	<!-- 显示结束 -->
	<form action="SearchServlet" method="POST">
		<input type="hidden" name="field" value="filename" size="7">
		<input type="hidden" name="field" value="content" size="7">
		<!-- <input type="text" name="field" value="content" size="7"> --> 
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		<input type="hidden" name="keyword" value="<%=keyword %>">
		<input type="hidden" name="targetPage" id="previousPage" value="<%=currentPage-1 %>">
		<input type="hidden" name="totalHits" value="<%=totalHits %>">
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		<input type="submit" value="上一页" onclick="return validate1();">
	</form>
	<form action="SearchServlet" method="POST">
		<input type="hidden" name="field" value="filename" size="7">
		<input type="hidden" name="field" value="content" size="7">
		<!-- <input type="text" name="field" value="content" size="7"> --> 
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		<input type="hidden" name="keyword" value="<%=keyword %>">
		<input type="hidden" name="targetPage" id="nextPage" value="<%=currentPage+1 %>">
		<input type="hidden" name="totalHits" id="totalHits" value="<%=totalHits %>">
		<input type="hidden" name="pageSize" id="pageSize" value="<%=pageSize %>">
		<input type="submit" value="下一页" onclick="return validate2();">
	</form>
	<form action="SearchServlet" method="POST">
		<input type="hidden" name="field" value="filename" size="7">
		<input type="hidden" name="field" value="content" size="7">
		<!-- <input type="text" name="field" value="content" size="7"> --> 
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		<input type="hidden" name="keyword" value="<%=keyword %>">
		<input type="text" size="3" name="targetPage" id="targetPage2">
		<input type="hidden" name="totalHits" value="<%=totalHits %>">
		<input type="hidden" name="pageSize" value="<%=pageSize %>">
		
		<input type="submit" value="转至" onclick="return validate3_2();"><br>
		第<%=currentPage%>页,共<%=(int)(Math.ceil((double)totalHits/pageSize)) %>页<br>
	</form>
	 <!-- 
	<p>
	调试信息：
	<br>
	request.getParameter("field") <%=request.getParameter("field") %><br>
	request.getParameter("pageSize") <%=request.getParameter("pageSize") %><br>
	request.getParameter("keyword") <%=request.getParameter("keyword") %><br>
	request.getAttribute("currentPage") <%=request.getAttribute("currentPage") %><br>
	request.getAttribute("lastDoc") <%=request.getAttribute("lastDoc") %><br>
	request.getParameter("targetPage") <%=request.getParameter("targetPage") %><br>
	request.getAttribute("showingResult") <%=request.getAttribute("showingResult") %><br>
	
	</p>
	  -->
</table>
</body>
</html>
