package com.manager.pub.util;

import java.util.List;

/**
 * @author dudujava
 * 
 */
public class CopyOfAVItoIMAGE {

	/**
	 * @param ffmpegpath
	 * @param videofilepath
	 * @param imgfilepath
	 * @return
	 */
	public static boolean makeImgbyvideo(String ffmpegpath,
			String videofilepath, String imgfilepath) {
		System.out.println(videofilepath + "->" + imgfilepath);
		List<String> commend = new java.util.ArrayList<String>();
		commend.add(ffmpegpath);
		commend.add("-i");
		commend.add(videofilepath);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("1.5");
		commend.add("-t");
		commend.add("0.001");
		commend.add("-s");
		commend.add("320x240");
		commend.add(imgfilepath);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
		}
	}

	/**
	 * @param ffmpegpath
	 * @param videofilepath
	 * @param flvfilepath
	 * @return
	 */
	public static void makeFlashbyvideo(String ffmpegpath,
			String videofilepath, String flvfilepath) {
		System.out.println(videofilepath + "->" + flvfilepath);
		List<String> commend = new java.util.ArrayList<String>();
//		commend.add("D:\\FreeTime\\FormatFactory\\FFModules\\ffmpeg");
		commend.add(ffmpegpath);
		commend.add("-i");
		commend.add(videofilepath);
		commend.add("-y");
		commend.add("-b");
		commend.add("1500");
		commend.add("-qscale");
		commend.add("4");
		//commend.add("8");
		commend.add(flvfilepath);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		makeFlashbyvideo("d:\\tt\\1.avi","d:\\tt\\1.avi","d:\\tt\\1.flv");
	}
}
