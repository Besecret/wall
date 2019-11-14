package com.wanding.controller.pc;

import com.alibaba.fastjson.JSONObject;
import com.wanding.model.UserInfo;
import com.wanding.service.UserInfoService;
import com.wanding.util.JsonResponse;
import com.wanding.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * pc 会员登陆
 *
 * @author dong.wang
 */

@Controller
@RequestMapping("/login")
public class MemberControlle {

    private static final Logger log = LoggerFactory.getLogger(MemberControlle.class);

    @Autowired
    private UserInfoService userService;


    @RequestMapping("/main")
    public String main() {
        return "index";

    }


    @RequestMapping("/loginVerify")
    @ResponseBody
    public Object login(@RequestBody() Map<String, Object> param) {

        String username = param.get("username").toString();
        String password = param.get("password").toString();


        UserInfo user = userService.findUserByUsername(username);
        // 1.根据用登陆角色查询用户
        if (user == null) {

            return JsonResponse.failure("查询无此用户！");
        }
        // 如果状态为admin PC端管理员
        if (user.getUserrole().equals("admin")) {

            String encPass = user.getPassword();
            if (checkpassword(password, encPass)) {
                // 验证成功
                String session = UUID.randomUUID().toString().replaceAll("-", "");
                user.setSession(session);
                user.setExpiresTime(LocalDateTime.now().plusHours(2)
                        .toInstant(ZoneOffset.of("+8")).toEpochMilli() / 1000);
                //更新session                
                int num = userService.updateByPrimaryKey(user);
                if (num < 0) {
                    log.error("更新session 失败");
                }
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("sessionKey", session);
                map.put("user", user);
                return JsonResponse.success("登陆成功", map);

            } else {

                return JsonResponse.failure("用户名或密码错误，请重新登陆");
            }
        } else {

            return JsonResponse.failure("用户名状态不可用，请等待审批");
        }
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Object logout(HttpServletRequest request) {


        String sessionKey = request.getHeader("sessionKey");
        UserInfo user = userService.findUserInfoBySession(sessionKey);
        // 1.根据用登陆角色查询用户
        if (user == null) {
            return JsonResponse.success("已注销");
        }
        user.setSession("");
        int num = userService.updateByPrimaryKey(user);
        if (num < 0) {
            log.error("更新session 失败");
            return JsonResponse.failure("更新session 失败");
        } else {
            return JsonResponse.success("已注销");
        }
    }


    @RequestMapping("/addManage")
    @ResponseBody
    public Object addManage(@RequestBody() Map<String, Object> param) {

        String username = param.get("username").toString();
        String password = param.get("password").toString();
        String phone = param.get("phone").toString();
        try {
            password = MD5Util.EncoderByMd5(password);
        } catch (Exception e) {
            log.info("加密失败");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setRealname(username);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setUsertel(phone);
        userInfo.setUserrole("admin");
        userInfo.setCreatedtime(new Date());
        int nameNum = userService.findUserByRealName(username);
        if (nameNum > 0) {
            log.error("名称重复");
            return JsonResponse.failure("添加失败，名称重复");
        }

        int num = userService.addUserInfo(userInfo);
        if (num < 0) {
            log.error("添加失败");
            return JsonResponse.failure("添加失败");
        } else {
            log.error("添加成功");
            return JsonResponse.success("添加成功");
        }
    }


    @RequestMapping("/updateManage")
    @ResponseBody
    public Object updateManage(@RequestBody() Map<String, Object> param) {

        int id = Integer.parseInt(param.get("id").toString());
        String username = param.get("username").toString();
        String password = param.get("password").toString();
        String phone = param.get("phone").toString();
        try {
            password = MD5Util.EncoderByMd5(password);
        } catch (Exception e) {
            log.info("加密失败");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setRealname(username);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setUsertel(phone);
        userInfo.setUserrole("admin");
        userInfo.setId(id);
        userInfo.setCreatedtime(new Date());

        int nameNum = userService.findUserByRealName(username);
        if (nameNum > 1) {
            log.error("名称重复");
            return JsonResponse.failure("添加失败，名称重复");
        }

        int num = userService.updateByPrimaryKey(userInfo);
        if (num < 0) {
            log.error("修改失败");
            return JsonResponse.failure("修改失败");
        } else {
            log.error("修改成功");
            return JsonResponse.success("修改成功");
        }


    }

    @RequestMapping("/queryManage")
    @ResponseBody
    public Object queryManage(@RequestBody() Map<String, Object> param) {

        String name = param.get("name") == null ? "" : param.get("name").toString();
        int pageNum = Integer.parseInt(param.get("pageNum").toString());
        int pageSize = Integer.parseInt(param.get("pageSize").toString());

        List<UserInfo> manage = userService.findAllManageInfo();
        if (!StringUtils.isEmpty(name)) {
            manage = manage.stream().filter(user -> user.getRealname().contains(name))
                    .collect(Collectors.toList());
        }

        int size = manage.size();
        int limit =pageNum * pageSize<size?pageNum * pageSize:size;
        manage= manage.subList((pageNum - 1) * pageSize,limit);

        if (manage == null) {
            log.error("查询失败");
            return JsonResponse.failure("查询失败");
        } else {

            JSONObject json = new JSONObject();
            json.put("data", manage);
            json.put("total", size);
            return JsonResponse.success("查询首成功！", json);

        }
    }

    public boolean checkpassword(String newpasswd, String oldpasswd) {
        try {
            if (MD5Util.EncoderByMd5(newpasswd).equals(oldpasswd)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.info("md5 解析失败");
            return false;
        }
    }


}
