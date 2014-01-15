package com.njmd.bo.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manager.pub.bean.TreeForm;
import com.manager.pub.util.Constants;
import com.manager.pub.util.DateUtils;
import com.njmd.bo.FrameTreeBO;
import com.njmd.dao.FrameTreeDAO;
import com.njmd.pojo.FrameTree;

public class FrameTreeBOImpl implements FrameTreeBO {
	private FrameTreeDAO frameTreeDAO;

	@SuppressWarnings("unchecked")
	public List<TreeForm> childTreeList(Long treeId, int queryType) {
		List<TreeForm> treeList = new ArrayList<TreeForm>();
		// TODO Auto-generated method stub
		if (queryType == 2) {
			FrameTree frameTree = frameTreeDAO.findById(treeId);
			if(frameTree!=null) {
				TreeForm treeForm = new TreeForm();
				treeList.add(setTreeFormByFrameTree(treeForm, frameTree));
			}
		} else {
			List list = frameTreeDAO.findByParentTreeId(treeId);
			if(list!=null && list.size()>0) {
				for(Object frameTreeObj: list) {
					TreeForm treeForm = new TreeForm();
					treeList.add(setTreeFormByFrameTree(treeForm, (FrameTree)frameTreeObj));
				}
			}
		}
		return treeList;
	}

	@SuppressWarnings("unchecked")
	public List getTreeList() {
		List list = null;
		// TODO Auto-generated method stub
		List frameTreeList = frameTreeDAO.findByParentTreeId(new Long(0));
		if(frameTreeList!=null && frameTreeList.size()>0) {
			list = new ArrayList();
			for(Object frameTreeObj: frameTreeList) {
				List tempList = new ArrayList();
				TreeForm treeForm = new TreeForm();
				tempList.add(setTreeFormByFrameTree(treeForm, (FrameTree)frameTreeObj));
				tempList.add(childTreeList(treeForm.getTreeId(), 1));
				list.add(tempList);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getTreeListByTreeId(Long treeId, HttpServletRequest request) {
		List list = null;
		// TODO Auto-generated method stub
		FrameTree frameTree = frameTreeDAO.findById(treeId);
		if(frameTree!=null) {
			list = new ArrayList();
			if(frameTree.getParentTreeId()==0) {
				request.setAttribute("ifFirst", "0");
				List tempList = new ArrayList();
				TreeForm treeForm = new TreeForm();
				tempList.add(setTreeFormByFrameTree(treeForm, frameTree));
				tempList.add(childTreeList(treeForm.getTreeId(), 1));
				list = new ArrayList();
				list.add(tempList);
			} else {
				request.setAttribute("ifFirst", "1");
				frameTree = frameTreeDAO.findById(frameTree.getParentTreeId());
				List tempList = new ArrayList();
				TreeForm treeForm = new TreeForm();
				tempList.add(setTreeFormByFrameTree(treeForm, frameTree));
				tempList.add(childTreeList(treeId, 2));
				list = new ArrayList();
				list.add(tempList);
			}
		}
		return list;
	}

	public TreeForm parentTreeDetail(TreeForm treeForm) {
		TreeForm tree = null;
		// TODO Auto-generated method stub
		FrameTree frameTree = frameTreeDAO.findById(treeForm.getParentTreeId());
		if(frameTree!=null) {
			tree = setTreeFormByFrameTree(treeForm, frameTree);
		}
		return tree;
	}

	@SuppressWarnings("unchecked")
	public int treeAdd(TreeForm treeForm) {
		int addResult;//0-添加成功；1-添加失败 系统超时~； 2-部门名称重复
		// TODO Auto-generated method stub
		List list = frameTreeDAO.findByTreeName(treeForm.getTreeName());
		if(list!=null && list.size()>0) {
			addResult = 2;
		} else {
			FrameTree frameTree = new FrameTree();
			frameTree.setTreeName(treeForm.getTreeName());
			frameTree.setTreeDesc(treeForm.getTreeDesc());
			frameTree.setParentTreeId(treeForm.getParentTreeId());
			frameTree.setTreeState(treeForm.getTreeState());
			frameTree.setCreateTime(DateUtils.getChar14());
			frameTree.setCreateUser(treeForm.getCreateUser());
			frameTree.setOrderBy(new Long(treeForm.getOrderBy()));
			addResult = frameTreeDAO.save(frameTree);
			if(addResult==Constants.ACTION_SUCCESS){
				list=frameTreeDAO.findByTreeName(treeForm.getTreeName());
				if(list!=null && list.size()>0){
					FrameTree tmp=(FrameTree)list.get(0);
					FrameTree parent=frameTreeDAO.findById(treeForm.getParentTreeId());
					if(null==parent){
						tmp.setPath(",0,"+tmp.getTreeId()+",");
					}else{
						tmp.setPath(parent.getPath()+tmp.getTreeId()+",");
					}
					tmp.setOrderBy(tmp.getTreeId());
					addResult=frameTreeDAO.attachDirty(tmp);
				}
				
			}
			
		}
		return addResult;
	}

	public int treeDelete(TreeForm treeForm) {
		int deleteResult;//0-删除成功；1-删除失败 系统超时~； 2-删除失败 还有其他用户正在使用该部门；
		// TODO Auto-generated method stub
		if(frameTreeDAO.existenceUser(treeForm.getTreeId())) {
			deleteResult = 2;
		} else {
			deleteResult = frameTreeDAO.delete(frameTreeDAO.findById(treeForm.getTreeId()));
		}
		return deleteResult;
	}

	public TreeForm treeDetail(TreeForm treeForm) {
		TreeForm tree = null;
		// TODO Auto-generated method stub
		FrameTree frameTree = frameTreeDAO.findById(treeForm.getTreeId());
		if(frameTree!=null) {
			tree = setTreeFormByFrameTree(treeForm, frameTree);
		}
		return tree;
	}

	public int treeModify(TreeForm treeForm) {
		int modifyResult;//0-修改成功；1-修改失败 系统超时~； 2-部门名称重复
		// TODO Auto-generated method stub
		FrameTree frameTree = new FrameTree();
		frameTree.setTreeId(treeForm.getTreeId());
		frameTree.setTreeName(treeForm.getTreeName());
		if(frameTreeDAO.treeNameRepeat(frameTree)) {
			modifyResult = 2;
		} else {
			frameTree = frameTreeDAO.findById(treeForm.getTreeId());
			frameTree.setTreeName(treeForm.getTreeName());
			frameTree.setTreeDesc(treeForm.getTreeDesc());
			frameTree.setCreateTime(DateUtils.getChar14());
			frameTree.setCreateUser(treeForm.getCreateUser());
//			frameTree.setOrderBy(new Long(treeForm.getOrderBy()));
			modifyResult = frameTreeDAO.attachDirty(frameTree);
		}
		return modifyResult;
	}

	@SuppressWarnings("unchecked")
	public List<TreeForm> treeAllList() {
		List<TreeForm> treeFormList = null;
		List list = frameTreeDAO.findAll();
		if(list!=null && list.size()>0) {
			treeFormList = new ArrayList<TreeForm>();
			for(Object frameTreeObj: list) {
				treeFormList.add(setTreeFormByFrameTree(new TreeForm(), (FrameTree)frameTreeObj));
			}
		}
		return treeFormList;
	}

	private TreeForm setTreeFormByFrameTree(TreeForm treeForm, FrameTree frameTree) {
		treeForm.setTreeId(frameTree.getTreeId());
		treeForm.setTreeName(frameTree.getTreeName());
		treeForm.setTreeDesc(frameTree.getTreeDesc());
		treeForm.setTreeState(frameTree.getTreeState());
		treeForm.setCreateUser(frameTree.getCreateUser()==null?new Long(0):frameTree.getCreateUser());
		treeForm.setCreateTime(frameTree.getCreateTime());
		treeForm.setParentTreeId(frameTree.getParentTreeId());
		treeForm.setOrderBy(frameTree.getOrderBy()+"");
		treeForm.setPath(frameTree.getPath()+"");
		return treeForm;
	}

	public void setFrameTreeDAO(FrameTreeDAO frameTreeDAO) {
		this.frameTreeDAO = frameTreeDAO;
	}
	
	public List<TreeForm> childTreeList(java.lang.Long treeId){
		List<TreeForm> treeList = new ArrayList<TreeForm>();
		
		FrameTree frameTree = frameTreeDAO.findById(treeId);
		if(frameTree!=null) {
			TreeForm treeForm = new TreeForm();
			treeList.add(setTreeFormByFrameTree(treeForm, frameTree));
		}
		
		List list = frameTreeDAO.findByParentTreeId(treeId);
		if(list!=null && list.size()>0) {
			for(Object frameTreeObj: list) {
				TreeForm treeForm = new TreeForm();
				treeList.add(setTreeFormByFrameTree(treeForm, (FrameTree)frameTreeObj));
				
				List tmpList=frameTreeDAO.findByParentTreeId(treeForm.getTreeId());
				
				if(tmpList!=null && tmpList.size()>0) {
					for(Object frameTreeObj1: tmpList) {
						TreeForm treeForm1 = new TreeForm();
						treeList.add(setTreeFormByFrameTree(treeForm1, (FrameTree)frameTreeObj1));
					}
				}
				
			}
		}
		
		return treeList;
	}
}
