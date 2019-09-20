package com.huxin.sboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huxin.sboot.bean.PageBean;
import com.huxin.sboot.bean.User;
import com.huxin.sboot.dao.IUserMapper;
import com.huxin.sboot.service.IUserServcie;
import com.huxin.sboot.util.ExcelUtil;



@Service
public class UserServiceImpl implements IUserServcie {
	
	Logger logger=Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserMapper userMapper;
	
	public int save(User user) throws Exception {
		int k=0;
		if(StringUtils.isNotEmpty(user.getId())){
			//�޸�
			k=userMapper.update(user);
		}else{
			k=userMapper.add(user);
		}
		return k;
	}

	public int del(String id) throws Exception {
		return userMapper.del(id);
	}

	public User getObjById(String id) throws Exception {
		
		return userMapper.getObjById(id);
	}

	public List<User> getAll(User user) throws Exception {
		
		return userMapper.getAll(user);
	}
	
	public String getAllUser(PageBean pagebean) throws Exception {
		//分页插件
		Page<Object> page=PageHelper.startPage(pagebean.getPageNumber(),pagebean.getPagesize());
		List<User> list=userMapper.getAllUser(pagebean);
		PageInfo info = new PageInfo(list);
		logger.info("总记录=="+info.getTotal()+",总页数=="+info.getPages()+","
				+ "当前第"+info.getPageNum()+",每页记录数=="+info.getPageSize());
		//放到分页组件中
		Map<String,Object> jsonmap=new HashMap<String,Object>();
		jsonmap.put("total", info.getTotal());
		jsonmap.put("rows", list);
		String json=JSONObject.toJSONString(jsonmap);
		return json;
	}

	@Override
	public int saveOrUpdateUser(User user) throws Exception {
		if(StringUtils.isNotEmpty(user.getId())){
			//修改
			return userMapper.update(user);
		}else{
			return userMapper.add(user);
		}
	}

	public  HSSFWorkbook exportExcel(Map<String,Object> map) {
		// 定义表头
		String[] headers = { "用户名", "邮件", "性别", "QQ", "微信", "注册日期" };
		// 获取数据集合
		HSSFWorkbook workbook=null;
		try {
			List<User> list = userMapper.queryAllUser(map);
			String[][] values = new String[list.size()][headers.length];
			for (int i = 0; i < list.size(); i++) {
				User u = list.get(i);
				values[i][0] = u.getName();
				values[i][1] = u.getEmail();
				values[i][2] = u.getSex();
				values[i][3] = u.getQq();
				values[i][4] = u.getWeixin();
				values[i][5] = u.getRegtime();
			}
			workbook = ExcelUtil.getHSSFWorkbook("用户管理", headers, values, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}
}
