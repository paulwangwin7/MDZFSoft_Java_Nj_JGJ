<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String flvPath = request.getParameter("flvPath");
if(flvPath==null)
{
	flvPath = "";
}
%>

<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" 
                            codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" 
                            height="400" width="560"> 
                            <param name="movie" 
                                value="vcastr22.swf?vcastr_file=<%=flvPath %>"> 
                            <param name="quality" value="high"> 
                            <param name="allowFullScreen" value="true" /> 
                            <embed 
                                src="vcastr22.swf?vcastr_file=<%=flvPath %>" 
                                quality="high" 
                                pluginspage="http://www.macromedia.com/go/getflashplayer" 
                                type="application/x-shockwave-flash" width="560" height="400"> 
                            </embed> 
                        </object> 
