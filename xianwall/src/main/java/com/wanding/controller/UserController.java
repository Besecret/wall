package com.wanding.controller;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.wanding.model.UserInfo;
import com.wanding.service.UserInfoService;
import com.wanding.util.JsonResponseGenerator;

/**
 * Controller 收集信息
 * 
 * @author wang.dong
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);



    @Autowired
    private UserInfoService userService;


    /**
     * userInfo 用户信息
     * 
     * @param <T>
     */
    @PostMapping("userInfo")
    @ResponseBody
    public <T> Object queryUserInfo(@RequestBody() Map<String, Object> param) {

        log.info(" --- collect userinfo  ---");
        try {

            @SuppressWarnings("unchecked")
            List<UserInfo> userList = (List<UserInfo>) userService
                    .queryUserInfoByPaging(Integer.parseInt(param.get("pageNum").toString()),
                            Integer.parseInt(param.get("pageSize").toString()))
                    .get("data");
            // filter role ,name
            userList = filterUserInfoByRole(param.get("roleType").toString(), userList);
            userList = filterUserInfoByName(param.get("name").toString(), userList);

            JSONObject json = new JSONObject();
            json.put("userList", userList);
            return JsonResponseGenerator.success(json);
        } catch (Exception e) {
            log.error(" Function queryUserInfo: collect userinfo fail!");
            return JsonResponseGenerator.fail(" check all user fail");

        }
    }





    /**
     * filter userList by name
     * 
     * @param name
     * @param userList
     * @return userList
     */
    private List<UserInfo> filterUserInfoByName(String name, List<UserInfo> userList) {

        if (StringUtils.isEmpty(name)) {
            return userList;
        }
        return userList.stream().filter(user -> user.getUsername().equals(name))
                .collect(Collectors.toList());

    }

    /**
     * filter userList by role
     * 
     * @param role
     * @param userList
     * @return userList
     */
    private List<UserInfo> filterUserInfoByRole(String role, List<UserInfo> userList) {

        if (StringUtils.isEmpty(role)) {
            return userList;
        }
        return userList.stream().filter(user -> user.getUserrole().equals(role))
                .collect(Collectors.toList());
    }


}
