<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*" %>
<%@ page import="com.manager.pub.bean.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
RoleForm roleForm = (RoleForm) request.getSession().getAttribute(Constants.SESSION_ROLE_FORM);
if(roleForm==null)
{
	response.sendRedirect(basePath);
}
%>
<%!
	public boolean canOperate(RoleForm form, String urlId) {
		for(int i=0; i<form.getUrlIdList().split(",").length; i++)
		{
			if(form.getUrlIdList().split(",")[i].equals(urlId))
			{
				return true;
			}
		}
		return false;
	}
%>
	<div id="nav">
			<div class="layout">
				<ul class="nav_list">
					<li class="nav_item"><a href="<%=basePath %>userAction.do?method=userMain" id="menu_0"><span>我的主页</span></a></li>
					<%
						if(canOperate(roleForm, "1") || canOperate(roleForm, "2") || canOperate(roleForm, "9"))
						{
					%>
					<li class="nav_item"><a id="menu_1" style="cursor:pointer"><span>权限管理</span></a></li>
					<%
						}
					%>
					<%
						if(canOperate(roleForm, "3") || canOperate(roleForm, "4"))
						{
					%>
					<li class="nav_item"><a id="menu_2" style="cursor:pointer"><span>用户管理</span></a></li>
					<%
						}
					%>
					<%
						if(canOperate(roleForm, "5") || canOperate(roleForm, "8") || canOperate(roleForm, "10"))
						{
					%>
					<li class="nav_item"><a id="menu_3" style="cursor:pointer"><span>文件管理</span></a></li>
					<%
						}
					%>
					<%
						if(canOperate(roleForm, "6") || canOperate(roleForm, "7"))
						{
					%>
					<li class="nav_item"><a id="menu_4" style="cursor:pointer"><span>日常查询</span></a></li>
					<%
						}
					%>
					<li class="nav_item"><a id="menu_5" style="cursor:pointer"><span>系统管理</span></a></li>
				</ul>
			</div>
			<div class="nav_subbox">
				<div class="layout">
					<ul class="sub_list">
						<li class="sub_item" id="menu_0li" style="display:none">
							&nbsp;
						</li>
						<li class="sub_item" id="menu_1li" style="display:none">
							<%
								if(canOperate(roleForm, "1"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=treeManager">部门管理</a>
							<%
								}
							%>
							<%
								if(canOperate(roleForm, "2"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=roleManager">角色管理</a>
							<%
								}
							%>
							<%
								if(canOperate(roleForm, "9"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=userNoticeManager">公告管理</a>
							<%
								}
							%>
						</li>
						<li class="sub_item" id="menu_2li" style="display:none">
							<%
								if(canOperate(roleForm, "3"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=userManagerToAdd">用户注册</a>
							<%
								}
							%>
							<%
								if(canOperate(roleForm, "4"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=userManager">用户查询</a>
							<%
								}
							%>
						</li>
						<li class="sub_item" id="menu_3li" style="display:none">
							<%
								if(canOperate(roleForm, "5"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=userFileUpload">文件上传</a>
							<%
								}
							%>
							<%
								if(canOperate(roleForm, "8"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=filePlayShow">文件查看</a>
							<%
								}
							%>
							<%
								if(canOperate(roleForm, "10"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=expiredFileList">文件删除</a>
							<%
								}
							%>
						</li>
						<li class="sub_item" id="menu_4li" style="display:none">
							<%
								if(canOperate(roleForm, "6"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=analysisUpload">上传报表</a>
							<%
								}
							%>
							<%
								if(canOperate(roleForm, "7"))
								{
							%>
							<a href="<%=basePath %>userAction.do?method=actionLogManagerQuery">操作日志</a>
							<%
								}
							%>
						</li>
						<li class="sub_item" id="menu_5li" style="display:none">
							<a href="<%=basePath %>analysisMonitor.jsp">系统监控</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
<script>
jQuery(function($) {
	$('#menu_'+menuIndex).attr('class', 'current');
	$('#menu_'+menuIndex+'li').css('display', 'block');
	$('.nav_list .nav_item a').hover(
		function () {
			//mouseover
			clearMenuClass();
			$(this).attr('class', 'current');
			hideMenuLi();
			$('#'+$(this).attr('id')+'li').css('display', 'block');
		},
		function () {
			//mouseout
		}
	);
	$('.sub_list .sub_item').hover(
		function () {
			//mouseover
		},
		function () {
			//mouseout
			clearMenuClass();
			hideMenuLi();
			$('#menu_'+menuIndex).attr('class', 'current');
			$('#menu_'+menuIndex+'li').css('display', 'block');
		}
	);
});
function clearMenuClass() {
	$('.nav_list .nav_item a').each(function(){
		//if($(this).attr('id') != 'menu_'+menuIndex) {
			$(this).attr('class', '');
		//}
	});
}
function hideMenuLi() {
	$('.sub_list li').each(function(){
		$(this).css('display', 'none');
	});
}
</script>