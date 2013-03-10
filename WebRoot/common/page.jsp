<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*" %>
<table width="100%" border="0">
<tr>
<td style="border:0px solid #ff6600;">
<input type="text" style="width:18px;" id="inputPage"/>
<input type="button" class="button small" style="width:28px;" onclick="pageFunction()" value="Go">
</td>
<td style="border:0px solid #ff6600;">
<div class="pagination">
<%
int maxPager = 5;//最多5大页
com.manager.pub.bean.Page pageRst = null;
String function = request.getParameter("function");
try
{
	pageRst = new com.manager.pub.bean.Page();
	pageRst = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
	//out.print("&nbsp;&nbsp;"+pageRst.getTotal()+"条数据&nbsp;");
	out.println(pageRst.getPageCute()+"/"+pageRst.getPageMax());
%>
<script>
function pageFunction()
{
jQuery(function($) {
	if($("#inputPage").val()=="" || isNaN($("#inputPage").val()))
	{
		alert("输入的页码参数有误，请确认！");
	}
	else
	{
		<%=function%>($("#inputPage").val());
	}
});
}
</script>
<%
	int forStartPage = (pageRst.getPageLineCute()-1)*pageRst.getPageLine()+1;//循环起始页
	forStartPage = forStartPage<0?0:forStartPage;
	int forEndPage = forStartPage+pageRst.getPageLine()-1;//循环结束页
	int forOverPage = pageRst.getPageMax();//强停循环页

	if(pageRst.getPageCute()!=1)
	{
%>
	<a href="javascript:<%=function %>('1')">首页</a>
<%
	}
	else
	{
%>
	<a style="color:#d1d1d1">首页</a>
<%
	}
	if(pageRst.getPageLineCute()>1)
	{
%>
	<a href="javascript:<%=function %>('<%=forStartPage-1 %>')">上翻</a>
<%
	}
	else
	{
%>
	<a style="color:#d1d1d1">上翻</a>
<%
	}
%>
	<span>
<%
	for(int i=forStartPage; i<=forEndPage && i<=forOverPage; i++)
	{
		out.print("<a href=\"javascript:"+function+"('"+i+"')\"");
		if(i==pageRst.getPageCute())
		{
			out.print(" class=\"current\"");
		}
		out.println(">"+i+"</a>");
	}
%>
	</span>
<%
	if(pageRst.getPageLineCute()<pageRst.getPageLineMax())
	{
%>
	<a href="javascript:<%=function %>('<%=forEndPage+1 %>')">下翻</a>
<%
	}
	else
	{	
%>
	<a style="color:#d1d1d1">下翻</a>
<%
	}
	if(pageRst.getPageCute()<pageRst.getPageMax())
	{
%>
	<a href="javascript:<%=function %>('<%=pageRst.getPageMax() %>')">尾页</a>
<%
	}
	else
	{
%>
	<a style="color:#d1d1d1">尾页</a>
<%
	}
%>
	&nbsp;&nbsp;&nbsp;&nbsp;
<script>
jQuery(function($) {
	$("#inputPage").val("<%=request.getParameter("pageCute")==null?1:request.getParameter("pageCute")%>");
});
</script>
<%
}
catch(Exception ex)
{
	//out.println("获取分页信息失败："+ex);
}
%>
</div>
</td>
</tr>
</table>