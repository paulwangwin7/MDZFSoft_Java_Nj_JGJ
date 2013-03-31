package com.njmd.bo;

import java.util.List;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.UploadForm;


public interface FrameUploadBO {
	/**
	 * 用户文件上传
	 * 
	 * @param uploadForm
	 * 			userId;			上传人id
	 * 			editId;			采集人id
	 * 			uploadName;		上传文件名
	 * 			playPath;		播放地址
	 * 			fileCreatetime;	文件创建时间
	 * 			showPath;		文件预览地址
	 * 			uploadTime;		上传时间
	 * 			fileState;		上传文件状态 A-有效；U-无效；F-过期
	 * 			tree1Id;		上传人部门id
	 * 			tree2Id;		上传人上级部门id
	 * 			fileStats;		文件重要性
	 * 			fileRemark;		文件备注说明
	 * 			ipAddr;			上传人IP地址
	 * 			fileSavePath;	文件保存查看前缀地址
	 * 			flvPath;		flv播放地址
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public int uploadSave(UploadForm uploadForm);

	/**
	 * 文件详情
	 * 
	 * @param uploadId	上传文件id
	 * @return
	 */
	public UploadForm uploadDetail(java.lang.Long uploadId);

	/**
	 * 文件加☆★
	 * 
	 * @param uploadId	上传文件id
	 * @param fileStats	1-重要；0-不重要；
	 * @return 0-成功； 1-失败 系统异常
	 */
	public int uploadStats(java.lang.Long uploadId, String fileStats);

	/**
	 * 文件备注修改
	 * 
	 * @param uploadId	上传文件id
	 * @param remark	文件备注说明
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public int uploadRemark(java.lang.Long uploadId, String remark);

	/**
	 * 文件删除（逻辑删除）
	 * 
	 * @param uploadList
	 * 			UploadForm.uploadId	需要删除的文件id
	 * @param deleteStats			false-保留重要；true-删除重要；
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public int uploadDel(List<UploadForm> uploadList, boolean deleteStats);

	/**
	 * 文件加☆★筛选列表
	 * 
	 * @param uploadIdArr
	 * @return List
	 * 			list.get(0)-重要性文件list； list.get(1)-非重要性文件list；
	 */
	@SuppressWarnings("unchecked")
	public List uploadStatsList(String[] uploadIdArr);

	/**
	 * 文件查询
	 * 
	 * @param uploadName		文件名 模糊查询
	 * @param treeId			部门id
	 * @param beginTime			查询开始时间
	 * @param endTime			查询截止时间
	 * @param uploadUserId		文件上传人
	 * @param fileCreateUserId	采集人
	 * @param fileStats			文件重要性 1-重要
	 * @param fileRemark		备注说明
	 * @param page
	 * 			pagecute 当前页 默认第1页
	 *			pageline 每页行数 默认10行
	 * @return
	 */
	public Page uploadManagerQuery(String uploadName, String treeId,
								String beginTime, String endTime, String uploadUserId,
								String fileCreateUserId, String fileStats, String fileRemark,
								Page page);

	/**
	 * 文件查询
	 * 
	 * @param uploadName		文件名 模糊查询
	 * @param treeId			部门id
	 * @param parentTreeId		上级部门id
	 * @param beginTime			查询开始时间
	 * @param endTime			查询截止时间
	 * @param uploadUserId		文件上传人
	 * @param fileCreateUserId	采集人
	 * @param fileStats			文件重要性 1-重要
	 * @param fileRemark		备注说明
	 * @param page
	 * 			pagecute 当前页 默认第1页
	 *			pageline 每页行数 默认10行
	 * @return
	 */
	public Page uploadListByTree(String uploadName, String treeId, String parentTreeId,
								String beginTime, String endTime, String uploadUserId,
								String fileCreateUserId, String fileStats, String fileRemark,
								Page page);

	/**
	 * 管理员文件查询
	 * 
	 * @param uploadName		文件名 模糊查询
	 * @param treeId			部门id
	 * @param parentTreeId		上级部门id
	 * @param beginTime			查询开始时间
	 * @param endTime			查询截止时间
	 * @param uploadUserId		文件上传人
	 * @param fileCreateUserId	采集人
	 * @param fileStats			文件重要性 1-重要
	 * @param fileRemark		备注说明
	 * @param page
	 * 			pagecute 当前页 默认第1页
	 *			pageline 每页行数 默认10行
	 * @return
	 */
	public Page uploadListByAdmin(String uploadName, String treeId, String parentTreeId,
								String beginTime, String endTime, String uploadUserId,
								String fileCreateUserId, String fileStats, String fileRemark,
								Page page);

	/**
	 * 我的上传列表
	 * 
	 * @param treeId		部门id
	 * @param parentTreeId	上级部门id
	 * @param uploadUserId	文件上传人id
	 * @param page
	 * 			pagecute 当前页 默认第1页
	 *			pageline 每页行数 默认10行
	 * @return
	 */
	public Page mineUploadList(String treeId, String parentTreeId, String uploadUserId, Page page);

	/**
	 * 过期文件列表（系统预删除的过期文件，所以给出的均为非加星的文件）
	 * 
	 * @param expiredDays 过期天数
	 * @return
	 */
	public List<UploadForm> expiredSysDekList(int expiredDays);

	/**
	 * 过期文件列表
	 * 
	 * @param expired 过期日期yyyyMMdd
	 * @return
	 */
	public List<UploadForm> expiredUploadAllList(String expired);
}
