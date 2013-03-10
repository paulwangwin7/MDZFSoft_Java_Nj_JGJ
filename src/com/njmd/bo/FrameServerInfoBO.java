package com.njmd.bo;

import java.util.List;

import com.manager.pub.bean.ServerInfoForm;


public interface FrameServerInfoBO {
	/**
	 * 系统状态添加
	 * 
	 * @param serverInfoForm
	 * 			ratioCPU		CPU占用率
	 * 			ratioMEMORY		内存占用率
	 * 			useMEMORY		内存使用（单位：kb）
	 * 			ratioHARDDISK	硬盘占用率
	 * 			useHARDDISK		硬盘使用（单位：gb)
	 * 			letter			硬盘盘符
	 * 			createTime		记录时间
	 * 			saveIp			服务器ip
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public int serverInfoAdd(ServerInfoForm serverInfoForm);

	/**
	 * 系统状态列表查询 —— 日查询
	 * 
	 * @param statTime	统计日期yyyyMMdd
	 * @return List<ServerInfoForm>
	 */
	public List<ServerInfoForm> serverInfoListByDay(String statTime);

	/**
	 * 系统状态列表查询 —— 周查询
	 * 
	 * @param beginTime 查询起始时间yyyyMMdd
	 * @param endTime 查询截止时间yyyyMMdd
	 * @return List<ServerInfoForm>
	 */
	public List<ServerInfoForm> serverInfoListByWeek(String beginTime, String endTime);

	/**
	 * 系统状态列表查询 —— 月查询
	 * 
	 * @param month	查询月yyyyMM
	 * @return List<ServerInfoForm>
	 */
	public List<ServerInfoForm> serverInfoListByMonth(String month);

	/**
	 * 系统状态列表查询 —— 年查询
	 * 
	 * @param month	查询年yyyy
	 * @return List<ServerInfoForm>
	 */
	public List<ServerInfoForm> serverInfoListByYear(String year);

	/**
	 * 系统状态详情
	 * 
	 * @param serverInfoId
	 * @return ServerInfoForm
	 */
	public ServerInfoForm serverInfoDetail(String serverInfoId);
}
