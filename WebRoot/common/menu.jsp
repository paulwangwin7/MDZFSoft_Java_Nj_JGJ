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
	<div class="nav_box">
		<div class="layout">
			<ul class="nav_list">
				<li class="nav_item current" id="menuIndex_0"><a class="nav_target" href="<%=basePath %>userAction.do?method=userMain">我的主页</a></li>
				<%
					if(canOperate(roleForm, "1") || canOperate(roleForm, "2") || canOperate(roleForm, "9"))
					{
				%>
				<li class="nav_item" id="menuIndex_1"><a class="nav_target">权限管理</a>
					<ul class="nav_sublist" id="childMenu_1">
						<li class="nav_subitem" id="menu_1"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=treeManager">部门管理</a></li>
						<li class="nav_subitem" id="menu_2"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=roleManager">角色管理</a></li>
						<li class="nav_subitem" id="menu_9"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=userNoticeManager">公告管理</a></li>
					</ul>
				</li>
				<%
					}
				%>
				<%
					if(canOperate(roleForm, "3") || canOperate(roleForm, "4"))
					{
				%>
				<li class="nav_item" id="menuIndex_2"><a class="nav_target">用户管理</a>
					<ul class="nav_sublist" id="childMenu_2">
						<li class="nav_subitem" id="menu_3"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=userManagerToAdd">用户注册</a></li>
						<li class="nav_subitem" id="menu_4"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=userManager">用户查询</a></li>
					</ul>
				</li>
				<%
					}
				%>
				<%
					if(canOperate(roleForm, "5") || canOperate(roleForm, "8") || canOperate(roleForm, "10"))
					{
				%>				
				<li class="nav_item" id="menuIndex_3"><a class="nav_target">文件管理</a>
					<ul class="nav_sublist" id="childMenu_3">
						<li class="nav_subitem" id="menu_5"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=userFileUpload">文件上传</a></li>
						<li class="nav_subitem" id="menu_8"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=filePlayShow">文件查看</a></li>
						<li class="nav_subitem" id="menu_10"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=expiredFileList">文件删除</a></li>
					</ul>
				</li>
				<%
					}
				%>
				<%
					if(canOperate(roleForm, "6") || canOperate(roleForm, "7"))
					{
				%>	
				<li class="nav_item" id="menuIndex_4"><a class="nav_target">日常查询</a>
					<ul class="nav_sublist" id="childMenu_4">
						<li class="nav_subitem" id="menu_6"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=analysisUpload">上传报表</a></li>
						<li class="nav_subitem" id="menu_7"><a class="nav_subtarget" href="<%=basePath %>userAction.do?method=actionLogManagerQuery">操作日志</a></li>
					</ul>
				</li>
				<%
					}
				%>	
				<li class="nav_item" id="menuIndex_5"><a class="nav_target">系统管理</a>
					<ul class="nav_sublist" id="childMenu_5">
						<li class="nav_subitem">
							<a class="nav_subtarget" href="<%=basePath %>analysisMonitor.jsp">系统监控</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
<script>
 jQuery(function($) {
 	$(".nav_item").hover(function(){
		var _this=$(this)
		_this.siblings(".nav_item").find(".nav_sublist").hide();
		if(_this.find(".nav_sublist").length>0)
		{
			_this.addClass("nav_hover")
			_this.find(".nav_sublist").show();
			
		}
	},function(){
		var _this=$(this)
		if(_this.find(".nav_sublist").length>0)
		{
			_this.removeClass("nav_hover")
			_this.find(".nav_sublist").hide();
			
		}
		$(".nav_item").each(function(){
			if($(this).hasClass("current")){
			$(this).find(".nav_sublist").show();
		}
		})
		
	})
 });

for(i=0; i<6; i++)
{
	$('#menuIndex_'+i).attr('class', 'nav_item');
}
$('#menuIndex_'+menuIndex).attr('class', 'nav_item current');

if(menuIndex>0)
{
$('#childMenu_'+menuIndex).css('display', 'block');
}
</script>

<%
for(int i=1; i<=10; i++)
{
	if(!canOperate(roleForm, ""+i))
	{
		out.println("<script>$('#menu_"+i+"').css('display', 'none')</script>");
	}
}
%>