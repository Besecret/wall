package com.wanding.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wanding.config.ImageProperties;
import com.wanding.model.UserInfo;
import com.wanding.model.UserInfoCheck;
import com.wanding.model.UserInfoVo;
import com.wanding.service.UserInfoCheckService;
import com.wanding.service.UserInfoService;
import com.wanding.util.DateFormatUtil;
import com.wanding.util.IDCardUtil;
import com.wanding.util.JsonResponse;

@RestController
@RequestMapping(value = "/api/user")
public class UserInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserInfoCheckService userInfoCheckService;
	@Autowired
	private ImageProperties imageProperties;
	/**
	 * 总后台小程序登陆流程 1.初始化 :通过code获取openid sessionid等 2.获取粉丝信息存到粉丝表中 3.判断用户是否存在登录权限
	 */
	// 用户登录
	@PostMapping(value = "/login")
	public Object login(HttpServletRequest request, @RequestBody String param) {
		LOGGER.info("login start ... param- : " + param);
		try {
			if (param == null) {
				return JsonResponse.failure("传入参数为空！");
			}

			UserInfo uerInfo = userInfoService.jscodeToSession(param);
			if (uerInfo == null) {
				return JsonResponse.failure("微信登录接口返回数据为空！");
			}
			request.getSession().setAttribute("user", uerInfo);
			JSONObject json = new JSONObject();
			// json.put("userinfo",uerInfo);
			json.put("sessionKey", uerInfo.getSession());
			System.out.println(json.toString());
			return JsonResponse.success("登录成功！", json);
		} catch (Exception e) {
			LOGGER.error("login error", e);
			return JsonResponse.failure("login error");
		}
	}

	// 用户信息维护
	@PostMapping(value = "/fans")
	public Object fans(HttpServletRequest request, @RequestBody Map<String, Object> requ) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			UserInfoVo userinfovo = JSON.parseObject(requ.get("rawData").toString(), UserInfoVo.class);
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute("user");
			userinfovo.setOpenId(userinfo.getOpenId());
			LOGGER.info("sessionId=====" + userinfovo.getSession());

			returnMap = userInfoService.miniFans(userinfovo);
		} catch (Exception e) {
			return JsonResponse.failure("用户信息失败");
		}
		LOGGER.info("miniFansMap======2=" + returnMap);
		return JsonResponse.success("成功");
	}

	// 用户注册
	@PostMapping(value = "/regist")
	public Object regist(HttpServletRequest request, @RequestBody String param) {
		LOGGER.info("=====regist====start==" + System.currentTimeMillis());
		try {
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute("user");
			if (!IDCardUtil.isValid(userinfo.getIdcard())) {
				return JsonResponse.failure("身份证输入不合法！");
			}

			userinfo.setCreatedtime(new Date(System.currentTimeMillis()));
			userinfo.setUpdatedtime(new Date(System.currentTimeMillis()));
			System.out.println(DateFormatUtil.format(userinfo.getCreatedtime(), DateFormatUtil.FORMATDATETIME));
			userInfoService.addUserInfo(userinfo);
			JSONObject json = new JSONObject();
			json.put("userinfo", userInfoService.findUserInfoByOpenId(userinfo.getOpenId()));
			return JsonResponse.success("注册成功！", json);
		} catch (Exception e) {
			LOGGER.error("param parse data error", e);
			return JsonResponse.failure("param parse data error！");
		}
	}

	// 修改用户
	@PostMapping(value = "/update")
	public Object update(HttpServletRequest request, @RequestBody String param) {
		LOGGER.info("=====update====start==" + System.currentTimeMillis());
		try {
			UserInfo userinfo = JSON.parseObject(param, UserInfo.class);
			userinfo.setUpdatedtime(new Date(System.currentTimeMillis()));
			userinfo.setSession(((UserInfo) request.getSession().getAttribute("user")).getSession());
			userInfoService.updateUserInfobysession(userinfo);
			JSONObject json = new JSONObject();
			json.put("userinfo", userInfoService.findUserInfoByOpenId(userinfo.getOpenId()));
			return JsonResponse.success("修改成功！", json);

		} catch (Exception e) {
			LOGGER.error("param parse data error", e);
			return JsonResponse.failure("param parse data error！");
		}
	}

	// 获取用户
	@PostMapping(value = "/getUser")
	public Object getUser(HttpServletRequest request, @RequestBody String param) {
		LOGGER.info("=====getUser====start==" + System.currentTimeMillis());
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
			UserInfo findUserInfoBySession = userInfoService.findUserInfoBySession(userInfo.getSession());
			JSONObject json = new JSONObject();
			json.put("userinfo", findUserInfoBySession);
			return JsonResponse.success("查询成功", json);
		} catch (Exception e) {
			LOGGER.error("param parse data error", e);
			return JsonResponse.failure("param parse data error！");
		}
	}

	// 获取用户审核信息
	@PostMapping(value = "/getUserInfoCheck")
	public Object getUserInfoCheck(HttpServletRequest request, @RequestBody String param) {
		LOGGER.info("=====userInfoCheck====start==" + System.currentTimeMillis());
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
			UserInfoCheck userInfoCheck = userInfoCheckService.findByUserId(userInfo.getId());
			JSONObject json = new JSONObject();
			if (userInfoCheck == null) {
				userInfoCheck = new UserInfoCheck();
				userInfoCheck.setUser(userInfo);
				json.put("userInfoCheck", userInfoCheck);
				return JsonResponse.success("查询成功", json);
			}
			userInfoCheck.setUser(userInfo);
			userInfoCheck.setIdcardbackdoc(imageProperties.getImagereadpath() + userInfoCheck.getIdcardbackdoc());
			userInfoCheck.setIdcardfrontdoc(imageProperties.getImagereadpath() + userInfoCheck.getIdcardfrontdoc());
			userInfoCheck.setAttachdoc(imageProperties.getImagereadpath() + userInfoCheck.getAttachdoc());
			json.put("userInfoCheck", userInfoCheck);
			return JsonResponse.success("查询成功", json);
		} catch (Exception e) {
			LOGGER.error("param parse data error", e);
			return JsonResponse.failure("param parse data error！");
		}
	}

        //用户审核状态
        @PostMapping(value = "/vaildApprovalStatus")
        public Object vaildApprovalStatus(HttpServletRequest request,@RequestBody  String param){
    		LOGGER.info("=====userInfoCheck====start==" + System.currentTimeMillis());  
        	try {
    			 String session = request.getHeader("sessionKey");
        		 UserInfo userInfo  = userInfoService.findUserInfoBySession(session);
        		 UserInfoCheck userInfoCheck  = userInfoCheckService.findByUserId(userInfo.getId());
           		 if(userInfoCheck==null) {
             		return JsonResponse.failure("没有审核信息");
        		 }else if(userInfoCheck.getStatus().equals("3")){
              		return JsonResponse.failure("审核拒绝");
        		 }else {
              		return JsonResponse.success("信息不可编辑");
        		 }
    		} catch (Exception e) {
    			LOGGER.error("param parse data error", e);
    			return JsonResponse.failure("param parse data error！");
    		}
        }
        
        
        //用户审核信息submit
        @PostMapping(value = "/submitApproval")
        public Object submitApproval(HttpServletRequest request,@RequestBody  String param){
    		LOGGER.info("=====submitApproval====start==" + System.currentTimeMillis());
        	try {
        		 JSONObject  json =  JSON.parseObject(param);
        		 UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        		 UserInfoCheck userInfoCheck = userInfoCheckService.findByUserId(userInfo.getId());

				if(userInfoCheck==null) {
        			 userInfoCheck = new UserInfoCheck();
        		 }
          		 userInfoCheck.setUserid(userInfo.getId());
        		 userInfoCheck.setIdcardfrontdoc(json.getString("cardFront"));
        		 userInfoCheck.setIdcardbackdoc(json.getString("cardBack"));
        		 if("3".equals(userInfo.getUserrole())) {
        			 userInfoCheck.setAttachtype("workCard");
        			 userInfoCheck.setAttachdoc(json.getString("workCard"));
        		 }else if("1".equals(userInfo.getUserrole())){
        			 userInfoCheck.setAttachtype("stuCard");
        			 userInfoCheck.setAttachdoc(json.getString("stuCard"));
        		 }
        		 userInfoCheck.setStatus("0");
        		 userInfoCheck.setUpdatedtime(new Date(System.currentTimeMillis()));
        		 if(userInfoCheckService.findByUserId(userInfoCheck.getUserid())==null) {
					 LOGGER.info("=====userInfo.getId====start== addUserInfoCheck" + userInfo.getId());
					 userInfoCheck.setCreatedtime(new Date(System.currentTimeMillis()));
            		 userInfoCheckService.addUserInfoCheck(userInfoCheck);
        		 }else {
					 LOGGER.info("=====userInfo.getId====start==" + userInfo.getId()+" getId " +userInfoCheck.getId() +"  getUserid" +userInfoCheck.getUserid());
					 LOGGER.info("=====userInfo.getId====start== updateUserInfoCheck" + userInfo.getId());
					 userInfoCheckService.updateUserInfoCheck(userInfoCheck);
        		 }
        		return JsonResponse.success("提交成功",json);
    		} catch (Exception e) {
    			LOGGER.error("param parse data error", e);
    			return JsonResponse.failure("param parse data error！");
    		}
        }
}
