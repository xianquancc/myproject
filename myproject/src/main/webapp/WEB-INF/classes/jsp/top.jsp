<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
    <script type="text/javascript">
       function exit()
       {
          var flag=window.confirm("您确定要退出本系统吗?");
          if(flag==true)
          {
            parent.location.href="user_logout.action";
          }
          else
          {
             return false;
          }
       }
    </script>
  </head>
  <body style="border-bottom:solid 1px #666;">
   <table style="width:100%;">
    <tr >
	<td ><img src="html/images/logo.gif"></td>
	<td style="font-family:微软雅黑;font-size:33px;font-weight:bold;">客户关系管理系统</td>	
	<td width="25%" align="right" style="font-size:12px;font-family:微软雅黑" valign="bottom">
	 当前用户：<s:property value="#session.user.userName"/>
	   （<s:property value="#session.user.sysRole.roleName"/>）&nbsp;&nbsp; <a onclick="javascript:exit()">退出系统</a> </td>
    </tr>
   </table>
  </body>
</html>