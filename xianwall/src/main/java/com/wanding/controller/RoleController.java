package com.wanding.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wanding.model.Role;
import com.wanding.service.RoleService;
import com.wanding.util.JsonResponse;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    //获取角色
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list(HttpServletRequest request){
		LOGGER.info("=====list====start==" + System.currentTimeMillis());
    		ArrayList<Role>  roles =      roleService.findAllRoles();
    		if(CollectionUtils.isEmpty(roles)) {
    			return JsonResponse.failure("没有可用role信息！");
    		}
	    	JSONObject json = new JSONObject();
	    	json.put("roles", roles);
    		return JsonResponse.success("查询成功！",json);
    }
}
