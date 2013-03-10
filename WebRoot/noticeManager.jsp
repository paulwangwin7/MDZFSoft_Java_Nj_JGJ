<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*,com.manager.pub.util.*, java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String actionMethod = "userNoticeManager";
	if(request.getAttribute("ImAdmin")!=null)
	{
		actionMethod = "adminNoticeManager";
	}
	List<NoticeForm> noticeFormList = null;
	if(request.getAttribute(Constants.PAGE_INFORMATION)!=null)
	{
		com.manager.pub.bean.Page pageRst = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
		noticeFormList = (List<NoticeForm>)pageRst.getListObject();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统管理 -- 公告管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/all.css" />
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath%>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>pagejs/noticeManager.js"></script>
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
<script>var menuIndex = 1;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
				<form id="noticeManagerForm" action="userAction.do?method=<%=actionMethod %>" method="post">
				<input type="hidden" name="pageCute" id="req_pageCute" />
					<h4 class="content_hd long_content_hd">公告管理</h4>
					<div class="content_bd">
						<div class="mange_table">
<div class="error msg" id="noticeManagerMsg" style="display:none" onclick="hideObj($(this).attr('id'))">Message if login failed</div>
							<table class="Js_grayBg common_table">
								<colgroup>
									<col class="col_220" />
									<col class="col_235" />
									<col class="col_65" />
									<col class="col_60" />
								</colgroup>
								<tr>
									<th>公告标题</th><th>公告内容</th><th>创建时间</th><th>相关操作</th>
								</tr>
						<%
						if(noticeFormList!=null)
						{
							for(int i=0; i<noticeFormList.size(); i++)
							{
								NoticeForm noticeForm = noticeFormList.get(i);
						%>
							<tr id="tr_<%=noticeForm.getNoticeId() %>">
								<td class="textc"><%=noticeForm.getNoticeTitle() %></td>
								<td class="textc"><%=noticeForm.getNoticeContent() %></td>
								<td class="textc"><%=DateUtils.formatChar14Time(noticeForm.getCreateTime()) %></td>
								<td class="textc">
									<a href="javascript:noticeDel('<%=noticeForm.getNoticeId() %>');" class="blue_link">删除</a>
								</td>
							</tr>
						<%
							}
						}
						%>
								<!--table opt-->
								<tr>
									<td class="pr">
										<a href="javascript:noticeAdd('req_roleId', 'req_roleName', '新增公告')" class="blue_mod_btn">新增公告</a>
									</td>
									<td class="pl" colspan="3">
										<jsp:include page="common/page.jsp?function=showNoticeManager"></jsp:include>
									</td>
								</tr>
							</table>
						</div>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</form>
				</div>
		</div>
	</div>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>
<script>
function showNoticeManager(pagecute)
{
jQuery(function($) {
	$("#req_pageCute").val(pagecute);
	$("#noticeManagerForm").submit();
});
}
</script>


<div id="noticeSaveOrUpdateDiv" icon="icon-save" style="display:none" class="boxcontent">
<div class="error msg" id="noticeAddMsg" style="display:none" onclick="hideObj($(this).attr('id'))">Message if login failed</div>
<form id="treeManagerForm" class="uniform" method="post" onsubmit="return noticeSave()">
<input type="hidden" id="saveOrUpdate" value="" />
<div class="gray_bor_bg">
<div class="new_form">
<ul class="form_list">
	<li class="form_item">
		<label class="input_hd">公告标题:</label>
		<input type="text" id="req_noticeTitle" class="input_130x20" value="" />
	</li>
	<li class="form_item">
		<label class="input_hd">公告部门:</label>
		&nbsp;&nbsp;
						<div class="gray_bor_bg">
						<div class="error msg" id="treeManagerMsg" style="display:none" onclick="hideObj($(this).attr('id'))"></div>
													<h5 class="gray_blod_word">
														请选择发布公告的部门
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
															<div class="police_hd_">
																<input type="checkbox" value="<%=rootTreeForm.getTreeId() %>" name="treeid" class="treeCheck"/>
																<%=rootTreeForm.getTreeName() %>
															</div>
															<ul class="voice_list mt_10">
						<%
									for(int j=0; j<treeFormList.size(); j++)//二级部门循环
									{
						%>
																<li>
																	<input type="checkbox" value="<%=treeFormList.get(j).getTreeId() %>" name="treeid" class="treeCheck"/>
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
	</li>
	<li class="form_item">
		<label class="input_hd">公告内容:</label>
		<textarea id="req_noticeContent" style="width:400px;height:100px"></textarea>
	</li>
	<li class="form_item" id="noticeSubmitArea">
		<input type="submit" class="blue_mod_btn" id="noticeSubmit" value="确认新增" />
		<a href="javascript:void(0)" class="Js_closePar blue_mod_btn" class="">取&nbsp;&nbsp;消</a>
	</li>
</ul>
</div>
</div>
</form>
</div>


</body>
</html>
