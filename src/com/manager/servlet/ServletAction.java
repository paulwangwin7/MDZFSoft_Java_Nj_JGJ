package com.manager.servlet;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manager.admin.dao.AdminDAO;
import com.manager.admin.dao.LogDAO;
import com.manager.admin.dao.SysDAO;
import com.manager.player.dao.UserDAO;
import com.manager.pub.bean.AdminInfoForm;
import com.manager.pub.bean.LogForm;
import com.manager.pub.bean.MonitorInfoBean;
import com.manager.pub.bean.NoticeForm;
import com.manager.pub.bean.Page;
import com.manager.pub.bean.Result;
import com.manager.pub.bean.RoleForm;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.TreeForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.Constants;
import com.manager.pub.util.DateUtils;
import com.manager.pub.util.HtmlUtils;
import com.manager.pub.util.SysMonitor_windows;
import com.njmd.bo.FrameMenuBO;
import com.njmd.bo.FrameRoleBO;
import com.njmd.bo.FrameServerInfoBO;
import com.njmd.bo.FrameTreeBO;
import com.njmd.bo.FrameUploadBO;
import com.njmd.bo.FrameUserBO;

public class ServletAction extends DispatchAction{
	private SysDAO sysDAO;
	private AdminDAO adminDAO;
	private UserDAO userDAO;
	private LogDAO logDAO;
	private FrameUserBO frameUserBO;
	private FrameRoleBO frameRoleBO;
	private FrameTreeBO frameTreeBO;
	private FrameServerInfoBO frameServerInfoBO;
	private FrameUploadBO frameUploadBO;
	private FrameMenuBO frameMenuBO;

	/**
	 * 用户登录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		String form_loginName = request.getParameter("loginName")==null?"":request.getParameter("loginName");//用户账号 来自用户提交的表单
		String form_loginPswd = request.getParameter("loginPswd")==null?"":request.getParameter("loginPswd");//用户密码 来自用户提交的表单
		String form_checkCode = request.getParameter("checkCode")==null?"":request.getParameter("checkCode");//登录验证码 来自用户提交的表单
		String sessionCheckCode = request.getSession().getAttribute("checkCode").toString();//从session中获取的登录验证码

		//--step1 确认验证码是否正确
		if(!sessionCheckCode.equals(form_checkCode))
		{
			result.setRetCode(Constants.ACTION_FAILED);
			result.setMsg("请确认您输入的验证码是否正确~");
		}
		//--step2 判断用户名和密码是否正确
		else
		{
			UserForm user = new UserForm();
			user.setLoginName(form_loginName);
			user.setLoginPswd(form_loginPswd);
			UserForm userForm = frameUserBO.userLogin(user);
			if(userForm!=null)//用户账号密码正确
			{
				//--step3 判断用户状态是否正常
				if(userForm.getUserState().equals("A"))//用户状态正常
				{
					if(userForm.getRoleType().equals("")) {//普通用户
						RoleForm role = new RoleForm();
						role.setRoleId(userForm.getRoleId());
						RoleForm roleForm = frameRoleBO.roleDetail(role);
						// 333
	//					List list_urlForm = userDAO.queryMenuAndUrlByRoleId(roleForm.getUrlIdList().split(","));
						
						request.getSession().setAttribute(Constants.SESSION_USER_FORM, userForm);//将该用户的userForm信息保存在session中
						request.getSession().setAttribute(Constants.SESSION_ROLE_FORM, roleForm);//将该用户的roleForm信息保存在session中
						request.getSession().setAttribute(Constants.SESSION_URL_LIST, frameMenuBO.queryMenuAndUrlByUrlIds(roleForm.getUrlIdList().split(",")));//将该用户的urlList信息保存在session中
						result.setMsg("登录成功~");
						userLog(request, "用户登录");
					} else {//观察者
						RoleForm roleForm = new RoleForm();
						roleForm.setRoleName("交管局领导");
						roleForm.setTreeId("0");
						roleForm.setUrlIdList("6,7,8");
						request.getSession().setAttribute(Constants.SESSION_ROLE_FORM, roleForm);//将该用户的roleForm信息保存在session中
						request.getSession().setAttribute(Constants.SESSION_USER_FORM, userForm);//将该用户的userForm信息保存在session中
						request.getSession().setAttribute(Constants.SESSION_URL_LIST, frameMenuBO.queryMenuAndUrlByUrlIds(roleForm.getUrlIdList().split(",")));//将该用户的urlList信息保存在session中
						result.setMsg("登录成功~");
					}
				}
				else//用户状态不正常 未激活或已被锁定
				{
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("用户状态不正常，未激活或已被锁定~");
				}
			}
			else//用户账号或密码不正确
			{
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("您输入的用户名或密码错误~");
			}
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 系统管理员登录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward sysLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		String form_loginName = request.getParameter("loginName")==null?"":request.getParameter("loginName");//管理员账号 来自用户提交的表单
		String form_loginPswd = request.getParameter("loginPswd")==null?"":request.getParameter("loginPswd");//管理员密码 来自用户提交的表单
		String form_checkCode = request.getParameter("checkCode")==null?"":request.getParameter("checkCode");//登录验证码 来自用户提交的表单
		String form_userIP = request.getRemoteAddr().toString();//登录ip地址 来自用户request请求
		String sessionCheckCode = request.getSession().getAttribute("checkCode").toString();//从session中获取的登录验证码
		boolean formSubmitSuccess = false;//判断此次用户提交行为是否正确
		//--step1 确认验证码是否正确
		if(!sessionCheckCode.equals(form_checkCode))
		{
			result.setRetCode(Constants.ACTION_FAILED);
			result.setMsg("请确认您输入的验证码是否正确~");
		}
		//--step2 判断用户名和密码是否正确
		else if(!form_loginName.equals(SystemConfig.getSystemConfig().getSysLoginName()) || !form_loginPswd.equals(SystemConfig.getSystemConfig().getSysLoginPswd()))
		{
			result.setRetCode(Constants.ACTION_FAILED);
			result.setMsg("您输入的用户名或密码错误~");
		}
		//--step3 判断是否要ip地址验证
		else if(SystemConfig.getSystemConfig().getLoginIPFilter().equals("Y"))
		{
			if(!ipCanLogin(form_userIP))
			{
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("您使用的ip不能通过~");
			}
			else
			{
				formSubmitSuccess = true;
			}
		}
		else
		{
			formSubmitSuccess = true;
		}
		if(formSubmitSuccess)
		{
			result.setMsg("登录成功~");
			AdminInfoForm adminInfoForm = new AdminInfoForm();
			adminInfoForm.setLoginName(form_loginName);
			adminInfoForm.setLoginPswd(form_loginPswd);
			adminInfoForm.setLoginIP(form_userIP);
			adminInfoForm.setLoginTime(DateUtils.getChar14());
//			request.getSession().setAttribute(Constants.SESSION_ADMININFO, adminInfoForm);
			UserForm userForm = new UserForm();
			userForm.setRoleNameStr("系统管理员");
			userForm.setLoginName("系统管理员");
			userForm.setUserName("系统管理员");
			userForm.setUserCode("000000");
			userForm.setTreeNameStr("交警总队");
			userForm.setRoleId(0);
			userForm.setTreeId(0);
			userForm.setSex("M");
			userForm.setCreateTime("20111212121212");
			RoleForm roleForm = new RoleForm();
			roleForm.setRoleName("系统超级管理员");
			roleForm.setTreeId("0");
			roleForm.setUrlIdList("1,2,3,4,5,6,7,8,9,10,11,12,13");
//			List list_urlForm = userDAO.queryMenuAndUrlByRoleId(roleForm.getUrlIdList().split(","));
			request.getSession().setAttribute(Constants.SESSION_USER_FORM, userForm);//将该用户的userForm信息保存在session中
			request.getSession().setAttribute(Constants.SESSION_ROLE_FORM, roleForm);//将该用户的roleForm信息保存在session中
//			request.getSession().setAttribute(Constants.SESSION_URL_LIST, list_urlForm);//将该用户的urlList信息保存在session中
			userLog(request, "系统管理员登录");
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}


	/**
	 * 用户管理 -- 添加（注册）/修改用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userToAddOrMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果
		String userId = request.getParameter("userId")==null?"":request.getParameter("userId");
		if(!userId.equals(""))
		{
			UserForm user = new UserForm();
			user.setUserId(new Long(userId));
			result.setRetObj(frameUserBO.userById(user));
		}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 我的主页 -- 用户密码修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userPswdMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		userForm.setLoginPswd(request.getParameter("loginPswd"));
		switch(frameUserBO.pswdModify(userForm, request.getParameter("oldPswd")))//0-修改成功；1-修改失败 系统超时~；2-修改失败 旧密码不正确
		{
			case 0 : {
				userLog(request, "密码修改");
				result.setMsg("密码修改成功~");
				break;
			}
			case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("密码修改失败 系统超时~");
				userLog(request, "密码修改失败 系统超时~");
				break;
			}
			case 2 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("密码修改失败 旧密码不正确~");
				userLog(request, "密码修改失败 旧密码不正确~");
				break;
			}
			default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("密码修改失败 系统超时~");
				userLog(request, "密码修改失败 系统超时~");
			}
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}


	/**
	 * 用户部门管理 -- 部门添加
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		String treeName = request.getParameter("treeName");//部门名称
		String treeDesc = request.getParameter("treeDesc");//部门描述
		long parentTreeId = Long.parseLong(request.getParameter("parentId"));//上级部门id
//		String orderBy = request.getParameter("orderBy");//排序
		TreeForm treeForm = new TreeForm();
		treeForm.setTreeName(treeName);
		treeForm.setTreeDesc(treeDesc);
		treeForm.setTreeState("A");
		treeForm.setCreateUser(userForm.getUserId());
		treeForm.setParentTreeId(parentTreeId);
		treeForm.setOrderBy("0");
	
		if(treeName.trim().replace("　", "").length()==0){
			result.setRetCode(Constants.ACTION_FAILED);
			result.setMsg("部门管理-部门添加失败 部门名称不能为空~");
			request.setAttribute("jsonViewStr", getJsonView(result));
			return mapping.findForward("servletResult");
		}
		
		switch(frameTreeBO.treeAdd(treeForm))//0-添加成功；1-添加失败 系统超时~；2-该部门出现重名 请换个名称
		{
			case 0 : {
				userLog(request, "部门添加");
				result.setMsg("部门管理-部门添加成功~");
				userLog(request, "部门管理-部门添加");
				break;
			}
			case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("部门管理-部门添加失败 系统超时~");
				userLog(request, "部门管理-部门添加失败 系统超时~");
				break;
			}
			case 2 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("部门管理-部门添加失败 该部门出现重名~");
				userLog(request, "部门管理-部门添加失败 该部门出现重名~");
				break;
			}
			default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("部门管理-部门添加失败 系统超时~");
				userLog(request, "部门管理-部门添加失败 系统超时~");
			}
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 用户部门管理 -- 部门修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		long treeId = Long.parseLong(request.getParameter("treeId"));
		String treeName = request.getParameter("treeName");//部门名称
		String treeDesc = request.getParameter("treeDesc");//部门描述
//		String orderBy = request.getParameter("orderBy");//排序
		TreeForm treeForm = new TreeForm();
		treeForm.setTreeId(treeId);
		treeForm.setTreeName(treeName);
		treeForm.setTreeDesc(treeDesc);
		treeForm.setTreeState("A");
		treeForm.setCreateUser(userForm.getUserId());
//		treeForm.setOrderBy(orderBy);

		switch(frameTreeBO.treeModify(treeForm))//0-修改成功；1-修改失败 系统超时~；2-该角色出现重名 请换个角色名称
		{
			case 0 : {
				userLog(request, "部门修改");
				result.setMsg("部门管理-部门修改成功~");
				userLog(request, "部门管理-部门修改");
				break;
			}
			case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("部门管理-部门修改失败 系统超时~");
				userLog(request, "部门管理-部门修改失败 系统超时~");
				break;
			}
			case 2 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("部门管理-部门修改失败 该部门出现重名~");
				userLog(request, "部门管理-部门修改失败 该部门出现重名~");
				break;
			}
			default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("部门管理-部门修改失败 系统超时~");
				userLog(request, "部门管理-部门修改失败 系统超时~");
			}
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 用户部门管理 -- 部门详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		String treeId = request.getParameter("treeId");
		if(treeId!=null && !treeId.equals(""))
		{
			TreeForm tree = new TreeForm();
			tree.setTreeId(Long.parseLong(treeId));
			result.setRetObj(frameTreeBO.treeDetail(tree));
		}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}


	/**
	 * 用户部门管理 -- 部门删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		String treeId = request.getParameter("treeId");
		if(treeId!=null && !treeId.equals("")) {
			TreeForm tree = new TreeForm();
			tree.setTreeId(Long.parseLong(treeId));
			switch(frameTreeBO.treeDelete(tree))//0-修改成功；1-修改失败 系统超时~；2-删除失败 还有其他用户正在使用该角色
			{
				case 0 : {
					userLog(request, "部门删除");
					result.setMsg("部门管理-部门删除成功~");
					userLog(request, "部门管理-部门删除");
					break;
				}
				case 1 : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("部门管理-部门删除失败 系统超时~");
					userLog(request, "部门管理-部门删除失败 系统超时~");
					break;
				}
				case 2 : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("部门管理-部门删除失败 该部门下还有警员，部门不能被删除~");
					userLog(request, "部门管理-部门删除失败 该部门下还有警员，部门不能被删除~");
					break;
				}
				default : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("部门管理-部门删除失败 系统超时~");
					userLog(request, "部门管理-部门删除失败 系统超时~");
				}
			}
		} else {
			result.setRetCode(Constants.ACTION_FAILED);
			result.setMsg("部门管理-部门删除失败 部门id异常~");
			userLog(request, "部门管理-部门删除失败 部门id异常~");
		}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}


	/**
	 * 用户角色管理 -- 角色详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		String roleId = request.getParameter("roleId");
		if(roleId!=null && !roleId.equals(""))
		{
			RoleForm role = new RoleForm();
			role.setRoleId(new Long(roleId));
			RoleForm roleForm = frameRoleBO.roleDetail(role);
			result.setRetObj(roleForm);
		}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}


	/**
	 * 用户角色管理 -- 角色添加
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果
		boolean addSuccess = false;
		switch(userAction(request, "2"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: {
				userLog(request, "角色添加");
				addSuccess = true;break;
			}
			case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("角色管理-角色添加失败 尊敬的用户，您登录已超时，请刷新后重新登录~");
			}
			case 2 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("角色管理-角色添加失败 尊敬的用户，您不具备此权限范围~");
			}
			default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("角色管理-角色添加失败 系统超时~");
			}
		}
		if(addSuccess)
		{
			String roleName = request.getParameter("roleName");//角色名称
			String roleDesc = request.getParameter("roleDesc");//角色描述
			String[] urlId = request.getParameter("urlId").split(",");//权限对应的功能url项
			String urlIdList = "";
			for(int i=0; i<urlId.length; i++)
			{
				urlIdList += urlId[i]+",";
			}
			String roleState = "A";//角色状态
			UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
			String createUser = uf.getUserId()+"";//创建人id
			String treeId = uf.getTreeId()+"";//所属部门
			RoleForm roleForm = new RoleForm();
			roleForm.setRoleName(roleName);
			roleForm.setRoleDesc(roleDesc);
			roleForm.setRoleState(roleState);
			roleForm.setCreateUser(createUser);
			roleForm.setTreeId(treeId);
			roleForm.setUrlIdList(urlIdList);
			if(roleName.trim().replace(" ", "").length()==0){
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("角色管理-角色添加失败 角色名称不能为空~");
				request.setAttribute("jsonViewStr", getJsonView(result));
				return mapping.findForward("servletResult");
			}
			int actionResult = frameRoleBO.roleAdd(roleForm);
			switch(actionResult)//0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
			{
				case 0 : {
					result.setMsg("角色管理-角色添加成功~");
					userLog(request, "角色管理-角色添加成功~");
					break;
				}
				case 1 : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("角色管理-角色添加失败 系统超时~");
					userLog(request, "角色管理-角色添加失败 系统超时~");
					break;
				}
				case 2 : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("角色管理-角色添加失败 该角色出现重名~");
					userLog(request, "角色管理-角色添加失败 该角色出现重名~");
					break;
				}
				default : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("角色管理-角色添加失败 系统超时~");
					userLog(request, "角色管理-角色添加失败 系统超时~");
				}
			}
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}


	/**
	 * 用户角色管理 -- 角色修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		long roleId = Long.parseLong(request.getParameter("roleId"));
		String roleName = request.getParameter("roleName");//角色名称
		String roleDesc = request.getParameter("roleDesc");//角色描述
		String[] urlId = request.getParameter("urlId").split(",");//权限对应的功能url项
		String urlIdList = "";
		for(int i=0; i<urlId.length; i++)
		{
			urlIdList += urlId[i]+",";
		}
		String roleState = "A";//角色状态
		UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		String createUser = uf.getUserId()+"";//创建人id
		String treeId = uf.getTreeId()+"";//所属部门
		RoleForm roleForm = new RoleForm();
		roleForm.setRoleId(roleId);
		roleForm.setRoleName(roleName);
		roleForm.setRoleDesc(roleDesc);
		roleForm.setRoleState(roleState);
		roleForm.setCreateUser(createUser);
		roleForm.setTreeId(treeId);
		roleForm.setUrlIdList(urlIdList);

		switch(frameRoleBO.roleModify(roleForm))//0-修改成功；1-修改失败 系统超时~；2-该角色出现重名 请换个角色名称
		{
			case 0 : {
				result.setMsg("角色管理-角色修改成功~");
				userLog(request, "角色管理-角色修改成功~");
				break;			
			}
			case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("角色管理-角色修改失败 系统超时~");
				userLog(request, "角色管理-角色修改失败 系统超时~");
				break;
			}
			case 2 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("角色管理-角色修改失败 该角色出现重名~");
				userLog(request, "角色管理-角色修改失败 该角色出现重名~");
				break;
			}
			default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("角色管理-角色修改失败 系统超时~");
				userLog(request, "角色管理-角色修改失败 系统超时~");
			}
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 角色管理首页 -- 角色删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		String roleId = request.getParameter("roleId");
		if(roleId!=null && !roleId.equals("")) {
			RoleForm role = new RoleForm();
			role.setRoleId(new Long(roleId));
			switch(frameRoleBO.roleDelete(role))//0-修改成功；1-修改失败 系统超时~；2-删除失败 还有其他用户正在使用该角色
			{
				case 0 : {
					result.setMsg("角色管理-角色删除成功~");
					userLog(request, "角色管理-角色删除成功~");
					break;
				}
				case 1 : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("角色管理-角色删除失败 系统超时~");
					userLog(request, "角色管理-角色删除失败 系统超时~");
					break;
				}
				case 2 : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("角色管理-角色删除失败 还有其他用户正在使用该角色~");
					userLog(request, "角色管理-角色删除失败 还有其他用户正在使用该角色~");
					break;
				}
				default : {
					result.setRetCode(Constants.ACTION_FAILED);
					result.setMsg("角色管理-角色删除失败 系统超时~");
				}
			}
		} else {
			result.setRetCode(Constants.ACTION_FAILED);
			result.setMsg("角色管理-角色删除失败 角色id异常~");
			userLog(request, "角色管理-角色删除失败 角色id异常~");
		}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}


	/**
	 * 用户管理 -- 添加（注册）用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		UserForm userForm = new UserForm();
		userForm.setLoginName(request.getParameter("loginName"));//登录帐号
		userForm.setLoginPswd(request.getParameter("loginPswd"));//登录密码
		userForm.setUserName(request.getParameter("userName"));//真实姓名
		userForm.setUserCode(request.getParameter("userCode"));//警员编号
		userForm.setSex(request.getParameter("sex"));//性别
		userForm.setUserIdCard(request.getParameter("userIdCard"));//身份证号
		//userForm.setCard_typeId(Long.parseLong(request.getParameter("cardTypeId")));
		userForm.setCard_typeId(0);//其他证件类型
		userForm.setCardCode("");//其他证件编号
		if(request.getParameter("roleType").equals("")) {
			userForm.setTreeId(Long.parseLong(request.getParameter("treeId")));//所在部门id
			userForm.setRoleId(Long.parseLong(request.getParameter("roleId")));//所属角色id
		}
		userForm.setCreateTime(DateUtils.getChar14());//创建时间
		userForm.setUserState(request.getParameter("userState"));//用户状态
		userForm.setRoleType(request.getParameter("roleType"));
		switch(frameUserBO.userRegister(userForm))//0-注册成功；1-注册失败 系统超时~；2-注册失败 登录名称重复
		{
			case 0 : {
				result.setMsg("用户管理-注册成功~");
				userLog(request, "用户管理-注册成功~");
				break;
			}
			case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("用户管理-注册失败 系统超时~");
				userLog(request, "用户管理-注册失败 系统超时~");
				break;
			}
			case 2 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("用户管理-注册失败 登录帐号重复~");
				userLog(request, "用户管理-注册失败 登录帐号重复~");
				break;
			}
			default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("用户管理-注册失败 系统超时~");
				userLog(request, "用户管理-注册失败 系统超时~");
			}
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 用户管理 -- 用户信息修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

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
		if(!request.getParameter("treeId").equals("")) {
			userForm.setTreeId(Long.parseLong(request.getParameter("treeId")));
		}
		if(!request.getParameter("roleId").equals("")) {
			userForm.setRoleId(Long.parseLong(request.getParameter("roleId")));
		}
		userForm.setCreateTime(request.getParameter("createTime"));
		userForm.setUserState(request.getParameter("userState"));
		userForm.setRoleType(request.getParameter("roleType"));
		switch(frameUserBO.userModify(userForm))//0-修改成功；1-修改失败 系统超时~；2-修改失败 登录名称重复
		{
			case 0 : {
				userLog(request, "用户信息修改");
				result.setMsg("用户管理-用户信息修改成功~");
				break;
			}
			case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("用户管理-用户信息修改失败 系统超时~");
				break;
			}
			case 2 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("用户管理-用户信息修改失败 账号名称重复~");
				break;
			}
			default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("用户管理-用户信息修改失败 系统超时~");
			}
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 用户管理 -- 删除用户（该方法未用到）
	 */
	public ActionForward userDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		Long user_id=Long.parseLong(request.getParameter("userId")==null?"":request.getParameter("userId"));
		int actionResult = userDAO.userManagerDel(user_id);
    	switch(actionResult){
    	   case 0 : {
				userLog(request, "用户信息删除");
    		   result.setMsg("用户管理-用户信息删除成功~");
    		   break;
    	   }
		   case 1 : {
			   result.setRetCode(Constants.ACTION_FAILED);
			   result.setMsg("用户管理-用户信息删除失败 系统超时~");
			   break;
		   }
		   default : {
			   result.setRetCode(Constants.ACTION_FAILED);
			   result.setMsg("用户管理-用户信息删除失败 系统超时~");
		   }
    	}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 用户管理 -- 用户密码重置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward resetPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		UserForm userForm = new UserForm();
		userForm.setUserId(new Long(request.getParameter("userId")));
		userForm = frameUserBO.userById(userForm);
		String oldPswd = userForm.getLoginPswd();
		userForm.setLoginPswd(SystemConfig.getSystemConfig().getResetPswd());
		switch(frameUserBO.pswdModify(userForm, oldPswd))//0-修改成功；1-修改失败 系统超时~；2-修改失败 旧密码不正确
		{
			case 0 : {
				result.setMsg("用户管理-用户密码重置成功~");
				userLog(request, "用户管理-用户密码重置成功~");
				break;
			}
			default: {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("用户管理-用户密码重置失败 系统超时~");
				userLog(request, "用户管理-用户密码重置失败 系统超时~");
			}
		}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

    /**
     * 系统监控
     */
    public ActionForward serverInfoQuery(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	//查看方式 1-按天统计；2-按周统计；3-按月统计；4-按年统计
    	int queryType = request.getParameter("queryType")==null?1:Integer.parseInt(request.getParameter("queryType"));
    	String statTime = DateUtils.getChar8ByJs(request.getParameter("statTime"));//按天统计 日期
    	String statDay_beginTime = DateUtils.getChar8ByJs(request.getParameter("statDay_beginTime"));//按多天统计 起始日期
    	String statDay_endTime = DateUtils.getChar8ByJs(request.getParameter("statDay_endTime"));//按多天统计 截止日期
    	String statMonth = request.getParameter("statMonth_1")+request.getParameter("statMonth_2");//按月统计 年+月
    	String statYear = request.getParameter("statYear");//按年统计 年份
    	switch(queryType)
    	{
			case 1: request.setAttribute(Constants.JSP_ServerInfo_LIST, frameServerInfoBO.serverInfoListByDay(statTime));break;
			case 2: request.setAttribute(Constants.JSP_ServerInfo_LIST, frameServerInfoBO.serverInfoListByWeek(statDay_beginTime, statDay_endTime));break;
			case 3: request.setAttribute(Constants.JSP_ServerInfo_LIST, frameServerInfoBO.serverInfoListByMonth(statMonth));break;
			case 4: request.setAttribute(Constants.JSP_ServerInfo_LIST, frameServerInfoBO.serverInfoListByYear(statYear));break;
			default : ;break;
    	}
		return mapping.findForward("result_serverimg");
    }

	/**
	 * 上传日志查询 —— 大队查询 222
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadLog_byTree1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int tree1_searchType = Integer.parseInt(request.getParameter("tree1_searchType"));
		String tree1_year = request.getParameter("tree1_year");
		String tree1_month = request.getParameter("tree1_month");
		switch(tree1_searchType)
		{
			// 333
			case 1 : request.setAttribute("uploadLogList_ByTree1", adminDAO.uploadImgQuery_ByTree1(tree1_searchType, tree1_year));break;
			case 2 : request.setAttribute("uploadLogList_ByTree1", adminDAO.uploadImgQuery_ByTree1(tree1_searchType, tree1_year+tree1_month));break;
			default : ;
		}
		return mapping.findForward("analysisLine");
	}

	/**
	 * 上传日志查询 —— 中队查询 222
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadLog_byTree2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int tree2_searchType = Integer.parseInt(request.getParameter("tree2_searchType"));
		String tree2_year = request.getParameter("tree2_year");
		String tree2_month = request.getParameter("tree2_month");
		String tree_id = request.getParameter("tree2_treeId");
		String tree_name = request.getParameter("tree2_treeName");
		List list = null;
		switch(tree2_searchType)
		{
			// 333
			case 1 : list = adminDAO.uploadImgQuery_ByTree2(tree2_searchType, tree2_year, tree_id);break;
			case 2 : list = adminDAO.uploadImgQuery_ByTree2(tree2_searchType, tree2_year+tree2_month, tree_id);break;
			default : ;
		}
		request.setAttribute("uploadLogList_ByTree2", list);
		return mapping.findForward("analysisPie");
	}

	/**
	 * 上传日志查询 —— 警员查询 222
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadLog_byUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int user_searchType = Integer.parseInt(request.getParameter("user_searchType"));
		String user_id = request.getParameter("user_userId");
		String user_year = request.getParameter("user_year");
		String user_month = request.getParameter("user_month");
		switch(user_searchType)
		{
			// 333
			case 1 : request.setAttribute("uploadLogList_ByUser", adminDAO.uploadImgQuery_ByUser(user_searchType, user_year, user_id));break;
			case 2 : request.setAttribute("uploadLogList_ByUser", adminDAO.uploadImgQuery_ByUser(user_searchType, user_year+user_month, user_id));break;
			default : ;
		}
		return mapping.findForward("analysisBasic");
	}
     
    /**
     * 公告添加 222
     */
    public ActionForward noticeManagerAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		Result result = new Result();//返回结果

		NoticeForm noticeForm = null;
    	String notice_title=request.getParameter("noticeTitle")==null?"":request.getParameter("noticeTitle");
    	String notice_content=request.getParameter("noticeContent")==null?"":request.getParameter("noticeContent");
    	String notice_type=request.getParameter("noticeType")==null?"":request.getParameter("noticeType");
    	String notice_begin=request.getParameter("noticeBegin")==null?"":request.getParameter("noticeBegin");
    	String notice_end=request.getParameter("noticeEnd")==null?"":request.getParameter("noticeEnd");
      	String treeIdArr = null;
      	if(request.getParameter("treeIdList")!=null)
      	{
      		treeIdArr = request.getParameter("treeIdList");
    	  	  	noticeForm = new NoticeForm();
    	  	  	noticeForm.setNoticeTitle(notice_title);
    	  	  	noticeForm.setNoticeContent(HtmlUtils.encodeHtml(notice_content));
    	  	  	noticeForm.setNoticeType(notice_type);
    	  	  	noticeForm.setNoticeBegin(notice_begin);
    	  	  	noticeForm.setNoticeEnd(notice_end);
    	  	  	noticeForm.setTreeIdList(treeIdArr);
      	}

      	if(notice_title.trim().replace("　", "").length()==0){
			result.setRetCode(Constants.ACTION_FAILED);
			result.setMsg("公告管理-公告添加失败 公告标题不能为空~");
			request.setAttribute("jsonViewStr", getJsonView(result));
			return mapping.findForward("servletResult");
		}
      	
      	if(notice_content.trim().replace("　", "").length()==0){
			result.setRetCode(Constants.ACTION_FAILED);
			result.setMsg("公告管理-公告添加失败 公告内容不能为空~");
			request.setAttribute("jsonViewStr", getJsonView(result));
			return mapping.findForward("servletResult");
		}
      	// 333
		switch(sysDAO.noticeManagerAdd(noticeForm))//0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
		{
		   case 0 : {
				userLog(request, "公告添加");
				result.setMsg("公告管理-公告添加成功~");
			    break;
		   }
		   case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("公告管理-公告添加失败 系统超时~");
				break;
		   }
		   default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("公告管理-公告添加失败 系统超时~");
		   }
		}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
    }

	/**
	 * 文件管理首页 -- 文件查看 修改文件加★状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadFileStats(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果
		boolean uploadSuccess = true;
		if(uploadSuccess)
		{
			String uploadId = request.getParameter("fileId");
			int stats = Integer.parseInt(request.getParameter("stats"));
			String msg = stats==1?"添加 ★ 成功~！":"去除 ★ 成功~！";
			switch(frameUploadBO.uploadStats(new Long(uploadId), stats+""))
			{
				case 0: result.setMsg(msg);userLog(request, msg);break;
				case 1 : result.setMsg(msg.replace("成功", "失败"));result.setRetCode(Constants.ACTION_FAILED);break;
			}
		}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 文件管理首页 -- 文件查看 修改文件备注
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadFileRemark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果
		boolean uploadSuccess = true;
		if(uploadSuccess)
		{
			String uploadId = request.getParameter("fileId");
			String file_remark = request.getParameter("file_remark")==null?"":request.getParameter("file_remark");
			switch(frameUploadBO.uploadRemark(new Long(uploadId), file_remark))
			{
				case 0: result.setMsg("备注信息修改成功！");userLog(request, "备注信息修改成功！");break;
				case 1 : result.setMsg("备注信息修改失败！");result.setRetCode(Constants.ACTION_FAILED);break;
			}
		}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}
    
    /**
     * 公告删除 222
     */
    public ActionForward noticeManagerDel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	Result result = new Result();//返回结果
    	Long notice_id = Long.parseLong(request.getParameter("noticeManager_noticeId")==null?"":request.getParameter("noticeManager_noticeId"));
    	// 333
    	switch(sysDAO.noticeManagerDel(notice_id)){
    	   case 0 : {
				userLog(request, "公告删除");
				result.setMsg("公告管理-公告删除成功~");break;
    	   }
		   case 1 : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("公告管理-公告删除失败 系统超时~");
				break;
		   }
		   default : {
				result.setRetCode(Constants.ACTION_FAILED);
				result.setMsg("公告管理-公告删除失败 系统超时~");
		   }
    	}

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
    }

	/**
	 * java对象转jsonStr
	 * @param javaObj
	 * @return
	 */
	public String getJsonView(Object javaObj) {
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		String sRst = gson.toJson(javaObj);
		System.out.println(sRst);
		return sRst;
	}

	/**
	 * 判断管理员ip是否有权登录
	 * @param userIP 登录的ip地址
	 * @return
	 */
	public boolean ipCanLogin(String userIP)
	{
		for(int i=0; i<SystemConfig.getSystemConfig().getLoginIPList().size(); i++)
		{
			if(userIP.equals(SystemConfig.getSystemConfig().getLoginIPList().get(i).toString()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 权限判断 判断用户是否有权限操作
	 * @param request
	 * @return 0-运行操作 1-用户登录已超时 2-用户不在此权限范围
	 */
	public int userAction(HttpServletRequest request, String actionUrlId) {
		int userActionCode = 0;
		if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)==null)//用户session为空 登录已超时
		{
			userActionCode = 1;
		}
		else
		{
//			UserForm userForm = (UserForm)(request.getSession().getAttribute(Constants.SESSION_USER_FORM));
//			RoleForm roleForm = (RoleForm)(request.getSession().getAttribute(Constants.SESSION_ROLE_FORM));
			String[] url = ((RoleForm)(request.getSession().getAttribute(Constants.SESSION_ROLE_FORM))).getUrlIdList().split(",");
			for(int i=0; i<url.length; i++)
			{
				if(url[i].equals(actionUrlId))
				{
					userActionCode = 0;
					return userActionCode;
				}
			}
			userActionCode = 2;
		}
		return userActionCode;
	}

	/**
	 * 获取一周内第一天
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getWeekFirstDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();//返回结果

		String chooseDay = request.getParameter("chooseDay");
		result.setMsg(DateUtils.rollDate(DateUtils.getChar8ByJs(chooseDay), -6));

		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
	}

	/**
	 * 公告管理 公告分页list 333
	 */
    public ActionForward noticeManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String pageCute = request.getParameter("pageCute")==null?"":request.getParameter("pageCute");
		int pagecute = 1;
		try
		{
			pagecute = Integer.parseInt(pageCute);
		}
		catch(Exception ex)
		{
			pagecute = 1;
		}
    	UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
    	// 333
    	List<NoticeForm> noticeFormList =  sysDAO.adminNoticeManager(uf);
    	List<NoticeForm> pageFormList = null;
    	if(noticeFormList!=null && noticeFormList.size()>0)
    	{
    		pageFormList = new ArrayList<NoticeForm>();
	    	for(int i=(pagecute-1)*5; i<pagecute*5 && i<noticeFormList.size(); i++)
	    	{
	    		pageFormList.add(noticeFormList.get(i));
	    	}
    	}
    	Page page = new Page();
    	page.setDbLine(10);//每页10条记录
    	page.setPageCute(pagecute);//当前页
    	page.setTotal(noticeFormList.size());//总页数
    	page.setListObject(pageFormList);//分页数据
    	request.setAttribute(Constants.PAGE_INFORMATION, page);
    	return mapping.findForward("user_noticeManager");
    }

	/**
	 * 根据userCode获取用户信息 222
	 */
    public ActionForward userFormByCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		String userCode = request.getParameter("userCode")==null?"":request.getParameter("userCode");
		// 333
		UserForm userForm = userDAO.queryUserFormByUserCode(userCode);
		Result result = new Result();//返回结果
		result.setRetObj(userForm);
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
    }

    /**
     * 弹出公告提示 222
     */
    public ActionForward popNoticeTitle(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
    	Result result = new Result();//返回结果
    	if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)==null) {//用户session为空 登录已超时
    		result.setRetCode(Constants.ACTION_FAILED);
    		result.setMsg("用户session为空 登录已超时，无需弹出提示框");
    		System.out.println("=======用户session为空 登录已超时，无需弹出提示框");
    	} else {
    		UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
    		// 333s
    		List<NoticeForm> noticeFormList =  sysDAO.noticeManager(userForm);
    		if(noticeFormList!=null && noticeFormList.size()>0) {
    			NoticeForm noticeForm = noticeFormList.get(0);//最新的一条公共
    			//判断最新一条公共是否超出当前时间后5分钟
    			if(DateUtils.timeXJ(noticeForm.getCreateTime().substring(0,12))>=3) {//超过3分钟
    				result.setRetCode(Constants.ACTION_FAILED);
            		result.setMsg("该公告已经超过3分钟，无需弹出提示框");
            		System.out.println("=======该公告已经超过3分钟，无需弹出提示框");
    			} else {
    			//判断是否看过
	    			if(noLooked(noticeForm, request)) {
	    				result.setRetCode(Constants.ACTION_SUCCESS);//该公告未看过，需弹出提示框
	            		result.setMsg("该公告未看过，需弹出提示框");
	            		result.setRetObj(noticeForm);
	            		System.out.println("=======该公告未看过，需弹出提示框");
	    			} else {//已看过
	    				result.setRetCode(Constants.ACTION_FAILED);
	            		result.setMsg("该公告已经看过，无需弹出提示框");
	            		System.out.println("=======该公告已经看过，无需弹出提示框");
	    			}
    			}
    		} else {
    			result.setRetCode(Constants.ACTION_FAILED);
        		result.setMsg("暂无公告，无需弹出提示框");
        		System.out.println("=======暂无公告，无需弹出提示框");
    		}
    	}
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
    }
    /**
     * 判断是否看过该公告
     */
    public boolean noLooked(NoticeForm noticeForm, HttpServletRequest request) {
    	boolean noLooked = true;
    	String lookedNotitce = "";
    	if(request.getSession().getAttribute(Constants.SESSION_LOOKED_NOTICE)!=null) {
    		lookedNotitce = (String) request.getSession().getAttribute(Constants.SESSION_LOOKED_NOTICE);
    	}
    	if(lookedNotitce.split(",")[lookedNotitce.split(",").length-1].equals(noticeForm.getNoticeId())) {
    		noLooked = false;//看过了
    	}
    	return noLooked;
    }

	/**
	 * 检查服务器磁盘空间大小
	 */
    public ActionForward letterFreeSpace(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		// 333
		Result result = new Result();//返回结果
		result.setRetObj(null);
		File f = new File(SystemConfig.getSystemConfig().getLetter()+":/");
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        if(Double.parseDouble((df.format(f.getFreeSpace()/1024.0/1024/1024)).replace(",", "")) < Double.parseDouble(SystemConfig.getSystemConfig().getMinfreeSpace())) {
        	result.setRetCode(Constants.ACTION_FAILED);
        	result.setMsg("服务器磁盘空间将用尽，上传功能暂时被停止，请联系管理员！");
        }
		request.setAttribute("jsonViewStr", getJsonView(result));
		return mapping.findForward("servletResult");
    }

	/**
	 * 用户操作日志添加 222
	 * @param request
	 * @param logDesc
	 * @return
	 */
	public ActionForward userLog(HttpServletRequest request, String logDesc) {
		UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		LogForm logForm = new LogForm(userForm.getUserId(), userForm.getUserCode(), userForm.getTreeId(), userForm.getTreeNameStr(), userForm.getRoleId(), userForm.getRoleNameStr(), logDesc, request.getRemoteAddr());
		// 333
		logDAO.logAdd(logForm);
		return null;
	}

	public void setSysDAO(SysDAO sysDAO) {
		this.sysDAO = sysDAO;
	}
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}
	public void setFrameUserBO(FrameUserBO frameUserBO) {
		this.frameUserBO = frameUserBO;
	}
	public void setFrameRoleBO(FrameRoleBO frameRoleBO) {
		this.frameRoleBO = frameRoleBO;
	}
	public void setFrameTreeBO(FrameTreeBO frameTreeBO) {
		this.frameTreeBO = frameTreeBO;
	}
	public void setFrameServerInfoBO(FrameServerInfoBO frameServerInfoBO) {
		this.frameServerInfoBO = frameServerInfoBO;
	}
	public void setFrameUploadBO(FrameUploadBO frameUploadBO) {
		this.frameUploadBO = frameUploadBO;
	}
	public void setFrameMenuBO(FrameMenuBO frameMenuBO) {
		this.frameMenuBO = frameMenuBO;
	}
}
