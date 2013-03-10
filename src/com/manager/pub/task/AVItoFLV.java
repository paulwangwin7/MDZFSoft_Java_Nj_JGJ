package com.manager.pub.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.TimerTask;
import javax.servlet.ServletContext;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.manager.admin.dao.SysDAO;
import com.manager.pub.bean.SystemConfig;
import com.manager.pub.bean.UploadForm;
import com.manager.pub.util.AVItoFormat;
import com.manager.pub.util.Comment;
import com.manager.pub.util.DateUtils;

public class AVItoFLV extends Thread {
	protected static final Logger logger = Logger.getLogger(AVItoFLV.class);
	private static Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private static ResultSet rs = null;
	private BasicDataSource dataSource;

	public AVItoFLV() {
	}

	public void run() {
		System.out.println("===========avi to flv");
		while (!Thread.interrupted()){
			logger.info("开始执行指定任务 -- 文件转flv");
//			String urlStr = SystemConfig.getSystemConfig().getFileSavePath()+"/sysAction.do?method=flvFormatWork";
//			String retstr = HttpUtils.callRPC(urlStr);
//			System.out.println("retstr==="+retstr);
//			if(retstr.equals("0"))
//			{
//				logger.info("开始执行指定任务 -- 记录成功");
//			}
//			else
//			{
//				logger.info("开始执行指定任务 -- 记录失败");
//			}
			// 在这里编写自己的功能，例：
			// File file = new File("temp");
			// file.mkdir();
			// 启动tomcat，可以发现在tomcat根目录下，会自动创建temp文件夹

			// -------------------结束
			UploadForm uploadForm = null;
			try
			{
				conn = SysDAO.getBasicDataSource().getConnection();
				String selectSQL = "select * from frame_upload t where t.real_path = ? and t.file_state='C' and rownum<=1 order by t.upload_id";
				preparedStatement = conn.prepareStatement(selectSQL);
				System.out.println(SystemConfig.getSystemConfig().getFileSavePath());
				preparedStatement.setString(1, SystemConfig.getSystemConfig().getFileSavePath());
				rs = preparedStatement.executeQuery();
				while(rs.next())
				{
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
				}
				if(uploadForm!=null)
				{
					AVItoFormat aviToFormat = new AVItoFormat();
					logger.info(SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath()+" --> "+SystemConfig.getSystemConfig().getFileRoot()+(uploadForm.getPlayPath().replace(".avi", ".flv").replace(".mp4", ".flv")));
					if(uploadForm.getPlayPath().lastIndexOf(".avi")>0) {
						aviToFormat.makeFlashbyvideo(new SystemConfig().getSystemConfig().getFfmpegPath(), SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath(), SystemConfig.getSystemConfig().getFileRoot()+(uploadForm.getPlayPath().replace(".avi", ".flv").replace(".mp4", ".flv")));
					} else {
//						aviToFormat.makeFlashbyMP4(new SystemConfig().getSystemConfig().getFfmpegPath(), SystemConfig.getSystemConfig().getFileRoot()+uploadForm.getPlayPath(), SystemConfig.getSystemConfig().getFileRoot()+(uploadForm.getPlayPath().replace(".avi", ".flv").replace(".mp4", ".flv")));
					}
					uploadForm.setFlvPath(uploadForm.getPlayPath().replace(".avi", ".flv").replace(".mp4", ".flv"));
					
					conn.setAutoCommit(false);
					String updateSQL = "update frame_upload set FLV_PATH = ?, FILE_STATE = 'P' where UPLOAD_ID = ?";
					preparedStatement = conn.prepareStatement(updateSQL);
					preparedStatement.setString(1, uploadForm.getFlvPath());
					preparedStatement.setLong(2, uploadForm.getUploadId());
					preparedStatement.executeUpdate();
					conn.commit();
				}
				Thread.sleep(Long.parseLong(SystemConfig.getSystemConfig().getFfmpegSleep()));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				Comment.closeConnection(conn, preparedStatement, rs);
			}
			logger.info("指定任务执行结束");
		}
	}
}