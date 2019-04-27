<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>welcome</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=path%>html/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
	<img src="<%=path%>/html/images/welcome.gif" />
</body>
</html>
