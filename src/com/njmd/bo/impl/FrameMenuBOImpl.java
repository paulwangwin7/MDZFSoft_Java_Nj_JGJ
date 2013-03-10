package com.njmd.bo.impl;

import java.util.List;

import com.njmd.bo.FrameMenuBO;
import com.njmd.dao.FrameMenuDAO;

public class FrameMenuBOImpl implements FrameMenuBO {
	private FrameMenuDAO frameMenuDAO;

	@SuppressWarnings("unchecked")
	@Override
	public List queryMenuAndUrlByUrlIds(String[] urlId) {
		// TODO Auto-generated method stub
		return frameMenuDAO.queryMenuAndUrlByUrlIds(urlId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List queryMenuAndUrl() {
		// TODO Auto-generated method stub
		return frameMenuDAO.queryMenuAndUrl();
	}

	public void setFrameMenuDAO(FrameMenuDAO frameMenuDAO) {
		this.frameMenuDAO = frameMenuDAO;
	}

}
