<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>欢迎使用！</title>
	<link rel="stylesheet" type="text/css" href="html/css/styles.css" >
  </head>
   <body style="font-size:12px;color:dark-blue;scroll:no;border-top:solid 1px #666;">
   <center>   
      <div style="padding:6px; font-family:微软雅黑;">&copy;2018 salmon</div>
   </center>
  </body>
</html>