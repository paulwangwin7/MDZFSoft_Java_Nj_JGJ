<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*,com.manager.pub.bean.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	UserForm userForm = (UserForm)(request.getSession().getAttribute(Constants.SESSION_USER_FORM));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>权限管理 -- 部门管理</title>
		<jsp:include page="common/tag.jsp" />
		<script type="text/javascript" src="<%=basePath%>js/jquery_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>pagejs/treeManager.js"></script>
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
						部门管理
					</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
<div class="error msg" id="treeManagerMsg" style="display:none" onclick="hideObj($(this).attr('id'))"></div>
							<h5 class="gray_blod_word">
								<%
								if(userForm!=null && userForm.getUserId()==0)
								{
									out.print("<input type=\"radio\" value=\"0\" name=\"treeid\" class=\"tree0Radio\"/>");
								}
								%>
								交警大队管理
							</h5>
							<ul class="Js_ShowPolice police_list">
<%
	List list_totalTree = (List)request.getAttribute(Constants.JSP_TREE_LIST);
	if(list_totalTree!=null && list_totalTree.size()>0)
	{
		for(int i=0; i<list_totalTree.size(); i++)//一级部门循环
		{
			TreeForm rootTreeForm = (TreeForm)((List)(list_totalTree.get(i))).get(0);
			List<TreeForm> treeFormList = ((List<TreeForm>)((List)(list_totalTree.get(i))).get(1));
%>
								<li class="police_item">
									<div class="police_hd">
										<input type="radio" value="<%=rootTreeForm.getTreeId() %>" name="treeid" class="tree1Radio"/>
										<%=rootTreeForm.getTreeName() %>
									</div>
									<ul class="voice_list mt_10">
<%
			for(int j=0; j<treeFormList.size(); j++)//二级部门循环
			{
%>
										<li>
											<input type="radio" value="<%=treeFormList.get(j).getTreeId() %>" name="treeid" class="tree2Radio"/>
											<%=treeFormList.get(j).getTreeName() %>
										</li>
<%
			}
%>
									</ul>
								</li>
<%
		}
	}
%>
							</ul>
						</div>
						<div class=" mt_10 pb_200">
							<a href="javascript:treeAdd('req_treeId', 'req_treeName', '新增部门')" class="blue_mod_btn">新增部门</a>
							<a href="javascript:treeMdf('req_treeId', 'req_treeName', '修改部门')" class="blue_mod_btn">修改部门</a>
							<a href="javascript:del()" class="blue_mod_btn">删除部门</a>
						</div>
					</div>
				</div>
				<div class=" mt_10 pb_200">
				</div>
			</div>
		</div>
		<jsp:include page="common/footer.jsp" />
		<script type="text/javascript" src="js/all.js"></script>

<div id="treeSaveOrUpdateDiv" icon="icon-save" style="display:none" class="boxcontent">
<div class="error msg" id="treeAddMsg" style="display:none" onclick="hideObj($(this).attr('id'))"></div>
<form id="treeManagerForm" class="uniform" method="post" onsubmit="return saveOrUpdate()">
<input type="hidden" id="treeId" value="" />
<input type="hidden" id="parentId" value="" />
<input type="hidden" id="actionType" value="" />
<div class="gray_bor_bg">
<div class="new_form">
<ul class="form_list">
	<li class="form_item">
		<label class="input_hd">部门名称:</label>
		<input type="text" id="treeName" class="input_130x20" value="" />
	</li>
	<li class="form_item">
		<label class="input_hd">部门描述:</label>
		<input type="text" id="desc" class="input_130x20" value="" />
	</li>
	<li class="form_item">
		<input type="submit" class="blue_mod_btn" id="treeSubmit" value="确认新增" />
		<a href="javascript:void(0)" class="Js_closePar blue_mod_btn" class="">取&nbsp;&nbsp;消</a>
	</li>
</ul>
</div>
</div>
</form>
</div>

	</body>
</html>
