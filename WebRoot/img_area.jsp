<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*" %>
<%@ page import="com.manager.pub.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

int countType = request.getParameter("queryType")==null?0:Integer.parseInt(request.getParameter("queryType"));
String imgText = "";
switch(countType)
{
  case 1 : imgText = "按天统计";break;
  case 2 : imgText = "按周统计";break;
  case 3 : imgText = "按月统计";break;
  case 4 : imgText = "按年统计";break;
  default : imgText = "";
}
if(countType>0)
{
	List<ServerInfoForm> list = (List<ServerInfoForm>)request.getAttribute(Constants.JSP_ServerInfo_LIST);
	List showImgList = null;
	if(countType==1)
	{
		showImgList = new ArrayList();
		for(int i=0; i<list.size(); i++)
		{
			ServerInfoForm sif = (ServerInfoForm)list.get(i);
			showImgList.add(sif.getCreateTime()+"#"+sif.getRatioCPU()+"#"+sif.getRatioMEMORY()+"#"+sif.getRatioHARDDISK());
		//System.out.println("=========================string[]=============="+sif.getCreateTime()+"#"+sif.getRatioCPU()+"#"+sif.getRatioMEMORY()+"#"+sif.getRatioMEMORY());
		}
	}
	else if(countType==2)
	{
		String statDay_beginTime = request.getParameter("statDay_beginTime")==null?"":request.getParameter("statDay_beginTime");
		//String[] statDay_beginTime_ = statDay_beginTime.split("-");
		//statDay_beginTime = statDay_beginTime_[0]+(statDay_beginTime_[1].length()!=2?("0"+statDay_beginTime_[1]):statDay_beginTime_[1])+(statDay_beginTime_[2].length()!=2?("0"+statDay_beginTime_[2]):statDay_beginTime_[2])+"000000";
		statDay_beginTime = DateUtils.getChar8ByJs(statDay_beginTime);
		showImgList = new ArrayList();
		for(int i=0; i<7; i++)
		{
			if(i==0)
			{
				statDay_beginTime = DateUtils.rollHour(statDay_beginTime+"000000",0);
			}
			else
			{
				statDay_beginTime = DateUtils.rollHour(statDay_beginTime+"000000",24);
			}
			int z = 0;
			double cpu_ = 0;
			double memory_ = 0;
			double harddisk_ = 0;
			for(int j=0; j<list.size(); j++)
			{
				ServerInfoForm sif = (ServerInfoForm)list.get(j);
				if(sif.getCreateTime().substring(0,8).equals(statDay_beginTime.substring(0,8)))
				{
					cpu_ += sif.getRatioCPU();
					memory_ += sif.getRatioMEMORY();
					harddisk_ += sif.getRatioHARDDISK();
					z++;
				}
			}
			if(z>0)
			{
				showImgList.add(statDay_beginTime.substring(0,8)+"#"+(int)(cpu_/z)+"#"+(int)(memory_/z)+"#"+(int)(harddisk_/z));
			}
			else
			{
				showImgList.add(statDay_beginTime.substring(0,8)+"#0#0#0");
			}
		}
	}
	else if(countType==3)
	{
		showImgList = new ArrayList();
		//for(int i=0; i<list.size(); i++) //固定每月31天
		for(int i=0; i<DateUtils.getMonthDays(request.getParameter("statMonth_1")+request.getParameter("statMonth_2")); i++)
		{
			ServerInfoForm sif = (ServerInfoForm)list.get(i);
			showImgList.add((i+1)+"#"+sif.getRatioCPU()+"#"+sif.getRatioMEMORY()+"#"+sif.getRatioHARDDISK());
		}
	}
	else if(countType==4)
	{
		showImgList = new ArrayList();
		for(int i=0; i<list.size(); i++)
		{
			ServerInfoForm sif = (ServerInfoForm)list.get(i);
			showImgList.add((i+1)+"#"+sif.getRatioCPU()+"#"+sif.getRatioMEMORY()+"#"+sif.getRatioHARDDISK());
		}
	}
	else
	{
		countType = 0;
	}
	if(countType>0)
	{
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		
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
					defaultSeriesType: 'areaspline'
				},
				title: {
					text: '服务器状态数据分析图'
				},
				legend: {
					layout: 'vertical',
					style: {
						position: 'absolute',
						bottom: 'auto',
						left: '150px',
						top: '100px'
					},
					borderWidth: 1,
					backgroundColor: '#FFFFFF'
				},
				xAxis: {
					categories: [
<%
					for(int i=0; i<showImgList.size(); i++)
					{
						if(countType==1)
						{
							if(i==showImgList.size()-1)
							{
								out.print("'"+showImgList.get(i).toString().split("#")[0].substring(8,10)+":"+showImgList.get(i).toString().split("#")[0].substring(10,12)+"'");
							}
							else
							{
								out.print("'"+showImgList.get(i).toString().split("#")[0].substring(8,10)+":"+showImgList.get(i).toString().split("#")[0].substring(10,12)+"',");
							}
						}
						else
						{
							if(i==showImgList.size()-1)
							{
								out.print("'"+showImgList.get(i).toString().split("#")[0]+"'");
							}
							else
							{
								out.print("'"+showImgList.get(i).toString().split("#")[0]+"',");
							}
						}
					}
%>
					],
					plotBands: [{ // visualize the weekend
						from: 0,
						to: <%=showImgList.size()-1%>,
						color: 'rgba(68, 170, 213, .2)'
					}]
				},
				yAxis: {
					title: {
						text: '<%=imgText%>'
					}
				},
				tooltip: {
					formatter: function() {
			                return '<b>'+ this.series.name +'</b><br/>'+
							this.x +': '+ this.y +' %';
					}
				},
				credits: {
					enabled: false
				},
				plotOptions: {
					areaspline: {
						fillOpacity: .5
					}
				},
				series: [{
					name: 'CPU占用率',
					data: [
<%
					for(int i=0; i<showImgList.size(); i++)
					{
						if(i==showImgList.size()-1)
						{
							out.print(showImgList.get(i).toString().split("#")[1]);
						}
						else
						{
							out.print(showImgList.get(i).toString().split("#")[1]+",");
						}
					}
%>
					]
				}, {
					name: '内存占用率',
					data: [
<%
					for(int i=0; i<showImgList.size(); i++)
					{
						if(i==showImgList.size()-1)
						{
							out.print(showImgList.get(i).toString().split("#")[2]);
						}
						else
						{
							out.print(showImgList.get(i).toString().split("#")[2]+",");
						}
					}
%>
					]
				}, {
					name: '硬盘占用率',
					data: [
<%
					for(int i=0; i<showImgList.size(); i++)
					{
						if(i==showImgList.size()-1)
						{
							out.print(showImgList.get(i).toString().split("#")[3]);
						}
						else
						{
							out.print(showImgList.get(i).toString().split("#")[3]+",");
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
	else
	{
%>
您输入的查询条件无结果~
<%
	}
}
else
{
%>
您输入的查询条件无结果~
<%
}
%>
