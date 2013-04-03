<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List list = null;
String text = "年";
if(request.getAttribute("uploadLogList_ByTree1")!=null)
{
	int session_tree1_searchType = Integer.parseInt(request.getParameter("tree1_searchType"));
	if(session_tree1_searchType!=1)
	{
		text = "月";
	}
	list = (List)request.getAttribute("uploadLogList_ByTree1");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highcharts Example</title>
		
		
		<!-- 1. Add these JavaScript inclusions in the head of your page -->
		<script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/highcharts.js"></script>
		<!--[if IE]>
			<script type="text/javascript" src="../js/excanvas.compiled.js"></script>
		<![endif]-->
		
		
		<!-- 2. Add the JavaScript to initialize the chart on document ready -->
		<script type="text/javascript">
		$(document).ready(function() {
			var chart = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					defaultSeriesType: 'line',
					margin: [50, 150, 60, 80]
				},
				title: {
					text: '大队上传文件<%=text%>报 <%=session_tree1_searchType==1?request.getParameter("tree1_year"):request.getParameter("tree1_year")+"-"+request.getParameter("tree1_month")%>',
					style: {
						margin: '10px 100px 0 0' // center it
					}
				},
				subtitle: {
					text: '',
					style: {
						margin: '0 100px 0 0' // center it
					}
				},
				xAxis: {
					categories: [
				<%
					if(session_tree1_searchType==1) {
				%>
					'1月', '2月', '3月', '4月', '5月', '6月', 
						'7月', '8月', '9月', '10月', '11月', '12月'
				<%
					}
					else {
				%>
					'1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15',
					'16','17','18','19','20','21','22','23','24','25', '26', '27', '28', '29', '30', '31'
				<%
					}
				%>
						],
					title: {
						text: 'Month'
					}
				},
				yAxis: {
					title: {
						text: '上传数据'
					}
				},
				tooltip: {
					formatter: function() {
			                return '<b>'+ this.series.name +'</b><br/>'+
							this.x +': '+ this.y +'（文件数量）';
					}
				},
				legend: {
					layout: 'vertical',
					style: {
						left: 'auto',
						bottom: 'auto',
						right: '10px',
						top: '100px'
					}
				},
				series: [
			<%
				for(int i=0; i<list.size();i++)
				{
					List tempList = (List)list.get(i);
					List tempListList = (List)tempList.get(1);
			%>
				{
					name: '<%=tempList.get(0).toString()%>',
					data: [
				<%
					for(int j=0;j<tempListList.size(); j++)
					{
						out.print(tempListList.get(j).toString());
						if(j!=tempListList.size()-1)
						{
							out.print(",");
						}
					}
				%>
					]
				}
			<%
					if(i!=list.size()-1)
					{
						out.print(",");
					}
				}
			%>
				]
			});
			
			
		});
		</script>
	</head>
	<body>
		<!-- 3. Add the container -->
		<div id="container" style="width: 800px; height: 600px"></div>
	</body>
</html>
<%
}
%>
