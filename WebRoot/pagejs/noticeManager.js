/**
 * 公告添加弹出
 * @param assignmentId 需要赋值公告id的id对象名
 * @param assignmentName 需要赋值公告名称的id对象名
 * @param dialogTitle 弹出层的标题
 */
function noticeAdd(assignmentId, assignmentName, dialogTitle, onlyRoot)
{
jQuery(function($) {
	hideObj('noticeManagerMsg',0);
	hideObj("noticeAddMsg",0);
	$('#saveOrUpdate').val('1');//1-添加；2-修改
	$.weeboxs.open('#noticeSaveOrUpdateDiv', {title:dialogTitle, contentType:'selector', width:'600', height:'440'});
});
}


/**
 * 公告添加/修改
 */
function noticeSave()
{
var urlStr = contextPath()+'servletAction.do?method=noticeManagerAdd';
jQuery(function($) {
	hideObj("noticeAddMsg", 0);
	if(getLenB($("#req_noticeTitle").val())<4 || getLenB($("#req_noticeTitle").val())>100)
	{
		showMsgObj('noticeAddMsg', '公告内容长度不符，请控制在【4,100】个字节。', 2, 'req_noticeTitle');
	}
	else if(getLenB($("#req_noticeContent").val())<4 || getLenB($("#req_noticeContent").val())>1000)
	{
		showMsgObj('noticeAddMsg', '公告内容长度不符，请控制在【4,1000】个字节。', 2, 'req_noticeContent');
	}
	else if(getJqueryArrayStr('treeCheck')=='')
	{
		showMsgObj('noticeAddMsg', '请选择该公告发布的部门。', 2);
	}
	else
	{
		$.ajax({
			url:urlStr,
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"treeIdList":getJqueryArrayStr('treeCheck'),"noticeTitle":$("#req_noticeTitle").val(),"noticeContent":$("#req_noticeContent").val()},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						showMsgObj('noticeAddMsg', res.msg, 1);
					}
					else
					{
						showMsgObj('noticeAddMsg', res.msg, 0);
						setTimeout(function(){location.href = contextPath()+'userAction.do?method=userNoticeManager';},3000);
					}
				}
				else{
					showMsgObj('noticeAddMsg', '请求失败，返回结果null', 1);
				}
			},
			error:function(){
				//showMsgObj('noticeAddMsg', '请求失败 error function', 1);
				showMsgObj('noticeAddMsg', '标题或内容存在不合法字符', 1);
			}
		});
	}
});
return false;
}


/**
 * 公告删除
 * @param noticeId 公告id
 */
function noticeDel(noticeId)
{
var urlStr = contextPath()+'servletAction.do?method=noticeManagerDel';
jQuery(function($) {
	if(confirm("您确认删除该公告吗？"))
	{
	hideObj('noticeManagerMsg',0);
	$.ajax({
		url:urlStr,
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {"noticeManager_noticeId":noticeId},
		success:function(res){
			if(res != null)
			{
				if(res.retCode!=0)
				{
					showMsgObj('noticeManagerMsg', res.msg, 1);
				}
				else
				{
					showMsgObj('noticeManagerMsg', res.msg, 0);
					setTimeout(function(){$('#tr_'+noticeId).css('display', 'none');},3000);
				}
			}
			else{
				showMsgObj('noticeManagerMsg', '请求失败，返回结果null', 1);
			}
		},
		error:function(){
			showMsgObj('noticeManagerMsg', '请求失败 error function', 1);
		}
	});
	}
});
}











var thisUrl = document.URL;
/**
 * 修改或新增公告
 * @param thisNoticeId 本级id 新增这个参数不传递
 * @param ifQueryDetail 是否只是查看
 */
function noticeSaveOrUpdate(thisNoticeId, ifQueryDetail)
{
jQuery(function($) {
	if(thisNoticeId!=null)
	{
		if(ifQueryDetail!=null)//查看
		{
			loadPage("noticeManagerAction", contextPath()+"/userAction.do?method=noticeManagerToAdd&action=query");
		}
		else//修改
		{
			loadPage("noticeManagerAction", contextPath()+"/userAction.do?method=noticeManagerToAdd&action=mdf");
		}
	}
	else//新增
	{
		loadPage("noticeManagerAction", contextPath()+"/userAction.do?method=noticeManagerToAdd&action=add");
	}
	showObj("noticeManagerAction");
});
}






/**
 * 显示二级部门
 * @param parentTreeId 一级部门id
 */
function showChildTree(parentTreeId)
{
jQuery(function($) {
	if($("#"+parentTreeId).css("display")=="none")
	{
		showObj(parentTreeId, '');
	}
	else
	{
		hideObj(parentTreeId, '');
	}
});
}

var nowShowAll = true;
/**
 * 显示所有部门
 */
function showAllTree()
{
jQuery(function($) {
	$("#treeManagerTable table").each(function() {
		if(!nowShowAll)
		{
			$(this).css("display","block");
		}
		else
		{
			$(this).css("display","none");
		}
	});
});
if(!nowShowAll)
{
	nowShowAll = true;
}
else
{
	nowShowAll = false;
}
}
