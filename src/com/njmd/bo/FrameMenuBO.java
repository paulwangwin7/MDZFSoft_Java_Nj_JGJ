package com.njmd.bo;

import java.util.List;

public interface FrameMenuBO {
	/**
	 * 查询所有菜单及菜单下对应的所有功能url
	 * 
	 * @return list_menuAndurl [ ['菜单1名称',[urlForm,urlForm...]],
	 *         ['菜单2名称',[urlForm,urlForm...]] ... ]
	 */
	@SuppressWarnings("unchecked")
	public List queryMenuAndUrlByUrlIds(String[] urlId);

	@SuppressWarnings("unchecked")
	public List queryMenuAndUrl();
}
