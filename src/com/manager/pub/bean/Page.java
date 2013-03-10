package com.manager.pub.bean;

public class Page {
	private int dbLine;//每页多少行数据
	private int pageCute;//当前页
	private int pageMax;//总页数
	private int pageLine;//每页多少小页 写死10小页
	private int pageLineCute;//当前第几大页
	private int pageLineMax;//总共几大页

	private int total;//总记录数
	private Object listObject;//分页数据对象

	public Page(){};

	public Page(int pageCute, int dbLine) {
		this.pageCute = pageCute;
		this.dbLine = dbLine;
	}

	public int getDbLine() {
		return dbLine;
	}
	public void setDbLine(int dbLine) {
		this.dbLine = dbLine;
	}
	public int getPageMax() {
		pageMax = getTotal()/getDbLine() + (getTotal()%getDbLine()>0?1:0);
		return pageMax;
	}
//	public void setPageMax(int pageMax) {
//		this.pageMax = pageMax;
//	}
	public int getPageLine() {
		pageLine = 10;
		return pageLine;
	}
//	public void setPageLine(int pageLine) {
//		this.pageLine = pageLine;
//	}
	public int getPageLineCute() {
		pageLineCute = getPageCute()/getPageLine() + (getPageCute()%getPageLine()>0?1:0);
		return pageLineCute;
	}
//	public void setPageLineCute(int pageLineCute) {
//		this.pageLineCute = pageLineCute;
//	}
	public int getPageLineMax() {
		pageLineMax = getPageMax()/getPageLine() + (getPageMax()%getPageLine()>0?1:0);
		return pageLineMax;
	}
//	public void setPageLineMax(int pageLineMax) {
//		this.pageLineMax = pageLineMax;
//	}
	public int getPageCute() {
		if(pageCute<1)
		{
			pageCute = 1;
		}
		if(pageCute>getPageMax())
		{
			pageCute = getPageMax();
		}
		return pageCute;
	}
	public void setPageCute(int pageCute) {
		this.pageCute = pageCute;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Object getListObject() {
		return listObject;
	}
	public void setListObject(Object listObject) {
		this.listObject = listObject;
	}
}
