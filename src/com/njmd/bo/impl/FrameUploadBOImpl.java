package com.njmd.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.manager.pub.bean.Page;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.TreeForm;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.bean.UserForm;
import com.manager.pub.util.DateUtils;
import com.manager.pub.util.FileUtils;
import com.njmd.bo.FrameUploadBO;
import com.njmd.dao.FrameUploadDAO;
import com.njmd.pojo.FrameUpload;
import com.njmd.pojo.FrameUser;

public class FrameUploadBOImpl implements FrameUploadBO {
	private FrameUploadDAO frameUploadDAO;

	public Page mineUploadList(String treeId, String parentTreeId,
			String uploadUserId, Page page) {
		// TODO Auto-generated method stub
		return frameUploadDAO.mineUploadList(treeId, parentTreeId, uploadUserId, page);
	}

	public int uploadDel(List<UploadForm> uploadList, boolean deleteStats) {
		// TODO Auto-generated method stub
		for(UploadForm uploadForm: uploadList) {
			FrameUpload frameUpload = frameUploadDAO.findById(uploadForm.getUploadId());
			frameUpload.setFileState("F");//删除过期
			new FileUtils().deleteFile(SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath());
			frameUploadDAO.attachDirty(frameUpload);
		}
		return 0;
	}

	public UploadForm uploadDetail(Long uploadId) {
		// TODO Auto-generated method stub
		return frameUploadDAO.uploadDetail(uploadId);
	}

	public Page uploadManagerQuery(String uploadName, String treeId,
			String beginTime, String endTime, String createTimeBegin, String createTimeEnd, 
			String uploadUserId, String fileCreateUserId, String fileStats, String fileRemark, Page page) {
		// TODO Auto-generated method stub
		return frameUploadDAO.uploadManagerQuery(uploadName, treeId, beginTime, endTime, createTimeBegin, createTimeEnd, uploadUserId, fileCreateUserId, fileStats, fileRemark, page);
	}

	public Page uploadListByTree(String uploadName, String treeId,
			String parentTreeId, String beginTime, String endTime,
			String uploadUserId, String fileCreateUserId, String fileStats,
			String fileRemark, Page page) {
		// TODO Auto-generated method stub
		return frameUploadDAO.uploadListByTree(uploadName, treeId, parentTreeId, beginTime, endTime, uploadUserId, fileCreateUserId, fileStats, fileRemark, page);
	}

	public Page uploadListByAdmin(String uploadName, String treeId,
			String parentTreeId, String beginTime, String endTime,
			String uploadUserId, String fileCreateUserId, String fileStats,
			String fileRemark, Page page) {
		// TODO Auto-generated method stub
		return frameUploadDAO.uploadListByAdmin(uploadName, treeId, parentTreeId, beginTime, endTime, uploadUserId, fileCreateUserId, fileStats, fileRemark, page);
	}

	public int uploadRemark(Long uploadId, String remark) {
		// TODO Auto-generated method stub
		FrameUpload frameUpload = frameUploadDAO.findById(uploadId);
		frameUpload.setFileRemark(remark);
		return frameUploadDAO.attachDirty(frameUpload);
	}

	public int uploadSave(UploadForm uploadForm) {
		int saveResult;//
		// TODO Auto-generated method stub
		FrameUpload frameUpload = new FrameUpload();
		frameUpload.setUserId(uploadForm.getUserId());
		frameUpload.setEditId(uploadForm.getEditId());
		frameUpload.setUploadName(uploadForm.getUploadName());
		frameUpload.setPlayPath(uploadForm.getPlayPath());
		frameUpload.setShowPath(uploadForm.getShowPath());
		frameUpload.setFileCreatetime(uploadForm.getFileCreatetime());
		frameUpload.setFileStats("0");
		frameUpload.setUploadTime(DateUtils.getChar14());
		frameUpload.setFileState(uploadForm.getFileState());
		frameUpload.setTree2Id(uploadForm.getTree2Id());
		frameUpload.setTree1Id(uploadForm.getTree1Id());
		frameUpload.setFileRemark(uploadForm.getFileRemark());
		frameUpload.setIpAddr(uploadForm.getIpAddr());
		frameUpload.setRealPath(uploadForm.getFileSavePath());
		frameUpload.setFlvPath(uploadForm.getFlvPath());
		saveResult = frameUploadDAO.save(frameUpload);
		return saveResult;
	}

	public int uploadStats(Long uploadId, String fileStats) {
		// TODO Auto-generated method stub
		FrameUpload frameUpload = frameUploadDAO.findById(uploadId);
		frameUpload.setFileStats(fileStats);
		return frameUploadDAO.attachDirty(frameUpload);
	}

	@SuppressWarnings("unchecked")
	public List uploadStatsList(String[] uploadIdArr) {
		// TODO Auto-generated method stub
		List list = null;//list.get(0)-重要性文件list； list.get(1)-非重要性文件list；
		List<UploadForm> statsList = null;
		List<UploadForm> unStatsList = null;
		for(String uploadId: uploadIdArr) {
			UploadForm uploadForm = frameUploadDAO.uploadDetail(new Long(uploadId));
			if (uploadForm.getFileStats().equals("1")) {//数据库中该字段 文件上传重要性 0-普通；1-重要
				if(statsList==null) {
					statsList = new ArrayList<UploadForm>();
				}
				statsList.add(uploadForm);
			} else {
				if(unStatsList==null) {
					unStatsList = new ArrayList<UploadForm>();
				}
				unStatsList.add(uploadForm);
			}
		}
		list = new ArrayList();
		list.add(statsList);
		list.add(unStatsList);
		return list;
	}

	public List<UploadForm> expiredSysDekList(int expiredDays) {
		// TODO Auto-generated method stub
		return frameUploadDAO.expiredSysDekList(expiredDays);
	}

	public List<UploadForm> expiredUploadAllList(String expired) {
		// TODO Auto-generated method stub
		return frameUploadDAO.expiredUploadAllList(expired);
	}

	public void setFrameUploadDAO(FrameUploadDAO frameUploadDAO) {
		this.frameUploadDAO = frameUploadDAO;
	}

	@Override
	public Map<String, Integer> getStatistics(int queryType,int userType,
			List<Object> yList, String year, String month, String startDate,
			String endDate,String dimension) throws Exception{
		if(null==yList || yList.size()==0)
			return new HashMap<String,Integer>();
		
		Map<String,Integer> res=new HashMap<String,Integer>();
		StringBuffer sb=new StringBuffer();
		StringBuffer sb1=new StringBuffer();
		
		String dimensionSql="";
		if(dimension!=null){
			if(dimension.equals("1")){
				dimensionSql=" and lower(t1.upload_name) like '%.mp4' ";
			}else if(dimension.equals("2")){
				dimensionSql=" and lower(t1.upload_name) like '%.wav' ";
			}else if(dimension.equals("3")){
				dimensionSql=" and lower(t1.upload_name) like '%.jpg' ";
			}
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat parser=new SimpleDateFormat("yyyyMMdd");
		
		Map<String,String> startTimes=new HashMap<String,String>();
		Map<String,String> endTimes=new HashMap<String,String>();
		
		if(queryType==1){
			Calendar begin=Calendar.getInstance();
			begin.setTime(format.parse(startDate));
			
			Calendar end=Calendar.getInstance(); 
			end.setTime(format.parse(endDate));
			end.add(Calendar.MONTH, -11);
			
			for (int i = 1; i <= 12; i++) {
				String tmp=year+(i<10?"0"+i:i);
				startTimes.put(tmp, parser.format(begin.getTime()));
				endTimes.put(tmp, parser.format(end.getTime()));
				
				begin.add(Calendar.MONTH, 1);
				end.add(Calendar.MONTH, 1);
			}
		}
		
		int c=0;
		int u=0;
		for(Object item:yList){
			if(queryType==1){
				//按年统计
				
				if(item instanceof TreeForm){
					for(Map.Entry<String,String> entry:startTimes.entrySet()){
						if( c!=0)
							sb.append(" union ");

						if(userType==1){
							sb.append("select 'C_").append(((TreeForm)item).getTreeId()).append("_").append(entry.getKey()).append("' a , count(*) b from frame_upload t1")
								.append(" left join frame_user t3 on t1.edit_id=t3.user_id ")
								.append(" left join frame_tree t2 on t3.tree_id=t2.tree_id ")
								.append(" where substr(t1.upload_time,0,8) >= '").append(entry.getValue()).append("' ")
								.append(" and substr(t1.upload_time,0,8) <= '").append(endTimes.get(entry.getKey())).append("' and ")
								.append(" t2.path like '").append(((TreeForm)item).getPath()).append("%' ")
								.append(dimensionSql);
						}else{
							sb.append("select 'C_").append(((TreeForm)item).getTreeId()).append("_").append(entry.getKey()).append("' a , count(*) b from frame_upload t1")
								.append(" left join frame_tree t2 on t1.tree2_id=t2.tree_id ")
								.append(" where substr(t1.upload_time,0,8) >= '").append(entry.getValue()).append("' ")
								.append(" and substr(t1.upload_time,0,8) <= '").append(endTimes.get(entry.getKey())).append("' and ")
								.append(" t2.path like '").append(((TreeForm)item).getPath()).append("%' ")
								.append(dimensionSql);
						}
						c++;
					}
				}else{
					u++;
				}
			}else if(queryType==2 || queryType==3){
				
				//按日统计
				if(item instanceof TreeForm){
					if( c!=0)
						sb.append(" union ");
					
					if(userType==1){
						sb.append("select 'C_").append(((TreeForm)item).getTreeId()).append("_'||substr(t1.upload_time,0,8) a,count(*) b from frame_upload t1")
							.append(" left join frame_user t3 on t1.edit_id=t3.user_id ")
							.append(" left join frame_tree t2 on t3.tree_id=t2.tree_id ")
							.append(" where substr(t1.upload_time,0,8) >= '").append(startDate).append("' ")
							.append(" and substr(t1.upload_time,0,8) <= '").append(endDate).append("' and ")
							.append(" t2.path like '").append(((TreeForm)item).getPath()).append("%' ")
							.append(dimensionSql)
							.append(" group by substr(t1.upload_time,0,8) "); 
					}else{
						sb.append("select 'C_").append(((TreeForm)item).getTreeId()).append("_'||substr(t1.upload_time,0,8) a,count(*) b from frame_upload t1")
							.append(" left join frame_tree t2 on t1.tree2_id=t2.tree_id ") 
							.append(" where substr(t1.upload_time,0,8) >= '").append(startDate).append("' ")
							.append(" and substr(t1.upload_time,0,8) <= '").append(endDate).append("' and ")
							.append(" t2.path like '").append(((TreeForm)item).getPath()).append("%' ")
							.append(dimensionSql)
							.append(" group by substr(t1.upload_time,0,8) "); 
					}
					c++;
				}else{
					u++;
				}
			}
			
		}
		
		if(c!=0){
			Session session = frameUploadDAO.getSession();
			session.clear();
			SQLQuery query = session.createSQLQuery(sb.toString());
			query.addScalar("a",Hibernate.STRING);
			query.addScalar("b",Hibernate.INTEGER);
			List<Object[]> list = (List<Object[]>) query.list();
			session.close();
			
			for (Object[] objs : list){
				res.put(""+objs[0], objs[1]==null?0:Integer.parseInt(objs[1].toString()));
			}
		}
		
		c=0;
		if(u!=0){
			String js="user_id";
			if(userType==1){
				js="edit_id";
			}
			
			if(queryType==1){
				//按年统计
				for(Map.Entry<String,String> entry:startTimes.entrySet()){
					if( c!=0)
						sb1.append(" union ");
					
					
						sb1.append("select 'U_'||").append(js).append("||'_").append(entry.getKey()).append("' a,count(*) b from frame_upload t1")
						.append(" where substr(t1.upload_time,0,8) >= '").append(entry.getValue()).append("' ")
						.append(" and substr(t1.upload_time,0,8) <= '").append(endTimes.get(entry.getKey())).append("' and ")
						.append(js).append(" in (");
					
					for(Object item:yList){
						if(item instanceof UserForm){
							sb1.append(((UserForm)item).getUserId()).append(",");
						}
					}
					
					sb1.deleteCharAt(sb1.length()-1);
					sb1.append(" ) ").append(dimensionSql).append(" group by  ").append(js).append(" ");
					
					c++;
				}
				
			}else if(queryType ==2 || queryType==3){
				//按日统计
				sb1.append("select 'U_'||").append(js).append("||'_'||substr(upload_time,0,8) a ,count(*) b from frame_upload t1")
				.append(" where substr(upload_time,0,8)>= '").append(startDate)
				.append("' and substr(upload_time,0,8)<='").append(endDate)
				.append("' and ").append(js)
				.append(" in (");
				
				for(Object item:yList){
					if(item instanceof UserForm){
						sb1.append(((UserForm)item).getUserId()).append(",");
					}
				}
				
				sb1.deleteCharAt(sb1.length()-1);
				sb1.append(" ) ").append(dimensionSql).append(" group by ").append(js).append(", ");
				sb1.append("substr(upload_time,0,8) ");
			}
			
			Session session = frameUploadDAO.getSession();
			session.clear();
			SQLQuery query = session.createSQLQuery(sb1.toString());
			query.addScalar("a",Hibernate.STRING);
			query.addScalar("b",Hibernate.INTEGER);
			List<Object[]> list = (List<Object[]>) query.list();
			session.close();
			
			for (Object[] objs : list){
				res.put(""+objs[0], objs[1]==null?0:Integer.parseInt(objs[1].toString()));
			}
		}
		
		return res;
	}

	@Override
	public List<String> todayUploadUsers(TreeForm treeForm) {
		if(null==treeForm)
			return new ArrayList<String>();
		
		
		
		StringBuffer sb=new StringBuffer();
		sb.append("select distinct edit_id from frame_upload t1")
			.append(" left join frame_tree t2 on t1.tree2_id=t2.tree_id ")
			.append(" where t1.upload_time like '").append(new SimpleDateFormat("yyyyMMdd").format(new Date())).append("%' and ")
			.append(" t2.path like '").append(treeForm.getPath()).append("%' ");
		
		Session session = frameUploadDAO.getSession();
		session.clear();
		Query query = session.createSQLQuery(sb.toString());
		List<String> list = (List<String>) query.list();
		session.close();
		
		List<String> res=new ArrayList<String>();
		for (Object obj : list){
			res.add(""+obj);
		}
		
		return res;
	}
}
