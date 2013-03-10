<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/all.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.2.6.pack.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.messager.js"></script>
<script type="text/javascript" src="<%=basePath %>pagejs/common.js"></script>