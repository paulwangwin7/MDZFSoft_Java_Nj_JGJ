<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.manager.pub.bean.*, com.manager.pub.util.*" %>
<jsp:include page="common/tag.jsp" />
<script>
/**
 * 显示二级部门
 * @param parentTreeId 一级部门id
 */
function showChildTree(parentTreeId)
{
	jQuery(function($) {
		if($("#"+parentTreeId).css("display")=="none")
		{
			showObj(parentTreeId, '');
		}
		else
		{
			hideObj(parentTreeId, '');
		}
	});
}

var nowShowAll = true;
/**
 * 显示所有部门
 */
function showAllTree()
{
	jQuery(function($) {
		$("#treeManagerTable table").each(function() {
			if(!nowShowAll)
			{
				$(this).css("display","block");
			}
			else
			{
				$(this).css("display","none");
			}
		});
	});
	if(!nowShowAll)
	{
		nowShowAll = true;
	}
	else
	{
		nowShowAll = false;
	}
}
</script>

<div class="error msg" id="treeSelectMsg" style="display:none;" onclick="hideObj('treeSelectMsg')">Message if login failed</div>
				<table class="gtable detailtable" id="treeManagerTable" style="width:560px">
				<tbody>
					<tr>
						<th>
						<a href="###" onclick="showAllTree()">交警大队总部</a>
						</th>
					</tr>
<%
	String tree_root_name = "总部";
	String tree_root_id = "0";
	if(request.getAttribute("tree_root_list")!=null && ((List)request.getAttribute("tree_root_list")).size()>0)
	{
		tree_root_name = ((RemarkForm)((((List)request.getAttribute("tree_root_list")).get(0)))).getRemarkContent();
		tree_root_id = ((RemarkForm)((((List)request.getAttribute("tree_root_list")).get(0)))).getRemarkId()+"";
	}
	List list_totalTree = (List)request.getAttribute(Constants.JSP_TREE_LIST);
	if(list_totalTree!=null && list_totalTree.size()>0)
	{
		for(int i=0; i<list_totalTree.size(); i++)//一级部门循环
		{
			TreeForm rootTreeForm = (TreeForm)((List)(list_totalTree.get(i))).get(0);
			List<TreeForm> treeFormList = ((List<TreeForm>)((List)(list_totalTree.get(i))).get(1));
%>
					<tr>
						<td>
							<span style="width:20px">&nbsp;</span>
							<input type="radio" value="<%=rootTreeForm.getTreeId() %>" name="treeid" thisName="<%=rootTreeForm.getTreeName() %>" class="selectTreeId"/>
							<a href="###" onclick="showChildTree('tb_<%=rootTreeForm.getTreeId() %>')"><%=rootTreeForm.getTreeName() %></a><br/>
							<table id="tb_<%=rootTreeForm.getTreeId() %>" style="display:block">
<%
			for(int j=0; j<treeFormList.size(); j++)//二级部门循环
			{
%>
								<tr>
									<td width="20px">&nbsp;</td>
									<td>
									<%
										if(request.getParameter("onlyRoot")==null) {
									%>
									<input type="radio" value="<%=treeFormList.get(j).getTreeId() %>" name="treeid" thisName="<%=treeFormList.get(j).getTreeName() %>" class="selectTreeId"/>
									<%
										}
									%>
									<%=treeFormList.get(j).getTreeName() %>
									</td>
								</tr>
<%
			}
%>
							</table>
						</td>
					</tr>
<%
		}
	}
%>
				</tbody>
				</table>
				<input type="button" class="button" onclick="thisSelectTree()" value="确认选择">
<script>
var staticTreeName = "";//全局静态部门名称
var assignmentId = "<%=request.getParameter("assignmentId")==null?"":request.getParameter("assignmentId")%>";
var assignmentName = "<%=request.getParameter("assignmentName")==null?"":request.getParameter("assignmentName")%>";

jQuery(function($) {//radio选择触发事件
	$(".selectTreeId").click(function(){
		staticTreeName = $(this).attr("thisName");
	});
});

/**
 * 确认选择部门
 */
function thisSelectTree()
{
jQuery(function($) {
	hideObj("treeSelectMsg", 0);
	if(getArray("selectTreeId").length<=0)
	{
		showMsgObj('treeSelectMsg', '请选择一个部门', 2);
	}
	else
	{
		var obj = new Object();
		$("#"+assignmentId).val(getJqueryArrayStr("selectTreeId"));
		$("#"+assignmentName).val(staticTreeName);
		closeSelectTree();
	}
});
}
</script>