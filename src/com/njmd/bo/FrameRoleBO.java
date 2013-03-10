package com.njmd.bo;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.RoleForm;


public interface FrameRoleBO {
	/**
	 * 分页查询所有角色列表
	 * 
	 * @param page
	 * 			pagecute 当前第几页
	 * 			pageline 每页多少天数据
	 * @return Page
	 */
	public Page getRoleList(Page page);

	/**
	 * 查询所有角色列表
	 * 
	 * @return Page
	 */
	public Page getRoleListAll();

	/**
	 * 角色详情
	 * 
	 * @param roleForm
	 * 			roleId 角色id
	 * @return RoleForm
	 */
	public RoleForm roleDetail(RoleForm roleForm);

	/**
	 * 角色添加
	 * 
	 * @param roleForm
	 * 			roleName	角色名称
	 * 			roleDesc	角色描述
	 * 			roleState	角色状态
	 * 			createUser	创建人id
	 * 			createTime	创建时间
	 * 			treeId		所属部门（好像是创建人的部门id）
	 * 			urlIdList	权限对应的功能url项
	 * @return 0-添加成功；1-添加失败 系统超时~；2-该角色出现重名 请换个角色名称
	 */
	public int roleAdd(RoleForm roleForm);

	/**
	 * 角色修改
	 * 
	 * @param roleForm
	 * 			roleId		角色id
	 * 			roleName	角色名称
	 * 			roleDesc	角色描述
	 * 			roleState	角色状态
	 * 			createUser	创建人id
	 * 			createTime	创建时间
	 * 			treeId		所属部门（好像是创建人的部门id）
	 * 			urlIdList	权限对应的功能url项
	 * @return 0-修改成功；1-修改失败 系统超时~；2-该角色出现重名 请换个角色名称
	 */
	public int roleModify(RoleForm roleForm);

	/**
	 * 角色删除
	 * 
	 * @param roleForm
	 * 			roleId 角色id
	 * @return 0-删除成功；1-删除失败 系统超时~； 2-删除失败 还有其他用户正在使用该角色；
	 */
	public int roleDelete(RoleForm roleForm);
}
