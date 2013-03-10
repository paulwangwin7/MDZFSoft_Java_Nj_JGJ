package com.njmd.bo.impl;

import java.util.List;

import com.manager.pub.bean.ServerInfoForm;
import com.manager.pub.util.DateUtils;
import com.njmd.bo.FrameServerInfoBO;
import com.njmd.dao.FrameServerinfoDAO;
import com.njmd.pojo.FrameServerinfo;

public class FrameServerInfoBOImpl implements FrameServerInfoBO {
	private FrameServerinfoDAO frameServerinfoDAO;

	public int serverInfoAdd(ServerInfoForm serverInfoForm) {
		// TODO Auto-generated method stub
		FrameServerinfo frameServerinfo = new FrameServerinfo();
		frameServerinfo.setRatioCpu(new Long(serverInfoForm.getRatioCPU()));
		frameServerinfo.setRatioMemory(new Long(serverInfoForm.getRatioMEMORY()));
		frameServerinfo.setUseMemory(serverInfoForm.getUseMEMORY());
		frameServerinfo.setRatioHarddisk(new Long(serverInfoForm.getRatioHARDDISK()));
		frameServerinfo.setUseHarddisk(serverInfoForm.getUseHARDDISK());
		frameServerinfo.setLetter(serverInfoForm.getLetter());
		frameServerinfo.setCreateTime(DateUtils.getChar14());
		frameServerinfo.setSaveIp(serverInfoForm.getSaveIp());
		return frameServerinfoDAO.save(frameServerinfo);
	}

	public ServerInfoForm serverInfoDetail(String serverInfoId) {
		ServerInfoForm serverInfoForm = null;
		// TODO Auto-generated method stub
		FrameServerinfo frameServerinfo = frameServerinfoDAO.findById(new Long(serverInfoId));
		if(frameServerinfo!=null) {
			serverInfoForm = new ServerInfoForm();
			serverInfoForm = setServerInfoFormByFrameServerinfo(serverInfoForm, frameServerinfo);
		}
		return serverInfoForm;
	}

	public List<ServerInfoForm> serverInfoListByDay(String statTime) {
		// TODO Auto-generated method stub
		return frameServerinfoDAO.serverInfoListByDay(statTime);
	}

	public List<ServerInfoForm> serverInfoListByMonth(String month) {
		// TODO Auto-generated method stub
		return frameServerinfoDAO.serverInfoListByMonth(month);
	}

	public List<ServerInfoForm> serverInfoListByWeek(String beginTime, String endTime) {
		// TODO Auto-generated method stub
		return frameServerinfoDAO.serverInfoListByWeek(beginTime, endTime);
	}

	public List<ServerInfoForm> serverInfoListByYear(String year) {
		// TODO Auto-generated method stub
		return frameServerinfoDAO.serverInfoListByYear(year);
	}

	private ServerInfoForm setServerInfoFormByFrameServerinfo(ServerInfoForm serverInfoForm, FrameServerinfo frameServerinfo) {
		serverInfoForm.setServerinfoId(frameServerinfo.getServerinfoId());
		serverInfoForm.setRatioCPU(Integer.parseInt(frameServerinfo.getRatioCpu()+""));
		serverInfoForm.setRatioMEMORY(Integer.parseInt(frameServerinfo.getRatioMemory()+""));
		serverInfoForm.setUseMEMORY(frameServerinfo.getUseMemory());
		serverInfoForm.setRatioHARDDISK(Integer.parseInt(frameServerinfo.getRatioHarddisk()+""));
		serverInfoForm.setUseHARDDISK(frameServerinfo.getUseHarddisk());
		serverInfoForm.setLetter(frameServerinfo.getLetter());
		serverInfoForm.setCreateTime(frameServerinfo.getCreateTime());
		return serverInfoForm;
	}

	public void setFrameServerinfoDAO(FrameServerinfoDAO frameServerinfoDAO) {
		this.frameServerinfoDAO = frameServerinfoDAO;
	}

}
