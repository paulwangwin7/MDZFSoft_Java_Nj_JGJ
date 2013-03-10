package com.njmd.bo.impl;

import java.util.List;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.RoleForm;
import com.manager.pub.util.DateUtils;
import com.njmd.bo.FrameRoleBO;
import com.njmd.dao.FrameRoleDAO;
import com.njmd.pojo.FrameRole;

public class FrameRoleBOImpl implements FrameRoleBO {
	private FrameRoleDAO frameRoleDAO;

	public Page getRoleList(Page page) {
		// TODO Auto-generated method stub
		return frameRoleDAO.getRoleList(page);
	}

	public Page getRoleListAll() {
		// TODO Auto-generated method stub
		return frameRoleDAO.getRoleList(null);
	}

	@SuppressWarnings("unchecked")
	public int roleAdd(RoleForm roleForm) {
		int addResult;//0-成功； 1-失败 系统异常； 2-角色名称重名
		// TODO Auto-generated method stub
		List list = frameRoleDAO.findByRoleName(roleForm.getRoleName());
		if(list!=null && list.size()>0) {
			addResult = 2;
		} else {
			FrameRole frameRole = new FrameRole();
			frameRole.setRoleName(roleForm.getRoleName());
			frameRole.setRoleDesc(roleForm.getRoleDesc());
			frameRole.setRoleState(roleForm.getRoleState());
			frameRole.setCreateUser(new Long(roleForm.getCreateUser()));
			frameRole.setCreateTime(DateUtils.getChar14());
			frameRole.setTreeId(new Long(roleForm.getTreeId()));
			frameRole.setUrlIdList(roleForm.getUrlIdList());
			addResult = frameRoleDAO.save(frameRole);
		}
		return addResult;
	}

	public int roleDelete(RoleForm roleForm) {
		int deleteResult;//0-删除成功；1-删除失败 系统超时~； 2-删除失败 还有其他用户正在使用该角色；
		// TODO Auto-generated method stub
		if(frameRoleDAO.existenceUser(roleForm.getRoleId())) {
			deleteResult = 2;
		} else {
			deleteResult = frameRoleDAO.delete(frameRoleDAO.findById(roleForm.getRoleId()));
		}
		return deleteResult;
	}

	public RoleForm roleDetail(RoleForm roleForm) {
		RoleForm role = null;
		// TODO Auto-generated method stub
		FrameRole frameRole = frameRoleDAO.findById(roleForm.getRoleId());
		if(frameRole!=null) {
			role = setRoleFormByFrameRole(roleForm, frameRole);
		}
		return role;
	}

	public int roleModify(RoleForm roleForm) {
		int modifyResult;//0-成功； 1-失败 系统异常； 2-角色名称重名
		// TODO Auto-generated method stub
		FrameRole frameRole = new FrameRole();
		frameRole.setRoleId(roleForm.getRoleId());
		frameRole.setRoleName(roleForm.getRoleName());
		if(frameRoleDAO.roleNameRepeat(frameRole)) {
			modifyResult = 2;
		} else {
			frameRole = frameRoleDAO.findById(roleForm.getRoleId());
			frameRole.setRoleName(roleForm.getRoleName());
			frameRole.setRoleDesc(roleForm.getRoleDesc());
			frameRole.setRoleState(roleForm.getRoleState());
			frameRole.setCreateUser(new Long(roleForm.getCreateUser()));
			frameRole.setCreateTime(DateUtils.getChar14());
			frameRole.setTreeId(new Long(roleForm.getTreeId()));
			frameRole.setUrlIdList(roleForm.getUrlIdList());
			modifyResult = frameRoleDAO.attachDirty(frameRole);
		}
		return modifyResult;
	}

	private RoleForm setRoleFormByFrameRole(RoleForm roleForm, FrameRole frameRole) {
		roleForm.setRoleId(frameRole.getRoleId());
		roleForm.setRoleName(frameRole.getRoleName());
		roleForm.setRoleDesc(frameRole.getRoleDesc());
		roleForm.setRoleState(frameRole.getRoleState());
		roleForm.setCreateUser(frameRole.getCreateUser()+"");
		roleForm.setCreateTime(frameRole.getCreateTime());
		roleForm.setTreeId(frameRole.getTreeId()+"");
		roleForm.setUrlIdList(frameRole.getUrlIdList());
		return roleForm;
	}

	public void setFrameRoleDAO(FrameRoleDAO frameRoleDAO) {
		this.frameRoleDAO = frameRoleDAO;
	}

}
