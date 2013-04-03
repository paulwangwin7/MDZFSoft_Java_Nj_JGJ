package com.manager.player.controller;

import java.util.ArrayList;
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
import com.manager.pub.bean.InformationFrameForm;
import com.manager.pub.bean.JspForm;
import com.manager.pub.bean.LogForm;
import com.manager.pub.bean.NoticeForm;
import com.manager.pub.bean.Page;
import com.manager.pub.bean.RoleForm;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.TreeForm;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.AVItoFormat;
import com.manager.pub.util.Constants;
import com.manager.pub.util.DateUtils;
import com.manager.pub.util.FileUtils;
import com.manager.pub.util.ImageFormat;
import com.njmd.bo.FrameMenuBO;
import com.njmd.bo.FrameNoticeBO;
import com.njmd.bo.FrameRoleBO;
import com.njmd.bo.FrameTreeBO;
import com.njmd.bo.FrameUploadBO;
import com.njmd.bo.FrameUserBO;


public class UserAction extends DispatchAction {
	private LogDAO logDAO;
	private SysDAO sysDAO;
	private AdminDAO adminDAO;

	private FileUtils fileUtils = new FileUtils();

	private JspForm jspForm;
	private UploadForm uploadForm;
	private InformationFrameForm informationFrameForm;
	private String frame_information = "frame_information";
	private int userActionCode = 0;//0-运行操作 1-用户登录已超时 2-用户不在此权限范围

	AVItoFormat ai = new AVItoFormat();

	private FrameRoleBO frameRoleBO;
	private FrameTreeBO frameTreeBO;
	private FrameUserBO frameUserBO;
	private FrameUploadBO frameUploadBO;
	private FrameNoticeBO frameNoticeBO;
	private FrameMenuBO frameMenuBO;

	/**
	 * 角色管理首页 -- 查询角色列表 222
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		switch(userAction(request, "2"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: {
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
				request.setAttribute(Constants.PAGE_INFORMATION, frameRoleBO.getRoleList(new Page(pagecute, 10)));
				// 333
				request.setAttribute(Constants.JSP_MENU_LIST, frameMenuBO.queryMenuAndUrl());
				return mapping.findForward("user_roleManager");
			}
			case 1: jspForm = new JspForm("","尊敬的用户，您登录已超时，请刷新后重新登录~",true,false,"",false,"","","");break;
			case 2: jspForm = new JspForm("","尊敬的用户，您不具备此权限范围~",true,false,"",false,"","","");break;
			default: jspForm = new JspForm("","系统超时~",true,false,"",false,"","","");break;
		}
		request.setAttribute(Constants.JSP_MESSAGE, jspForm);
		return mapping.findForward("login_information");
	}


	/**
	 * 用户角色管理 -- 角色添加前 222（貌似不用这个方法了，需检查确认）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManagerToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		switch(userAction(request, "2"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: {
				request.setAttribute(Constants.JSP_ROLE_MANAGER_ACTION, "roleManagerAdd");
				request.setAttribute(Constants.JSP_MENU_LIST, frameMenuBO.queryMenuAndUrl());
				return mapping.findForward("user_roleManagerToAdd");
			}
			case 1: jspForm = new JspForm("","尊敬的用户，您登录已超时，请刷新后重新登录~",true,false,"",false,"","","");break;
			case 2: jspForm = new JspForm("","尊敬的用户，您不具备此权限范围~",true,false,"",false,"","","");break;
			default: jspForm = new JspForm("","系统超时~",true,false,"",false,"","","");break;
		}
		request.setAttribute(Constants.JSP_MESSAGE, jspForm);
		return mapping.findForward("login_information");
	}


	/**
	 * 用户角色管理 -- 角色修改前 222（貌似不用这个方法了，需检查确认）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleManagerToMdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleForm roleForm = new RoleForm();
		roleForm.setRoleId(new Long(request.getParameter("roleId")));
		roleForm = frameRoleBO.roleDetail(roleForm);
		if(roleForm==null)
		{
			informationFrameForm = new InformationFrameForm("请确认您选择的角色是否正确~","","1","tab_jsgl","角色管理");
			request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
			return mapping.findForward(frame_information);
		}
		else
		{
			request.setAttribute(Constants.JSP_ROLE_FORM, roleForm);
			request.setAttribute(Constants.JSP_MENU_LIST, frameMenuBO.queryMenuAndUrl());
		}
		request.setAttribute(Constants.JSP_ROLE_MANAGER_ACTION, "roleManagerMdf");
		return mapping.findForward("user_roleManagerToAdd");
	}

	/**
	 * 部门管理首页 -- 查询部门列表 222
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		switch(userAction(request, "1"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: {
				UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
				if(uf.getUserId()==0) {
					// 333
					request.setAttribute("tree_root_list", sysDAO.remarkFormList("FRAME_TREE"));
					request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
				} else {
					// 333
					request.setAttribute("tree_root_list", sysDAO.remarkFormList("FRAME_TREE"));
					request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(uf.getTreeId(), request));
				}
				return mapping.findForward("user_treeManager");
			}
			case 1: jspForm = new JspForm("","尊敬的用户，您登录已超时，请刷新后重新登录~",true,false,"",false,"","","");break;
			case 2: jspForm = new JspForm("","尊敬的用户，您不具备此权限范围~",true,false,"",false,"","","");break;
			default: jspForm = new JspForm("","系统超时~",true,false,"",false,"","","");break;
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}


	/**
	 * 用户管理 -- 查询用户列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		switch(userAction(request, "4"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: {
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
				UserForm userForm = new UserForm();
				userForm.setTreeId(uf.getTreeId());
				userForm.setUserName(request.getParameter("userNameStr")==null?"":request.getParameter("userNameStr"));
				userForm.setUserCode(request.getParameter("userCodeStr")==null?"":request.getParameter("userCodeStr"));
				String queryTreeId = request.getParameter("query_treeId")==null?"":request.getParameter("query_treeId");
				if(uf.getUserId()==0) {
					//request.setAttribute(Constants.PAGE_INFORMATION, frameUserBO.getUserList(new Page(pagecute, 10)));
					request.setAttribute(Constants.PAGE_INFORMATION, frameUserBO.getUserListByAdmin(userForm, queryTreeId, new Page(pagecute, 10)));
				} else {
					request.setAttribute(Constants.PAGE_INFORMATION, frameUserBO.getUserList(userForm, queryTreeId, new Page(pagecute, 10)));
				}
				request.setAttribute(Constants.JSP_USER_MANAGER_ACTION, "userManagerAdd");
				if(uf.getUserId()==0) {
					request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
				} else {
					request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(uf.getTreeId(), request));
				}
				request.setAttribute(Constants.JSP_ROLE_LIST, frameRoleBO.getRoleListAll());
				
				return mapping.findForward("userManager");
			}
			case 1: informationFrameForm = new InformationFrameForm("尊敬的用户，您登录已超时，请刷新后重新登录~","","1","tab_yhcx","用户查询");break;
			case 2: informationFrameForm = new InformationFrameForm("尊敬的用户，您不具备此权限范围~","","1","tab_yhcx","用户查询");break;
			default: informationFrameForm = new InformationFrameForm("系统超时~","","1","tab_yhcx","用户查询");break;
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	/**
	 * 用户 -- 选择部门列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward treeSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		request.setAttribute("tree_root_list", sysDAO.remarkFormList("FRAME_TREE"));
		if(uf.getUserId()==0) {
			request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
		} else {
			request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(uf.getTreeId(), request));
		}
		if(request.getParameter("typeSelect")!=null)
		{
			String typeSelect = request.getParameter("typeSelect");
			if(typeSelect.equals("choose"))//多选
			{
				return mapping.findForward("treeChoose");
			}
			else//单选一级部门
			{
				
			}
		}
		return mapping.findForward("treeSelect");
	}

	/**
	 * 用户 -- 选择角色列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward roleSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
		request.setAttribute(Constants.PAGE_INFORMATION, frameRoleBO.getRoleList(new Page(pagecute, 5)));
		return mapping.findForward("roleSelect");
	}


	/**
	 * 用户 -- 选择用户列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
		request.setAttribute(Constants.JSP_USER_MANAGER_ACTION, "userManagerAdd");
		if(uf.getUserId()==0) {
			request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
		} else {
			request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(uf.getTreeId(), request));
		}
		
		UserForm userForm = new UserForm();
		userForm.setTreeId(uf.getTreeId());
		userForm.setUserName(request.getParameter("user_name")==null?"":request.getParameter("user_name"));
		userForm.setUserCode(request.getParameter("user_code")==null?"":request.getParameter("user_code"));
		String queryTreeId = request.getParameter("query_treeId")==null?"":request.getParameter("query_treeId");
		if(uf.getUserId()==0) {
//			request.setAttribute(Constants.PAGE_INFORMATION, frameUserBO.getUserList(new Page(pagecute, 5)));
			request.setAttribute(Constants.PAGE_INFORMATION, frameUserBO.getUserList(userForm, queryTreeId, new Page(pagecute, 5)));
		} else {
			request.setAttribute(Constants.PAGE_INFORMATION, frameUserBO.getUserList(userForm, queryTreeId, new Page(pagecute, 5)));
		}
		return mapping.findForward("userSelect");
	}

	/**
	 * 用户 -- 选择用户列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userChoose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
//		Long queryTreeId = new Long(request.getParameter("query_treeId"));
//		request.setAttribute(Constants.PAGE_INFORMATION, frameUserBO.getUserListByTree(queryTreeId, new Page(pagecute, 5)));
		UserForm userForm = new UserForm();
		UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		userForm.setTreeId(uf.getTreeId());
		userForm.setUserName(request.getParameter("user_name")==null?"":request.getParameter("user_name"));
		userForm.setUserCode(request.getParameter("user_code")==null?"":request.getParameter("user_code"));
		String queryTreeId = request.getParameter("query_treeId")==null?"":request.getParameter("query_treeId");
		request.setAttribute(Constants.PAGE_INFORMATION, frameUserBO.getUserList(userForm, queryTreeId, new Page(pagecute, 5)));
		return mapping.findForward("userChoose");
	}

	/**
	 * 用户管理首页 -- 添加（注册）用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward userManagerToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		request.setAttribute(Constants.JSP_USER_MANAGER_ACTION, "userManagerAdd");
		if(uf.getUserId()==0) {
			request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
		} else {
			request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(uf.getTreeId(), request));
		}
		request.setAttribute(Constants.PAGE_INFORMATION, frameRoleBO.getRoleListAll());
		return mapping.findForward("toUserAdd");
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
		String userId = request.getParameter("userId")==null?"":request.getParameter("userId");
		if(!userId.equals(""))
		{
			UserForm user = new UserForm();
			user.setUserId(new Long(userId));
			request.setAttribute(Constants.JSP_USER_FORM, frameUserBO.userDetail(user));
		}
		return mapping.findForward("toUserAddOrMdf");
	}

	/**
	 * 用户退出
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute(Constants.SESSION_USER_FORM);
		request.getSession().removeAttribute(Constants.SESSION_ROLE_FORM);
		request.getSession().removeAttribute(Constants.SESSION_URL_LIST);
		request.getSession().removeAttribute(Constants.SESSION_LOOKED_NOTICE);
		return mapping.findForward("user_login");
	}


	/**
	 * 文件管理首页 -- 文件上传 222
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean uploadSuccess = false;
		switch(userAction(request, "5"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: uploadSuccess = true;break;
			case 1 : informationFrameForm = new InformationFrameForm("尊敬的用户，您登录已超时，请刷新后重新登录~","","1","tab_wjsc","文件上传");break;
			case 2 : informationFrameForm = new InformationFrameForm("尊敬的用户，您不具备此权限范围~","","1","tab_wjsc","文件上传");break;
			default : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","tab_wjsc","文件上传");
		}
		if(uploadSuccess)
		{
			uploadForm = new UploadForm();
			UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
			uploadForm.setUserId(userForm.getUserId());
			uploadForm.setEditId(Long.parseLong(request.getParameter("upload_editId")));
			uploadForm.setUploadName(request.getParameter("upload_uploadName"));
			uploadForm.setPlayPath(request.getParameter("upload_playPath").replace(",", "/"));
			uploadForm.setShowPath(uploadForm.getPlayPath());
			uploadForm.setFileRemark("");
			uploadForm.setIpAddr(request.getRemoteAddr());
			uploadForm.setFileSavePath(SystemConfig.getSystemConfig().getFileSavePath());
			uploadForm.setFileState("A");

			if(uploadForm.getPlayPath().toLowerCase().lastIndexOf(".jpg")>0)
			{
				String showPath = uploadForm.getPlayPath().substring(0,uploadForm.getPlayPath().toLowerCase().lastIndexOf(".jpg"))+"_small_.jpg";
				ImageFormat.compressPic(SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath(), SystemConfig.getSystemConfig().getFileRoot()+showPath);
				uploadForm.setShowPath(showPath);
			}
			if(uploadForm.getPlayPath().lastIndexOf(".avi")>0)
			{
				String showPath = uploadForm.getPlayPath().substring(0,uploadForm.getPlayPath().lastIndexOf(".avi"))+".jpg";
				ai.makeImgbyvideo(SystemConfig.getSystemConfig().getFfmpegPath(), SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath(), SystemConfig.getSystemConfig().getFileRoot()+showPath);
//				ai.makeFlashbyvideo(SystemConfig.getSystemConfig().getFfmpegPath(), SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath(), SystemConfig.getSystemConfig().getFileRoot()+(uploadForm.getPlayPath().substring(0,uploadForm.getPlayPath().lastIndexOf(".avi"))+".flv"));
				uploadForm.setShowPath(showPath);
				uploadForm.setFileState("C");
			}
			if(uploadForm.getPlayPath().toLowerCase().lastIndexOf(".mp4")>0)
			{
				String showPath = uploadForm.getPlayPath().substring(0,uploadForm.getPlayPath().toLowerCase().lastIndexOf(".mp4"))+".jpg";
//				System.out.println(showPath);
//				System.out.println(SystemConfig.getSystemConfig().getFfmpegPath());
//				System.out.println(SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath());
//				System.out.println(SystemConfig.getSystemConfig().getFileRoot()+showPath);
				ai.makeImgbyMP4(SystemConfig.getSystemConfig().getFfmpegPath(), SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath(), SystemConfig.getSystemConfig().getFileRoot()+showPath);
//				ai.makeFlashbyvideo(SystemConfig.getSystemConfig().getFfmpegPath(), SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath(), SystemConfig.getSystemConfig().getFileRoot()+(uploadForm.getPlayPath().substring(0,uploadForm.getPlayPath().lastIndexOf(".avi"))+".flv"));
				uploadForm.setShowPath(showPath);
				uploadForm.setFlvPath(uploadForm.getPlayPath());
				uploadForm.setFileState("A");
			}
			if(uploadForm.getPlayPath().toLowerCase().lastIndexOf(".wav")>0)
			{
				String showPath = "images/WAV.png";
				uploadForm.setShowPath(showPath);
				uploadForm.setFileState("A");
			}
			uploadForm.setFileCreatetime(request.getParameter("upload_playCreatetime").replace(":", "").replace("-", "").replace(" ", ""));
			uploadForm.setTree2Id(userForm.getTreeId());
			//
			TreeForm tree = new TreeForm();
			tree.setTreeId(userForm.getTreeId());
			if(frameTreeBO.parentTreeDetail(tree) == null)//如果用户是大队的，那么就没有上级，上级还是大队的treeid
			{
				uploadForm.setTree1Id(userForm.getTreeId());
			} else {
				uploadForm.setTree1Id(frameTreeBO.parentTreeDetail(tree).getTreeId());
			}
			switch(frameUploadBO.uploadSave(uploadForm))//0-添加成功；1-添加失败 系统超时~
			{
				case 0 : {
//					userLog(request, "上传文件： "+uploadForm.getUploadName()+"<"+uploadForm.getPlayPath()+"> 上传成功");
					return mapping.findForward("db");
				}
				case 1 : informationFrameForm = new InformationFrameForm("上传失败 系统超时~","","1","tab_wjsc","文件上传");break;
				default : informationFrameForm = new InformationFrameForm("上传失败 系统超时~","","1","tab_wjsc","文件上传");
			}
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}


	/**
	 * 我的主页 -- 文件查看
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadFileShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean uploadSuccess = false;
		switch(userAction(request, "8"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: uploadSuccess = true;break;
			case 1 : informationFrameForm = new InformationFrameForm("尊敬的用户，您登录已超时，请刷新后重新登录~","","1","wjck","文件查看");break;
			case 2 : informationFrameForm = new InformationFrameForm("尊敬的用户，您不具备此权限范围~","","1","wjck","文件查看");break;
			default : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","wjck","文件查看");
		}
		if(uploadSuccess)
		{
			UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);

			if(userForm.getUserId()==0) {
				request.setAttribute("tree_root_list", sysDAO.remarkFormList("FRAME_TREE"));
				request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
			} else {
				request.setAttribute("tree_root_list", sysDAO.remarkFormList("FRAME_TREE"));
				request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(userForm.getTreeId(), request));
			}
			TreeForm tree = new TreeForm();
			tree.setTreeId(userForm.getTreeId());
			tree = frameTreeBO.parentTreeDetail(tree);
			long parentTreeId = tree==null?new Long(0):tree.getTreeId();
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
			String uploadUserId = request.getParameter("uploadUserId")==null?"":request.getParameter("uploadUserId");

			if(parentTreeId!=-1)
			{
				if(parentTreeId==0){
					parentTreeId = userForm.getTreeId();
				}
				request.setAttribute(Constants.PAGE_INFORMATION, frameUploadBO.mineUploadList(userForm.getTreeId()+"", parentTreeId+"", uploadUserId, new Page(pagecute, 5)));
				return mapping.findForward("fileShow");
			}
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}


	/**
	 * 文件管理 -- 文件查看（部门展示）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward filePlayShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean uploadSuccess = false;
		switch(userAction(request, "8"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: uploadSuccess = true;break;
			case 1 : informationFrameForm = new InformationFrameForm("尊敬的用户，您登录已超时，请刷新后重新登录~","","1","wjck","文件查看");break;
			case 2 : informationFrameForm = new InformationFrameForm("尊敬的用户，您不具备此权限范围~","","1","wjck","文件查看");break;
			default : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","wjck","文件查看");
		}
		if(uploadSuccess)
		{
			UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
			if(userForm.getUserId()==0 || userForm.getRoleType().equals("0")) {
				request.setAttribute("tree_root_list", sysDAO.remarkFormList("FRAME_TREE"));
				request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
			} else {
				request.setAttribute("tree_root_list", sysDAO.remarkFormList("FRAME_TREE"));
				request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(userForm.getTreeId(), request));
			}
			return mapping.findForward("uploadFileShow");
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}


	/**
	 * 文件管理 -- 文件查看
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean uploadSuccess = false;
		switch(userAction(request, "8"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: uploadSuccess = true;break;
			case 1 : informationFrameForm = new InformationFrameForm("尊敬的用户，您登录已超时，请刷新后重新登录~","","1","wjck","文件查看");break;
			case 2 : informationFrameForm = new InformationFrameForm("尊敬的用户，您不具备此权限范围~","","1","wjck","文件查看");break;
			default : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","wjck","文件查看");
		}
		if(uploadSuccess)
		{
			UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
			TreeForm tree = new TreeForm();
			tree.setTreeId(userForm.getTreeId());
			tree = frameTreeBO.parentTreeDetail(tree);
			long parentTreeId = tree==null?new Long(0):tree.getTreeId();
			String beginTime = request.getParameter("beginTime")==null?"":request.getParameter("beginTime");
			String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
			String createTimeBegin = request.getParameter("createTimeBegin")==null?"":request.getParameter("createTimeBegin");
			String createTimeEnd = request.getParameter("createTimeEnd")==null?"":request.getParameter("createTimeEnd");
			String fileStats = request.getParameter("fileStats")==null?"":request.getParameter("fileStats");
			String fileRemark = request.getParameter("fileRemark")==null?"":request.getParameter("fileRemark");
			String uploadName = request.getParameter("uploadName")==null?"":request.getParameter("uploadName");
			String pageCute = request.getParameter("pageCute")==null?"":request.getParameter("pageCute");
			String uploadUserId = request.getParameter("uploadUserId")==null?"":request.getParameter("uploadUserId");
			String fileCreateUserId = request.getParameter("fileCreateUserId")==null?"":request.getParameter("fileCreateUserId");//采集人
			String treeId = request.getParameter("treeId")==null?"":request.getParameter("treeId");
			int pagecute = 1;
			try
			{
				pagecute = Integer.parseInt(pageCute);
			}
			catch(Exception ex)
			{
				pagecute = 1;
			}
			if((!beginTime.equals("") && !endTime.equals("")) || (beginTime.equals("") && endTime.equals(""))){
				if(!beginTime.equals("") && !endTime.equals(""))
				{
					beginTime = beginTime.replace("-", "").replace(" ", "").replace(":", "");
					endTime = endTime.replace("-", "").replace(" ", "").replace(":", "");
				}
				if(!createTimeBegin.equals("") && !createTimeEnd.equals(""))
				{
					createTimeBegin = createTimeBegin.replace("-", "").replace(" ", "").replace(":", "");
					createTimeEnd = createTimeEnd.replace("-", "").replace(" ", "").replace(":", "");
				}
				if(userForm.getUserId()==0) {
					//request.setAttribute(Constants.PAGE_INFORMATION, frameUploadBO.uploadListByAdmin(uploadName, "", "", beginTime, endTime, uploadUserId, fileCreateUserId, fileStats, fileRemark, new Page(pagecute, 10)));
					request.setAttribute(Constants.PAGE_INFORMATION, frameUploadBO.uploadManagerQuery(uploadName, treeId, beginTime, endTime, createTimeBegin, createTimeEnd, uploadUserId, fileCreateUserId, fileStats, fileRemark, new Page(pagecute, 8)));
					return mapping.findForward("uploadManager");
				} else {
					if(parentTreeId!=-1)
					{
						if(parentTreeId==0){
							parentTreeId = userForm.getTreeId();
						}
//						request.setAttribute(Constants.PAGE_INFORMATION, frameUploadBO.uploadListByTree(uploadName, userForm.getTreeId()+"", parentTreeId+"", beginTime, endTime, uploadUserId, fileCreateUserId, fileStats, fileRemark, new Page(pagecute, 10)));
						request.setAttribute(Constants.PAGE_INFORMATION, frameUploadBO.uploadManagerQuery(uploadName, treeId, beginTime, endTime, createTimeBegin, createTimeEnd, uploadUserId, fileCreateUserId, fileStats, fileRemark, new Page(pagecute, 8)));
						return mapping.findForward("uploadManager");
					}
					else {
//						request.setAttribute(Constants.PAGE_INFORMATION, frameUploadBO.uploadListByAdmin(uploadName, "", "", beginTime, endTime, uploadUserId, fileCreateUserId, fileStats, fileRemark, new Page(pagecute, 10)));
						request.setAttribute(Constants.PAGE_INFORMATION, frameUploadBO.uploadManagerQuery(uploadName, treeId, beginTime, endTime, createTimeBegin, createTimeEnd, uploadUserId, fileCreateUserId, fileStats, fileRemark, new Page(pagecute, 8)));
						return mapping.findForward("uploadManager");
					}
				}
			}
			else
			{
				informationFrameForm = new InformationFrameForm("您选择的日期范围有误~","","1","tab_wjck","文件查看");
			}
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
	}

	public ActionForward fileDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("fileDetail", frameUploadBO.uploadDetail(Long.parseLong(request.getParameter("uploadId"))));
		return mapping.findForward("playFile");
	}

	/**
	 * 权限判断 判断用户是否有权限操作
	 * @param request
	 * @return 0-运行操作 1-用户登录已超时 2-用户不在此权限范围
	 */
	public int userAction(HttpServletRequest request, String actionUrlId) {
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
	 * 上传日志查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("user_upload_log");
	}


	/**
	 * 操作日志查询 —— 模糊查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @param loginName
	 */
	public ActionForward actionLogManagerQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
		String userCode = request.getParameter("userCode")==null?"":request.getParameter("userCode");
		String beginTime = request.getParameter("beginTime")==null?"":request.getParameter("beginTime");
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
		UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
		if(!beginTime.equals("") && !endTime.equals(""))
		{
			beginTime = DateUtils.getChar8ByJs(beginTime);
			endTime = DateUtils.getChar8ByJs(endTime);
			request.setAttribute(Constants.PAGE_INFORMATION, logDAO.logQuery(userCode, beginTime, endTime, userForm.getTreeId()+"", pagecute, 10));
			return mapping.findForward("userActionLogManager");
		}
		else
		{
			return mapping.findForward("userActionLogManager");
		}
	}
	
	/**
	 * 用户公告管理
	 */
    public ActionForward userNoticeManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	boolean uploadSuccess = false;
		switch(userAction(request, "8"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: uploadSuccess = true;break;
			case 1 : informationFrameForm = new InformationFrameForm("尊敬的用户，您登录已超时，请刷新后重新登录~","","1","wjck","文件查看");break;
			case 2 : informationFrameForm = new InformationFrameForm("尊敬的用户，您不具备此权限范围~","","1","wjck","文件查看");break;
			default : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","wjck","文件查看");
		}
		if(uploadSuccess)
		{
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
	    	List<NoticeForm> noticeFormList =  frameNoticeBO.noticeList(uf);
	    	List<NoticeForm> tempFormList = null;
	    	if(noticeFormList!=null && noticeFormList.size()>0)
	    	{
	    		tempFormList = new ArrayList<NoticeForm>();
		    	for(int i=(pagecute-1)*5; i<pagecute*5 && i<noticeFormList.size(); i++)
		    	{
		    		tempFormList.add(noticeFormList.get(i));
		    	}
	    	}
	    	List<NoticeForm> pageFormList = null;
	    	if(tempFormList!=null && tempFormList.size()>0) {
	    		pageFormList = new ArrayList<NoticeForm>();
	    		for(NoticeForm noticeForm: tempFormList) {
	    			String editerName = "系统管理员";
    				noticeForm.setEditer(editerName);
	    			if(noticeForm.getUserId()!=0) {
	    				UserForm user = new UserForm();
	    				user.setUserId(noticeForm.getUserId());
	    				noticeForm.setEditer(frameUserBO.userDetail(user).getUserName());
	    			}
    				noticeForm.setTreeIdListStr(treeNameByTreeIds(noticeForm.getTreeIdList()));
    				pageFormList.add(noticeForm);
	    		}
	    	}
	    	Page page = new Page();
	    	page.setDbLine(5);//每页5条记录
	    	page.setPageCute(pagecute);//当前页
	    	page.setTotal(noticeFormList==null?0:noticeFormList.size());//总页数
	    	page.setListObject(pageFormList);//分页数据
	    	request.setAttribute(Constants.PAGE_INFORMATION, page);
	    	
	    	if(uf.getUserId()==0) {
		 		   request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
	 	   } else {
	 		   request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(uf.getTreeId(), request));
	 	   }
	    	return mapping.findForward("userNoticeManager");
		}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
    }
	
	/**
	 * 管理员公告管理
	 */
    public ActionForward adminNoticeManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
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
//    	List<NoticeForm> noticeFormList =  sysDAO.adminNoticeManager(uf);
    	List<NoticeForm> noticeFormList =  frameNoticeBO.noticeList(uf);
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
    	return mapping.findForward("userNoticeManager");
    }

	/**
	 * 我的主页 —— 我的公告分页
	 */
    public ActionForward mineNotice(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
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
//    	List<NoticeForm> noticeFormList =  sysDAO.noticeManager(uf);
    	List<NoticeForm> noticeFormList =  frameNoticeBO.noticeList(uf);
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
    	page.setDbLine(5);//每页10条记录
    	page.setPageCute(pagecute);//当前页
    	page.setTotal(noticeFormList.size());//总页数
    	page.setListObject(pageFormList);//分页数据
    	request.setAttribute(Constants.PAGE_INFORMATION, page);
    	return mapping.findForward("noticePageList");
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
	public ActionForward expiredFileList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
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
		
		String expiredTime = DateUtils.rollHour(DateUtils.getChar14(), -Integer
				.parseInt(SystemConfig.getSystemConfig().getExpiredDay()) * 24);
//		request.setAttribute(Constants.JSP_UPLOAD_LIST, adminDAO.uploadManagerQuery_expired(expiredTime.substring(0, 8)));
		userLog(request, "过期文件删除 —— 文件列表删除");
		List<UploadForm> listUploadForm = frameUploadBO.expiredUploadAllList(expiredTime.substring(0, 8));
		Page page = new Page();
    	page.setDbLine(10);//每页10条记录
    	page.setPageCute(pagecute);//当前页
		if(listUploadForm!=null && listUploadForm.size()>0) {
			List<UploadForm> totalUploadForm = new ArrayList<UploadForm>();
			List<UploadForm> tempUploadForm = new ArrayList<UploadForm>();
			for(int i=0; i<listUploadForm.size(); i++) {
				if(listUploadForm.get(i).getTree1Id()==uf.getTreeId() || listUploadForm.get(i).getTree2Id()==uf.getTreeId())
				{
					if(("http://"+SystemConfig.getSystemConfig().getFtpHost()).equals(listUploadForm.get(i).getFileSavePath())) {
						totalUploadForm.add(listUploadForm.get(i));
					}
				}
			}
			int total = 0;
			if(totalUploadForm!=null) {
				total = totalUploadForm.size();
				for(int i=(pagecute-1)*10; i<=pagecute*10-1; i++) {
					if(i<totalUploadForm.size()) {
						tempUploadForm.add(totalUploadForm.get(i));
					}
				}
			}
			List<UploadForm> pageUploadForm = new ArrayList<UploadForm>();
			for(UploadForm upload: tempUploadForm) {
				pageUploadForm.add(frameUploadBO.uploadDetail(upload.getUploadId()));
			}
	    	page.setTotal(total);//总记录数
	    	page.setListObject(pageUploadForm);//分页数据
		}
    	request.setAttribute(Constants.PAGE_INFORMATION, page);
    	
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
		String[] expired_uploadId = request.getParameterValues("expired_uploadId");
		List l = frameUploadBO.uploadStatsList(expired_uploadId);
		List<UploadForm> upForm1 = (List<UploadForm>) (l.get(0));// 重要性
		List<UploadForm> upForm2 = (List<UploadForm>) (l.get(1));// 非重要性
		if(upForm2!=null && upForm2.size()>0) {
			frameUploadBO.uploadDel(upForm2, false);// 首先删除非重要性的文件
		}
		if (upForm1.size() > 0) {
			request.setAttribute(Constants.JSP_MESSAGE, "删除完成 但是其中含有加★文件没有删除~");
		} else {
			request.setAttribute(Constants.JSP_MESSAGE, "删除完成~");
		}
		String expiredTime = DateUtils.rollHour(DateUtils.getChar14(), -Integer
				.parseInt(SystemConfig.getSystemConfig().getExpiredDay()) * 24);
//		request.setAttribute(Constants.JSP_UPLOAD_LIST, frameUploadBO.expiredUploadAllList(expiredTime.substring(0, 8)));
		
		userLog(request, "过期文件删除 —— 文件删除");
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
		List<UploadForm> listUploadForm = frameUploadBO.expiredUploadAllList(expiredTime.substring(0, 8));
		List<UploadForm> totalUploadForm = new ArrayList<UploadForm>();
		List<UploadForm> tempUploadForm = new ArrayList<UploadForm>();
		for(int i=0; i<listUploadForm.size(); i++) {
			if(listUploadForm.get(i).getTree1Id()==uf.getTreeId() || listUploadForm.get(i).getTree2Id()==uf.getTreeId())
			{
				if(("http://"+SystemConfig.getSystemConfig().getFtpHost()).equals(listUploadForm.get(i).getFileSavePath())) {
				totalUploadForm.add(listUploadForm.get(i));
				}
			}
		}
		int total = 0;
		if(totalUploadForm!=null) {
			total = totalUploadForm.size();
			for(int i=(pagecute-1)*10; i<=pagecute*10-1; i++) {
				if(i<totalUploadForm.size()) {
					tempUploadForm.add(totalUploadForm.get(i));
				}
			}
		}
		List<UploadForm> pageUploadForm = new ArrayList<UploadForm>();
		for(UploadForm upload: tempUploadForm) {
			pageUploadForm.add(frameUploadBO.uploadDetail(upload.getUploadId()));
		}
		Page page = new Page();
    	page.setDbLine(10);//每页10条记录
    	page.setPageCute(pagecute);//当前页
    	page.setTotal(total);//总记录数
    	page.setListObject(pageUploadForm);//分页数据
    	request.setAttribute(Constants.PAGE_INFORMATION, page);
		return mapping.findForward("expiredFileList");
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
		String[] expired_uploadId = request.getParameterValues("expired_uploadId");
		List l = frameUploadBO.uploadStatsList(expired_uploadId);
		List<UploadForm> upForm1 = (List<UploadForm>) (l.get(0));// 重要性
		List<UploadForm> upForm2 = (List<UploadForm>) (l.get(1));// 非重要性
		if(upForm1!=null && upForm1.size()>0) {
			frameUploadBO.uploadDel(upForm1, false);// 首先删除重要性
		}
		if(upForm2!=null && upForm2.size()>0) {
			frameUploadBO.uploadDel(upForm2, false);// 首先删除非重要性的文件
		}
		request.setAttribute(Constants.JSP_MESSAGE, "删除完成~");
		String expiredTime = DateUtils.rollHour(DateUtils.getChar14(), -Integer
				.parseInt(SystemConfig.getSystemConfig().getExpiredDay()) * 24);
//		request.setAttribute(Constants.JSP_UPLOAD_LIST, frameUploadBO.expiredUploadAllList(expiredTime.substring(0, 8)));
		
		userLog(request, "过期文件删除 —— 文件删除（含重要性）");
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
		List<UploadForm> listUploadForm = frameUploadBO.expiredUploadAllList(expiredTime.substring(0, 8));
		List<UploadForm> totalUploadForm = new ArrayList<UploadForm>();
		List<UploadForm> tempUploadForm = new ArrayList<UploadForm>();
		for(int i=0; i<listUploadForm.size(); i++) {
			if(listUploadForm.get(i).getTree1Id()==uf.getTreeId() || listUploadForm.get(i).getTree2Id()==uf.getTreeId())
			{
				if(("http://"+SystemConfig.getSystemConfig().getFtpHost()).equals(listUploadForm.get(i).getFileSavePath())) {
					totalUploadForm.add(listUploadForm.get(i));
				}
			}
		}
		int total = 0;
		if(totalUploadForm!=null) {
			total = totalUploadForm.size();
			for(int i=(pagecute-1)*10; i<=pagecute*10-1; i++) {
				if(i<totalUploadForm.size()) {
					tempUploadForm.add(totalUploadForm.get(i));
				}
			}
		}
		List<UploadForm> pageUploadForm = new ArrayList<UploadForm>();
		for(UploadForm upload: tempUploadForm) {
			pageUploadForm.add(frameUploadBO.uploadDetail(upload.getUploadId()));
		}
		Page page = new Page();
    	page.setDbLine(10);//每页10条记录
    	page.setPageCute(pagecute);//当前页
    	page.setTotal(total);//总记录数
    	page.setListObject(pageUploadForm);//分页数据
    	request.setAttribute(Constants.PAGE_INFORMATION, page);
		return mapping.findForward("expiredFileList");
	}

    /**
     * 公告添加前
     */
   public ActionForward noticeManagerToAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	   request.setAttribute(Constants.JSP_NOTICE_MANAGER_ACTION, "noticeManagerAdd");
	   return mapping.findForward("noticeManagerToAdd");
   }

   /**
    * 上传分析
    */
   public ActionForward analysisUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	   UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
	   if(uf.getUserId()==0 || uf.getRoleType().equals("0")) {
			request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
		} else {
			request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(uf.getTreeId(), request));
		}
	   return mapping.findForward("analysisUpload");
   }

   /**
    * 用户文件上传
    */
   public ActionForward userFileUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	   UserForm uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
	   if(uf.getUserId()==0 || uf.getRoleType().equals("0")) {
		   request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeList());
	   } else {
		   request.setAttribute(Constants.JSP_TREE_LIST, frameTreeBO.getTreeListByTreeId(uf.getTreeId(), request));
	   }
	   return mapping.findForward("userFileUpload");
   }

	/**
	 * flv视频制作 —— avi转flv 222
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @param loginName
	 */
	public ActionForward aviMakeFlv(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if(Constants.UPLOADFORM == null) {
			Constants.UPLOADFORM = adminDAO.willMake();
			if(Constants.UPLOADFORM!=null) {
				ai.makeFlashbyvideo(SystemConfig.getSystemConfig().getFfmpegPath(), SystemConfig.getSystemConfig().getFileRoot()+Constants.UPLOADFORM.getPlayPath(), SystemConfig.getSystemConfig().getFileRoot()+(Constants.UPLOADFORM.getPlayPath().substring(0,Constants.UPLOADFORM.getPlayPath().lastIndexOf(".avi"))+".flv"));
			}
		} else {
			String filePath = SystemConfig.getSystemConfig().getFileRoot()+(Constants.UPLOADFORM.getPlayPath().substring(0,Constants.UPLOADFORM.getPlayPath().lastIndexOf(".avi"))+".flv");
			if(fileUtils.fileExists(filePath) == Constants.ACTION_SUCCESS) {
				//修改数据库信息 添加flv_path 修改file_state为A
				Constants.UPLOADFORM.setFlvPath(Constants.UPLOADFORM.getPlayPath().substring(0, Constants.UPLOADFORM.getPlayPath().lastIndexOf(".avi"))+".flv");
				if(adminDAO.makeFlvOver(Constants.UPLOADFORM)==Constants.ACTION_SUCCESS) {
					//清空Constants.UPLOADFORM
					Constants.UPLOADFORM = null;
				}
			}
		}
		return mapping.findForward("result");
	}

	   /**
	    * 我的主页 222
	    */
	   public ActionForward userMain(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 //最新公告查询 333
	   	List<NoticeForm> noticeFormList =  sysDAO.noticeManager((UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM));
	   	List<NoticeForm> pageFormList = null;
	   	if(noticeFormList!=null && noticeFormList.size()>0)
	   	{
	   		pageFormList = new ArrayList<NoticeForm>();
		    for(int i=0; i<5 && i<noticeFormList.size(); i++)
		    {
		    	pageFormList.add(noticeFormList.get(i));
		    }
	   	}
	   	request.setAttribute(Constants.SESSION_NOTICE_LIST, pageFormList);
		   return mapping.findForward("userMain");
	   }

	   /**
	    * 公告详情页 222
	    */
	   public ActionForward noticeDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		   if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)!=null) {
			   Long noticeid = Long.parseLong(request.getParameter("noticeId")==null?"":request.getParameter("noticeId"));
		    	request.setAttribute("noticeForm", sysDAO.queryNotice(noticeid));
		    	
		    	if(request.getSession().getAttribute(Constants.SESSION_LOOKED_NOTICE)==null) {//从未阅读过公告
		    		request.getSession().setAttribute(Constants.SESSION_LOOKED_NOTICE, noticeid+"");
		    	} else {//判断新阅读的id和已阅读过的id
		    		String lookedNotitce = (String) request.getSession().getAttribute(Constants.SESSION_LOOKED_NOTICE);

		    		System.out.println(lookedNotitce);
		    		System.out.println(Long.parseLong(lookedNotitce.split(",")[lookedNotitce.split(",").length-1]));
		    		System.out.println(noticeid);
		    		
		    		if(Long.parseLong(lookedNotitce.split(",")[lookedNotitce.split(",").length-1]) < noticeid) {
		    			
		    			System.out.println(lookedNotitce);
		    			
		    			lookedNotitce += ","+noticeid;
		    			request.getSession().setAttribute(Constants.SESSION_LOOKED_NOTICE, lookedNotitce);
		    		}
		    	}
		   }
		   return mapping.findForward("user_noticeDetail");
	   }
	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}
	public void setSysDAO(SysDAO sysDAO) {
		this.sysDAO = sysDAO;
	}
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	public void setFrameRoleBO(FrameRoleBO frameRoleBO) {
		this.frameRoleBO = frameRoleBO;
	}
	public void setFrameTreeBO(FrameTreeBO frameTreeBO) {
		this.frameTreeBO = frameTreeBO;
	}
	public void setFrameUserBO(FrameUserBO frameUserBO) {
		this.frameUserBO = frameUserBO;
	}
	public void setFrameUploadBO(FrameUploadBO frameUploadBO) {
		this.frameUploadBO = frameUploadBO;
	}
	public void setFrameNoticeBO(FrameNoticeBO frameNoticeBO) {
		this.frameNoticeBO = frameNoticeBO;
	}
	public void setFrameMenuBO(FrameMenuBO frameMenuBO) {
		this.frameMenuBO = frameMenuBO;
	}

	public String treeNameByTreeIds(String treeIds) {
		String treeName = "";
		if(treeIds.length()>0) {
			List<TreeForm> treeFormList = frameTreeBO.treeAllList();
			String[] treeIdArr = treeIds.split(",");
			for(TreeForm treeForm: treeFormList) {
				for(String treeId: treeIdArr) {
					if(treeId.equals(treeForm.getTreeId())) {
						treeName += treeForm.getTreeName()+",";
					}
				}
			}
		}
		return treeName;
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
