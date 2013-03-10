package com.njmd.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.manager.pub.bean.UrlForm;
import com.njmd.pojo.FrameMenu;
import com.njmd.pojo.FrameUrl;

/**
 * A data access object (DAO) providing persistence and search support for
 * FrameMenu entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameMenu
 * @author MyEclipse Persistence Tools
 */

public class FrameMenuDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameMenuDAO.class);
	// property constants
	public static final String MENU_NAME = "menuName";
	public static final String MENU_SORT = "menuSort";
	public static final String MENU_STATE = "menuState";

	public void save(FrameMenu transientInstance) {
		log.debug("saving FrameMenu instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FrameMenu persistentInstance) {
		log.debug("deleting FrameMenu instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FrameMenu findById(java.lang.Long id) {
		log.debug("getting FrameMenu instance with id: " + id);
		try {
			FrameMenu instance = (FrameMenu) getSession().get(
					"com.njmd.pojo.FrameMenu", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FrameMenu instance) {
		log.debug("finding FrameMenu instance by example");
		try {
			List results = getSession().createCriteria(
					"com.njmd.pojo.FrameMenu").add(Example.create(instance))
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
		log.debug("finding FrameMenu instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FrameMenu as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMenuName(Object menuName) {
		return findByProperty(MENU_NAME, menuName);
	}

	public List findByMenuSort(Object menuSort) {
		return findByProperty(MENU_SORT, menuSort);
	}

	public List findByMenuState(Object menuState) {
		return findByProperty(MENU_STATE, menuState);
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findAll() {
		List results = null;
		log.debug("finding all FrameMenu instances");
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameMenu";
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

	public FrameMenu merge(FrameMenu detachedInstance) {
		log.debug("merging FrameMenu instance");
		try {
			FrameMenu result = (FrameMenu) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FrameMenu instance) {
		log.debug("attaching dirty FrameMenu instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FrameMenu instance) {
		log.debug("attaching clean FrameMenu instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 查询所有菜单及菜单下对应的所有功能url
	 * 
	 * @return list_menuAndurl [ ['菜单1名称',[urlForm,urlForm...]],
	 *         ['菜单2名称',[urlForm,urlForm...]] ... ]
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	public List queryMenuAndUrl() {
		List list = null;
		List list_menuAndurl = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameMenu as model order by model.menuSort";
			Query queryObject = session.createQuery(queryString);
			List menuList = queryObject.list();
			if(menuList!=null && menuList.size()>0) {
				list = new ArrayList();
				for(Object frameMenuObj: menuList) {
					list_menuAndurl = new ArrayList();
					List<UrlForm> urlList = findUrlByMenuId(((FrameMenu)frameMenuObj).getMenuId());
					if(urlList!=null && urlList.size()>0) {
						list_menuAndurl.add(((FrameMenu)frameMenuObj).getMenuName());
						list_menuAndurl.add(urlList);
						list.add(list_menuAndurl);
					}
				}
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
//			throw re;
		} finally {
			session.close();
			return list;
		}
	}

	/**
	 * 查询所有菜单及菜单下对应的所有功能url
	 * 
	 * @return list_menuAndurl [ ['菜单1名称',[urlForm,urlForm...]],
	 *         ['菜单2名称',[urlForm,urlForm...]] ... ]
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	public List queryMenuAndUrlByUrlIds(String[] urlId) {
		List list = null;
		List list_menuAndurl = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameMenu as model order by model.menuSort";
			Query queryObject = session.createQuery(queryString);
			List menuList = queryObject.list();
			if(menuList!=null && menuList.size()>0) {
				list = new ArrayList();
				for(Object frameMenuObj: menuList) {
					list_menuAndurl = new ArrayList();
					List<UrlForm> urlList = findUrlByMenuId(((FrameMenu)frameMenuObj).getMenuId(), urlId);
					if(urlList!=null && urlList.size()>0) {
						list_menuAndurl.add(((FrameMenu)frameMenuObj).getMenuName());
						list_menuAndurl.add(urlList);
						list.add(list_menuAndurl);
					}
				}
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
//			throw re;
		} finally {
			session.close();
			return list;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	private List<UrlForm> findUrlByMenuId(java.lang.Long menuId,String[] urlId) {
		List<UrlForm> urlList = null;
		Session session = getSession();
		try {
			if(urlId.length>0) {
				urlList = new ArrayList<UrlForm>();
				String queryString = "from FrameUrl as model where model.parentMenuId=? and model.urlId in ";
				queryString += "(";
				for(int i=0; i<urlId.length; i++) {
					if(i==urlId.length-1) {
						queryString += urlId[i];
					} else {
						queryString += urlId[i]+",";
					}
				}
				queryString += ") order by model.urlSort";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter(0, menuId);
				List list = queryObject.list();
				if(list!=null && list.size()>0) {
					for(Object frameUrlObj: list) {
						FrameUrl frameUrl = (FrameUrl) frameUrlObj;
						UrlForm urlForm = new UrlForm();
						urlForm.setUrlId(frameUrl.getUrlId());
						urlForm.setUrlName(frameUrl.getUrlName());
						urlForm.setUrlDesc(frameUrl.getUrlDesc());
						urlForm.setUrlValue(frameUrl.getUrlValue());
						urlForm.setParentMenuId(frameUrl.getParentMenuId()+"");
						urlForm.setUrlState(frameUrl.getUrlState());
						urlForm.setUrlSort(frameUrl.getUrlSort()+"");
						urlForm.setUrlTab(frameUrl.getUrlTab());
						urlList.add(urlForm);
					}
				}
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.clear();
			return urlList;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	private List<UrlForm> findUrlByMenuId(java.lang.Long menuId) {
		List<UrlForm> urlList = null;
		Session session = getSession();
		try {
			String queryString = "from FrameUrl as model where model.parentMenuId=? order by model.urlSort";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, menuId);
			List list = queryObject.list();
			if(list!=null && list.size()>0) {
				urlList = new ArrayList<UrlForm>();
				for(Object frameUrlObj: list) {
					FrameUrl frameUrl = (FrameUrl) frameUrlObj;
					UrlForm urlForm = new UrlForm();
					urlForm.setUrlId(frameUrl.getUrlId());
					urlForm.setUrlName(frameUrl.getUrlName());
					urlForm.setUrlDesc(frameUrl.getUrlDesc());
					urlForm.setUrlValue(frameUrl.getUrlValue());
					urlForm.setParentMenuId(frameUrl.getParentMenuId()+"");
					urlForm.setUrlState(frameUrl.getUrlState());
					urlForm.setUrlSort(frameUrl.getUrlSort()+"");
					urlForm.setUrlTab(frameUrl.getUrlTab());
					urlList.add(urlForm);
				}
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.clear();
			return urlList;
		}
	}
}