/**
 * 用户注册/修改
 */
function saveOrUpdate()
{
var saveOrUpdateAction = "userAdd";
jQuery(function($) {
	hideObj("userAddMsg", 0);
	if(getLenB($("#req_loginName").val())<4 || getLenB($("#req_loginName").val())>20)
	{
		showMsg("userAddMsg", "登录帐号长度不符，请控制在【4,20】个字节。", 2, "req_loginName");
	}
	else if(getLenB($("#req_userName").val())<4 || getLenB($("#req_userName").val())>20)
	{
		showMsg("userAddMsg", "姓名长度不符，请控制在【4,20】个字节。", 2, "req_userName");
	}
	else if(getLenB($("#req_userCode").val())<4 || getLenB($("#req_userCode").val())>20)
	{
		showMsg("userAddMsg", "警员编号长度不符，请控制在【4,20】个字节。", 2, "req_userCode");
	}
	else if(getArray("req_sex").length<=0)
	{
		showMsg("userAddMsg", "请选择性别。", 2);
	}
	else if(getLenB($("#req_treeName").val())<=0)
	{
		showMsg("userAddMsg", "请选择所属部门。", 2);
	}
	else if(getLenB($("#req_roleName").val())<=0)
	{
		showMsg("userAddMsg", "请选择所属角色。", 2);
	}
	else if(getArray("req_userState").length<=0)
	{
		showMsg("userAddMsg", "请选择用户状态。", 2);
	}
	else
	{
		$.ajax({
			url:contextPath()+'servletAction.do?method='+saveOrUpdateAction,
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"loginName":$("#req_loginName").val(),"loginPswd":$("#req_loginPswd").val(),"userName":$("#req_userName").val(),"userCode":$("#req_userCode").val(),"sex":getJqueryArrayStr("req_sex"),"userIdCard":"","treeId":$("#req_treeId").val(),"roleId":$("#req_roleId").val(),"userState":getJqueryArrayStr("req_userState")},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						showMsg("userAddMsg", res.msg, 1);
					}
					else
					{
						showMsg("userAddMsg", res.msg, 0);
						setTimeout(function(){location.href=contextPath()+"/userAction.do?method=userManager";},1000);
					}
				}
				else{
					showMsg("userAddMsg", "请求失败，返回结果null", 1);
				}
			},
			error:function(){
				showMsg("userAddMsg", "请求失败 error function", 1);
			}
		});
	}
});
}