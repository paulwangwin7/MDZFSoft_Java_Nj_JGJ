<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script>
var jsnoticeId = "";

jQuery(function($) {
	$("#showMessager300x200").click(function(){
		$.messager.lays(300, 200);
		$.messager.show(0, '<a href="http://www.baidu.com" target="_blank">百度</a>', 0);
	});
});

function popNotice() {
	$.ajax({
		url:contextPath()+"/servletAction.do?method=popNoticeTitle&forwardType="+Math.random(),
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: '',
		success:function(res){
			if(res != null)
			{
				if(res.retCode=='0')
				{
					if(jsnoticeId!=res.retObj.noticeId)
					{
						$.messager.lays(300, 200);
						$.messager.show(0, '<a href="javascript:noticeDetailShow(\''+res.retObj.noticeId+'\')">'+res.retObj.noticeTitle+'</a>', 0);
						jsnoticeId=res.retObj.noticeId;
					}
				} else {
					//alert(res.msg);
				}
			}
		},
		error:function(){
			//alert('请求失败 error function');
		}
	});
}

setInterval("popNotice()", 60000);
</script>
<div id="noticeDetailShow" icon="icon-save" style="display:none" class="boxcontent">
<iframe id="noticeDetailFrame" src="" width="570px" height="420px" frameborder="0" scrolling="no" /></iframe>
</div>
<div class="footer">
<span>copyright &copy;2003-2012 njmdaf.com.All Rights Reserved</span><span>南京名都安防器械有限公司 版权所有</span>
</div>