<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*, java.util.*" %>
<table width="100%">
<%
	List<NoticeForm> noticeFormList = null;
	if(request.getAttribute(Constants.SESSION_NOTICE_LIST)!=null)
	{
		noticeFormList = (List<NoticeForm>)request.getAttribute(Constants.SESSION_NOTICE_LIST);
	}
	if(noticeFormList!=null)
	{
		for(int i=0; i<noticeFormList.size(); i++)
		{
			String dayStr = noticeFormList.get(i).getCreateTime();
			dayStr = dayStr.substring(0,4) +"-"+ dayStr.substring(4,6) +"-"+ dayStr.substring(6,8) +" "+ dayStr.substring(8,10) +":"+ dayStr.substring(10,12);
%>
<tr>
<td>
<div class="label_1">『<%=dayStr %>』&nbsp;&nbsp;<b><a href="javascript:noticeDetailShow('<%=noticeFormList.get(i).getNoticeId() %>')"><%=noticeFormList.get(i).getNoticeTitle() %></a></b></div>
<hr/>
</td>
</tr>
<%
		}
	}
%>
</table>
