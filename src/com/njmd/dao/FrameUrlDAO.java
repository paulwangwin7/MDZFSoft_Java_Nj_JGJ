package com.njmd.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.njmd.pojo.FrameUrl;

/**
 * A data access object (DAO) providing persistence and search support for
 * FrameUrl entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameUrl
 * @author MyEclipse Persistence Tools
 */

public class FrameUrlDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameUrlDAO.class);
	// property constants
	public static final String URL_VALUE = "urlValue";
	public static final String URL_NAME = "urlName";
	public static final String URL_DESC = "urlDesc";
	public static final String URL_STATE = "urlState";
	public static final String PARENT_MENU_ID = "parentMenuId";
	public static final String URL_SORT = "urlSort";
	public static final String URL_TAB = "urlTab";

	public void save(FrameUrl transientInstance) {
		log.debug("saving FrameUrl instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FrameUrl persistentInstance) {
		log.debug("deleting FrameUrl instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FrameUrl findById(java.lang.Long id) {
		log.debug("getting FrameUrl instance with id: " + id);
		try {
			FrameUrl instance = (FrameUrl) getSession().get(
					"com.njmd.pojo.FrameUrl", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FrameUrl instance) {
		log.debug("finding FrameUrl instance by example");
		try {
			List results = getSession()
					.createCriteria("com.njmd.pojo.FrameUrl").add(
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
		log.debug("finding FrameUrl instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FrameUrl as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUrlValue(Object urlValue) {
		return findByProperty(URL_VALUE, urlValue);
	}

	public List findByUrlName(Object urlName) {
		return findByProperty(URL_NAME, urlName);
	}

	public List findByUrlDesc(Object urlDesc) {
		return findByProperty(URL_DESC, urlDesc);
	}

	public List findByUrlState(Object urlState) {
		return findByProperty(URL_STATE, urlState);
	}

	public List findByParentMenuId(Object parentMenuId) {
		return findByProperty(PARENT_MENU_ID, parentMenuId);
	}

	public List findByUrlSort(Object urlSort) {
		return findByProperty(URL_SORT, urlSort);
	}

	public List findByUrlTab(Object urlTab) {
		return findByProperty(URL_TAB, urlTab);
	}

	public List findAll() {
		log.debug("finding all FrameUrl instances");
		try {
			String queryString = "from FrameUrl";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FrameUrl merge(FrameUrl detachedInstance) {
		log.debug("merging FrameUrl instance");
		try {
			FrameUrl result = (FrameUrl) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FrameUrl instance) {
		log.debug("attaching dirty FrameUrl instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FrameUrl instance) {
		log.debug("attaching clean FrameUrl instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}