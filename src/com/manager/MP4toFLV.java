package com.manager;

import java.util.List;

public class MP4toFLV {
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
	public static void main(String[] args) {
		makeFlashbyMP4("D:\\ffmpeg\\bin\\ffmpeg","D:/20121109_205138_87.mp4","D:/20121109_205138_87.flv");
	}
}
