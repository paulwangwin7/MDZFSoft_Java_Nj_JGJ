<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*, java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserForm userForm = null;
if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)!=null) {
	userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
}
	com.manager.pub.bean.Page pageRst = null;
	List<UploadForm> uploadFormList = null;
	if(request.getAttribute(Constants.PAGE_INFORMATION)!=null)
	{
		pageRst = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
		uploadFormList = (List<UploadForm>)pageRst.getListObject();
	}
String uploadUserName = request.getParameter("uploadUser")==null?"":request.getParameter("uploadUser");
String createUserName = request.getParameter("uploadCreate")==null?"":request.getParameter("uploadCreate");

System.out.println(request.getParameter("treeName"));


String fileStatsVal = request.getParameter("fileStats")==null?"":request.getParameter("fileStats");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心统计报表</title>
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath %>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath %>js/adddate.js"></script>
</head>
					<div class="content_bd">
						<div class="gray_bor_bg">
							<h5 class="gray_blod_word">
							文件查看
							</h5>
							<div class="search_form">
<form id="uploadManagerForm" action="<%=basePath %>userAction.do?method=uploadManager" onsubmit="return uploadManagerFormSubmit()" method="post">
<input type="hidden" name="treeId" value="<%=request.getParameter("treeId")==null?"":request.getParameter("treeId") %>" />
<input type="hidden" name="uploadUserId" id="upload_userId" value="<%=request.getParameter("uploadUserId")==null?"":request.getParameter("uploadUserId") %>" />
<input type="hidden" name="fileCreateUserId" id="upload_editId" value="<%=request.getParameter("fileCreateUserId")==null?"":request.getParameter("fileCreateUserId") %>" />
								<div class="mt_10">
								<label>文件名:</label>
									<input type="text" class="input_186x19" name="uploadName" id="uploadName" value="<%=request.getParameter("uploadName")==null?"":request.getParameter("uploadName") %>" />&nbsp;&nbsp;&nbsp;&nbsp;
								<label>文件备注:</label><input class="input_186x19" type="text" name="fileRemark" value="<%=request.getParameter("fileRemark")==null?"":request.getParameter("fileRemark") %>" /><br/><br/>
								<label>上传时间:</label><input class="input_186x19" id="beginTime" type="text" name="beginTime" value="<%=request.getParameter("beginTime")==null?"":request.getParameter("beginTime") %>" onclick="SelectDate(this,'yyyy-MM-dd hh:mm:ss')" readonly />&nbsp;&nbsp;-&nbsp;&nbsp;<input type="text" id="endTime" class="input_186x19" name="endTime" value="<%=request.getParameter("endTime")==null?"":request.getParameter("endTime") %>" onclick="SelectDate(this,'yyyy-MM-dd hh:mm:ss')" readonly />
								</div>
								<div class="mt_10">
								<label>录制时间:</label><input class="input_186x19" id="createTimeBegin" type="text" name="createTimeBegin" value="<%=request.getParameter("createTimeBegin")==null?"":request.getParameter("createTimeBegin") %>" onclick="SelectDate(this,'yyyy-MM-dd hh:mm:ss')" readonly />&nbsp;&nbsp;-&nbsp;&nbsp;<input type="text" id="createTimeEnd" class="input_186x19" name="createTimeEnd" value="<%=request.getParameter("createTimeEnd")==null?"":request.getParameter("createTimeEnd") %>" onclick="SelectDate(this,'yyyy-MM-dd hh:mm:ss')" readonly />
								</div>
								<div class="mt_10">
								<label>文件重要性:</label>
									<select name="fileStats" class="select_79x19">
										<option value=""<%=fileStatsVal.equals("")?"selected":""%>>--</option>
										<option value="0"<%=fileStatsVal.equals("0")?"selected":""%>>普通</option>
										<option value="1"<%=fileStatsVal.equals("1")?"selected":""%>>重要</option>
									</select>
								<!--label>文件备注:</label><input type="text" class="input_79x19" name="fileRemark" value="<%=request.getParameter("fileRemark")==null?"":request.getParameter("fileRemark") %>"/-->
								<input type="submit" class="blue_mod_btn" value="搜 &nbsp;索" />
								</div>
</form>
							</div>
							
						</div>
						<div class=" mt_10">
							<ul class="upload_list">
							<%
							if(uploadFormList!=null && uploadFormList.size()>0)
							{
								for(int i=0; i<uploadFormList.size(); i++)
								{
									UploadForm uploadForm = uploadFormList.get(i);
							%>
								<li class="upload_item">
									<div class="upload_img">
										<%
											if(uploadForm.getPlayPath()!=null && uploadForm.getPlayPath().length()>4) {
												if(uploadForm.getPlayPath().substring(uploadForm.getPlayPath().length()-4).toLowerCase().equals(".jpg"))
												{
										%>
										<a href="javascript:parent.imageDialogShow('<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getPlayPath() %>','','查看图片');" >
										<img src="<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getShowPath() %>" alt="" width="150px" height="150px" />
										</a>
										<%
												}
												else if(uploadForm.getPlayPath().substring(uploadForm.getPlayPath().length()-4).toLowerCase().equals(".wav"))
												{
													if(uploadForm.getFileState().equals("A")) {
										%>
										<a href="javascript:parent.playWavDialogShow('<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getPlayPath() %>','','播放音频');" >
										<%
													}
										%>
										<img title="<%=uploadForm.getFileRemark()==null?"":uploadForm.getFileRemark() %>" src="<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getShowPath() %>" alt="" width="150px" height="150px" />
										<%
													if(uploadForm.getFileState().equals("A")) {
										%>
										</a>
										<%
													}
												}
												else//mp4
												{
													if(uploadForm.getFileState().equals("A")) {
										%>
										<a href="javascript:parent.playFlvDialogShow('<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getFlvPath() %>','','播放视频');" >
										<%
													}
										%>
										<img title="<%=uploadForm.getFileRemark()==null?"":uploadForm.getFileRemark() %>" src="<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getShowPath() %>" alt="" width="150px" height="150px" />
										<%
													if(uploadForm.getFileState().equals("A")) {
										%>
										</a>
										<%
													}
												}
											}
										%>
									</div>
									<div title="<%=uploadForm.getUploadName() %>" class="upload_descript" style="width:150px; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">
										<%=uploadForm.getUploadName() %>
									</div>
									<div class="upload_opterdetails">
										<ul>
											<li>
												<span class="hd">创建时间：<br/>&nbsp;&nbsp;<%=Constants.timeFormat(uploadForm.getFileCreatetime(), "1").substring(2,16) %></span>
											</li>
											<li>
												<span class="hd">上传时间：<br/>&nbsp;&nbsp;<%=Constants.timeFormat(uploadForm.getUploadTime(), "1").substring(2,16) %></span>
											</li>
											<li>
												<span class="hd">上 传 人：</span><span class="bd"><%=uploadForm.getUserName() %></span>
											</li>
											<li>
												<span class="hd">使 用 人：</span><span class="bd"><%=uploadForm.getEditName() %></span>
											</li>
											<li>
												<span class="hd">部门：<%=uploadForm.getTreeName() %></span>
											</li>
										</ul>
									</div>
									<div class="clearfix mt_10">
										<!--a href="" class="fl cancle">取消</a-->
										<%
											if(userForm!=null && userForm.getUserId()==0) {
										%>
										<a href="<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getPlayPath() %>" target="_blank" class="blue_mod_btn fr">下载文件</a>
										<%
											}
										%>
										<%
											if(uploadForm.getPlayPath()!=null && uploadForm.getPlayPath().length()>4) {
												if(uploadForm.getPlayPath().substring(uploadForm.getPlayPath().length()-4).toLowerCase().equals(".jpg"))
												{
										%>
										<a href="javascript:parent.imageDialogShow('<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getPlayPath() %>','','查看图片');" class="blue_mod_btn fr">查看图片</a>
										<%
												}
												else if(uploadForm.getPlayPath().substring(uploadForm.getPlayPath().length()-4).toLowerCase().equals(".wav"))
												{
													if(uploadForm.getFileState().equals("A")) {
										%>
										<a href="javascript:parent.playWavDialogShow('<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getPlayPath() %>','','播放音频');" class="blue_mod_btn fr">播放音频</a>
										<%
													} else {
										%>
										<a href="javascript:alert('视频正在剪辑中，请稍后');" class="blue_mod_btn fr">剪辑中...</a>
										<%
													}
												}
												else//mp4
												{
													if(uploadForm.getFileState().equals("A")) {
										%>
										<a href="javascript:parent.playFlvDialogShow('<%=uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getFlvPath() %>','','播放视频');" class="blue_mod_btn fr">播放视频</a>
										<%
													} else {
										%>
										<a href="javascript:alert('视频正在剪辑中，请稍后');" class="blue_mod_btn fr">剪辑中...</a>
										<%
													}
												}
											}
										%>
									</div>
								</li>
							<%
								}
							}
							%>
							</ul>
<form action="<%=basePath %>userAction.do?method=uploadManager" method="post" id="hidUploadForm">
<input type="hidden" name="pageCute" id="uploadManager_pageCute" />
<input type="hidden" name="uploadName" value="<%=request.getParameter("uploadName")==null?"":request.getParameter("uploadName") %>" />
<input type="hidden" name="fileRemark" value="<%=request.getParameter("fileRemark")==null?"":request.getParameter("fileRemark") %>" />
<input type="hidden" name="beginTime" value="<%=request.getParameter("beginTime")==null?"":request.getParameter("beginTime") %>" />
<input type="hidden" name="endTime" value="<%=request.getParameter("endTime")==null?"":request.getParameter("endTime") %>" />
<input type="hidden" name="uploadUserName" value="<%=request.getParameter("uploadUserName")==null?"":request.getParameter("uploadUserName") %>" />
<input type="hidden" name="uploadEditName" value="<%=request.getParameter("uploadEditName")==null?"":request.getParameter("uploadEditName") %>" />
<input type="hidden" name="uploadUserId" value="<%=request.getParameter("uploadUserId")==null?"":request.getParameter("uploadUserId") %>" />
<input type="hidden" name="fileCreateUserId" value="<%=request.getParameter("fileCreateUserId")==null?"":request.getParameter("fileCreateUserId") %>" />
<input type="hidden" name="fileStats" value="<%=fileStatsVal %>" />
<input type="hidden" name="treeId" value="<%=request.getParameter("treeId")==null?"":request.getParameter("treeId") %>" />
<input type="hidden" name="treeName" value="<%=request.getParameter("treeName")==null?"":request.getParameter("treeName") %>" />
<input type="hidden" name="createTimeBegin" value="<%=request.getParameter("createTimeBegin")==null?"":request.getParameter("createTimeBegin") %>" />
<input type="hidden" name="createTimeEnd" value="<%=request.getParameter("createTimeEnd")==null?"":request.getParameter("createTimeEnd") %>" />
</form>
<script>
function showUpload(pageCute)
{
	$('#uploadManager_pageCute').val(pageCute);
	$('#hidUploadForm').submit();
}
function uploadManagerFormSubmit() {
	var beginTimeVal = document.getElementById('beginTime').value;
	var endTimeVal = document.getElementById('endTime').value;
	var createBeginVal = document.getElementById('createTimeBegin').value;
	var createEndVal = document.getElementById('createTimeEnd').value;
	if(beginTimeVal=='' && endTimeVal!='') {
		alert("上传日期错误，请选择起始时间或清除结束时间！");
	}else if(beginTimeVal!='' && endTimeVal=='') {
		alert("上传日期错误，请选择结束时间或清除起始时间！");
	} else if(createBeginVal=='' && createEndVal!='') {
		alert("录制时间错误，请选择起始时间或清除结束时间！");
	} else if(createBeginVal!='' && createEndVal=='') {
		alert("录制时间错误，请选择结束时间或清除起始时间！");
	} else {
		return true;
	}
	return false;
}
</script>
							<table align="center"><tr><td><jsp:include page="common/page.jsp?function=showUpload"></jsp:include></td></tr></table>
						</div><br/>
					</div>
</html>