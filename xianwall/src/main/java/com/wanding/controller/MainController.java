package com.wanding.controller;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wanding.model.UserInfo;
import com.wanding.service.UserInfoService;
import com.wanding.util.JsonResponse;
import com.wanding.util.MD5Util;

@Controller
@RequestMapping(value = "/main")
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/index")
	public String  index(String username,String password) {
		return "index";
	}
	
	
	@RequestMapping("/login")
	@ResponseBody
	public Object login(@RequestBody String param) {
		try {
			JSONObject json  = JSONObject.parseObject(param);
			String  username = json.getString("username");
			String  password = json.getString("password");
			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			 return JsonResponse.failure("用户名或密码不能为空！") ;	
			}
			UserInfo  user = userInfoService.findUserByUsername(username);
			if(user==null) {
				 return JsonResponse.failure("用户不存在！") ;	
			}
			if(user.getPassword().equals(MD5Util.md5(password))) {
				 return JsonResponse.success("登录成功！");
			}
			  return JsonResponse.failure("密码不匹配！") ;
	    }catch(Exception e) {
		   LOGGER.error("regist error", e);
		   return JsonResponse.success("登录失败，请联系管理员！");
	   }
	}
	
	@RequestMapping("/regist")
	@ResponseBody
	public Object regist(@RequestBody String param) {
		try {
			JSONObject json  = JSONObject.parseObject(param);
			String  username = json.getString("username");
			String  password = json.getString("password");
			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			 return JsonResponse.failure("用户名或密码不能为空！") ;	
			}
			UserInfo  user = userInfoService.findUserByUsername(username);
			if(user!=null) {
				 return JsonResponse.failure("用户已存在！") ;	
			}
			user = new UserInfo();
			user.setUserrole("admin");
			user.setUsername(username);
			user.setPassword(MD5Util.md5(password));
			userInfoService.addUserInfo(user);
		     return JsonResponse.success("注册成功！");
	   }catch(Exception e) {
		   LOGGER.error("regist error", e);
		   return JsonResponse.failure("注册失败，请联系管理员！");
	   }
	}
	
	@RequestMapping("/modifyPwd")
	@ResponseBody
	public Object modifyPwd(@RequestBody String param) {
		try {
			JSONObject json  = JSONObject.parseObject(param);
			String  username = json.getString("username");
			String  newPassword = json.getString("newPassword");
			String  oldPassword = json.getString("oldPassword");			
			if(StringUtils.isEmpty(username) || StringUtils.isEmpty(newPassword)|| StringUtils.isEmpty(oldPassword)) {
			 return JsonResponse.failure("用户名或密码不能为空！") ;	
			}
			UserInfo  user = userInfoService.findUserByUsername(username);
			if(user==null) {
				 return JsonResponse.failure("用户不存在！") ;	
			}else if (!user.getPassword().equalsIgnoreCase(MD5Util.md5(oldPassword))){
				 return JsonResponse.failure("旧密码输入不正确！") ;	
			}
			user.setPassword(MD5Util.md5(newPassword));
			userInfoService.modifyPwd(user);
		     return JsonResponse.success("修改成功！");
	   }catch(Exception e) {
		   LOGGER.error("regist error", e);
		   return JsonResponse.success("修改失败，请联系管理员！");
	   }
	}
	
}

