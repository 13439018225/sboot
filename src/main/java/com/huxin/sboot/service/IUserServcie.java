package com.huxin.sboot.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.huxin.sboot.bean.PageBean;
import com.huxin.sboot.bean.User;

public interface IUserServcie {

	public int save(User user) throws Exception;
	
	public int del(String id)throws Exception;
	
	public User getObjById(String id)throws Exception;
	
	public List<User> getAll(User user)throws Exception;
	
	public String getAllUser(PageBean pagebean)throws Exception; 
	
	public int saveOrUpdateUser(User user)throws Exception;
	
	public HSSFWorkbook exportExcel(Map<String,Object> map)throws Exception;
	
}
