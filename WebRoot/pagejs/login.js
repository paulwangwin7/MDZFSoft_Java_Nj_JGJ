/**
 * 用户登录
 */
function userLogin()
{
	if(getTrimLen($("#username").val())<2 || getTrimLen($("#username").val())>20)
	{
		//showMsgObj('loginMsg', '登录帐号长度在[2,20]字节以内！', 2, 'username');
		alert('登录帐号长度在[2,20]字节以内！');
		$('#username').focus();
	}
	else if(getTrimLen($("#userpswd").val())<6 || getTrimLen($("#userpswd").val())>20)
	{
		//showMsgObj('loginMsg', '登录密码长度在[6,20]字节以内！', 2, 'userpswd');
		alert('登录密码长度在[6,20]字节以内！');
		$('#userpswd').focus();
	}
	else if(!isCheckCode($("#checkcode").val()))
	{
		//showMsgObj('loginMsg', '验证码填写不正确！', 2, 'checkcode');
		alert('验证码填写不正确内！');
		$('#checkcode').focus();
	}
	else
	{
		//showMsgObj('loginMsg', '正在登录，请稍后...', 3);
		$.ajax({
			url:contextPath()+'servletAction.do?method=userLogin',
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"loginName":$("#username").val(),"loginPswd":$("#userpswd").val(),"checkCode":$("#checkcode").val()},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						//showMsgObj('loginMsg', res.msg, 1);
						alert(res.msg);
					}
					else
					{
						//showMsgObj('loginMsg', res.msg, 0);
						alert(res.msg);
						setTimeout(function(){location.href=contextPath()+"/userAction.do?method=userMain";},1000);
					}
				}
				else{
					//showMsgObj('loginMsg', "请求失败，返回结果null", 1);
					alert('请求失败，返回结果null');
				}
			},
			error:function(){
				//showMsgObj('loginMsg', "请求失败 error function", 1);
				alert('请求失败 error function');
			}
		});
	}
	return false;
}

/**
 * 系统管理员登录
 */
function sysLogin()
{
	if(getTrimLen($("#username").val())<3 || getTrimLen($("#username").val())>20)
	{
		//showMsgObj('loginMsg', '登录帐号长度在[3,20]字节以内！', 2, 'username');
		alert('登录帐号长度在[3,20]字节以内！');
		$('#username').focus();
	}
	else if(getTrimLen($("#userpswd").val())<6 || getTrimLen($("#userpswd").val())>20)
	{
		//showMsgObj('loginMsg', '登录密码长度在[6,20]字节以内！', 2, 'userpswd');
		alert('登录密码长度在[6,20]字节以内！');
		$('#userpswd').focus();
	}
	else if(!isCheckCode($("#checkcode").val()))
	{
		//showMsgObj('loginMsg', '验证码填写不正确！', 2, 'checkcode');
		alert('验证码填写不正确内！');
		$('#checkcode').focus();
	}
	else
	{
		$.ajax({
			url:contextPath()+'servletAction.do?method=sysLogin',
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"loginName":$("#username").val(),"loginPswd":$("#userpswd").val(),"checkCode":$("#checkcode").val()},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						alert(res.msg);
					}
					else
					{
						alert(res.msg);
						setTimeout(function(){location.href=contextPath()+"/userAction.do?method=userMain";},1000);
					}
				}
				else{
					alert("请求失败，返回结果null");
				}
			},
			error:function(){
				alert("请求失败 error function");
			}
		});
	}
	return false;
}

/**
 * 验证码 换一张
 */
function changeCheckImg()
{
	$("#checkImg").attr("src", contextPath()+"checkCode.jsp?"+Math.random());
}