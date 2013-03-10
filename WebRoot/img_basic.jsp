<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List list = null;
if(request.getAttribute("uploadLogList_ByUser")!=null)
{
	int user_searchType = Integer.parseInt(request.getParameter("user_searchType"));
	list = (List)request.getAttribute("uploadLogList_ByUser");
	if(list!=null && list.size()>0)
	{
	System.out.println("==============="+list.size());
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
			<script type="text/javascript" src="<%=basePath %>js/excanvas.compiled.js"></script>
		<![endif]-->
		
		
		<!-- 2. Add the JavaScript to initialize the chart on document ready -->
		<script type="text/javascript">
		$(document).ready(function() {
			var chart = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					defaultSeriesType: 'column',
					margin: [ 50, 50, 100, 80]
				},
				title: {
					text: '警员 <%=request.getParameter("user_name")%> <%=request.getParameter("user_year")%>年<%=user_searchType==1?"":(request.getParameter("user_month")+"月")%>上传数据'
				},
				xAxis: {
					categories: [
				<%
					if(user_searchType==2)
					{
						for(int i=1; i<=31; i++) //固定每月31天
						{
							out.print("'"+((i<10)?("0"+i):i)+"日'");
							if(i!=31)
							{
								out.print(",");
							}
						}
					}
					else
					{
						for(int i=1; i<=12; i++)
						{
							out.print("'"+((i<10)?("0"+i):i)+"月'");
							if(i!=28)
							{
								out.print(",");
							}
						}
				%>
				<%
					}
				%>
					],
					labels: {
						rotation: -45,
						align: 'right',
						style: {
							 font: 'normal 13px Verdana, sans-serif'
						}
					}
				},
				yAxis: {
					min: 0,
					title: {
						text: '警员 (<%=request.getParameter("user_name")%>)'
					}
				},
				legend: {
					enabled: false
				},
				tooltip: {
					formatter: function() {
						return '<b><%=request.getParameter("user_year")%>年<%=user_searchType==1?"":(request.getParameter("user_month")+"月")%>'+ this.x +'</b><br/>'+
							 '警员 (<%=request.getParameter("user_name")%>) '+ Highcharts.numberFormat(this.y, 0) +
							 ' 个文件';
					}
				},
			        series: [{
					name: 'Population',
					data: [
				<%
					for(int i=0; i<list.size()&&i<DateUtils.getMonthDays(request.getParameter("user_year")+request.getParameter("user_month")); i++)
					{
						out.print(list.get(i).toString());
						if(i<list.size()-1)
						{
							out.print(",");
						}
					}
				%>
						],
					dataLabels: {
						enabled: true,
						rotation: -90,
						color: '#FFFFFF',
						align: 'right',
						x: 15,
						y: 10,
						formatter: function() {
							return this.y;
						},
						style: {
							font: 'normal 13px Verdana, sans-serif'
						}
					}			
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
}
%>