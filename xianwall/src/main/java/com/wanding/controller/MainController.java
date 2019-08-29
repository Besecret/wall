package com.wanding.controller;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanding.model.UserInfo;
import com.wanding.service.UserInfoCheckService;
import com.wanding.service.UserInfoService;
import com.wanding.util.JsonResponseGenerator;
import com.wanding.util.MD5Util;

@Controller
@RequestMapping(value = "/main")
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserInfoCheckService userInfoCheckService;
	
	
	@RequestMapping("/login")
	@ResponseBody
	public Object login(String username,String password) {
		try {
			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			 return JsonResponseGenerator.fail("用户名或密码不能为空！") ;	
			}
			UserInfo  user = userInfoService.findUserByUsername(username);
			if(user==null) {
				 return JsonResponseGenerator.fail("用户不存在！") ;	
			}
			if(user.getPassword().equals(MD5Util.md5(password))) {
				 return JsonResponseGenerator.success("登录成功！");
			}
			  return JsonResponseGenerator.fail("密码不匹配！") ;
	    }catch(Exception e) {
		   LOGGER.error("regist error", e);
		   return JsonResponseGenerator.success("登录失败，请联系管理员！");
	   }
	}
	
	@RequestMapping("/regist")
	@ResponseBody
	public Object regist(String username,String password) {
		try {
			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			 return JsonResponseGenerator.fail("用户名或密码不能为空！") ;	
			}
			UserInfo  user = userInfoService.findUserByUsername(username);
			if(user!=null) {
				 return JsonResponseGenerator.fail("用户已存在！") ;	
			}
			user = new UserInfo();
			user.setUserrole("admin");
			user.setUsername(username);
			user.setPassword(MD5Util.md5(password));
			userInfoService.addUserInfo(user);
		     return JsonResponseGenerator.success("注册成功！");
	   }catch(Exception e) {
		   LOGGER.error("regist error", e);
		   return JsonResponseGenerator.success("注册失败，请联系管理员！");
	   }
	}
	
	@RequestMapping("/modifyPwd")
	@ResponseBody
	public Object modifyPwd(String username,String newPassword,String oldPassword) {
		try {
			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(newPassword)|| StringUtils.isEmpty(oldPassword)) {
			 return JsonResponseGenerator.fail("用户名或密码不能为空！") ;	
			}
			UserInfo  user = userInfoService.findUserByUsername(username);
			if(user==null) {
				 return JsonResponseGenerator.fail("用户不存在！") ;	
			}else if (!user.getPassword().equalsIgnoreCase(MD5Util.md5(oldPassword))){
				 return JsonResponseGenerator.fail("旧密码输入不正确！") ;	
			}
			user.setPassword(MD5Util.md5(newPassword));
			userInfoService.modifyPwd(user);
		     return JsonResponseGenerator.success("修改成功！");
	   }catch(Exception e) {
		   LOGGER.error("regist error", e);
		   return JsonResponseGenerator.success("修改失败，请联系管理员！");
	   }
	}
	
	
	
	
	
	

}

