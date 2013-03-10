package com.njmd.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.manager.pub.bean.NoticeForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.DateUtils;
import com.njmd.bo.FrameNoticeBO;
import com.njmd.dao.FrameNoticeDAO;
import com.njmd.pojo.FrameNotice;

public class FrameNoticeBOImpl implements FrameNoticeBO {
	private FrameNoticeDAO frameNoticeDAO;

	@Override
	public int noticeAdd(NoticeForm noticeForm) {
		int addResult;//0-添加成功；1-添加失败
		// TODO Auto-generated method stub
		FrameNotice frameNotice = new FrameNotice();
		frameNotice.setNoticeTitle(noticeForm.getNoticeTitle());
		frameNotice.setNoticeContent(noticeForm.getNoticeContent());
		frameNotice.setNoticeType(noticeForm.getNoticeType());
		frameNotice.setCreateTime(DateUtils.getChar14());
		frameNotice.setNoticeBegin(noticeForm.getNoticeBegin());
		frameNotice.setNoticeEnd(noticeForm.getNoticeEnd());
		addResult = frameNoticeDAO.save(frameNotice);
		return addResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NoticeForm> noticeAllList() {
		List<NoticeForm> noticeFormList = null;
		// TODO Auto-generated method stub
		List list = frameNoticeDAO.findAll();
		if(list!=null && list.size()>0) {
			noticeFormList = new ArrayList<NoticeForm>();
			for(Object frameNoticeObj: list) {
				FrameNotice frameNotice = (FrameNotice) frameNoticeObj;
				NoticeForm noticeForm = new NoticeForm();
				noticeForm.setNoticeId(frameNotice.getNoticeId());
				noticeForm.setNoticeTitle(frameNotice.getNoticeTitle());
				noticeForm.setNoticeContent(frameNotice.getNoticeContent());
				noticeForm.setNoticeType(frameNotice.getNoticeType());
				noticeForm.setCreateTime(frameNotice.getCreateTime());
				noticeForm.setNoticeBegin(frameNotice.getNoticeBegin());
				noticeForm.setNoticeEnd(frameNotice.getNoticeEnd());
				noticeForm.setTreeIdList(frameNotice.getTreeIdList());
//				noticeForm.setEditer(editer);
//				noticeForm.setTreeIdListStr(getTreeListName(noticeForm.getTreeIdList()));
				noticeFormList.add(noticeForm);
			}
		}
		return noticeFormList;
	}

	@Override
	public int noticeDelete(Long noticeId) {
		int deleteResult;//0-添加成功；1-添加失败
		// TODO Auto-generated method stub
		deleteResult = frameNoticeDAO.delete(frameNoticeDAO.findById(noticeId));
		return deleteResult;
	}

	@Override
	public NoticeForm noticeDetail(Long noticeId) {
		NoticeForm noticeForm = null;
		// TODO Auto-generated method stub
		FrameNotice frameNotice = frameNoticeDAO.findById(noticeId);
		if(frameNotice!=null) {
			noticeForm = new NoticeForm();
			noticeForm.setNoticeId(frameNotice.getNoticeId());
			noticeForm.setNoticeTitle(frameNotice.getNoticeTitle());
			noticeForm.setNoticeContent(frameNotice.getNoticeContent());
			noticeForm.setNoticeType(frameNotice.getNoticeType());
			noticeForm.setCreateTime(frameNotice.getCreateTime());
			noticeForm.setNoticeBegin(frameNotice.getNoticeBegin());
			noticeForm.setNoticeEnd(frameNotice.getNoticeEnd());
			noticeForm.setTreeIdList(frameNotice.getTreeIdList());
//			noticeForm.setEditer(editer);
//			noticeForm.setTreeIdListStr(getTreeListName(noticeForm.getTreeIdList()));
		}
		return noticeForm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NoticeForm> noticeList(UserForm userForm) {
		List<NoticeForm> noticeFormList = null;
		// TODO Auto-generated method stub
		if(userForm.getUserId()==0) {
			noticeFormList = noticeAllList();
		} else {
			List list = frameNoticeDAO.findAll();
			if(list!=null && list.size()>0) {
				noticeFormList = new ArrayList<NoticeForm>();
				for(Object frameNoticeObj: list) {
					FrameNotice frameNotice = (FrameNotice) frameNoticeObj;
						if(userTree(frameNotice.getTreeIdList().split(","), userForm.getTreeId())) {
						NoticeForm noticeForm = new NoticeForm();
						noticeForm.setNoticeId(frameNotice.getNoticeId());
						noticeForm.setNoticeTitle(frameNotice.getNoticeTitle());
						noticeForm.setNoticeContent(frameNotice.getNoticeContent());
						noticeForm.setNoticeType(frameNotice.getNoticeType());
						noticeForm.setCreateTime(frameNotice.getCreateTime());
						noticeForm.setNoticeBegin(frameNotice.getNoticeBegin());
						noticeForm.setNoticeEnd(frameNotice.getNoticeEnd());
						noticeForm.setTreeIdList(frameNotice.getTreeIdList());
	//					noticeForm.setEditer(editer);
	//					noticeForm.setTreeIdListStr(getTreeListName(noticeForm.getTreeIdList()));
						noticeFormList.add(noticeForm);
					}
				}
			}
		}
		return noticeFormList;
	}

	@Override
	public int noticeModify(NoticeForm noticeForm) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 判断用户是否在部门列表中
	 * @param treeIdList 部门列表数组
	 * @param userForm 用户form
	 * @return
	 */
	public boolean userTree(String[] treeIdList, java.lang.Long treeId)
	{
		for(int i=0; i<treeIdList.length; i++)
		{
			if(treeIdList[i].equals(treeId+""))
			{
				return true;
			}
		}
		return false;
	}

	public void setFrameNoticeDAO(FrameNoticeDAO frameNoticeDAO) {
		this.frameNoticeDAO = frameNoticeDAO;
	}
}
