<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.UserForm,com.manager.pub.util.Constants,com.manager.pub.util.DateUtils" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userCode = "";
String treeName = "";
String registerTime = "";
try
{
	UserForm userForm = (UserForm)(request.getSession().getAttribute(Constants.SESSION_USER_FORM));
	userCode = userForm.getUserCode()==null?"":userForm.getUserCode();
	treeName = userForm.getTreeNameStr()==null?"":userForm.getTreeNameStr();
	//registerTime = userForm.getCreateTime()==null?"":DateUtils.formatChar14Time(userForm.getCreateTime());
}
catch(Exception ex)
{
}
%>
<ul class="user_info">
<li>警员编号：<span><%=userCode %></span></li>
<li>所属部门：<span><%=treeName %></span></li>
</ul>