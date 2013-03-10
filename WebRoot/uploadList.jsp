<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*, com.manager.pub.bean.*" %>
<%
	List<UploadForm> uploadFormList = null;
	int thispagecute = 1;
	try
	{
		com.manager.pub.bean.Page pageRst = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
		thispagecute = pageRst.getPageCute();
		uploadFormList = (List<UploadForm>)(pageRst.getListObject());
	}
	catch(Exception ex)
	{
	}
%>
<div class=" mt_10">
	<ul class="upload_list">
	<%
	if(uploadFormList!=null)
	{
		for(int i=0; i<uploadFormList.size(); i++)
		{
			UploadForm uploadForm = uploadFormList.get(i);
			String fileType = "图片";
			if(uploadForm.getPlayPath().substring(uploadForm.getPlayPath().length()-3,uploadForm.getPlayPath().length()).equals("avi"))
			{
				fileType = "视频";
			}
			String fileState = "有效";
			if(uploadForm.getFileState().equals("U"))
			{
				fileState = "无效";
			}
			else if(uploadForm.getFileState().equals("F"))
			{
				fileState = "过期";
			}
			if(uploadForm!=null)
			{
	%>
	<li class="upload_item_">
		<a href="javascript:void(0)" rel="facebox" onclick="showUploadDetail('<%=i %>')">
		<img src="<%=uploadForm.getFileSavePath() %>/upload/files/<%=uploadForm.getShowPath()%>" width="96" height="96" />
		</a>
		<div class="links">
			<!--<%=uploadForm.getFileStats().equals("0")?"":"<font color=red>★</font>" %>&nbsp;-->
			<%=uploadForm.getFileStats().equals("0")?"":"<img src='images/red-star.png' width='11px' height='10px' style='postion:static; top:-50%; left:-50%'/>" %>&nbsp;
			<a href="javascript:void(0)" rel="facebox" onclick="showUploadDetail('<%=i %>')">详情</a>
			<input type="hidden" id="img_<%=i %>" value="<%=uploadForm.getFileSavePath() %>/upload/files/<%=uploadForm.getShowPath()%>" />
			<input type="hidden" id="type_<%=i %>" value="<%=fileType %>" />
			<input type="hidden" id="name_<%=i %>" value="<%=uploadForm.getUploadName() %>" />
			<input type="hidden" id="uploadTime_<%=i %>" value="<%=DateUtils.formatChar14Time(uploadForm.getUploadTime()==null?"":uploadForm.getUploadTime()) %>" />
			<input type="hidden" id="user_<%=i %>" value="<%=uploadForm.getUserName() %>" />
			<input type="hidden" id="edit_<%=i %>" value="<%=uploadForm.getEditName() %>" />
			<input type="hidden" id="createTime_<%=i %>" value="<%=DateUtils.formatChar14Time(uploadForm.getFileCreatetime()==null?"":uploadForm.getFileCreatetime()) %>" />
			<input type="hidden" id="state_<%=i %>" value="<%=fileState %>" />
			<input type="hidden" id="stats_<%=i %>" value="<%=uploadForm.getFileStats().equals("0")?"普通":"重要★" %>" />
			<input type="hidden" id="fileId_<%=i %>" value="<%=uploadForm.getUploadId() %>" />
			<input type="hidden" id="_file_remark_<%=i %>" value="<%=uploadForm.getFileRemark()==null?"":uploadForm.getFileRemark() %>" />
		</div>
	</li>
	<%
			}
		}
	}
	%>
</ul>
</div>
<div id="uploadDetail" style="display:none">
<hr>
<TABLE>
	<tr>
		<td>
			上传详情：
		</td>
		<td align="right">
		<button type="button" class="button" onclick="hideObj('uploadDetail')">隐藏详情</button>
		</td>
	</tr>
<TR>
<TD WIDTH=500PX>
<table width="100%" align="center" border="0">
	<tr>
		<td width="20%">
			<img id="img_" src="" width="120" height="120" /><br/>
		</td>
		<td>
			<table>
				<tr>
					<td align="right">文件类型</td>
					<td>：</td>
					<td id="type_"></td>
				</tr>
				<tr>
					<td align="right">上传文件名</td>
					<td>：</td>
					<td id="name_"></td>
				</tr>
				<tr>
					<td align="right">上传时间</td>
					<td>：</td>
					<td id="uploadTime_"></td>
				</tr>
				<tr>
					<td align="right">上传人</td>
					<td>：</td>
					<td id="user_"></td>
				</tr>
				<tr>
					<td align="right">采集人</td>
					<td>：</td>
					<td id="edit_"></td>
				</tr>
				<tr>
					<td align="right">采集时间</td>
					<td>：</td>
					<td id="createTime_"></td>
				</tr>
				<tr>
					<td align="right">文件状态</td>
					<td>：</td>
					<td id="state_"></td>
				</tr>
				<tr>
					<td align="right">文件重要性</td>
					<td>：</td>
					<td id="stats_"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<b>
		<font color="red">
			<button type="button" class="blue_mod_btn" onclick="uploadFileStats('1')">"重要★"</button>
			<button type="button" class="blue_mod_btn" onclick="uploadFileStats('0')">"普通"</button>
		</font>
		</b>
		</td>
	</tr>
</table>
</TD>
<TD align="left" valign="top">
<table>
<tr>
<td>
备注信息：<br/><textarea name="_file_remark_" id="_file_remark_" style="width:200px; height:100px"></textarea><br/>
<button type="button" class="blue_mod_btn" onclick="uploadFileRemark()">备注修改</button>
</td>
</tr>
</table>
</TD>
</TR>
</TABLE>
</div>

<div class="tablefooter clearfix">
<jsp:include page="common/page.jsp?function=mineUpload"></jsp:include>
</div>
<form name="uploadFileStatsForm" id="uploadFileStatsForm" action="userAction.do?method=uploadFileStats" method="post">
<input type="hidden" name="fileId" id="_fileId_" />
<input type="hidden" name="stats" id="_stats_" />
<!--input type="hidden" name="file_remark" value="这个文件很重要" id="_file_remark_" /-->
</form>
<script>
function uploadFileStats(statsVal)
{
jQuery(function($) {
	$('#_stats_').val(statsVal);
	$.ajax({
		url:contextPath()+'servletAction.do?method=uploadFileStats',
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {"fileId":$("#_fileId_").val(),"stats":$("#_stats_").val()},
		success:function(res){
			if(res != null)
			{
				if(res.retCode!=0)
				{
					alert(res.msg);
				}
				else
				{
					alert(res.msg);
					mineUpload('<%=thispagecute%>');
				}
			}
			else{
				showMsg("请求失败，返回结果null", 1);
			}
		},
		error:function(){
			showMsg("请求失败 error function", 1);
		}
	});
});
}

function uploadFileRemark()
{
jQuery(function($) {
	$.ajax({
		url:contextPath()+'servletAction.do?method=uploadFileRemark',
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {"fileId":$("#_fileId_").val(),"file_remark":$("#_file_remark_").val()},
		success:function(res){
			if(res != null)
			{
				if(res.retCode!=0)
				{
					alert(res.msg);
				}
				else
				{
					alert(res.msg);
					mineUpload('<%=thispagecute%>');
				}
			}
			else{
				showMsg("请求失败，返回结果null", 1);
			}
		},
		error:function(){
			showMsg("请求失败 error function", 1);
		}
	});
});
}


/**
 * 查看文件详情
 * @param forIndex 循环下标
 */
function showUploadDetail(forIndex, editAction) {
jQuery(function($) {
	$("#img_").attr("src", $("#img_"+forIndex).val());
	$("#type_").html($("#type_"+forIndex).val());
	$("#name_").html($("#name_"+forIndex).val());
	$("#uploadTime_").html($("#uploadTime_"+forIndex).val());
	$("#user_").html($("#user_"+forIndex).val());
	$("#edit_").html($("#edit_"+forIndex).val());
	$("#createTime_").html($("#createTime_"+forIndex).val());
	$("#state_").html($("#state_"+forIndex).val());
	$("#stats_").html($("#stats_"+forIndex).val());
	$("#_file_remark_").html($("#_file_remark_"+forIndex).val());
	if($("#stats_"+forIndex).val()=="重要★")
	{
		$("#stats_").html("<font color='red'>"+$("#stats_"+forIndex).val()+"</font>");
	}
	$("#_fileId_").val($("#fileId_"+forIndex).val());
	showObj("uploadDetail");
});
}
</script>