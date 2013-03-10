<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*" %>
<%
UploadForm uploadForm = null;
String fileImgPath = "";
String playVodPath = "";
if(request.getAttribute("fileDetail")!=null)
{
	uploadForm = (UploadForm)request.getAttribute("fileDetail");
	fileImgPath = uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getShowPath();
	System.out.println(fileImgPath);
	playVodPath = uploadForm.getFileSavePath()+"/upload/files/"+uploadForm.getPlayPath();
	if(uploadForm.getPlayPath()!=null && uploadForm.getPlayPath().length()>0 && uploadForm.getPlayPath().substring(uploadForm.getPlayPath().length()-4).equals(".avi"))
	{
%>
<object id="video" name="video" width="640" height="480" border="0" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA">
<param name="ShowDisplay" value="0">
<param name="ShowControls" value="1">
<param name="AutoStart" value="1">
<param name="AutoRewind" value="0">
<param name="PlayCount" value="-1">
<param name="Appearance" value="0" value="">
<param name="BorderStyle" value="0" value="">
<param name="MovieWindowHeight" value="640">
<param name="MovieWindowWidth" value="480">
<param name="FileName" value="<%=playVodPath%>">
<embed id="videoObj" width="640" height="480" border="0" showdisplay="0" showcontrols="1" autostart="1" autorewind="0" playcount="-1" moviewindowheight="640" moviewindowwidth="480" filename="<%=fileImgPath%>" src="<%=playVodPath%>"> 
</embed>
</object>
<%
	}
	else
	{
%>
<img id="imgObj" src="<%=fileImgPath %>" style="display:none" />
<script>
function imageShow() {
jQuery(function($) {
	var img = $('#imgObj');
	var theImage = new Image();
	theImage.src = img.attr("src");
	//alert("Width: " + theImage.width);
	//alert("Height: " + theImage.height);
	//alert(theImage.width+":"+theImage.height);
	narrowImg(theImage.width, theImage.height);
});
}
function narrowImg(imgW, imgH)
{
jQuery(function($) {
	if(imgW>800)
	{
		imgW = Math.round(imgW/1.5);
		imgH = Math.round(imgH/1.5);
	}
	if(imgW>800)
	{
		narrowImg(imgW, imgH);
	}
	else
	{
		//alert(imgW+":"+imgH);
		$("#imgObj").attr("width", imgW);
		$("#imgObj").attr("height", imgH);
		$("#imgObj").css("display", "block");
	}
});
}
setTimeout('imageShow()', 1000);
</script>
<%
	}
}
%>