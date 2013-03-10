<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.UserForm,com.manager.pub.util.Constants,com.manager.pub.util.DateUtils" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserForm userForm = (UserForm)(request.getSession().getAttribute(Constants.SESSION_USER_FORM));
%>
<div id="header">
<div class="logobox">
<div class="layout">
<a href="" class="mainlogo">
<img src="images/mainlogo.png" alt="" />
</a>
<div class="cur_time">
当前时间：<span id="localtime"></span>
</div>
<div class="show_user">
<div class="cureent_user">
hi,<a href="#"><%=userForm.getUserName() %></a>,欢迎登录<a href="javascript:userLogout();" class="blue_btn">退出系统</a>
</div>
</div>
</div>
</div>
	</div>