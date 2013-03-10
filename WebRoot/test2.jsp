<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery_dialog.js"></script>
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
<script>var menuIndex = 1;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
					<h4 class="content_hd long_content_hd">统计报表</h4>
					<div class="content_bd">
						<ul class="Js_tab tab_list">
							<li class="tab_item current">
								大队统计
							</li>
							<li class="tab_item">中队统计</li>
							<li class="tab_item">警员统计</li>
						</ul>
						<ul class="tab_conlist">
							<li class="tab_conitem current">
							<div class="gray_bor_bg">
								<h5 class="gray_blod_word">组合条件搜索</h5>
								<div class="search_form">
									大队统计
									<div class="mt_10">
									<label>按年份:</label>
									<select class="input_79x19">
									<option value="" >年份</option>
									</select>&nbsp;&nbsp;								
									<input type="button"class="blue_mod_btn" id="dialog" value="统&nbsp;计1" />
									<div style="display:none" class="boxcontent">
										<div class="gray_bor_bg">
											<h5 class="gray_blod_word">组合条件搜索</h5>
											<div class="search_form">
												<div class="mt_10"><label>上传人:</label>
													<select class="input_79x19">
														<option value="" >上传人</option>
													</select>&nbsp;&nbsp;&nbsp;&nbsp;
												<label>操作时段:</label><input type="text" class="input_79x19" id="begainDate"/>&nbsp;&nbsp;-&nbsp;&nbsp;<input type="text" class="input_79x19" id="endDate"/>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<label>所属部门:</label><input type="text" class="input_79x19" />
												</div>
												<div class="mt_10">
												<label>采集人:</label>
												<select class="input_79x19">
														<option value="" >上传人</option>
													</select>&nbsp;&nbsp;&nbsp;&nbsp;
												<label>操作时段:</label><input type="text" class="input_79x19" id="UploadBeginDate"/>&nbsp;&nbsp;-&nbsp;&nbsp;<input type="text" class="input_79x19" id="UploadendDate"/>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<label>文件备注:</label><input type="text" class="input_79x19" />
												<a href="" class="blue_mod_btn">搜 &nbsp;索</a>
												</div>
											</div>
											
										</div>
										<div class="mt_10">
											<table class="Js_grayBg common_table file_table">
												<tr>
													<th>全选</th><th>上传人</th><th>采集人</th><th>上传文件名</th><th>上传时间</th><th>采集时间</th><th>重要性</th><th>备注</th><th>上传人IP</th><th>上传人部门</th>
												</tr>
												<tr>
													<td class="textc"><input type="checkbox" /></td>
													<td class="textc">zdadmin</td>
													<td class="textc">ksi5691s</td>
													<td class="textc">20120306_lixmnr</td>
													<td class="textc">2012-03-06</td>
													<td class="textc">2012-03-06</td>
													<td class="textc">普通</td>
													<td class="textc">暂无</td>
													<td class="textc">192.168.1.1</td>
													<td class="textc">交警一大队 3中队</td>
												</tr>
												<tr>
													<td class="textc"><input type="checkbox" /></td>
													<td class="textc">zdadmin</td>
													<td class="textc">ksi5691s</td>
													<td class="textc">20120306_lixmnr</td>
													<td class="textc">2012-03-06</td>
													<td class="textc">2012-03-06</td>
													<td class="textc">普通</td>
													<td class="textc">暂无</td>
													<td class="textc">192.168.1.1</td>
													<td class="textc">交警一大队 3中队</td>
												</tr>
												<tr>
													<td class="textc"><input type="checkbox" /></td>
													<td class="textc">zdadmin</td>
													<td class="textc">ksi5691s</td>
													<td class="textc">20120306_lixmnr</td>
													<td class="textc">2012-03-06</td>
													<td class="textc">2012-03-06</td>
													<td class="textc">普通</td>
													<td class="textc">暂无</td>
													<td class="textc">192.168.1.1</td>
													<td class="textc">交警一大队 3中队</td>
												</tr>
												<!--table opt-->
												<tr>
													<td class="pr textc">
														<a href="" class="blue_mod_btn">确认删除</a>
													</td>
													<td class="pl textc" colspan="9">
														<div class="pagination">
															<a href="">上一页</a>
															<span>
																<a href="" class="current">1</a>
																<a href="">2</a>
																<a href="">3</a>
																...
																<a href="">10</a>
															</span>
															<a href="">下一页</a>
														</div>
													</td>
												</tr>
											</table>
										</div>
									</div>
									</div>
								</div>
							</div>
							</li>
							<li class="tab_conitem">
							<div class="gray_bor_bg">
								<h5 class="gray_blod_word">组合条件搜索</h5>
								中队统计
								<div class="search_form">
									<div class="mt_10">
									<label>按年份:</label>
									<select class="input_79x19">
									<option value="" >年份</option>
									</select>&nbsp;&nbsp;								
									<a href="" class="blue_mod_btn">统 &nbsp;计</a>
									</div>
								</div>
							</div>
							</li>
							<li class="tab_conitem">
							<div class="gray_bor_bg">
								<h5 class="gray_blod_word">组合条件搜索</h5>
								警员统计
								<div class="search_form">
									<div class="mt_10">
									<label>按年份:</label>
									<select class="input_79x19">
									<option value="" >年份</option>
									</select>&nbsp;&nbsp;								
									<a href="" class="blue_mod_btn">统 &nbsp;计</a>
									</div>
								</div>
							</div>
							</li>
						</ul>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</div>
		</div>
	</div>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>
</body>
</html>
