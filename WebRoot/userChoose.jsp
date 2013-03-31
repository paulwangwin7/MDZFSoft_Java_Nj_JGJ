<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*, java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userNameStr = request.getParameter("userNameStr")==null?"":request.getParameter("userNameStr");
String userCodeStr = request.getParameter("userCodeStr")==null?"":request.getParameter("userCodeStr");
String query_treeId = request.getParameter("query_treeId")==null?"":request.getParameter("query_treeId");

com.manager.pub.bean.Page pageRst = null;
List<UserForm> userFormList = null;
if(request.getAttribute(Constants.PAGE_INFORMATION)!=null)
{
	pageRst = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
	userFormList = (List<UserForm>)pageRst.getListObject();
}
%>
<jsp:include page="common/tag.jsp" />
<div class="error msg" id="userSelectMsg" style="display:none;" onclick="hideObj('userSelectMsg')">Message if login failed</div>

				<form id="userSelectForm" action="<%=basePath %>userAction.do?method=userChoose" method="post">
				<input type="hidden" name="userNameStr" value="<%=userNameStr %>"/>
				<input type="hidden" name="userCodeStr" value="<%=userCodeStr %>"/>
				<input type="hidden" name="query_treeId" value="<%=query_treeId %>"/>
				<input type="hidden" name="pageCute" id="userSelect_pageCute" value=""/>
				<input type="hidden" name="changeUser" value="<%=request.getParameter("changeUser")==null?"":request.getParameter("changeUser") %>"/>
					<table id="table1" class="Js_grayBg common_table">
						<thead>
							<tr>
								<th>选择</th>
								<th>警员编号</th>
								<th>真实姓名</th>
								<th>性别</th>
								<th>所属部门</th>
								<th>所属角色</th>
							</tr>
						</thead>
						<tbody>
						<%
						if(userFormList!=null && userFormList.size()>0)
						{
							for(int i=0; i<userFormList.size(); i++)
							{
								UserForm userForm = userFormList.get(i);
						%>
							<tr>
								<td><input type="radio" name="selectUserId" class="selectUserId" value="<%=userForm.getUserId() %>" /></td>
								<td><%=userForm.getUserCode() %></td>
								<td id="userName_<%=userForm.getUserId() %>"><%=userForm.getUserName() %></td>
								<td><%=userForm.getSex().equals("M")?"男":"女" %></td>
								<td><%=userForm.getTreeNameStr() %></td>
								<td><%=userForm.getRoleNameStr() %></td>
							</tr>
						<%
							}
						}
						%>
							<tr>
								<td colspan="2">
								<button class="Js_closePar blue_mod_btn" onclick="thisPageChooseUser()">确认选择</button>
								</td>
								<td colspan="4">
								<jsp:include page="common/page.jsp?function=showSelectUser"></jsp:include>
								</td>
							</tr>
						</tbody>
					</table>
				</form>

<script>
var assignmentId = "<%=request.getParameter("assignmentId")==null?"":request.getParameter("assignmentId")%>";
var assignmentName = "<%=request.getParameter("assignmentName")==null?"":request.getParameter("assignmentName")%>";
/**
 * 用户选择
 * @param pageCute 当前第几页
 */
function showSelectUser(pageCute)
{
jQuery(function($) {
	$("#userSelect_pageCute").val(pageCute);
	$("#userSelectForm").submit();
});
}

/**
 * 确认选择用户
 */
function thisPageChooseUser()
{
jQuery(function($) {
	hideObj("userSelectMsg", 0);
	if(getArray("selectUserId").length<=0)
	{
		showMsg("userSelectMsg", "请选择一名警员", 2);
	}
	else
	{
		parent.uploadManagerQuery('', getJqueryArrayStr("selectUserId"));
		try{//上传部分默认的id和name
			parent.setDefaultVal(getJqueryArrayStr("selectUserId"), $("#userName_"+getJqueryArrayStr("selectUserId")).html());
		}catch(e){}
		showMsg("userSelectMsg", "选择完成", 0);
		try{parent.setFocus();}catch(e){}
		setTimeout(function(){parent.closeDialog();},1000);
	}
});
}

/**
 * 获取信息div
 * @param objId 显示操作的id
 * @param msg 提示信息
 * @param type 提示类型 0-success 1-error 2-warning 3-information
 * @param focusId 焦点停留id名称
 */
function showMsg(objId, msg, type, focusId)
{
jQuery(function($) {
	$("#"+objId).html(msg);
	var msgType = "error";
	switch(type)
	{
		case 0 : msgType = "success"; break;
		case 1 : msgType = "error"; break;
		case 2 : msgType = "warning"; break;
		case 3 : msgType = "information"; break;
		default: msgType = "error";
	}
	$("#"+objId).attr("class", msgType+" msg");
	$("#"+objId).css("opacity" ,4);
	showObj(objId);
	if(focusId != null)
	{
		$("#"+focusId).focus();
	}
});
}
</script>
