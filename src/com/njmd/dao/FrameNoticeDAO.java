package com.njmd.dao;

import com.manager.pub.util.Constants;
import com.njmd.dao.BaseHibernateDAO;
import com.njmd.pojo.FrameNotice;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * FrameNotice entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameNotice
 * @author MyEclipse Persistence Tools
 */

public class FrameNoticeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameNoticeDAO.class);
	// property constants
	public static final String NOTICE_TITLE = "noticeTitle";
	public static final String NOTICE_CONTENT = "noticeContent";
	public static final String NOTICE_TYPE = "noticeType";
	public static final String CREATE_TIME = "createTime";
	public static final String NOTICE_BEGIN = "noticeBegin";
	public static final String NOTICE_END = "noticeEnd";
	public static final String USER_ID = "userId";
	public static final String TREE_ID_LIST = "treeIdList";

	@SuppressWarnings("finally")
	public int save(FrameNotice transientInstance) {
		int saveResult = Constants.ACTION_FAILED;
		log.debug("saving FrameNotice instance");
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
	public int delete(FrameNotice persistentInstance) {
		int deleteResult = Constants.ACTION_FAILED;
		log.debug("deleting FrameNotice instance");
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
	public FrameNotice findById(java.lang.Long id) {
		FrameNotice instance = null;
		log.debug("getting FrameNotice instance with id: " + id);
		Session session = getSession();
		try {
			session.clear();
			instance = (FrameNotice) session.get("com.njmd.pojo.FrameNotice", id);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return instance;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findByExample(FrameNotice instance) {
		List results = null;
		log.debug("finding FrameNotice instance by example");
		Session session = getSession();
		try {
			session.clear();
			results = session.createCriteria("com.njmd.pojo.FrameNotice").add(Example.create(instance)).list();
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
		log.debug("finding FrameNotice instance with property: " + propertyName + ", value: " + value);
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameNotice as model where model." + propertyName + "= ?";
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
	public List findByNoticeTitle(Object noticeTitle) {
		return findByProperty(NOTICE_TITLE, noticeTitle);
	}

	@SuppressWarnings("unchecked")
	public List findByNoticeContent(Object noticeContent) {
		return findByProperty(NOTICE_CONTENT, noticeContent);
	}

	@SuppressWarnings("unchecked")
	public List findByNoticeType(Object noticeType) {
		return findByProperty(NOTICE_TYPE, noticeType);
	}

	@SuppressWarnings("unchecked")
	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	@SuppressWarnings("unchecked")
	public List findByNoticeBegin(Object noticeBegin) {
		return findByProperty(NOTICE_BEGIN, noticeBegin);
	}

	@SuppressWarnings("unchecked")
	public List findByNoticeEnd(Object noticeEnd) {
		return findByProperty(NOTICE_END, noticeEnd);
	}

	@SuppressWarnings("unchecked")
	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	@SuppressWarnings("unchecked")
	public List findByTreeIdList(Object treeIdList) {
		return findByProperty(TREE_ID_LIST, treeIdList);
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findAll() {
		List results = null;
		log.debug("finding all FrameNotice instances");
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameNotice";
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
	public FrameNotice merge(FrameNotice detachedInstance) {
		FrameNotice result = null;
		log.debug("merging FrameNotice instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			result = (FrameNotice) session.merge(detachedInstance);
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
	public int attachDirty(FrameNotice instance) {
		int attachResult = Constants.ACTION_FAILED;
		log.debug("attaching dirty FrameNotice instance");
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

	public void attachClean(FrameNotice instance) {
		log.debug("attaching clean FrameNotice instance");
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
}