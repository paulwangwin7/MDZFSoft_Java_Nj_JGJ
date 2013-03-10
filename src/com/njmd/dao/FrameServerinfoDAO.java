package com.njmd.dao;

import com.manager.pub.bean.ServerInfoForm;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.util.Constants;
import com.njmd.dao.BaseHibernateDAO;
import com.njmd.pojo.FrameServerinfo;

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
 * FrameServerinfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameServerinfo
 * @author MyEclipse Persistence Tools
 */

public class FrameServerinfoDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameServerinfoDAO.class);
	// property constants
	public static final String RATIO_CPU = "ratioCpu";
	public static final String RATIO_MEMORY = "ratioMemory";
	public static final String USE_MEMORY = "useMemory";
	public static final String RATIO_HARDDISK = "ratioHarddisk";
	public static final String USE_HARDDISK = "useHarddisk";
	public static final String LETTER = "letter";
	public static final String CREATE_TIME = "createTime";

	@SuppressWarnings("finally")
	public int save(FrameServerinfo transientInstance) {
		int saveResult = Constants.ACTION_FAILED;
		log.debug("saving FrameServerinfo instance");
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
	public int delete(FrameServerinfo persistentInstance) {
		int deleteResult = Constants.ACTION_FAILED;
		log.debug("deleting FrameServerinfo instance");
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
	public FrameServerinfo findById(java.lang.Long id) {
		FrameServerinfo instance = null;
		log.debug("getting FrameServerinfo instance with id: " + id);
		Session session = getSession();
		try {
			session.clear();
			instance = (FrameServerinfo) session.get("com.njmd.pojo.FrameServerinfo", id);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return instance;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findByExample(FrameServerinfo instance) {
		List results = null;
		log.debug("finding FrameServerinfo instance by example");
		Session session = getSession();
		try {
			session.clear();
			results = session.createCriteria("com.njmd.pojo.FrameServerinfo").add(Example.create(instance)).list();
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
		log.debug("finding FrameServerinfo instance with property: " + propertyName + ", value: " + value);
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameServerinfo as model where model." + propertyName + "= ?";
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
	public List findByRatioCpu(Object ratioCpu) {
		return findByProperty(RATIO_CPU, ratioCpu);
	}

	@SuppressWarnings("unchecked")
	public List findByRatioMemory(Object ratioMemory) {
		return findByProperty(RATIO_MEMORY, ratioMemory);
	}

	@SuppressWarnings("unchecked")
	public List findByUseMemory(Object useMemory) {
		return findByProperty(USE_MEMORY, useMemory);
	}

	@SuppressWarnings("unchecked")
	public List findByRatioHarddisk(Object ratioHarddisk) {
		return findByProperty(RATIO_HARDDISK, ratioHarddisk);
	}

	@SuppressWarnings("unchecked")
	public List findByUseHarddisk(Object useHarddisk) {
		return findByProperty(USE_HARDDISK, useHarddisk);
	}

	@SuppressWarnings("unchecked")
	public List findByLetter(Object letter) {
		return findByProperty(LETTER, letter);
	}

	@SuppressWarnings("unchecked")
	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findAll() {
		List results = null;
		log.debug("finding all FrameServerinfo instances");
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameServerinfo";
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
	public FrameServerinfo merge(FrameServerinfo detachedInstance) {
		FrameServerinfo result = null;
		log.debug("merging FrameServerinfo instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			result = (FrameServerinfo) session.merge(detachedInstance);
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
	public int attachDirty(FrameServerinfo instance) {
		int attachResult = Constants.ACTION_FAILED;
		log.debug("attaching dirty FrameServerinfo instance");
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

	public void attachClean(FrameServerinfo instance) {
		log.debug("attaching clean FrameServerinfo instance");
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
	public List<ServerInfoForm> serverInfoListByDay(String statTime) {
		List<ServerInfoForm> serverInfoList = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameServerinfo as model where to_number(substr(model.createTime,0,8)) = ? and model.saveIp = ? order by model.createTime asc";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, statTime);
			queryObject.setParameter(1, SystemConfig.getSystemConfig().getFtpHost());
			List results = queryObject.list();
			if(results!=null && results.size()>0) {
				serverInfoList = new ArrayList<ServerInfoForm>();
				for(Object frameServerinfoObj: results) {
					serverInfoList.add(setServerInfoFormByFrameServerinfo(new ServerInfoForm(), (FrameServerinfo)frameServerinfoObj));
				}
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
		} finally {
			session.close();
			return serverInfoList;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<ServerInfoForm> serverInfoListByWeek(String beginTime, String endTime) {
		List<ServerInfoForm> serverInfoList = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameServerinfo as model where to_number(substr(model.createTime,0,8)) >= ? and to_number(substr(model.createTime,0,8)) <= ? and model.saveIp = ? order by model.createTime asc";
			serverInfoList = new ArrayList<ServerInfoForm>();
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, beginTime);
			queryObject.setParameter(1, endTime);
			queryObject.setParameter(2, SystemConfig.getSystemConfig().getFtpHost());
			List results = queryObject.list();
			if(results!=null && results.size()>0) {
				for(Object frameServerinfoObj: results) {
					serverInfoList.add(setServerInfoFormByFrameServerinfo(new ServerInfoForm(), (FrameServerinfo)frameServerinfoObj));
				}
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
		} finally {
			session.close();
			return serverInfoList;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<ServerInfoForm> serverInfoListByMonth(String month) {
		List<ServerInfoForm> serverInfoList = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "select round(avg(model.ratioCpu),0),round(avg(model.ratioMemory),0),round(avg(model.ratioHarddisk),0)" +
					" from FrameServerinfo as model where to_number(substr(model.createTime,0,8)) = ? and model.saveIp = ? order by model.createTime asc";
			serverInfoList = new ArrayList<ServerInfoForm>();
			for(int i=1; i<=31; i++) {
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter(0, month+(i<10?("0"+i):i));
				queryObject.setParameter(1, SystemConfig.getSystemConfig().getFtpHost());
				List results = queryObject.list();
				if(results!=null && results.size()>0) {
					Object[] obj = (Object[]) results.get(0);
					ServerInfoForm serverInfoFrom = new ServerInfoForm();
					serverInfoFrom.setRatioCPU(obj[0]==null?0:Integer.parseInt(obj[0].toString().substring(0, obj[0].toString().indexOf("."))));
					serverInfoFrom.setRatioMEMORY(obj[1]==null?0:Integer.parseInt(obj[1].toString().substring(0, obj[1].toString().indexOf("."))));
					serverInfoFrom.setRatioHARDDISK(obj[2]==null?0:Integer.parseInt(obj[2].toString().substring(0, obj[2].toString().indexOf("."))));
					serverInfoList.add(serverInfoFrom);
				} else {
					ServerInfoForm serverInfoFrom = new ServerInfoForm();
					serverInfoFrom.setRatioCPU(0);
					serverInfoFrom.setRatioMEMORY(0);
					serverInfoFrom.setRatioHARDDISK(0);
					serverInfoList.add(serverInfoFrom);
				}
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
		} finally {
			session.close();
			return serverInfoList;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<ServerInfoForm> serverInfoListByYear(String year) {
		List<ServerInfoForm> serverInfoList = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "select round(avg(model.ratioCpu),0),round(avg(model.ratioMemory),0),round(avg(model.ratioHarddisk),0)" +
					" from FrameServerinfo as model where to_number(substr(model.createTime,0,6)) = ? and model.saveIp = ? order by model.createTime asc";
			serverInfoList = new ArrayList<ServerInfoForm>();
			for(int i=1; i<=12; i++) {
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter(0, year+(i<10?("0"+i):i));
				queryObject.setParameter(1, SystemConfig.getSystemConfig().getFtpHost());
				List results = queryObject.list();
				if(results!=null && results.size()>0) {
					Object[] obj = (Object[]) results.get(0);
					ServerInfoForm serverInfoFrom = new ServerInfoForm();
					serverInfoFrom.setRatioCPU(obj[0]==null?0:Integer.parseInt(obj[0].toString().substring(0, obj[0].toString().indexOf("."))));
					serverInfoFrom.setRatioMEMORY(obj[1]==null?0:Integer.parseInt(obj[1].toString().substring(0, obj[1].toString().indexOf("."))));
					serverInfoFrom.setRatioHARDDISK(obj[2]==null?0:Integer.parseInt(obj[2].toString().substring(0, obj[2].toString().indexOf("."))));
					serverInfoList.add(serverInfoFrom);
				} else {
					ServerInfoForm serverInfoFrom = new ServerInfoForm();
					serverInfoFrom.setRatioCPU(0);
					serverInfoFrom.setRatioMEMORY(0);
					serverInfoFrom.setRatioHARDDISK(0);
					serverInfoList.add(serverInfoFrom);
				}
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
		} finally {
			session.close();
			return serverInfoList;
		}
	}

	private ServerInfoForm setServerInfoFormByFrameServerinfo(ServerInfoForm serverInfoForm, FrameServerinfo frameServerinfo) {
		serverInfoForm.setServerinfoId(frameServerinfo.getServerinfoId());
		serverInfoForm.setRatioCPU(Integer.parseInt(frameServerinfo.getRatioCpu()+""));
		serverInfoForm.setRatioMEMORY(Integer.parseInt(frameServerinfo.getRatioMemory()+""));
		serverInfoForm.setUseMEMORY(frameServerinfo.getUseMemory());
		serverInfoForm.setRatioHARDDISK(Integer.parseInt(frameServerinfo.getRatioHarddisk()+""));
		serverInfoForm.setUseHARDDISK(frameServerinfo.getUseHarddisk());
		serverInfoForm.setLetter(frameServerinfo.getLetter());
		serverInfoForm.setCreateTime(frameServerinfo.getCreateTime());
		return serverInfoForm;
	}

	public static void main(String[] args) {
//		List<ServerInfoForm> list = new FrameServerinfoDAO().serverInfoListByDay("20121107");
//		List<ServerInfoForm> list = new FrameServerinfoDAO().serverInfoListByWeek("20121107","20121113");
//		List<ServerInfoForm> list = new FrameServerinfoDAO().serverInfoListByMonth("201212");
		List<ServerInfoForm> list = new FrameServerinfoDAO().serverInfoListByYear("2012");
		System.out.println(list.size());
		for(ServerInfoForm serverInfoForm: list) {
			System.out.println("=================================");
			System.out.print("  "+serverInfoForm.getRatioCPU());
			System.out.print("  "+serverInfoForm.getRatioMEMORY());
			System.out.print("  "+serverInfoForm.getRatioHARDDISK());
			System.out.println();
		}
	}
}