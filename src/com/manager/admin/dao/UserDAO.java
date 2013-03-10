package com.manager.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import com.manager.pub.bean.RoleForm;
import com.manager.pub.bean.UrlForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.Comment;
import com.manager.pub.util.DateUtils;

public class UserDAO {
	private static Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private static ResultSet rs = null;
	private BasicDataSource dataSource;

	private UserForm userForm = null;

	/**
	 * 用户添加
	 * @return 0-添加成功；1-添加失败 系统超时~；2-该用户使用的工号已经注册 请检查以前是否注册过账号
	 */
	public int roleAdd(UserForm userForm)
	{
		try
		{
			conn = dataSource.getConnection();
			//--step1 确认新增的用户工号在用户表中不会出现（防止同一工号被重复注册）
			String selectSQL = "select * from frame_user where user_code = ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, userForm.getUserCode());
			rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				return 2;
			}
			//--step2 新增用户
			else
			{
				conn.setAutoCommit(false);
				selectSQL = "insert into frame_user values(seq_user_id.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, userForm.getLoginName());//登录账号
				preparedStatement.setString(2, userForm.getLoginPswd());//登录密码
				preparedStatement.setString(3, userForm.getUserName());//用户名
				preparedStatement.setString(4, userForm.getUserCode());//用户编号（警官编号）
				preparedStatement.setString(5, userForm.getSex());//性别 M-男 W-女
				preparedStatement.setString(6, userForm.getUserIdCard());//身份证号
				preparedStatement.setLong(7, userForm.getCard_typeId());//其他证件类别 0-表示没有填写其他证件号
				preparedStatement.setString(8, userForm.getCardCode());//其他证件号
				preparedStatement.setLong(9, userForm.getTreeId());//所属部门
				preparedStatement.setLong(10, userForm.getRoleId());//所属角色
				preparedStatement.setString(11, DateUtils.getChar14());//创建时间
				preparedStatement.setString(12, userForm.getUserState());//用户状态
				preparedStatement.executeUpdate();
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
	 * 用户修改
	 * @return 0-修改成功；1-修改失败 系统超时~；2-该用户使用的工号已经注册 请检查以前是否注册过账号
	 */
	public int roleMdf(UserForm userForm)
	{
		try
		{
			conn = dataSource.getConnection();
			//--step1 确认修改的用户工号在用户表中其他用户工号中不会出现（防止同一工号被重复注册）
			String selectSQL = "select * from frame_user where user_code = ? and user_id != ?";
			preparedStatement = conn.prepareStatement(selectSQL);
			preparedStatement.setString(1, userForm.getUserCode());
			preparedStatement.setLong(2, userForm.getUserId());
			rs = preparedStatement.executeQuery();
			if(rs.next())
			{
				return 2;
			}
			//--step2 修改用户
			else
			{
				conn.setAutoCommit(false);
				selectSQL = "update frame_user set login_name=?,login_pswd=?,user_name=?,user_code=?,sex=?,user_idcard=?,card_typeid=?,card_code=?,tree_id=?,role_id=?,create_time=?,user_state=? where user_id=?";
				preparedStatement = conn.prepareStatement(selectSQL);
				preparedStatement.setString(1, userForm.getLoginName());//登录账号
				preparedStatement.setString(2, userForm.getLoginPswd());//登录密码
				preparedStatement.setString(3, userForm.getUserName());//用户名
				preparedStatement.setString(4, userForm.getUserCode());//用户编号（警官编号）
				preparedStatement.setString(5, userForm.getSex());//性别 M-男 W-女
				preparedStatement.setString(6, userForm.getUserIdCard());//身份证号
				preparedStatement.setLong(7, userForm.getCard_typeId());//其他证件类别 0-表示没有填写其他证件号
				preparedStatement.setString(8, userForm.getCardCode());//其他证件号
				preparedStatement.setLong(9, userForm.getTreeId());//所属部门
				preparedStatement.setLong(10, userForm.getRoleId());//所属角色
				preparedStatement.setString(11, DateUtils.getChar14());//创建时间
				preparedStatement.setString(12, userForm.getUserState());//用户状态
				preparedStatement.setLong(13, userForm.getUserId());//用户id
				preparedStatement.executeUpdate();
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

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
}