package com.huxin.sboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huxin.sboot.service.IUserServcie;
@Controller
public class MainController {
	
	@Autowired
	private IUserServcie userService;

	@RequestMapping("/tomain")
	public String topage(){
		return "main";
	}
    
	@RequestMapping("/touserlist")
	public String touserlist(){
		return "userlist";
	}
	
}
