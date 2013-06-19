<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*, java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserForm userForm = null;
if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)!=null) {
	userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心统计报表</title>
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath %>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath %>dtree/dtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>dtree/dtree.css"/>
<script type="text/javascript" src="<%=basePath %>pagejs/uploadManager.js"></script>
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
					<h4 class="content_hd long_content_hd">文件查看</h4>
					<div class="content_bd">
					<TABLE>
					<TR>
					<TD WIDTH="250PX" HEIGHT="800PX" VALIGN="TOP">
						<div class="gray_bor_bg" style="width:240px;height:1058px;overflow:auto;">
<div class="dtree">
	<p><a href="javascript: d.openAll();">全部展开</a> | <a href="javascript: d.closeAll();">全部收缩</a></p>
	<script type="text/javascript">
		<!--
		var treeRoot = "交管局";
		d = new dTree('d');
		var treeIndex = 0;
		var childTreeIndex;
<%
	if(userForm!=null && (userForm.getUserId()==0 || userForm.getRoleType().equals("0"))) {
%>
		d.add(treeIndex,-1,treeRoot,','+treeRoot);treeIndex++;
<%
	} else {
%>
		d.add(treeIndex,-1,treeRoot);treeIndex++;
<%
	}
	List list_totalTree = (List)request.getAttribute(Constants.JSP_TREE_LIST);
	if(list_totalTree!=null && list_totalTree.size()>0)
	{
		for(int i=0; i<list_totalTree.size(); i++)//一级部门循环
		{
			TreeForm rootTreeForm = (TreeForm)((List)(list_totalTree.get(i))).get(0);
			List<TreeForm> treeFormList = ((List<TreeForm>)((List)(list_totalTree.get(i))).get(1));
%>
		d.add(treeIndex,0,'<%=rootTreeForm.getTreeName() %>&nbsp;&nbsp;<a href="javascript:userChoose(\'<%=rootTreeForm.getTreeId() %>\')">警员选择</a>','<%=rootTreeForm.getTreeId() %>,<%=rootTreeForm.getTreeName() %>');
		childTreeIndex = treeIndex;
		treeIndex++;
<%
			for(int j=0; j<treeFormList.size(); j++)//二级部门循环
			{
%>
		d.add(treeIndex,childTreeIndex,'<%=treeFormList.get(j).getTreeName() %>&nbsp;&nbsp;<a href="javascript:userChoose(\'<%=treeFormList.get(j).getTreeId() %>\')">警员选择</a>','<%=treeFormList.get(j).getTreeId() %>,<%=treeFormList.get(j).getTreeName() %>');
		treeIndex++;
<%
			}
		}
	}
%>

		document.write(d);

		//-->
	</script>

</div>
						</div>
					</TD>
					<TD VALIGN="TOP">
						<iframe src="" id="uploadManagerQuery" name="uploadManagerQuery" style="width:700px;height:1400px;overflow-x:hidden" frameborder="0"></iframe>
					</TD>
					</TABLE>
					</div>
				</div>
			</div>
	</div>
<jsp:include page="common/footer.jsp" />

<div id="userChooseDiv" icon="icon-save" style="display:none" class="boxcontent">
<div class="gray_bor_bg">
<div class="error msg" id="userChooseMsg" style="display:none" onclick="hideObj('userChooseMsg')">Message if login failed</div>
							<h5 class="gray_blod_word">组合条件搜索</h5>
							<div class="search_form">
					<form id="userManagerForm" name="userManagerForm" target="userChooseIframe" action="<%=basePath %>userAction.do?method=userChoose" method="post">
								<input type="hidden" name="changeUser" id="changeUser" value="0"/>
								<label>姓名：</label><input type="text" name="user_name" value="" class="input_79x19"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<label>警员编号：</label><input type="text" name="user_code" value="" class="input_79x19"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="hidden" id="query_treeId" name="query_treeId" />
								<input type="submit" class="blue_mod_btn" value="搜 &nbsp;索" />
								<a id="closeDialog" style="display:none" href="javascript:void(0)" class="Js_closePar blue_mod_btn" class="">取&nbsp;&nbsp;消</a>
					</form>
							</div>
						</div>
						<iframe src="" id="userChooseIframe" name="userChooseIframe" width="578" height="330" frameborder="0" scrolling="no"></iframe>
</div>
<script>
/**
 * 用户选择
 * @param assignmentId 
 * @param assignmentName 
 * @param dialogTitle 弹出层的标题
 */
function userChoose(assignmentId, assignmentName, dialogTitle, onlyRoot)
{
jQuery(function($) {
	$.weeboxs.open('#userChooseDiv', {title:dialogTitle, contentType:'selector', width:'600', height:'450'});
	if(assignmentId!=null)
	{
		$('#query_treeId').val(assignmentId);
		//$('#userManagerForm').submit();
		$('#userChooseIframe').attr('src', '<%=basePath %>userAction.do?method=userChoose&query_treeId='+assignmentId);
	}
	else
	{
		$('#changeUser').val('');
	}
});
}
function closeDialog()
{
	$('#closeDialog').click();
}
function uploadManagerQuery(treeId, userId) {
jQuery(function($) {
	if(userId!=null && userId!='') {
		$('#uploadManagerQuery').attr('src', contextPath()+'/userAction.do?method=uploadManager&fileCreateUserId='+userId+'&timer='+Math.random());
	} else {
		$('#uploadManagerQuery').attr('src', contextPath()+'/userAction.do?method=uploadManager&treeId='+treeId.split(',')[0]+'&treeName='+treeId.split(',')[1]+'&timer='+Math.random());
	}
});
}
</script>
</body>

<div id="imgShowDiv" icon="icon-save" style="display:none" class="boxcontent">
<img id="imgObj" src="" />
</div>
<div id="playShowDiv" icon="icon-save" style="display:none" class="boxcontent">
<object id="video" name="video" width="640" height="480" border="0" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA">
<param name="ShowDisplay" value="0">
<param name="ShowControls" value="1">
<param name="AutoStart" value="1">
<param name="AutoRewind" value="0">
<param name="PlayCount" value="-1">
<param name="Appearance" value="0" value="">
<param name="BorderStyle" value="0" value="">
<param name="MovieWindowHeight" value="570">
<param name="MovieWindowWidth" value="440">
<param name="FileName" value="" id="playObj">
<embed id="videoObj" width="570" height="440" border="0" showdisplay="0" showcontrols="1" autostart="1" autorewind="0" playcount="-1" moviewindowheight="570" moviewindowwidth="440" filename="" src=""> 
</embed>
</object>
</div>
<div id="playFlvDiv" icon="icon-save" style="display:none" class="boxcontent">
<object id="flv" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" height="640" width="480"> 
<param name="movie" id="flvplayObj" value="vcastr22.swf?vcastr_file="> 
<param name="quality" value="high"> 
<param name="allowFullScreen" value="true" /> 
<embed id="flvvideoObj" src="vcastr22.swf?vcastr_file=" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" 
type="application/x-shockwave-flash" width="640" height="480"> 
</embed> 
</object>
</div>
<div id="playFlvFrame" icon="icon-save" style="display:none" class="boxcontent">
<iframe src="" name="flvplay" frameborder="0" width="560" height="420" scrolling="no"></iframe>
</div>
<div id="playWavFrame" icon="icon-save" style="display:none" class="boxcontent">
<iframe src="" name="wavplay" frameborder="0" width="420" height="100" scrolling="no"></iframe>
</div>
<form id="playFlvDivForm" action="videoPlayer/play.jsp" method="post" target="flvplay">
<input type="hidden" id="flvPath" name="flvPath" />
</form>
<form id="playWavDivForm" action="wavplay.jsp" method="post" target="wavplay">
<input type="hidden" id="wavPath" name="wavPath" />
</form>
</html>
