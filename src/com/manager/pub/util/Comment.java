package com.manager.pub.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public class Comment {

	public static String basePath(HttpServletRequest request)
	{
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/";
	}

	/**
	 * 关闭数据库连接
	 * @param conn
	 * @param preparedStatement
	 * @param rs
	 */
	public static void closeConnection(Connection conn, PreparedStatement preparedStatement, ResultSet rs)
	{
		try
		{
			if(rs!=null)
			{
				try
				{
					rs.close();
				}
				catch(Exception e)
				{
					
				}
			}
			if(preparedStatement!=null)
			{
				try
				{
					preparedStatement.close();
				}
				catch(Exception e)
				{
					
				}
			}
			if(conn!=null)
			{
				try
				{
					conn.close();
				}
				catch(Exception e)
				{
					
				}
			}
		}
		catch(Exception ex)
		{
			try	{rs.close();}catch(Exception e){}
			try	{preparedStatement.close();}catch(Exception e){}
			try	{conn.close();}catch(Exception e){}
		}
	}
}
