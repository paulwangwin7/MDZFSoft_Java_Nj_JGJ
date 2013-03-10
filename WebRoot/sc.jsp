<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String static_sum = request.getParameter("static_sum")==null?"0":request.getParameter("static_sum");
	String static_uploaded = request.getParameter("static_uploaded")==null?"0":request.getParameter("static_uploaded");
	String speed = request.getParameter("speed")==null?"0":request.getParameter("speed");
	System.out.println("speed================"+speed);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Loading Status Bar</title>
<meta name="robots" content="noindex,follow" />
<script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
<script type="text/javascript">
//document.writeln("<style type=\"text\/css\">#loading{width:400px;height:20px;background:#A0DB0E;padding:5px;position:fixed;left:0;top:0}#loading div{width:4px;height:20px;background:#F1FF4D;font:10px/20px Arial}<\/style>")
</script>
<style>
#loading {
  width:200px;height:10px;background:#A0DB0E;padding:5px;position:fixed;left:0;top:0
}
#loading div {
  width:2px;height:10px;background:#F1FF4D;font:10px/10px Arial;
  FILTER:progid:DXImageTransform.Microsoft.Gradient(gradientType=1,startColorStr=#46B5FF,endColorStr=#ff00ff);
}
</style>
</head>
<body>
<noscript>您的浏览器不支持 Javascript</noscript>
<table>
<tr><td>
<div id="loading" style="top:20px"><div></div></div>
<!--input type="text" name="text1" id="text1"/-->
</td></tr>
<tr><td height="50px" valign="bottom">
<div style="top:20px"><br/>&nbsp;<br/>
当前上传进度：<%=static_uploaded %>/<%=static_sum %>
</div>
</td></tr>
<tr><td>
<div style="top:20px">
当前上传速度：<span id="spd">0</span>&nbsp; K / S
</div>
</td></tr>
</table>
<script>
var sc_setInterval;
var static_sc = 0;

function showScrolling(scNum)
{
  try
  {
    if(isNaN(scNum))
    {
      alert('请输入一个数字');
    }
    else if(scNum>100 || scNum<0)
    {
      alert('请输入一个0到100的数字');
    }
    else
    {
      
      $("#loading div").animate({width:(scNum<3?3:scNum)*2+"px"}).text(scNum+"%");
      showSpeed();
	    if(scNum == "100")
	    {
	  	  scOver();
	    }
    }
  }
  catch(e)
  {
    alert(e);
  }
}


/*function showScrolling2()
{
  setTimeout('start(88)',1000);
}
function start(sss)
{
      $("#loading div").animate({width:sss*2+"px"}).text(sss+"%")
}*/


function showScrolling2()
{
  static_sc = parent.ftpGetUploadFilePercent();
  showScrolling(static_sc);
}

function scOver()
{
window.clearInterval(sc_setInterval);
window.setTimeout(function(){parent.uploadSuccess();},2000);
}

sc_setInterval = window.setInterval('showScrolling2()', 2000);

function showSpeed()
{
document.getElementById('spd').innerHTML = parent.ftpGetUploadSpeed();
}
</script>
</body>
</html>