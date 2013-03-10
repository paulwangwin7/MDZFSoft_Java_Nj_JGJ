package com.manager.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.manager.admin.dao.SysDAO;
import com.manager.pub.bean.InformationFrameForm;
import com.manager.pub.bean.MonitorInfoBean;
import com.manager.pub.bean.NoticeForm;
import com.manager.pub.bean.Result;
import com.manager.pub.bean.ServerInfoForm;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.Constants;
import com.manager.pub.util.DateUtils;
import com.manager.pub.util.SysMonitor_windows;
import com.njmd.bo.FrameServerInfoBO;
import com.njmd.bo.FrameUploadBO;


public class SysAction extends DispatchAction {
	private SysDAO sysDAO;
	private NoticeForm noticeForm = null;
	private InformationFrameForm informationFrameForm;
	private String frame_information = "frame_information";
	private ServerInfoForm serverInfoForm = null;
	private UserForm uf;

	private FrameServerInfoBO frameServerInfoBO;
	private FrameUploadBO frameUploadBO;

	public void setSysDAO(SysDAO sysDAO) {
		this.sysDAO = sysDAO;
	}
	
	/**
	 * 登录前首页公告查询list
	 */
    public ActionForward userNoticeList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	request.setAttribute("noticeList", sysDAO.noticeManager(null));
    	return mapping.findForward("user_noticeListFrame");
    }
	
	/**
	 * 登录后首页公告查询list
	 */
    public ActionForward userIndexNoticeList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	UserForm userForm = (UserForm)(request.getSession().getAttribute(Constants.SESSION_USER_FORM));
    	request.setAttribute("noticeList", sysDAO.noticeManager(userForm));
    	return mapping.findForward("user_noticeListIndex");
    }
	
	/**
	 * 登录后首页公告查询form
	 */
    public ActionForward userNoticeForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	Long noticeid = Long.parseLong(request.getParameter("noticeId")==null?"":request.getParameter("noticeId"));
    	request.setAttribute("noticeForm", sysDAO.queryNotice(noticeid));
    	return mapping.findForward("user_noticeForm");
    }
	
	/**
	 * 公告查询list
	 */
    public ActionForward noticeManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	request.setAttribute("noticeList", sysDAO.noticeManager((UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM)));
    	return mapping.findForward("userNoticeManager");
    }
    
    /**
     * 公告查询list
     */
    public ActionForward adminNoticeManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	request.setAttribute("noticeList", sysDAO.adminNoticeManager(null));
    	return mapping.findForward("noticeManager");
    }
    
    /**
     * 公告添加前
     */
   public ActionForward noticeManagerToAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	   request.setAttribute(Constants.JSP_NOTICE_MANAGER_ACTION, "noticeManagerAdd");
	   return mapping.findForward("noticeManagerToAdd");
   }
   
   /**
    * 公告添加前 普通用户
    */
  public ActionForward userNoticeManagerToAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	   request.setAttribute(Constants.JSP_NOTICE_MANAGER_ACTION, "userNoticeManagerAdd");
	   return mapping.findForward("userNoticeManagerToAdd");
  }
  
  /**
   * 公告添加 普通用户
   */
  public ActionForward userNoticeManagerAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
  	String notice_title=request.getParameter("noticeTitle")==null?"":request.getParameter("noticeTitle");
  	String notice_content=request.getParameter("noticeContent")==null?"":request.getParameter("noticeContent");
  	String notice_type="2";
  	String notice_begin=request.getParameter("noticeBegin")==null?"":request.getParameter("noticeBegin");
  	String notice_end=request.getParameter("noticeEnd")==null?"":request.getParameter("noticeEnd");
  	String treeIdArr = null;
  	if(request.getParameter("treeIdList")!=null)
  	{
  		treeIdArr = request.getParameter("treeIdList");
	  	  	noticeForm = new NoticeForm();
	  	  	noticeForm.setNoticeTitle(notice_title);
	  	  	noticeForm.setNoticeContent(notice_content);
	  	  	noticeForm.setNoticeType(notice_type);
	  	  	noticeForm.setNoticeBegin(notice_begin);
	  	  	noticeForm.setNoticeEnd(notice_end);
	  	  	noticeForm.setTreeIdList(treeIdArr);
  	}
	if(request.getSession().getAttribute(Constants.SESSION_USER_FORM)==null)
	{
		informationFrameForm = new InformationFrameForm("您登录已超时，请刷新后重新登录~","","1","tab_ggfb","公告管理");
	}
	else
	{
    	uf = (UserForm)request.getSession().getAttribute(Constants.SESSION_USER_FORM);
    	noticeForm.setUserId(uf.getUserId());
		switch(sysDAO.noticeManagerAdd(noticeForm))//0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
		{
		   case 0 : informationFrameForm = new InformationFrameForm("添加成功~","sysAction.do?method=noticeManager","0","tab_ggfb","公告管理");break;
		   case 1 : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","tab_ggfb","公告管理");break;
		   case 2 : informationFrameForm = new InformationFrameForm("添加失败 您还没有选择部门~","","1","tab_ggfb","公告管理");break;
		   default : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","tab_ggfb","公告管理");
		}
	}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);	
  }
  
    /**
     * 公告添加
     */
    public ActionForward noticeManagerAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	String notice_title=request.getParameter("noticeTitle")==null?"":request.getParameter("noticeTitle");
    	String notice_content=request.getParameter("noticeContent")==null?"":request.getParameter("noticeContent");
    	String notice_type=request.getParameter("noticeType")==null?"":request.getParameter("noticeType");
    	String notice_begin=request.getParameter("noticeBegin")==null?"":request.getParameter("noticeBegin");
    	String notice_end=request.getParameter("noticeEnd")==null?"":request.getParameter("noticeEnd");
      	String treeIdArr = null;
      	List<NoticeForm> noticeFormList = null;
      	if(request.getParameter("treeIdList")!=null)
      	{
      		treeIdArr = request.getParameter("treeIdList");
    	  	  	noticeForm = new NoticeForm();
    	  	  	noticeForm.setNoticeTitle(notice_title);
    	  	  	noticeForm.setNoticeContent(notice_content);
    	  	  	noticeForm.setNoticeType(notice_type);
    	  	  	noticeForm.setNoticeBegin(notice_begin);
    	  	  	noticeForm.setNoticeEnd(notice_end);
    	  	  	noticeForm.setTreeIdList(treeIdArr);
      	}
			switch(sysDAO.noticeManagerAdd(noticeForm))//0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
			{
			   case 0 : informationFrameForm = new InformationFrameForm("添加成功~","sysAction.do?method=adminNoticeManager","0","tab_ggfb","公告管理");break;
			   case 1 : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","tab_ggfb","公告管理");break;
			   case 2 : informationFrameForm = new InformationFrameForm("添加失败 您还没有选择部门~","","1","tab_ggfb","公告管理");break;
			   default : informationFrameForm = new InformationFrameForm("添加失败 系统超时~","","1","tab_ggfb","公告管理");
			}
		request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);	
    }
    
    /**
     * 公告删除
     */
    public ActionForward noticeManagerDel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	Long notice_id=Long.parseLong(request.getParameter("noticeManager_noticeId")==null?"":request.getParameter("noticeManager_noticeId"));
    	switch(sysDAO.noticeManagerDel(notice_id)){
    	   case 0 : informationFrameForm = new InformationFrameForm("删除成功~","sysAction.do?method=noticeManager","0","tab_ggfb","公告管理");break;
		   case 1 : informationFrameForm = new InformationFrameForm("删除失败 系统超时~","","1","tab_ggfb","公告管理");break;
		   default : informationFrameForm = new InformationFrameForm("删除失败 系统超时~","","1","tab_ggfb","公告管理");
    	}
    	request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
    }
    
    
    /**
     * 公告修改前
     */
    public ActionForward noticeManagerToMdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	Long noticeid=  Long.parseLong(request.getParameter("noticeId")==null?"":request.getParameter("noticeId"));  
    	request.setAttribute("noticeForm", sysDAO.queryNotice(noticeid));
    	request.setAttribute(Constants.JSP_NOTICE_MANAGER_ACTION, "noticeManagerMdf");
    	return mapping.findForward("userNoticeManagerToMdf");
    }
    
    
    /**
     * 公告修改前
     */
    public ActionForward adminNoticeManagerToMdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	Long noticeid=  Long.parseLong(request.getParameter("noticeId")==null?"":request.getParameter("noticeId"));  
    	request.setAttribute("noticeForm", sysDAO.queryNotice(noticeid));
    	request.setAttribute(Constants.JSP_NOTICE_MANAGER_ACTION, "adminNoticeManagerMdf");
    	return mapping.findForward("noticeManagerToMdf");
    }
    
    
    /**
     * 公告修改
     */
    public ActionForward noticeManagerMdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	Long notice_id=Long.parseLong(request.getParameter("noticeId")==null?"":request.getParameter("noticeId"));
    	String notice_title=request.getParameter("noticeTitle")==null?"":request.getParameter("noticeTitle");
    	String notice_content=request.getParameter("noticeContent")==null?"":request.getParameter("noticeContent");
    	String notice_type=request.getParameter("noticeType")==null?"":request.getParameter("noticeType");
    	String notice_begin=request.getParameter("noticeBegin")==null?"":request.getParameter("noticeBegin");
    	String notice_end=request.getParameter("noticeEnd")==null?"":request.getParameter("noticeEnd");
    	noticeForm = new NoticeForm();
        noticeForm.setNoticeId(notice_id);
    	noticeForm.setNoticeTitle(notice_title);
    	noticeForm.setNoticeContent(notice_content);
    	noticeForm.setNoticeType(notice_type);
    	noticeForm.setNoticeBegin(notice_begin);
    	noticeForm.setNoticeEnd(notice_end);
    	switch(sysDAO.noticeManagerMdf(noticeForm)){
    	   case 0 : informationFrameForm = new InformationFrameForm("修改成功~","sysAction.do?method=noticeManager","0","tab_ggfb","公告管理");break;
		   case 1 : informationFrameForm = new InformationFrameForm("修改失败 系统超时~","","1","tab_ggfb","公告管理");break;
		   default : informationFrameForm = new InformationFrameForm("修改失败 系统超时~","","1","tab_ggfb","公告管理");
    	}
    	request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
    }

    
    /**
     * 公告修改
     */
    public ActionForward adminNoticeManagerMdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	Long notice_id=Long.parseLong(request.getParameter("noticeId")==null?"":request.getParameter("noticeId"));
    	String notice_title=request.getParameter("noticeTitle")==null?"":request.getParameter("noticeTitle");
    	String notice_content=request.getParameter("noticeContent")==null?"":request.getParameter("noticeContent");
    	String notice_type=request.getParameter("noticeType")==null?"":request.getParameter("noticeType");
    	String notice_begin=request.getParameter("noticeBegin")==null?"":request.getParameter("noticeBegin");
    	String notice_end=request.getParameter("noticeEnd")==null?"":request.getParameter("noticeEnd");
    	noticeForm = new NoticeForm();
        noticeForm.setNoticeId(notice_id);
    	noticeForm.setNoticeTitle(notice_title);
    	noticeForm.setNoticeContent(notice_content);
    	noticeForm.setNoticeType(notice_type);
    	noticeForm.setNoticeBegin(notice_begin);
    	noticeForm.setNoticeEnd(notice_end);
    	switch(sysDAO.noticeManagerMdf(noticeForm)){
    	   case 0 : informationFrameForm = new InformationFrameForm("修改成功~","sysAction.do?method=adminNoticeManager","0","tab_ggfb","公告管理");break;
		   case 1 : informationFrameForm = new InformationFrameForm("修改失败 系统超时~","","1","tab_ggfb","公告管理");break;
		   default : informationFrameForm = new InformationFrameForm("修改失败 系统超时~","","1","tab_ggfb","公告管理");
    	}
    	request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward(frame_information);
    }
        
    /**
     * 公告查询
     */
    public ActionForward noticeQuery(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	Long noticeid=  Long.parseLong(request.getParameter("noticeId")==null?"":request.getParameter("noticeId"));  
    	request.setAttribute("noticeForm", sysDAO.queryNotice(noticeid));
    	request.setAttribute(Constants.JSP_NOTICE_MANAGER_ACTION, "noticeManagerMdf");
    	return mapping.findForward("noticeQueryShow");
    }
    
    
    /**
     * 服务器日志添加
     * 
     */
    public ActionForward serverInfoAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	SysMonitor_windows sysInfo = new SysMonitor_windows();
    	try {
    		MonitorInfoBean mib = sysInfo.getMonitorInfoBean(SystemConfig.getSystemConfig().getLetter());
        	ServerInfoForm serverInfoForm = new ServerInfoForm();
    		serverInfoForm.setRatioCPU(Math.abs((int)mib.getCpuRatio()));
    		serverInfoForm.setRatioMEMORY((int)((mib.getTotalMemory()-mib.getFreeMemory())/mib.getTotalMemory()*100));
    		serverInfoForm.setUseMEMORY((mib.getTotalMemory()-mib.getFreeMemory())+"");
    		serverInfoForm.setRatioHARDDISK((int)((Double.parseDouble(mib.getHarddiskTotal())-Double.parseDouble(mib.getHarddiskFree()))/Double.parseDouble(mib.getHarddiskTotal())*100));
    		serverInfoForm.setUseHARDDISK(Double.parseDouble(mib.getHarddiskTotal())-Double.parseDouble(mib.getHarddiskFree())+"");
    		serverInfoForm.setLetter(SystemConfig.getSystemConfig().getLetter());
    		serverInfoForm.setSaveIp(SystemConfig.getSystemConfig().getFtpHost());
//        	request.setAttribute("result", sysDAO.serverInfoAdd(serverInfoForm));
    		request.setAttribute("result", frameServerInfoBO.serverInfoAdd(serverInfoForm));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward("result");
    }
   
    public ActionForward fileSystemDel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	String expiredTime = DateUtils.rollHour(DateUtils.getChar14(), -Integer.parseInt(SystemConfig.getSystemConfig().getExpiredDay()) * 24);
    	List<UploadForm> listUploadForm = frameUploadBO.expiredUploadAllList(expiredTime.substring(0, 8));
//    	System.out.println("listUploadForm.size=="+listUploadForm.size());
    	List<UploadForm> delList = new ArrayList<UploadForm>();//要过滤掉重要性文件
    	if(listUploadForm!=null && listUploadForm.size()>0) {
	    	for(UploadForm uploadForm: listUploadForm) {
	    		if(uploadForm.getFileStats().equals("0") && ("http://"+SystemConfig.getSystemConfig().getFtpHost()).equals(uploadForm.getFileSavePath())) {//普通
	    			delList.add(uploadForm);
	    		}
	    	}
	    	System.out.println("delList.size=="+delList.size());
	    	frameUploadBO.uploadDel(delList, false);// 删除
    	}
    	request.setAttribute("result", 0);
    	return mapping.findForward("result");
    }
    
    
    /**
     * 服务器文件格式转换工作
     * 
     */
    public ActionForward flvFormatWork(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	SysMonitor_windows sysInfo = new SysMonitor_windows();
    	try {
    		MonitorInfoBean mib = sysInfo.getMonitorInfoBean(SystemConfig.getSystemConfig().getLetter());
        	ServerInfoForm serverInfoForm = new ServerInfoForm();
    		serverInfoForm.setRatioCPU(Math.abs((int)mib.getCpuRatio()));
    		serverInfoForm.setRatioMEMORY((int)((mib.getTotalMemory()-mib.getFreeMemory())/mib.getTotalMemory()*100));
    		serverInfoForm.setUseMEMORY((mib.getTotalMemory()-mib.getFreeMemory())+"");
    		serverInfoForm.setRatioHARDDISK((int)((Double.parseDouble(mib.getHarddiskTotal())-Double.parseDouble(mib.getHarddiskFree()))/Double.parseDouble(mib.getHarddiskTotal())*100));
    		serverInfoForm.setUseHARDDISK(Double.parseDouble(mib.getHarddiskTotal())-Double.parseDouble(mib.getHarddiskFree())+"");
    		serverInfoForm.setLetter(SystemConfig.getSystemConfig().getLetter());
        	request.setAttribute("result", sysDAO.serverInfoAdd(serverInfoForm));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	request.setAttribute(Constants.JSP_MESSAGE, informationFrameForm);
		return mapping.findForward("result");
    }

	public void setFrameServerInfoBO(FrameServerInfoBO frameServerInfoBO) {
		this.frameServerInfoBO = frameServerInfoBO;
	}

	public void setFrameUploadBO(FrameUploadBO frameUploadBO) {
		this.frameUploadBO = frameUploadBO;
	}
}
