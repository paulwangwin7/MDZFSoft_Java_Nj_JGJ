<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List list = null;
String text = "年";
System.out.println("======================="+request.getAttribute("uploadLogList_ByTree2"));
if(request.getAttribute("uploadLogList_ByTree2")!=null)
{
	int tree2_searchType = Integer.parseInt(request.getParameter("tree2_searchType"));
	if(tree2_searchType!=1)
	{
		text = "月";
	}
	list = (List)request.getAttribute("uploadLogList_ByTree2");
	float totalNums = 0;
	for(int i=0;i<list.size();i++)
	{
		totalNums += Integer.parseInt(((List)list.get(i)).get(1).toString());
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中队上传统计报表图</title>
		
		
		<!-- 1. Add these JavaScript inclusions in the head of your page -->
		<script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/highcharts.js"></script>
		<!--[if IE]>
			<script type="text/javascript" src="<%=basePath %>js/excanvas.compiled.js"></script>
		<![endif]-->
		
		
		<!-- 2. Add the JavaScript to initialize the chart on document ready -->
		<script type="text/javascript">
		$(document).ready(function() {
			var chart = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					margin: [50, 200, 60, 170]
				},
				title: {
					text: '中队上传文件<%=text%>报 <%=tree2_searchType==1?request.getParameter("tree2_year"):request.getParameter("tree2_year")+"-"+request.getParameter("tree2_month")%>'
				},
				plotArea: {
					shadow: null,
					borderWidth: null,
					backgroundColor: null
				},
				tooltip: {
					formatter: function() {
						return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
					}
				},
				plotOptions: {
					pie: {
						dataLabels: {
							enabled: true,
							formatter: function() {
								if (this.y > 5) return this.point.name;
							},
							color: 'white',
							style: {
								font: '13px Trebuchet MS, Verdana, sans-serif'
							}
						}
					}
				},
				legend: {
					layout: 'vertical',
					style: {
						left: 'auto',
						bottom: 'auto',
						right: '50px',
						top: '100px'
					}
				},
			        series: [{
					type: 'pie',
					name: 'Browser share',
					data: [
			<%
				for(int i=0; i<list.size();i++)
				{
					List tempList = (List)list.get(i);
			%>
						{
							name: '<%=tempList.get(0).toString()%>(<%=tempList.get(1).toString()%>)',
							y: <%=(totalNums>0)?((Float.parseFloat(tempList.get(1).toString())<=0?0.01:Float.parseFloat(tempList.get(1).toString()))/totalNums)*100:0.1%>,
							sliced: false
						}
			<%
					if(i!=list.size()-1)
					{
						out.print(",");
					}
				}
			%>
					]
				}]
			});
			
			
		});
		</script>
		
	</head>
	<body>
		
		<!-- 3. Add the container -->
		<div id="container" style="width: 800px; height: 400px"></div>
	</body>
</html>
<%
}
%>
