<%@ page language="java" import="java.util.*,com.manager.pub.bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

NoticeForm noticeForm = null;
if(request.getAttribute("noticeForm")!=null) {
	noticeForm = (NoticeForm)request.getAttribute("noticeForm");
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公告详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<div style="height:400px; overflow-y:auto;">  
    <table style="table-layout: fixed" width="540px" height="380px" bgcolor="#f7f7f7">
<%
if(noticeForm!=null) {
%>
    <tr height="20px">
    <td align="center" colspan="2">
    <b><%=noticeForm.getNoticeTitle() %></b>
    </td>
    </tr>
    <tr height="20px">
    <td>编辑人：<%=noticeForm.getEditer() %></td>
    <td align="right">
    <%=noticeForm.getCreateTime().substring(0,4) %>年<%=noticeForm.getCreateTime().substring(4,6) %>月<%=noticeForm.getCreateTime().substring(6,8) %>日&nbsp;<%=noticeForm.getCreateTime().substring(8,10) %>点<%=noticeForm.getCreateTime().substring(10,12) %>分
    </td>
    </tr>
    <tr>
    <td style="word-wrap: break-word;" colspan="2" valign="top">
    <hr/>
    <div style="height:260px;overflow-y:auto">
    <%=noticeForm.getNoticeContent() %>
    </div>
    </td>
    </tr>
    <tr height="60px">
    <td colspan="2">
    <hr/>
    公告部门：<%=noticeForm.getTreeIdListStr() %>
    </td>
    </tr>
<%
}
%>
    </table>
</div>
  </body>
</html>
