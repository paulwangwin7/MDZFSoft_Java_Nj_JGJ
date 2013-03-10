<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	if(request.getAttribute("jsonViewStr")!=null)
	{
		System.out.print("page print:"+request.getAttribute("jsonViewStr").toString());
		out.println(request.getAttribute("jsonViewStr").toString());
	}
%>