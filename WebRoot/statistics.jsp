<%@page import="com.manager.pub.bean.UserForm"%>
<%@page import="com.manager.pub.util.Constants"%>
<%@page import="com.manager.pub.bean.TreeForm"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.manager.pub.util.DateTimeUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserForm userForm = null;
if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)!=null) {
    userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
}
%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	<title>部门[${corpName}]
	<c:if test="${param['queryType'] =='1'}">&nbsp;${param['year']}年&nbsp;统计报表</c:if>
	<c:if test="${param['queryType'] =='2'}">&nbsp;${param['year']}年${param['month'] }月&nbsp;统计报表</c:if>
	<c:if test="${param['queryType'] =='3'}">&nbsp;${param['startDate']}-${param['endDate'] }&nbsp;统计报表</c:if></title> 
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/all.css" />
    <jsp:include page="common/tag.jsp" />
	<script language="javascript" type="text/javascript" src="<%=basePath %>js/jquery.fixedtableheader.min.js"></script>
	<link rel="stylesheet" href="<%=basePath %>js/tablesorter/themes/table.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="<%=basePath %>js/tablesorter/themes/table1.css" type="text/css" media="print, projection, screen" />
	<script src="<%=basePath %>js/tablesorter/table.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="<%=basePath %>js/My97DatePicker/WdatePicker.js"></script>

	<style>
.fixedtableheader {
    background-color: #E1E1E1;
    border: 1px solid #CACACA;
    color: #010101;
    font-weight: bold;
    height: 34px;
    line-height: 34px;
    text-align: center;
}
</style>
	
		<style type="text/css">
		body {
	background-color: #FFFFFF;
} 
		.blue{color: blue;}
		

img {
    border: 0 none;
    vertical-align: middle;
}
		</style>
		<script type="text/javascript">
$(function(){
    $("table[fixedtableheader]").fixedtableheader();
    
                $("td[flag^='total1_']").each(function(){
                 var flag=$(this).attr("flag");
                 var index=flag.split("_")[1]; 
                 var total=0;
                 $(this).parent().parent().find("td[index='"+index+"']").each(function(){
                     if($(this).find("a").length>0){
                         total+=parseInt($(this).find("a").html());
                     }else{
                         total+=parseInt($(this).html());
                     }
                 });
                 
                 var u=$(this).attr("u");
                 if(u=="U"){
                    $(this).html(total);
                 }else
                     $(this).html("<a href='javascript:void(0)' class='detials' corpId='"+$(this).attr("corpId")+"' corpName='"+$(this).attr("corpName")+"'>"+total+"</a>");
                 if(total!=0){
                     $(this).css("color","blue");
                 }else{
                 }
            });
            
            var total1=0;
            $("td[flag='total2']").each(function(){
                 var total=0;
                 $(this).parent().find("td[index]").each(function(){
                     if($(this).find("a").length>0){
                         total+=parseInt($(this).find("a").html());
                     }else{
                         total+=parseInt($(this).html());
                     }
                 });
                 total1+=total;
                 if(total!=0){
                     $(this).html(total);
                     $(this).css("color","blue");
                 }else{
                      $(this).html(total);
                 }
            });
            
            if(total1!=0){
               $("td[flag='total']").html(total1);
               $("td[flag='total']").css("color","blue");
            }else{
                $("td[flag='total']").html(total1);
            }
            
            $(".tablesorter").fixedtableheader();
            
            $(".detials").click(function(event){
                var corpId=$(this).attr("corpId");
                $("#form").find("#corpId").val(corpId);
                
                //var corpName=$(this).attr("corpName");
                //$("#form").find("#corpName").val(corpName);
                
                var start=$(this).parent().parent().children("td:first").attr("start");
                var end=$(this).parent().parent().children("td:first").attr("end");
                var query=$(this).parent().parent().children("td:first").attr("query");

                $("#form").find("#startDate").val(start);
                $("#form").find("#endDate").val(end);
                $("#form").find("#queryType").val(query);
                
                $("#form").submit();
            });
            
            $(".detialsTotal").click(function(event){
                var corpId=$(this).attr("corpId");
                $("#form").find("#corpId").val(corpId);
                
                //var corpName=$(this).attr("corpName");
                //$("#form").find("#corpName").val(corpName);
                
                $("#form").submit();
            });
});


</script>
	</head>
	<body > 
	<div class="gray_bor_bg">
                                <h5 class="gray_blod_word">组合条件搜索</h5>
                                <div class="search_form">
                                    <div class="mt_10">
                        <form id="newReportForm" action=""  method="post" >
                                     <label>选择部门：</label>
                                     <select name="corpId" id="corpId">
                                        <%
    if(userForm!=null && (userForm.getUserId()==0 || userForm.getRoleType().equals("0"))) {
%>
       <option value="0">交管局</option>
<%
    }
                                        
                                        
    List list_totalTree = (List)request.getAttribute(Constants.JSP_TREE_LIST);
    if(list_totalTree!=null && list_totalTree.size()>0)
    {
        for(int i=0; i<list_totalTree.size(); i++)//一级部门循环
        {
            TreeForm rootTreeForm = (TreeForm)((List)(list_totalTree.get(i))).get(0);
            List<TreeForm> treeFormList = ((List<TreeForm>)((List)(list_totalTree.get(i))).get(1));
%>
    <option value="<%=rootTreeForm.getTreeId() %>"><%=rootTreeForm.getTreeName() %></option>
<%
            for(int j=0; j<treeFormList.size(); j++)//二级部门循环
            {
%>
       <option value="<%=treeFormList.get(j).getTreeId() %>">&nbsp;&nbsp;<%=treeFormList.get(j).getTreeName() %></option>

<%
            }
        }
    }
%>
                                     </select>
                                   
                                    <label>统计口径：</label>
                                    <select class="input_79x19" id="queryType" name="queryType"  >
                                    <option value="1">按年统计</option>
                                    <option value="2">按月统计</option>
                                    <option value="3">按天统计</option>
                                    </select>
                                    &nbsp;&nbsp;
                                    <select id="year" name="year" class="input_79x19">
                                    <%
                                    int year=Integer.parseInt(DateTimeUtil.getChar8().substring(0,4));
                                    for(int i=0;i<8;i++){
                                         
                                         out.print("<option  value=\""+(year-i)+"\">"+(year-i)+"</option>");
                                    }
                                    %>
                                    </select>
                                    <select id="month" name="month" class="input_79x19"  style="display:none">
                                        <%
                                        String month=DateTimeUtil.getChar8().substring(4,6);
                                        for(int i=1;i<=12;i++){
                                            String mon="0"+i;
                                             if(i>=10){
                                                 mon=Integer.toString(i);
                                             }
                                             out.print("<option "+(month.equals(mon)?"selected":"")+"  value=\""+mon+"\">"+mon+"</option>");
                                        }
                                        %>
                                    </select>
                                    <label id='tmp' >统计起止日期:</label>
                                    <input type="text" class="input_79x19 Wdate" id="startDate_1" name="startDate" value=""  readonly  style="width:100px;"  onClick="WdatePicker()"/>&nbsp;&nbsp;
                                    <input type="text" class="input_79x19 Wdate" id="endDate_1" name="endDate" value=""  readonly  style="width:100px;"  onClick="WdatePicker()" />
                                  <br/>
                                   <label>统计内容：</label>  
                                   <input type="radio" value="0" name="dimension" />汇总
                                   <input type="radio" value="1" name="dimension" />视频
                                   <input type="radio" value="2" name="dimension" />音频
                                   <input type="radio" value="3" name="dimension" />图片
                                   &nbsp;&nbsp;
                                   <label>是否包含大队中的人员：<input type="checkbox" value="1" name="includeDZUser" /></label>  
                                    &nbsp;&nbsp;
                                    <label>按
                                     <select class="" id="userType" name="userType" >
                                     <option value="1">采 集 人</option>  
                                     <option value="2">上 传 人</option>
                                    </select></label>
                                    &nbsp;&nbsp;
                                <input type="submit" id="submit" name="submit" class="blue_mod_btn" value="统&nbsp;计" />
                                
                                <input type="submit" id="export" name="export" class="blue_mod_btn" value="导&nbsp;出" />
</form>
<script>
//数据初始化
jQuery(function($) {
    $("#submit").click(function(event){
        $("#newReportForm").attr("action","<%=basePath %>servletAction.do?method=statistics");
        $("#newReportForm").removeAttr("target");
    });
     $("#export").click(function(event){ 
        $("#newReportForm").attr("action","<%=basePath %>servletAction.do?method=statisticsExport");
        $("#newReportForm").attr("target","_blank");
    });
    $("#queryType").change(function(){
        if($(this).val()=="3")
        {
            $("#year").hide();
            $("#month").hide();
            
            $("#tmp").hide();
            
            $("#startDate_1").val("<%=DateTimeUtil.formatChar8(DateTimeUtil.rollDate(DateTimeUtil.getChar8(),-6)).trim() %>");
            $("#endDate_1").val("<%=DateTimeUtil.formatChar8(DateTimeUtil.getChar8()).trim() %>");
            
        }else if($(this).val()=="2"){
            $("#year").show();
            $("#month").show();
            $("#tmp").show();
            
            var year=$("#year").val();
            var month=$("#month").val();
            if(month=="01"){
                $("#startDate_1").val(parseInt(year)-1+"-12-21");
                $("#endDate_1").val(year+"-01"+"-20");
            }else{
                var month1=parseInt(year)-1;
                month1="0"+month1;
                month1=month1.substring(month1.length-2, month1.length);
                var month2=parseInt(year);
                month2="0"+month2;
                month2=month2.substring(month2.length-2, month2.length);
                
                $("#startDate_1").val(year+"-"+month1+"-21");
                $("#endDate_1").val(year+"-"+month2+"-20");
            }
            
        } else   {
            $("#year").show();
            $("#month").hide();
            $("#tmp").show();
            
            var year=$("#year").val();
            $("#startDate_1").val(parseInt(year)-1+"-12-21");
            $("#endDate_1").val(year+"-12-20");
        }
    });
    
    $("#year").change(function(){
        var queryType=$("#queryType").val();
        if(queryType=="1"){
            var year=$("#year").val();
            $("#startDate_1").val(parseInt(year)-1+"-12-21");
            $("#endDate_1").val(year+"-12-20");
        }else if(queryType=="2"){
            var year=$("#year").val();
            var month=$("#month").val();
            if(month=="01"){
                $("#startDate_1").val(parseInt(year)-1+"-12-21");
                $("#endDate_1").val(year+"-01"+"-20");
            }else{
                var month1=parseInt(month)-1;
                month1="0"+month1;
                month1=month1.substring(month1.length-2, month1.length);
                var month2=parseInt(month);
                month2="0"+month2;
                month2=month2.substring(month2.length-2, month2.length);
                
                $("#startDate_1").val(year+"-"+month1+"-21");
                $("#endDate_1").val(year+"-"+month2+"-20");
            }
        }
    });
    
    $("#month").change(function(){
        var year=$("#year").val();
        var month=$("#month").val();
        if(month=="01"){
            $("#startDate_1").val(parseInt(year)-1+"-12-21");
            $("#endDate_1").val(year+"-01"+"-20");
        }else{
            var month1=parseInt(month)-1;
            month1="0"+month1;
            month1=month1.substring(month1.length-2, month1.length);
            var month2=parseInt(month);
            month2="0"+month2;
            month2=month2.substring(month2.length-2, month2.length);
            
            $("#startDate_1").val(year+"-"+month1+"-21");
            $("#endDate_1").val(year+"-"+month2+"-20");
        }
    
    });
    
    $("#corpId").val("${param['corpId']}");
    $("#queryType").val("${param['queryType']}");
    $("#queryType").change();
    $("#userType").val("${param['userType']}");
    $("#year").val("${param['year']}");
    $("#month").val("${param['month']}");
    $("#endDate_1").val("${param['endDate']}");
    $("#startDate_1").val("${param['startDate']}");
    
    <% String[] dimension=request.getParameterValues("dimension"); 
    if(null!=dimension){
        for(int i=0;i<dimension.length;i++){
            out.println("$(\"input[name='dimension']\").eq("+dimension[i]+").attr(\"checked\",\"checked\");");
        }
    }
    
    String includeDZUser=request.getParameter("includeDZUser");
    if(null!=includeDZUser && "1".equals(includeDZUser))
        out.println("$(\"input[name='includeDZUser']\").attr(\"checked\",\"checked\");");
    
    %>
    
});
</script>                                   
                                    </div>
                                </div>
                            </div>
	<div style="height:440px">
	<table cellspacing="1" class="tablesorter" fixedtableheader='1' width="${100+40+fn:length(yList)*100+100 }px" >
			<thead>
				<tr align="center">
					<th style="width: 100px" class="fixedtableheader"></th>
					<c:forEach items="${yList}" var="yitem">
						<th style="width:100px" class="fixedtableheader">
						<c:if test="${yitem['class'] =='class com.manager.pub.bean.TreeForm'}">
						  ${yitem.treeName }
						</c:if>
						<c:if test="${yitem['class'] =='class com.manager.pub.bean.UserForm'}">
                          ${yitem.userName }
                        </c:if>
						</th>
					</c:forEach>
					<th style="width:100px" class="fixedtableheader">小计</th>
				</tr>
			</thead>
			<tbody>
			<tr align="center"  style="font-weight: bold;">
                        <td start="${startFileUploadTime }" end="${endFileUploadTime }" query="${param['queryType']}">合计</td>
                        <c:forEach items="${yList}" var="yitem" varStatus="status">
                             <c:if test="${yitem['class'] =='class com.manager.pub.bean.TreeForm'}">
                            <td flag="total1_${status.index}" corpId="${yitem.treeId }" corpName="${yitem.treeName }">
                             </td>
                             </c:if>
                             <c:if test="${yitem['class'] =='class com.manager.pub.bean.UserForm'}">
                               <td flag="total1_${status.index}" U="U" corpId="${yitem.userId }" corpName="${yitem.userName }">
                             </td>
                            </c:if>
                         </c:forEach>
                         <td flag="total"></td>
                    </tr>
				<c:forEach items="${xList}" var="xItem">
                    <tr align="center">
                        <td  start="${startTimes[xItem] }" end="${endTimes[xItem] }" query="3">
                          ${xItem}</td>
                        <c:forEach items="${yList}" var="yitem" varStatus="status">
	                        <c:if test="${yitem['class'] =='class com.manager.pub.bean.TreeForm'}">
	                           <c:set var="key" value="C_${yitem.treeId}_${xItem}"></c:set>
	                           <c:set var="id" value="${yitem.treeId }"></c:set>
	                           <c:set var="name" value="${yitem.treeName }"></c:set>
	                        </c:if>
	                        <c:if test="${yitem['class'] =='class com.manager.pub.bean.UserForm'}">
	                          <c:set var="key" value="U_${yitem.userId}_${xItem}"></c:set>
	                        </c:if>
                            <%
                                String key=(String)pageContext.getAttribute("key");
                                key=key.replaceAll("-", "");
                                pageContext.setAttribute("key",key);
                             %>
                            <c:if test="${detialData[key]==null}"><td index="${status.index}"><c:if test="${yitem['class'] =='class com.manager.pub.bean.TreeForm'}">
                            <a href="javascript:void(0)" class="detials" corpId="${id }" corpName="${name }"></c:if>0<c:if test="${yitem['class'] =='class com.manager.pub.bean.TreeForm'}">
                            </a></c:if></td></c:if>
                            <c:if test="${detialData[key]!=null}"><td index="${status.index}" style="color: blue">
                           <c:if test="${yitem['class'] =='class com.manager.pub.bean.TreeForm'}">
                             <a href="javascript:void(0)"  class="detials" corpId="${id }" corpName="${name }">
                             </c:if>${detialData[key]}<c:if test="${yitem['class'] =='class com.manager.pub.bean.TreeForm'}">
                            </a></c:if></td></c:if>
                         </c:forEach>
                         <td flag="total2" style="font-weight: bold;"></td>
                    </tr>
                </c:forEach>
                    <tr align="center"  style="font-weight: bold;">
                        <td start="${startFileUploadTime }" end="${endFileUploadTime }" query="${param['queryType']}">合计</td>
                        <c:forEach items="${yList}" var="yitem" varStatus="status">
                             <c:if test="${yitem['class'] =='class com.manager.pub.bean.TreeForm'}">
                            <td flag="total1_${status.index}" corpId="${yitem.treeId }" corpName="${yitem.treeName }">
                             </td>
                             </c:if>
                             <c:if test="${yitem['class'] =='class com.manager.pub.bean.UserForm'}">
                               <td flag="total1_${status.index}" U="U" corpId="${yitem.userId }" corpName="${yitem.userName }">
                             </td>
                            </c:if>
                         </c:forEach>
                         <td flag="total"></td>
                    </tr>
	                  <tr align="left"  style="font-weight: bold;" >
	                       <td colspan="${fn:length(yList)+2 }">今天[<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>]已经有${fn:length(todayUploadUsers) }个警员上传数据!</td>
	                  </tr>
			</tbody>
		</table>
	</div>
	   <form target="_blank" id="form" action="<%=basePath %>servletAction.do?method=statistics" method="post">
        <input name="corpId" id="corpId" type="hidden" value="${param['corpId']}"/>  
        <input name="queryType" id="queryType" type="hidden" value="${param['queryType']}"/>
        <input name="year" id="year" type="hidden" value="${param['year']}"/>
        <input name="month" id="month" type="hidden" value="${param['month']}"/>
        <input name="startDate" id="startDate" type="hidden" value="${param['startDate']}"/>
        <input name="endDate" id="endDate" type="hidden" value="${param['endDate']}"/>
        <%
         if(null!=dimension){
	        for(int i=0;i<dimension.length;i++){
	            out.println("<input type=\"hidden\" value=\""+dimension[i]+"\" name=\"dimension\" />");
	        }
        }
    
        if(null!=includeDZUser && "1".equals(includeDZUser))
            out.println("<input type=\"hidden\" value=\"1\" name=\"includeDZUser\" />");
    
         %>
    </form>
</body>
</html>

 
