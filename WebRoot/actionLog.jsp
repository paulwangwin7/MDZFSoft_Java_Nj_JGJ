<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*, java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String beginTime = request.getParameter("beginTime")==null?"":request.getParameter("beginTime");
String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
String userCode = request.getParameter("userCode")==null?"":request.getParameter("userCode");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心统计报表</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/all.css" />
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath%>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>pagejs/noticeManager.js"></script>
<script type="text/javascript" src="<%=basePath %>js/adddate.js"></script>
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
<script>var menuIndex = 4;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
					<h4 class="content_hd long_content_hd">操作日志</h4>
					<div class="content_bd">
					<div class="gray_bor_bg">
							<h5 class="gray_blod_word">组合条件搜索</h5>
<div class="error msg" id="actionLogMsg" style="display:none" onclick="hideObj('actionLogMsg')">Message if login failed</div>
<form id="actionLogForm" action="<%=basePath %>userAction.do?method=actionLogManagerQuery" method="post" onsubmit="return searchLog()">
							<div class="search_form">
								<label>警员编号</label><input type="text" class="input_79x19" name="userCode" value="<%=userCode %>"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<label>操作时段</label><input type="text" class="input_79x19" name="beginTime" value="<%=beginTime %>" onclick="SelectDate(this,'MM/dd/yyyy')" readonly />&nbsp;&nbsp;-&nbsp;&nbsp;<input type="text" class="input_79x19" name="endTime" value="<%=endTime %>" onclick="SelectDate(this,'MM/dd/yyyy')" readonly/>
								<input type="submit" class="blue_mod_btn" value="搜 &nbsp;索" />
							</div>
</form>
						</div>
					<div class="mange_table log_table mt_10">
							<table class="Js_grayBg common_table">
								<tr>
									<th>警员编号</th><th>IP记录</th><th>日志时间</th><th>所属部门</th><th>所属角色</th><th>操作描述</th>
								</tr>
						<%
						List<LogForm> logFormList = null;
						if(request.getAttribute(Constants.PAGE_INFORMATION)!=null)
						{
							com.manager.pub.bean.Page respPage = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
							logFormList = (List<LogForm>)respPage.getListObject();
							if(logFormList!=null)
							{
								for(int i=0; i<logFormList.size(); i++)
								{
									LogForm logForm = logFormList.get(i);
						%>
								<tr>
									<td class="textc"><%=logForm.getUserCode() %></td>
									<td class="textc"><%=logForm.getIpAddr() %></td>
									<td class="textc"><%=DateUtils.formatChar14Time(logForm.getCreateTime()) %></td>
									<td class="textc"><%=logForm.getTreeName()==null?"交警大队总部":logForm.getTreeName() %></td>
									<td class="textc"><%=logForm.getRoleName()==null?"admin":logForm.getRoleName() %></td>
									<td class="textc"><%=logForm.getLogDesc() %></td>
								</tr>
						<%
								}
							}
						}
						%>
								<!--table opt-->
								<tr>
									<td class=" textc" colspan="6">
										<jsp:include page="common/page.jsp?function=showLog"></jsp:include>
									</td>
								</tr>
							</table>
						</div>
<form id="hidLogForm" action="<%=basePath %>userAction.do?method=actionLogManagerQuery" method="post">
<input type="hidden" name="pageCute" value="" id="actionLog_pageCute" />
<input type="hidden" name="userCode" value="<%=userCode %>"/>
<input type="hidden" name="beginTime" value="<%=beginTime %>"/>
<input type="hidden" name="endTime" value="<%=endTime %>"/>
</form>
<script>
/**
 * 分页显示日志列表
 * @param pageCute 当前第几页
 */
function showLog(pageCute)
{
jQuery(function($) {
	$("#actionLog_pageCute").val(pageCute);
	$("#hidLogForm").submit();
});
}
</script>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</div>
			</div>
		</div>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>
</body>
</html>
