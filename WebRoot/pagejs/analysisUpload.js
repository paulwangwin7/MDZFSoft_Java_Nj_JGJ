/**
 * 获取信息div
 * @param msg 提示信息
 * @param type 提示类型 0-success 1-error 2-warning 3-information
 * @param focusId 焦点停留id名称
 */
function showMsg(msg, type, focusId)
{
jQuery(function($) {
	$("#analysisUploadMsg").html(msg);
	var msgType = "error";
	switch(type)
	{
		case 0 : msgType = "success"; break;
		case 1 : msgType = "error"; break;
		case 2 : msgType = "warning"; break;
		case 3 : msgType = "information"; break;
		default: msgType = "error";
	}
	$("#analysisUploadMsg").attr("class", msgType+" msg");
	$("#analysisUploadMsg").css("opacity" ,4);
	showObj("analysisUploadMsg");
	if(focusId != null)
	{
		$("#"+focusId).focus();
	}
});
}