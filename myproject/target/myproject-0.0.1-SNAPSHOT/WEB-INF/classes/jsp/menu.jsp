<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>左侧树形菜单</title>
    <base href="<%=basePath%>">
	<link rel="StyleSheet" href="css/dtree.css" type="text/css" />
	<script type="text/javascript" src="js/dtree.js"></script>
  </head>
  
  <body bgcolor="#0066cc"> 
    <!--   
		               创建树形菜单的各个节点的关联关系 ,使用d.add(id, pid, name, url, title, target, icon, iconOpen, open)创建
		      id :子节点名称
		      pid:父节点名称
		      name:节点文本标签名称
		      url:节点链接地址
		      title:节点标题
		      target:节点跳转目标地址
		      icon:节点图片
		      iconOpen:打开节点图片
		      open:节点已经打开后的图片    
		      
		      //新建一个名称为d的树形菜单
		        d = new dTree('d');
		     //设置树形菜单的根节点为ROOT_MENU
		        d.add('ROOT_MENU',-1,'客户关系管理系统'); 
		     //保存树形菜单
		     document.write(d);
		     
          <s:property value="#session.user.usrName"/>
          <s:property value="#session.user.sysRole.roleName"/><br>
    --> 
    <script type="text/javascript">
    	d = new dTree('d');
		d.add('ROOT_MENU',-1,'客户关系管理系统','','','','html/images/menu/doc.gif','html/images/menu/ns_doc.gif');  
		<s:iterator var="right" value="#session.user.sysRole.sysRights">
    		d.add(
    		  '<s:property value="#right.rightCode"/>',
    		  '<s:property value="#right.rightParentCode"/>',
    		  '<s:property value="#right.rightText"/>',
    		  '<s:property value="#right.rightUrl"/>',
    		  '',
    		  'mainFrame','html/images/menu/doc.gif','html/images/menu/ns_doc.gif');
    	</s:iterator>
	    document.write(d);
    </script>
    
 </body>
</html>




















