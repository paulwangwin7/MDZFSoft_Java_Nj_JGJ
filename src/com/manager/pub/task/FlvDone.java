package com.manager.pub.task;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.manager.admin.dao.SysDAO;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.util.AVItoFormat;
import com.manager.pub.util.Comment;

public class FlvDone extends Thread {

	protected static final Logger logger = Logger.getLogger(FlvDone.class);
	private static Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private PreparedStatement preparedStatement2 = null;
	private static ResultSet rs = null;
	private BasicDataSource dataSource;

	public FlvDone() {
	}

	public void run() {
		System.out.println("===========flv done");
		while (!Thread.interrupted()){
			boolean batch = false;
			logger.info("开始执行指定任务 -- 文件转flv");
			UploadForm uploadForm = null;
			String selectSQL = "select * from frame_upload t where t.real_path = ? and t.file_state='P' order by t.upload_id";
			String updateSQL = "update frame_upload set FILE_STATE = 'A' where UPLOAD_ID = ?";
			try
			{
				conn = SysDAO.getBasicDataSource().getConnection();
				preparedStatement = conn.prepareStatement(selectSQL);
				System.out.println(SystemConfig.getSystemConfig().getFileSavePath());
				preparedStatement.setString(1, SystemConfig.getSystemConfig().getFileSavePath());
				rs = preparedStatement.executeQuery();
				preparedStatement2 = conn.prepareStatement(updateSQL);
				conn.setAutoCommit(false);
				while(rs.next())
				{
					batch = true;
					uploadForm = new UploadForm();
					uploadForm.setUploadId(rs.getLong("UPLOAD_ID"));
					uploadForm.setUserId(rs.getLong("USER_ID"));
					uploadForm.setEditId(rs.getLong("EDIT_ID"));
					uploadForm.setUploadName(rs.getString("UPLOAD_NAME"));
					uploadForm.setPlayPath(rs.getString("PLAY_PATH"));
					uploadForm.setShowPath(rs.getString("SHOW_PATH"));
					uploadForm.setUploadTime(rs.getString("UPLOAD_TIME"));
					uploadForm.setFileState(rs.getString("FILE_STATE"));
					uploadForm.setTree1Id(rs.getLong("TREE1_ID"));
					uploadForm.setTree2Id(rs.getLong("TREE2_ID"));
					uploadForm.setFileStats(rs.getString("FILE_STATS"));
					uploadForm.setFileRemark(rs.getString("FILE_REMARK"));
					uploadForm.setFileSavePath(rs.getString("REAL_PATH"));
					uploadForm.setFlvPath(rs.getString("FLV_PATH")==null?"":rs.getString("FLV_PATH"));
//					
					File file = new File(new SystemConfig().getSystemConfig().getFileRoot()+uploadForm.getFlvPath());
					if(file.exists()) {
						System.out.println(new SystemConfig().getSystemConfig().getFileRoot()+uploadForm.getFlvPath());
						preparedStatement2.setLong(1, uploadForm.getUploadId());
						preparedStatement2.addBatch();
					}
				}
				if(batch) {
					preparedStatement2.executeBatch();
				}
				conn.commit();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				try{if(preparedStatement2==null)preparedStatement2.close();}catch(Exception e){}
				Comment.closeConnection(conn, preparedStatement, rs);
				try {
					Thread.sleep(Long.parseLong(SystemConfig.getSystemConfig().getFfmpegSleep()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			logger.info("指定任务执行结束");
		}
	}
}
