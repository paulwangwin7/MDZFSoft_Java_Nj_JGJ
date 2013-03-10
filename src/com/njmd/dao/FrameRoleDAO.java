package com.njmd.dao;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.RoleForm;
import com.manager.pub.util.Constants;
import com.njmd.dao.BaseHibernateDAO;
import com.njmd.pojo.FrameRole;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * FrameRole entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameRole
 * @author MyEclipse Persistence Tools
 */

public class FrameRoleDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameRoleDAO.class);
	// property constants
	public static final String ROLE_NAME = "roleName";
	public static final String ROLE_DESC = "roleDesc";
	public static final String ROLE_STATE = "roleState";
	public static final String CREATE_USER = "createUser";
	public static final String CREATE_TIME = "createTime";
	public static final String TREE_ID = "treeId";
	public static final String URL_ID_LIST = "urlIdList";

	// property constants

	@SuppressWarnings("finally")
	public int save(FrameRole transientInstance) {
		int saveResult = Constants.ACTION_FAILED;
		log.debug("saving FrameRole instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			session.save(transientInstance);
			session.getTransaction().commit();
			log.debug("save successful");
			saveResult = Constants.ACTION_SUCCESS;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			session.getTransaction().rollback();
//			throw re;
		} finally {
			session.close();
			return saveResult;
		}
	}

	@SuppressWarnings("finally")
	public int delete(FrameRole persistentInstance) {
		int deleteResult = Constants.ACTION_FAILED;
		log.debug("deleting FrameRole instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			session.delete(persistentInstance);
			session.getTransaction().commit();
			log.debug("delete successful");
			deleteResult = Constants.ACTION_SUCCESS;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			session.getTransaction().rollback();
//			throw re;
		} finally {
			session.close();
			return deleteResult;
		}
	}

	@SuppressWarnings("finally")
	public FrameRole findById(java.lang.Long id) {
		FrameRole instance = null;
		log.debug("getting FrameRole instance with id: " + id);
		Session session = getSession();
		try {
			session.clear();
			instance = (FrameRole) session.get("com.njmd.pojo.FrameRole", id);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return instance;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public boolean existenceUser(java.lang.Long id) {
		boolean existence = true;//判断该角色是否存在用户
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from com.njmd.pojo.FrameUser as model where model.roleId = ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, id);
			List list = queryObject.list();
			if(list!=null && list.size()>0) {
				existence = true;
			} else {
				existence = false;
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return existence;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findByExample(FrameRole instance) {
		List results = null;
		log.debug("finding FrameRole instance by example");
		Session session = getSession();
		try {
			session.clear();
			results = session.createCriteria("com.njmd.pojo.FrameRole").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
//			throw re;
		} finally {
			session.close();
			return results;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findByProperty(String propertyName, Object value) {
		List results = null;
		log.debug("finding FrameRole instance with property: " + propertyName + ", value: " + value);
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameRole as model where model." + propertyName + "= ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			results = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return results;
		}
	}

	@SuppressWarnings("unchecked")
	public List findByRoleName(Object roleName) {
		return findByProperty(ROLE_NAME, roleName);
	}

	@SuppressWarnings("unchecked")
	public List findByRoleDesc(Object roleDesc) {
		return findByProperty(ROLE_DESC, roleDesc);
	}

	@SuppressWarnings("unchecked")
	public List findByRoleState(Object roleState) {
		return findByProperty(ROLE_STATE, roleState);
	}

	@SuppressWarnings("unchecked")
	public List findByCreateUser(Object createUser) {
		return findByProperty(CREATE_USER, createUser);
	}

	@SuppressWarnings("unchecked")
	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	@SuppressWarnings("unchecked")
	public List findByTreeId(Object treeId) {
		return findByProperty(TREE_ID, treeId);
	}

	@SuppressWarnings("unchecked")
	public List findByUrlIdList(Object urlIdList) {
		return findByProperty(URL_ID_LIST, urlIdList);
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findAll() {
		List results = null;
		log.debug("finding all FrameRole instances");
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameRole";
			Query queryObject = session.createQuery(queryString);
			results = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
//			throw re;
		} finally {
			session.close();
			return results;
		}
	}

	@SuppressWarnings("finally")
	public FrameRole merge(FrameRole detachedInstance) {
		FrameRole result = null;
		log.debug("merging FrameRole instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			result = (FrameRole) session.merge(detachedInstance);
			session.getTransaction().commit();
			log.debug("merge successful");
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			session.getTransaction().rollback();
//			throw re;
		} finally {
			session.close();
			return result;
		}
	}

	@SuppressWarnings("finally")
	public int attachDirty(FrameRole instance) {
		int attachResult = Constants.ACTION_FAILED;
		log.debug("attaching dirty FrameRole instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			session.saveOrUpdate(instance);
			session.getTransaction().commit();
			log.debug("attach successful");
			attachResult = Constants.ACTION_SUCCESS;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			session.getTransaction().rollback();
//			throw re;
		} finally {
			session.close();
			return attachResult;
		}
	}

	public void attachClean(FrameRole instance) {
		log.debug("attaching clean FrameRole instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			session.lock(instance, LockMode.NONE);
			session.getTransaction().commit();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			session.getTransaction().rollback();
//			throw re;
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public boolean roleNameRepeat(FrameRole instance) {
		boolean repeat = false;//角色名称是否重复
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameRole as model where model.roleName = ? and model.roleId != ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, instance.getRoleName());
			queryObject.setParameter(1, instance.getRoleId());
			List list = queryObject.list();
			if(list!=null && list.size()>0) {
				repeat = true;
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return repeat;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Page getRoleList(Page page) {
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameRole as model order by model.createTime desc";
			Query queryObject = session.createQuery(queryString);
			if(page!=null) {
				page.setTotal(queryObject.list().size());
				queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
				queryObject.setMaxResults(page.getDbLine());
			} else {
				page = new Page();
			}
			List<RoleForm> roleList = new ArrayList<RoleForm>();
			List querylist = queryObject.list();
			for(int i=0; i<querylist.size(); i++) {
				roleList.add(setRoleFormByFrameRole(new RoleForm(), (FrameRole) querylist.get(i)));
			}
			page.setListObject(roleList);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}

	private RoleForm setRoleFormByFrameRole(RoleForm roleForm, FrameRole frameRole) {
		roleForm.setRoleId(frameRole.getRoleId());
		roleForm.setRoleName(frameRole.getRoleName());
		roleForm.setRoleDesc(frameRole.getRoleDesc());
		roleForm.setRoleState(frameRole.getRoleState());
		roleForm.setCreateUser(frameRole.getCreateUser()+"");
		roleForm.setCreateTime(frameRole.getCreateTime());
		roleForm.setTreeId(frameRole.getTreeId()+"");
		roleForm.setUrlIdList(frameRole.getUrlIdList());
		return roleForm;
	}

	public static void main(String[] args) {
		System.out.println(new FrameRoleDAO().existenceUser(new Long(1)));
	}
}