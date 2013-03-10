<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="com.manager.pub.bean.*,com.manager.pub.util.*" %>
<%
	String returnCode = "";
	String returnMsg = "";
	InformationFrameForm jspInformation = null;
	if(request.getAttribute(Constants.JSP_MESSAGE)!=null) {
		jspInformation = (InformationFrameForm)request.getAttribute(Constants.JSP_MESSAGE);
		returnCode = jspInformation.getFrameMsgCode();
		returnMsg = jspInformation.getMessage();
	}
%>
<%=returnCode%>;<%=returnMsg%>