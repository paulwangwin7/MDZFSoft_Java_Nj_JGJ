<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*, com.manager.pub.bean.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	String action = request.getParameter("action")==null?"add":request.getParameter("action");
	String req_userId = request.getParameter("userId")==null?"":request.getParameter("userId");

	String req_loginName = "";
	String req_loginPswd = SystemConfig.getSystemConfig().getResetPswd();
	String req_userIdCard = "";
	String req_userName = "";
	String req_userCode = "";
	String req_sex = "";
	String req_treeId = "";
	String req_treeName = "";
	String req_roleId = "";
	String req_roleName = "";
	String req_userState = "";

	UserForm userForm = null;
	if(request.getAttribute(Constants.JSP_USER_FORM)!=null)
	{
		userForm = (UserForm)request.getAttribute(Constants.JSP_USER_FORM);

		if(userForm!=null)
		{
			req_loginName = userForm.getLoginName();
			req_loginPswd = userForm.getLoginPswd();
			req_userIdCard = userForm.getUserIdCard();
			req_userName = userForm.getUserName();
			req_userCode = userForm.getUserCode();
			req_sex = userForm.getSex();
			req_treeId = userForm.getTreeId()+"";
			req_treeName = userForm.getTreeNameStr();
			req_roleId = userForm.getRoleId()+"";
			req_roleName = userForm.getRoleNameStr();
			req_userState = userForm.getUserState();
		}
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
</head>

<body>

	<!--header=============================================begin-->
<jsp:include page="common/header.jsp" />
<script>var menuIndex = 2;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
					<h4 class="content_hd long_content_hd">用户注册</h4>
					<div class="content_bd">
<div class="error msg" id="userAddMsg" style="display:none" onclick="hideObj('userAddMsg')">Message if login failed</div>
<form action="#" method="post" onsubmit="return saveOrUpdate()">
			<input type="hidden" id="req_userId" name="userId" value="<%=req_userId %>" />
			<input type="hidden" id="actionType" value="<%=action %>" />
						<div class="gray_bor_bg">
							<div class="new_form">
								<ul class="form_list">
									<li class="form_item">
										<label class="input_hd">登录帐户:</label>
										<input type="text" class="input_130x20" id="req_loginName" name="loginName" value="<%=req_loginName %>" />
									</li>
									<li class="form_item">
										<label class="input_hd">登录密码:</label>
										<input type="password" class="input_130x20" id="req_loginPswd" name="loginPswd" value="<%=req_loginPswd %>" readonly />
									</li>
									<li class="form_item">
										<label class="input_hd">身份证号:</label>
										<input type="text" class="input_130x20" id="req_userIdCard" name="userIdCard" value="<%=req_userIdCard %>" />
									</li>
									<li class="form_item">
										<label class="input_hd">姓&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
										<input type="text" class="input_130x20" id="req_userName" name="userName" value="<%=req_userName %>" />
									</li>
									<li class="form_item">
										<label class="input_hd">警员编号:</label>
										<input type="text" class="input_130x20" id="req_userCode" name="userCode" value="<%=req_userCode %>" />
									</li>
									<li class="form_item">
										<label class="input_hd">性&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
										<input type="radio" class="req_sex" name="sex" value="M" <%=action.equals("add")?"checked":req_sex.equals("M")?"checked":"" %> />男&nbsp;&nbsp;
										<input type="radio" class="req_sex" name="sex" value="W" <%=req_sex.equals("W")?"checked":"" %> />女
										
									</li>
									<li class="form_item">
										<label class="input_hd">所属部门:</label>
										<select id="req_treeName" class="input_130x20">
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
										<input type="radio" class="req_userState" name="userState" value="A" <%=action.equals("add")?"checked":req_userState.equals("A")?"checked":"" %> />激活&nbsp;&nbsp;
										<input type="radio" class="req_userState" name="userState" value="U" <%=req_userState.equals("U")?"checked":"" %> />锁定
									</li>
									<li class="form_item">
										<input type="submit" class="blue_mod_btn" value="确认注册" />
 									</li>
								</ul>
							</div>

						</div>
						</form>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</div>
		</div>
	</div>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>
<div id="selectTreeDiv" icon="icon-save" style="display:none" class="boxcontent">
<iframe width="100%" height="300px" src="<%=basePath %>userAction.do?method=treeSelect&assignmentId=req_treeId&assignmentName=req_treeName"></iframe>
</div>
</body>
</html>
