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

<title>�������</title>

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
			alert("������ѡ��һ��"); 
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
		�������������:<br/>
		<!-- filename content <br>
		<input type="hidden" name="field" value="filename"/>
		<input type="hidden" name="field" value="content"/> -->
		����<input type="checkbox" name="field" value="title" /> 
		������<input
			type="checkbox" name="field" value="creator" /> 
			����<input
			type="checkbox" name="field" value="subject" /> 
			����<input
			type="checkbox" name="field" value="description" /> 
			������<input
			type="checkbox" name="field" value="publisher" /> 
			����<input
			type="checkbox" name="field" value="date" /><br />
			 ����<input type="checkbox"
			name="field" value="type" /> ��Դ<input type="checkbox" name="field"
			value="source" /> ����<input type="checkbox" name="field"
			value="language" /> Ȩ��<input type="checkbox" name="field"
			value="rights" />
			�ļ���<input type="checkbox" name="field" value="filename" />
			����<input type="checkbox" name="field" value="content" /><br />
			<input type="button" value="ȫѡ" onclick="selectAll('form1', 'field')">
            <input type="button" value="ȫ��ѡ" onclick="unSelectAll('form1', 'field')">
            <input type="button" value="��ѡ" onclick="reverseAll('form1', 'field')"><br>
			
		<!-- <input type="text" name="field" value="content" size="7"> -->
		ÿҳ��ʾ����:<input type="text" name="pageSize" value="10" size="4" />
		������:<input type="text" name="keyword" /> <input type="hidden"
			name="targetPage" value="1" /> <input type="submit" onclick="return check_check('field');"/>
		<input type="reset" /><br />
	</form>
</body>
</html>
