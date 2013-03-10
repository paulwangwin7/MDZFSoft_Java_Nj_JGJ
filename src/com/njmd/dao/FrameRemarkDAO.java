package com.njmd.dao;

import com.njmd.dao.BaseHibernateDAO;
import com.njmd.pojo.FrameRemark;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * FrameRemark entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameRemark
 * @author MyEclipse Persistence Tools
 */

public class FrameRemarkDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameRemarkDAO.class);

	// property constants

	public void save(FrameRemark transientInstance) {
		log.debug("saving FrameRemark instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FrameRemark persistentInstance) {
		log.debug("deleting FrameRemark instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FrameRemark findById(com.njmd.pojo.FrameRemarkId id) {
		log.debug("getting FrameRemark instance with id: " + id);
		try {
			FrameRemark instance = (FrameRemark) getSession().get(
					"com.njmd.pojo.FrameRemark", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FrameRemark instance) {
		log.debug("finding FrameRemark instance by example");
		try {
			List results = getSession().createCriteria(
					"com.njmd.pojo.FrameRemark").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding FrameRemark instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FrameRemark as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all FrameRemark instances");
		try {
			String queryString = "from FrameRemark";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FrameRemark merge(FrameRemark detachedInstance) {
		log.debug("merging FrameRemark instance");
		try {
			FrameRemark result = (FrameRemark) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FrameRemark instance) {
		log.debug("attaching dirty FrameRemark instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FrameRemark instance) {
		log.debug("attaching clean FrameRemark instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}