package com.njmd.dao;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.util.Constants;
import com.manager.pub.util.DateUtils;
import com.njmd.dao.BaseHibernateDAO;
import com.njmd.pojo.FrameTree;
import com.njmd.pojo.FrameUpload;
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
 * FrameUpload entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.njmd.pojo.FrameUpload
 * @author MyEclipse Persistence Tools
 */

public class FrameUploadDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FrameUploadDAO.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String EDIT_ID = "editId";
	public static final String UPLOAD_NAME = "uploadName";
	public static final String PLAY_PATH = "playPath";
	public static final String FILE_CREATETIME = "fileCreatetime";
	public static final String SHOW_PATH = "showPath";
	public static final String UPLOAD_TIME = "uploadTime";
	public static final String FILE_STATE = "fileState";
	public static final String TREE2_ID = "tree2Id";
	public static final String TREE1_ID = "tree1Id";
	public static final String TREE_NAME = "treeName";
	public static final String FILE_STATS = "fileStats";
	public static final String FILE_REMARK = "fileRemark";
	public static final String IP_ADDR = "ipAddr";
	public static final String REAL_PATH = "realPath";
	public static final String FLV_PATH = "flvPath";

	@SuppressWarnings("finally")
	public int save(FrameUpload transientInstance) {
		int saveResult = Constants.ACTION_FAILED;
		log.debug("saving FrameUpload instance");
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
	public int delete(FrameUpload persistentInstance) {
		int deleteResult = Constants.ACTION_FAILED;
		log.debug("deleting FrameUpload instance");
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
	public FrameUpload findById(java.lang.Long id) {
		FrameUpload instance = null;
		log.debug("getting FrameUpload instance with id: " + id);
		Session session = getSession();
		try {
			session.clear();
			instance = (FrameUpload) session.get("com.njmd.pojo.FrameUpload", id);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return instance;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findByExample(FrameUpload instance) {
		List results = null;
		log.debug("finding FrameUpload instance by example");
		Session session = getSession();
		try {
			session.clear();
			results = session.createCriteria("com.njmd.pojo.FrameUpload").add(Example.create(instance)).list();
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
		log.debug("finding FrameUpload instance with property: " + propertyName + ", value: " + value);
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameUpload as model where model." + propertyName + "= ?";
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
	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	@SuppressWarnings("unchecked")
	public List findByEditId(Object editId) {
		return findByProperty(EDIT_ID, editId);
	}

	@SuppressWarnings("unchecked")
	public List findByUploadName(Object uploadName) {
		return findByProperty(UPLOAD_NAME, uploadName);
	}

	@SuppressWarnings("unchecked")
	public List findByPlayPath(Object playPath) {
		return findByProperty(PLAY_PATH, playPath);
	}

	@SuppressWarnings("unchecked")
	public List findByFileCreatetime(Object fileCreatetime) {
		return findByProperty(FILE_CREATETIME, fileCreatetime);
	}

	@SuppressWarnings("unchecked")
	public List findByShowPath(Object showPath) {
		return findByProperty(SHOW_PATH, showPath);
	}

	@SuppressWarnings("unchecked")
	public List findByUploadTime(Object uploadTime) {
		return findByProperty(UPLOAD_TIME, uploadTime);
	}

	@SuppressWarnings("unchecked")
	public List findByFileState(Object fileState) {
		return findByProperty(FILE_STATE, fileState);
	}

	@SuppressWarnings("unchecked")
	public List findByTree2Id(Object tree2Id) {
		return findByProperty(TREE2_ID, tree2Id);
	}

	@SuppressWarnings("unchecked")
	public List findByTree1Id(Object tree1Id) {
		return findByProperty(TREE1_ID, tree1Id);
	}

	@SuppressWarnings("unchecked")
	public List findByTreeName(Object treeName) {
		return findByProperty(TREE_NAME, treeName);
	}

	@SuppressWarnings("unchecked")
	public List findByFileStats(Object fileStats) {
		return findByProperty(FILE_STATS, fileStats);
	}

	@SuppressWarnings("unchecked")
	public List findByFileRemark(Object fileRemark) {
		return findByProperty(FILE_REMARK, fileRemark);
	}

	@SuppressWarnings("unchecked")
	public List findByIpAddr(Object ipAddr) {
		return findByProperty(IP_ADDR, ipAddr);
	}

	@SuppressWarnings("unchecked")
	public List findByRealPath(Object realPath) {
		return findByProperty(REAL_PATH, realPath);
	}

	@SuppressWarnings("unchecked")
	public List findByFlvPath(Object flvPath) {
		return findByProperty(FLV_PATH, flvPath);
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List findAll() {
		List results = null;
		log.debug("finding all FrameUpload instances");
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameUpload";
			Query queryObject = session.createQuery(queryString);
			results = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		} finally {
			session.close();
			return results;
		}
	}

	@SuppressWarnings("finally")
	public FrameUpload merge(FrameUpload detachedInstance) {
		FrameUpload result = null;
		log.debug("merging FrameUpload instance");
		Session session = getSession();
		try {
			session.getTransaction().begin();
			result = (FrameUpload) session.merge(detachedInstance);
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
	public int attachDirty(FrameUpload instance) {
		int attachResult = Constants.ACTION_FAILED;
		log.debug("attaching dirty FrameUpload instance");
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

	public void attachClean(FrameUpload instance) {
		log.debug("attaching clean FrameUpload instance");
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

	@SuppressWarnings("finally")
	public UploadForm uploadDetail(java.lang.Long uploadId) {
		UploadForm uploadForm = null;
		Session session = getSession();
		try {
			session.clear();
			FrameUpload instance = (FrameUpload) session.get("com.njmd.pojo.FrameUpload", uploadId);
			if(instance!=null) {
				uploadForm = setUploadFormByFrameUpload(new UploadForm(), instance);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return uploadForm;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Page uploadManagerQuery(String uploadName, String treeId,
			String beginTime, String endTime, String createTimeBegin, String createTimeEnd,
			String uploadUserId, String fileCreateUserId, String fileStats,
			String fileRemark, Page page) {
		Session session = getSession();
		try {
			session.clear();
			StringBuffer queryString = new StringBuffer("from FrameUpload as model");
			queryString.append(" where model.fileState = 'A'");
//			if(treeId.equals("")) {
//			} else {
//				queryString.append(" and model.tree2Id = ?");
//			}
			if(beginTime.equals("") || beginTime.equals("")) {

			} else {
				queryString.append(" and model.uploadTime >=? and model.uploadTime <=?");
			}
			if(createTimeBegin.equals("") || createTimeEnd.equals("")) {

			} else {
				queryString.append(" and model.fileCreatetime >=? and model.fileCreatetime <=?");
			}
			if(!uploadUserId.equals("")) {
				queryString.append(" and model.userId = ?");
			}
			if(!fileCreateUserId.equals("")) {
				queryString.append(" and model.editId = ?");
			}
			if(!fileStats.equals("")) {
				queryString.append(" and model.fileStats = ?");
			}
			if(!fileRemark.equals("")) {
				queryString.append(" and model.fileRemark like ?");
			}
			if(!uploadName.equals("")) {
				queryString.append(" and model.uploadName like ?");
			}
			queryString.append(" and (model.fileState!='F' or model.fileState!='U')");
			queryString.append(" and model.tree1Id in( "+treeId+") ");
			queryString.append(" order by model.uploadId desc");
			Query queryObject = session.createQuery(queryString.toString());
			int parameterIndex = 0;
//			if(treeId.equals("")) {
//			} else {
//				queryObject.setParameter(parameterIndex++, new Long(treeId));
//			}
			if(beginTime.equals("") || endTime.equals("")) {

			} else {
				queryObject.setParameter(parameterIndex++, beginTime);
				queryObject.setParameter(parameterIndex++, endTime);
			}
			if(createTimeBegin.equals("") || createTimeEnd.equals("")) {

			} else {
				queryObject.setParameter(parameterIndex++, createTimeBegin);
				queryObject.setParameter(parameterIndex++, createTimeEnd);
			}
			if(!uploadUserId.equals("")) {
				queryObject.setParameter(parameterIndex++, new Long(uploadUserId));
			}
			if(!fileCreateUserId.equals("")) {
				queryObject.setParameter(parameterIndex++, new Long(fileCreateUserId));
			}
			if(!fileStats.equals("")) {
				queryObject.setParameter(parameterIndex++, fileStats);
			}
			if(!fileRemark.equals("")) {
				queryObject.setParameter(parameterIndex++, "%"+fileRemark+"%");
			}
			if(!uploadName.equals("")) {
				queryObject.setParameter(parameterIndex++, "%"+uploadName+"%");
			}
			page.setTotal(queryObject.list().size());
			queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
			queryObject.setMaxResults(page.getDbLine());
			List<UploadForm> uploadList = null;
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				uploadList = new ArrayList<UploadForm>();
				for(Object obj: querylist) {
					uploadList.add(setUploadFormByFrameUpload(new UploadForm(), (FrameUpload)obj));
				}
			}
			page.setListObject(uploadList);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Page uploadListByTree(String uploadName, String treeId, String parentTreeId,
			String beginTime, String endTime, String uploadUserId,
			String fileCreateUserId, String fileStats, String fileRemark,
			Page page) {
		Session session = getSession();
		try {
			session.clear();
			StringBuffer queryString = new StringBuffer("from FrameUpload as model");
			queryString.append(" where model.fileState = 'A'");
			if(treeId.equals(parentTreeId)) {
				queryString.append(" and model.tree1Id = ?");
			} else {
				queryString.append(" and model.tree2Id = ?");
			}
			if(beginTime.equals("") || beginTime.equals("")) {

			} else {
				queryString.append(" and model.uploadTime >=? and model.uploadTime <=?");
			}
			if(!uploadUserId.equals("")) {
				queryString.append(" and model.userId = ?");
			}
			if(!fileCreateUserId.equals("")) {
				queryString.append(" and model.editId = ?");
			}
			if(!fileStats.equals("")) {
				queryString.append(" and model.fileStats = ?");
			}
			if(!fileRemark.equals("")) {
				queryString.append(" and model.fileRemark like ?");
			}
			if(!uploadName.equals("")) {
				queryString.append(" and model.uploadName like ?");
			}
			queryString.append(" and (model.fileState!='F' or model.fileState!='U')");
			queryString.append(" order by model.uploadId desc");
			Query queryObject = session.createQuery(queryString.toString());
			int parameterIndex = 0;
			queryObject.setParameter(parameterIndex++, new Long(treeId));
			if(beginTime.equals("") || endTime.equals("")) {

			} else {
				queryObject.setParameter(parameterIndex++, beginTime);
				queryObject.setParameter(parameterIndex++, endTime);
			}
			if(!uploadUserId.equals("")) {
				queryObject.setParameter(parameterIndex++, new Long(uploadUserId));
			}
			if(!fileCreateUserId.equals("")) {
				queryObject.setParameter(parameterIndex++, new Long(fileCreateUserId));
			}
			if(!fileStats.equals("")) {
				queryObject.setParameter(parameterIndex++, fileStats);
			}
			if(!fileRemark.equals("")) {
				queryObject.setParameter(parameterIndex++, "%"+fileRemark+"%");
			}
			if(!uploadName.equals("")) {
				queryObject.setParameter(parameterIndex++, "%"+uploadName+"%");
			}
			page.setTotal(queryObject.list().size());
			queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
			queryObject.setMaxResults(page.getDbLine());
			List<UploadForm> uploadList = null;
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				uploadList = new ArrayList<UploadForm>();
				for(Object obj: querylist) {
					uploadList.add(setUploadFormByFrameUpload(new UploadForm(), (FrameUpload)obj));
				}
			}
			page.setListObject(uploadList);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Page uploadListByAdmin(String uploadName, String treeId, String parentTreeId,
			String beginTime, String endTime, String uploadUserId,
			String fileCreateUserId, String fileStats, String fileRemark,
			Page page) {
		Session session = getSession();
		try {
			session.clear();
			StringBuffer queryString = new StringBuffer("from FrameUpload as model");
			queryString.append(" where model.fileState = 'A'");
			if(beginTime.equals("") || beginTime.equals("")) {

			} else {
				queryString.append(" and model.uploadTime >=? and model.uploadTime <=?");
			}
			if(!uploadUserId.equals("")) {
				queryString.append(" and model.userId = ?");
			}
			if(!fileCreateUserId.equals("")) {
				queryString.append(" and model.editId = ?");
			}
			if(!fileStats.equals("")) {
				queryString.append(" and model.fileStats = ?");
			}
			if(!fileRemark.equals("")) {
				queryString.append(" and model.fileRemark like ?");
			}
			if(!uploadName.equals("")) {
				queryString.append(" and model.uploadName like ?");
			}
			queryString.append(" and (model.fileState!='F' or model.fileState!='U')");
			queryString.append(" order by model.uploadId desc");
			Query queryObject = session.createQuery(queryString.toString());
			int parameterIndex = 0;
			if(beginTime.equals("") || endTime.equals("")) {

			} else {
				queryObject.setParameter(parameterIndex++, beginTime);
				queryObject.setParameter(parameterIndex++, endTime);
			}
			if(!uploadUserId.equals("")) {
				queryObject.setParameter(parameterIndex++, new Long(uploadUserId));
			}
			if(!fileCreateUserId.equals("")) {
				queryObject.setParameter(parameterIndex++, new Long(fileCreateUserId));
			}
			if(!fileStats.equals("")) {
				queryObject.setParameter(parameterIndex++, fileStats);
			}
			if(!fileRemark.equals("")) {
				queryObject.setParameter(parameterIndex++, "%"+fileRemark+"%");
			}
			if(!uploadName.equals("")) {
				queryObject.setParameter(parameterIndex++, "%"+uploadName+"%");
			}
			page.setTotal(queryObject.list().size());
			queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
			queryObject.setMaxResults(page.getDbLine());
			List<UploadForm> uploadList = null;
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				uploadList = new ArrayList<UploadForm>();
				for(Object obj: querylist) {
					uploadList.add(setUploadFormByFrameUpload(new UploadForm(), (FrameUpload)obj));
				}
			}
			page.setListObject(uploadList);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public Page mineUploadList(String treeId, String parentTreeId,
			String uploadUserId, Page page) {
		Session session = getSession();
		try {
			session.clear();
			StringBuffer queryString = new StringBuffer("from FrameUpload as model");
			queryString.append(" where model.fileState = 'A'");
			if(treeId.equals(parentTreeId)) {
				queryString.append(" and model.tree1Id = ?");
			} else {
				queryString.append(" and model.tree2Id = ?");
			}
			if (!uploadUserId.equals("")) {
				queryString.append(" and (model.userId = ? or model.editId = ?)");
			}
			queryString.append(" and (model.fileState!='F' or model.fileState!='U')");
			queryString.append(" order by model.uploadId desc");
			Query queryObject = session.createQuery(queryString.toString());
			int parameterIndex = 0;
			queryObject.setParameter(parameterIndex++, new Long(treeId));
			if (!uploadUserId.equals("")) {
				queryObject.setParameter(parameterIndex++, new Long(uploadUserId));
				queryObject.setParameter(parameterIndex++, new Long(uploadUserId));
			}
			page.setTotal(queryObject.list().size());
			queryObject.setFirstResult((page.getPageCute()-1)*page.getDbLine());
			queryObject.setMaxResults(page.getDbLine());
			List<UploadForm> uploadList = null;
			List querylist = queryObject.list();
			if(querylist!=null && querylist.size()>0) {
				uploadList = new ArrayList<UploadForm>();
				for(Object obj: querylist) {
					uploadList.add(setUploadFormByFrameUpload(new UploadForm(), (FrameUpload)obj));
				}
			}
			page.setListObject(uploadList);
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
			session.close();
			return page;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<UploadForm> expiredSysDekList(int expiredDays) {
		List<UploadForm> uploadList = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameUpload as model where model.fileStats = '0' and model.fileState != 'F' and to_number(substr(uploadTime,0,8))<=to_number(to_char(sysdate-"+expiredDays+",'yyyyMMdd'))";
			Query queryObject = session.createQuery(queryString);
			List list = queryObject.list();
			if(list!=null && list.size()>0) {
				uploadList = new ArrayList<UploadForm>();
				for(Object obj: list) {
					uploadList.add(setUploadFormByFrameUpload(new UploadForm(), (FrameUpload)obj));
				}
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
//			session.close();
			return uploadList;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<UploadForm> expiredUploadAllList(String expired) {
		List<UploadForm> uploadList = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from FrameUpload as model where model.fileState = 'A' and substr(uploadTime,0,8)<=?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, expired);
			List list = queryObject.list();
			if(list!=null && list.size()>0) {
				uploadList = new ArrayList<UploadForm>();
				for(Object obj: list) {
					UploadForm uploadForm = new UploadForm();
					FrameUpload frameUpload = (FrameUpload)obj;
					uploadForm.setUploadId(frameUpload.getUploadId());
					uploadForm.setUserId(frameUpload.getUserId());
					uploadForm.setEditId(frameUpload.getEditId());
					uploadForm.setUploadName(frameUpload.getUploadName());
					uploadForm.setPlayPath(frameUpload.getPlayPath());
					uploadForm.setShowPath(frameUpload.getShowPath());
					uploadForm.setFileCreatetime(frameUpload.getFileCreatetime());
					uploadForm.setUploadTime(frameUpload.getUploadTime());
					uploadForm.setFileState(frameUpload.getFileState());
					uploadForm.setTree1Id(frameUpload.getTree1Id());
					uploadForm.setTree2Id(frameUpload.getTree2Id());
					uploadForm.setFileStats(frameUpload.getFileStats());
					uploadForm.setFileRemark(frameUpload.getFileRemark());
					uploadForm.setIpAddr(frameUpload.getIpAddr());
					uploadForm.setFileSavePath(frameUpload.getRealPath());
					uploadForm.setFlvPath(frameUpload.getFlvPath());
					uploadList.add(uploadForm);
				}
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
//			session.close();
			return uploadList;
		}
	}

	private UploadForm setUploadFormByFrameUpload(UploadForm uploadForm, FrameUpload frameUpload) {
		uploadForm.setUploadId(frameUpload.getUploadId());
		uploadForm.setUserId(frameUpload.getUserId());
		uploadForm.setUserName(findByUserId(frameUpload.getUserId()).getUserName());
		uploadForm.setEditId(frameUpload.getEditId());
		uploadForm.setEditName(findByUserId(frameUpload.getEditId()).getUserName());
		uploadForm.setUploadName(frameUpload.getUploadName());
		uploadForm.setPlayPath(frameUpload.getPlayPath());
		uploadForm.setShowPath(frameUpload.getShowPath());
		uploadForm.setFileCreatetime(frameUpload.getFileCreatetime());
		uploadForm.setUploadTime(frameUpload.getUploadTime());
		uploadForm.setFileState(frameUpload.getFileState());
		uploadForm.setTree1Id(frameUpload.getTree1Id());
		uploadForm.setTree2Id(frameUpload.getTree2Id());
//		uploadForm.setTreeName(findByTreeId(frameUpload.getTree1Id()).getTreeName());//显示的是上传人的部门名称
		uploadForm.setTreeName(findByTreeId(frameUpload.getTree2Id()).getTreeName());//显示的是上传人的部门名称
		uploadForm.setFileStats(frameUpload.getFileStats());
		uploadForm.setFileRemark(frameUpload.getFileRemark());
		uploadForm.setIpAddr(frameUpload.getIpAddr());
		uploadForm.setFileSavePath(frameUpload.getRealPath());
		uploadForm.setFlvPath(frameUpload.getFlvPath());
		return uploadForm;
	}

	@SuppressWarnings({ "finally", "unchecked" })
	private FrameTree findByTreeId(java.lang.Long treeId) {
		FrameTree frameTree = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from com.njmd.pojo.FrameTree as model where model.treeId = ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, treeId);
			List list = queryObject.list();
			if(list!=null && list.size()>0) {
				frameTree = (FrameTree) list.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
//			session.close();
			return frameTree;
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	private FrameUser findByUserId(java.lang.Long userId) {
		FrameUser frameUser = null;
		Session session = getSession();
		try {
			session.clear();
			String queryString = "from com.njmd.pojo.FrameUser as model where model.userId = ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, userId);
			List list = queryObject.list();
			if(list!=null && list.size()>0) {
				frameUser = (FrameUser) list.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
//			throw re;
		} finally {
//			session.close();
			return frameUser;
		}
	}

	public static void main(String[] args) {
		UploadForm uploadForm = new FrameUploadDAO().uploadDetail(new Long(87));
		System.out.println(uploadForm.getUserId());
		System.out.println(uploadForm.getEditId());
		System.out.println(uploadForm.getUploadName());
		System.out.println(uploadForm.getPlayPath());
		System.out.println(uploadForm.getShowPath());
		System.out.println(uploadForm.getFileCreatetime());
		System.out.println(DateUtils.getChar14());
		System.out.println(uploadForm.getFileState());
		System.out.println(uploadForm.getTree2Id());
		System.out.println(uploadForm.getTree1Id());
		System.out.println(uploadForm.getFileRemark());
		System.out.println(uploadForm.getIpAddr());
		System.out.println(uploadForm.getFileSavePath());
		System.out.println(uploadForm.getFlvPath());
		System.out.println(uploadForm.getTreeName());
		System.out.println(uploadForm.getUserName());
		System.out.println(uploadForm.getEditName());
	}
}