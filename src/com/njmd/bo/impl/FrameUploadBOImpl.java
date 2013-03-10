package com.njmd.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.util.DateUtils;
import com.manager.pub.util.FileUtils;
import com.njmd.bo.FrameUploadBO;
import com.njmd.dao.FrameUploadDAO;
import com.njmd.pojo.FrameUpload;

public class FrameUploadBOImpl implements FrameUploadBO {
	private FrameUploadDAO frameUploadDAO;

	public Page mineUploadList(String treeId, String parentTreeId,
			String uploadUserId, Page page) {
		// TODO Auto-generated method stub
		return frameUploadDAO.mineUploadList(treeId, parentTreeId, uploadUserId, page);
	}

	public int uploadDel(List<UploadForm> uploadList, boolean deleteStats) {
		// TODO Auto-generated method stub
		for(UploadForm uploadForm: uploadList) {
			FrameUpload frameUpload = frameUploadDAO.findById(uploadForm.getUploadId());
			frameUpload.setFileState("F");//删除过期
			new FileUtils().deleteFile(SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath());
			frameUploadDAO.attachDirty(frameUpload);
		}
		return 0;
	}

	public UploadForm uploadDetail(Long uploadId) {
		// TODO Auto-generated method stub
		return frameUploadDAO.uploadDetail(uploadId);
	}

	public Page uploadListByTree(String uploadName, String treeId,
			String parentTreeId, String beginTime, String endTime,
			String uploadUserId, String fileCreateUserId, String fileStats,
			String fileRemark, Page page) {
		// TODO Auto-generated method stub
		return frameUploadDAO.uploadListByTree(uploadName, treeId, parentTreeId, beginTime, endTime, uploadUserId, fileCreateUserId, fileStats, fileRemark, page);
	}

	public Page uploadListByAdmin(String uploadName, String treeId,
			String parentTreeId, String beginTime, String endTime,
			String uploadUserId, String fileCreateUserId, String fileStats,
			String fileRemark, Page page) {
		// TODO Auto-generated method stub
		return frameUploadDAO.uploadListByAdmin(uploadName, treeId, parentTreeId, beginTime, endTime, uploadUserId, fileCreateUserId, fileStats, fileRemark, page);
	}

	public int uploadRemark(Long uploadId, String remark) {
		// TODO Auto-generated method stub
		FrameUpload frameUpload = frameUploadDAO.findById(uploadId);
		frameUpload.setFileRemark(remark);
		return frameUploadDAO.attachDirty(frameUpload);
	}

	public int uploadSave(UploadForm uploadForm) {
		int saveResult;//
		// TODO Auto-generated method stub
		FrameUpload frameUpload = new FrameUpload();
		frameUpload.setUserId(uploadForm.getUserId());
		frameUpload.setEditId(uploadForm.getEditId());
		frameUpload.setUploadName(uploadForm.getUploadName());
		frameUpload.setPlayPath(uploadForm.getPlayPath());
		frameUpload.setShowPath(uploadForm.getShowPath());
		frameUpload.setFileCreatetime(uploadForm.getFileCreatetime());
		frameUpload.setFileStats("0");
		frameUpload.setUploadTime(DateUtils.getChar14());
		frameUpload.setFileState(uploadForm.getFileState());
		frameUpload.setTree2Id(uploadForm.getTree2Id());
		frameUpload.setTree1Id(uploadForm.getTree1Id());
		frameUpload.setFileRemark(uploadForm.getFileRemark());
		frameUpload.setIpAddr(uploadForm.getIpAddr());
		frameUpload.setRealPath(uploadForm.getFileSavePath());
		frameUpload.setFlvPath(uploadForm.getFlvPath());
		saveResult = frameUploadDAO.save(frameUpload);
		return saveResult;
	}

	public int uploadStats(Long uploadId, String fileStats) {
		// TODO Auto-generated method stub
		FrameUpload frameUpload = frameUploadDAO.findById(uploadId);
		frameUpload.setFileStats(fileStats);
		return frameUploadDAO.attachDirty(frameUpload);
	}

	@SuppressWarnings("unchecked")
	public List uploadStatsList(String[] uploadIdArr) {
		// TODO Auto-generated method stub
		List list = null;//list.get(0)-重要性文件list； list.get(1)-非重要性文件list；
		List<UploadForm> statsList = null;
		List<UploadForm> unStatsList = null;
		for(String uploadId: uploadIdArr) {
			UploadForm uploadForm = frameUploadDAO.uploadDetail(new Long(uploadId));
			if (uploadForm.getFileStats().equals("1")) {//数据库中该字段 文件上传重要性 0-普通；1-重要
				if(statsList==null) {
					statsList = new ArrayList<UploadForm>();
				}
				statsList.add(uploadForm);
			} else {
				if(unStatsList==null) {
					unStatsList = new ArrayList<UploadForm>();
				}
				unStatsList.add(uploadForm);
			}
		}
		list = new ArrayList();
		list.add(statsList);
		list.add(unStatsList);
		return list;
	}

	public List<UploadForm> expiredSysDekList(int expiredDays) {
		// TODO Auto-generated method stub
		return frameUploadDAO.expiredSysDekList(expiredDays);
	}

	public List<UploadForm> expiredUploadAllList(String expired) {
		// TODO Auto-generated method stub
		return frameUploadDAO.expiredUploadAllList(expired);
	}

	public void setFrameUploadDAO(FrameUploadDAO frameUploadDAO) {
		this.frameUploadDAO = frameUploadDAO;
	}
}
