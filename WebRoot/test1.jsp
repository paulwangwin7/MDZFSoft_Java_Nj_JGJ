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
<link rel="stylesheet" type="text/css" href="css/all.css" />
<script type="text/javascript" src="js/jquery.js"></script>
</head>

<body>
	<!--header=============================================begin-->
<jsp:include page="common/header.jsp" />
<script>var menuIndex = 3;</script>
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
						<ul class="user_info">
							<li>警员编号：<span>13716018</span></li>
							<li>所属部门：<span>市交警一大队</span></li>
							<li>注册时间：<span>12/01/12 14：30</span></li>
						</ul>
					</div>
				</div>
				<div class="mod_white mt_10">
					<h4 class="mod_hd">
						个人菜单
					</h4>
					<div class="inner_bor">
						<ul class="user_orders">
							<li><a href="">我的上传</a></li>
							<li><a href="">所有公告</a></li>
							<li><a href="">修改密码</a></li>
							<li><a href="">修改资料</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="w_805 fr">
				<div class="white_p10">
					<h4 class="content_hd">公告管理</h4>
					<div class="error msg" id="loginMsg" style="display:none" onclick="hideObj($(this).attr('id'))"></div>
					<div class="content_bd">
						<div class="mange_table">
							<table class="Js_grayBg common_table">
								<colgroup>
									<col class="col_220" />
									<col class="col_235" />
									<col class="col_65" />
									<col class="col_60" />
								</colgroup>
								<tr>
									<th>公告标题</th><th>公告内容</th><th>创建时间</th><th>相关操作</th>
								</tr>
								<tr>
									<td>关于龙西路施工期间交通管制胡通告</td>
									<td>因龙西路工程施工需要，经市政府批准，定于2010年6月6日起至2010年12月底1号止对龙西路（宁丹路至有坊桥中新路）进行交通管制</td>
									<td class="textc">2012-03-06</td>
									<td class="textc"><a href="" class="blue_link">删除</a></td>
								</tr>
								<tr>
									<td>关于龙西路施工期间交通管制胡通告</td>
									<td>因龙西路工程施工需要，经市政府批准，定于2010年6月6日起至2010年12月底1号止对龙西路（宁丹路至有坊桥中新路）进行交通管制</td>
									<td class="textc">2012-03-06</td>
									<td class="textc"><a href="" class="blue_link">删除</a></td>
								</tr>
								<tr>
									<td>关于龙西路施工期间交通管制胡通告</td>
									<td>因龙西路工程施工需要，经市政府批准，定于2010年6月6日起至2010年12月底1号止对龙西路（宁丹路至有坊桥中新路）进行交通管制</td>
									<td class="textc">2012-03-06</td>
									<td class="textc"><a href="" class="blue_link">删除</a></td>
								</tr>
								<!--table opt-->
								<tr>
									<td class="pr">
										<a href="" class="blue_mod_btn">新增公告</a>
									</td>
									<td class="pl" colspan="3">
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
						<div class=" mt_10 pb_200">
							
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
