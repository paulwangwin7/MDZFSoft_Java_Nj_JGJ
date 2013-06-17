package com.njmd.bo;

import java.util.List;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.UserForm;
import com.njmd.pojo.FrameUser;


public interface FrameUserBO {
	/**
	 * 根据身份证号查询用户列表
	 * @param idCard
	 * @return
	 */
	public List<FrameUser> getUserListByIdCard(String idCard);
	/**
	 * 查询用户列表
	 * 
	 * @param userForm 
	 * 			userName 用户名 	 模糊查询
	 * 			userCode 警员编号	 精准查询
	 * 			sex 	 性别	 精准查询
	 * 			treeId	 所属部门	 精准查询（并查询下属部门）
	 * @param queryTreeId 需要查询的部门id
	 * @param page
	 *			pagecute 当前第几页
	 *			pageline 每页多少条数据
	 * @return Page
	 */
	public Page getUserList(UserForm userForm, String queryTreeId, Page page);
	/**
	 * 查询用户列表
	 * 
	 * @param userForm 
	 * 			userName 用户名 	 模糊查询
	 * 			userCode 警员编号	 精准查询
	 * 			sex 	 性别	 精准查询
	 * 			treeId	 所属部门	 精准查询（并查询下属部门）
	 * @param queryTreeId 需要查询的部门id
	 * @param page
	 *			pagecute 当前第几页
	 *			pageline 每页多少条数据
	 * @return Page
	 */
	public Page getUserListByAdmin(UserForm userForm, String queryTreeId, Page page);
	/**
	 * 查询用户列表
	 * 
	 * @param userForm 
	 * 			userName 用户名 	 模糊查询
	 * 			userCode 警员编号	 精准查询
	 * 			sex 	 性别	 精准查询
	 * 			treeId	 所属部门	 精准查询（并查询下属部门）
	 * @param queryTreeId 需要查询的部门id
	 * @param page
	 *			pagecute 当前第几页
	 *			pageline 每页多少条数据
	 * @return Page
	 */
	public Page getUserList(Page page);
	/**
	 * 查询用户列表
	 * 
	 * @param treeId	 所属部门	 精准查询（并查询下属部门）
	 * @param page
	 *			pagecute 当前第几页
	 *			pageline 每页多少条数据
	 * @return Page
	 */
	public Page getUserListByTree(Long treeId, Page page);

	/**
	 * 用户登录
	 * 
	 * @param userForm
	 * 			loginName 登录帐号
	 * 			loginPswd 登录密码
	 * @return
	 */
	public UserForm userLogin(UserForm userForm);

	/**
	 * 注册用户
	 * 
	 * @param userForm
	 * @return 0-成功； 1-失败 系统异常； 2-登录账号重名
	 */
	public int userRegister(UserForm userForm);

	/**
	 * 用户信息修改
	 * 
	 * @param userForm
	 * @return 0-成功； 1-失败 系统异常； 2-登录账号重名
	 */
	public int userModify(UserForm userForm);

	/**
	 * 用户密码修改
	 * 
	 * @param userForm
	 * 			userId 		用户id
	 * 			loginPswd 	新密码
	 * @param oldPswd
	 * @return //0-成功； 1-失败 系统异常； 2-旧密码不正确
	 */
	public int pswdModify(UserForm userForm, String oldPswd);

	/**
	 * 用户详情
	 * 
	 * @param userForm
	 * 			userId 		用户id
	 * @return
	 */
	public UserForm userDetail(UserForm userForm);

	/**
	 * 用户详情
	 * 
	 * @param userForm
	 * 			userId 		用户id
	 * @return
	 */
	public UserForm userById(UserForm userForm);
}
