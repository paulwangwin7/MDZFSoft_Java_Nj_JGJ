package com.njmd.dao;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.Constants;
import com.njmd.dao.BaseHibernateDAO;
import com.njmd.pojo.FrameRole;
import com.njmd.pojo.FrameTree;
import com.njmd.pojo.FrameUser;

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
 * FrameUser entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameUser
 * @author MyEclipse Persistence Tools
 */

public class FrameUserDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameUserDAO.class);
	// property constants
	public static final String LOGIN_NAME = "loginName";
	public static final String LOGIN_PSWD = "loginPswd";
	public static final String USER_NAME = "userName";
	public static final String USER_CODE = "userCode";
	public static final String SEX = "sex";
	public static final String USER_IDCARD = "userIdcard";
	public static final String CARD_TYPEID = "cardTypeid";
	public static final String CARD_CODE = "cardCode";
	public static final String TREE_ID = "treeId";
	public static final String ROLE_ID = "roleId";
	public static final String CREATE_TIME = "createTime";
	public static final String USER_STATE = "userState";
	public static final String ROLE_TYPE = "roleType";

	@SuppressWarnings("finally")
	public int save(FrameUser transientInstance) {
		int saveResult = Constants.ACTION_FAILED;
		log.debug("saving FrameUser instance");
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
	public int delete(FrameUser persistentInstance) {
		int deleteResult = Constants.ACTION_FAILED;
		log.debug("deleting FrameUser instance");
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
	public FrameUser findById(java.lang.Long id) {
		FrameUser instance = null;
		log.debug("getting FrameUser instance with id: " + id);
		Session session = getSession();
		try {
			session.clear();
			instance = (FrameUser) session.get("com.njmd.pojo.FrameUser", id);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return instance;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findByExample(FrameUser instance) {
		List results = null;
		log.debug("finding FrameUser instance by example");
		Session session = getSession();
		try {
			session.clear();
			results = session.createCriteria("com.njmd.pojo.FrameUser").add(Example.create(instance)).list();
//			results = session.createCriteria("com.njmd.pojo.FrameUser").add(Example.create(instance).enableLike()).list();
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
		List list = null;
		log.debug("finding FrameUser instance with property: " + propertyName
				+ ", value: " + value);
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameUser as model where model." + propertyName + "= ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	public List findByLoginName(Object loginName) {
		return findByProperty(LOGIN_NAME, loginName);
	}

	@SuppressWarnings("unchecked")
	public List findByLoginPswd(Object loginPswd) {
		return findByProperty(LOGIN_PSWD, loginPswd);
	}

	@SuppressWarnings("unchecked")
	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	@SuppressWarnings("unchecked")
	public List findByUserCode(Object userCode) {
		return findByProperty(USER_CODE, userCode);
	}

	@SuppressWarnings("unchecked")
	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	@SuppressWarnings("unchecked")
	public List findByUserIdcard(Object userIdcard) {
		return findByProperty(USER_IDCARD, userIdcard);
	}

	@SuppressWarnings("unchecked")
	public List findByCardTypeid(Object cardTypeid) {
		return findByProperty(CARD_TYPEID, cardTypeid);
	}

	@SuppressWarnings("unchecked")
	public List findByCardCode(Object cardCode) {
		return findByProperty(CARD_CODE, cardCode);
	}

	@SuppressWarnings("unchecked")
	public List findByTreeId(Object treeId) {
		return findByProperty(TREE_ID, treeId);
	}

	@SuppressWarnings("unchecked")
	public List findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
	}

	@SuppressWarnings("unchecked")
	public List findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	@SuppressWarnings("unchecked")
	public List findByUserState(Object userState) {
		return findByProperty(USER_STATE, userState);
	}

	@SuppressWarnings("unchecked")
	public List findByRoleType(Object roleType) {
		return findByProperty(ROLE_TYPE, roleType);
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findAll() {
		List list = null;
		log.debug("finding all FrameUser instances");
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameUser";
			Query queryObject = session.createQuery(queryString);
			list = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
//			throw re;
		} finally {
			session.close();
			return list;
		}
	}

	@SuppressWarnings("finally")
	public FrameUser merge(FrameUser detachedInstance) {
		FrameUser result = null;
		log.debug("merging FrameUser instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			result = (FrameUser) session.merge(detachedInstance);
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
	public int attachDirty(FrameUser instance) {
		int attachResult = Constants.ACTION_FAILED;
		log.debug("attaching dirty FrameUser instance");
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

	public void attachClean(FrameUser instance) {
		log.debug("attaching clean FrameUser instance");
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
	public Page getUserList(FrameUser instance, String queryTreeId, Page page) {
		Session session = getSession();
		try {
			session.clear();
			//获取该警员下级部门treeIds
			List<Long> childTreeIds = new ArrayList<Long>();
			String childTree = "from FrameTree as model where model.parentTreeId = ?";
			Query queryObject = session.createQuery(childTree);
			queryObject.setParameter(0, instance.getTreeId());
			List list = queryObject.list();
			for(int i=0; i<list.size(); i++) {
				childTreeIds.add(((FrameTree)list.get(i)).getTreeId());
			}

			StringBuffer queryString = new StringBuffer("select model,model2,model3 from FrameUser as model,FrameTree as model2,FrameRole as model3 where model.treeId=model2.treeId and model.roleId=model3.roleId");
			if (instance.getUserName() != null && !instance.getUserName().equals("")) {
				queryString.append(" and model.userName like ?");
			}
			if (instance.getUserCode() != null && !instance.getUserCode().equals("")) {
				queryString.append(" and model.userCode = ?");
			}
			if (instance.getSex() != null && !instance.getSex().equals("")) {
				queryString.append(" and model.sex = ?");
			}
			if (instance.getTreeId()!=null && instance.getTreeId()!=0) {
				if(childTreeIds.size()>0) {
					queryString.append(" and (model2.treeId = ?");
					for(long treeId: childTreeIds) {
						System.out.println("childTreeId: "+treeId);
						queryString.append(" or model2.treeId = ?");
					}
					queryString.append(")");
				} else {
					queryString.append(" and model2.treeId = ?");
				}
			}
			if(!queryTreeId.equals(""))
			{
				queryString.append(" and model.treeId = ?");
			}
			queryString.append(" order by model.createTime desc");
			queryObject = session.createQuery(queryString.toString());
			int paramIndex = 0;
			if (instance.getUserName() != null && !instance.getUserName().equals("")) {
				queryObject.setParameter(paramIndex++, "%"+instance.getUserName()+"%");
			}
			if (instance.getUserCode() != null && !instance.getUserCode().equals("")) {
				queryObject.setParameter(paramIndex++, instance.getUserCode());
			}
			if (instance.getSex() != null && !instance.getSex().equals("")) {
				queryObject.setParameter(paramIndex++, instance.getSex());
			}
			if (instance.getTreeId()!=null && instance.getTreeId()!=0) {
				if(childTreeIds.size()>0) {
					queryObject.setParameter(paramIndex++, instance.getTreeId());
					for(long treeId: childTreeIds) {
						queryObject.setParameter(paramIndex++, treeId);
					}
					queryString.append(")");
				} else {
					queryObject.setParameter(paramIndex++, instance.getTreeId());
				}
			}
			if(!queryTreeId.equals(""))
			{
				queryObject.setParameter(paramIndex++, new Long(queryTreeId));
			}
			page.setTotal(queryObject.list().size());
			queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
			queryObject.setMaxResults(page.getDbLine());
			List<UserForm> userList = new ArrayList<UserForm>();
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				for(int i=0; i<querylist.size(); i++) {
					Object[] obj = (Object[]) querylist.get(i);
					FrameUser frameUser = (FrameUser)(obj[0]);
					FrameTree frameTree = (FrameTree)(obj[1]);
					FrameRole frameRole = (FrameRole)(obj[2]);
					userList.add(setUserForm(frameUser, frameTree, frameRole));
				}
			}
			page.setListObject(userList);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Page getUserListByAdmin(FrameUser instance, String queryTreeId, Page page) {
		Session session = getSession();
		try {
			session.clear();
			StringBuffer queryString = new StringBuffer("from FrameUser as model where model.userName like ? and model.userCode like ?");
			if(!queryTreeId.equals(""))
			{
				queryString.append(" and treeId = ?");
			}
			queryString.append(" order by model.createTime desc");
			Query queryObject = session.createQuery(queryString.toString());
			queryObject.setParameter(0, "%"+instance.getUserName()+"%");
			queryObject.setParameter(1, "%"+instance.getUserCode()+"%");
			if(!queryTreeId.equals(""))
			{
				queryObject.setParameter(2, new Long(queryTreeId));
			}
			page.setTotal(queryObject.list().size());
			queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
			queryObject.setMaxResults(page.getDbLine());
			List<UserForm> userList = new ArrayList<UserForm>();
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				for(int i=0; i<querylist.size(); i++) {
					FrameUser frameUser = (FrameUser)(querylist.get(i));
					FrameTree frameTree = null;
					if(frameUser.getTreeId()!=0) {
						frameTree = frameTreeById(frameUser.getTreeId());
					}
					FrameRole frameRole = null;
					if(frameUser.getRoleId()!=0) {
						frameRole = frameRoleById(frameUser.getRoleId());
					}
	//				UserForm userForm = new UserForm();
	//				userForm.setUserId(frameUser.getUserId());
	//				userForm.setLoginName(frameUser.getLoginName());
	//				userForm.setLoginPswd(frameUser.getLoginPswd());
	//				userForm.setUserName(frameUser.getUserName());
	//				userForm.setUserCode(frameUser.getUserCode());
	//				userForm.setSex(frameUser.getSex());
	//				userForm.setUserIdCard(frameUser.getUserIdcard());
	//				userForm.setCard_typeId(frameUser.getCardTypeid());
	//				userForm.setCardCode(frameUser.getCardCode());
	//				userForm.setTreeId(frameUser.getTreeId());
	//				userForm.setRoleId(frameUser.getRoleId());
	//				userForm.setCreateTime(frameUser.getCreateTime());
	//				userForm.setUserState(frameUser.getUserState());
	//				userForm.setTreeNameStr(frameTree.getTreeName());
	//				userForm.setRoleNameStr(frameRole.getId().getRoleName());
					userList.add(setUserForm(frameUser, frameTree, frameRole));
				}
			}
			page.setListObject(userList);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}
	@SuppressWarnings({ "finally", "unchecked" })
	public Page getUserList(Page page) {
		Session session = getSession();
		try {
			session.clear();

			StringBuffer queryString = new StringBuffer("select model,model2,model3 from FrameUser as model,FrameTree as model2,FrameRole as model3 where model.treeId=model2.treeId and model.roleId=model3.roleId");
			queryString.append(" order by model.createTime desc");
			Query queryObject = session.createQuery(queryString.toString());
			page.setTotal(queryObject.list().size());
			queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
			queryObject.setMaxResults(page.getDbLine());
			List<UserForm> userList = new ArrayList<UserForm>();
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				for(int i=0; i<querylist.size(); i++) {
					Object[] obj = (Object[]) querylist.get(i);
					FrameUser frameUser = (FrameUser)(obj[0]);
					FrameTree frameTree = (FrameTree)(obj[1]);
					FrameRole frameRole = (FrameRole)(obj[2]);
	//				UserForm userForm = new UserForm();
	//				userForm.setUserId(frameUser.getUserId());
	//				userForm.setLoginName(frameUser.getLoginName());
	//				userForm.setLoginPswd(frameUser.getLoginPswd());
	//				userForm.setUserName(frameUser.getUserName());
	//				userForm.setUserCode(frameUser.getUserCode());
	//				userForm.setSex(frameUser.getSex());
	//				userForm.setUserIdCard(frameUser.getUserIdcard());
	//				userForm.setCard_typeId(frameUser.getCardTypeid());
	//				userForm.setCardCode(frameUser.getCardCode());
	//				userForm.setTreeId(frameUser.getTreeId());
	//				userForm.setRoleId(frameUser.getRoleId());
	//				userForm.setCreateTime(frameUser.getCreateTime());
	//				userForm.setUserState(frameUser.getUserState());
	//				userForm.setTreeNameStr(frameTree.getTreeName());
	//				userForm.setRoleNameStr(frameRole.getId().getRoleName());
					userList.add(setUserForm(frameUser, frameTree, frameRole));
				}
			}
			page.setListObject(userList);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}
	@SuppressWarnings({ "finally", "unchecked" })
	public Page findByTree(long treeId, Page page) {
		Session session = getSession();
		try {
			session.clear();

			StringBuffer queryString = new StringBuffer("select model,model2,model3 from FrameUser as model,FrameTree as model2,FrameRole as model3 where model.treeId=? and model.treeId=model2.treeId and model.roleId=model3.roleId");
			queryString.append(" order by model.createTime desc");
			Query queryObject = session.createQuery(queryString.toString());
			queryObject.setParameter(0, treeId);
			page.setTotal(queryObject.list().size());
			queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
			queryObject.setMaxResults(page.getDbLine());
			List<UserForm> userList = new ArrayList<UserForm>();
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				for(int i=0; i<querylist.size(); i++) {
					Object[] obj = (Object[]) querylist.get(i);
					FrameUser frameUser = (FrameUser)(obj[0]);
					FrameTree frameTree = (FrameTree)(obj[1]);
					FrameRole frameRole = (FrameRole)(obj[2]);
					userList.add(setUserForm(frameUser, frameTree, frameRole));
				}
			}
			page.setListObject(userList);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public UserForm userLogin2(FrameUser instance) {
		UserForm userForm = null;
		Session session = getSession();
		try {
			session.clear();
			StringBuffer queryString = new StringBuffer("select model,model2,model3 from FrameUser as model,FrameTree as model2,FrameRole as model3 where model.treeId=model2.treeId and model.roleId=model3.roleId");
			queryString.append(" and model.loginName = ? and model.loginPswd = ?");
			Query queryObject = session.createQuery(queryString.toString());
			queryObject.setParameter(0, instance.getLoginName());
			queryObject.setParameter(1, instance.getLoginPswd());
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				Object[] obj = (Object[]) querylist.get(0);
				FrameUser frameUser = (FrameUser)(obj[0]);
				FrameTree frameTree = (FrameTree)(obj[1]);
				FrameRole frameRole = (FrameRole)(obj[2]);
				userForm = setUserForm(frameUser, frameTree, frameRole);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return userForm;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public UserForm userLogin(FrameUser instance) {
		UserForm userForm = null;
		Session session = getSession();
		try {
			session.clear();
			StringBuffer queryString = new StringBuffer("from FrameUser as model where model.loginName = ? and model.loginPswd = ?");
			Query queryObject = session.createQuery(queryString.toString());
			queryObject.setParameter(0, instance.getLoginName());
			queryObject.setParameter(1, instance.getLoginPswd());
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				FrameUser frameUser = (FrameUser)(querylist.get(0));
				FrameTree frameTree = null;
				if(frameUser.getTreeId()!=0) {
					frameTree = frameTreeById(frameUser.getTreeId());
				}
				FrameRole frameRole = null;
				if(frameUser.getRoleId()!=0) {
					frameRole = frameRoleById(frameUser.getRoleId());
				}
				userForm = setUserForm(frameUser, frameTree, frameRole);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return userForm;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public UserForm userDetailById(java.lang.Long id) {
		UserForm userForm = null;
		Session session = getSession();
		try {
			session.clear();
			StringBuffer queryString = new StringBuffer("select model,model2,model3 from FrameUser as model,FrameTree as model2,FrameRole as model3 where model.treeId=model2.treeId and model.roleId=model3.roleId");
			queryString.append(" and model.userId = ?");
			Query queryObject = session.createQuery(queryString.toString());
			queryObject.setParameter(0, id);
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				Object[] obj = (Object[]) querylist.get(0);
				FrameUser frameUser = (FrameUser)(obj[0]);
				FrameTree frameTree = (FrameTree)(obj[1]);
				FrameRole frameRole = (FrameRole)(obj[2]);
				userForm = setUserForm(frameUser, frameTree, frameRole);
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
			session.close();
			return userForm;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public boolean loginNameRepeat(FrameUser instance) {
		boolean repeat = false;//登录帐号是否重复
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameUser as model where model.loginName = ? and model.userId != ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, instance.getLoginName());
			queryObject.setParameter(1, instance.getUserId());
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
	public FrameTree frameTreeById(Long treeId) {
		FrameTree frameTree = null;
		try {
			if(treeId!=null) {
				Session session = getSession();
				session.clear();
				String queryString = "from FrameTree as model where model.treeId = ?";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter(0, treeId);
				List list = queryObject.list();
				if(list!=null && list.size()>0) {
					frameTree = (FrameTree)list.get(0);
				}
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
//			session.close();
			return frameTree;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public FrameRole frameRoleById(Long roleId) {
		FrameRole frameRole = null;
		try {
			if(roleId!=null) {
				Session session = getSession();
				session.clear();
				String queryString = "from FrameRole as model where model.roleId = ?";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter(0, roleId);
				List list = queryObject.list();
				if(list!=null && list.size()>0) {
					frameRole = (FrameRole)list.get(0);
				}
			}
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
//			throw re;
		} finally {
//			session.close();
			return frameRole;
		}
	}

	@SuppressWarnings("finally")
	public UserForm findDetailById(java.lang.Long id) {
		UserForm userForm = null;
		log.debug("getting FrameUser instance with id: " + id);
		Session session = getSession();
		try {
			session.clear();
			FrameUser frameUser = (FrameUser) session.get("com.njmd.pojo.FrameUser", id);
			FrameTree frameTree = null;
			if(frameUser.getTreeId()!=0) {
				frameTree = frameTreeById(frameUser.getTreeId());
			}
			FrameRole frameRole = null;
			if(frameUser.getRoleId()!=0) {
				frameRole = frameRoleById(frameUser.getRoleId());
			}
			userForm = setUserForm(frameUser, frameTree, frameRole);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return userForm;
		}
	}

	private UserForm setUserForm(FrameUser frameUser, FrameTree frameTree, FrameRole frameRole) {
		UserForm userForm = new UserForm();
		userForm.setUserId(frameUser.getUserId());
		userForm.setLoginName(frameUser.getLoginName());
		userForm.setLoginPswd(frameUser.getLoginPswd());
		userForm.setUserName(frameUser.getUserName());
		userForm.setUserCode(frameUser.getUserCode());
		userForm.setSex(frameUser.getSex());
		userForm.setUserIdCard(frameUser.getUserIdcard());
		userForm.setCard_typeId(frameUser.getCardTypeid());
		userForm.setCardCode(frameUser.getCardCode());
		userForm.setTreeId(frameUser.getTreeId());
		userForm.setRoleId(frameUser.getRoleId());
		userForm.setCreateTime(frameUser.getCreateTime());
		userForm.setUserState(frameUser.getUserState());
		if(frameTree!=null) {
			userForm.setTreeNameStr(frameTree.getTreeName());
		} else {
			userForm.setTreeNameStr("交警总队");
		}
		if(frameRole!=null) {
			userForm.setRoleNameStr(frameRole.getRoleName());
		} else {
			userForm.setRoleNameStr("交管局领导");
		}
		userForm.setRoleType(frameUser.getRoleType()==null?"":"0");
		return userForm;
	}

	public static void main(String[] args) {
//		FrameUser frameUser = new FrameUser();
////		frameUser.setUserName("张");
//		frameUser.setTreeId(new Long(1));
//		Page page = new Page();
//		page.setPageCute(1);
//		page.setDbLine(5);
//		page = new FrameUserDAO().getUserList(frameUser, page);
//		System.out.println(page.getPageMax()+":"+((List)page.getListObject()).size());
//		for(int i=0; i<((List)page.getListObject()).size(); i++) {
//			UserForm userform = (UserForm) ((List)page.getListObject()).get(i);
//			System.out.print(userform.getLoginName()+"  ");
//			System.out.print(userform.getUserName()+"  ");
//			System.out.print(userform.getTreeNameStr()+"  ");
//			System.out.println(userform.getRoleNameStr());
//		}
	}
}