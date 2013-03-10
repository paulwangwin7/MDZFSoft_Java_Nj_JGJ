var thisUrl = document.URL;

function chooseAll(thisObj)
{
	jQuery(function($) {
		$('.fileDelId').each(function(){
			$(this).attr("checked", thisObj.checked);
		});
	});
}

function fileDel()
{
	jQuery(function($) {
		if(getArray('fileDelId').length>0) {
			if(confirm("您确认删除这些文件？"))
			{
				//var fileDelForm = $('#fileDelForm');
				var fileDelForm = document.getElementById('fileDelForm');
				fileDelForm.action = "userAction.do?method=fileDel";
				fileDelForm.submit();
			}
		}
		else {
			showMsgObj('fileDelMsg', '请选择需要删除的文件', 2);
		}
	});
}

function fileDel2()
{
	jQuery(function($) {
		if(getArray('fileDelId').length>0) {
			if(confirm("您确认删除这些文件...？"))
			{
				var fileDelForm = document.getElementById('fileDelForm');
				fileDelForm.action = "userAction.do?method=fileDel_2";
				fileDelForm.submit();
			}
		}
		else {
			showMsgObj('fileDelMsg', '请选择需要删除的文件', 2);
		}
	});
}