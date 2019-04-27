<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
  <base href="<%=basePath%>">
  <title>客户关系管理系统</title>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <link href="html/css/style.css" rel="stylesheet" type="text/css">
 </head>
   <frameset rows="85,*,40" frameborder="no" framespacing="0" border="no" > 
     <frame name="topFrame"  scrolling="no" noresize  src="jsp/top.jsp"  marginwidth="value" marginheight="value" >
     <frameset cols="20%,80%" frameborder="no" framespacing="0" border="no">
       <frame name="menuFrame"  scrolling="no" noresize  src="jsp/menu.jsp"  marginwidth="value" marginheight="value" >
       <frame name="mainFrame"  scrolling="no" noresize  src="jsp/main.jsp"  marginwidth="value" marginheight="value" >
     </frameset>
     <frame src="jsp/footer.jsp" name="footerFrame"  scrolling="no" noresize  marginwidth="0" marginheight="0" >
   </frameset>
  <noframes><body bgcolor="#FFFFFF">
  </body></noframes>
</html>