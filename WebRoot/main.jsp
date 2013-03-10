<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心公告管理</title>
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath %>pagejs/main.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery_dialog.js"></script>
</head>

<body>
<script>
$(document).ready(function(){
	$("#dialog").click(function(){
	$.weeboxs.open('.boxcontent', {title:'统计表报',contentType:'selector'});
});})
</script>
	<!--header=============================================begin-->
<jsp:include page="common/header.jsp" />
<script>var menuIndex = 0;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="w_185 fl">
				<div class="mod_white">
					<h4 class="mod_hd">
						个人信息
					</h4>
					<div class="inner_bor">
<jsp:include page="common/userinfo.jsp" />
					</div>
				</div>
				<div class="mod_white mt_10">
					<h4 class="mod_hd">
						个人菜单
					</h4>
					<div class="inner_bor">
<jsp:include page="common/usermenu.jsp" />
					</div>
				</div>
			</div>
			<div class="w_805 fr">
				<div class="white_p10">
					<h4 class="content_hd" id="mainTitle">公告管理</h4>
					<div class="content_bd">
						<div class="mange_table" id="mainContext">
<jsp:include page="common/notice.jsp" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>
</body>
</html>
