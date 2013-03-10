<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String wavPath = request.getParameter("wavPath");
	if (wavPath == null) {
		wavPath = "";
	}
%>
<OBJECT type=application/x-oleobject
	classid=CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6>
	<PARAM NAME="URL" VALUE="<%=wavPath %>">
	<PARAM NAME="rate" VALUE="1">
	<PARAM NAME="balance" VALUE="0">
	<PARAM NAME="currentPosition" VALUE="0">
	<PARAM NAME="defaultFrame" VALUE="">
	<PARAM NAME="playCount" VALUE="1">
	<PARAM NAME="autoStart" VALUE="-1">
	<PARAM NAME="currentMarker" VALUE="0">
	<PARAM NAME="invokeURLs" VALUE="-1">
	<PARAM NAME="baseURL" VALUE="">
	<PARAM NAME="volume" VALUE="80">
	<PARAM NAME="mute" VALUE="0">
	<PARAM NAME="uiMode" VALUE="full">
	<PARAM NAME="stretchToFit" VALUE="-1">
	<PARAM NAME="windowlessVideo" VALUE="0">
	<PARAM NAME="enabled" VALUE="-1">
	<PARAM NAME="enableContextMenu" VALUE="0">
	<PARAM NAME="fullScreen" VALUE="0">
	<PARAM NAME="SAMIStyle" VALUE="">
	<PARAM NAME="SAMILang" VALUE="">
	<PARAM NAME="SAMIFilename" VALUE="">
	<PARAM NAME="captioningID" VALUE="">
	<PARAM NAME="enableErrorDialogs" VALUE="0">
	<PARAM NAME="_cx" VALUE="10372">
	<PARAM NAME="_cy" VALUE="1693">

	<!--为了支持FF 开始-->
	<embed type="application/x-mplayer2"></embed>
	<!--为了支持FF 结束-->
</OBJECT>
</DIV>