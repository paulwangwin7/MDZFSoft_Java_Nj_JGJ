<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
test.jsp

<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" 
                            codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" 
                            height="600" width="800"> 
                            <param name="movie" 
                                value="vcastr22.swf?vcastr_file=http://192.168.1.101/upload/files/41/2/20121101_114004_854.flv"> 
                            <param name="quality" value="high"> 
                            <param name="allowFullScreen" value="true" /> 
                            <embed 
                                src="vcastr22.swf?vcastr_file=http://192.168.1.101/upload/files/41/2/20121101_114004_854.flv" 
                                quality="high" 
                                pluginspage="http://www.macromedia.com/go/getflashplayer" 
                                type="application/x-shockwave-flash" width="800" height="600"> 
                            </embed> 
                        </object> 
