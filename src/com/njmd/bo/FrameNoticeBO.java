package com.njmd.bo;

import java.util.List;

import com.manager.pub.bean.NoticeForm;
import com.manager.pub.bean.UserForm;


public interface FrameNoticeBO {
	/**
	 * 公告添加
	 * 
	 * @param noticeForm
	 * 			noticeTitle		公告标题
	 * 			noticeContent	公告内容
	 * 			noticeType		公告类型 1-首页公告；2-消息公告
	 * 			createTime		公告创建时间
	 * 			noticeBegin		公告开始时间 格式xxxx年xx月xx日xx时xx秒 24小时制
	 * 			noticeEnd		公告结束时间
	 * 			userId			公告发布人id
	 * 			treeIdList		公告部门
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public int noticeAdd(NoticeForm noticeForm);

	/**
	 * 公告列表
	 * 
	 * @param userForm
	 * 			userId	用户id
	 * 			treeId	部门id
	 * @return List<NoticeForm>
	 */
	public List<NoticeForm> noticeList(UserForm userForm);

	/**
	 * 所有公告
	 * 
	 * @return List<NoticeForm>
	 */
	public List<NoticeForm> noticeAllList();

	/**
	 * 公告详情
	 * 
	 * @param noticeId 公告id
	 * @return NoticeForm
	 */
	public NoticeForm noticeDetail(java.lang.Long noticeId);

	/**
	 * 公告修改
	 * 
	 * @param noticeForm
	 * 			noticeId		公告id
	 * 			noticeTitle		公告标题
	 * 			noticeContent	公告内容
	 * 			noticeType		公告类型 1-首页公告；2-消息公告
	 * 			createTime		公告创建时间
	 * 			noticeBegin		公告开始时间 格式xxxx年xx月xx日xx时xx秒 24小时制
	 * 			noticeEnd		公告结束时间
	 * 			userId			公告发布人id
	 * 			treeIdList		公告部门
	 * @return 0-修改成功； 1-修改失败 系统异常;
	 */
	public int noticeModify(NoticeForm noticeForm);

	/**
	 * 公告删除
	 * 
	 * @param noticeId	公告id
	 * @return	0-删除成功； 1-删除失败 系统异常;
	 */
	public int noticeDelete(java.lang.Long noticeId);
}
