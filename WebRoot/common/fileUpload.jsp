<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table align="right">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String uploadType = request.getParameter("uploadType")==null?"":request.getParameter("uploadType");
	if(uploadType.equals("local"))//save到本地
	{
%>
<tr valign="top"><td>
	<div id="selectLetter"></div>
	<div id="localSaveDiv" style="display:none">
	&nbsp;&nbsp;&nbsp;&nbsp;<label>选择文件夹：</label>
	<input type="text" id="localSaveDir" readonly onclick="selectLocalSaveDir()"/>
	<input type="button" class="button" id="localSaveButton" onclick="copyLocalFile()" value="确认"/>
	<br/>
	</div>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<label>
	<div>
<iframe id="sc" src="" width="470px" height="200px" frameborder="0"></iframe>
<iframe name="db" src="" width="0" height="0" frameborder="0"></iframe>
	</div>
	</label>
</td></tr>
</table>
<script>
getUsbDriver();
</script>
<%
	}
	else if(uploadType.equals("server"))//upload到服务器
	{
%>
<table>
<tr valign="top"><td>
	<div id="selectLetter"></div>
	<div id="localSaveDiv" style="display:none"><br/>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<label>采&nbsp;集&nbsp;人：</label>
	<input type="text" id="editName" name="editName" value="" class="input_79x19" readonly onclick="userChoose()" /><br/><br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<label>上传名称：</label>
	<input type="text" style="width:280px" id="uploadNameValue1" class="input_79x19" name="uploadNameValue" value="" dataType="LimitB" min="4" max="80" msg="上传名称【4-80】个字符" />
	<br/>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<a class="blue_mod_btn" href="#" onclick="startUpload()">开始上传</a>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<label>
	<div>
<iframe id="sc" src="" width="470px" height="200px" frameborder="0"></iframe>
<iframe name="db" src="" width="0" height="0" frameborder="0"></iframe>
	</div></label>
	</div>
</td></tr>
</table>
<script>
getUsbDriver();
</script>
<%
	}
	else//本地upload到服务器
	{
%>
<table>
<tr valign="top"><td>
	&nbsp;&nbsp;&nbsp;&nbsp;<label>选择文件夹(上传至服务器)：</label>
	<input type="text" id="localUploadDir" readonly onclick="selectLocalSaveDir2()" class="input_79x19"/>
	<br/>
</td></tr>
<tr valign="top" id="localUploadDiv" style="display:none"><td>
	
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<label>采&nbsp;集&nbsp;人：</label>
	<input type="text" id="editName" name="editName" value="" class="input_79x19" readonly onclick="userChoose()" /><br/><br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<label>上传名称：</label>
	<input type="text" style="width:280px" id="uploadNameValue1" class="input_79x19" name="uploadNameValue" value="" dataType="LimitB" min="4" max="80" msg="上传名称【4-80】个字符" />
	<br/>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<a class="blue_mod_btn" href="#" onclick="startUpload()">开始上传</a>
	<br/>&nbsp;&nbsp;&nbsp;&nbsp;<label>
<iframe id="sc" src="" width="470px" height="200px" frameborder="0"></iframe>
<iframe name="db" src="" width="0" height="0" frameborder="0"></iframe>
	<!--/div-->
	</label>
</td></tr>
</table>
<%
	}
%>
<script>
function setFocus()
{
	$('#uploadNameValue1').focus();
}
</script>