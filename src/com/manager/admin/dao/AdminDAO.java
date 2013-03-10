package com.manager.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.manager.pub.bean.NoticeForm;
import com.manager.pub.bean.Page;
import com.manager.pub.bean.RoleForm;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.TreeForm;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.bean.UrlForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.Comment;
import com.manager.pub.util.Constants;
import com.manager.pub.util.DateUtils;
import com.manager.pub.util.EncodingFilter;
import com.manager.pub.util.FileUtils;

public class AdminDAO {

	protected static final Logger logger = Logger.getLogger(AdminDAO.class);

	private static Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private static ResultSet rs = null;
	private BasicDataSource dataSource;
	private FileUtils fileUtils = null;

	private List list_menuAndurl = null;
	private UrlForm urlForm = null;

	private List<UrlForm> list_urlForm = null;
	private RoleForm roleForm = null;
	private List<RoleForm> list_roleForm = null;

	private List list_totalTree = null;
	private TreeForm treeForm = null;
	private List<TreeForm> list_treeForm = null;

	private UserForm userForm = null;
	private List<UserForm> list_userForm = null;

	private NoticeForm noticeForm = null;
	private List<NoticeForm> list_noticeForm = null;

	private UploadForm uploadForm = null;
	private List<UploadForm> list_uploadForm = null;
	private List<UploadForm> list_uploadForm_unStats = null;

	/**
	 * 查询所有角色列表
	 * @return List<RoleForm>
	 */
	public List<RoleForm> queryRoleList()
	{
		logger.info("查询所有角色列表--开始");
		roleForm = null;
		list_roleForm = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_role order by create_time desc";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_roleForm = new ArrayList<RoleForm>();
			while(rs.next())
			{
				roleForm = new RoleForm();
				roleForm.setRoleId(rs.getLong("ROLE_ID"));
				roleForm.setRoleName(rs.getString("ROLE_NAME"));
				roleForm.setRoleDesc(rs.getString("ROLE_DESC"));
				roleForm.setRoleState(rs.getString("ROLE_STATE"));
				roleForm.setCreateUser(rs.getString("CREATE_USER"));
				roleForm.setCreateTime(rs.getString("CREATE_TIME"));
				roleForm.setTreeId(rs.getString("TREE_ID"));
				roleForm.setUrlIdList(rs.getString("URL_ID_LIST"));
				list_roleForm.add(roleForm);
			}
			logger.info("查询所有角色列表--查询结束 查询结果："+list_roleForm.size()+"条");
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("查询所有角色列表--结束 数据库连接释放");
		}
		return list_roleForm;
	}

	/**
	 * 根据roleId查询角色详情
	 * @return roleForm
	 */
	public RoleForm roleQuery(String roleId)
	{
		logger.info("根据roleId查询角色详情--开始");
		roleForm = null;
		try
		{
			conn = dataSource.getConnection();
			//--step1 确认修改的角色名称在角色表中不会出现（防止角色名称重复）
			String selectSQL = "select * from frame_role where role_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, roleId);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				roleForm = new RoleForm();
				roleForm.setRoleId(rs.getLong("ROLE_ID"));
				roleForm.setRoleName(rs.getString("ROLE_NAME"));
				roleForm.setRoleDesc(rs.getString("ROLE_DESC"));
				roleForm.setRoleState(rs.getString("ROLE_STATE"));
				roleForm.setCreateUser(rs.getString("CREATE_USER"));
				roleForm.setCreateTime(rs.getString("CREATE_TIME"));
				roleForm.setTreeId(rs.getString("TREE_ID"));
				roleForm.setUrlIdList(rs.getString("URL_ID_LIST"));
				logger.info("根据roleId查询角色详情--查询结束 查询成功");
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("根据roleId查询角色详情--结束 数据库连接释放");
		}
		return roleForm;
	}

	/**
	 * 角色添加
	 * @return 0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
	 */
	public int roleAdd(RoleForm roleForm)
	{
		logger.info("角色添加--开始");
		try
		{
			conn = dataSource.getConnection();
			//--step1 确认新增的角色名称在角色表中不会出现（防止角色名称重复）
			String selectSQL = "select * from frame_role where role_name = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, roleForm.getRoleName());
			rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				logger.info("角色添加--失败，角色名重复");
				return 2;
			}
			//--step2 新增角色
			else
			{
				conn.setAutoCommit(false);
				selectSQL = "insert into frame_role values(seq_role_id.nextval,?,?,?,?,?,?,?)";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, roleForm.getRoleName());
				preparedStatement.setString(2, roleForm.getRoleDesc());
				preparedStatement.setString(3, roleForm.getRoleState());
				preparedStatement.setString(4, roleForm.getCreateUser());
				preparedStatement.setString(5, DateUtils.getChar14());
				preparedStatement.setString(6, roleForm.getTreeId());
				preparedStatement.setString(7, roleForm.getUrlIdList());
				int updateResult = preparedStatement.executeUpdate();
				if(updateResult>0)
				{
					logger.info("角色添加--添加成功");
					conn.commit();
					return 0;
				}
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("角色添加--结束 数据库连接释放");
		}
		return 1;
	}

	/**
	 * 角色修改
	 * @return 0-修改成功；1-修改失败 系统超时~；2-该角色出现重名 请换个角色名称
	 */
	public int roleMdf(RoleForm roleForm)
	{
		logger.info("角色修改--开始");
		try
		{
			conn = dataSource.getConnection();
			//--step1 确认修改的角色名称在角色表中不会出现（防止角色名称重复）
			String selectSQL = "select * from frame_role where role_name = ? and role_id != ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, roleForm.getRoleName());
			preparedStatement.setLong(2, roleForm.getRoleId());
			rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				logger.info("角色修改--失败，角色名重复");
				return 2;
			}
			//--step2 修改角色
			else
			{
				conn.setAutoCommit(false);
				selectSQL = "update frame_role set role_name=?,role_desc=?,role_state=?,create_user=?,create_time=?,tree_id=?,url_id_list=? where role_id=?";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, roleForm.getRoleName());
				preparedStatement.setString(2, roleForm.getRoleDesc());
				preparedStatement.setString(3, roleForm.getRoleState());
				preparedStatement.setString(4, roleForm.getCreateUser());
				preparedStatement.setString(5, DateUtils.getChar14());
				preparedStatement.setString(6, roleForm.getTreeId());
				preparedStatement.setString(7, roleForm.getUrlIdList());
				preparedStatement.setLong(8, roleForm.getRoleId());
				int updateResult = preparedStatement.executeUpdate();
				if(updateResult>0)
				{
					logger.info("角色修改--修改成功");
					conn.commit();
					return 0;
				}
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("角色修改--结束 数据库连接释放");
		}
		return 1;
	}

	/**
	 * 角色删除
	 * @return 0-修改成功；1-修改失败 系统超时~； 2-删除失败 还有其他用户正在使用该角色；
	 */
	public int roleDel(String roleId)
	{
		logger.info("角色删除--开始");
		try
		{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			//--!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 这里需要检查是否还有用户在使用该角色
			String selectSQL = "delete from frame_role where role_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, roleId);
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("角色删除--删除成功");
				conn.commit();
				return 0;
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("角色修改--删除 数据库连接释放");
		}
		return 1;
	}

	/**
	 * 查询所有菜单及菜单下对应的所有功能url
	 * @return list_menuAndurl [ ['菜单1名称',[urlForm,urlForm...]], ['菜单2名称',[urlForm,urlForm...]] ... ]
	 */
	public List queryMenuAndUrl()
	{
		logger.info("查询所有菜单及菜单下对应的所有功能url--开始");
		list_menuAndurl = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_menu order by menu_sort";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_menuAndurl = new ArrayList();
			while(rs.next())
			{
				List tempList = new ArrayList();
				tempList.add(rs.getString("MENU_NAME"));
				tempList.add(queryFrameUrlByMenuId(rs.getLong("MENU_ID")));
				list_menuAndurl.add(tempList);
			}
			logger.info("查询所有菜单及菜单下对应的所有功能url--查询结束 查询结果："+list_menuAndurl.size()+"条");
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("查询所有菜单及菜单下对应的所有功能url--结束 数据库连接释放");
		}
		return list_menuAndurl;
	}

	/**
	 * 根据菜单id查询功能地址列表
	 * @param menuId 菜单id
	 * @return
	 */
	public List<UrlForm> queryFrameUrlByMenuId(long menuId)
	{
		logger.info("根据菜单id查询功能地址列表--开始");
		list_urlForm = null;
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rst = null;
		try
		{
			connection = dataSource.getConnection();
			String selectSQL = "select * from frame_url where parent_menu_id = ? order by url_sort";
			pStatement = connection.prepareStatement(selectSQL);
			pStatement.setLong(1, menuId);
			rst = pStatement.executeQuery();
			list_urlForm = new ArrayList<UrlForm>();
			while(rst.next())
			{
				urlForm = new UrlForm();
				urlForm.setUrlId(rst.getLong("URL_ID"));
				urlForm.setUrlValue(rst.getString("URL_VALUE"));
				urlForm.setUrlName(rst.getString("URL_NAME"));
				urlForm.setUrlDesc(rst.getString("URL_DESC"));
				urlForm.setUrlState(rst.getString("URL_STATE"));
				urlForm.setParentMenuId(rst.getString("PARENT_MENU_ID"));
				urlForm.setUrlSort(rst.getString("URL_SORT"));
				list_urlForm.add(urlForm);
			}
			logger.info("根据菜单id查询功能地址列表--查询结束 查询结果："+list_menuAndurl.size()+"条");
		}
		catch(Exception ex)
		{
			logger.info(ex);
		}
		finally
		{
			Comment.closeConnection(connection, pStatement, rst);
			logger.info("根据菜单id查询功能地址列表--结束 数据库连接释放");
		}
		return list_urlForm;
	}

	/**
	 * 部门添加
	 * @return 0-添加成功；1-添加失败 系统超时~
	 */
	public int treeAdd(TreeForm treeForm)
	{
		logger.info("部门添加--开始");
		try
		{
			conn = dataSource.getConnection();
//			//--step1 确认新增的角色名称在角色表中不会出现（防止角色名称重复）
//			String selectSQL = "select * from frame_role where role_name = ?";
//			preparedStatement = conn.prepareStatement(selectSQL);
//			preparedStatement.setString(1, roleForm.getRoleName());
//			rs = preparedStatement.executeQuery();
//			if(rs.next())
//			{
//				return 2;
//			}
//			//--step2 新增角色
//			else
//			{
				conn.setAutoCommit(false);
				String selectSQL = "insert into frame_tree values(seq_tree_id.nextval,?,?,?,?,?,?,?)";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, treeForm.getTreeName());
				preparedStatement.setString(2, treeForm.getTreeDesc());
				preparedStatement.setString(3, treeForm.getTreeState());
				preparedStatement.setLong(4, treeForm.getCreateUser());
				preparedStatement.setString(5, DateUtils.getChar14());
				preparedStatement.setLong(6, treeForm.getParentTreeId());
				preparedStatement.setString(7, treeForm.getOrderBy());
				int updateResult = preparedStatement.executeUpdate();
				if(updateResult>0)
				{
					logger.info("部门添加--添加成功");
					conn.commit();
					return 0;
				}
//			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("部门添加--结束 数据库连接释放");
		}
		return 1;
	}

	/**
	 * 部门修改
	 * @return 0-修改成功；1-修改失败 系统超时~
	 */
	public int treeMdf(TreeForm treeForm)
	{
		logger.info("部门修改--开始");
		try
		{
			conn = dataSource.getConnection();
//			//--step1 确认修改的角色名称在角色表中不会出现（防止角色名称重复）
//			String selectSQL = "select * from frame_role where role_name = ? and role_id != ?";
//			preparedStatement = conn.prepareStatement(selectSQL);
//			preparedStatement.setString(1, roleForm.getRoleName());
//			preparedStatement.setLong(2, roleForm.getRoleId());
//			rs = preparedStatement.executeQuery();
//			if(rs.next())
//			{
//				return 2;
//			}
//			//--step2 修改角色
//			else
//			{
				conn.setAutoCommit(false);
				String selectSQL = "update frame_tree set tree_name=?,tree_desc=?,tree_state=?,create_user=?,create_time=?,parent_tree_id=?,order_by=? where tree_id=?";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, treeForm.getTreeName());
				preparedStatement.setString(2, treeForm.getTreeDesc());
				preparedStatement.setString(3, treeForm.getTreeState());
				preparedStatement.setLong(4, treeForm.getCreateUser());
				preparedStatement.setString(5, DateUtils.getChar14());
				preparedStatement.setLong(6, treeForm.getParentTreeId());
				preparedStatement.setString(7, treeForm.getOrderBy());
				preparedStatement.setLong(8, treeForm.getTreeId());
				int updateResult = preparedStatement.executeUpdate();
				if(updateResult>0)
				{
					logger.info("部门修改--修改成功");
					conn.commit();
					return 0;
				}
//			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("部门添加--结束 数据库连接释放");
		}
		return 1;
	}

	/**
	 * 部门删除
	 * @return 0-修改成功；1-修改失败 系统超时~； 2-删除失败 还有其他用户正在使用该角色；
	 */
	public int treeDel(String treeId)
	{
		logger.info("部门删除--开始");
		try
		{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			//--!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 这里需要检查是否还有用户在使用该角色
			String selectSQL = "delete from frame_tree where tree_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, treeId);
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("部门删除--删除成功");
				conn.commit();
				return 0;
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("部门添加--结束 数据库连接释放");
		}
		return 1;
	}

	/**
	 * 查询所有一级部门以及以下对应的所有部门
	 * @return list_totalTree [ [treeForm,[treeForm,treeForm...]], [treeForm,[treeForm,treeForm...]] ... ]
	 */
	public List queryTotalTreeList()
	{
		list_totalTree = null;
		treeForm = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_tree where parent_tree_id = 0 order by order_by,tree_id";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_totalTree = new ArrayList();
			while(rs.next())
			{
				List tempList = new ArrayList();
				treeForm = new TreeForm();
				treeForm.setTreeId(rs.getLong("TREE_ID"));
				treeForm.setTreeName(rs.getString("TREE_NAME"));
				treeForm.setTreeDesc(rs.getString("TREE_DESC"));
				treeForm.setTreeState(rs.getString("TREE_STATE"));
				treeForm.setCreateUser(rs.getLong("CREATE_USER"));
				treeForm.setCreateTime(rs.getString("CREATE_TIME"));
				treeForm.setParentTreeId(rs.getLong("PARENT_TREE_ID"));
				treeForm.setOrderBy(rs.getString("ORDER_BY"));
				tempList.add(treeForm);
				tempList.add(queryTreeByParentId(treeForm.getTreeId()));
				list_totalTree.add(tempList);
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		System.out.println("list_totalTree==="+list_totalTree);
		return list_totalTree;
	}

	/**
	 * 根据部门父节点id查询子部门列表
	 * @param treeId 父节点treeid
	 * @return
	 */
	public List<TreeForm> queryTreeByParentId(long treeId)
	{
		list_treeForm = null;
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rst = null;
		try
		{
			connection = dataSource.getConnection();
			String selectSQL = "select * from frame_tree where parent_tree_id = ? order by order_by,tree_id";
			pStatement = connection.prepareStatement(selectSQL);
			pStatement.setLong(1, treeId);
			rst = pStatement.executeQuery();
			list_treeForm = new ArrayList<TreeForm>();
			while(rst.next())
			{
				treeForm = new TreeForm();
				treeForm.setTreeId(rst.getLong("TREE_ID"));
				treeForm.setTreeName(rst.getString("TREE_NAME"));
				treeForm.setTreeDesc(rst.getString("TREE_DESC"));
				treeForm.setTreeState(rst.getString("TREE_STATE"));
				treeForm.setCreateUser(rst.getLong("CREATE_USER"));
				treeForm.setCreateTime(rst.getString("CREATE_TIME"));
				treeForm.setParentTreeId(rst.getLong("PARENT_TREE_ID"));
				treeForm.setOrderBy(rst.getString("ORDER_BY"));
				list_treeForm.add(treeForm);
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(connection, pStatement, rst);
		}
		return list_treeForm;
	}

	/**
	 * 用户删除
	 * @param NoticeForm
	 * @return 0-删除成功； 1-删除失败 系统异常;
	 */
	public int userManagerDel(Long user_id){
		try{
			//String delSQL="delete from FRAME_USER where user_id=?";
			String delSQL="update FRAME_USER set user_state='U' where user_id=?";
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			preparedStatement=conn.prepareStatement(delSQL);
			preparedStatement.setLong(1, user_id);
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("用户删除--删除成功");
				conn.commit();
				return 0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 查询所有用户
	 * @param UserForm
	 * @return List<UserForm>
	 */
	public List<UserForm> queryUserListByUserForm(UserForm userForm)
	{
		list_userForm = null;
		try
		{
			conn = dataSource.getConnection();
			StringBuffer selectSQL = new StringBuffer("");
				selectSQL.append("select t.*,(select tree_name from frame_tree where tree_id = t.tree_id)as TREE_NAME,(select role_name from frame_role where role_id = t.role_id)as ROLE_NAME from frame_user t");
				selectSQL.append(" where t.user_name like '%"+(userForm.getUserName()==null?"":userForm.getUserName())+"%'");
				if(userForm.getUserCode()!=null)
				{
					if(!userForm.getUserCode().equals(""))
					{
						selectSQL.append(" and t.user_code = '"+userForm.getUserCode()+"'");
					}
				}
				if(userForm.getSex()!=null)
					selectSQL.append(" and t.sex = '"+userForm.getSex()+"'");
				if(userForm.getTreeId()!=0)
					selectSQL.append(" and t.tree_id = "+userForm.getTreeId()+"");
				selectSQL.append(" order by create_time desc");
			System.out.println(selectSQL.toString());
			preparedStatement = conn.prepareStatement(selectSQL.toString());
			rs = preparedStatement.executeQuery();
			list_userForm = new ArrayList<UserForm>();
			while(rs.next())
			{
				userForm = new UserForm();
				userForm.setUserId(rs.getLong("USER_ID"));
				userForm.setLoginName(rs.getString("LOGIN_NAME"));
				userForm.setLoginPswd(rs.getString("LOGIN_PSWD"));
				userForm.setUserName(rs.getString("USER_NAME"));
				userForm.setUserCode(rs.getString("USER_CODE"));
				userForm.setSex(rs.getString("SEX"));
				userForm.setUserIdCard(rs.getString("USER_IDCARD"));
				userForm.setCard_typeId(rs.getLong("CARD_TYPEID"));
				userForm.setCardCode(rs.getString("CARD_CODE"));
				userForm.setTreeId(rs.getLong("TREE_ID"));
				userForm.setRoleId(rs.getLong("ROLE_ID"));
				userForm.setCreateTime(rs.getString("CREATE_TIME"));
				userForm.setUserState(rs.getString("USER_STATE"));
				userForm.setTreeNameStr(rs.getString("TREE_NAME"));
				userForm.setRoleNameStr(rs.getString("ROLE_NAME"));
				list_userForm.add(userForm);
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_userForm;
	}

	/**
	 * 根据用户id查询用户信息详情
	 * @param userId
	 * @return userForm
	 */
	public UserForm queryUserFormByUserId(String userId)
	{
		userForm = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select fu.*,ft.tree_name as treeName,fr.role_name as roleName from frame_user fu, frame_tree ft, frame_role fr";
				selectSQL+=" where fu.tree_id = ft.tree_id and fu.role_id = fr.role_id and user_id="+userId;
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				userForm = new UserForm();
				userForm.setUserId(rs.getLong("USER_ID"));
				userForm.setLoginName(rs.getString("LOGIN_NAME"));
				userForm.setLoginPswd(rs.getString("LOGIN_PSWD"));
				userForm.setUserName(rs.getString("USER_NAME"));
				userForm.setUserCode(rs.getString("USER_CODE"));
				userForm.setSex(rs.getString("SEX"));
				userForm.setUserIdCard(rs.getString("USER_IDCARD"));
				userForm.setCard_typeId(rs.getLong("CARD_TYPEID"));
				userForm.setCardCode(rs.getString("CARD_CODE"));
				userForm.setTreeId(rs.getLong("TREE_ID"));
				userForm.setRoleId(rs.getLong("ROLE_ID"));
				userForm.setCreateTime(rs.getString("CREATE_TIME"));
				userForm.setUserState(rs.getString("USER_STATE"));
				userForm.setTreeNameStr(rs.getString("treeName"));
				userForm.setRoleNameStr(rs.getString("roleName"));
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return userForm;
	}

	/**
	 * 注册用户
	 * @param userForm
	 * @return 0-成功； 1-失败 系统异常； 2-登录账号重名
	 */
	public int userRegister(UserForm userForm)
	{
		try
		{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String selectSQL = "select * from frame_user where login_name = '"+userForm.getLoginName()+"'";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				return 2;
			}
			String insertSQL = "insert into frame_user(user_id,login_name,login_pswd,user_name,user_code,sex,user_idcard,card_typeid,card_code,tree_id,role_id,create_time,user_state)";
				insertSQL += "values(seq_user_id.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setString(1, userForm.getLoginName());
			preparedStatement.setString(2, userForm.getLoginPswd());
			preparedStatement.setString(3, userForm.getUserName());
			preparedStatement.setString(4, userForm.getUserCode());
			preparedStatement.setString(5, userForm.getSex());
			preparedStatement.setString(6, userForm.getUserIdCard());
			preparedStatement.setLong(7, userForm.getCard_typeId());
			preparedStatement.setString(8, userForm.getCardCode());
			preparedStatement.setLong(9, userForm.getTreeId());
			preparedStatement.setLong(10, userForm.getRoleId());
			preparedStatement.setString(11, DateUtils.getChar14());
			preparedStatement.setString(12, userForm.getUserState());
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("用户注册--注册成功");
				conn.commit();
				return 0;
			}
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
	 * 用户信息修改
	 * @param userForm
	 * @return 0-成功； 1-失败 系统异常； 2-登录账号重名
	 */
	public int userModify(UserForm userForm)
	{
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_user where login_name = '"+userForm.getLoginName()+"' and user_id !="+userForm.getUserId();
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				return 2;
			}
			String insertSQL = "update frame_user set login_name = ?,user_name=?,user_code=?,sex=?,user_idcard=?,card_typeid=?,card_code=?,tree_id=?,role_id=?,create_time=?,user_state=? where user_id=?";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setString(1, userForm.getLoginName());
			preparedStatement.setString(2, userForm.getUserName());
			preparedStatement.setString(3, userForm.getUserCode());
			preparedStatement.setString(4, userForm.getSex());
			preparedStatement.setString(5, userForm.getUserIdCard());
			preparedStatement.setLong(6, userForm.getCard_typeId());
			preparedStatement.setString(7, userForm.getCardCode());
			preparedStatement.setLong(8, userForm.getTreeId());
			preparedStatement.setLong(9, userForm.getRoleId());
			preparedStatement.setString(10, DateUtils.getChar14());
			preparedStatement.setString(11, userForm.getUserState());
			preparedStatement.setLong(12, userForm.getUserId());
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("用户信息修改--修改成功");
				conn.commit();
				return 0;
			}
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
	 * 上传日志图片展示 -- 大队查询
	 * @param showType 1-年 2-月
	 * @param yearORmonth 查询时间 showType为0则是年yyyy showType为1则是月yyyyMM
	 * @return List[ [部门1,[1月总数,2月总数,...,12月总数]],[部门2,[1月总数,2月总数,...,12月总数]]...[部门N,[1月总数,2月总数,...,12月总数]] ] 部门1年或1月的总上传量
	 */
	public List uploadImgQuery_ByTree1(int showType, String yearORmonth)
	{
		List tree1_uploadList = null;
		try
		{
			conn = dataSource.getConnection();
			if(showType==1 || showType==2)
			{
				//获取一级部门tree_id
				String selectSQL = "select tree_id,tree_name from frame_tree where parent_tree_id = 0 order by tree_id";
				preparedStatement = conn.prepareStatement(selectSQL);
				rs = preparedStatement.executeQuery();
				List treeList = new ArrayList();//获取一级部门list tree_id
				while(rs.next())
				{
					treeList.add(rs.getString(1)+"##"+rs.getString(2));
				}
				if(showType==1)//年数据查询 查询一年之中12个月的数据
				{
					tree1_uploadList = new ArrayList();
					for(int i=0; i<treeList.size(); i++)//按照1级部门开始循环查询相应信息
					{
						List tempList = new ArrayList();
						List tree_monthList = new ArrayList();
						for(int j=1; j<=12; j++)//按照年份12个月依次查询
						{
							selectSQL = "select count(*)  from frame_upload fu, frame_tree ft where (fu.tree1_id = ft.tree_id or fu.tree2_id = ft.tree_id) and ft.tree_id = ? and substr(fu.upload_time, 0, 6) = ?";
							preparedStatement = conn.prepareStatement(selectSQL);
							preparedStatement.setString(1, treeList.get(i).toString().split("##")[0]);
							preparedStatement.setString(2, yearORmonth+(j<10?("0"+j):j));
							rs = preparedStatement.executeQuery();
							while(rs.next())
							{
								tree_monthList.add(rs.getString(1));
							}
						}
						tempList.add(treeList.get(i).toString().split("##")[1]);
						tempList.add(tree_monthList);
						tree1_uploadList.add(tempList);
					}
				}
				else if(showType==2)//月数据查询 查询一月之中31天的数据
				{
					tree1_uploadList = new ArrayList();
					for(int i=0; i<treeList.size(); i++)//按照1级部门开始循环查询相应信息
					{
						List tempList = new ArrayList();
						List tree_dayList = new ArrayList();
						for(int j=1; j<=31; j++)//按照月份31天依次查询
						{
							selectSQL = "select count(*)  from frame_upload fu, frame_tree ft where (fu.tree1_id = ft.tree_id or fu.tree2_id = ft.tree_id) and ft.tree_id = ? and substr(fu.upload_time, 0, 8) = ?";
							preparedStatement = conn.prepareStatement(selectSQL);
							preparedStatement.setString(1, treeList.get(i).toString().split("##")[0]);
							preparedStatement.setString(2, yearORmonth+(j<10?("0"+j):j));
							rs = preparedStatement.executeQuery();
							while(rs.next())
							{
								tree_dayList.add(rs.getString(1));
							}
						}
						tempList.add(treeList.get(i).toString().split("##")[1]);
						tempList.add(tree_dayList);
						tree1_uploadList.add(tempList);
					}
				}
			}
			else
			{
				
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return tree1_uploadList;
	}

	/**
	 * 上传日志图片展示 -- 中队查询
	 * @param showType 1-年 2-月
	 * @param yearORmonth 查询时间 showType为0则是年yyyy showType为1则是月yyyyMM
	 * @param tree1Id 一级部门id
	 * @return List[ [部门1,[12个月总数]],[部门2,[12个月总数]]...[部门N,[12个月总数]] ] 部门1年或1月的总上传量
	 */
	public List uploadImgQuery_ByTree2(int showType, String yearORmonth, String tree1Id)
	{
		List tree1_uploadList = null;
		try
		{
			conn = dataSource.getConnection();
			if(showType==1 || showType==2)
			{
				//获取二级部门tree_id
				String selectSQL = "select tree_id,tree_name from frame_tree where parent_tree_id = ? order by tree_id";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, tree1Id);
				rs = preparedStatement.executeQuery();
				List treeList = new ArrayList();//获取一级部门list tree_id
				while(rs.next())
				{
					treeList.add(rs.getString(1)+"##"+rs.getString(2));
				}
				if(showType==1)//年数据查询 查询一年之中12个月的数据
				{
					tree1_uploadList = new ArrayList();
					for(int i=0; i<treeList.size(); i++)//按照1级部门开始循环查询相应信息
					{
						List tempList = new ArrayList();
						int tree_monthTotal = 0;
						for(int j=1; j<=12; j++)//按照年份12个月依次查询
						{
							selectSQL = "select count(*)  from frame_upload fu where fu.tree2_id = ? and substr(fu.upload_time, 0, 6) = ?";
							preparedStatement = conn.prepareStatement(selectSQL);
							preparedStatement.setString(1, treeList.get(i).toString().split("##")[0]);
							preparedStatement.setString(2, yearORmonth+(j<10?("0"+j):j));
							rs = preparedStatement.executeQuery();
							while(rs.next())
							{
								tree_monthTotal += rs.getInt(1);
							}
						}
						tempList.add(treeList.get(i).toString().split("##")[1]);
						tempList.add(tree_monthTotal);
						tree1_uploadList.add(tempList);
					}
				}
				else if(showType==2)//月数据查询 查询一月之中31天的数据
				{
					tree1_uploadList = new ArrayList();
					for(int i=0; i<treeList.size(); i++)//按照1级部门开始循环查询相应信息
					{
						List tempList = new ArrayList();
						int tree_dayTotal = 0;
						for(int j=1; j<=31; j++)//按照月份31天依次查询
						{
							selectSQL = "select count(*)  from frame_upload fu where fu.tree2_id = ? and substr(fu.upload_time, 0, 8) = ?";
							preparedStatement = conn.prepareStatement(selectSQL);
							preparedStatement.setString(1, treeList.get(i).toString().split("##")[0]);
							preparedStatement.setString(2, yearORmonth+(j<10?("0"+j):j));
							rs = preparedStatement.executeQuery();
							while(rs.next())
							{
								tree_dayTotal += rs.getInt(1);
							}
						}
						tempList.add(treeList.get(i).toString().split("##")[1]);
						tempList.add(tree_dayTotal);
						tree1_uploadList.add(tempList);
					}
				}
			}
			else
			{
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return tree1_uploadList;
	}

	/**
	 * 上传日志图片展示 -- 警员查询
	 * @param userId 警员id
	 * @param beginTime 查询开始时间
	 * @param endTime 查询截止时间
	 * @return list_uploadForm
	 */
	public List<UploadForm> uploadManagerQuery_ByUser(String userId, String beginTime, String endTime)
	{
		List list_uploadForm = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select fu.*,";
				  selectSQL += "(select user_name from frame_user where user_id = fu.user_id) as USERNAME,";
				  selectSQL += "(select user_name from frame_user where user_id = fu.edit_id) as EDITNAME,";
				  selectSQL += "(select tree_name from frame_tree where tree_id = fu.tree2_id) as TREENAME";
				  selectSQL += " from frame_upload fu";
				  selectSQL += " where fu.edit_id = ?";
				  selectSQL += " and substr(fu.upload_time, 0, 8) >= ?";
				  selectSQL += " and substr(fu.upload_time, 0, 8) <= ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, beginTime);
			preparedStatement.setString(3, endTime);
			rs = preparedStatement.executeQuery();
			list_uploadForm = new ArrayList<UploadForm>();
			while(rs.next())
			{
				uploadForm = new UploadForm();
				uploadForm.setUploadId(rs.getLong("UPLOAD_ID"));
				uploadForm.setUserId(rs.getLong("USER_ID"));
				uploadForm.setUserName(rs.getString("USERNAME"));
				uploadForm.setEditId(rs.getLong("EDIT_ID"));
				uploadForm.setEditName(rs.getString("EDITNAME"));
				uploadForm.setUploadName(rs.getString("UPLOAD_NAME"));
				uploadForm.setPlayPath(rs.getString("PLAY_PATH"));
				uploadForm.setUploadTime(rs.getString("UPLOAD_TIME"));
				uploadForm.setFileState(rs.getString("FILE_STATE"));
				uploadForm.setTree1Id(rs.getLong("TREE1_ID"));
				uploadForm.setTree2Id(rs.getLong("TREE2_ID"));
				uploadForm.setTreeName(rs.getString("TREENAME"));
				list_uploadForm.add(uploadForm);
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_uploadForm;
	}

	/**
	 * 上传日志图片展示 -- 警员查询
	 * @param showType 0-年 1-月
	 * @param yearORmonth 查询时间 showType为0则是年yyyy showType为1则是月yyyyMM
	 * @param userId 警员id
	 * @return List[ [1月总数,2月总数,...,12月总数]],[[1月总数,2月总数,...,12月总数]]...[[1月总数,2月总数,...,12月总数]] ] 警员1年或1月的总上传量
	 */
	public List uploadImgQuery_ByUser(int showType, String yearORmonth, String userId)
	{
		List user_uploadList = null;
		try
		{
			conn = dataSource.getConnection();
			if(showType==1 || showType==2)
			{
				if(showType==1)//年数据查询 查询一年之中12个月的数据
				{
					user_uploadList = new ArrayList();
					for(int j=1; j<=12; j++)//按照年份12个月依次查询
					{
						String selectSQL = "select count(*) from frame_upload fu where substr(fu.upload_time,0,6) = ? and fu.edit_id = ?";
						preparedStatement = conn.prepareStatement(selectSQL);
						preparedStatement.setString(1, yearORmonth+(j<10?("0"+j):j));
						preparedStatement.setString(2, userId);
						rs = preparedStatement.executeQuery();
						while(rs.next())
						{
							user_uploadList.add(rs.getString(1));
						}
					}
				}
				else if(showType==2)//月数据查询 查询一月之中31天的数据
				{
					user_uploadList = new ArrayList();
					for(int j=1; j<=31; j++)//按照月份31天依次查询
					{
						String selectSQL = "select count(*) from frame_upload fu where substr(fu.upload_time,0,8) = ? and fu.edit_id = ?";
						preparedStatement = conn.prepareStatement(selectSQL);
						preparedStatement.setString(1, yearORmonth+(j<10?("0"+j):j));
						preparedStatement.setString(2, userId);
						rs = preparedStatement.executeQuery();
						while(rs.next())
						{
							user_uploadList.add(rs.getString(1));
						}
					}
				}
			}
			else
			{
				
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return user_uploadList;
	}

	/**
	 * 公告列表查询
	 * @return List<NoticeForm>
	 */
	public List<NoticeForm> noticeListQuery()
	{
		list_noticeForm = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_notice order by notice_end desc";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_noticeForm = new ArrayList<NoticeForm>();
			while(rs.next())
			{
				NoticeForm noticeForm = new NoticeForm();
				noticeForm.setNoticeId(rs.getLong("notice_id"));
				noticeForm.setNoticeTitle(rs.getString("notice_title"));
				noticeForm.setNoticeContent(rs.getString("notice_content"));
				noticeForm.setNoticeType(rs.getString("notice_type"));
				noticeForm.setCreateTime(rs.getString("create_time"));
				noticeForm.setNoticeBegin(rs.getString("notice_begin"));
				noticeForm.setNoticeEnd(rs.getString("notice_end"));
				list_noticeForm.add(noticeForm);
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_noticeForm;
	}

	/**
	 * 公告查看
	 * @param noticeId
	 * @return NoticeForm
	 */
	public NoticeForm noticeQuery(String noticeId)
	{
		noticeForm = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_notice where notice_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, noticeId);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				noticeForm = new NoticeForm();
				noticeForm.setNoticeId(rs.getLong("notice_id"));
				noticeForm.setNoticeTitle(rs.getString("notice_title"));
				noticeForm.setNoticeContent(rs.getString("notice_content"));
				noticeForm.setNoticeType(rs.getString("notice_type"));
				noticeForm.setCreateTime(rs.getString("create_time"));
				noticeForm.setNoticeBegin(rs.getString("notice_begin"));
				noticeForm.setNoticeEnd(rs.getString("notice_end"));
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return noticeForm;
	}

	/**
	 * 公告添加
	 * @param noticeForm
	 * @return 0-添加成功； 1-添加失败 系统异常;
	 */
	public int noticeAdd(NoticeForm noticeForm)
	{
		try
		{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String insertSQL = "insert into frame_notice(notice_id,notice_title,notice_content,notice_type,create_time,notice_begin,notice_end)";
			insertSQL += " values(seq_notice_id.nextval,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setString(1, noticeForm.getNoticeTitle());
			preparedStatement.setString(2, noticeForm.getNoticeContent());
			preparedStatement.setString(3, noticeForm.getNoticeType());
			preparedStatement.setString(4, DateUtils.getChar14());
			preparedStatement.setString(5, noticeForm.getNoticeBegin());
			preparedStatement.setString(6, noticeForm.getNoticeEnd());
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("公告添加--添加成功");
				conn.commit();
				return 0;
			}
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
	 * 公告修改
	 * @param noticeForm
	 * @return 0-修改成功； 1-修改失败 系统异常;
	 */
	public int noticeMdf(NoticeForm noticeForm)
	{
		try
		{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String updateSQL = "update frame_notice set notice_title=?,notice_content=?,notice_type=?,create_time=?,notice_begin=?,notice_end=? where notice_id=?";
			preparedStatement = conn.prepareStatement(updateSQL);
			preparedStatement.setString(1, noticeForm.getNoticeTitle());
			preparedStatement.setString(2, noticeForm.getNoticeContent());
			preparedStatement.setString(3, noticeForm.getNoticeType());
			preparedStatement.setString(4, DateUtils.getChar14());
			preparedStatement.setString(5, noticeForm.getNoticeBegin());
			preparedStatement.setString(6, noticeForm.getNoticeEnd());
			preparedStatement.setLong(7, noticeForm.getNoticeId());
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("公告修改--修改成功");
				conn.commit();
				return 0;
			}
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
	 * 公告删除
	 * @param noticeId
	 * @return 0-删除成功； 1-删除失败 系统异常;
	 */
	public int noticeDel(String noticeId)
	{
		try
		{
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String delSQL = "delete from frame_notice where notice_id=?";
			preparedStatement = conn.prepareStatement(delSQL);
			preparedStatement.setString(1, noticeId);
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("公告删除--修改删除");
				conn.commit();
				return 0;
			}
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

	/*
	select * from frame_upload t
	where t.file_stats = 0 
	and to_number(substr(upload_time,0,8))<=to_number(to_char(sysdate-7,'yyyyMMdd'))
	*/

	/**
	 * 过期文件列表 -- 系统定时删除
	 * @param expired 过期时间 yyyyMMdd
	 * @return list_uploadForm
	 */
	public List<UploadForm> expiredSysDekList(String expired)
	{
		List list_uploadForm = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_upload t";
				selectSQL += " where t.file_stats = 0";
				selectSQL += " and to_number(substr(upload_time,0,8))<=to_number(to_char(sysdate-"+expired+",'yyyyMMdd'))";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_uploadForm = new ArrayList<UploadForm>();
			while(rs.next())
			{
				uploadForm = new UploadForm();
				uploadForm.setUploadId(rs.getLong("UPLOAD_ID"));
				uploadForm.setUserId(rs.getLong("USER_ID"));
				uploadForm.setUserName(rs.getString("USERNAME"));
				uploadForm.setEditId(rs.getLong("EDIT_ID"));
				uploadForm.setEditName(rs.getString("EDITNAME"));
				uploadForm.setUploadName(rs.getString("UPLOAD_NAME"));
				uploadForm.setPlayPath(rs.getString("PLAY_PATH"));
				uploadForm.setUploadTime(rs.getString("UPLOAD_TIME"));
				uploadForm.setFileState(rs.getString("FILE_STATE"));
				uploadForm.setTree1Id(rs.getLong("TREE1_ID"));
				uploadForm.setTree2Id(rs.getLong("TREE2_ID"));
				uploadForm.setTreeName(rs.getString("TREENAME"));
				uploadForm.setFileStats(rs.getString("FILE_STATS"));
				list_uploadForm.add(uploadForm);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_uploadForm;
	}

	/**
	 * 过期文件列表 -- 文件查询
	 * @param expired 过期时间 yyyyMMdd
	 * @return list_uploadForm
	 */
	public List<UploadForm> uploadManagerQuery_expired(String expired)
	{
		List list_uploadForm = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select fu.*,";
			selectSQL += " (select user_name from frame_user where user_id = fu.user_id) as USERNAME,";
			selectSQL += " (select user_name from frame_user where user_id = fu.edit_id) as EDITNAME,";
			selectSQL += " (select tree_name from frame_tree where tree_id = fu.tree2_id) as TREENAME";
				selectSQL += " from frame_upload fu where fu.file_state = 'A' and substr(fu.upload_time,0,8)<='"+expired+"'";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_uploadForm = new ArrayList<UploadForm>();
			while(rs.next())
			{
				uploadForm = new UploadForm();
				uploadForm.setUploadId(rs.getLong("UPLOAD_ID"));
				uploadForm.setUserId(rs.getLong("USER_ID"));
				uploadForm.setUserName(rs.getString("USERNAME"));
				uploadForm.setEditId(rs.getLong("EDIT_ID"));
				uploadForm.setEditName(rs.getString("EDITNAME"));
				uploadForm.setUploadName(rs.getString("UPLOAD_NAME"));
				uploadForm.setPlayPath(rs.getString("PLAY_PATH"));
				uploadForm.setUploadTime(rs.getString("UPLOAD_TIME"));
				uploadForm.setFileState(rs.getString("FILE_STATE"));
				uploadForm.setTree1Id(rs.getLong("TREE1_ID"));
				uploadForm.setTree2Id(rs.getLong("TREE2_ID"));
				uploadForm.setTreeName(rs.getString("TREENAME"));
				uploadForm.setFileStats(rs.getString("FILE_STATS"));
				uploadForm.setIpAddr(rs.getString("IP_ADDR"));
				uploadForm.setFileCreatetime(rs.getString("FILE_CREATETIME"));
				list_uploadForm.add(uploadForm);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_uploadForm;
	}

	/**
	 * 文件删除
	 * @param uploadId 
	 * @param fileStats
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public void uploadFileDel(List<UploadForm> list_uploadForm)
	{
		try
		{
			conn = dataSource.getConnection();
			String delSQL = "update frame_upload set file_state = 'F' where upload_id = ?";
			for(int i=0; i<list_uploadForm.size(); i++)
			{
				preparedStatement = conn.prepareStatement(delSQL);
				preparedStatement.setLong(1, ((UploadForm)list_uploadForm.get(i)).getUploadId());
				preparedStatement.executeUpdate();
				fileUtils = new FileUtils();
				fileUtils.deleteFile(SystemConfig.getSystemConfig().getFileRoot()+((UploadForm)list_uploadForm.get(i)).getPlayPath());
			}
			conn.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
	}

	/**
	 * 文件重要性筛选列表
	 * @param uploadId 
	 * @param fileStats
	 * @return list.get(0)-重要性文件list； list.get(1)-非重要性文件list；
	 */
	public List uploadFileStatsList(String[] uploadIdArr)
	{
		list_uploadForm = null;
		list_uploadForm_unStats = null;
		List l = null;
		try
		{
			conn = dataSource.getConnection();
			list_uploadForm = new ArrayList<UploadForm>(){};
			list_uploadForm_unStats = new ArrayList<UploadForm>(){};
			l = new ArrayList();
			String selectSQL = "select fu.*,(select tree_name from frame_tree where tree_id = fu.tree2_id) as TREENAME,(select user_name from frame_user where user_id = fu.user_id) as USERNAME,(select user_name from frame_user where user_id = fu.edit_id) as EDITNAME from frame_upload fu where fu.upload_id = ?";
			for(int i=0; i<uploadIdArr.length; i++)
			{
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, uploadIdArr[i]);
				rs = preparedStatement.executeQuery();
				while(rs.next())
				{
					uploadForm = new UploadForm();
					uploadForm.setUploadId(rs.getLong("UPLOAD_ID"));
					uploadForm.setUserId(rs.getLong("USER_ID"));
					uploadForm.setUserName(rs.getString("USERNAME"));
					uploadForm.setEditId(rs.getLong("EDIT_ID"));
					uploadForm.setEditName(rs.getString("EDITNAME"));
					uploadForm.setUploadName(rs.getString("UPLOAD_NAME"));
					uploadForm.setPlayPath(rs.getString("PLAY_PATH"));
					uploadForm.setUploadTime(rs.getString("UPLOAD_TIME"));
					uploadForm.setFileState(rs.getString("FILE_STATE"));
					uploadForm.setTree1Id(rs.getLong("TREE1_ID"));
					uploadForm.setTree2Id(rs.getLong("TREE2_ID"));
					uploadForm.setTreeName(rs.getString("TREENAME"));
					uploadForm.setFileStats(rs.getString("FILE_STATS"));
					if(uploadForm.getFileStats().equals("1"))
					{
						list_uploadForm.add(uploadForm);
					}
					else
					{
						list_uploadForm_unStats.add(uploadForm);
					}
				}
			}
			l.add(list_uploadForm);
			l.add(list_uploadForm_unStats);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return l;
	}
	


	/**
	 * 上传列表展示 -- 部门查询
	 * @param treeId 部门id
	 * @param parentTreeId 上级部门id
	 * @param beginTime 查询开始时间
	 * @param endTime 查询截止时间
	 * @param uploadUserId 文件上传人
	 * @param fileStats 文件重要性 1-重要
	 * @param fileRemark 备注说明
	 * @return list_uploadForm
	 */
	public List<UploadForm> uploadManagerQuery_ByTree(String treeId, String beginTime,
			 String endTime, String uploadUserId, String fileStats, String fileRemark)
	{
		List list_uploadForm = null;
		boolean isWhere = true;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select fu.*,";
				  selectSQL += "(select user_name from frame_user where user_id = fu.user_id) as USERNAME,";
				  selectSQL += "(select user_name from frame_user where user_id = fu.edit_id) as EDITNAME,";
				  selectSQL += "(select tree_name from frame_tree where tree_id = fu.tree2_id) as TREENAME";
				  selectSQL += " from frame_upload fu ";
				  if(!fileRemark.equals(""))
				  {
					  if(isWhere){selectSQL+="where";isWhere=false;}
					  selectSQL += " fu.file_remark like'%"+fileRemark+"%'";
				  }
				  if(!treeId.equals(""))
				  {
					  if(isWhere){selectSQL+="where";isWhere=false;}else{selectSQL+="and";}
					  selectSQL += " fu.tree2_id = "+treeId;
				  }
				  if(beginTime.equals("") || beginTime.equals(""))
				  {
					  
				  }
				  else
				  {
					  if(isWhere){selectSQL+="where";isWhere=false;}else{selectSQL+="and";}
					  selectSQL += " substr(fu.upload_time, 0, 8) >= '"+beginTime+"'";
					  selectSQL += " and substr(fu.upload_time, 0, 8) <= '"+endTime+"'";
				  }
				  if(!uploadUserId.equals(""))
				  {
					  if(isWhere){selectSQL+="where";isWhere=false;}else{selectSQL+="and";}
					  selectSQL += " fu.edit_id = "+uploadUserId;
				  }
				  if(!fileStats.equals(""))
				  {
					  if(isWhere){selectSQL+="where";isWhere=false;}else{selectSQL+="and";}
					  selectSQL += " fu.file_stats = "+fileStats;
				  }
				  if(isWhere){selectSQL+="where fu.file_state='A'";isWhere=false;}else{selectSQL+="and fu.file_state='A'";}
				  selectSQL += " order by fu.upload_id desc";
//				  System.out.println(selectSQL);
			preparedStatement = conn.prepareStatement(selectSQL);
			//preparedStatement.setString(1, treeId);
			//preparedStatement.setString(2, beginTime);
			//preparedStatement.setString(3, endTime);
			rs = preparedStatement.executeQuery();
			list_uploadForm = new ArrayList<UploadForm>();
			while(rs.next())
			{
				uploadForm = new UploadForm();
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
				list_uploadForm.add(uploadForm);
			}
		}
		catch(Exception ex)
		{
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return list_uploadForm;
	}

	/**
	 * 文件管理 -- 文件查询
	 * 
	 * @param treeId
	 *            部门id
	 * @param parentTreeId
	 *            上级部门id
	 * @param beginTime
	 *            查询开始时间
	 * @param endTime
	 *            查询截止时间
	 * @param uploadUserId
	 *            文件上传人
	 * @param fileCreateUserId
	 * 			  采集人
	 * @param fileStats
	 *            文件重要性 1-重要
	 * @param fileRemark
	 *            备注说明
	 * @param pagecute 当前页 默认第1页
	 * @param pageline 每页行数 默认10行
	 * @return list_uploadForm
	 */
	public Page uploadManagerQuery_ByTree(String uploadName, String treeId, String parentTreeId,
			String beginTime, String endTime, String uploadUserId,
			String fileCreateUserId, String fileStats, String fileRemark, int pagecute, int pageline) {
		Page page = null;
		List list_uploadForm = null;
		try {
			conn = dataSource.getConnection();
			String countSQL = "select count(*) from frame_upload fu ";
			String selectSQL = "select * from (";
			selectSQL += "select rst.*, rownum row_num from(";
			selectSQL += "select fu.*,";
			selectSQL += "(select user_name from frame_user where user_id = fu.user_id) as USERNAME,";
			selectSQL += "(select user_name from frame_user where user_id = fu.edit_id) as EDITNAME,";
			selectSQL += "(select tree_name from frame_tree where tree_id = fu.tree2_id) as TREENAME";
			selectSQL += " from frame_upload fu where 1=1";
//			if (treeId.equals(parentTreeId)) {
//				selectSQL += " where fu.tree1_id = " + treeId;
//				countSQL += " where fu.tree1_id = " + treeId;
//			} else {
//				selectSQL += " where fu.tree2_id = " + treeId;
//				countSQL += " where fu.tree2_id = " + treeId;
//			}
			countSQL += " where 1=1 and fu.file_state = 'A'";
			selectSQL += " and file_state = 'A'";
			if (beginTime.equals("") || beginTime.equals("")) {

			} else {
//				selectSQL += " and substr(fu.upload_time, 0, 8) >= '"
//						+ beginTime + "'";
//				selectSQL += " and substr(fu.upload_time, 0, 8) <= '" + endTime
//						+ "'";
//				countSQL += " and substr(fu.upload_time, 0, 8) >= '"
//						+ beginTime + "'";
//				countSQL += " and substr(fu.upload_time, 0, 8) <= '" + endTime
//						+ "'";
				selectSQL += " and fu.upload_time >= '"
						+ beginTime + "'";
				selectSQL += " and fu.upload_time <= '" + endTime
						+ "'";
				countSQL += " and fu.upload_time >= '"
						+ beginTime + "'";
				countSQL += " and fu.upload_time <= '" + endTime
						+ "'";
			}
			if (!uploadUserId.equals("")) {
				selectSQL += " and fu.user_id = " + uploadUserId;
				countSQL += " and fu.user_id = " + uploadUserId;
			}
			if (!fileCreateUserId.equals("")) {
				selectSQL += " and fu.edit_id = " + fileCreateUserId;
				countSQL += " and fu.edit_id = " + fileCreateUserId;
			}
			if (!fileStats.equals("")) {
				selectSQL += " and fu.file_stats = " + fileStats;
				countSQL += " and fu.file_stats = " + fileStats;
			}
			if (!fileRemark.equals("")) {
				selectSQL += " and fu.file_remark like '%" + fileRemark + "%'";
				countSQL += " and fu.file_remark like '%" + fileRemark + "%'";
			}
			if (!uploadName.equals("")) {
				selectSQL += " and fu.upload_name like '%" + uploadName + "%'";
				countSQL += " and fu.upload_name like '%" + uploadName + "%'";
			}
			selectSQL += " order by fu.upload_id desc";
			selectSQL += ")rst)where row_num>="+((pagecute-1)*pageline+1)+" and row_num<="+pagecute*pageline;
			page = new Page();
			preparedStatement = conn.prepareStatement(countSQL);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				page.setTotal(rs.getInt(1));
			}
			page.setDbLine(pageline);
			page.setPageCute(pagecute);
			preparedStatement = conn.prepareStatement(selectSQL);
			System.out.println("文件管理 -- 文件查询SQL======"+selectSQL);
			// preparedStatement.setString(1, treeId);
			// preparedStatement.setString(2, beginTime);
			// preparedStatement.setString(3, endTime);
			rs = preparedStatement.executeQuery();
			list_uploadForm = new ArrayList<UploadForm>();
			while (rs.next()) {
				uploadForm = new UploadForm();
				uploadForm.setUploadId(rs.getLong("UPLOAD_ID"));
				uploadForm.setUserId(rs.getLong("USER_ID"));
				uploadForm.setUserName(rs.getString("USERNAME"));
				uploadForm.setEditId(rs.getLong("EDIT_ID"));
				uploadForm.setEditName(rs.getString("EDITNAME"));
				uploadForm.setUploadName(rs.getString("UPLOAD_NAME"));
				uploadForm.setPlayPath(rs.getString("PLAY_PATH"));
				uploadForm.setShowPath(rs.getString("SHOW_PATH"));
				uploadForm.setUploadTime(rs.getString("UPLOAD_TIME"));
				uploadForm.setFileCreatetime(rs.getString("FILE_CREATETIME"));
				uploadForm.setFileState(rs.getString("FILE_STATE"));
				uploadForm.setTree1Id(rs.getLong("TREE1_ID"));
				uploadForm.setTree2Id(rs.getLong("TREE2_ID"));
				uploadForm.setTreeName(rs.getString("TREENAME"));
				uploadForm.setFileStats(rs.getString("FILE_STATS"));
				uploadForm.setFileRemark(rs.getString("FILE_REMARK"));
				uploadForm.setFileSavePath(rs.getString("REAL_PATH"));
				uploadForm.setFlvPath(rs.getString("FLV_PATH") == null ? ""
						: rs.getString("FLV_PATH"));
				list_uploadForm.add(uploadForm);
			}
			page.setListObject(list_uploadForm);
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return page;
	}

	/**
	 * 查询需要格式化的avi
	 * @return List<RoleForm>
	 */
	public UploadForm willMake()
	{
		logger.info("查询需要格式化的avi--开始");
		UploadForm uploadForm_ = null;
		try
		{
			conn = dataSource.getConnection();
			String selectSQL = "select fu.* from frame_upload fu";
			  selectSQL += " where fu.real_path = '"+SystemConfig.getSystemConfig().getReadFilePath()+"'";
			  selectSQL += " and fu.file_state = 'C'";
			  selectSQL += " order by fu.upload_id asc";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			boolean notSelect = true;  
			while(rs.next() && notSelect)
			{
				uploadForm_ = new UploadForm();
				uploadForm_.setUploadId(rs.getLong("UPLOAD_ID"));
				uploadForm_.setUserId(rs.getLong("USER_ID"));
				uploadForm_.setUserName(rs.getString("USERNAME"));
				uploadForm_.setEditId(rs.getLong("EDIT_ID"));
				uploadForm_.setEditName(rs.getString("EDITNAME"));
				uploadForm_.setUploadName(rs.getString("UPLOAD_NAME"));
				uploadForm_.setPlayPath(rs.getString("PLAY_PATH"));
				uploadForm_.setUploadTime(rs.getString("UPLOAD_TIME"));
				uploadForm_.setFileState(rs.getString("FILE_STATE"));
				uploadForm_.setTree1Id(rs.getLong("TREE1_ID"));
				uploadForm_.setTree2Id(rs.getLong("TREE2_ID"));
				uploadForm_.setTreeName(rs.getString("TREENAME"));
				notSelect = false;
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("查询需要格式化的avi--结束 数据库连接释放");
		}
		return uploadForm_;
	}

	/**
	 * 需要格式化的avi完成
	 * @return List<RoleForm>
	 */
	public int makeFlvOver(UploadForm frameUploadForm)
	{
		logger.info("需要格式化的avi完成--开始");
		int rst = Constants.ACTION_FAILED;
		try
		{
			conn = dataSource.getConnection();
			String updateSQL = "update frame_upload set flv_path = ?, file_state = 'A' where upload_id = ?";
			preparedStatement = conn.prepareStatement(updateSQL);
			preparedStatement.setString(1, frameUploadForm.getFlvPath());
			preparedStatement.setLong(2, frameUploadForm.getUserId());
			int updateResult = preparedStatement.executeUpdate();
			if(updateResult>0)
			{
				logger.info("需要格式化的avi完成--添加成功");
				conn.commit();
				return 0;
			}
			rst = Constants.ACTION_SUCCESS;
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		finally
		{
			Comment.closeConnection(conn, preparedStatement, rs);
			logger.info("需要格式化的avi完成--结束 数据库连接释放");
			return rst;
		}
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
}