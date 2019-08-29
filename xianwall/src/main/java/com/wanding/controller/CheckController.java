package com.wanding.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wanding.comm.Constant;
import com.wanding.model.UserInfo;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.CheckInfoService;
import com.wanding.service.UserInfoService;
import com.wanding.util.CommonUtil;
import com.wanding.util.JsonResponseGenerator;

/**
 * controller 信息审核
 * 
 * @author wang.dong
 *
 */
@Controller
@RequestMapping("/check")
public class CheckController {

    private static final Logger log = LoggerFactory.getLogger(CheckController.class);

    @Autowired
    private CheckInfoService checkService;

    @Autowired
    private UserInfoService userService;

    /**
     * collect 收集所用审核信息
     */
    @RequestMapping("details")
    @ResponseBody
    public Object collectCheckInfo(@RequestBody() Map<String, Object> param) {

        log.info("--- check collect ---");
        try {

            @SuppressWarnings("unchecked")
            List<UserInfoCheck> checkInfo = (List<UserInfoCheck>) checkService
                    .queryCheckInfoByPaging(Integer.parseInt(param.get("pageNum").toString()),
                            Integer.parseInt(param.get("pageSize").toString()))
                    .get("data");
            //filter role ,status 
            checkInfo = filterCheckInfoByRole(param.get("role").toString(),checkInfo);
            checkInfo = filterCheckInfoByStatus(param.get("status").toString(),checkInfo);
            JSONObject json = new JSONObject();
            json.put("checkList", checkInfo);
            return JsonResponseGenerator.success(json);

        } catch (Exception e) {

            log.error("check details collect fail ! ");
            return JsonResponseGenerator.fail(" check details collect fail ");
        }
    }



    /**
     * modify 修改审核信息
     */
    @RequestMapping("modify")
    @ResponseBody
    public Object modify(@RequestBody() String param) {

        log.info("--- checkInfo modify ---");
        try {
            // modify 修改审核信息
            UserInfoCheck checkInfo = JSON.parseObject(param, UserInfoCheck.class);
            UserInfoCheck checkUser= checkService.findByUserId(checkInfo.getUserid());
            
            if(!Constant.STATUS_UNCHECK.equals(checkUser.getStatus())) {
                log.warn("modify checkInfo fail ! "); 
                return JsonResponseGenerator.success(" check already done! ");
            }
            
            int num = checkService.updateCheckInfo(checkInfo);
            if (num < 0) {
                log.error("modify checkInfo fail ! ");
                return JsonResponseGenerator.fail(" modify checkInfo fail ! ");
            }
            return JsonResponseGenerator.success(" modify checkInfo success ! ");
        } catch (Exception e) {
            log.error("modify checkInfo fail ! ");
            return JsonResponseGenerator.fail(" modify checkInfo fail ! ");
        }
    }

    /**
     * add 新增审核信息
     */
    @PostMapping("add")
    @ResponseBody
    public Object add(@RequestBody() String param) {

        log.info("--- check add ---");
        // insert checkInfo
        try {
            UserInfoCheck checkinfo = (UserInfoCheck) JSONObject.parse(param);
            int num = checkService.insertCheckInfo(checkinfo);
            if (num < 0) {
                log.error("insert checkInfo fail ! ");
                return JsonResponseGenerator.fail(" insert checkInfo fail ! ");
            }
            return JsonResponseGenerator.success(" insert checkInfo success ! ");
        } catch (Exception e) {
            log.error("add checkInfo fail ! ");
            return JsonResponseGenerator.fail(" add checkInfo fail ! ");
        }
    }

    /**
     * delete 删除审核信息
     */
    @PostMapping("delete")
    @ResponseBody
    public Object delete(@RequestBody() Map<String, Integer> param) {

        log.info("--- check delete ---");
        try {
            int num = checkService.deleteCheckInfoByUserId(param.get("userId"));
            if (num < 0) {
                log.error("delete checkInfo fail ! ");
                return JsonResponseGenerator.fail(" delete checkInfo fail ! ");
            }
            return JsonResponseGenerator.success(" delete checkInfo success ! ");
        } catch (Exception e) {
            log.error("delete checkInfo fail ! ");
            return JsonResponseGenerator.fail(" delete checkInfo fail ! ");
        }
    }

    /**
     * todo 当前审核下、待审核信息
     */
    @PostMapping("/toCheck")
    @ResponseBody
    public Object toCheck(@RequestBody() String param) {

        log.info("--- check  tocheck 当前未审核信息 ---- ");
        // status 0 审核状态为 0 的用户
        UserInfo user = null;
        try {
            UserInfo userinfo = JSON.parseObject(param, UserInfo.class);
            user = userService.findUserInfoByOpenId(userinfo.getOpenId());

            Map<String, Map<String, List<UserInfo>>> allCheckInfo =
                    groupingByCheckUser(queryAllCheckInfo());
            if (allCheckInfo.isEmpty()) {
                return JsonResponseGenerator.fail("check collect fail ！");
            }
            List<UserInfo> undoUsers =
                    allCheckInfo.get(user.getUsername()).get(Constant.STATUS_UNCHECK);

            JSONObject json = new JSONObject();
            json.put("userInfoCheck", undoUsers);
            return JsonResponseGenerator.success(json);
        } catch (Exception e) {
            log.error("-- get userInfo  fail !-- ");
            return JsonResponseGenerator.fail("check tocheck fail ！");

        }
    }
    
    
    /**
     * query All
     * 
     * @return checkInfo List
     */
    private List<UserInfoCheck> queryAllCheckInfo() {

        List<UserInfoCheck> checkInfo = checkService.queryAllCheckInfo();
        return checkInfo;
    }

    /**
     * group by checkUser
     * 
     * @param checkInfo List
     * @return map集合 eg:{审核人：{审核状态：List<用户信息>}}
     */
    private Map<String, Map<String, List<UserInfo>>> groupingByCheckUser(
            List<UserInfoCheck> checkInfo) {

        return checkInfo.stream()
                .collect(Collectors.groupingBy(UserInfoCheck::getCheckuser,
                        Collectors.toMap(UserInfoCheck::getStatus,
                                e -> new ArrayList<UserInfo>(Arrays.asList(e.getUser())),
                                CommonUtil.mergeFunction())));

    }



    /**
     * filter checkInfo by role
     * @param roleType
     * @param checkInfo
     * @return checkList
     */
    private List<UserInfoCheck> filterCheckInfoByRole(String roleType,
            List<UserInfoCheck> checkInfo) {
        
        if(StringUtils.isEmpty(roleType)) {
            return checkInfo;
        }
        
        return checkInfo.stream().filter(check -> check.getUser().getUserrole().equals(roleType))
                .collect(Collectors.toList());
    }
    
    /**
     * filter checkInfo by checkStatus
     * @param checkStatus
     * @param checkInfo
     * @return
     */
    private List<UserInfoCheck> filterCheckInfoByStatus(String checkStatus,List<UserInfoCheck> checkInfo){
       
        if(StringUtils.isEmpty(checkStatus)) {
            return checkInfo;
        }
        
        return checkInfo.stream().filter(check -> check.getStatus().equals(checkStatus))
                .collect(Collectors.toList());
    }
    
}
