<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*, java.util.*" %>
<div class="accordion">
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
			dayStr = dayStr.substring(0,4) +"-"+ dayStr.substring(4,6) +"-"+ dayStr.substring(6,8) +"-"+ " "+ dayStr.substring(8,10) +":"+ dayStr.substring(10,12);
%>
	<h3>『<%=dayStr %>』<a href="javascript:showNoticeDetail('<%=i %>')"><%=noticeFormList.get(i).getNoticeTitle() %></a></h3>
	<div id="detail_<%=i %>" <%=i==0?"":"style=\"display:none\"" %> class="noticeDetailDiv">
		<table class="Js_grayBg common_table">
		<tr>
		<th width="100px">编&nbsp;辑&nbsp;人：</th>
		<td><%=noticeFormList.get(i).getEditer() %></td>
		</tr>
		<tr>
		<th>公告部门：<a href="javascript:noticeDetailShow('<%=noticeFormList.get(i).getNoticeId() %>')">test</a></th>
		<td>
		<%=noticeFormList.get(i).getTreeIdListStr() %>
		</td>
		</tr>
		<tr>
		<th>公告内容：</th>
		<td>
		<%=noticeFormList.get(i).getNoticeContent() %>
		</td>
		</tr>
		</table>
	</div>
<%
		}
	}
%>
</div>
<script>
function showNoticeDetail(detailIndex)
{
jQuery(function($) {
	$('.noticeDetailDiv').each(function(){
		$(this).css('display', 'none');
	});
	showObj('detail_'+detailIndex,0);
});
}
</script>