package com.manager.pub.util;

import java.io.File;

public class FileUtils {
	File dirFile = null;

	/**
	 * 
	 * @param filePath 文件全路径
	 * @return 0-成功；1-失败
	 */
	public int deleteFile(String filePath)
	{
		try
		{
			System.out.println(filePath);
			dirFile = new File(filePath);
			if(dirFile.isFile() && dirFile.exists()){    
				dirFile.delete();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Constants.ACTION_FAILED;
		}
		return Constants.ACTION_SUCCESS;
	}

	public int fileExists(String filePath) {
		int rst = Constants.ACTION_FAILED;
		try {
			File file = new File(filePath);
			rst = file.exists()?Constants.ACTION_SUCCESS:Constants.ACTION_FAILED;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			return rst;
		}
	}

	public static void main(String[] args)
	{
//		new FileUtils().deleteFile("c:/a.tt");
		System.out.println(new FileUtils().fileExists("d:/aaa/FLAT_20120612133833_11111250.0000000002.gz"));
	}
}
