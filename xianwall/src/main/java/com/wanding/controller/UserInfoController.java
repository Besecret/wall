package com.wanding.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wanding.comm.Constant;
import com.wanding.model.UserInfo;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.UserInfoCheckService;
import com.wanding.service.UserInfoService;
import com.wanding.util.DateFormatUtil;
import com.wanding.util.HttpPostUtil;
import com.wanding.util.IDCardUtil;
import com.wanding.util.JsonResponseGenerator;

@RestController
@RequestMapping(value = "/api/user")
public class UserInfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoCheckService userInfoCheckService;
    
    
    
    //用户登录
    @PostMapping(value = "/login")
    public Object login(HttpServletRequest request,String param){//code,appid,session
		LOGGER.info("login start ... PARAM : " + param);
    	try {
			LOGGER.info("sendPost MEMBER_WXLOGIN= "+Constant.MEMBER_WXLOGIN+" PARAM : " + param);
    		String  result =  HttpPostUtil.sendPost(Constant.MEMBER_WXLOGIN, param);
    		if(StringUtils.isEmpty(result)) {
    			LOGGER.info("会员登录接口返回数据为空");
    			return JsonResponseGenerator.fail("会员登录接口返回数据为空");
    		}
    		JSONObject resultObj = JSON.parseObject(result);//appId,openId,sessionId
    		UserInfo uerInfo =userInfoService.findUserInfoByOpenId(resultObj.getString("openId"));
    		if(uerInfo==null){
    			uerInfo =new  UserInfo();
    			uerInfo.setOpenId(resultObj.getString("openId"));
    			uerInfo.setAppId(resultObj.getString("appId"));
    			uerInfo.setCreatedtime(new Date(System.currentTimeMillis()));
    			uerInfo.setUpdatedtime(new Date(System.currentTimeMillis()));
    			userInfoService.addUserInfo(uerInfo);
    		}
    		request.getSession().setAttribute("user", uerInfo);
    		resultObj.put("userinfo",uerInfo);
			return  JsonResponseGenerator.success(resultObj);
		} catch (Exception e) {
			LOGGER.error("login error", e);
			return JsonResponseGenerator.fail("login error");
		}
    }
    
    
 
    
    //获取fans接口
    @PostMapping(value = "/fans")
    public Object fans(HttpServletRequest request,String param){//openid,appid
		LOGGER.info("fans start ... PARAM : " + param);
    	try {
			LOGGER.info("sendPost MEMBER_WXLOGIN= "+Constant.MEMBER_WXLOGIN+" PARAM : " + param);
    		String  result =  HttpPostUtil.sendPost(Constant.MEMBER_WXLOGIN, param);
    		if(StringUtils.isEmpty(result)) {
    			LOGGER.info("获取fans接口返回数据为空");
    			return JsonResponseGenerator.fail("获取fans接口返回数据为空");
    		}
    		JSONObject resultObj = JSON.parseObject(result);//appId,openId,sessionId
    		UserInfo uerInfo =userInfoService.findUserInfoByOpenId(resultObj.getString("openId"));
    		if(uerInfo==null){
    			uerInfo =new  UserInfo();
    			uerInfo.setOpenId(resultObj.getString("openId"));
    			uerInfo.setAppId(resultObj.getString("appId"));
    			uerInfo.setCreatedtime(new Date(System.currentTimeMillis()));
    			uerInfo.setUpdatedtime(new Date(System.currentTimeMillis()));
    			userInfoService.addUserInfo(uerInfo);
    		}
    		resultObj.put("userinfo",uerInfo);
			return  JsonResponseGenerator.success(resultObj);
		} catch (Exception e) {
			LOGGER.error("login error", e);
			return JsonResponseGenerator.fail("login error");
		}
    }
    
    
    
    //用户注册
    @PostMapping(value = "/regist")
    public Object regist(HttpServletRequest request,String param){
		LOGGER.info("=====regist====start==" + System.currentTimeMillis());
    	try {
    		UserInfo userinfo = JSON.parseObject(param, UserInfo.class);
    		if(userInfoService.findUserInfoByOpenId(userinfo.getOpenId())!=null){
    			return JsonResponseGenerator.fail("此用户已经注册！");
    		}
    		
    		if(!IDCardUtil.isValid(userinfo.getIdcard())) {
    			return JsonResponseGenerator.fail("身份证输入不合法！");
    		}
    		
    		userinfo.setCreatedtime(new Date(System.currentTimeMillis()));
    		userinfo.setUpdatedtime(new Date(System.currentTimeMillis()));
    		System.out.println(DateFormatUtil.format(userinfo.getCreatedtime(), DateFormatUtil.FORMATDATETIME));
    		userInfoService.addUserInfo(userinfo);
    	    JSONObject json = new JSONObject();	
    	    json.put("userinfo",userInfoService.findUserInfoByOpenId(userinfo.getOpenId()));
    		return JsonResponseGenerator.success(json);
		} catch (Exception e) {
			LOGGER.error("param parse data error", e);
			return JsonResponseGenerator.fail("param parse data error！");
		}
    }

    	
    	
    	 //修改用户
        @PostMapping(value = "/update")
        public Object update(HttpServletRequest request,String param){
    		LOGGER.info("=====update====start==" + System.currentTimeMillis());
        	try {
        		UserInfo userinfo = JSON.parseObject(param, UserInfo.class);
        		if(userInfoService.findUserInfoByOpenId(userinfo.getOpenId())==null){
        			return JsonResponseGenerator.fail("此用户不存在！");
        		}
        		userinfo.setUpdatedtime(new Date(System.currentTimeMillis()));
        		userInfoService.updateUserInfo(userinfo);
        		JSONObject json = new JSONObject();	
        	    json.put("userinfo",userInfoService.findUserInfoByOpenId(userinfo.getOpenId()));
        		return JsonResponseGenerator.success(json);

    		} catch (Exception e) {
    			LOGGER.error("param parse data error", e);
    			return JsonResponseGenerator.fail("param parse data error！");
    		}          
        }

         //获取用户
        @GetMapping(value = "/getUser")
        public Object getUser(HttpServletRequest request, String param){
    		LOGGER.info("=====getUser====start==" + System.currentTimeMillis());
        	try {
        		UserInfo userinfo =  JSON.parseObject(param, UserInfo.class);
        		UserInfo userInfo  = userInfoService.findUserInfoByOpenId(userinfo.getOpenId());
        		if(userInfo==null){
        			return JsonResponseGenerator.fail("未查到此用户信息");
        		}
          		JSONObject json = new JSONObject();	
        	    json.put("userinfo",userInfo);
        		return JsonResponseGenerator.success(json);
    		} catch (Exception e) {
    			LOGGER.error("param parse data error", e);
    			return JsonResponseGenerator.fail("param parse data error！");
    		}
        }
        
        //获取用户审核信息
        @PostMapping(value = "/getUserInfoCheck")
        public Object getUserInfoCheck(HttpServletRequest request,String param){
    		LOGGER.info("=====userInfoCheck====start==" + System.currentTimeMillis());  
        	try {
        		JSONObject obj = JSON.parseObject(param);
        		UserInfoCheck userInfoCheck =userInfoCheckService.findByUserId(obj.getInteger("userId"));
        		if(userInfoCheck==null){
        			return JsonResponseGenerator.fail("未查到此用户审核信息");
        		}        		
        		JSONObject json = new JSONObject();	
        	    json.put("userInfoCheck",userInfoCheck);
        		return JsonResponseGenerator.success(json);
    		} catch (Exception e) {
    			LOGGER.error("param parse data error", e);
    			return JsonResponseGenerator.fail("param parse data error！");
    		}
        }
}
