/**
 * 部门添加弹出
 * @param assignmentId 需要赋值部门id的id对象名
 * @param assignmentName 需要赋值部门名称的id对象名
 * @param dialogTitle 弹出层的标题
 */
function treeAdd(assignmentId, assignmentName, dialogTitle, onlyRoot)
{
jQuery(function($) {
	hideObj('treeManagerAction',0);
	hideObj("treeManagerMsg",0);
	var parentTreeId = "";//上级部门id
	$(".tree0Radio").each(function(){
		if($(this).attr("checked"))
		{
			parentTreeId = $(this).val();
		}
	});
	if(parentTreeId=="") {//没有在根部门位置选择
		$(".tree1Radio").each(function(){
			if($(this).attr("checked"))
			{
				parentTreeId = $(this).val();
			}
		});
		if(parentTreeId=="")//没有在一级部门位置选择
		{
			$(".tree2Radio").each(function(){
				if($(this).attr("checked"))
				{
					parentTreeId = $(this).val();
				}
			});
			if(parentTreeId=="")//选择错误 还未选择
			{
				showMsgObj('treeManagerMsg', '选择错误 您还未选择需要在哪个部门下添加！', 2);
			}
			else//选择错误 不能选择在二级部门下新增部门
			{
				showMsgObj('treeManagerMsg', '选择错误 不能选择在二级部门下新增部门！', 2);
			}
		}
		else//成功选择 可以开始添加新的二级部门
		{
			//treeSaveOrUpdate(parentTreeId);
			$('#actionType').val('1');
			$('#parentId').val(parentTreeId);
			$('#treeSubmit').val('确认新增');
			$.weeboxs.open('#treeSaveOrUpdateDiv', {title:dialogTitle, contentType:'selector', width:'400', height:'240'});
		}
	}
	else//成功选择 可以开始添加新的一级部门
	{
		//treeSaveOrUpdate(parentTreeId);
		$('#actionType').val('1');
		$('#parentId').val(parentTreeId);
		$('#treeSubmit').val('确认新增');
		$.weeboxs.open('#treeSaveOrUpdateDiv', {title:dialogTitle, contentType:'selector', width:'400', height:'240'});
	}
});
}


/**
 * 部门修改弹出
 * @param assignmentId 需要赋值部门id的id对象名
 * @param assignmentName 需要赋值部门名称的id对象名
 * @param dialogTitle 弹出层的标题
 */
function treeMdf(assignmentId, assignmentName, dialogTitle, onlyRoot)
{
jQuery(function($) {
	hideObj('treeManagerAction',0);
	hideObj("treeManagerMsg",0);
	var parentTreeId = "";//选择部门id
	$(".tree1Radio").each(function(){
		if($(this).attr("checked"))
		{
			parentTreeId = $(this).val();
		}
	});
	if(parentTreeId=="")//没有在一级部门位置选择
	{
		$(".tree2Radio").each(function(){
			if($(this).attr("checked"))
			{
				parentTreeId = $(this).val();
			}
		});
		if(parentTreeId=="")//选择错误 还未选择
		{
			showMsgObj('treeManagerMsg', '选择错误 您还未选择需要修改的部门！', 2);
		}
	}

	if(parentTreeId!="")
	{
	$.ajax({
		url:contextPath()+"/servletAction.do?method=treeQuery&action=mdf&forwardType="+Math.random(),
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {"treeId":parentTreeId},
		success:function(res){
			if(res != null)
			{
				if(res.retCode!=0)
				{
					showMsgObj('treeManagerMsg', res.msg, 1);
				}
				else
				{
					$('#actionType').val('2');
					$('#treeId').val(res.retObj.treeId);
					$('#parentId').val(res.retObj.parentTreeId);
					$('#treeName').val(res.retObj.treeName);
					$('#desc').val(res.retObj.treeDesc);
					$('#treeSubmit').val('确认修改');
					$.weeboxs.open('#treeSaveOrUpdateDiv', {title:dialogTitle, contentType:'selector', width:'400', height:'240'});
				}
			}
			else{
				showMsgObj('treeManagerMsg', '请求失败，返回结果null', 1);
			}
		},
		error:function(){
			showMsgObj('treeManagerMsg', '请求失败 error function', 1);
		}
	});
	}
});
}


/**
 * 提交添加、修改部门请求到后台
 */
function saveOrUpdate()
{
jQuery(function($) {
if($("#treeName").val().length==0)
{
	showMsgObj('treeAddMsg', '请填写部门名称', 2, 'treeName');
}
else if($("#desc").val().length==0)
{
	showMsgObj('treeAddMsg', '请填写部门描述', 2, 'desc');
}
else
{
	hideObj('treeAddMsg');
	var urlStr = contextPath()+'servletAction.do?method=';
	if($("#actionType").val()=='1') //actionType 1-新增；2-修改
	{
		urlStr += 'treeAdd';
	}
	else if($("#actionType").val()=='2')
	{
		urlStr += 'treeMdf';
	}
	else
	{
		
	}
	$.ajax({
		url:urlStr,
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {"treeId":$("#treeId").val(),"parentId":$("#parentId").val(),"treeName":$("#treeName").val(),"treeDesc":$("#desc").val()},
		success:function(res){
			if(res != null)
			{
				if(res.retCode!=0)
				{
					showMsgObj('treeAddMsg', res.msg, 1);
				}
				else
				{
					showMsgObj('treeAddMsg', res.msg, 0);
					setTimeout(function(){location.reload();},3000);
				}
			}
			else{
				showMsgObj('treeAddMsg', '请求失败，返回结果null', 1);
			}
		},
		error:function(){
			showMsgObj('treeAddMsg', '请求失败 error function', 1);
		}
	});
}
});
return false;
}


/**
 * 部门删除
 */
function del()
{
jQuery(function($) {
	var treeId = "";
	$(".tree1Radio").each(function(){
		if($(this).attr("checked"))
		{
			treeId = $(this).val();
		}
	});
	$(".tree2Radio").each(function(){
		if($(this).attr("checked"))
		{
			treeId = $(this).val();
		}
	});
	if(treeId!="")
	{
		if(confirm("您确认删除该部门吗？"))
		{
		$.ajax({
			url:contextPath()+"/servletAction.do?method=treeDel",
			type: 'post',
			dataType: 'json',
			cache: false,
			async: false,
			data: {"treeId":treeId},
			success:function(res){
				if(res != null)
				{
					if(res.retCode!=0)
					{
						showMsgObj('treeManagerMsg', res.msg, 1);
					}
					else
					{
						showMsgObj('treeManagerMsg', res.msg, 0);
						setTimeout(function(){location.reload();},3000);
					}
				}
				else{
					showMsgObj('treeManagerMsg', '请求失败，返回结果null', 1);
				}
			},
			error:function(){
				showMsgObj('treeManagerMsg', '请求失败 error function', 1);
			}
		});
		}
	}
	else
	{
		showMsgObj('treeManagerMsg', '请选择您需要删除的部门', 2);
	}
});
}