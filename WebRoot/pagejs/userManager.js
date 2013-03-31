/**
 * 用户注册/修改
 */
function saveOrUpdate()
{
var saveOrUpdateAction = "userAdd";
if($("#actionType").val()=="mdf")
{
	saveOrUpdateAction = "userMdf";
}

jQuery(function($) {
	hideObj("userAddMsg", 0);
	if(getLenB($("#req_loginName").val())<4 || getLenB($("#req_loginName").val())>20)
	{
		showMsgObj('userAddMsg', '登录帐号长度不符，请控制在【4,20】个字节。', 2, 'req_loginName');
	}
	else if(getLenB($("#req_userName").val())<4 || getLenB($("#req_userName").val())>20)
	{
		showMsgObj('userAddMsg', '姓名长度不符，请控制在【4,20】个字节。', 2, 'req_userName');
	}
	else if(getLenB($("#req_userCode").val())<4 || getLenB($("#req_userCode").val())>20)
	{
		showMsgObj('userAddMsg', '警员编号长度不符，请控制在【4,20】个字节。', 2, 'req_userCode');
	}
	else if(getArray("req_sex").length<=0)
	{
		showMsgObj('userAddMsg', '请选择性别。', 2);
	}
	else if($("#req_treeName").val()=='' && $('#roleType').val()=='')
	{
		showMsgObj('userAddMsg', '请选择所属部门。', 2);
	}
	else if($("#req_roleName").val()=='' && $('#roleType').val()=='')
	{
		showMsgObj('userAddMsg', '请选择所属角色。', 2);
	}
	else if(getArray("req_userState").length<=0)
	{
		showMsgObj('userAddMsg', '请选择用户状态。', 2);
	}
	else
	{
		$.ajax({
			url:contextPath()+'servletAction.do?method='+saveOrUpdateAction,
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"userId":$("#req_userId").val(),"loginName":$("#req_loginName").val(),"loginPswd":$("#req_loginPswd").val(),"userName":$("#req_userName").val(),"userCode":$("#req_userCode").val(),"sex":getJqueryArrayStr("req_sex"),"userIdCard":"","treeId":$("#req_treeName").val(),"roleId":$("#req_roleName").val(),"userState":getJqueryArrayStr("req_userState"),"roleType":$("#roleType").val()},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						showMsgObj('userAddMsg', res.msg, 1);
					}
					else
					{
						showMsgObj('userAddMsg', res.msg, 0);
						setTimeout(function(){location.href=contextPath()+"/userAction.do?method=userManager";},1000);
					}
				}
				else{
					showMsgObj('userAddMsg', '请求失败，返回结果null', 1);
				}
			},
			error:function(){
				showMsgObj('userAddMsg', '请求失败 error function', 1);
			}
		});
	}
});
return false;
}


/**
 * 用户密码重置
 * @param userIdVal 用户id
 */
function resetPassword(userIdVal)
{
jQuery(function($) {
	hideObj("userManagerMsg", 0);
	if(confirm("您确认重置该用户的密码吗？"))
	{
		$.ajax({
			url:contextPath()+'servletAction.do?method=resetPassword',
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"userId":userIdVal},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						showMsgObj('userManagerMsg', res.msg, 1);
					}
					else
					{
						showMsgObj('userManagerMsg', res.msg, 0);
					}
				}
				else{
					showMsgObj('userManagerMsg', '请求失败，返回结果null', 0);
				}
			},
			error:function(){
				showMsgObj('userManagerMsg', '请求失败 error function', 0);
			}
		});
	}
});
}


/**
 * 用户修改弹出
 * @param assignmentId 需要赋值用户id的id对象名
 * @param assignmentName 需要赋值用户名称的id对象名
 * @param dialogTitle 弹出层的标题
 */
function userMdf(assignmentId, assignmentName, dialogTitle, onlyRoot)
{
jQuery(function($) {
	hideObj('userMdfMsg',0);
	hideObj("userManagerMsg",0);
	$('#actionArea').css('display', 'none');
	if(assignmentName.length>0)
	{
		$('#actionArea').css('display', 'block');
	}
	$.ajax({
		url:contextPath()+"/servletAction.do?method=userToAddOrMdf&forwardType="+Math.random(),
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {"userId":assignmentId},
		success:function(res){
			if(res != null)
			{
				if(res.retCode!=0)
				{
					showMsgObj('userManagerMsg', res.msg, 1);
				}
				else
				{
					$('#req_userId').val(res.retObj.userId);
					$('#req_loginName').val(res.retObj.loginName);
					$('#req_userName').val(res.retObj.userName);
					$('#req_userCode').val(res.retObj.userCode);
					toSelected('req_treeName', res.retObj.treeId);
					toSelected('req_roleName', res.retObj.roleId);
					$('#roleType').val(res.retObj.roleType);
					if(res.retObj.roleType=='0') {
						$('#treeLi').css('display', 'none');
						$('#roleLi').css('display', 'none');
						$('#roleTypeVal').html('交管局领导');
					} else {
						$('#treeLi').css('display', 'block');
						$('#roleLi').css('display', 'block');
						$('#roleTypeVal').html('普通帐号');
					}
					
					$.weeboxs.open('#userSaveOrUpdateDiv', {title:dialogTitle, contentType:'selector', width:'600', height:'430'});
					toChecked('req_sex', res.retObj.sex);
					toChecked('req_userState', res.retObj.userState);
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