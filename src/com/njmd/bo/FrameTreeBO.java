package com.njmd.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manager.pub.bean.TreeForm;


public interface FrameTreeBO {
	/**
	 * 部门添加
	 * 
	 * @param treeForm
	 * 			treeName 		部门名称
	 * 			treeDesc 		部门描述
	 * 			treeState 		部门状态
	 * 			createUser		创建人id
	 * 			createTime		创建时间
	 * 			parentTreeId	父级treeId
	 * 			orderBy			排序
	 * @return 0-添加成功；1-添加失败 系统超时~； 2-部门名称重复
	 */
	public int treeAdd(TreeForm treeForm);

	/**
	 * 部门修改
	 * 
	 * @param treeForm
	 * 			treeId			部门id
	 * 			treeName 		部门名称
	 * 			treeDesc 		部门描述
	 * 			treeState 		部门状态
	 * 			createUser		创建人id
	 * 			createTime		创建时间
	 * 			parentTreeId	父级treeId
	 * 			orderBy			排序
	 * @return 0-修改成功；1-修改失败 系统超时~； 2-部门名称重复
	 */
	public int treeModify(TreeForm treeForm);

	/**
	 * 部门删除
	 * 
	 * @param treeForm
	 * 			treeId 部门id
	 * @return 0-删除成功；1-删除失败 系统超时~； 2-删除失败 还有其他用户正在使用该部门；
	 */
	public int treeDelete(TreeForm treeForm);

	/**
	 * 部门详情
	 * 
	 * @param treeForm
	 * 			treeId 部门id
	 * @return TreeForm
	 */
	public TreeForm treeDetail(TreeForm treeForm);

	/**
	 * 获取上级部门
	 * 
	 * @param treeForm
	 * 			treeId 部门id
	 * @return TreeForm
	 */
	public TreeForm parentTreeDetail(TreeForm treeForm);

	/**
	 * 获取子部门列表
	 * @param treeId	部门id
	 * @param queryType	查询类型	1-treeId为一级部门id；2-treeId为二级部门id
	 * @return
	 */
	public List<TreeForm> childTreeList(java.lang.Long treeId, int queryType);

	/**
	 * 查询部门id所在的一级部门以及以下对应的所有部门
	 * 
	 * @param treeId
	 * @param request
	 * @return List [ [treeForm,[treeForm,treeForm...]], [treeForm,[treeForm,treeForm...]] ... ]
	 */
	@SuppressWarnings("unchecked")
	public List getTreeListByTreeId(java.lang.Long treeId, HttpServletRequest request);

	/**
	 * 查询所有一级部门以及以下对应的所有二级部门
	 * 
	 * @return List [ [treeForm,[treeForm,treeForm...]], [treeForm,[treeForm,treeForm...]] ... ]
	 */
	@SuppressWarnings("unchecked")
	public List getTreeList();

	/**
	 * 获取所有部门
	 * 
	 * @return
	 */
	public List<TreeForm> treeAllList();
}
