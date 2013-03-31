package com.njmd.bo.impl;

import java.util.List;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.DateUtils;
import com.njmd.bo.FrameUserBO;
import com.njmd.dao.FrameUserDAO;
import com.njmd.pojo.FrameUser;

public class FrameUserBOImpl implements FrameUserBO {
	private FrameUserDAO frameUserDAO;

	public Page getUserList(UserForm user, String queryTreeId, Page page) {
		// TODO Auto-generated method stub
		FrameUser instance = new FrameUser();
		if(user.getUserName()!=null) {
			instance.setUserName(user.getUserName());
		}
		if(user.getUserCode()!=null) {
			instance.setUserCode(user.getUserCode());
		}
		if(user.getSex()!=null) {
			instance.setSex(user.getSex());
		}
		if(user.getTreeId()>0) {
			instance.setTreeId(user.getTreeId());
		}
		return frameUserDAO.getUserList(instance, queryTreeId, page);
	}

	public Page getUserListByAdmin(UserForm user, String queryTreeId, Page page) {
		// TODO Auto-generated method stub
		FrameUser instance = new FrameUser();
		if(user.getUserName()!=null) {
			instance.setUserName(user.getUserName());
		}
		if(user.getUserCode()!=null) {
			instance.setUserCode(user.getUserCode());
		}
		if(user.getSex()!=null) {
			instance.setSex(user.getSex());
		}
		if(user.getTreeId()>0) {
			instance.setTreeId(user.getTreeId());
		}
		return frameUserDAO.getUserListByAdmin(instance, queryTreeId, page);
	}

	public Page getUserList(Page page) {
		// TODO Auto-generated method stub
		return frameUserDAO.getUserList(page);
	}

	public Page getUserListByTree(Long treeId, Page page) {
		return frameUserDAO.findByTree(treeId, page);
	}

	public UserForm userLogin(UserForm user) {
		// TODO Auto-generated method stub
		FrameUser frameUser = new FrameUser();
		frameUser.setLoginName(user.getLoginName());
		frameUser.setLoginPswd(user.getLoginPswd());
		return frameUserDAO.userLogin(frameUser);
	}

	@SuppressWarnings("unchecked")
	public int userRegister(UserForm userForm) {
		int registerResult;//0-成功； 1-失败 系统异常； 2-登录账号重名
		// TODO Auto-generated method stub
		List list = frameUserDAO.findByLoginName(userForm.getLoginName());
		if(list!=null && list.size()>0) {
			registerResult = 2;
		} else {
			FrameUser frameUser = new FrameUser();
			frameUser.setLoginName(userForm.getLoginName());
			frameUser.setLoginPswd(userForm.getLoginPswd());
			frameUser.setUserName(userForm.getUserName());
			frameUser.setUserCode(userForm.getUserCode());
			frameUser.setSex(userForm.getSex());
			frameUser.setUserIdcard(userForm.getUserIdCard());
			frameUser.setCardTypeid(userForm.getCard_typeId());
			frameUser.setCardCode(userForm.getCardCode());
			frameUser.setTreeId(userForm.getTreeId());
			frameUser.setRoleId(userForm.getRoleId());
			frameUser.setCreateTime(DateUtils.getChar14());
			frameUser.setUserState(userForm.getUserState());
			if(!userForm.getRoleType().equals("")) {
				frameUser.setRoleType(new Long(userForm.getRoleType()));
			}
			registerResult = frameUserDAO.save(frameUser);
		}
		return registerResult;
	}

	public int userModify(UserForm userForm) {
		int modifyResult;//0-成功； 1-失败 系统异常； 2-登录账号重名
		FrameUser frameUser = new FrameUser();
		frameUser.setLoginName(userForm.getLoginName());
		frameUser.setUserId(userForm.getUserId());
		// TODO Auto-generated method stub
		if(frameUserDAO.loginNameRepeat(frameUser)) {
			modifyResult = 2;
		} else {
			frameUser = frameUserDAO.findById(userForm.getUserId());
			frameUser.setLoginName(userForm.getLoginName());
			frameUser.setUserName(userForm.getUserName());
			frameUser.setUserCode(userForm.getUserCode());
			frameUser.setSex(userForm.getSex());
			frameUser.setUserIdcard(userForm.getUserIdCard());
			frameUser.setCardTypeid(userForm.getCard_typeId());
			frameUser.setCardCode(userForm.getCardCode());
			frameUser.setTreeId(userForm.getTreeId());
			frameUser.setRoleId(userForm.getRoleId());
			frameUser.setUserState(userForm.getUserState());
			if(!userForm.getRoleType().equals("")) {
				frameUser.setRoleType(new Long(userForm.getRoleType()));
			}
			modifyResult = frameUserDAO.attachDirty(frameUser);
		}
		return modifyResult;
	}

	@SuppressWarnings("unchecked")
	public int pswdModify(UserForm userForm, String oldPswd) {
		int modifyResult = 1;//0-成功； 1-失败 系统异常； 2-旧密码不正确
		// TODO Auto-generated method stub
		FrameUser frameUser = new FrameUser();
		frameUser.setLoginPswd(oldPswd);
		frameUser.setLoginName(userForm.getLoginName());
		List list = frameUserDAO.findByExample(frameUser);
		if(list==null || list.size()==0) {
			modifyResult = 2;
		} else {
			frameUser = (FrameUser) list.get(0);
			frameUser.setLoginPswd(userForm.getLoginPswd());
			modifyResult = frameUserDAO.attachDirty(frameUser);
		}
		return modifyResult;
	}

	public UserForm userDetail(UserForm userForm) {
		// TODO Auto-generated method stub
		return frameUserDAO.userDetailById(userForm.getUserId());
	}

	public UserForm userById(UserForm userForm) {
		// TODO Auto-generated method stub
		return frameUserDAO.findDetailById(userForm.getUserId());
	}

	public void setFrameUserDAO(FrameUserDAO frameUserDAO) {
		this.frameUserDAO = frameUserDAO;
	}
}
