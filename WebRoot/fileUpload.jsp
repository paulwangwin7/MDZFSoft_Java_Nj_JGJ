<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	UserForm uf = (UserForm)(request.getSession().getAttribute(Constants.SESSION_USER_FORM));
	String ftpHost = SystemConfig.getSystemConfig().getFtpHost();
	String ftpPort = SystemConfig.getSystemConfig().getFtpPort();
	String ftpUser = SystemConfig.getSystemConfig().getFtpUser();
	String ftpPswd = SystemConfig.getSystemConfig().getFtpPswd();
	String userTreeId = uf.getTreeId()+"";
	String userUserId = uf.getUserId()+"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心统计报表</title>
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath %>js/jquery_dialog.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#dialog").click(function(){
	$.weeboxs.open('.boxcontent', {title:'统计表报',contentType:'selector'});
});})
</script>
<object id="MDOCX" classid="clsid:8B2CE00D-FEE7-4B4A-B6A4-BDCF5A0BA624" width=0 height=0></object>
<script>
var ftpHost = "<%=ftpHost%>";
var ftpPort = "<%=ftpPort%>";
var ftpUser = "<%=ftpUser%>";
var ftpPswd = ""
</script>
<script type="text/javascript" src="<%=basePath %>pagejs/fileUpload.js"></script>
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
					<h4 class="content_hd long_content_hd">文件上传</h4>
					<div class="content_bd">
<div class="error msg" id="fileManagerAddMsg" style="display:none" onclick="hideObj('fileManagerAddMsg')">Message if login failed</div>
<div class="error msg" id="fileManagerAddInformation" style="display:none" onclick="hideObj('fileManagerAddInformation')">Message if login failed</div>
						<TABLE>
						<TR>
						<TD>
						<ul class="my_uplist">
							<li class="my_upitem clearfix" id="uploadType1">
								<div class="up_help_img ">
									<img src="images/up_step1.png" alt="" /><a href="javascript:uploadTypeDiv1()">选择进入&gt;&gt;</a>
								</div>
								<div class="up_details" style="display:none">
									<span class="progress_box">
										<span class="progress_inner"></span>
										uploaded 30%
									</span>
									<div class=" w_230 textc mt_15">
										<input type="radio" class="radio" />E:\\盘&nbsp;&nbsp;
										<input type="radio" class="radio" />F:\\盘&nbsp;&nbsp;
										<input type="radio" class="radio" />G:\\盘
									</div>
									<div class=" w_230 textc mt_15">
										<a href="" class="blue_mod_btn">选择盘符</a>
										<a href="" class="green_mod_btn">确定上传</a>
									</div>
								</div>
							</li>
							<li class="my_upitem clearfix" id="uploadType2">
								<div class="up_help_img ">
									<img src="images/up_step2.png" alt="" /><a href="javascript:uploadTypeDiv2()">选择进入&gt;&gt;</a>
								</div>
								<div class="up_details" style="display:none">
									<span class="progress_box">
										<span class="progress_inner"></span>
										uploaded 30%
									</span>
									<div class=" w_230 textc mt_15">
										<input type="radio" class="radio" />E:\\盘&nbsp;&nbsp;
										<input type="radio" class="radio" />F:\\盘&nbsp;&nbsp;
										<input type="radio" class="radio" />G:\\盘
									</div>
									<div class=" w_230 textc mt_15">
										<a href="" class="blue_mod_btn">选择盘符</a>
										<a href="" class="green_mod_btn">确定上传</a>
									</div>
								</div>
							</li>
							<li class="my_upitem clearfix" id="uploadType3">
								<div class="up_help_img ">
									<img src="images/up_step3.png" alt="" /><a href="javascript:uploadTypeDiv3()">选择进入&gt;&gt;</a>
								</div>
								<div class="up_details" style="display:none">
									<span class="progress_box">
										<span class="progress_inner"></span>
										uploaded 30%
									</span>
									<div class=" w_230 textc mt_15">
										<input type="radio" class="radio" />E:\\盘&nbsp;&nbsp;
										<input type="radio" class="radio" />F:\\盘&nbsp;&nbsp;
										<input type="radio" class="radio" />G:\\盘
									</div>
									<div class=" w_230 textc mt_15">
										<a href="" class="blue_mod_btn">选择盘符</a>
										<a href="" class="green_mod_btn">确定上传</a>
									</div>
								</div>
							</li>
						</ul>
						</TD>
						<TD>
							<div id="fileUploadDiv" style="display:none"></div>
						</TD>
						</TR>
						</TABLE>
					</div>
				</div>
		</div>
	</div>
<form id="uploadForm" method="post" action="<%=basePath%>userAction.do?method=uploadFile" target="db">
<input type="hidden" id="upload_editId" name="upload_editId" value=""/>
<input type="hidden" id="upload_playPath" name="upload_playPath" value=""/>
<input type="hidden" id="upload_uploadName" name="upload_uploadName" value=""/>
<input type="hidden" id="upload_playCreatetime" name="upload_playCreatetime" value=""/>
</form>

<div id="userChooseDiv" icon="icon-save" style="display:none" class="boxcontent">
<div class="gray_bor_bg">
<div class="error msg" id="userChooseMsg" style="display:none" onclick="hideObj('userChooseMsg')">Message if login failed</div>
							<h5 class="gray_blod_word">组合条件搜索</h5>
							<div class="search_form">
					<form id="userManagerForm" name="userManagerForm" target="userChooseIframe" action="<%=basePath %>userAction.do?method=userSelect" method="post">
								<label>姓名：</label><input type="text" name="user_name" value="" class="input_79x19"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<label>警员编号：</label><input type="text" name="user_code" value="" class="input_79x19"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<label>所属部门：</label>
										<select name="query_treeId" class="input_130x20">
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
								<input type="submit" class="blue_mod_btn" value="搜 &nbsp;索" />
								<a id="closeDialog" style="display:none" href="javascript:void(0)" class="Js_closePar blue_mod_btn" class="">取&nbsp;&nbsp;消</a>
					</form>
							</div>
						</div>
						<iframe src="" name="userChooseIframe" width="578" height="330" frameborder="0" scrolling="no"></iframe>
</div>

<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>

<script>
function closeDialog()
{
	$('#closeDialog').click();
}

if(static_ftpLogin)//ftp登录没有问题
{
	showInformation("请您选择需要上传的方式");
}
//打开提示图片信息
function showImg(showIndex)
{
jQuery(function($) {
	if(static_imgShow)
	{
		$("#imgShow").attr("src", contextPath()+"/images/uploadtype"+showIndex+".jpg");
		$("#imgShow").css("display", "block");
	}
});
}
//隐藏提示图片信息
function hideImg(showIndex)
{
jQuery(function($) {
	$("#imgShow").css("display", "none");
});
}
//弹出 从USB设备获取文件 上传至服务器
function uploadTypeDiv1()
{
jQuery(function($) {
	$('#uploadNameValue1').val("");
	hideObj("fileManagerAddMsg");
	hideObj("uploadType2");
	hideObj("uploadType3");
	uploadTable('<%=uf.getTreeId() %>,<%=uf.getUserId() %>');
	loadPage("fileUploadDiv", contextPath()+"/common/fileUpload.jsp?uploadType=server");
	$("#fileUploadDiv").css("display", "block");
	static_imgShow = false;
	fileTotalNums = 0;//上传总个数 重置
	static_uploaded = 0;//当前上传个数 重置
});
}
//弹出 从USB设备获取文件 上传至本地文件夹
function uploadTypeDiv2()
{
jQuery(function($) {
	$('#uploadNameValue1').val("");
	hideObj("fileManagerAddMsg");
	hideObj("uploadType1");
	hideObj("uploadType3");
	$("#imgShow").css("display", "none");
	loadPage("fileUploadDiv", contextPath()+"/common/fileUpload.jsp?uploadType=local");
	$("#fileUploadDiv").css("display", "block");
	static_imgShow = false;
	fileTotalNums = 0;//上传总个数 重置
	static_uploaded = 0;//当前上传个数 重置
});
}
//弹出 从本地文件夹获取文件 上传至服务器
function uploadTypeDiv3()
{
jQuery(function($) {
	$('#uploadNameValue1').val("");
	hideObj("fileManagerAddMsg");
	hideObj("uploadType1");
	hideObj("uploadType2");
	uploadTable2('<%=userTreeId %>,<%=userUserId %>');
	$("#imgShow").css("display", "none");
	loadPage("fileUploadDiv", contextPath()+"/common/fileUpload.jsp");
	$("#fileUploadDiv").css("display", "block");
	static_imgShow = false;
	fileTotalNums = 0;//上传总个数 重置
	static_uploaded = 0;//当前上传个数 重置
});
}

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
});
}
</script>
</body>
</html>
