<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ocxTest.jsp' starting page</title>
    
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
<object id="MDOCX" classid="clsid:8B2CE00D-FEE7-4B4A-B6A4-BDCF5A0BA624" width="0" height="0"></object>
<input type="button" value="set" onclick="alert(MDOCX.setParm('127.0.0.1','ftpdemo','ftpdemo',21));"/>
<input type="button" value="login" onclick="alert(MDOCX.ftpLogin());"/>
<input type="button" value="上传" onclick="alert(MDOCX.ftpUploadFile('d:\\demo.avi','1267.135'));" />
<input type="button" value="进度" onclick="alert(MDOCX.ftpGetUploadFilePercent());" />
<input type="button" value="查看" onclick="alert(MDOCX.ftpGetUploadSpeed());"/>
  </body>
</html>
