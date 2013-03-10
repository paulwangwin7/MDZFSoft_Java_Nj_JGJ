<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*" %>
<%@ page import="com.manager.pub.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
out.println(request.getAttribute("result")==null?"":request.getAttribute("result").toString());
%>