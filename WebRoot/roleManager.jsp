<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*, java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>权限管理 -- 角色管理</title>
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath%>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>pagejs/roleManager.js"></script>
</head>
<body>

	<!--header=============================================begin-->
<jsp:include page="common/header.jsp" />
<script>var menuIndex = 1;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
				<h4 class="content_hd long_content_hd">
						角色管理
				</h4>
				<div class="content_bd">
				<div class="mange_table log_table mt_10">
<div class="error msg" id="roleManagerMsg" style="display:none" onclick="hideObj('roleManagerMsg')">Message if login failed</div>
				<form id="roleManagerForm" action="<%=basePath %>userAction.do?method=roleManager" method="post">
				<input type="hidden" name="pageCute" id="roleManager_pageCute" />
						<table class="Js_grayBg common_table">
								<tr>
									<th>角色名称</th>
									<th width="60%">角色描述</th>
									<th>相关操作</th>
								</tr>
<%
						List<RoleForm> roleFormList = null;
						if(request.getAttribute(Constants.PAGE_INFORMATION)!=null)
						{
							com.manager.pub.bean.Page respPage = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
							roleFormList = (List<RoleForm>)respPage.getListObject();
							if(roleFormList!=null)
							{
								for(int i=0; i<roleFormList.size(); i++)
								{
									RoleForm roleForm = roleFormList.get(i);
						%>
							<tr id="tr_<%=roleForm.getRoleId() %>">
								<td class="textc"><%=roleForm.getRoleName() %></td>
								<td class="textc"><%=roleForm.getRoleDesc() %></td>
								<td class="textc">
									<!--img class="move" src="../images/icons/arrow-move.png" alt="Move" title="Move" /-->
									<a href="###" onclick="roleMdf(false, '<%=roleForm.getRoleId() %>', 'req_roleName', '角色修改')" title="查看"><img src="../images/icons/information-octagon.png" alt="查看" /></a>
									<a href="###" onclick="roleMdf(true, '<%=roleForm.getRoleId() %>', 'req_roleName', '角色修改')" title="修改"><img src="../images/icons/edit.png" alt="修改" /></a>
									<a href="###" onclick="roleDel('<%=roleForm.getRoleId() %>')" title="删除"><img src="../images/icons/cross.png" alt="删除" /></a>
								</td>
							</tr>
						<%
								}
							}
						}
						%>
								<!--table opt-->
								<tr>
									<td class="textc pr">
										<a href="javascript:roleAdd('req_roleId', 'req_roleName', '新增角色')" class="blue_mod_btn">角色添加</a>
									</td>
									<td class=" textc pl" colspan="7">
										<jsp:include page="common/page.jsp?function=showRole"></jsp:include>
									</td>
								</tr>
							</table>
					</form>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</div>
		</div>
	</div>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>

<div id="roleSaveOrUpdateDiv" icon="icon-save" style="display:none" class="boxcontent">
<div class="error msg" id="roleAddMsg" style="display:none" onclick="hideObj($(this).attr('id'))">Message if login failed</div>
<form id="roleAddForm" class="uniform" method="post" onsubmit="return roleSaveOrUpdate()">
<input type="hidden" id="saveOrUpdate" value="" />
<input type="hidden" id="resp_roleId" value="" />
<div class="gray_bor_bg">
<div class="new_form">
<ul class="form_list">
	<li class="form_item">
		<label class="input_hd">角色名称:</label>
		<input type="text" id="req_roleName" class="input_130x20" value="" />
	</li>
	<li class="form_item">
		<label class="input_hd">角色描述:</label>
		<input type="text" id="req_roleDesc" class="input_130x20" value="" />
	</li>
	<li class="form_item">
		<label class="input_hd">角色权限:</label>
		&nbsp;&nbsp;<table align="center" width="80%">
<%
	List list_menuAndurl = (List)request.getAttribute(Constants.JSP_MENU_LIST);
	if(list_menuAndurl!=null && list_menuAndurl.size()>0)
	{
		for(int i=0; i<list_menuAndurl.size(); i++)
		{
			List<UrlForm> urlFormList = ((List<UrlForm>)((List)(list_menuAndurl.get(i))).get(1));
%>
			<tr><td><%=((List)(list_menuAndurl.get(i))).get(0).toString() %></td></tr>
			<tr>
				<td>
<%
			for(int j=0; j<urlFormList.size(); j++)
			{
				UrlForm urlForm = (UrlForm)urlFormList.get(j);
%>
					<input type="checkbox" class="req_roleUrl" value="<%=urlForm.getUrlId() %>"><%=urlForm.getUrlName() %>
<%
			}
%>
				</td>
			</tr>
<%
		}
	}
%>
		</table>
	</li>
	<li class="form_item" id="roleSubmitArea">
		<input type="submit" class="blue_mod_btn" id="roleSubmit" value="确认新增" />
		<a href="javascript:void(0)" class="Js_closePar blue_mod_btn" class="">取&nbsp;&nbsp;消</a>
	</li>
</ul>
</div>
</div>
</form>
</div>

</body>
</html>
