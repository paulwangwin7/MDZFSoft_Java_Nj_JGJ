package com.manager.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.manager.pub.bean.LogForm;
import com.manager.pub.bean.Page;
import com.manager.pub.util.Comment;
import com.manager.pub.util.DateUtils;

public class LogDAO {
	protected static final Logger logger = Logger.getLogger(LogDAO.class);

	private static Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private static ResultSet rs = null;
	private BasicDataSource dataSource;
	private List<LogForm> list_logForm = null;
	private LogForm logForm = null;


	/**
	 * 行为日志添加
	 * @param logForm
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public void logAdd(LogForm logForm)
	{
		try
		{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String insertSQL = "insert into frame_log(log_id,create_time,user_id,user_code,tree_id,tree_name,role_id,role_name,log_desc,ip_add)";
			insertSQL += " values(seq_log_id.nextval,?,?,?,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setString(1, DateUtils.getChar14());
			preparedStatement.setLong(2, logForm.getUserId());
			preparedStatement.setString(3, logForm.getUserCode());
			preparedStatement.setLong(4, logForm.getTreeId());
			preparedStatement.setString(5, logForm.getTreeName());
			preparedStatement.setLong(6, logForm.getRoleId());
			preparedStatement.setString(7, logForm.getRoleName());
			preparedStatement.setString(8, logForm.getLogDesc());
			preparedStatement.setString(9, logForm.getIpAddr());
			int addResult = preparedStatement.executeUpdate();
			if(addResult>0)
			{
				logger.info("行为日志添加成功");
			}
			else
			{
				logger.info("行为日志添加失败，但未异常。");
			}
			conn.commit();
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
	}


	/**
	 * 行为日志查询
	 * @param logForm
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public Page logQuery(String userCode, String beginTime, String endTime, String treeId, int pagecute, int pageline)
	{
		Page page = null;
		list_logForm = null;
		try
		{
			page = new Page();
			page.setDbLine(pageline);//每页多少行数据
			page.setPageCute(pagecute);//当前页
			conn = dataSource.getConnection();
			String countSQL = "select count(*)";
			countSQL += " from frame_log t";
			countSQL += " where ";
			if(!treeId.equals("0"))
			{
				countSQL += "t.tree_id in (select t2.tree_id from frame_tree t2 where t2.tree_id="+treeId+" or t2.parent_tree_id="+treeId+") and ";
			}
			countSQL += "t.user_code like '%"+userCode+"%'";
			countSQL += " and substr(t.create_time,0,8)>='"+beginTime+"' and substr(t.create_time,0,8)<='"+endTime+"'";
			preparedStatement = conn.prepareStatement(countSQL);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				page.setTotal(rs.getInt(1));//总记录数
			}

			String querySQL = "select * from (";
				querySQL += "select rst.*, rownum row_num from(";
				querySQL += "select  t.*,";
				querySQL += "(select ft.tree_name from frame_tree ft where ft.tree_id = t.tree_id) as TREENAME,";
				querySQL += "(select fr.role_name from frame_role fr where fr.role_id = t.role_id) as ROLENAME";
				querySQL += " from frame_log t";
				querySQL += " where ";
				if(!treeId.equals("0"))
				{
					querySQL += "t.tree_id in (select t2.tree_id from frame_tree t2 where t2.tree_id="+treeId+" or t2.parent_tree_id="+treeId+") and ";
				}
				querySQL += "t.user_code like '%"+userCode+"%'";
				querySQL += " and substr(t.create_time,0,8)>='"+beginTime+"' and substr(t.create_time,0,8)<='"+endTime+"'";
				querySQL += ")rst)where row_num>="+((pagecute-1)*pageline+1)+" and row_num<="+pagecute*pageline;
			preparedStatement = conn.prepareStatement(querySQL);
			rs = preparedStatement.executeQuery();
			list_logForm = new ArrayList<LogForm>();
			while(rs.next())
			{
				logForm = new LogForm();
				logForm.setLogId(rs.getLong("LOG_ID"));
				logForm.setCreateTime(rs.getString("CREATE_TIME"));
				logForm.setUserId(rs.getLong("USER_ID"));
				logForm.setUserCode(rs.getString("USER_CODE"));
				logForm.setTreeId(rs.getLong("TREE_ID"));
				logForm.setTreeName(rs.getString("TREENAME"));
				logForm.setRoleId(rs.getLong("ROLE_ID"));
				logForm.setRoleName(rs.getString("ROLENAME"));
				logForm.setLogDesc(rs.getString("LOG_DESC"));
				logForm.setIpAddr(rs.getString("IP_ADD"));
				list_logForm.add(logForm);
			}
			page.setListObject(list_logForm);
		}
		catch(Exception ex)
		{
			logger.error(ex);
			return null;
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return page;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
}