<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*, java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userNameStr = request.getParameter("userNameStr")==null?"":request.getParameter("userNameStr");
String userCodeStr = request.getParameter("userCodeStr")==null?"":request.getParameter("userCodeStr");
String treeId = request.getParameter("query_treeId")==null?"":request.getParameter("query_treeId");
String treeName = request.getParameter("query_treeName")==null?"":request.getParameter("query_treeName");

com.manager.pub.bean.Page pageRst = null;
List<UserForm> userFormList = null;
if(request.getAttribute(Constants.PAGE_INFORMATION)!=null)
{
	pageRst = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
	userFormList = (List<UserForm>)pageRst.getListObject();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人中心统计报表</title>
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath %>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath %>pagejs/userManager.js"></script>
		<script type="text/javascript">
$(document).ready(function(){
	$("#dialog").click(function(){
	$.weeboxs.open('.boxcontent', {title:'统计表报',contentType:'selector'});
});})



</script>
	</head>

	<body>

		<!--header=============================================begin-->
		<jsp:include page="common/header.jsp" />
		<script>var menuIndex = 2;</script>
		<jsp:include page="common/menu.jsp" />
		<!--content============================================begin-->
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10"><h4 class="content_hd long_content_hd">用户查询</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
<div class="error msg" id="userManagerMsg" style="display:none" onclick="hideObj('userManagerMsg')">Message if login failed</div>
							<h5 class="gray_blod_word">组合条件搜索</h5>
							<div class="search_form">
					<form id="userManagerForm" name="userManagerForm" action="<%=basePath %>userAction.do?method=userManager" method="post">
								<label>姓名：</label><input type="text" name="userNameStr" value="<%=userNameStr %>" class="input_79x19"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<label>警员编号：</label><input type="text" name="userCodeStr" value="<%=userCodeStr %>" class="input_79x19"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<label>所属部门：</label>
										<select name="query_treeId" class="input_130x20">
											<option value=""> -- </option>
<%
	List list_totalTree = (List)request.getAttribute(Constants.JSP_TREE_LIST);
	if(list_totalTree!=null && list_totalTree.size()>0)
	{
		for(int i=0; i<list_totalTree.size(); i++)//一级部门循环
		{
			TreeForm rootTreeForm = (TreeForm)((List)(list_totalTree.get(i))).get(0);
			List<TreeForm> treeFormList = ((List<TreeForm>)((List)(list_totalTree.get(i))).get(1));
%>
											<option value="<%=rootTreeForm.getTreeId() %>"<%=treeId.equals(rootTreeForm.getTreeId()+"")?"selected":"" %>><%=rootTreeForm.getTreeName() %></option>
<%
			for(int j=0; j<treeFormList.size(); j++)//二级部门循环
			{
%>
											<option value="<%=treeFormList.get(j).getTreeId() %>"<%=treeId.equals(treeFormList.get(j).getTreeId()+"")?"selected":"" %>>&nbsp;<%=treeFormList.get(j).getTreeName() %></option>
<%
			}
		}
	}
%>
										</select>
								<input type="submit" class="blue_mod_btn" value="搜 &nbsp;索" />
					</form>
							</div>
						</div>
						<div class="mange_table log_table mt_10">
							<table class="Js_grayBg common_table">
								<tr>
									<th>警员编号</th><th>登录帐号</th><th>req_userIdCard</th><th>真实姓名</th><th>性别</th><th>所属部门</th><th>所属角色</th><th>用户状态</th><th>相关操作</th>
								</tr>
								<%
						if(userFormList!=null && userFormList.size()>0)
						{
							for(int i=0; i<userFormList.size(); i++)
							{
								UserForm userForm = userFormList.get(i);
						%>
							<tr>
								<td class="textc"><%=userForm.getUserCode() %></td>
								<td class="textc"><%=userForm.getLoginName() %></td>
								<td class="textc"><%=userForm.getUserIdCard()==null?"":userForm.getUserIdCard() %></td>
								<td class="textc"><%=userForm.getUserName() %></td>
								<td class="textc"><%=userForm.getSex().equals("M")?"男":"女" %></td>
								<td class="textc"><%=userForm.getTreeNameStr() %></td>
								<td class="textc"><%=userForm.getRoleNameStr() %></td>
								<td class="textc orange_word"><%=userForm.getUserState().equals("A")?"有效":"未激活或已冻结" %></td>
								<td class="textc">
									<!--img class="move" src="images/icons/arrow-move.png" alt="Move" title="Move" /-->
									<a href="###" onclick="userMdf('<%=userForm.getUserId() %>', '', '用户修改')" title="查看"><img src="images/icons/information-octagon.png" alt="查看" /></a>
									<a href="###" onclick="userMdf('<%=userForm.getUserId() %>', 'req_roleName', '用户修改')" title="修改"><img src="images/icons/edit.png" alt="修改" /></a>
									<!--a href="###" onclick="userDel('<%=userForm.getUserId() %>')" title="删除"><img src="images/icons/cross.png" alt="删除" /></a-->
									<a href="###" onclick="resetPassword('<%=userForm.getUserId() %>')" title="密码重置">密码重置</a>
								</td>
							</tr>
						<%
							}
						}
						%>
								<!--table opt-->
								<tr>
									<td class="textc pr">
										<a href="<%=basePath %>userAction.do?method=userManagerToAdd" class="blue_mod_btn">用户注册</a>
									</td>
									<td class=" textc pl" colspan="7">
										<jsp:include page="common/page.jsp?function=showUser"></jsp:include>
									</td>
								</tr>
							</table>
						</div>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</div>
				</div>
			</div>
		<jsp:include page="common/footer.jsp" />
		<script type="text/javascript" src="js/all.js"></script>
<form id="hiddUserManagerForm" action="<%=basePath %>userAction.do?method=userManager" method="post">
<input type="hidden" name="pageCute" id="userManager_pageCute" />
<input type="hidden" name="userNameStr" value="<%=userNameStr %>" />
<input type="hidden" name="userCodeStr" value="<%=userCodeStr %>" />
<input type="hidden" name="query_treeId" value="<%=treeId %>" />
</form>
<script>
/**
 * 分页显示用户列表
 * @param pageCute 当前第几页
 */
function showUser(pageCute)
{
jQuery(function($) {
	$("#userManager_pageCute").val(pageCute);
	$("#hiddUserManagerForm").submit();
});
}
</script>


<div id="userSaveOrUpdateDiv" icon="icon-save" style="display:none" class="boxcontent">
<div class="error msg" id="userAddMsg" style="display:none" onclick="hideObj('userAddMsg')">Message if login failed</div>
<form action="#" method="post" onsubmit="return saveOrUpdate()">
			<input type="hidden" id="req_userId" />
			<input type="hidden" id="actionType" value="mdf" />
						<div class="gray_bor_bg">
							<div class="new_form">
								<ul class="form_list">
									<li class="form_item">
										<label class="input_hd">登录帐户:</label>
										<input type="text" class="input_130x20" id="req_loginName" name="loginName" value="" />
									</li>
									<li class="form_item">
										<label class="input_hd">身份证号:</label>
										<input type="text" class="input_130x20" id="req_userIdCard" name="userIdCard" value="" />
									</li>
									<li class="form_item">
										<label class="input_hd">姓&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
										<input type="text" class="input_130x20" id="req_userName" name="userName" value="" />
									</li>
									<li class="form_item">
										<label class="input_hd">警员编号:</label>
										<input type="text" class="input_130x20" id="req_userCode" name="userCode" value="" />
									</li>
									<li class="form_item">
										<label class="input_hd">性&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
										<input type="radio" class="req_sex" name="sex" value="M" />男&nbsp;&nbsp;
										<input type="radio" class="req_sex" name="sex" value="W" />女
										
									</li>
									<li class="form_item">
										<label class="input_hd">所属部门:</label>
										<select id="req_treeName" class="input_130x20">
											<option value=""> -- </option>
<%
	if(list_totalTree!=null && list_totalTree.size()>0)
	{
		for(int i=0; i<list_totalTree.size(); i++)//一级部门循环
		{
			TreeForm rootTreeForm = (TreeForm)((List)(list_totalTree.get(i))).get(0);
			List<TreeForm> treeFormList = ((List<TreeForm>)((List)(list_totalTree.get(i))).get(1));
%>
											<option value="<%=rootTreeForm.getTreeId() %>"><%=rootTreeForm.getTreeName() %></option>
<%
			for(int j=0; j<treeFormList.size(); j++)//二级部门循环
			{
%>
											<option value="<%=treeFormList.get(j).getTreeId() %>">&nbsp;<%=treeFormList.get(j).getTreeName() %></option>
<%
			}
		}
	}
%>
										</select>
									</li>
									<li class="form_item">
										<label class="input_hd">所属角色:</label>
										<select id="req_roleName" class="input_130x20">
											<option value=""> -- </option>
<%
	List<RoleForm> roleFormList = null;
	if(request.getAttribute(Constants.JSP_ROLE_LIST)!=null)
	{
		com.manager.pub.bean.Page respPage = (com.manager.pub.bean.Page)request.getAttribute(Constants.JSP_ROLE_LIST);
		roleFormList = (List<RoleForm>)respPage.getListObject();
		if(roleFormList!=null)
		{
			for(int i=0; i<roleFormList.size(); i++)
			{
				RoleForm roleForm = roleFormList.get(i);
%>
											<option value="<%=roleForm.getRoleId() %>"><%=roleForm.getRoleName() %></option>
<%
			}
		}
	}
%>
										</select>
									</li>
									<li class="form_item">
										<label class="input_hd">状&nbsp;&nbsp;&nbsp;&nbsp;态:</label>
										<input type="radio" class="req_userState" name="userState" value="A" />激活&nbsp;&nbsp;
										<input type="radio" class="req_userState" name="userState" value="U" />锁定
									</li>
									<li class="form_item" id="actionArea" style="display:none">
										<input type="submit" class="blue_mod_btn" value="确认修改" />
										<a href="javascript:void(0)" class="Js_closePar blue_mod_btn" class="">取&nbsp;&nbsp;消</a>
 									</li>
								</ul>
							</div>

						</div>
</form>
</div>
	</body>
</html>
