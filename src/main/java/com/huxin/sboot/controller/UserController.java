package com.huxin.sboot.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.huxin.sboot.bean.PageBean;
import com.huxin.sboot.bean.User;
import com.huxin.sboot.service.IUserServcie;
@Controller
public class UserController {
	
	@Autowired
	private IUserServcie userService;

	@RequestMapping("/topage")
	public String topage(){
		return "form";
	}

	@RequestMapping(value={"/insert","add"})
	public String insert(@RequestParam("name") String name,Model model){
		model.addAttribute("name",name);
		return "index";
	}
	
	@RequestMapping(value={"save"})
	public String save(User user,Model model){
		int k=0;
		try {
			k=userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(k>0){
			return "redirect:query";
		}else{
			return "form";
		}
	}
	
	@RequestMapping("/query")
	public String query(User user,Map<String,Object> map){
		try {
			List<User> list=userService.getAll(user);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	@RequestMapping("/delete")
	public String del(String id){
		try {
			userService.del(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:query";
	}
	
	@RequestMapping("/getAllUser")
	@ResponseBody
	public String getAllUser(HttpServletRequest request){
		//接收分页参数
		String currentpage=request.getParameter("page");//当前第几页
		String rows=request.getParameter("rows");//每页记录数
		//查询条件
		String qname=request.getParameter("qname");
		//自定义分页对象，用于封装 当前页数 与 每页记录数
		PageBean pagebean=new PageBean();
		pagebean.setPageNumber(Integer.parseInt(currentpage));
		pagebean.setPagesize(Integer.parseInt(rows));
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", qname);
		pagebean.setMap(map);
		String json="";
		try {
			json=userService.getAllUser(pagebean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	@RequestMapping("/saveOrUpdateUser")
	@ResponseBody
	public String saveOrUpdateUser(User user,HttpServletRequest req){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		MultipartFile file = multipartRequest.getFile("userfile");
		int n=0;
		try {
			//上传资料
			if(file!=null){
				String filepath="E:\\upload";
				File f=new File(filepath);
				if(!f.exists()){
					f.mkdir();
				}
				//获取文件名称
				String filename=file.getOriginalFilename();
				//将文件上传到指定命令
				file.transferTo(new File(f,filename));
				user.setFilepath(filepath+File.separator+filename);
			}
			//保存或者更新数据
			n=userService.saveOrUpdateUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return String.valueOf(n);
	}
	
	@RequestMapping("/downloadFile")
	public void downloadFile(String id,HttpServletResponse response){
		try {
			User user=userService.getObjById(id);
			String filepatname=user.getFilepath();
			String filename=filepatname.substring(filepatname.lastIndexOf("\\")+1,filepatname.length());
			File file=new File(filepatname);
			if(file==null || !file.exists()){
				return;
			}
			filename = new String(filename.getBytes(),"ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ filename);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream out = response.getOutputStream();
            out.write(FileUtils.readFileToByteArray(file));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	@RequestMapping("/getObjectById")
	@ResponseBody
	public String getObjectById(String id){
		String json="";
		try {
			User user=userService.getObjById(id);
			json=JSONObject.toJSONString(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(String id){
		int k=0;
		try {
			k=userService.del(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(k);
	}
	
	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(String name,HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map.put("name",java.net.URLDecoder.decode(name,"UTF-8"));
			HSSFWorkbook wb=userService.exportExcel(map);
			String fileName = "用户信息表"+System.currentTimeMillis()+".xls";
            fileName = new String(fileName.getBytes(),"ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
