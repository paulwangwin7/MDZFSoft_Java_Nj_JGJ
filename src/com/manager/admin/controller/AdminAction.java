package com.manager.admin.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.manager.admin.dao.AdminDAO;
import com.manager.admin.dao.LogDAO;
import com.manager.admin.dao.SysDAO;
import com.manager.pub.bean.AdminInfoForm;
import com.manager.pub.bean.InformationFrameForm;
import com.manager.pub.bean.JspForm;
import com.manager.pub.bean.LogForm;
import com.manager.pub.bean.RemarkForm;
import com.manager.pub.bean.RoleForm;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.TreeForm;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.Constants;
import com.manager.pub.util.DateUtils;

public class AdminAction extends DispatchAction {

	private AdminDAO adminDAO;
	private SysDAO sysDAO;
	private LogDAO logDAO;

	private JspForm jspForm;
	private LogForm logForm;
	private InformationFrameForm informationFrameForm;
	private String frame_information = "frame_information";

	private AdminInfoForm adminInfoForm = null;

	/**
	 * 管理员登录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String form_loginName = request.getParameter("loginName") == null ? ""
				: request.getParameter("loginName");// 管理员账号 来自用户提交的表单
		String form_loginPswd = request.getParameter("loginPswd") == null ? ""
				: request.getParameter("loginPswd");// 管理员密码 来自用户提交的表单
		String form_checkCode = request.getParameter("checkCode") == null ? ""
				: request.getParameter("checkCode");// 登录验证码 来自用户提交的表单
		String form_userIP = request.getRemoteAddr().toString();// 登录ip地址
																// 来自用户request请求
		String sessionCheckCode = request.getSession()
				.getAttribute("checkCode").toString();// 从session中获取的登录验证码
		boolean formSubmitSuccess = false;// 判断此次用户提交行为是否正确
		// --step1 确认验证码是否正确
		if (!sessionCheckCode.equals(form_checkCode)) {
			jspForm = new JspForm("", "请确认您输入的验证码是否正确~", true, false, "",
					false, "", "", "");
		}
		// --step2 判断用户名和密码是否正确
		else if (!form_loginName.equals(SystemConfig.getSystemConfig()
				.getSysLoginName())
				|| !form_loginPswd.equals(SystemConfig.getSystemConfig()
						.getSysLoginPswd())) {
			jspForm = new JspForm("", "您输入的用户名或密码错误~", true, false, "", false,
					"", "", "");
		}
		// --step3 判断是否要ip地址验证
		else if (SystemConfig.getSystemConfig().getLoginIPFilter().equals("Y")) {
			if (!ipCanLogin(form_userIP)) {
				jspForm = new JspForm("", "您使用的ip不能通过~", true, false, "",
						false, "", "", "");
			} else {
				formSubmitSuccess = true;
			}
		} else {
			formSubmitSuccess = true;
		}
		if (formSubmitSuccess) {
			jspForm = new JspForm("", "登录成功~", false, true, "admin_index.jsp",
					false, "", "", "");
			adminInfoForm = new AdminInfoForm();
			adminInfoForm.setLoginName(form_loginName);
			adminInfoForm.setLoginPswd(form_loginPswd);
			adminInfoForm.setLoginIP(form_userIP);
			adminInfoForm.setLoginTime(DateUtils.getChar14());
			request.getSession().setAttribute(Constants.SESSION_ADMININFO,
					adminInfoForm);
		}
		request.setAttribute(Constants.JSP_MESSAGE, jspForm);
		return mapping.findForward("login_information");
	}

	/**
	 * 角色管理首页 -- 查询角色列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			request.setAttribute(Constants.JSP_ROLE_LIST, adminDAO
					.queryRoleList());
			return mapping.findForward("roleManager");
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 角色管理首页 -- 角色添加前
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManagerToAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (ifLogin(request)) {
			request.setAttribute(Constants.JSP_ROLE_MANAGER_ACTION,
					"roleManagerAdd");
			request.setAttribute(Constants.JSP_MENU_LIST, adminDAO
					.queryMenuAndUrl());
			return mapping.findForward("roleManagerToAdd");
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 角色管理首页 -- 角色添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManagerAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			String roleName = request.getParameter("roleName");// 角色名称
			String roleDesc = request.getParameter("roleDesc");// 角色描述
			String[] urlId = request.getParameterValues("urlId");// 权限对应的功能url项
			String urlIdList = "";
			for (int i = 0; i < urlId.length; i++) {
				urlIdList += urlId[i] + ",";
			}
			String roleState = "A";// 角色状态
			String createUser = "0";// 创建人id
			// String createTime = request.getParameter("roleName");//创建时间
			String treeId = "0";// 所属部门
			RoleForm roleForm = new RoleForm();
			roleForm.setRoleName(roleName);
			roleForm.setRoleDesc(roleDesc);
			roleForm.setRoleState(roleState);
			roleForm.setCreateUser(createUser);
			roleForm.setTreeId(treeId);
			roleForm.setUrlIdList(urlIdList);
			int actionResult = adminDAO.roleAdd(roleForm);
			switch (actionResult)// 0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
			{
			case 0: {
				informationFrameForm = new InformationFrameForm("添加成功~",
						"adminAction.do?method=roleManager", "0", "tab_jsgl",
						"角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-角色添加：" + roleName + " 被添加成功");
				break;
			}
			case 1: {
				informationFrameForm = new InformationFrameForm("添加失败 系统超时~",
						"", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-角色失败：系统超时");
				break;
			}
			case 2: {
				informationFrameForm = new InformationFrameForm(
						"添加失败 该角色出现重名~", "", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-角色失败：" + roleName + " 该角色出现重名");
				break;
			}
			default: {
				informationFrameForm = new InformationFrameForm("添加失败 系统超时~",
						"", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色失败：系统超时");
			}
			}
			logDAO.logAdd(logForm);
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 角色管理首页 -- 角色修改前
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManagerToMdf(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (ifLogin(request)) {
			String roleId = request.getParameter("roleId");
			RoleForm roleForm = adminDAO.roleQuery(roleId);
			if (roleForm == null) {
				informationFrameForm = new InformationFrameForm(
						"请确认您选择的角色是否正确~", "", "1", "tab_jsgl", "角色管理");
				request.setAttribute(Constants.JSP_MESSAGE,
						informationFrameForm);
				return mapping.findForward(frame_information);
			} else {
				request.setAttribute(Constants.JSP_ROLE_FORM, roleForm);
				request.setAttribute(Constants.JSP_MENU_LIST, adminDAO
						.queryMenuAndUrl());
			}
			request.setAttribute(Constants.JSP_ROLE_MANAGER_ACTION,
					"roleManagerMdf");
			return mapping.findForward("roleManagerToAdd");
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 角色管理首页 -- 角色修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManagerMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			long roleId = Long.parseLong(request.getParameter("roleId"));
			String roleName = request.getParameter("roleName");// 角色名称
			String roleDesc = request.getParameter("roleDesc");// 角色描述
			String[] urlId = request.getParameterValues("urlId");// 权限对应的功能url项
			String urlIdList = "";
			for (int i = 0; i < urlId.length; i++) {
				urlIdList += urlId[i] + ",";
			}
			String roleState = "A";// 角色状态
			String createUser = "0";// 创建人id
			String treeId = "0";// 所属部门
			RoleForm roleForm = new RoleForm();
			roleForm.setRoleId(roleId);
			roleForm.setRoleName(roleName);
			roleForm.setRoleDesc(roleDesc);
			roleForm.setRoleState(roleState);
			roleForm.setCreateUser(createUser);
			roleForm.setTreeId(treeId);
			roleForm.setUrlIdList(urlIdList);

			int actionResult = adminDAO.roleMdf(roleForm);
			switch (actionResult)// 0-修改成功；1-修改失败 系统超时~；2-该角色出现重名 请换个角色名称
			{
			case 0: {
				informationFrameForm = new InformationFrameForm("修改成功~",
						"adminAction.do?method=roleManager", "0", "tab_jsgl",
						"角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-修改成功：最新角色名称 " + roleName + " 修改成功");
				break;
			}
			case 1: {
				informationFrameForm = new InformationFrameForm("修改失败 系统超时~",
						"", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-修改失败：系统超时");
				break;
			}
			case 2: {
				informationFrameForm = new InformationFrameForm(
						"修改失败 该角色出现重名~", "", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-修改失败：最新角色名称 " + roleName + " 该角色出现重名");
				break;
			}
			default: {
				informationFrameForm = new InformationFrameForm("修改失败 系统超时~",
						"", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-修改失败：系统超时");
			}
			}
			logDAO.logAdd(logForm);
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 角色管理首页 -- 角色删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManagerDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			String roleId = request.getParameter("roleManager_roleId");
			int actionResult = adminDAO.roleDel(roleId);
			switch (actionResult)// 0-删除成功；1-删除失败 系统超时~；2-删除失败 还有其他用户正在使用该角色
			{
			case 0: {
				informationFrameForm = new InformationFrameForm("删除成功~",
						"adminAction.do?method=roleManager", "0", "tab_jsgl",
						"角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-删除角色：删除成功");
				break;
			}
			case 1: {
				informationFrameForm = new InformationFrameForm("删除失败 系统超时~",
						"", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-删除角色：删除失败 系统超时");
				break;
			}
			case 2: {
				informationFrameForm = new InformationFrameForm(
						"删除失败 还有其他用户正在使用该角色~", "", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-删除角色：删除失败 其他用户正在使用该角色");
				break;
			}
			default: {
				informationFrameForm = new InformationFrameForm("删除失败 系统超时~",
						"", "1", "tab_jsgl", "角色管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"角色管理-删除角色：删除失败 系统超时");
			}
			}
			logDAO.logAdd(logForm);
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 总部门名称修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeRootMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			String remarkContent = request.getParameter("remark_content");
			String remarkId = request.getParameter("remark_id");
			RemarkForm remarkForm = new RemarkForm();
			remarkForm.setRemarkContent(remarkContent);
			remarkForm.setRemarkId(Long.parseLong(remarkId));

			int actionResult = sysDAO.remarkFormMdf(remarkForm);
			switch (actionResult)// 0-修改成功；1-修改失败 系统超时~
			{
			case 0: {
				informationFrameForm = new InformationFrameForm("修改成功~",
						"adminAction.do?method=treeManager", "0", "tab_bmgl",
						"部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"总部门名称修改：最新总部名称 " + remarkContent + " 修改成功");
				break;
			}
			case 1: {
				informationFrameForm = new InformationFrameForm("修改失败 系统超时~",
						"", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"总部门名称修改：修改失败 系统超时");
				break;
			}
			default: {
				informationFrameForm = new InformationFrameForm("添加失败 系统超时~",
						"", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"总部门名称修改：修改失败 系统超时");
			}
			}
			logDAO.logAdd(logForm);
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 部门管理首页 -- 查询部门列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			request.setAttribute("tree_root_list", sysDAO
					.remarkFormList("FRAME_TREE"));
			request.setAttribute(Constants.JSP_TREE_LIST, adminDAO
					.queryTotalTreeList());
			return mapping.findForward("treeManager");
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 部门管理首页 -- 部门添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeManagerAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			String treeName = request.getParameter("treeName");// 部门名称
			String treeDesc = request.getParameter("treeDesc");// 部门描述
			long parentTreeId = Long.parseLong(request.getParameter("treeId"));// 上级部门id
			String orderBy = request.getParameter("orderBy");// 排序
			TreeForm treeForm = new TreeForm();
			treeForm.setTreeName(treeName);
			treeForm.setTreeDesc(treeDesc);
			treeForm.setTreeState("A");
			treeForm.setCreateUser(0);
			treeForm.setParentTreeId(parentTreeId);
			treeForm.setOrderBy(orderBy);

			int actionResult = adminDAO.treeAdd(treeForm);
			switch (actionResult)// 0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
			{
			case 0: {
				informationFrameForm = new InformationFrameForm("添加成功~",
						"adminAction.do?method=treeManager", "0", "tab_bmgl",
						"部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门添加： " + (parentTreeId > 0 ? "二级部门" : "一级部门")
								+ " " + treeName + " 添加成功");
				break;
			}
			case 1: {
				informationFrameForm = new InformationFrameForm("添加失败 系统超时~",
						"", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门添加： 添加失败 系统超时");
				break;
			}
			case 2: {
				informationFrameForm = new InformationFrameForm(
						"添加失败 该角色出现重名~", "", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门添加： 添加失败 部门名称重名");
				break;
			}
			default: {
				informationFrameForm = new InformationFrameForm("添加失败 系统超时~",
						"", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门添加： 添加失败 系统超时");
			}
			}
			logDAO.logAdd(logForm);
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 部门管理首页 -- 部门修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeManagerMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			long treeId = Long.parseLong(request.getParameter("treeId"));
			String treeName = request.getParameter("treeName");// 部门名称
			String treeDesc = request.getParameter("treeDesc");// 部门描述
			long parentTreeId = Long.parseLong(request
					.getParameter("parentTreeId"));// 上级部门id
			String orderBy = request.getParameter("orderBy");// 排序
			TreeForm treeForm = new TreeForm();
			treeForm.setTreeId(treeId);
			treeForm.setTreeName(treeName);
			treeForm.setTreeDesc(treeDesc);
			treeForm.setTreeState("A");
			treeForm.setCreateUser(0);
			treeForm.setParentTreeId(parentTreeId);
			treeForm.setOrderBy(orderBy);

			int actionResult = adminDAO.treeMdf(treeForm);
			switch (actionResult)// 0-修改成功；1-修改失败 系统超时~；2-该角色出现重名 请换个角色名称
			{
			case 0: {
				informationFrameForm = new InformationFrameForm("修改成功~",
						"adminAction.do?method=treeManager", "0", "tab_bmgl",
						"部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门修改： " + (parentTreeId > 0 ? "二级部门" : "一级部门")
								+ " " + treeName + " 修改成功");
				break;
			}
			case 1: {
				informationFrameForm = new InformationFrameForm("修改失败 系统超时~",
						"", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门修改： 修改失败 系统超时");
				break;
			}
			case 2: {
				informationFrameForm = new InformationFrameForm(
						"修改失败 该角色出现重名~", "", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门修改： 修改失败 部门名称重名");
				break;
			}
			default: {
				informationFrameForm = new InformationFrameForm("修改失败 系统超时~",
						"", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门修改： 修改失败 系统超时");
			}
			}
			logDAO.logAdd(logForm);
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 部门管理首页 -- 部门删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeManagerDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			String treeId = request.getParameter("treeId");

			int actionResult = adminDAO.treeDel(treeId);
			switch (actionResult)// 0-修改成功；1-修改失败 系统超时~；2-删除失败 还有其他用户正在使用该角色
			{
			case 0: {
				informationFrameForm = new InformationFrameForm("删除成功~",
						"adminAction.do?method=treeManager", "0", "tab_bmgl",
						"部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门删除： 删除成功");
				break;
			}
			case 1: {
				informationFrameForm = new InformationFrameForm("删除失败 系统超时~",
						"", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门删除： 删除失败 系统超时");
				break;
			}
			case 2: {
				informationFrameForm = new InformationFrameForm(
						"删除失败 还有其他用户正在使用该部门~", "", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门删除： 删除失败 有其他用户正在使用该部门");
				break;
			}
			default: {
				informationFrameForm = new InformationFrameForm("删除失败 系统超时~",
						"", "1", "tab_bmgl", "部门管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"部门管理首页-部门删除： 删除失败 系统超时");
			}
			}
			logDAO.logAdd(logForm);
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 用户管理首页 -- 删除用户
	 */
	public ActionForward userManagerDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			Long user_id = Long.parseLong(request
					.getParameter("userManager_userId") == null ? "" : request
					.getParameter("userManager_userId"));

			int actionResult = adminDAO.userManagerDel(user_id);
			switch (actionResult) {
			case 0: {
				informationFrameForm = new InformationFrameForm("删除成功~",
						"adminAction.do?method=userManager", "0", "tab_yhcx",
						"用户管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"用户管理-删除用户： 用户id为 " + user_id + " 的用户被删除成功");
				break;
			}
			case 1: {
				informationFrameForm = new InformationFrameForm("删除失败 系统超时~",
						"", "1", "tab_yhcx", "用户管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"用户管理-删除用户：删除失败 系统超时");
				break;
			}
			default: {
				informationFrameForm = new InformationFrameForm("删除失败 系统超时~",
						"", "1", "tab_yhcx", "用户管理");
				logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
						"用户管理-删除用户：删除失败 系统超时");
			}
			}
			logDAO.logAdd(logForm);
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 用户管理首页 -- 查询用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (ifLogin(request)) {
			UserForm userForm = new UserForm();
			userForm.setUserName(request.getParameter("userNameStr"));
			userForm.setUserCode(request.getParameter("userCodeStr"));
			request.setAttribute(Constants.JSP_USER_LIST, adminDAO
					.queryUserListByUserForm(userForm));
			return mapping.findForward("userManager");
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
	}

	/**
	 * 用户管理首页 -- 注册选择部门列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("tree_root_list", sysDAO
				.remarkFormList("FRAME_TREE"));
		request.setAttribute(Constants.JSP_TREE_LIST, adminDAO
				.queryTotalTreeList());
		return mapping.findForward("treeSelect");
	}

	/**
	 * 用户选择 -- 选择角色列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(Constants.JSP_ROLE_LIST, adminDAO.queryRoleList());
		return mapping.findForward("roleSelect");
	}

	/**
	 * 用户管理首页 -- 查询用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm = new UserForm();
		userForm.setUserName(request.getParameter("user_name") == null ? ""
				: request.getParameter("user_name"));
		userForm.setUserCode(request.getParameter("user_code") == null ? ""
				: request.getParameter("user_code"));
		request.setAttribute(Constants.JSP_USER_LIST, adminDAO
				.queryUserListByUserForm(userForm));
		return mapping.findForward("userSelect");
	}

	/**
	 * 用户管理首页 -- 添加（注册）用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userManagerToAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute(Constants.JSP_USER_MANAGER_ACTION,
				"userManagerAdd");
		return mapping.findForward("userManagerToAdd");
	}

	/**
	 * 用户管理首页 -- 添加（注册）用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userManagerAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm = new UserForm();
		userForm.setLoginName(request.getParameter("loginName"));
		userForm.setLoginPswd(request.getParameter("loginPswd"));
		userForm.setUserName(request.getParameter("userName"));
		userForm.setUserCode(request.getParameter("userCode"));
		userForm.setSex(request.getParameter("sex"));
		userForm.setUserIdCard(request.getParameter("userIdCard"));
		userForm.setCard_typeId(0);
		userForm.setCardCode("");
		userForm.setTreeId(Long.parseLong(request.getParameter("treeId")));
		userForm.setRoleId(Long.parseLong(request.getParameter("roleId")));
		userForm.setCreateTime(request.getParameter("createTime"));
		userForm.setUserState(request.getParameter("userState"));

		int actionResult = adminDAO.userRegister(userForm);
		switch (actionResult)// 0-注册成功；1-注册失败 系统超时~；2-注册失败 登录名称重复
		{
		case 0: {
			informationFrameForm = new InformationFrameForm("注册成功~",
					"adminAction.do?method=userManager", "0", "tab_yhcx",
					"用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-注册用户： 登录名 " + userForm.getLoginName() + " 注册成功");
			break;
		}
		case 1: {
			informationFrameForm = new InformationFrameForm("注册失败 系统超时~", "",
					"1", "tab_yhcx", "用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-注册用户： 注册失败 系统超时");
			break;
		}
		case 2: {
			informationFrameForm = new InformationFrameForm("注册失败 登录名称重复~", "",
					"1", "tab_yhcx", "用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-注册用户： 注册失败 登录名称重复");
			break;
		}
		default: {
			informationFrameForm = new InformationFrameForm("注册失败 系统超时~", "",
					"1", "tab_yhcx", "用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-注册用户： 注册失败 系统超时");
		}
		}
		logDAO.logAdd(logForm);
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	/**
	 * 用户管理首页 -- 修改用户信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userManagerToMdf(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId") == null ? "" : request
				.getParameter("userId");
		request
				.setAttribute("userForm", adminDAO
						.queryUserFormByUserId(userId));
		request.setAttribute(Constants.JSP_USER_MANAGER_ACTION,
				"userManagerMdf");
		return mapping.findForward("userManagerToAdd");
	}

	/**
	 * 用户管理首页 -- 用户信息修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userManagerMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm = new UserForm();
		userForm.setUserId(Long.parseLong(request.getParameter("userId")));
		userForm.setLoginName(request.getParameter("loginName"));
		userForm.setLoginPswd(request.getParameter("loginPswd"));
		userForm.setUserName(request.getParameter("userName"));
		userForm.setUserCode(request.getParameter("userCode"));
		userForm.setSex(request.getParameter("sex"));
		userForm.setUserIdCard(request.getParameter("userIdCard"));
		userForm.setCard_typeId(0);
		userForm.setCardCode("");
		userForm.setTreeId(Long.parseLong(request.getParameter("treeId")));
		userForm.setRoleId(Long.parseLong(request.getParameter("roleId")));
		userForm.setCreateTime(request.getParameter("createTime"));
		userForm.setUserState(request.getParameter("userState"));

		int actionResult = adminDAO.userModify(userForm);
		switch (actionResult)// 0-修改成功；1-修改失败 系统超时~；2-修改失败 登录名称重复
		{
		case 0: {
			informationFrameForm = new InformationFrameForm("修改成功~",
					"adminAction.do?method=userManager", "0", "tab_yhcx",
					"用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-用户信息修改： 登录名 " + userForm.getLoginName() + " 修改成功");
			break;
		}
		case 1: {
			informationFrameForm = new InformationFrameForm("修改失败 系统超时~", "",
					"1", "tab_yhcx", "用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-用户信息修改： 修改失败 系统超时");
			break;
		}
		case 2: {
			informationFrameForm = new InformationFrameForm("修改失败 登录名称重复~", "",
					"1", "tab_yhcx", "用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-用户信息修改： 修改失败 登录名称重名");
			break;
		}
		default: {
			informationFrameForm = new InformationFrameForm("修改失败 系统超时~", "",
					"1", "tab_yhcx", "用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-用户信息修改： 修改失败 系统超时");
		}
		}
		logDAO.logAdd(logForm);
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	/**
	 * 管理员退出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute(Constants.SESSION_ADMININFO);
		return mapping.findForward("admin_login");
	}

	/**
	 * 上传日志查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("upload_log");
	}

	/**
	 * 上传日志查询 —— 大队查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadLog_byTree1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int tree1_searchType = Integer.parseInt(request
				.getParameter("tree1_searchType"));
		String tree1_year = request.getParameter("tree1_year");
		String tree1_month = request.getParameter("tree1_month");
		switch (tree1_searchType) {
		case 1:
			request.setAttribute("uploadLogList_ByTree1", adminDAO
					.uploadImgQuery_ByTree1(tree1_searchType, tree1_year));
			break;
		case 2:
			request.setAttribute("uploadLogList_ByTree1", adminDAO
					.uploadImgQuery_ByTree1(tree1_searchType, tree1_year
							+ tree1_month));
			break;
		default:
			;
		}
		return mapping.findForward("result_uploadlogimg_tree1");
	}

	/**
	 * 上传日志查询 —— 警员查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadManager_byUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int user_searchType = Integer.parseInt(request
				.getParameter("user_searchType"));
		String user_id = request.getParameter("user_id");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		request.setAttribute(Constants.JSP_UPLOAD_LIST, adminDAO
				.uploadManagerQuery_ByUser(user_id, beginTime, endTime));
		return mapping.findForward("uploadManager");
	}

	/**
	 * 操作日志查询 —— 模糊查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @param loginName
	 */
	public ActionForward actionLogManagerQuery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String userCode = request.getParameter("userCode") == null ? ""
				: request.getParameter("userCode");
		String beginTime = request.getParameter("beginTime") == null ? ""
				: request.getParameter("beginTime");
		String endTime = request.getParameter("endTime") == null ? "" : request
				.getParameter("endTime");
		if (!beginTime.equals("") && !endTime.equals("")) {
			request.getSession().setAttribute("log_userCode", userCode);
			request.getSession().setAttribute("log_beginTime", beginTime);
			request.getSession().setAttribute("log_endTime", endTime);
			request.getSession().setAttribute(Constants.JSP_LOG_FORM,
					logDAO.logQuery(userCode, beginTime, endTime, "0", 1, 10));
			informationFrameForm = new InformationFrameForm("查询完成~",
					"adminAction.do?method=actionLogManager", "0", "tab_czrz",
					"操作日志");
		} else {
			informationFrameForm = new InformationFrameForm("查询失败 时间参数不正确~",
					"", "1", "tab_czrz", "操作日志");
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	/**
	 * 操作日志查询 —— 模糊显示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @param loginName
	 */
	public ActionForward actionLogManager(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("actionLogManager");
	}

	/**
	 * 过期文件删除 —— 列表显示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @param loginName
	 */
	public ActionForward fileList_expired(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String expiredTime = DateUtils.rollHour(DateUtils.getChar14(), -Integer
				.parseInt(SystemConfig.getSystemConfig().getExpiredDay()) * 24);
		request.setAttribute(Constants.JSP_UPLOAD_LIST, adminDAO
				.uploadManagerQuery_expired(expiredTime.substring(0, 8)));
		return mapping.findForward("expiredFileList");
	}

	/**
	 * 过期文件删除 —— 文件列表删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @param loginName
	 */
	public ActionForward fileDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String[] expired_uploadId = request
				.getParameterValues("expired_uploadId");
		List l = adminDAO.uploadFileStatsList(expired_uploadId);
		List<UploadForm> upForm1 = (List<UploadForm>) (l.get(0));// 重要性
		List<UploadForm> upForm2 = (List<UploadForm>) (l.get(1));// 非重要性
		adminDAO.uploadFileDel(upForm2);// 首先删除非重要性的文件
		if (upForm1.size() > 0) {
			request.setAttribute(Constants.JSP_UPLOAD_LIST, upForm1);
			informationFrameForm = new InformationFrameForm(
					"删除完成 但是其中含有加★文件没有删除~",
					"adminAction.do?method=fileList_expired", "0", "tab_wjsc",
					"文件删除");
		} else {
			informationFrameForm = new InformationFrameForm("删除完成~",
					"adminAction.do?method=fileList_expired", "0", "tab_wjsc",
					"文件删除");
		}
		userLog(request, "过期文件删除 —— 文件列表删除");
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	/**
	 * 过期文件删除 —— 重要性文件列表删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @param loginName
	 */
	public ActionForward fileDel_2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String[] expired_uploadId = request
				.getParameterValues("expired_uploadId");
		List l = adminDAO.uploadFileStatsList(expired_uploadId);
		List<UploadForm> upForm1 = (List<UploadForm>) (l.get(0));// 重要性
		List<UploadForm> upForm2 = (List<UploadForm>) (l.get(1));// 非重要性
		adminDAO.uploadFileDel(upForm1);// 首先删除重要性
		adminDAO.uploadFileDel(upForm2);// 首先删除非重要性的文件
		informationFrameForm = new InformationFrameForm("删除完成~",
				"adminAction.do?method=fileList_expired", "0", "tab_wjsc",
				"文件删除");
		userLog(request, "过期文件删除 —— 文件列表（含重要性）删除");
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	/**
	 * 用户管理首页 -- 用户密码重置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userResetPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId") == null ? "" : request
				.getParameter("userId");
		if (sysDAO.userResetPassword(userId, SystemConfig.getSystemConfig()
				.getResetPswd())) {
			informationFrameForm = new InformationFrameForm("密码重置成功~", "", "2",
					"tab_yhcx", "用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-用户密码重置：账号id " + userId + " 密码重置成功");
		} else {
			informationFrameForm = new InformationFrameForm("密码修改失败 系统超时~", "",
					"1", "tab_yhcx", "用户查询");
			logForm = new LogForm(0, "admin", 0, "", 0, "系统超级管理员",
					"用户管理-用户密码重置：账号id " + userId + " 密码重置失败~");
		}
		logDAO.logAdd(logForm);
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	/**
	 * 文件管理首页 -- 文件查看
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadFileShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean uploadSuccess = false;
		if (ifLogin(request))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			String treeId = request.getParameter("treeId") == null ? ""
					: request.getParameter("treeId");
			String beginTime = request.getParameter("beginTime") == null ? ""
					: request.getParameter("beginTime");
			String endTime = request.getParameter("endTime") == null ? ""
					: request.getParameter("endTime");
			String fileStats = request.getParameter("fileStats") == null ? ""
					: request.getParameter("fileStats");
			String fileRemark = request.getParameter("fileRemark") == null ? ""
					: request.getParameter("fileRemark");
			if ((!beginTime.equals("") && !endTime.equals(""))
					|| (beginTime.equals("") && endTime.equals(""))) {
				if (!beginTime.equals("") && !endTime.equals("")) {
					String[] beginTimeArr = beginTime.split("-");
					beginTime = beginTimeArr[0]
							+ ""
							+ (Integer.parseInt(beginTimeArr[1]) < 10 ? ("0" + beginTimeArr[1])
									: beginTimeArr[1])
							+ ""
							+ (Integer.parseInt(beginTimeArr[2]) < 10 ? ("0" + beginTimeArr[2])
									: beginTimeArr[2]);
					String[] endTimeArr = endTime.split("-");
					endTime = endTimeArr[0]
							+ ""
							+ (Integer.parseInt(endTimeArr[1]) < 10 ? ("0" + endTimeArr[1])
									: endTimeArr[1])
							+ ""
							+ (Integer.parseInt(endTimeArr[2]) < 10 ? ("0" + endTimeArr[2])
									: endTimeArr[2]);
				}
				String uploadUserId = request.getParameter("uploadUserId") == null ? ""
						: request.getParameter("uploadUserId");

				request.setAttribute(Constants.JSP_UPLOAD_LIST, adminDAO
						.uploadManagerQuery_ByTree(treeId + "", beginTime,
								endTime, uploadUserId, fileStats, fileRemark));
				return mapping.findForward("admin_uploadFileShow");

			} else {
				informationFrameForm = new InformationFrameForm("您选择的日期范围有误~",
						"", "1", "tab_wjck", "文件查看");
			}
		} else {
			informationFrameForm = new InformationFrameForm(
					"您的登录已超时，请刷新后重新登录~", "", "1", "", "");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	/**
	 * 判断管理员ip是否有权登录
	 * 
	 * @param userIP
	 *            登录的ip地址
	 * @return
	 */
	public boolean ipCanLogin(String userIP) {
		for (int i = 0; i < SystemConfig.getSystemConfig().getLoginIPList()
				.size(); i++) {
			if (userIP.equals(SystemConfig.getSystemConfig().getLoginIPList()
					.get(i).toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 系统管理员主页
	 */
	public ActionForward adminMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("adminMain");
	}

	public boolean ifLogin(HttpServletRequest request) {
		if (request.getSession().getAttribute(Constants.SESSION_ADMININFO) != null) {
			return true;
		}
		return false;
	}

	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	public void setSysDAO(SysDAO sysDAO) {
		this.sysDAO = sysDAO;
	}

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	/**
	 * 用户操作日志添加
	 * @param request
	 * @param logDesc
	 * @return
	 */
	public ActionForward userLog(HttpServletRequest request, String logDesc) {
		UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		LogForm logForm = new LogForm(userForm.getUserId(), userForm.getUserCode(), userForm.getTreeId(), userForm.getTreeNameStr(), userForm.getRoleId(), userForm.getRoleNameStr(), logDesc, request.getRemoteAddr());
		logDAO.logAdd(logForm);
		return null;
	}
}
