package com.wanding.controller.pc;

import com.alibaba.fastjson.JSONObject;
import com.wanding.comm.Constant;
import com.wanding.model.CardUseRecordInfo;
import com.wanding.model.UserInfo;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.CardInfoService;
import com.wanding.service.CardRecordService;
import com.wanding.service.CheckInfoService;
import com.wanding.service.UserInfoService;
import com.wanding.util.CommonUtil;
import com.wanding.util.JsonResponse;
import com.wanding.util.JsonResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * index dash board
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/index")
public class DashBoardController {

    private static final Logger log = LoggerFactory.getLogger(DashBoardController.class);

    @Autowired
    private CheckInfoService checkService;

    @Autowired
    private CardRecordService cardRecordSer;

    @Autowired
    private CardInfoService cardService;

    @Autowired
    private UserInfoService userService;

    @RequestMapping("dashBoard")
    @ResponseBody
    public Object dashBoard() {


        Map<String, Object> result = new HashMap<String, Object>();
        try {

            // 未审核信息用户
            result.put("unCheckUse", countUnCheckInfo());
            // 本周惠民卡使用次数
            result.put("countRecord", countRecordLimitWeek());
            // 本周新增会员卡
            result.put("countMemRecord", countMemberLimitWeek());
            // 本周会员卡新激活
            result.put("countMemUseRecord", countMemberFirstUseByWeek());
            // 新增用户
            result.put("countAddUser", getUserInfoLatest());
            // 操作记录
            result.put("countAddRecord", getCheckInfoLatest());
        } catch (Exception e) {
            log.error("查询异常");
            return JsonResponse.failure(" 查询首页信息失败");
        }
        JSONObject json = new JSONObject();
        json.put("data", result);
        return JsonResponse.success("查询首页信息成功！", json);
    }


    /**
     * count 未审核信息用户
     *
     * @return num
     */
    private long countUnCheckInfo() {

        List<UserInfoCheck> checkInfo =
                groupingByCheckStatus(queryAllCheckInfo()).get(Constant.STATUS_UNCHECK);
        return checkInfo == null ? 0 : checkInfo.stream().count();

    }


    /**
     * query All审核信息
     *
     * @return checkInfo List
     */
    private List<UserInfoCheck> queryAllCheckInfo() {

        List<UserInfoCheck> checkInfo = checkService.queryAllCheckInfo();
        return checkInfo;
    }


    /**
     * group by checkStatus
     *
     * @param checkInfo List
     * @return map集合 eg:{审核状态： List<审核信息>}
     */
    private Map<String, List<UserInfoCheck>> groupingByCheckStatus(List<UserInfoCheck> checkInfo) {

        return checkInfo.stream().collect(Collectors.groupingBy(UserInfoCheck::getStatus));
    }

    /**
     * all query 卡使用记录
     *
     * @return recordList
     */
    private List<CardUseRecordInfo> queryAllCardRecord() {
        return cardRecordSer.queryAllCardRecord();
    }


    private Map<String, Date> getThisWeekDate() {

        Map<String, Date> date = new HashMap<String, Date>();
        Calendar cal = Calendar.getInstance();
        date.put("start", cal.getTime());
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        date.put("end", cal.getTime());
        return date;
    }

    /**
     * 当前时间之前 一周使用记录总数
     *
     * @return long
     */
    private long countRecordLimitWeek() {
        Map<String, Date> date = getThisWeekDate();

        return queryAllCardRecord().stream()
                .filter(record -> record.getUsetime().getTime() < date.get("start").getTime()
                        && record.getUsetime().getTime() > date.get("end").getTime())
                .count();

    }


    /**
     * 本周新增会员卡
     *
     * @return
     */
    private long countMemberLimitWeek() {

        Map<String, Date> date = getThisWeekDate();
        return cardService.queryAllCardInfo().stream().filter(
                cardInfo -> cardInfo.getCreatedtime().getTime() < date.get("start").getTime()
                        && cardInfo.getCreatedtime().getTime() > date.get("end").getTime())
                .count();

    }


    /**
     * 本周会员卡新激活
     *
     * @return
     */
    private long countMemberFirstUseByWeek() {

        Map<String, Date> date = getThisWeekDate();
        return cardService.queryAllCardInfo().stream().filter(
                cardInfo -> cardInfo.getFirstusetime().getTime() < date.get("start").getTime()
                        && cardInfo.getFirstusetime().getTime() > date.get("end").getTime())
                .count();
    }

    /**
     * 获取最近注册用户
     *
     * @return
     */
    private List<UserInfo> getUserInfoLatest() {
        List<UserInfo> userList = userService.queryAllUserInfo();
        // 性别转换 -> 1男,0女;用户类型转换 ->1 学生,2 本地市民,3 本地常住居民
        userList = transformList(userList);
        return userList.stream().sorted(Comparator.comparing(UserInfo::getCreatedtime)).limit(5)
                .collect(Collectors.toList());
    }

    /**
     * 转换信息
     *
     * @param listInfo
     * @return
     */
    private List<UserInfo> transformList(List<UserInfo> listInfo) {
        List<UserInfo> userlist = new ArrayList<UserInfo>();
        listInfo.stream().forEach(user -> {
            user.setGender(user.getGender() == null ? "" : user.getGender().equals("1") ? "男" : "女");
            user.setUserrole(user.getUserrole() == null ? "" : CommonUtil.getUserNameById(user.getUserrole()));
            userlist.add(user);
        });
        return userlist;
    }

    /**
     * @param userName 获取最新审核信息
     * @return checkList
     */
    private List<UserInfoCheck> getCheckInfoLatest() {
        List<UserInfoCheck> checkList = checkService.queryAllCheckInfo();
        checkList = transform(checkList);
        return checkList.stream()
                .sorted(Comparator.comparing(UserInfoCheck::getChecktime).reversed()).limit(5)
                .collect(Collectors.toList());
    }

    /**
     * 转换信息
     *
     * @param listInfo
     * @return
     */
    private List<UserInfoCheck> transform(List<UserInfoCheck> checkList) {
        List<UserInfoCheck> check = new ArrayList<UserInfoCheck>();
        checkList.stream().forEach(c -> {
            c.setStatus(c.getStatus().equals("1") ? "已通过" : "未通过");
            check.add(c);
        });
        return check;
    }


    public Object queryMemberInfo(@RequestBody() Map<String, Integer> param) {

        log.info("--- collect memberInfo ---");
        // by cardId
        try {
            List<CardUseRecordInfo> cardRecordList =
                    cardRecordSer.queryCardRecordByCardId(param.get("cardId"));

            /**
             * pageing query cardRecordSer.queryCardRecordByPaging(pageNum,pageSize); Map<Integer,
             * List<Date>> map =
             * cardRecordList.stream().collect(Collectors.groupingBy(CardUseRecordInfo::getCardid,
             * Collectors.mapping(CardUseRecordInfo::getUsetime, Collectors.toList())));
             **/
            JSONObject json = new JSONObject();
            json.put("recordList", cardRecordList);
            return JsonResponseGenerator.success(json);
        } catch (Exception e) {
            log.error("collect memberInfo fail ! ");
            return JsonResponseGenerator.fail(" check all memberInfo fail");
        }
    }

}
