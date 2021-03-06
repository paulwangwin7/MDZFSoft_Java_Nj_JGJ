<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.util.*, com.manager.pub.bean.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserForm userForm = null;
if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)!=null) {
    userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心统计报表</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/all.css" />
<jsp:include page="common/tag.jsp" />
<script type="text/javascript" src="<%=basePath%>js/jquery_dialog.js"></script>
<script type="text/javascript" src="<%=basePath %>pagejs/dateUtil.js"></script>
<script type="text/javascript" src="<%=basePath %>js/adddate.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath %>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#dialog").click(function(){
	$.weeboxs.open('.boxcontent', {title:'统计表报',contentType:'selector'});
});})

var yearArray = getYearArrChar8("<%=DateUtils.getChar8()%>");
var nowMonth = "<%=DateUtils.getChar8().substring(4,6)%>";

</script>
</head>

<body>

	<!--header=============================================begin-->
<jsp:include page="common/header.jsp" />
<script>var menuIndex = 4;</script>
<jsp:include page="common/menu.jsp" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
					<h4 class="content_hd long_content_hd">统计报表</h4>
					<div class="content_bd">
						<ul class="Js_tab tab_list">
						    <li class="tab_item current" onclick="showFrame('1')">
						      新报表
						    </li>
						    
							<li class="tab_item" onclick="showFrame('2')">
								大队统计
							</li>
							<li class="tab_item" onclick="showFrame('3')">中队统计</li>
							<li class="tab_item" onclick="showFrame('4')">警员统计</li>
						</ul>
						<ul class="tab_conlist">

							<li class="tab_conitem current">
							<div class="gray_bor_bg">
								<h5 class="gray_blod_word">组合条件搜索</h5>
								<div class="search_form">
									<div class="mt_10">
<form id="newReportForm" action="" method="post" target="_blank">
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
                                    <select class="input_79x19" id="queryType" name="queryType" >
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
                                   <input type="radio" value="0" name="dimension" checked='checked'/>汇总
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
    });
     $("#export").click(function(event){ 
        $("#newReportForm").attr("action","<%=basePath %>servletAction.do?method=statisticsExport");
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
    
    $("#queryType").change();
});
</script>									
									</div>
								</div>
							</div>
							</li>
							
							<li class="tab_conitem">
                            <div class="gray_bor_bg">
                                <h5 class="gray_blod_word">组合条件搜索</h5>
                                <div class="search_form">
                                    大队统计
                                    <div class="mt_10">
<form id="childTreeForm" action="<%=basePath %>servletAction.do?method=uploadLog_byTree1" method="post" target="rootTree">
                                    <select class="input_79x19" id="req_tree1_searchType" name="tree1_searchType">
                                    <option value="1">按年统计</option><option value="2">按月统计</option>
                                    </select>&nbsp;&nbsp;
                                    <br/>
                                    <select id="req_tree1_year" name="tree1_year" class="input_79x19"></select>
                                    <select id="req_tree1_month" name="tree1_month" class="input_79x19" style="display:none"></select>
                                    <br/>
                                    <input type="submit" class="blue_mod_btn" value="统&nbsp;计" />
</form>
<script>
//数据初始化
jQuery(function($) {
    for(i=0; i<yearArray.length; i++)
    {
        var appendStr = "<option value='"+yearArray[i]+"'";
        if((i+1)==yearArray.length)
        {
            appendStr += " selected";
        }
        appendStr += ">"+yearArray[i]+"</option>";
        $("#req_tree1_year").append(appendStr);
    }
    for(i=1; i<=12; i++)
    {
        var monthStr = (i<10)?("0"+i):i;
        var appendStr = "<option value='"+monthStr+"'";
        if(monthStr==nowMonth)
        {
            appendStr += " selected";
        }
        appendStr += ">"+monthStr+"</option>";
        $("#req_tree1_month").append(appendStr);
    }

    $("#req_tree1_searchType").change(function(){
        if($(this).val()=="1")
        {
            $("#req_tree1_month").css("display", "none");
        }
        else
        {
            $("#req_tree1_month").css("display", "block");
        }
    });
});
</script>
                                    
                                    </div>
                                </div>
                            </div>
                            </li>
							<li class="tab_conitem">
							<div class="gray_bor_bg">
								<h5 class="gray_blod_word">组合条件搜索</h5>
								中队统计
								<div class="search_form">
<form id="childTreeForm" action="<%=basePath %>servletAction.do?method=uploadLog_byTree2" method="post" onsubmit="return tree2Sub()" target="childTree">
<input type="hidden" name="tree2_treeName" value="test"/>
									<div class="mt_10">
									<label>选择部门:</label>
									<select id="req_tree2_treeId" name="tree2_treeId">
<%
	if(list_totalTree!=null && list_totalTree.size()>0)
	{
		for(int i=0; i<list_totalTree.size(); i++)//一级部门循环
		{
			TreeForm rootTreeForm = (TreeForm)((List)(list_totalTree.get(i))).get(0);
			List<TreeForm> treeFormList = ((List<TreeForm>)((List)(list_totalTree.get(i))).get(1));
%>
											<option value="<%=rootTreeForm.getTreeId() %>"><%=rootTreeForm.getTreeName() %></option>
<%
		}
	}
%>
									</select>
									<br/>
									<select style="width:144px" name="tree2_searchType" id="req_tree2_searchType">
										<option value="1">按年统计</option>
										<option value="2">按月统计</option>
									</select>
									<br/>
									<select id="req_tree2_year" name="tree2_year" class="input_79x19"></select>
									<select id="req_tree2_month" name="tree2_month" class="input_79x19" style="display:none"></select>
									<br/>
									<input type="submit" class="blue_mod_btn" value="统&nbsp;计" />
</form>
<script>
//数据初始化
jQuery(function($) {
	for(i=0; i<yearArray.length; i++)
	{
		var appendStr = "<option value='"+yearArray[i]+"'";
		if((i+1)==yearArray.length)
		{
			appendStr += " selected";
		}
		appendStr += ">"+yearArray[i]+"</option>";
		$("#req_tree2_year").append(appendStr);
	}
	for(i=1; i<=12; i++)
	{
		var monthStr = (i<10)?("0"+i):i;
		var appendStr = "<option value='"+monthStr+"'";
		if(monthStr==nowMonth)
		{
			appendStr += " selected";
		}
		appendStr += ">"+monthStr+"</option>";
		$("#req_tree2_month").append(appendStr);
	}

	$("#req_tree2_searchType").change(function(){
		if($(this).val()=="1")
		{
			$("#req_tree2_month").css("display", "none");
		}
		else
		{
			$("#req_tree2_month").css("display", "block");
		}
	});
});

function tree2Sub()
{
jQuery(function($) {
	hideObj("analysisUploadMsg");
});
return true;
}
</script>
									</div>
								</div>
							</div>
							</li>
							<li class="tab_conitem">
							<div class="gray_bor_bg">
								<h5 class="gray_blod_word">组合条件搜索</h5>
<div class="error msg" id="analysisUploadMsg" style="display:none" onclick="hideObj('analysisUploadMsg')">Message if login failed</div>
								警员统计
<form id="byUserForm" action="<%=basePath %>servletAction.do?method=uploadLog_byUser" method="post" target="personal">
<input type="hidden" id="upload_editId" name="user_userId" />
								<div class="search_form">
									<div class="mt_10">
									<label>警员:</label>
									<input type="text" id="editName" name="user_name" value="" class="input_79x19" readonly onclick="userChoose()" />
									<label>按年份:</label>
									<select style="width:144px" name="user_searchType" id="req_user_searchType">
										<option value="1">按年统计</option>
										<option value="2">按月统计</option>
									</select>
									<br/>
									<select id="req_user_year" name="user_year" style="width:144px"></select>
									<select id="req_user_month" name="user_month" style="width:144px; display:none"></select>
									<br/>
									<input type="button" onclick="byUserSub()" class="blue_mod_btn" value="统&nbsp;计" />
									</div>
								</div>
</form>
<div id="userChooseDiv" icon="icon-save" style="display:none" class="boxcontent">
<div class="gray_bor_bg">
<div class="error msg" id="userChooseMsg" style="display:none" onclick="hideObj('userChooseMsg')">Message if login failed</div>
							<h5 class="gray_blod_word">组合条件搜索</h5>
							<div class="search_form">
					<form id="userManagerForm" name="userManagerForm" target="userChooseIframe" action="<%=basePath %>userAction.do?method=userSelect" method="post">
								<label>姓名：</label><input type="text" name="user_name" value="" class="input_79x19"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<label>警员编号：</label><input type="text" name="user_code" value="" class="input_79x19"/>&nbsp;&nbsp;&nbsp;&nbsp;
								<label>所属部门：</label>
										<select name="query_treeId" class="input_130x20">
											<option value=""> -- </option>
<%
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
											<option value="<%=treeFormList.get(j).getTreeId() %>">&nbsp;<%=treeFormList.get(j).getTreeName() %></option>
<%
			}
		}
	}
%>
										</select>
								<input type="submit" class="blue_mod_btn" value="搜 &nbsp;索" />
								<a id="closeDialog" style="display:none" href="javascript:void(0)" class="Js_closePar blue_mod_btn" class="">取&nbsp;&nbsp;消</a>
					</form>
							</div>
						</div>
						<iframe src="" name="userChooseIframe" width="578" height="330" frameborder="0" scrolling="no"></iframe>
</div>
							</div>
							</li>
						</ul>
<script>
//数据初始化
jQuery(function($) {
	for(i=0; i<yearArray.length; i++)
	{
		var appendStr = "<option value='"+yearArray[i]+"'";
		if((i+1)==yearArray.length)
		{
			appendStr += " selected";
		}
		appendStr += ">"+yearArray[i]+"</option>";
		$("#req_user_year").append(appendStr);
	}
	for(i=1; i<=12; i++)
	{
		var monthStr = (i<10)?("0"+i):i;
		var appendStr = "<option value='"+monthStr+"'";
		if(monthStr==nowMonth)
		{
			appendStr += " selected";
		}
		appendStr += ">"+monthStr+"</option>";
		$("#req_user_month").append(appendStr);
	}

	$("#req_user_searchType").change(function(){
		if($(this).val()=="1")
		{
			$("#req_user_month").css("display", "none");
		}
		else
		{
			$("#req_user_month").css("display", "block");
		}
	});
});

function byUserSub()
{
jQuery(function($) {
	hideObj("analysisUploadMsg");
	if($("#editName").val().length>0)
	{
		$("#byUserForm").submit();
	}
	else
	{
		showMsgObj('analysisUploadMsg', '请选择警员', 2);
	}
});	
}

/**
 * 用户选择
 * @param assignmentId 
 * @param assignmentName 
 * @param dialogTitle 弹出层的标题
 */
function userChoose(assignmentId, assignmentName, dialogTitle, onlyRoot)
{
jQuery(function($) {
	$.weeboxs.open('#userChooseDiv', {title:dialogTitle, contentType:'selector', width:'600', height:'450'});
});
}
function closeDialog()
{
	$('#closeDialog').click();
}
</script>
<script>
function showFrame(frameIndex)
{
	hideObj('frame_1',0);
	hideObj('frame_2',0);
	hideObj('frame_3',0);
	showObj('frame_'+frameIndex);
}
</script>
<iframe id="frame_1" name="newReport" src="" width="916px" height="610px" frameborder="0" scrolling="no"></iframe>
<iframe style="display:none" id="frame_2" name="rootTree" src="" width="916px" height="410px" frameborder="0" scrolling="no"></iframe>
<iframe style="display:none" id="frame_3" name="childTree" src="" width="916px" height="410px" frameborder="0" scrolling="no"></iframe>
<iframe style="display:none" id="frame_4" name="personal" src="" width="916px" height="410px" frameborder="0" scrolling="no"></iframe>
						<div class=" mt_10 pb_200">
							
						</div>
					</div>
				</div>
		</div>
	</div>
<jsp:include page="common/footer.jsp" />
<script type="text/javascript" src="js/all.js"></script>
</body>
</html>
