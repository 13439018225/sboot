package com.huxin.sboot.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageBean implements Serializable {
	
	private static final long serialVersionUID = -6132563685243414374L;

	private int pageNumber;//当前第几页
	
	private int pagesize;//每页记录数
	
	private int StartRow;
	
	private int endRow;
	
	private Map<String,Object> map;//封装参数
	
	private List<?> list=new ArrayList<>();
	
	private int rowCount;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber==0?1:pageNumber;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize =pagesize;
	}
    
	public int getStartRow() {
		this.StartRow=(this.pageNumber-1)*this.pagesize+1;
		return StartRow;
	}


	public int getEndRow() {
		this.endRow=this.pageNumber*this.pagesize;
		return this.endRow;
	}

	//设置分页统一方法
	public void setPagein(int pageNumber,int pagesize){
		setPageNumber(pageNumber);
		setPagesize(pagesize);
		getStartRow();
		getEndRow();
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}


}
