<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>上传报表</title>
<meta charset="utf-8">

<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/skins/gray.css" title="gray">

<link rel="alternate stylesheet" type="text/css" href="css/skins/orange.css" title="orange">
<link rel="alternate stylesheet" type="text/css" href="css/skins/red.css" title="red">
<link rel="alternate stylesheet" type="text/css" href="css/skins/green.css" title="green">
<link rel="alternate stylesheet" type="text/css" href="css/skins/purple.css" title="purple">
<link rel="alternate stylesheet" type="text/css" href="css/skins/yellow.css" title="yellow">
<link rel="alternate stylesheet" type="text/css" href="css/skins/black.css" title="black">
<link rel="alternate stylesheet" type="text/css" href="css/skins/blue.css" title="blue">

<link rel="stylesheet" type="text/css" href="css/superfish.css">
<link rel="stylesheet" type="text/css" href="css/uniform.default.css">
<link rel="stylesheet" type="text/css" href="css/jquery.wysiwyg.css">
<link rel="stylesheet" type="text/css" href="css/facebox.css">
<link rel="stylesheet" type="text/css" href="css/demo_table_jui.css">
<link rel="stylesheet" type="text/css" href="css/smoothness/jquery-ui-1.8.8.custom.css">

<!--[if lte IE 8]>
<script type="text/javascript" src="js/html5.js"></script>
<script type="text/javascript" src="js/selectivizr.js"></script>
<script type="text/javascript" src="js/excanvas.min.js"></script>
<![endif]-->

<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="pageJs/common.js"></script>
<script type="text/javascript" src="pageJs/dateUtil.js"></script>
<script type="text/javascript" src="pageJs/analysisUpload.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/jquery.uniform.min.js"></script>
<script type="text/javascript" src="js/jquery.wysiwyg.js"></script>
<script type="text/javascript" src="js/superfish.js"></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/Delicious_500.font.js"></script>
<script type="text/javascript" src="js/jquery.flot.min.js"></script>
<script type="text/javascript" src="js/custom.js"></script>
<script type="text/javascript" src="js/facebox.js"></script>

<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>

<script type="text/javascript">
jQuery(function($) {
	$('#datatable').dataTable({
		'bJQueryUI': true,
		'sPaginationType': 'full_numbers'
	});
});
</script>

<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/switcher.js"></script>

</head>
<body>

<header id="top">
	<div class="container_12 clearfix">
		<div id="logo" class="grid_5">
		<jsp:include page="../common/logo.html"></jsp:include>
		</div>
		
		<div class="grid_4" id="colorstyle">
		<jsp:include page="../common/colorstyle.jsp"></jsp:include>
		</div>

		<div id="userinfo" class="grid_3">
		<jsp:include page="../common/welcome.jsp"></jsp:include>
		</div>
	</div>
</header>

<nav id="topmenu">
	<div class="container_12 clearfix">
		<div class="grid_12">
		<jsp:include page="../common/navigationBar.jsp"></jsp:include>
		</div>
	</div>
</nav>

<section id="content">
	<section class="container_12 clearfix">
		<section id="main">
			<article>
			<div class="error msg" id="analysisUploadMsg" style="display:none" onclick="hideObj('analysisUploadMsg')">Message if login failed</div>
				<!--ul class="tabs"-->
				<div id="analysisTabs">
					<a href="###" rel="tabs-1_" class="tabButtonFocus">大队统计</a><a href="###" rel="tabs-2_" class="tabButton">中队统计</a><a href="###" rel="tabs-3_" class="tabButton">警员统计</a>
				</div>
				<!--/ul-->
				<div class="tabcontent_">
					<div id="tabs-1_">
						<p>
						</p>
					</div>
					<div id="tabs-2_" style="display:none">
						<p>
						</p>
					</div>
					<div id="tabs-3_" style="display:none">
						<p>
						</p>
					</div>
				</div>
			</article>
		</section>
	</section>
</section>

<footer id="bottom">
<jsp:include page="../common/footer.jsp"></jsp:include>
</footer>
<jsp:include page="../common/dialogArea.jsp"></jsp:include>
<script>
var yearArray = getYearArrChar8("<%=DateUtils.getChar8()%>");
var nowMonth = "<%=DateUtils.getChar8().substring(4,6)%>";
jQuery(function($) {
	loadPage("tabs-1_", contextPath()+"/common/queryRootTree.html");
	loadPage("tabs-2_", contextPath()+"/common/queryChildTree.html");
	loadPage("tabs-3_", contextPath()+"/common/queryPersonal.html");
});
jQuery(function($) {
	$("#analysisTabs a").click(function(){
		showTabs($(this).attr("rel"));
		$("#analysisTabs a").each(function(){
			$(this).attr("class", "tabButton");
		});
		$(this).attr("class", "tabButtonFocus");
	});
});
function showTabs(tabsId)
{
jQuery(function($) {
	$("#tabs-1_").css("display", "none");
	$("#tabs-2_").css("display", "none");
	$("#tabs-3_").css("display", "none");
	$("#"+tabsId).css("display", "block");
});
}
</script>
</body>
</html>
