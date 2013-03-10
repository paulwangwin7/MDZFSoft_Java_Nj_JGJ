<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*, com.manager.pub.bean.*" %>
<%
	com.manager.pub.bean.Page pageRst = (com.manager.pub.bean.Page)request.getAttribute(Constants.PAGE_INFORMATION);
	List<NoticeForm> noticeFormList = null;
	if(pageRst!=null)
	{
		noticeFormList = (List<NoticeForm>)pageRst.getListObject();
	}
%>
<div class="accordion">
<table width="100%"><tr><td>
	<%
	if(noticeFormList!=null)
	{
		for(int i=0; i<noticeFormList.size(); i++)
		{
			NoticeForm noticeForm = noticeFormList.get(i);
			String dayStr = noticeForm.getCreateTime();
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
	</td></tr></table>
	<div class="tablefooter clearfix">
	<br/>&nbsp;<br/>
		<jsp:include page="common/page.jsp?function=showNotice"></jsp:include>
	</div>
</div>
<script>
function showNoticeDetail(detailIndex)
{
jQuery(function($) {
	$('.noticeDetailDiv').each(function(){
		$(this).css('display', 'none');
	});
	showObj('detail_'+detailIndex);
});
}
</script>