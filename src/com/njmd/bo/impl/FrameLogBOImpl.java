package com.njmd.bo.impl;

import com.manager.pub.bean.LogForm;
import com.manager.pub.util.DateUtils;
import com.njmd.bo.FrameLogBO;
import com.njmd.dao.FrameLogDAO;
import com.njmd.pojo.FrameLog;

public class FrameLogBOImpl implements FrameLogBO {
	private FrameLogDAO frameLogDAO;

	@Override
	public int logAdd(LogForm logForm) {
		int addResult;
		// TODO Auto-generated method stub
		FrameLog frameLog = new FrameLog();
		frameLog.setCreateTime(DateUtils.getChar14());
		frameLog.setUserId(logForm.getUserId());
		frameLog.setUserCode(logForm.getUserCode());
		frameLog.setTreeId(logForm.getTreeId());
		frameLog.setTreeName(logForm.getTreeName());
		frameLog.setRoleId(logForm.getRoleId());
		frameLog.setRoleName(logForm.getRoleName());
		frameLog.setLogDesc(logForm.getLogDesc());
		frameLog.setIpAdd(logForm.getIpAddr());
		addResult = frameLogDAO.save(frameLog);
		return addResult;
	}

	public void setFrameLogDAO(FrameLogDAO frameLogDAO) {
		this.frameLogDAO = frameLogDAO;
	}
}
