package com.njmd.bo;

import com.manager.pub.bean.LogForm;

public interface FrameLogBO {
	/**
	 * 行为日志添加
	 * @param logForm
	 * 			userId		被记录人id
	 * 			userCode	警员编号
	 * 			treeId		部门id
	 * 			treeName	部门名称
	 * 			roleId		角色id
	 * 			roleName	角色名称
	 * 			logDesc		日志描述
	 * 			ipAddr		id地址
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public int logAdd(LogForm logForm);
}
