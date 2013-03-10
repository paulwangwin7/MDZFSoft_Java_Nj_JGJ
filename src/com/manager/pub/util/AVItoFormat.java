package com.manager.pub.util;

import java.util.List;

/**
 * @author dudujava
 * 
 */
public class AVItoFormat {

	/**
	 * @param ffmpegpath
	 * @param videofilepath
	 * @param imgfilepath
	 * @return
	 */
	public static boolean makeImgbyMP4(String ffmpegpath,
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
		commend.add("1");
		commend.add("-qscale");
		commend.add("1");
		commend.add("-s");
		commend.add("640*360");
		commend.add("-vframes");
		commend.add("1");
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
//		commend.add("-ss");
//		commend.add("8");
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
			commend.add(ffmpegpath);
			commend.add("-i");
			commend.add(videofilepath);
			commend.add("-sameq");
			commend.add("-ar");
			commend.add("44100");
			commend.add(flvfilepath);
			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commend);
				builder.start();
				System.out.println("over");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * @param ffmpegpath
	 * @param videofilepath
	 * @param flvfilepath
	 * @return
	 */
	public static void makeFlashbyMP4(String ffmpegpath,
			String videofilepath, String flvfilepath) {
			System.out.println(videofilepath + "->" + flvfilepath);
			List<String> commend = new java.util.ArrayList<String>();
			commend.add(ffmpegpath);
			commend.add("-i");
			commend.add(videofilepath);
			commend.add("-qscale");
			commend.add("10");
			commend.add("-ar");
			commend.add("44100");
			commend.add("-s");
			commend.add("640*360");
			commend.add(flvfilepath);
			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.command(commend);
				builder.start();
				System.out.println("over");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
//	ffmpeg -i D:\TrafficSysFiles\1\102\0218620121203100244.mp4 -sameq -ar 44100 -s 640*360 D:\TrafficSysFiles\1\102\0218620121203100244.flv
////	ffmpeg.exe -i D:\TrafficSysFiles\1\102\0218620121203100244.mp4 -y -f image2 -t 0.001 -s 352*240 D:\TrafficSysFiles\1\102\0218620121203100244.jpg
////	ffmpeg -i D:\TrafficSysFiles\1\102\0218620121203100244.mp4 D:\TrafficSysFiles\1\102\0218620121203100244.flv
//	ffmpeg -i D:\TrafficSysFiles\1\102\0218620121203100244.mp4 -ab 1048k -ar 44100 -s 640*360 D:\TrafficSysFiles\1\102\0218620121203100244.flv
//	ffmpeg -i D:\TrafficSysFiles\1\102\0218620121203100244.mp4 -y -f image2 -ss 1 -ar 44100 -t 0.001 -s 320*180 D:\TrafficSysFiles\1\102\0218620121203100244.jpg
//	ffmpeg -i D:\TrafficSysFiles\1\102\0218620121203100244.mp4 -y -f image2 -ss 1 -t 0.001 -ab 12200 -ar 8000 -s 320*180 D:\TrafficSysFiles\1\102\0218620121203100244.jpg
	public static void main(String[] args) {
//		makeImgbyvideo("D:\\ffmpeg\\bin\\ffmpeg","D:/TrafficSysFiles/1/102/0218620121203100244.mp4","D:/TrafficSysFiles/1/102/0218620121203100244.jpg");
//		makeFlashbyvideo("D:\\ffmpeg\\bin\\ffmpeg","D:/TrafficSysFiles/1/102/0218620121203100244.mp4","D:/TrafficSysFiles/1/102/0218620121203100244.flv");
//		makeImgbyvideo("D:\\ffmpeg\\bin\\ffmpeg","D:/TrafficSysFiles/1/102/20120016_171224_185.mp4","D:/TrafficSysFiles/1/102/20120016_171224_185.jpg");
//		makeFlashbyvideo("D:\\ffmpeg\\Libs\\ffmpeg","D:/TrafficSysFiles/1/102/0218620121203100244.mp4","D:/TrafficSysFiles/1/102/0218620121203100244.flv");
//		makeFlashbyvideo("D:\\ffmpeg\\bin\\ffmpeg","D:/TrafficSysFiles/1/102/20120016_171224_185.avi","D:/TrafficSysFiles/1/102/20120016_171224_185.mp4");
//		makeImgbyMP4("D:\\ffmpeg\\bin\\ffmpeg","D:/TrafficSysFiles/1/107/20121109_190907_997.mp4","D:/TrafficSysFiles/1/107/20121109_190907_997.jpg");
		makeFlashbyMP4("D:\\ffmpeg\\bin\\ffmpeg","D:/TrafficSysFiles/1/107/20121109_191928_534.mp4","D:/TrafficSysFiles/1/107/20121109_191928_534.flv");
	}
}
