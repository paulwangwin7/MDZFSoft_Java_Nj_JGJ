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
<title>个人中心统计报表</title>
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath%>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>pagejs/fileDel.js"></script>
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
<script>var menuIndex = 3;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
					<h4 class="content_hd long_content_hd">过期列表</h4>
					<div class="mt_10">
<div class="error msg" id="fileDelMsg" style="display:none" onclick="hideObj('fileDelMsg')">Message if login failed</div>
<%
	if(request.getAttribute(Constants.JSP_MESSAGE)!=null) {
%>
<script>showMsgObj('fileDelMsg', '<%=request.getAttribute(Constants.JSP_MESSAGE).toString() %>', 0);</script>
<%
	}
%>
<form id="fileDelForm" name="fileDelForm" action="" method="post">
							<table class="Js_grayBg common_table file_table">
								<tr>
									<th><input type="checkbox" onclick="chooseAll(this)"/>全选</th><th>上传人</th><th>采集人</th><th>上传文件名</th><th>上传时间</th><th>采集时间</th><th>重要性</th><th>备注</th><th>上传人IP</th><th>上传人部门</th>
								</tr>
								<%
								UserForm userForm = null;
								if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)!=null) {
									userForm = (UserForm) request.getSession().getAttribute(Constants.SESSION_USER_FORM);
								}
								List<UploadForm> fileList = null;
								if(request.getAttribute(Constants.JSP_UPLOAD_LIST)!=null) {
									fileList = (List<UploadForm>) request.getAttribute(Constants.JSP_UPLOAD_LIST);
								}
								List<RoleForm> roleFormList = null;
								if(userForm!=null && fileList!=null)
								{
									for(int i=0; i<fileList.size(); i++) {
										UploadForm file = fileList.get(i);
										if(file.getTree1Id()==userForm.getTreeId() || file.getTree2Id()==userForm.getTreeId())
										{
								%>
								<tr>
									<td class="textc"><input type="checkbox" class="fileDelId" name="expired_uploadId" value="<%=file.getUploadId() %>" /></td>
									<td class="textc"><%=file.getUserName() %></td>
									<td class="textc"><%=file.getEditName() %></td>
									<td class="textc"><%=file.getFileStats().equals("0")?"":"<font color='red'>★</font>" %><%=file.getUploadName() %></td>
									<td class="textc"><%=DateUtils.formatChar14Time(file.getUploadTime()) %></td>
									<td class="textc"><%=DateUtils.formatChar14Time(file.getFileCreatetime()) %></td>
									<td class="textc"><%=file.getFileStats().equals("0")?"普通":"重要★" %></td>
									<td class="textc"><%=file.getFileRemark()==null?"暂无":file.getFileRemark() %></td>
									<td class="textc"><%=file.getIpAddr() %></td>
									<td class="textc"><%=file.getTreeName() %></td>
								</tr>
								<%
										}
									}
								}
								%>
								<!--table opt-->
								<tr>
									<td class="pr textc" colspan="10">
										<input type="button" class="blue_mod_btn" onclick="fileDel()" value="删除普通" />
										<input type="button" class="blue_mod_btn" onclick="fileDel2()" value="删除重要" />
									</td>
								</tr>
							</table>
</form>
						</div>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</div>
		</div>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>
</body>
</html>
