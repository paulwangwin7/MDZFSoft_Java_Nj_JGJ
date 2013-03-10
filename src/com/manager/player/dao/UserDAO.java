package com.manager.player.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbcp.BasicDataSource;

import com.manager.pub.bean.RoleForm;
import com.manager.pub.bean.TreeForm;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.bean.UrlForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.bean.Page;
import com.manager.pub.util.Comment;
import com.manager.pub.util.DateUtils;

public class UserDAO {
	private static Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private static ResultSet rs = null;
	private BasicDataSource dataSource;

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

	private UploadForm uploadForm = null;
	private List<UploadForm> list_uploadForm = null;
	private List<UploadForm> list_uploadForm_unStats = null;

	/**
	 * 用户登录
	 * 
	 * @return null-登录失败
	 */
	public UserForm userLogin(String loginName, String loginPswd) {
		userForm = null;
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select t.*,(select fr.role_name from frame_role fr where fr.role_id = t.role_id) as ROLE_NAME,(select ft.tree_name from frame_tree ft where ft.tree_id = t.tree_id) as TREE_NAME from frame_user t where t.login_name = ? and t.login_pswd = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, loginName);
			preparedStatement.setString(2, loginPswd);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
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
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return userForm;
	}

	/**
	 * 客户端用户登录
	 * 
	 * @return null-登录失败
	 */
	public UserForm clientLogin(String userCode) {
		userForm = null;
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select t.*,(select fr.role_name from frame_role fr where fr.role_id = t.role_id) as ROLE_NAME,(select ft.tree_name from frame_tree ft where ft.tree_id = t.tree_id) as TREE_NAME from frame_user t where t.user_code = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, userCode);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
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
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return userForm;
	}

	/**
	 * 查询所有角色列表
	 * @param pagecute 当前第几页
	 * @param pageline 每页多少天数据
	 * @return List<RoleForm>
	 */
	public Page queryRoleList(int pagecute, int pageline) {
		Page page = null;
		roleForm = null;
		list_roleForm = null;
		try {
			page = new Page();
			page.setDbLine(pageline);//每页多少行数据
			page.setPageCute(pagecute);//当前页
			conn = dataSource.getConnection();
			String countSQL = "select count(*) from frame_role";
			preparedStatement = conn.prepareStatement(countSQL);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				page.setTotal(rs.getInt(1));//总记录数
			}
			String selectSQL = "select * from (";
			selectSQL += "select rst.*, rownum row_num from(";
			selectSQL += "select * from frame_role order by create_time desc";
			selectSQL += ")rst)where row_num>="+((pagecute-1)*pageline+1)+" and row_num<="+pagecute*pageline;
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_roleForm = new ArrayList<RoleForm>();
			while (rs.next()) {
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
			page.setListObject(list_roleForm);
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return page;
	}

	/**
	 * 查询所有角色列表
	 * @param pagecute 当前第几页
	 * @param pageline 每页多少天数据
	 * @return List<RoleForm>
	 */
	public Page queryRoleAll() {
		Page page = null;
		roleForm = null;
		list_roleForm = null;
		try {
			page = new Page();
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_role order by create_time desc";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_roleForm = new ArrayList<RoleForm>();
			while (rs.next()) {
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
			page.setListObject(list_roleForm);
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return page;
	}

	/**
	 * 角色修改前角色查询
	 * 
	 * @return RoleForm
	 */
	public RoleForm roleQuery(String roleId) {
		roleForm = null;
		try {
			conn = dataSource.getConnection();
			// --step1 确认修改的角色名称在角色表中不会出现（防止角色名称重复）
			String selectSQL = "select * from frame_role where role_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, roleId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				roleForm = new RoleForm();
				roleForm.setRoleId(rs.getLong("ROLE_ID"));
				roleForm.setRoleName(rs.getString("ROLE_NAME"));
				roleForm.setRoleDesc(rs.getString("ROLE_DESC"));
				roleForm.setRoleState(rs.getString("ROLE_STATE"));
				roleForm.setCreateUser(rs.getString("CREATE_USER"));
				roleForm.setCreateTime(rs.getString("CREATE_TIME"));
				roleForm.setTreeId(rs.getString("TREE_ID"));
				roleForm.setUrlIdList(rs.getString("URL_ID_LIST"));
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return roleForm;
	}

	/**
	 * 角色添加
	 * 
	 * @return 0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
	 */
	public int roleAdd(RoleForm roleForm) {
		try {
			conn = dataSource.getConnection();
			// --step1 确认新增的角色名称在角色表中不会出现（防止角色名称重复）
			String selectSQL = "select * from frame_role where role_name = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, roleForm.getRoleName().trim());
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return 2;
			}
			// --step2 新增角色
			else {
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
				conn.commit();
				if (updateResult > 0) {
					return 0;
				}
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 角色修改
	 * 
	 * @return 0-修改成功；1-修改失败 系统超时~；2-该角色出现重名 请换个角色名称
	 */
	public int roleMdf(RoleForm roleForm) {
		try {
			conn = dataSource.getConnection();
			// --step1 确认修改的角色名称在角色表中不会出现（防止角色名称重复）
			String selectSQL = "select * from frame_role where role_name = ? and role_id != ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, roleForm.getRoleName());
			preparedStatement.setLong(2, roleForm.getRoleId());
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return 2;
			}
			// --step2 修改角色
			else {
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
				conn.commit();
				if (updateResult > 0) {
					return 0;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 角色删除
	 * 
	 * @return 0-修改成功；1-修改失败 系统超时~； 2-删除失败 还有其他用户正在使用该角色；
	 */
	public int roleDel(String roleId) {
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			// 检查是否还有用户在使用该角色
			String selectSQL = "select * from frame_user where role_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, roleId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return 2;
			}
			String delSQL = "delete from frame_role where role_id = ?";
			preparedStatement = conn.prepareStatement(delSQL);
			preparedStatement.setString(1, roleId);
			int updateResult = preparedStatement.executeUpdate();
			conn.commit();
			if (updateResult > 0) {
				return 0;
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 查询所有菜单及菜单下对应的所有功能url
	 * 
	 * @return list_menuAndurl [ ['菜单1名称',[urlForm,urlForm...]],
	 *         ['菜单2名称',[urlForm,urlForm...]] ... ]
	 */
	public List queryMenuAndUrlByRoleId(String[] urlId) {
		list_menuAndurl = null;
		list_urlForm = null;
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_menu order by menu_sort";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_menuAndurl = new ArrayList();
			while (rs.next()) {
				List tempList = new ArrayList();
				list_urlForm = new ArrayList<UrlForm>();
				list_urlForm = (List<UrlForm>) (queryFrameUrlByUserMenuId(rs
						.getLong("MENU_ID"), urlId));
				if (list_urlForm != null && list_urlForm.size() > 0) {
					tempList.add(rs.getString("MENU_NAME"));
					tempList.add(list_urlForm);
					list_menuAndurl.add(tempList);
				}
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		System.out.println("list_menuAndurl===" + list_menuAndurl);
		return list_menuAndurl;
	}

	/**
	 * 查询所有菜单及菜单下对应的所有功能url
	 * 
	 * @return list_menuAndurl [ ['菜单1名称',[urlForm,urlForm...]],
	 *         ['菜单2名称',[urlForm,urlForm...]] ... ]
	 */
	public List queryMenuAndUrl() {
		list_menuAndurl = null;
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_menu order by menu_sort";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			list_menuAndurl = new ArrayList();
			while (rs.next()) {
				List tempList = new ArrayList();
				tempList.add(rs.getString("MENU_NAME"));
				tempList.add(queryFrameUrlByMenuId(rs.getLong("MENU_ID")));
				list_menuAndurl.add(tempList);
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		System.out.println("list_menuAndurl===" + list_menuAndurl);
		return list_menuAndurl;
	}

	/**
	 * 根据菜单id查询功能地址列表
	 * 
	 * @param menuId
	 *            菜单id
	 * @return
	 */
	public List<UrlForm> queryFrameUrlByMenuId(long menuId) {
		list_urlForm = null;
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rst = null;
		try {
			connection = dataSource.getConnection();
			String selectSQL = "select * from frame_url where parent_menu_id = ? order by url_sort";
			pStatement = connection.prepareStatement(selectSQL);
			pStatement.setLong(1, menuId);
			rst = pStatement.executeQuery();
			list_urlForm = new ArrayList<UrlForm>();
			while (rst.next()) {
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
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(connection, pStatement, rst);
		}
		return list_urlForm;
	}

	/**
	 * 根据菜单id查询功能地址列表
	 * 
	 * @param menuId
	 *            菜单id
	 * @return
	 */
	public List<UrlForm> queryFrameUrlByUserMenuId(long menuId, String[] urlId) {
		list_urlForm = null;
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rst = null;
		try {
			connection = dataSource.getConnection();
			String selectSQL = "select * from frame_url where parent_menu_id = ? order by url_sort";
			pStatement = connection.prepareStatement(selectSQL);
			pStatement.setLong(1, menuId);
			rst = pStatement.executeQuery();
			list_urlForm = new ArrayList<UrlForm>();
			while (rst.next()) {
				urlForm = new UrlForm();
				urlForm.setUrlId(rst.getLong("URL_ID"));
				urlForm.setUrlValue(rst.getString("URL_VALUE"));
				urlForm.setUrlName(rst.getString("URL_NAME"));
				urlForm.setUrlDesc(rst.getString("URL_DESC"));
				urlForm.setUrlState(rst.getString("URL_STATE"));
				urlForm.setParentMenuId(rst.getString("PARENT_MENU_ID"));
				urlForm.setUrlSort(rst.getString("URL_SORT"));
				urlForm.setUrlTab(rst.getString("URL_TAB"));
				for (int i = 0; i < urlId.length; i++) {
					if ((urlForm.getUrlId() + "").equals(urlId[i])) {
						list_urlForm.add(urlForm);
						break;
					}
				}
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(connection, pStatement, rst);
		}
		return list_urlForm;
	}

	/**
	 * 部门添加
	 * 
	 * @return 0-添加成功；1-添加失败 系统超时~
	 */
	public int treeAdd(TreeForm treeForm) {
		try {
			conn = dataSource.getConnection();
			 //--step1 确认新增的角色名称在角色表中不会出现（防止角色名称重复）
			 String selectSQL = "select * from frame_tree where tree_name = ?";
			 preparedStatement = conn.prepareStatement(selectSQL);
			 preparedStatement.setString(1, treeForm.getTreeName().trim());
			 rs = preparedStatement.executeQuery();
			 if(rs.next())
			 {
			 return 2;
			 }
			 //--step2 新增角色
			 else
			 {
			conn.setAutoCommit(false);
			String insertSQL = "insert into frame_tree values(seq_tree_id.nextval,?,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setString(1, treeForm.getTreeName());
			preparedStatement.setString(2, treeForm.getTreeDesc());
			preparedStatement.setString(3, treeForm.getTreeState());
			preparedStatement.setLong(4, treeForm.getCreateUser());
			preparedStatement.setString(5, DateUtils.getChar14());
			preparedStatement.setLong(6, treeForm.getParentTreeId());
			preparedStatement.setString(7, treeForm.getOrderBy());
			int updateResult = preparedStatement.executeUpdate();
			conn.commit();
			if (updateResult > 0) {
				return 0;
			}
			 }
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 部门修改
	 * 
	 * @return 0-修改成功；1-修改失败 系统超时~
	 */
	public int treeMdf(TreeForm treeForm) {
		try {
			conn = dataSource.getConnection();
			// //--step1 确认修改的角色名称在角色表中不会出现（防止角色名称重复）
			// String selectSQL = "select * from frame_role where role_name = ?
			// and role_id != ?";
			// preparedStatement = conn.prepareStatement(selectSQL);
			// preparedStatement.setString(1, roleForm.getRoleName());
			// preparedStatement.setLong(2, roleForm.getRoleId());
			// rs = preparedStatement.executeQuery();
			// if(rs.next())
			// {
			// return 2;
			// }
			// //--step2 修改角色
			// else
			// {
			String selectSQL = "select * from frame_tree where tree_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setLong(1, treeForm.getTreeId());
			rs = preparedStatement.executeQuery();
			while(rs.next())//先查询 后修改
			{

				conn.setAutoCommit(false);
				String updateSQL = "update frame_tree set tree_name=?,tree_desc=?,tree_state=?,create_user=?,create_time=?,parent_tree_id=?,order_by=? where tree_id=?";
				preparedStatement = conn.prepareStatement(updateSQL);
				preparedStatement.setString(1, treeForm.getTreeName());
				preparedStatement.setString(2, treeForm.getTreeDesc());
				preparedStatement.setString(3, rs.getString("TREE_STATE"));
				preparedStatement.setLong(4, treeForm.getCreateUser());
				preparedStatement.setString(5, DateUtils.getChar14());
				preparedStatement.setLong(6, rs.getLong("PARENT_TREE_ID"));
				preparedStatement.setString(7, rs.getString("ORDER_BY"));
				preparedStatement.setLong(8, treeForm.getTreeId());
				int updateResult = preparedStatement.executeUpdate();
				conn.commit();
				if (updateResult > 0) {
					return 0;
				}
			}
			// }
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 部门删除
	 * 
	 * @return 0-修改成功；1-修改失败 系统超时~； 2-删除失败 还有其他用户正在使用该角色；
	 */
	public int treeDel(String treeId) {
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			// 检查是否还有用户在使用该部门
			String selectSQL = "select * from frame_user where tree_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, treeId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return 2;
			}
			String delSQL = "delete from frame_tree where tree_id = ?";
			preparedStatement = conn.prepareStatement(delSQL);
			preparedStatement.setString(1, treeId);
			int updateResult = preparedStatement.executeUpdate();
			conn.commit();
			if (updateResult > 0) {
				return 0;
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 部门查询
	 * 
	 * @param treeId
	 * @return 
	 */
	public TreeForm treeByTreeId(long treeId) {
		treeForm = null;
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_tree where tree_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setLong(1, treeId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				treeForm = new TreeForm();
				treeForm.setTreeId(rs.getLong("TREE_ID"));
				treeForm.setTreeName(rs.getString("TREE_NAME"));
				treeForm.setTreeDesc(rs.getString("TREE_DESC"));
				treeForm.setTreeState(rs.getString("TREE_STATE"));
				treeForm.setCreateUser(rs.getLong("CREATE_USER"));
				treeForm.setCreateTime(rs.getString("CREATE_TIME"));
				treeForm.setParentTreeId(rs.getLong("PARENT_TREE_ID"));
				treeForm.setOrderBy(rs.getString("ORDER_BY"));
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return treeForm;
	}

	/**
	 * 查询所有一级部门以及以下对应的所有部门
	 * 
	 * @param userTreeId
	 *            用户的部门id
	 * @return list_totalTree [ [treeForm,[treeForm,treeForm...]],
	 *         [treeForm,[treeForm,treeForm...]] ... ]
	 */
	public List queryTotalTreeList(long userTreeId, HttpServletRequest request) {
		list_totalTree = null;
		treeForm = null;
		try {
			conn = dataSource.getConnection();
			// step 根据userTreeId检查parent_tree_id来定位用户处于第几级（0-一级大队；1-二级中队）
			String selectSQL = "select parent_tree_id from frame_tree where tree_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setLong(1, userTreeId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String parentTreeId = rs.getString(1).trim();
				if (parentTreeId.equals("0")) {
					request.setAttribute("ifFirst", "0");
					selectSQL = "select * from frame_tree where tree_id = ?";
					preparedStatement = conn.prepareStatement(selectSQL);
					preparedStatement.setLong(1, userTreeId);
					rs = preparedStatement.executeQuery();
					list_totalTree = new ArrayList();
					while (rs.next()) {
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
						tempList.add(queryTreeByParentId(treeForm.getTreeId(),
								1));
						list_totalTree.add(tempList);
					}
				} else {
					request.setAttribute("ifFirst", "1");
					selectSQL = "select * from frame_tree where tree_id = ?";
					preparedStatement = conn.prepareStatement(selectSQL);
					preparedStatement.setString(1, parentTreeId);
					rs = preparedStatement.executeQuery();
					list_totalTree = new ArrayList();
					while (rs.next()) {
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
						tempList.add(queryTreeByParentId(userTreeId, 2));
						list_totalTree.add(tempList);
					}
				}
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		System.out.println("list_totalTree===" + list_totalTree);
		return list_totalTree;
	}

	/**
	 * 根据部门父节点id查询子部门列表
	 * 
	 * @param treeId
	 *            父节点treeid
	 * @param sqlType
	 *            1-一级部门id；2-二级部门id
	 * @return
	 */
	public List<TreeForm> queryTreeByParentId(long parentTreeId, int sqlType) {
		list_treeForm = null;
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rst = null;
		try {
			connection = dataSource.getConnection();
			String selectSQL = "";
			if (sqlType == 1) {
				selectSQL = "select * from frame_tree where parent_tree_id = ? order by order_by,tree_id";
			} else if (sqlType == 2) {
				selectSQL = "select * from frame_tree where tree_id = ?";
			} else {
				selectSQL = "select * from frame_tree where parent_tree_id = ? order by order_by,tree_id";
			}
			pStatement = connection.prepareStatement(selectSQL);
			pStatement.setLong(1, parentTreeId);
			rst = pStatement.executeQuery();
			list_treeForm = new ArrayList<TreeForm>();
			while (rst.next()) {
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
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(connection, pStatement, rst);
		}
		return list_treeForm;
	}

	/**
	 * 根据所有用户
	 * 
	 * @param UserForm
	 * @param pagecute 当前第几页
	 * @param pageline 每页多少条数据
	 * @return List<UserForm>
	 */
	public Page queryUserListByUserForm(UserForm userForm, String treeId, int pagecute, int pageline) {
		Page page = null;
		list_userForm = null;
		try {
			page = new Page();
			long userParentTree = getUserParentTreeId(userForm.getTreeId());
			conn = dataSource.getConnection();
			String countSQL = "select count(*) from frame_user t, frame_tree t2, frame_role t3";
			countSQL += " where t.tree_id = t2.tree_id and t.role_id = t3.role_id";
			StringBuffer selectSQL = new StringBuffer("select * from (select rst.*, rownum row_num from(");
			selectSQL.append("select t.*,t2.tree_name as TREE_NAME,t3.role_name as ROLE_NAME from frame_user t, frame_tree t2, frame_role t3");
			selectSQL.append(" where t.tree_id = t2.tree_id and t.role_id = t3.role_id");
			if (userForm.getUserName() != null) {
				selectSQL.append(" and t.user_name like '%" + (userForm.getUserName() == null ? "" : userForm.getUserName()) + "%'");
				countSQL += " and t.user_name like '%" + (userForm.getUserName() == null ? "" : userForm.getUserName()) + "%'";
			}
			if (userForm.getUserCode() != null) {
				if (!userForm.getUserCode().equals("")) {
					selectSQL.append(" and t.user_code = '" + userForm.getUserCode() + "'");
					countSQL += "and t.user_code = '" + userForm.getUserCode() + "'";
				}
			}
			if (userForm.getSex() != null) {
				selectSQL.append(" and t.sex = '" + userForm.getSex() + "'");
				countSQL += " and t.sex = '" + userForm.getSex() + "'";
			}
			if (userForm.getTreeId() != 0) {
				if (userParentTree > -1 && userParentTree == 0) {
					selectSQL.append(" and (t2.tree_id = " + userForm.getTreeId() + " or t2.parent_tree_id = " + userForm.getTreeId() + ")");
					countSQL += " and (t2.tree_id = " + userForm.getTreeId() + " or t2.parent_tree_id = " + userForm.getTreeId() + ")";
				} else {
					selectSQL.append(" and t.tree_id = " + userForm.getTreeId() + "");
					countSQL += " and t.tree_id = " + userForm.getTreeId() + "";
				}
			}
			if(!treeId.equals(""))
			{
				selectSQL.append(" and t.tree_id = " + treeId);
				countSQL += " and t.tree_id = " + treeId;
			}
			selectSQL.append(" order by t.create_time desc");
			selectSQL.append(")rst)where row_num>="+((pagecute-1)*pageline+1)+" and row_num<="+pagecute*pageline);
			System.out.println("countSQL================="+countSQL.toString());
			System.out.println("selectSQL================="+selectSQL.toString());
			preparedStatement = conn.prepareStatement(countSQL);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				page.setTotal(rs.getInt(1));
			}
			preparedStatement = conn.prepareStatement(selectSQL.toString());
			rs = preparedStatement.executeQuery();
			list_userForm = new ArrayList<UserForm>();
			while (rs.next()) {
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
			page.setPageCute(pagecute);
			page.setDbLine(pageline);
			page.setListObject(list_userForm);
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return page;
	}

	/**
	 * 根据用户id查询用户信息详情
	 * 
	 * @param userId
	 * @return
	 */
	public UserForm queryUserFormByUserId(String userId) {
		userForm = null;
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select fu.*,ft.tree_name as treeName,fr.role_name as roleName from frame_user fu, frame_tree ft, frame_role fr";
			selectSQL += " where fu.tree_id = ft.tree_id and fu.role_id = fr.role_id and user_id="
					+ userId;
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
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
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return userForm;
	}

	/**
	 * 根据用户code查询用户信息详情
	 * 
	 * @param userId
	 * @return
	 */
	public UserForm queryUserFormByUserCode(String userCode) {
		userForm = null;
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_user where user_code = '"+ userCode +"'";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
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
				list_userForm.add(userForm);
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return userForm;
	}

	/**
	 * 注册用户
	 * 
	 * @param userForm
	 * @return 0-成功； 1-失败 系统异常； 2-登录账号重名
	 */
	public int userRegister(UserForm userForm) {
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_user where login_name = '"
					+ userForm.getLoginName() + "'";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
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
			if (updateResult > 0) {
				return 0;
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 用户信息修改
	 * 
	 * @param userForm
	 * @return 0-成功； 1-失败 系统异常； 2-登录账号重名
	 */
	public int userModify(UserForm userForm) {
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_user where login_name = '"
					+ userForm.getLoginName() + "' and user_id !="
					+ userForm.getUserId();
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return 2;
			}
			// String insertSQL = "update frame_user set login_name =
			// ?,login_pswd=?,user_name=?,user_code=?,sex=?,user_idcard=?,card_typeid=?,card_code=?,tree_id=?,role_id=?,create_time=?,user_state=?
			// where user_id=?";
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
			preparedStatement.setString(10, userForm.getCreateTime());
			preparedStatement.setString(11, userForm.getUserState());
			preparedStatement.setLong(12, userForm.getUserId());
			int updateResult = preparedStatement.executeUpdate();
			if (updateResult > 0) {
				return 0;
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 用户密码修改
	 * 
	 * @param userForm
	 * @param oldPswd
	 *            旧密码
	 * @return 0-成功； 1-失败 系统异常； 2-登录账号重名
	 */
	public int userPasswordModify(UserForm userForm, String oldPswd) {
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select * from frame_user where user_id = '"
					+ userForm.getUserId() + "' and login_pswd = '" + oldPswd
					+ "'";
			preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery();
			if (!rs.next()) {
				return 2;
			}
			String updateSQL = "update frame_user set login_pswd = ? where user_id = ?";
			preparedStatement = conn.prepareStatement(updateSQL);
			preparedStatement.setString(1, userForm.getLoginPswd());
			preparedStatement.setLong(2, userForm.getUserId());
			int updateResult = preparedStatement.executeUpdate();
			if (updateResult > 0) {
				return 0;
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 用户删除
	 * 
	 * @param NoticeForm
	 * @return 0-删除成功； 1-删除失败 系统异常;
	 */
	public int userManagerDel(Long user_id) {
		try {
			// String delSQL="delete from FRAME_USER where user_id=?";
			String delSQL = "update FRAME_USER set user_state='U' where user_id=?";
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			preparedStatement = conn.prepareStatement(delSQL);
			preparedStatement.setLong(1, user_id);
			int updateResult = preparedStatement.executeUpdate();
			if (updateResult > 0) {
				conn.commit();
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 用户文件上传
	 * 
	 * @param uploadForm
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public int uploadFile(UploadForm uploadForm) {
		try {
			conn = dataSource.getConnection();
			String insertSQL = "insert into frame_upload(upload_id,user_id,edit_id,upload_name,play_path,show_path,file_createtime,upload_time,file_state,tree2_id,tree1_id,FILE_REMARK,ip_addr,REAL_PATH,FLV_PATH)";
			insertSQL += "values(seq_upload_id.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setLong(1, uploadForm.getUserId());
			preparedStatement.setLong(2, uploadForm.getEditId());
			preparedStatement.setString(3, uploadForm.getUploadName());
			preparedStatement.setString(4, uploadForm.getPlayPath());
			preparedStatement.setString(5, uploadForm.getShowPath());
			preparedStatement.setString(6, uploadForm.getFileCreatetime());
			preparedStatement.setString(7, DateUtils.getChar14());
			preparedStatement.setString(8, uploadForm.getFileState());
			preparedStatement.setLong(9, uploadForm.getTree2Id());
			preparedStatement.setLong(10, uploadForm.getTree1Id());
			preparedStatement.setString(11, uploadForm.getFileRemark());
			preparedStatement.setString(12, uploadForm.getIpAddr());
			preparedStatement.setString(13, uploadForm.getFileSavePath());
			preparedStatement.setString(14, uploadForm.getFlvPath());
			int updateResult = preparedStatement.executeUpdate();
			if (updateResult > 0) {
				return 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 用户文件上传
	 * 
	 * @param uploadForm
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public UploadForm getFile(UploadForm uploadForm) {
		UploadForm returnInfo = null;
		try {
			conn = dataSource.getConnection();
			String insertSQL = "select t.*,(select ft.tree_name from frame_tree ft where ft.tree_id = t.tree1_id) as TREENAME,(select user_name from frame_user where user_id = t.user_id) as USERNAME,(select user_name from frame_user where user_id = t.edit_id) as EDITNAME from frame_upload t where upload_id = ?";
			preparedStatement = conn.prepareStatement(insertSQL);
			preparedStatement.setLong(1, uploadForm.getUploadId());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				returnInfo = new UploadForm();
				returnInfo.setUploadId(rs.getLong("UPLOAD_ID"));
				returnInfo.setUserId(rs.getLong("USER_ID"));
				returnInfo.setUserName(rs.getString("USERNAME"));
				returnInfo.setEditId(rs.getLong("EDIT_ID"));
				returnInfo.setEditName(rs.getString("EDITNAME"));
				returnInfo.setUploadName(rs.getString("UPLOAD_NAME"));
				returnInfo.setPlayPath(rs.getString("PLAY_PATH"));
				returnInfo.setShowPath(rs.getString("SHOW_PATH"));
				returnInfo.setUploadTime(rs.getString("UPLOAD_TIME"));
				returnInfo.setFileState(rs.getString("FILE_STATE"));
				returnInfo.setTree1Id(rs.getLong("TREE1_ID"));
				returnInfo.setTree2Id(rs.getLong("TREE2_ID"));
				returnInfo.setTreeName(rs.getString("TREENAME"));
				returnInfo.setFileStats(rs.getString("FILE_STATS"));
				returnInfo.setFileRemark(rs.getString("FILE_REMARK"));
				returnInfo.setFileSavePath(rs.getString("REAL_PATH"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return returnInfo;
	}

	/**
	 * 查询用户上级部门id
	 * 
	 * @param uploadForm
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public long getUserParentTreeId(long userTreeId) {
		try {
			conn = dataSource.getConnection();
			String selectSQL = "select parent_tree_id from frame_tree where tree_id = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setLong(1, userTreeId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return rs.getLong(1);
			}
		} catch (Exception ex) {
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return -1;
	}

	/**
	 * 文件加☆★ 222
	 * 
	 * @param uploadId
	 * @param fileStats
	 *            1-重要；0-不重要；
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public int uploadFileStats(String uploadId, int fileStats) {
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String updateSQL = "update frame_upload set file_stats = ? where upload_id = ?";
			preparedStatement = conn.prepareStatement(updateSQL);
			preparedStatement.setInt(1, fileStats);
			preparedStatement.setString(2, uploadId);
			int updateResult = preparedStatement.executeUpdate();
			conn.commit();
			if (updateResult > 0) {
				return 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 文件加备注 222
	 * 
	 * @param uploadId
	 * @param file_remark
	 *            文件重要性备注描述
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public int uploadFileRemark(String uploadId, String file_remark) {
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String updateSQL = "update frame_upload set file_remark = ? where upload_id = ?";
			preparedStatement = conn.prepareStatement(updateSQL);
			preparedStatement.setString(1, file_remark);
			preparedStatement.setString(2, uploadId);
			int updateResult = preparedStatement.executeUpdate();
			conn.commit();
			if (updateResult > 0) {
				return 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return 1;
	}

	/**
	 * 文件删除
	 * 
	 * @param uploadId
	 * @param fileStats
	 * @return 0-成功； 1-失败 系统异常；
	 */
	public List uploadFileDel(List<UploadForm> list_uploadForm) {
		try {
			conn = dataSource.getConnection();
			String delSQL = "delete from frame_upload where upload_id = ?";
			for (int i = 0; i < list_uploadForm.size(); i++) {
				preparedStatement = conn.prepareStatement(delSQL);
				preparedStatement.setLong(1, ((UploadForm) list_uploadForm
						.get(i)).getUploadId());
				preparedStatement.executeUpdate();
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return null;
	}

	/**
	 * 文件重要性筛选列表
	 * 
	 * @param uploadId
	 * @param fileStats
	 * @return list.get(0)-重要性文件list； list.get(1)-非重要性文件list；
	 */
	public List uploadFileStatsList(String[] uploadIdArr) {
		list_uploadForm = null;
		list_uploadForm_unStats = null;
		List l = null;
		try {
			conn = dataSource.getConnection();
			list_uploadForm = new ArrayList<UploadForm>() {
			};
			list_uploadForm_unStats = new ArrayList<UploadForm>() {
			};
			l = new ArrayList();
			String selectSQL = "select fu.*,(select tree_name from frame_tree where tree_id = fu.tree2_id) as TREENAME from frame_upload fu where fu.upload_id = ?";
			for (int i = 0; i < uploadIdArr.length; i++) {
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, uploadIdArr[i]);
				preparedStatement.executeUpdate();
				while (rs.next()) {
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
					if (uploadForm.getFileStats().equals("1")) {
						list_uploadForm.add(uploadForm);
					} else {
						list_uploadForm_unStats.add(uploadForm);
					}
				}
			}
			l.add(list_uploadForm);
			l.add(list_uploadForm_unStats);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Comment.closeConnection(conn, preparedStatement, rs);
		}
		return l;
	}

	/**
	 * 文件管理 -- 文件查询
	 * 
	 * @param uploadName
	 * 			  文件名 模糊查询
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
			selectSQL += " from frame_upload fu ";
			if (treeId.equals(parentTreeId)) {
				selectSQL += " where fu.tree1_id = " + treeId;
				countSQL += " where fu.tree1_id = " + treeId;
			} else {
				selectSQL += " where fu.tree2_id = " + treeId;
				countSQL += " where fu.tree2_id = " + treeId;
			}
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
			selectSQL += " and (file_state!='F' or file_state!='U')";
			countSQL += " and (file_state!='F' or file_state!='U')";
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
	 * 我的主页 -- 我上传的文件 222
	 * 
	 * @param treeId
	 *            部门id
	 * @param parentTreeId
	 *            上级部门id
	 * @param uploadUserId
	 *            文件上传人
	 * @param pagecute 当前页 默认第1页
	 * @param pageline 每页行数 默认10行
	 * @return list_uploadForm
	 */
	public Page uploadQuery_MyUpload(String treeId,
			String parentTreeId, String uploadUserId, int pagecute, int pageline) {
		Page page = null;
		List list_uploadForm = null;
		try {
			page = new Page();
			page.setDbLine(pageline);//每页多少行数据
			page.setPageCute(pagecute);//当前页
			conn = dataSource.getConnection();
			String countSQL = "select count(*) from frame_upload where (tree1_id = ? or tree2_id = ?) and (file_state!='F' or file_state!='U')";
			preparedStatement = conn.prepareStatement(countSQL);
			preparedStatement.setString(1, treeId);
			preparedStatement.setString(2, treeId);
			rs = preparedStatement.executeQuery();
			while(rs.next())
			{
				page.setTotal(rs.getInt(1));//总记录数
			}

			String selectSQL = "select * from (";
			selectSQL += "select rst.*, rownum row_num from(";
			selectSQL += "select fu.*,";
			selectSQL += "(select user_name from frame_user where user_id = fu.user_id) as USERNAME,";
			selectSQL += "(select user_name from frame_user where user_id = fu.edit_id) as EDITNAME,";
			selectSQL += "(select tree_name from frame_tree where tree_id = fu.tree2_id) as TREENAME";
			selectSQL += " from frame_upload fu ";
			if (treeId.equals(parentTreeId)) {
				selectSQL += " where fu.tree1_id = " + treeId;
			} else {
				selectSQL += " where fu.tree2_id = " + treeId;
			}
			if (!uploadUserId.equals("")) {
				selectSQL += " and (fu.edit_id = " + uploadUserId + " or fu.user_id = " + uploadUserId;
			}
			selectSQL += " order by fu.upload_id desc";
			selectSQL += ")rst)where row_num>="+((pagecute-1)*pageline+1)+" and row_num<="+pagecute*pageline;
			preparedStatement = conn.prepareStatement(selectSQL);
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

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
}