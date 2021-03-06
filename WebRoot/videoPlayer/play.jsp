<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String flvPath = request.getParameter("flvPath");
if(flvPath==null)
{
	flvPath = "";
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ckplayer</title>
<style type="text/css">
body,td,th {
	font-size: 14px;
	line-height: 28px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
p {
	padding-left: 20px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
}
</style>
<script type="text/javascript" src="js/offlights.js" charset="utf-8"></script>
</head>
  
<body>
<div id="flashcontent"></div>
<div id="video" style="position:relative;z-index: 100;width:560px;height:400px;"><div id="a1"></div></div>
<script type="text/javascript" src="ckplayer/ckplayer.js" charset="utf-8"></script>
<script type="text/javascript">
	var flashvars={
	f:'<%=flvPath%>',//视频地址
	a:'',//调用时的参数，只有当s>0的时候有效
	s:'0',//调用方式，0=普通方法（f=视频地址），1=网址形式,2=xml形式，3=swf形式(s>0时f=网址，配合a来完成对地址的组装)
	c:'0',//是否读取文本配置,0不是，1是
	x:'',//调用xml风格路径，为空的话将使用ckplayer.js的配置
	i:'',//初始图片地址
	//d:'http://www.ckplayer.com/down/pause.swf',//暂停时播放的广告，swf/图片
	d:'',//暂停时播放的广告，swf/图片
	u:'',//暂停时如果是图片的话，加个链接地址
	//l:'http://www.ckplayer.com/down/start.swf',//视频开始前播放的广告，swf/图片/视频
	l:'',//视频开始前播放的广告，swf/图片/视频
	r:'',//视频开始前播放图片/视频时加一个链接地址
	t:'',//视频开始前播放swf/图片时的时间
	e:'2',//视频结束后的动作，0是调用js函数，1是循环播放，2是暂停播放，3是调用视频推荐列表的插件
	v:'85',//默认音量，0-100之间
	p:'1',//视频默认0是暂停，1是播放
	h:'1',//播放http视频流时采用何种拖动方法，0是按关键帧，1是按关键时间点
	q:'',//视频流拖动时参考函数，默认是start
	m:'0',//默认是否采用点击播放按钮后再加载视频，0不是，1是,设置成1时不要有前置广告
	g:'',//视频直接g秒开始播放
	j:'',//视频提前j秒结束
	k:'10|40|80',//提示点时间，如 30|60鼠标经过进度栏30秒，60秒会提示n指定的相应的文字
	//n:'跳过开头|小企鹅宝宝下水了|跳过结尾',//提示点文字，跟k配合使用，如 提示点1|提示点2
	n:'',//提示点文字，跟k配合使用，如 提示点1|提示点2
	b:'0x000',//播放器的背景色，如果不设置的话将默认透明
 	w:'',//指定调用自己配置的文本文件,不指定将默认调用和播放器同名的txt文件
	//调用播放器的所有参数列表结束
	//以下为自定义的播放器参数用来在插件里引用的
	my_title:'视频标题',//视频标题
	my_url:'http://www.ckplayer.com/index.php',//本页面地址
	my_summary:'这是一个测试页面',//视频介绍，请保持在一行文字，不要换行
	//my_pic:'http://www.ckplayer.com/temp/11.jpg'//分享的图片地址
	my_pic:''//分享的图片地址
	//调用自定义播放器参数结束
	};
	var params={bgcolor:'#000000',allowFullScreen:true,allowScriptAccess:'always'};//这里定义播放器的其它参数如背景色（跟flashvars中的b不同），是否支持全屏，是否支持交互
	var attributes={id:'ckplayer_a1',name:'ckplayer_a1'};
	//下面一行是调用播放器了，括号里的参数含义：（播放器文件，要显示在的div容器，宽，高，需要flash的版本，当用户没有该版本的提示，加载初始化参数，加载设置参数如背景，加载attributes参数，主要用来设置播放器的id）
	swfobject.embedSWF('ckplayer/ckplayer.swf', 'a1', '560', '400', '10.0.0','ckplayer/expressInstall.swf', flashvars, params, attributes); //播放器地址，容器id，宽，高，需要flash插件的版本，flashvars,params,attributes	
	//调用播放器结束
	//第一部分：开关灯
	var box = new LightBox('flashcontent');
	var $=function(id){return document.getElementById(id)};
	function closelights(){//关灯
		box.Show();
		$('video').style.width='940px';
		$('video').style.height='550px';
		swfobject.getObjectById('ckplayer_a1').width=940;
		swfobject.getObjectById('ckplayer_a1').height=550;
	}
	function openlights(){//开灯
		box.Close();
		$('video').style.width='560px';
		$('video').style.height='400px';
		swfobject.getObjectById('ckplayer_a1').width=560;
		swfobject.getObjectById('ckplayer_a1').height=400;
	}
</script>

</body>
  </body>
</html>
