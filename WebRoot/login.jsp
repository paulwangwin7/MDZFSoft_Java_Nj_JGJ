<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>名都执法记录仪管理系统软件 V2.0</title>
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath %>pagejs/login.js"></script>
</head>

<body class="login_box">
<!--header=============================================begin-->
<div id="header">
	<div class="af_hd">
		<a href="" class="logo" >
			<img src="images/logo.png" alt="" />
		</a>
	</div>
</div>
<!--content============================================begin-->
<div class="login_main">
<div class="error msg" id="loginMsg" style="display:none" onclick="hideObj($(this).attr('id'))"></div>
	<form action="#" onsubmit="return userLogin()" method="post">
		<ul class="form_list">
			<li class="form_item">
				<label class="label">用户名</label>
				<input type="text" class="input_186x25" id="username" />
			</li>
			<li class="form_item">
				<label class="label">密&nbsp;&nbsp;码</label>
				<input type="password" class="input_186x25" id="userpswd" />			
			</li>
			<li class="form_item">
				<label class="label">验证码</label>
				<input type="text" class="input_67x25" id="checkcode" />	
				<img id="checkImg" src="checkCode.jsp" class="validate_img" /><a href="javascript:changeCheckImg()">重获验证码</a>
				<p class="pl_50 mt_10">
					<input type="submit" class="login_btn" value="" />
				</p>
			</li>
		</ul>
	</form>
	<div class="login_opt white">
		<span class="h_l"></span>
		<span class="h_r"></span>
		<a href="<%=basePath %>downloads/mdDrivers.zip" class="white" target="_blank">执法记录仪驱动下载</a>&nbsp;&nbsp;|&nbsp;
		<a href="<%=basePath %>downloads/md_1.0.0.5.CAB" class="white" target="_blank">上传插件下载</a>
	</div>
</div>
<div class="footer_out">
<jsp:include page="common/footer.jsp" />
</div>
</body>
</html>