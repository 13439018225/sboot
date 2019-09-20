package com.huxin.sboot.dao;

import java.util.List;
import java.util.Map;

import com.huxin.sboot.bean.PageBean;
import com.huxin.sboot.bean.User;

public interface IUserMapper {

    public int add(User user) throws Exception;
    
    public int update(User user) throws Exception;
	
	public int del(String id)throws Exception;
	
	public User getObjById(String id)throws Exception;
	
	public List<User> getAll(User user)throws Exception;
	
	public List<User> getAllUser(PageBean pagebean)throws Exception;
	
	public List<User> queryAllUser(Map<String,Object> map)throws Exception;
}
