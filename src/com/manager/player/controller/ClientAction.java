package com.manager.player.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.manager.admin.dao.LogDAO;
import com.manager.admin.dao.SysDAO;
import com.manager.player.dao.UserDAO;
import com.manager.pub.bean.InformationFrameForm;
import com.manager.pub.bean.JspForm;
import com.manager.pub.bean.LogForm;
import com.manager.pub.bean.NoticeForm;
import com.manager.pub.bean.RoleForm;
import com.manager.pub.bean.TreeForm;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.AVItoFormat;
import com.manager.pub.util.Constants;
import com.manager.pub.util.DateUtils;
import com.manager.pub.util.tt;


public class ClientAction extends DispatchAction {
	private UserDAO userDAO;
	private LogDAO logDAO;
	private SysDAO sysDAO;

	private JspForm jspForm;
	private LogForm logForm;
	private UploadForm uploadForm;
	private NoticeForm noticeForm = null;
	private InformationFrameForm informationFrameForm;
	private String frame_information = "frame_information";
	private int userActionCode = 0;//0-运行操作 1-用户登录已超时 2-用户不在此权限范围

	AVItoFormat ai = new AVItoFormat();
	tt tt_ = new tt();

	/**
	 * 用户登录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String form_loginName = request.getParameter("loginName")==null?"":request.getParameter("loginName");//用户账号 来自用户提交的表单
		
		UserForm userForm = userDAO.clientLogin(form_loginName);
		if(userForm!=null)//用户账号密码正确
		{
			//--step3 判断用户状态是否正常
			if(userForm.getUserState().equals("A"))//用户状态正常
			{
				RoleForm roleForm = userDAO.roleQuery(userForm.getRoleId()+"");
				List list_urlForm = userDAO.queryMenuAndUrlByRoleId(roleForm.getUrlIdList().split(","));
				request.getSession().setAttribute(Constants.SESSION_USER_FORM, userForm);//将该用户的userForm信息保存在session中
				request.getSession().setAttribute(Constants.SESSION_ROLE_FORM, roleForm);//将该用户的roleForm信息保存在session中
				request.getSession().setAttribute(Constants.SESSION_URL_LIST, list_urlForm);//将该用户的urlList信息保存在session中
				jspForm = new JspForm("","身份验证成功~",false,true,"jsp/client/fileManagerAdd.jsp",false,"","","");
			}
			else//用户状态不正常 未激活或已被锁定
			{
				jspForm = new JspForm("","用户状态不正常，未激活或已被锁定~",true,false,"",false,"","","");
			}
		}
		else//用户账号不存在 需注册 进入注册页面
		{
			jspForm = new JspForm("","您的信息尚未注册，请填写注册信息~",false,true,"jsp/client/userManagerAdd.jsp",false,"","","");
		}
		request.setAttribute(Constants.JSP_MESSAGE, jspForm);
		return mapping.findForward("login_information");
	}


	/**
	 * 用户管理首页 -- 添加（注册）用户
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
		//userForm.setCard_typeId(Long.parseLong(request.getParameter("cardTypeId")));
		userForm.setCard_typeId(0);
		userForm.setCardCode("");
		userForm.setTreeId(Long.parseLong(request.getParameter("treeId")));
		userForm.setRoleId(Long.parseLong(request.getParameter("roleId")));
		userForm.setCreateTime(request.getParameter("createTime"));
		userForm.setUserState(request.getParameter("userState"));
		switch(userDAO.userRegister(userForm))//0-注册成功；1-注册失败 系统超时~；2-注册失败 登录名称重复
		{
			case 0 : {
				jspForm = new JspForm("","注册成功 等待管理员审核~",false,true,"jsp/client/login.jsp",false,"","","");
				userLog(request, "用户管理-用户注册：账号 "+userForm.getLoginName()+" 注册失败 登录名称重复~",userForm);
				break;
			}
			case 1 : {
				jspForm = new JspForm("","注册失败 系统超时~",false,true,"jsp/client/login.jsp",false,"","","");
				userLog(request, "用户管理-用户注册：账号 "+userForm.getLoginName()+" 注册失败 系统超时~",userForm);
				break;
			}
			case 2 : {
				jspForm = new JspForm("","注册失败 登录名称重复",false,true,"jsp/client/userManagerAdd.jsp",false,"","","");
				userLog(request, "用户管理-用户注册：账号 "+userForm.getLoginName()+" 注册失败 登录名称重复",userForm);
				break;
			}
			default : {
				jspForm = new JspForm("","注册失败 系统超时~",false,true,"jsp/client/login.jsp",false,"","","");
				userLog(request, "用户管理-用户注册：账号 "+userForm.getLoginName()+" 注册失败 系统超时~",userForm);
			}
		}
		request.setAttribute(Constants.JSP_MESSAGE, jspForm);
		return mapping.findForward("login_information");
	}


	/**
	 * 文件管理首页 -- 文件查看
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
			case 1 : {
				jspForm = new JspForm("","尊敬的用户，您登录已超时，请重新登录~",false,true,"jsp/client/login.jsp",false,"","","");
				break;
			}
			case 2 : {
				jspForm = new JspForm("","尊敬的用户，您不具备此权限范围~",false,true,"jsp/client/login.jsp",false,"","","");
				break;
			}
			default : {
				jspForm = new JspForm("","操作失败 系统超时~",false,true,"jsp/client/login.jsp",false,"","","");
			}
		}
		if(uploadSuccess)
		{
			UserForm userForm = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
			long parentTreeId = userDAO.getUserParentTreeId(userForm.getTreeId());
			String beginTime = request.getParameter("beginTime")==null?"":request.getParameter("beginTime");
			String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
			String fileStats = request.getParameter("fileStats")==null?"":request.getParameter("fileStats");
			String pageCute = request.getParameter("pageCute")==null?"":request.getParameter("pageCute");
			String uploadName = request.getParameter("uploadName")==null?"":request.getParameter("uploadName");
			int pagecute = 1;
			String uploadUserId = request.getParameter("uploadUserId")==null?"":request.getParameter("uploadUserId");
			String fileCreateUserId = request.getParameter("fileCreateUserId")==null?"":request.getParameter("fileCreateUserId");
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
					String[] beginTimeArr = beginTime.split("-");
					beginTime = beginTimeArr[0]+""+(Integer.parseInt(beginTimeArr[1])<10?("0"+beginTimeArr[1]):beginTimeArr[1])+""+(Integer.parseInt(beginTimeArr[2])<10?("0"+beginTimeArr[2]):beginTimeArr[2]);
					String[] endTimeArr = endTime.split("-");
					endTime = endTimeArr[0]+""+(Integer.parseInt(endTimeArr[1])<10?("0"+endTimeArr[1]):endTimeArr[1])+""+(Integer.parseInt(endTimeArr[2])<10?("0"+endTimeArr[2]):endTimeArr[2]);
				}

				if(parentTreeId!=-1)
				{
					if(parentTreeId==0){
						parentTreeId = userForm.getTreeId();
					}
					request.setAttribute(Constants.JSP_UPLOAD_LIST, userDAO.uploadManagerQuery_ByTree(uploadName, userForm.getTreeId()+"", parentTreeId+"", beginTime, endTime, uploadUserId, fileCreateUserId, fileStats, "", pagecute, 10));
					return mapping.findForward("clientUploadFileShow");
				}
			}
			else
			{
				jspForm = new JspForm("","您选择的日期范围有误~",false,true,"clientAction.do?method=uploadFileShow",false,"","","");
			}
		}
		request.setAttribute(Constants.JSP_MESSAGE, jspForm);
		return mapping.findForward("login_information");
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
		boolean uploadSuccess = false;
		switch(userAction(request, "8"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: uploadSuccess = true;break;
			case 1 : {
				jspForm = new JspForm("","尊敬的用户，您登录已超时，请重新登录~",false,true,"jsp/client/login.jsp",false,"","","");
				break;
			}
			case 2 : {
				jspForm = new JspForm("","尊敬的用户，您不具备此权限范围~",false,true,"jsp/client/login.jsp",false,"","","");
				break;
			}
			default : {
				jspForm = new JspForm("","操作失败 系统超时~",false,true,"jsp/client/login.jsp",false,"","","");
			}
		}
		if(uploadSuccess)
		{
			String uploadId = request.getParameter("fileId");
			int stats = Integer.parseInt(request.getParameter("stats"));
			String msg = stats==1?"添加 ★ 成功~！":"去除 ★ 成功~！";
			switch(userDAO.uploadFileStats(uploadId, stats))
			{
				case 0: {
					jspForm = new JspForm("",msg,false,true,"clientAction.do?method=uploadFileShow",false,"","","");
					break;
				}
				case 1 : {
					jspForm = new JspForm("","操作失败 系统超时~",false,true,"clientAction.do?method=uploadFileShow",false,"","","");
					break;
				}
			}
		}
		request.setAttribute(Constants.JSP_MESSAGE, jspForm);
		return mapping.findForward("login_information");
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
		boolean uploadSuccess = false;
		switch(userAction(request, "8"))// 0-正常运行操作 1-用户登录已超时 2-用户不在此权限范围
		{
			case 0: uploadSuccess = true;break;
			case 1 : {
				jspForm = new JspForm("","尊敬的用户，您登录已超时，请重新登录~",false,true,"jsp/client/login.jsp",false,"","","");
				break;
			}
			case 2 : {
				jspForm = new JspForm("","尊敬的用户，您不具备此权限范围~",false,true,"jsp/client/login.jsp",false,"","","");
				break;
			}
			default : {
				jspForm = new JspForm("","操作失败 系统超时~",false,true,"jsp/client/login.jsp",false,"","","");
			}
		}
		if(uploadSuccess)
		{
			String uploadId = request.getParameter("fileId");
			String file_remark = request.getParameter("file_remark")==null?"":request.getParameter("file_remark");
			switch(userDAO.uploadFileRemark(uploadId, file_remark))
			{
				case 0: {
					jspForm = new JspForm("","修改备注信息成功",false,true,"clientAction.do?method=uploadFileShow",false,"","","");
					break;
				}
				case 1 : {
					jspForm = new JspForm("","操作失败 系统超时~",false,true,"clientAction.do?method=uploadFileShow",false,"","","");
					break;
				}
			}
		}
		request.setAttribute(Constants.JSP_MESSAGE, jspForm);
		return mapping.findForward("login_information");
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

	public void userLog(HttpServletRequest request, String logDesc, UserForm userForm) {
		logForm = new LogForm(userForm.getUserId(), userForm.getUserCode(), userForm.getTreeId(), userForm.getTreeNameStr(), userForm.getRoleId(), userForm.getRoleNameStr(), logDesc);
		logDAO.logAdd(logForm);
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	public void setSysDAO(SysDAO sysDAO) {
		this.sysDAO = sysDAO;
	}
}


