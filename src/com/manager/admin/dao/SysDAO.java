package com.manager.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import com.manager.pub.bean.NoticeForm;
import com.manager.pub.bean.RemarkForm;
import com.manager.pub.bean.RoleForm;
import com.manager.pub.bean.ServerInfoForm;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.bean.UrlForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.Comment;
import com.manager.pub.util.DateUtils;

public class SysDAO {
	private static Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private static ResultSet rs = null;
	private BasicDataSource dataSource;
	public static BasicDataSource staticDataSource;
	private List<NoticeForm> list_noticeForm = null;
	private NoticeForm noticeForm=null;
	private List<ServerInfoForm> list_serverInfo = null;
	private ServerInfoForm serverInfo = null;
	private List<RemarkForm> list_remark = null;
	private RemarkForm remarkForm = null;


	/**
	 * 日志添加
	 * @param logForm
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public int noticeAdd(NoticeForm noticeForm)
	{
		try
		{
			conn = dataSource.getConnection();
			String insertSQL = "insert into frame_notice(notice_id,notice_title,notice_content,notice_type,create_time,notice_begin,notice_end)";
			insertSQL += " values(seq_notice_id.nextval,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setString(1, noticeForm.getNoticeTitle());
			preparedStatement.setString(2, noticeForm.getNoticeContent());
			preparedStatement.setString(3, noticeForm.getNoticeType());
			preparedStatement.setString(4, DateUtils.getChar14());
			preparedStatement.setString(5, noticeForm.getNoticeBegin());
			preparedStatement.setString(6, noticeForm.getNoticeEnd());
			preparedStatement.executeUpdate();
			return 0;
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}
	
	
	/**
	 * 公告查询list
	 * @param NoticeForm
	 */
	public List<NoticeForm> noticeManager(UserForm userForm){	
		list_noticeForm = null;
		try{
			String querySQL = "select t.*,";
			querySQL+= " (select user_name from frame_user where user_id=t.user_id) as editer";
			querySQL+= " from FRAME_NOTICE t order by t.create_time desc";
			conn=dataSource.getConnection();	
			preparedStatement=conn.prepareStatement(querySQL);
			rs=preparedStatement.executeQuery();
			list_noticeForm = new ArrayList<NoticeForm>();
			while(rs.next()){
				if(userTree(rs.getString("tree_id_list").split(","),userForm) || userForm.getUserId()==0)
				{
					noticeForm = new NoticeForm();
					noticeForm.setNoticeId(rs.getLong("notice_id"));
					noticeForm.setNoticeTitle(rs.getString("notice_title"));
					noticeForm.setNoticeContent(rs.getString("notice_content"));
					noticeForm.setNoticeType(rs.getString("notice_type"));
					noticeForm.setCreateTime(rs.getString("create_time"));
					noticeForm.setNoticeBegin(rs.getString("notice_begin"));
					noticeForm.setNoticeEnd(rs.getString("notice_end"));
					noticeForm.setTreeIdList(rs.getString("tree_id_list"));
					noticeForm.setEditer(rs.getString("editer"));
					noticeForm.setTreeIdListStr(getTreeListName(noticeForm.getTreeIdList()));
					list_noticeForm.add(noticeForm);
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_noticeForm;		
	}
	
	
	/**
	 * 管理员公告查询list
	 * @param NoticeForm
	 */
	public List<NoticeForm> adminNoticeManager(UserForm userForm){	
		list_noticeForm = null;
		try{
			String querySQL="select * from FRAME_NOTICE where notice_type = 1 order by create_time desc";
			conn=dataSource.getConnection();	
			preparedStatement=conn.prepareStatement(querySQL);
			rs=preparedStatement.executeQuery();
			list_noticeForm = new ArrayList<NoticeForm>();
			while(rs.next()){
				noticeForm = new NoticeForm();
				noticeForm.setNoticeId(rs.getLong("notice_id"));
				noticeForm.setNoticeTitle(rs.getString("notice_title"));
				noticeForm.setNoticeContent(rs.getString("notice_content"));
				noticeForm.setNoticeType(rs.getString("notice_type"));
				noticeForm.setCreateTime(rs.getString("create_time"));
				noticeForm.setNoticeBegin(rs.getString("notice_begin"));
				noticeForm.setNoticeEnd(rs.getString("notice_end"));
				list_noticeForm.add(noticeForm);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_noticeForm;		
	}
	
	/**
	 * 公告查询noticeForm
	 * @param NoticeForm
	 */
	public NoticeForm queryNotice(Long noticeid){
		noticeForm = null;
		Connection conn_ = null;
		PreparedStatement preparedStatement_ = null;
		ResultSet rs_ = null;
		try{
			String querySQL="select t.*,(select user_name from frame_user where user_id=t.user_id) as editer from FRAME_NOTICE t where t.notice_id="+noticeid;
			conn_=dataSource.getConnection();	
			preparedStatement_=conn_.prepareStatement(querySQL);
			rs_=preparedStatement_.executeQuery();
			while(rs_.next()){
				noticeForm = new NoticeForm();
				noticeForm.setNoticeId(rs_.getLong("notice_id"));
				noticeForm.setNoticeTitle(rs_.getString("notice_title"));
				noticeForm.setNoticeContent(rs_.getString("notice_content"));
				noticeForm.setNoticeType(rs_.getString("notice_type"));
				noticeForm.setCreateTime(rs_.getString("create_time"));
				noticeForm.setNoticeBegin(rs_.getString("notice_begin"));
				noticeForm.setNoticeEnd(rs_.getString("notice_end"));
				noticeForm.setTreeIdList(rs_.getString("tree_id_list"));
				noticeForm.setEditer(rs_.getString("editer"));
				noticeForm.setTreeIdListStr(getTreeListName(noticeForm.getTreeIdList()));
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
//			Comment.closeConnection(conn, preparedStatement, rs_);
			try{
				if(rs_!=null) {
					rs_.close();
				}
			} catch(Exception ex) {
				
			}
			try{
				if(preparedStatement_!=null) {
					preparedStatement_.close();
				}
			} catch(Exception ex) {
				
			}
			try{
				if(conn_!=null) {
					conn_.close();
				}
			} catch(Exception ex) {
				
			}
		}
		return noticeForm;		
	}
	
	/**
	 * 公告添加
	 * @param NoticeForm
	 * @return 0-添加成功； 1-添加失败 系统异常; 2-添加失败 没有选择部门
	 */
	public int noticeManagerAdd(NoticeForm noticeForm){
		try{
			String addSQL="insert into FRAME_NOTICE(notice_id,notice_title,notice_content,notice_type,create_time,notice_begin,notice_end,user_id,tree_id_list)";
			       addSQL+=" values(seq_notice_id.nextval,?,?,?,?,?,?,?,?)";
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			
				preparedStatement=conn.prepareStatement(addSQL);
				preparedStatement.setString(1, noticeForm.getNoticeTitle());
				preparedStatement.setString(2, noticeForm.getNoticeContent());
				preparedStatement.setString(3, noticeForm.getNoticeType());
				preparedStatement.setString(4, DateUtils.getChar14());
				preparedStatement.setString(5, noticeForm.getNoticeBegin());
				preparedStatement.setString(6, noticeForm.getNoticeEnd());
				preparedStatement.setLong(7, noticeForm.getUserId());
				preparedStatement.setString(8, noticeForm.getTreeIdList());
				preparedStatement.executeUpdate();
			conn.commit();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}
	
	/**
	 * 公告添加
	 * @param NoticeForm
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public int userNoticeManagerAdd(NoticeForm noticeForm){
		try{
			String addSQL="insert into FRAME_NOTICE(notice_id,notice_title,notice_content,notice_type,create_time,notice_begin,notice_end)";
			       addSQL+=" values(seq_notice_id.nextval,?,?,?,?,?,?)";
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			preparedStatement=conn.prepareStatement(addSQL);
			preparedStatement.setString(1, noticeForm.getNoticeTitle());
			preparedStatement.setString(2, noticeForm.getNoticeContent());
			preparedStatement.setString(3, noticeForm.getNoticeType());
			preparedStatement.setString(4, DateUtils.getChar14());
			preparedStatement.setString(5, noticeForm.getNoticeBegin());
			preparedStatement.setString(6, noticeForm.getNoticeEnd());
			System.out.println(addSQL);
			preparedStatement.executeUpdate();
			conn.commit();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}
	
	/**
	 * 公告删除
	 * @param NoticeForm
	 * @return 0-删除成功； 1-删除失败 系统异常;
	 */
	public int noticeManagerDel(Long noticeid){
		try{
			String delSQL="delete from FRAME_NOTICE where notice_id=?";
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			preparedStatement=conn.prepareStatement(delSQL);
			preparedStatement.setLong(1, noticeid);
			preparedStatement.executeUpdate();
			conn.commit();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}
	
	/**
	 * 公告修改
	 * @param NoticeForm
	 * @return 0-修改成功； 1-修改失败 系统异常;
	 */
	public int noticeManagerMdf(NoticeForm noticeForm){
		try{
			String alterSQL="update FRAME_NOTICE set notice_title=?,notice_content=?,notice_type=?,create_time=?,notice_begin=?,notice_end=?";
			       alterSQL+=" where notice_id=?";
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			preparedStatement=conn.prepareStatement(alterSQL);
			preparedStatement.setString(1, noticeForm.getNoticeTitle());
			preparedStatement.setString(2, noticeForm.getNoticeContent());
			preparedStatement.setString(3, noticeForm.getNoticeType());
			preparedStatement.setString(4, DateUtils.getChar14());
			preparedStatement.setString(5, noticeForm.getNoticeBegin());
			preparedStatement.setString(6, noticeForm.getNoticeEnd());
			preparedStatement.setLong(7, noticeForm.getNoticeId());
			preparedStatement.executeUpdate();
			conn.commit();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 系统状态添加
	 * @param serverInfoForm
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public int serverInfoAdd(ServerInfoForm serverInfoForm)
	{
		try
		{
			conn = dataSource.getConnection();
			String insertSQL = "insert into frame_serverinfo(serverinfo_id,ratio_cpu,ratio_memory,use_memory,ratio_harddisk,use_harddisk,letter,create_time,save_ip)";
			insertSQL += " values(seq_serverinfo_id.nextval,?,?,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setString(1, serverInfoForm.getRatioCPU()+"");
			preparedStatement.setString(2, serverInfoForm.getRatioMEMORY()+"");
			preparedStatement.setString(3, serverInfoForm.getUseMEMORY());
			preparedStatement.setString(4, serverInfoForm.getRatioHARDDISK()+"");
			preparedStatement.setString(5, serverInfoForm.getUseHARDDISK());
			preparedStatement.setString(6, serverInfoForm.getLetter());
			preparedStatement.setString(7, DateUtils.getChar14());
			preparedStatement.setString(8, serverInfoForm.getSaveIp());
			preparedStatement.executeUpdate();
			return 0;
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 系统状态列表查询 —— 天查询
	 * @param beginTime 查询起始时间yyyyMMdd
	 * @param endTime 查询截止时间yyyyMMdd
	 * @return List<ServerInfoForm>
	 */
	public List<ServerInfoForm> queryServerInfoList_ByDay(String statTime)
	{
		list_serverInfo = null;
		String real_statTime = statTime;
		statTime = statTime.substring(0,4)+"-"+statTime.substring(4,6)+"-"+statTime.substring(6,8);//历时原因造成
		String[] statTime_ = statTime.split("-");
		if(statTime_.length==3)
		{
			try
			{
				list_serverInfo = new ArrayList<ServerInfoForm>();
				conn = dataSource.getConnection();
				String selectSQL = "select * from frame_serverinfo where";
					selectSQL += " to_number(substr(create_time,0,8)) = ? order by create_time asc";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, real_statTime);
				rs = preparedStatement.executeQuery();
				while(rs.next())
				{
					ServerInfoForm sif = new ServerInfoForm();
					sif.setServerinfoId(rs.getLong("SERVERINFO_ID"));
					sif.setRatioCPU(rs.getInt("RATIO_CPU"));
					sif.setRatioMEMORY(rs.getInt("RATIO_MEMORY"));
					sif.setUseMEMORY(rs.getString("USE_MEMORY"));
					sif.setRatioHARDDISK(rs.getInt("RATIO_HARDDISK"));
					sif.setUseHARDDISK(rs.getString("USE_HARDDISK"));
					sif.setLetter(rs.getString("LETTER"));
					sif.setCreateTime(rs.getString("CREATE_TIME"));
					list_serverInfo.add(sif);
				}
			}
			catch(Exception ex)
			{
			}
			finally
			{
				Comment.closeConnection(conn, preparedStatement, rs);
			}
		}
		return list_serverInfo;
	}

	/**
	 * 系统状态列表查询 —— 周查询
	 * @param beginTime 查询起始时间yyyyMMdd
	 * @param endTime 查询截止时间yyyyMMdd
	 * @return List<ServerInfoForm>
	 */
	public List<ServerInfoForm> queryServerInfoList_ByWeek(String beginTime, String endTime)
	{
		list_serverInfo = null;
		String real_beginTime = beginTime;
		String real_endTime = endTime;
		beginTime = beginTime.substring(0,4)+"-"+beginTime.substring(4,6)+"-"+beginTime.substring(6,8);//历时原因造成
		String[] beginTime_ = beginTime.split("-");
		endTime = endTime.substring(0,4)+"-"+endTime.substring(4,6)+"-"+endTime.substring(6,8);//历时原因造成
		String[] endTime_ = endTime.split("-");
		if(beginTime_.length==3 && endTime_.length==3)
		{
			try
			{
				list_serverInfo = new ArrayList<ServerInfoForm>();
				conn = dataSource.getConnection();
				String selectSQL = "select * from frame_serverinfo where";
					selectSQL += " to_number(substr(create_time,0,8)) >= ? and";
					selectSQL += " to_number(substr(create_time,0,8)) <= ? order by create_time asc";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, real_beginTime);
				preparedStatement.setString(2, real_endTime);
				rs = preparedStatement.executeQuery();
				while(rs.next())
				{
					ServerInfoForm sif = new ServerInfoForm();
					sif.setServerinfoId(rs.getLong("SERVERINFO_ID"));
					sif.setRatioCPU(rs.getInt("RATIO_CPU"));
					sif.setRatioMEMORY(rs.getInt("RATIO_MEMORY"));
					sif.setUseMEMORY(rs.getString("USE_MEMORY"));
					sif.setRatioHARDDISK(rs.getInt("RATIO_HARDDISK"));
					sif.setUseHARDDISK(rs.getString("USE_HARDDISK"));
					sif.setLetter(rs.getString("LETTER"));
					sif.setCreateTime(rs.getString("CREATE_TIME"));
					list_serverInfo.add(sif);
				}
			}
			catch(Exception ex)
			{
			}
			finally
			{
				Comment.closeConnection(conn, preparedStatement, rs);
			}
		}
		return list_serverInfo;
	}

	/**
	 * 系统状态列表查询 —— 月查询
	 * @param beginTime 查询起始时间yyyyMMdd
	 * @param endTime 查询截止时间yyyyMMdd
	 * @return List<ServerInfoForm>
	 */
	public List<ServerInfoForm> queryServerInfoList_ByMonth(String month)
	{
		list_serverInfo = null;
		try
		{
			list_serverInfo = new ArrayList<ServerInfoForm>();
			conn = dataSource.getConnection();
			for(int i=1; i<=31; i++)
			{
				String selectSQL = "select round(avg(ratio_cpu),0) as RATIO_CPU,round(avg(ratio_memory),0) as RATIO_MEMORY,round(avg(ratio_harddisk),0) as RATIO_HARDDISK from frame_serverinfo where";
					selectSQL += " to_number(substr(create_time,0,8)) = ? order by create_time asc";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, month+(i<10?("0"+i):i));
				rs = preparedStatement.executeQuery();
				while(rs.next())
				{
					ServerInfoForm sif = new ServerInfoForm();
					sif.setRatioCPU(rs.getInt("RATIO_CPU"));
					sif.setRatioMEMORY(rs.getInt("RATIO_MEMORY"));
					sif.setRatioHARDDISK(rs.getInt("RATIO_HARDDISK"));
					list_serverInfo.add(sif);
				}
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_serverInfo;
	}

	/**
	 * 系统状态列表查询 —— 年查询
	 * @param beginTime 查询起始时间yyyyMMdd
	 * @param endTime 查询截止时间yyyyMMdd
	 * @return List<ServerInfoForm>
	 */
	public List<ServerInfoForm> queryServerInfoList_ByYear(String year)
	{
		list_serverInfo = null;
		try
		{
			list_serverInfo = new ArrayList<ServerInfoForm>();
			conn = dataSource.getConnection();
			for(int i=1; i<=12; i++)
			{
				String selectSQL = "select round(avg(ratio_cpu),0) as RATIO_CPU,round(avg(ratio_memory),0) as RATIO_MEMORY,round(avg(ratio_harddisk),0) as RATIO_HARDDISK from frame_serverinfo where";
					selectSQL += " to_number(substr(create_time,0,6)) = ? order by create_time asc";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, year+(i<10?("0"+i):i));
				rs = preparedStatement.executeQuery();
				while(rs.next())
				{
					ServerInfoForm sif = new ServerInfoForm();
					sif.setRatioCPU(rs.getInt("RATIO_CPU"));
					sif.setRatioMEMORY(rs.getInt("RATIO_MEMORY"));
					sif.setRatioHARDDISK(rs.getInt("RATIO_HARDDISK"));
					list_serverInfo.add(sif);
				}
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_serverInfo;
	}

	/**
	 * 系统状态列表查询
	 * @param serverinfoId
	 * @return ServerInfoForm
	 */
	public ServerInfoForm queryServerInfo(String serverinfoId)
	{
		serverInfo = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_serverinfo where serverinfo_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				serverInfo = new ServerInfoForm();
				serverInfo.setServerinfoId(rs.getLong("SERVERINFO_ID"));
				serverInfo.setRatioCPU(rs.getInt("RATIO_CPU"));
				serverInfo.setRatioMEMORY(rs.getInt("RATIO_MEMORY"));
				serverInfo.setUseMEMORY(rs.getString("USE_MEMORY"));
				serverInfo.setRatioHARDDISK(rs.getInt("RATIO_HARDDISK"));
				serverInfo.setUseHARDDISK(rs.getString("USE_HARDDISK"));
				serverInfo.setLetter(rs.getString("LETTER"));
				serverInfo.setCreateTime(rs.getString("CREATE_TIME"));
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return serverInfo;
	}

	/**
	 * 备注表 —— 查询
	 * @param beginTime 查询起始时间yyyyMMdd
	 * @param endTime 查询截止时间yyyyMMdd
	 * @return List<ServerInfoForm>
	 */
	public List<RemarkForm> remarkFormList(String tableName)
	{
		list_remark = null;
		try
		{
			list_remark = new ArrayList<RemarkForm>();
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_remark where remark_table = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, tableName);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				remarkForm = new RemarkForm();
				remarkForm.setRemarkId(rs.getLong("REMARK_ID"));
				remarkForm.setRemarkTable(rs.getString("REMARK_TABLE"));
				remarkForm.setRemarkContent(rs.getString("REMARK_CONTENT"));
				remarkForm.setCreateTime(rs.getString("CREATE_TIME"));
				list_remark.add(remarkForm);
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_remark;
	}

	/**
	 * 备注表 —— 修改
	 * @param beginTime 查询起始时间yyyyMMdd
	 * @param endTime 查询截止时间yyyyMMdd
	 * @return 0-成功 1-失败
	 */
	public int remarkFormMdf(RemarkForm remarkForm)
	{
		int mdfResult = 1;
		try
		{
			list_remark = new ArrayList<RemarkForm>();
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String selectSQL = "update frame_remark set remark_content = ?, create_time = ? where remark_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, remarkForm.getRemarkContent());
			preparedStatement.setString(2, DateUtils.getChar14());
			preparedStatement.setLong(3, remarkForm.getRemarkId());
			preparedStatement.executeUpdate();
			conn.commit();
			return 0;
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return mdfResult;
	}

	/**
	 * 判断用户是否在部门列表中
	 * @param treeIdList 部门列表数组
	 * @param userForm 用户form
	 * @return
	 */
	public boolean userTree(String[] treeIdList, UserForm userForm)
	{
		if(userForm==null)
		{
			return false;
		}
		for(int i=0; i<treeIdList.length; i++)
		{
			if(treeIdList[i].equals(userForm.getTreeId()+""))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 用户密码重置
	 * @param userId 用户id
	 * @param resetPswd 重置密码
	 * @return
	 */
	public boolean userResetPassword(String userId, String resetPswd)
	{
		try
		{
			list_remark = new ArrayList<RemarkForm>();
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String selectSQL = "update frame_user set login_pswd = ? where user_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, resetPswd);
			preparedStatement.setString(2, userId);
			preparedStatement.executeUpdate();
			conn.commit();
		}
		catch(Exception ex)
		{
			return false;
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return true;
	}

	/**
	 * 得到未编辑生成flv的对象
	 * @return
	 */
	public UploadForm getNowWork()
	{
		UploadForm uploadForm = null;
		try
		{
			uploadForm = new UploadForm();
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_upload t where t.real_path = ? and t.flv_path is null and rownum<=1 order by t.upload_id";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, SystemConfig.getSystemConfig().getFileSavePath());
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				uploadForm.setUploadId(rs.getLong("UPLOAD_ID"));
				uploadForm.setUserId(rs.getLong("USER_ID"));
				uploadForm.setUserName(rs.getString("USERNAME"));
				uploadForm.setEditId(rs.getLong("EDIT_ID"));
				uploadForm.setEditName(rs.getString("EDITNAME"));
				uploadForm.setUploadName(rs.getString("UPLOAD_NAME"));
				uploadForm.setPlayPath(rs.getString("PLAY_PATH"));
				uploadForm.setShowPath(rs.getString("SHOW_PATH"));
				uploadForm.setUploadTime(rs.getString("UPLOAD_TIME"));
				uploadForm.setFileState(rs.getString("FILE_STATE"));
				uploadForm.setTree1Id(rs.getLong("TREE1_ID"));
				uploadForm.setTree2Id(rs.getLong("TREE2_ID"));
				uploadForm.setTreeName(rs.getString("TREENAME"));
				uploadForm.setFileStats(rs.getString("FILE_STATS"));
				uploadForm.setFileRemark(rs.getString("FILE_REMARK"));
				uploadForm.setFileSavePath(rs.getString("REAL_PATH"));
				uploadForm.setFlvPath(rs.getString("FLV_PATH")==null?"":rs.getString("FLV_PATH"));
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return uploadForm;
	}
	
	public void formatWork(UploadForm uploadForm)
	{
		try
		{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String selectSQL = "update frame_upload set flv_path = ? where upload_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, uploadForm.getFlvPath());
			preparedStatement.setLong(2, uploadForm.getUploadId());
			preparedStatement.executeUpdate();
			conn.commit();
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
	}
	
	public String getTreeListName(String treeList)
	{
		String treeListName = "";
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		try
		{
			if(treeList.length()>0)
			{
				String[] treeArr = treeList.split(",");
				connection = dataSource.getConnection();
				String selectSQL = "select tree_name from frame_tree where tree_id = ?";
				for(int i=0; i<treeArr.length; i++)
				{
					pStatement = connection.prepareStatement(selectSQL);
					pStatement.setLong(1, new Long(treeArr[i]));
					resultSet = pStatement.executeQuery();
					while(resultSet.next())
					{
						treeListName += resultSet.getString(1) + ",";
					}
				}
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(connection, pStatement, resultSet);
			if(treeListName.length()>0)
			{
				treeListName = treeListName.substring(0, treeListName.length()-1);
			}
			return treeListName;
		}
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
		staticDataSource = dataSource;
	}

	public static BasicDataSource getBasicDataSource()
	{
		return staticDataSource;
	}
}