<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=gbk"%>
<%
	request.setCharacterEncoding("gbk");
%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>多域检索</title>

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
<script type="text/javascript">
	function check_check(checkname) {
		var flag = false;
		var checks = document.getElementsByName(checkname);
		for (var i = 0; i < checks.length; i++) {
			if (checks[i].checked == true) {
				flag = true;
				return true;
				break;
			}
		}
		if (!flag) {
			alert("请至少选择一项"); 
			return false;
		}
	}
	 function selectAll(formName, cbName) {
            var o = document.forms[formName].elements[cbName];
            
            for (i=0; i<o.length; i++) {
                document.forms[formName].elements[cbName][i].checked = true;
            }

        }
        function unSelectAll(formName, cbName) {
            var o = document.forms[formName].elements[cbName];
            
            for (i=0; i<o.length; i++) {
                document.forms[formName].elements[cbName][i].checked = false;
            }
        }
        function reverseAll(formName, cbName) {
            var o = document.forms[formName].elements[cbName];

            for (i=0; i<o.length; i++) {
                document.forms[formName].elements[cbName][i].checked = !document.forms[formName].elements[cbName][i].checked;
            }
        }
	
</script>

</head>

<body>
	<br>
	<form action="MultiFieldSearchServlet" method="POST" name="form1">
		在下列区域检索:<br/>
		<!-- filename content <br>
		<input type="hidden" name="field" value="filename"/>
		<input type="hidden" name="field" value="content"/> -->
		题名<input type="checkbox" name="field" value="title" /> 
		创建者<input
			type="checkbox" name="field" value="creator" /> 
			主题<input
			type="checkbox" name="field" value="subject" /> 
			描述<input
			type="checkbox" name="field" value="description" /> 
			出版者<input
			type="checkbox" name="field" value="publisher" /> 
			日期<input
			type="checkbox" name="field" value="date" /><br />
			 类型<input type="checkbox"
			name="field" value="type" /> 来源<input type="checkbox" name="field"
			value="source" /> 语种<input type="checkbox" name="field"
			value="language" /> 权限<input type="checkbox" name="field"
			value="rights" />
			文件名<input type="checkbox" name="field" value="filename" />
			内容<input type="checkbox" name="field" value="content" /><br />
			<input type="button" value="全选" onclick="selectAll('form1', 'field')">
            <input type="button" value="全不选" onclick="unSelectAll('form1', 'field')">
            <input type="button" value="反选" onclick="reverseAll('form1', 'field')"><br>
			
		<!-- <input type="text" name="field" value="content" size="7"> -->
		每页显示条数:<input type="text" name="pageSize" value="10" size="4" />
		检索词:<input type="text" name="keyword" /> <input type="hidden"
			name="targetPage" value="1" /> <input type="submit" onclick="return check_check('field');"/>
		<input type="reset" /><br />
	</form>
</body>
</html>
