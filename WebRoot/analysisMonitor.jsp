<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心统计报表</title>
<link rel="stylesheet" type="text/css" href="css/all.css" />
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath %>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath %>js/adddate.js"></script>
<script type="text/javascript" src="<%=basePath %>pagejs/dateUtil.js"></script>
<script type="text/javascript" src="<%=basePath %>pagejs/analysisMonitor.js"></script>
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
<script>var menuIndex = 5;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
					<h4 class="content_hd long_content_hd">系统监控</h4>
					<div class="content_bd">
<div class="error msg" id="serverInfoMsg" style="display:none" onclick="hideObj($(this).attr('id'))">Message if login failed</div>
						<div class="gray_bor_bg">
							<h5 class="gray_blod_word">组合条件搜索</h5>
					<form action="<%=basePath %>servletAction.do?method=serverInfoQuery" method="post" target="monitor">
					<input type="hidden" id="req_statDay_beginTime" name="statDay_beginTime" />
							<div class="search_form">
								<div class="mt_10">统计粒度：
									<select class="input_79x19" id="req_queryType" name="queryType">
										<option value="1">按天统计</option>
										<option value="2">按周统计</option>
										<option value="3">按月统计</option>
										<option value="4">按年统计</option>
									</select>&nbsp;&nbsp;&nbsp;&nbsp;
									<label>
									<div id="type_1">
									统计时间：<input type="text" class="input_79x19" id="req_statTime" name="statTime" onclick="SelectDate(this,'MM/dd/yyyy')" readonly />
									</div>
									<div id="type_2" style="display:none">
									统计时间：<input type="text" class="input_79x19" id="req_statDay_beginTimeShow" disabled />~<input type="text" class="input_79x19" id="req_statDay_endTime" name="statDay_endTime" onchange="changeWeekDay()" onclick="SelectDate(this,'MM/dd/yyyy')" readonly />
									</div>
									<div id="type_3" style="display:none">
									统计时间：<select id="req_statMonth_1" name="statMonth_1" class="input_79x19"></select><select id="req_statMonth_2" name="statMonth_2" class="input_79x19"></select>
									</div>
									<div id="type_4" style="display:none">
									统计时间：<select class="input_79x19" id="req_statYear" name="statYear"></select>
									</div>
									</label>&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="submit" class="blue_mod_btn" value="搜 &nbsp;索" />
								</div>
							</div>
					</form>
						</div>
					<iframe id="monitor" name="monitor" src="" width="916px" height="410px" frameborder="0" scrolling="no"></iframe>
						<div class=" mt_10">
							
						</div>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</div>
			</div>
	</div>
<script>
//数据初始化
jQuery(function($) {
	$("#req_statTime").val(formatJsChar8("<%=DateUtils.getChar8()%>"));
	$("#req_statDay_endTime").val(formatJsChar8("<%=DateUtils.getChar8()%>"));
	$("#req_statDay_beginTime").val(formatJsChar8("<%=DateUtils.rollDate(DateUtils.getChar8(), -6)%>"));
	$("#req_statDay_beginTimeShow").val(formatJsChar8("<%=DateUtils.rollDate(DateUtils.getChar8(), -6)%>"));
	var yearArray = getYearArrChar8("<%=DateUtils.getChar8()%>");
	for(i=0; i<yearArray.length; i++)
	{
		var appendStr = "<option value='"+yearArray[i]+"'";
		if((i+1)==yearArray.length)
		{
			appendStr += " selected";
		}
		appendStr += ">"+yearArray[i]+"</option>";
		$("#req_statMonth_1").append(appendStr);
		$("#req_statYear").append(appendStr);
	}

	for(i=1; i<=12; i++)
	{
		var monthStr = (i<10)?("0"+i):i;
		var appendStr = "<option value='"+monthStr+"'";
		if(monthStr=="<%=DateUtils.getChar8().substring(4,6)%>")
		{
			appendStr += " selected";
		}
		appendStr += ">"+monthStr+"</option>";
		$("#req_statMonth_2").append(appendStr);
	}

	$("#req_queryType").change(function(){
		showChooseTime();
	});
});
</script>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>
</body>
</html>
