package com.njmd.dao;

import com.manager.pub.util.Constants;
import com.njmd.dao.BaseHibernateDAO;
import com.njmd.pojo.FrameTree;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * FrameTree entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameTree
 * @author MyEclipse Persistence Tools
 */

public class FrameTreeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameTreeDAO.class);
	// property constants
	public static final String TREE_NAME = "treeName";
	public static final String TREE_DESC = "treeDesc";
	public static final String TREE_STATE = "treeState";
	public static final String CREATE_USER = "createUser";
	public static final String CREATE_TIME = "createTime";
	public static final String PARENT_TREE_ID = "parentTreeId";
	public static final String ORDER_BY = "orderBy";

	@SuppressWarnings("finally")
	public int save(FrameTree transientInstance) {
		int saveResult = Constants.ACTION_FAILED;
		log.debug("saving FrameTree instance");
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
	public int delete(FrameTree persistentInstance) {
		int deleteResult = Constants.ACTION_FAILED;
		log.debug("deleting FrameTree instance");
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
	public FrameTree findById(java.lang.Long id) {
		FrameTree instance = null;
		log.debug("getting FrameTree instance with id: " + id);
		Session session = getSession();
		try {
			session.clear();
			instance = (FrameTree) session.get("com.njmd.pojo.FrameTree", id);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return instance;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findByExample(FrameTree instance) {
		List results = null;
		log.debug("finding FrameTree instance by example");
		Session session = getSession();
		try {
			session.clear();
			results = session.createCriteria("com.njmd.pojo.FrameTree").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
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
		log.debug("finding FrameTree instance with property: " + propertyName + ", value: " + value);
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameTree as model where model." + propertyName + "= ? order by model.orderBy";
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
	public List findByTreeName(Object treeName) {
		return findByProperty(TREE_NAME, treeName);
	}

	@SuppressWarnings("unchecked")
	public List findByTreeDesc(Object treeDesc) {
		return findByProperty(TREE_DESC, treeDesc);
	}

	@SuppressWarnings("unchecked")
	public List findByTreeState(Object treeState) {
		return findByProperty(TREE_STATE, treeState);
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
	public List findByParentTreeId(Object parentTreeId) {
		return findByProperty(PARENT_TREE_ID, parentTreeId);
	}

	@SuppressWarnings("unchecked")
	public List findByOrderBy(Object orderBy) {
		return findByProperty(ORDER_BY, orderBy);
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List findAll() {
		List results = null;
		log.debug("finding all FrameTree instances");
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameTree";
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
	public FrameTree merge(FrameTree detachedInstance) {
		FrameTree result = null;
		log.debug("merging FrameTree instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			result = (FrameTree) session.merge(detachedInstance);
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
	public int attachDirty(FrameTree instance) {
		int attachResult = Constants.ACTION_FAILED;
		log.debug("attaching dirty FrameTree instance");
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

	public void attachClean(FrameTree instance) {
		log.debug("attaching clean FrameTree instance");
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
	public boolean treeNameRepeat(FrameTree instance) {
		boolean repeat = false;//部门名称是否重复
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameTree as model where model.treeName = ? and model.treeId != ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, instance.getTreeName());
			queryObject.setParameter(1, instance.getTreeId());
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
	public boolean existenceUser(java.lang.Long id) {
		boolean existence = true;//判断该角色是否存在用户
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from com.njmd.pojo.FrameUser as model where model.treeId = ?";
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
}