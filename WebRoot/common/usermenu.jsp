<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<ul class="user_orders">
<li><a href="###" onclick="javascript:mineUpload();">我的上传</a></li>
<li><a href="###" onclick="javascript:showNotice();">所有公告</a></li>
<li><a href="###" onclick="javascript:showChangePswd();">修改密码</a></li>
<li><a href="###" onclick="javascript:userLogout();">退出系统</a></li>
</ul>