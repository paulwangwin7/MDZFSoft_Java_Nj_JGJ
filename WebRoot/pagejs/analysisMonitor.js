/**
 * 按周统计 选择结束日期事件触发
 */
function changeWeekDay(dateStr)
{
jQuery(function($) {
	$.ajax({
		url:contextPath()+'/servletAction.do?method=getWeekFirstDay',
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		data: {"chooseDay":$("#req_statDay_endTime").val()},
		success:function(res){
			if(res != null)
			{
				if(res.retCode!=0)
				{
					//showMsg("treeAddMsg", res.msg, 1);
				}
				else
				{
					//location.reload();
					$("#req_statDay_beginTime").val(formatJsChar8(res.msg));
					$("#req_statDay_beginTimeShow").val(formatJsChar8(res.msg));
				}
			}
			else{
				//showMsg("treeAddMsg", "请求失败，返回结果null", 1);
			}
		},
		error:function(){
			//showMsg("treeAddMsg", "请求失败 error function", 1);
		}
	});
});
}

/**
 * 根据时间粒度 切换需要选择的时间区域
 */
function showChooseTime()
{
jQuery(function($) {
	for(i=1; i<=4; i++)
	{
		if(i==$("#req_queryType").val())
		{
			$("#type_"+i).css("display", "block");
		}
		else
		{
			$("#type_"+i).css("display", "none");
		}
	}
});
}