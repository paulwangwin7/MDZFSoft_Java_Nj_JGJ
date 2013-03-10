/**
 * 分页显示角色列表
 * @param pageCute 当前第几页
 */
function showRole(pageCute)
{
jQuery(function($) {
	$("#roleManager_pageCute").val(pageCute);
	$("#roleManagerForm").submit();
});
}


/**
 * 角色添加弹出
 * @param assignmentId 需要赋值角色id的id对象名
 * @param assignmentName 需要赋值角色名称的id对象名
 * @param dialogTitle 弹出层的标题
 */
function roleAdd(assignmentId, assignmentName, dialogTitle, onlyRoot)
{
jQuery(function($) {
	hideObj('roleAddMsg',0);
	hideObj("roleManagerMsg",0);
	$('#saveOrUpdate').val('1');//1-添加；2-修改
	$('#roleSubmit').val('确认新增');
	$('#roleSubmitArea').css('display', 'block');
	$.weeboxs.open('#roleSaveOrUpdateDiv', {title:dialogTitle, contentType:'selector', width:'600', height:'430'});
});
}


/**
 * 角色添加弹出
 * @param assignmentId 需要赋值角色id的id对象名
 * @param assignmentName 需要赋值角色名称的id对象名
 * @param dialogTitle 弹出层的标题
 */
function roleMdf(ifMdf, assignmentId, assignmentName, dialogTitle, onlyRoot)
{
jQuery(function($) {
	hideObj('roleAddMsg',0);
	hideObj("roleManagerMsg",0);
	$('#saveOrUpdate').val('2');//1-添加；2-修改
	$('#roleSubmit').val('确认修改');
	$('#roleSubmitArea').css('display', 'block');
	if(!ifMdf)
	{
		$('#roleSubmitArea').css('display', 'none');
	}
	$.ajax({
		url:contextPath()+"/servletAction.do?method=roleQuery&action=mdf&forwardType="+Math.random(),
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {"roleId":assignmentId},
		success:function(res){
			if(res != null)
			{
				if(res.retCode!=0)
				{
					showMsgObj('roleManagerMsg', res.msg, 1);
				}
				else
				{
					$('#resp_roleId').val(res.retObj.roleId);
					$('#req_roleName').val(res.retObj.roleName);
					$('#req_roleDesc').val(res.retObj.roleDesc);
					$('.req_roleUrl').each(function(){
						for(i=0; i<res.retObj.urlIdList.split(',').length; i++)
						{
							if($(this).val()==res.retObj.urlIdList.split(',')[i])
							{
								$(this).attr('checked', true);
							}
						}
					});
					$.weeboxs.open('#roleSaveOrUpdateDiv', {title:dialogTitle, contentType:'selector', width:'600', height:'430'});
				}
			}
			else{
				showMsgObj('roleManagerMsg', '请求失败，返回结果null', 1);
			}
		},
		error:function(){
			showMsgObj('roleManagerMsg', '请求失败 error function', 1);
		}
	});
	
});
}


/**
 * 角色添加/修改
 */
function roleSaveOrUpdate()
{
var urlStr = contextPath()+'servletAction.do?method=';
jQuery(function($) {
	if(formSubmitVali())
	{
		if($("#saveOrUpdate").val()=='1')//1-添加；2-修改
		{
			urlStr += 'roleAdd';
		}
		else if($("#saveOrUpdate").val()=='2')
		{
			urlStr += 'roleMdf';
		}
		else
		{
			urlStr += 'roleAdd';
		}
		$.ajax({
			url:urlStr,
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"roleId":$("#resp_roleId").val(),"roleName":$("#req_roleName").val(),"roleDesc":$("#req_roleDesc").val(),"urlId":getJqueryArrayStr("req_roleUrl")},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						showMsgObj('roleAddMsg', res.msg, 1);
					}
					else
					{
						showMsgObj('roleAddMsg', res.msg, 0);
						setTimeout(function(){location.href=contextPath()+'userAction.do?method=roleManager';},3000);
					}
				}
				else{
					showMsgObj('roleAddMsg', '请求失败，返回结果null', 1);
				}
			},
			error:function(){
				showMsgObj('roleAddMsg', '请求失败 error function', 1);
			}
		});
	}
});
return false;
}


/**
 * 角色删除
 * @param roleIdVal 角色id
 */
function roleDel(roleIdVal)
{
jQuery(function($) {
	hideObj("roleManagerMsg", 0);
	if(confirm("您确认删除该角色吗？"))
	{
		$.ajax({
			url:contextPath()+'servletAction.do?method=roleDel',
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"roleId":roleIdVal},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						showMsgObj('roleManagerMsg', res.msg, 1);
					}
					else
					{
						showMsgObj('roleManagerMsg', res.msg, 0);
						setTimeout(function(){hideObj('tr_'+roleIdVal);},1000);
					}
				}
				else{
					showMsgObj('roleManagerMsg', '请求失败，返回结果null', 1);
				}
			},
			error:function(){
				showMsgObj('roleManagerMsg', '请求失败 error function', 1);
			}
		});
	}
});
return false;
}


/**
 * 修改或新增角色
 * @param thisRoleId 本级id 新增这个参数不传递
 * @param ifQueryDetail 是否只是查看
 */
function roleDetailShow(thisRoleId, ifQueryDetail)
{
jQuery(function($) {
	hideObj("roleManagerAction",0);
	if(thisRoleId!=null)
	{
		if(ifQueryDetail!=null)//查看
		{
			loadPage("roleManagerAction", contextPath()+"/userAction.do?method=roleManagerToMdf&action=query&roleId="+thisRoleId);
		}
		else//修改
		{
			loadPage("roleManagerAction", contextPath()+"/userAction.do?method=roleManagerToMdf&action=mdf&roleId="+thisRoleId);
		}
	}
	else//新增
	{
		loadPage("roleManagerAction", contextPath()+"/userAction.do?method=roleManagerToAdd&action=add");
	}
	showObj("roleManagerAction");
});
}


/**
 * form表单提交验证
 */
function formSubmitVali()
{
var vali = false;
jQuery(function($) {
	hideObj("roleAddMsg", 0);
	if(getLenB($("#req_roleName").val())<4 || getLenB($("#req_roleName").val())>10)
	{
		showMsgObj('roleAddMsg', '角色名称长度不符，请控制在【4,10】个字节。', 2, 'req_roleName');
	}
	else if(getLenB($("#req_roleDesc").val())<4 || getLenB($("#req_roleDesc").val())>100)
	{
		showMsgObj('roleAddMsg', '角色描述长度不符，请控制在【4,100】个字节。', 2, 'req_roleDesc');
	}
	else if(getArray("req_roleUrl").length<=0)
	{
		showMsgObj('roleAddMsg', '请选择该角色具备的相关操作。', 2);
	}
	else
	{
		vali = true;
	}
});
return vali;
}