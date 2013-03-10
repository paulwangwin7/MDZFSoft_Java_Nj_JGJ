package com.njmd.dao;

import com.manager.pub.util.Constants;
import com.njmd.dao.BaseHibernateDAO;
import com.njmd.pojo.FrameLog;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * FrameLog entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameLog
 * @author MyEclipse Persistence Tools
 */

public class FrameLogDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameLogDAO.class);
	// property constants
	public static final String CREATE_TIME = "createTime";
	public static final String USER_ID = "userId";
	public static final String USER_CODE = "userCode";
	public static final String TREE_ID = "treeId";
	public static final String TREE_NAME = "treeName";
	public static final String ROLE_ID = "roleId";
	public static final String ROLE_NAME = "roleName";
	public static final String LOG_DESC = "logDesc";
	public static final String IP_ADD = "ipAdd";

	@SuppressWarnings("finally")
	public int save(FrameLog transientInstance) {
		int saveResult = Constants.ACTION_FAILED;
		log.debug("saving FrameLog instance");
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

	public void delete(FrameLog persistentInstance) {
		log.debug("deleting FrameLog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FrameLog findById(java.lang.Long id) {
		log.debug("getting FrameLog instance with id: " + id);
		try {
			FrameLog instance = (FrameLog) getSession().get(
					"com.njmd.pojo.FrameLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FrameLog instance) {
		log.debug("finding FrameLog instance by example");
		try {
			List results = getSession()
					.createCriteria("com.njmd.pojo.FrameLog").add(
							Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FrameLog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FrameLog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByUserCode(Object userCode) {
		return findByProperty(USER_CODE, userCode);
	}

	public List findByTreeId(Object treeId) {
		return findByProperty(TREE_ID, treeId);
	}

	public List findByTreeName(Object treeName) {
		return findByProperty(TREE_NAME, treeName);
	}

	public List findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
	}

	public List findByRoleName(Object roleName) {
		return findByProperty(ROLE_NAME, roleName);
	}

	public List findByLogDesc(Object logDesc) {
		return findByProperty(LOG_DESC, logDesc);
	}

	public List findByIpAdd(Object ipAdd) {
		return findByProperty(IP_ADD, ipAdd);
	}

	public List findAll() {
		log.debug("finding all FrameLog instances");
		try {
			String queryString = "from FrameLog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FrameLog merge(FrameLog detachedInstance) {
		log.debug("merging FrameLog instance");
		try {
			FrameLog result = (FrameLog) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FrameLog instance) {
		log.debug("attaching dirty FrameLog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FrameLog instance) {
		log.debug("attaching clean FrameLog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}