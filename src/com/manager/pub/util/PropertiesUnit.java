package com.manager.pub.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class PropertiesUnit {
	private String filename; 

	 private Properties p;

	 private FileInputStream in;

	 private FileOutputStream out;

	 public PropertiesUnit(String filename) { 
	  this.filename = filename; 
	  File file = new File(filename); 
	  try { 
	   in = new FileInputStream(file); 
	   p = new Properties(); 
	   p.load(in); 
	   in.close(); 
	  } catch (FileNotFoundException e) { 
	   // TODO Auto-generated catch block 
	   System.err.println("配置文件config.properties找不到！"); 
	   e.printStackTrace(); 
	  } catch (IOException e) { 
	   // TODO Auto-generated catch block 
	   System.err.println("读取配置文件config.properties错误！"); 
	   e.printStackTrace(); 
	  } 
	 } 

	 public static String getConfigFile(HttpServlet hs) { 
	  return getConfigFile(hs, "config.properties"); 
	 } 

	 /** 
	  * @param hs 
	  * @param configFileName 
	  * @return configFile 
	  */ 
	 private static String getConfigFile(HttpServlet hs, String configFileName) { 
	  String configFile = ""; 
	  ServletContext sc = hs.getServletContext(); 
	  configFile = sc.getRealPath("/" + configFileName); 
	  if (configFile == null || configFile.equals("")) { 
	   configFile = "/" + configFileName; 
	  } 
	  // TODO Auto-generated method stub 
	  return configFile; 
	 } 

	 public void list() { 
	  p.list(System.out); 
	 } 

	 public String getValue(String itemName) { 
	  return p.getProperty(itemName); 
	 } 

	 public String getValue(String itemName, String defaultValue) { 
	  return p.getProperty(itemName, defaultValue); 
	 } 

	 public void setValue(String itemName, String value) { 
	  p.setProperty(itemName, value); 
	 } 

	 public void saveFile(String filename, String description) throws Exception { 
	  try { 
	   File f = new File(filename); 
	   out = new FileOutputStream(f); 
	   p.store(out, description); 
	   out.close(); 
	  } catch (IOException ex) { 
	   throw new Exception("无法保存指定的配置文件:" + filename); 
	  } 
	 } 
	  
	 public void saveFile(String filename) throws Exception{ 
	  saveFile(filename,""); 
	 } 
	  
	 public void saveFile() throws Exception{ 
	  if(filename.length()==0) 
	   throw new Exception("需指定保存的配置文件名"); 
	  saveFile(filename); 
	 } 
	  
	 public void deleteValue(String value){ 
	  p.remove(value); 
	 } 
	  
	 public static void main(String args[]){ 
	  String file="d:/workspaceMyEclipse/TrafficSys_new/WebRoot/WEB-INF/ApplicationResources.properties";
	  
	//  String file="D:\\eclipse\\workspace\\NewsTest\\WEB-INF\\config.properties"; 
	  PropertiesUnit pu=new PropertiesUnit(file); 
//	  pu.list(); 
	  System.out.println(pu.getValue("formatFactoryPath"));
	 } 


}
