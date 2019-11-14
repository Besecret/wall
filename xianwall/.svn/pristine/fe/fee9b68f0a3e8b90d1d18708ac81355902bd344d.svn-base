package com.wanding.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;
import com.wanding.comm.Constant;
import com.wanding.model.CardUseRecordInfo;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.CardRecordService;
import com.wanding.service.CheckInfoService;
import com.wanding.util.JsonResponseGenerator;

/**
 * index dash board
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/index")
public class DashBoardController {

    private static final Logger log = LoggerFactory.getLogger(DashBoardController.class);

    @Autowired
    private CheckInfoService checkService;

    @Autowired
    private CardRecordService cardRecordSer;
    
    @RequestMapping("dashBoard")
    public void dashBoard() {
        // 未审核信息用户
        countUnCheckInfo();
        // 本周惠民卡使用次数
        countRecordLimitWeek();
    }

    
    /**
     * count 未审核信息用户
     * @return num
     */
    private long countUnCheckInfo() {
       
        List<UserInfoCheck> checkInfo =
                groupingByCheckStatus(queryAllCheckInfo()).get(Constant.STATUS_UNCHECK);
        return checkInfo.stream().count();
        
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
     * group by checkStatus
     * 
     * @param checkInfo List
     * @return map集合 eg:{审核状态： List<审核信息>}
     */
    private Map<String, List<UserInfoCheck>> groupingByCheckStatus(List<UserInfoCheck> checkInfo) {

        return checkInfo.stream().collect(Collectors.groupingBy(UserInfoCheck::getStatus));
    }
    
    /**
     * all query
     * @return recordList
     */
    private List<CardUseRecordInfo> queryAllCardRecord(){
      return  cardRecordSer.queryAllCardRecord();
    }
    
  
    private Map<String, Date> getThisWeekDate() {
        
        Map<String, Date> date = new HashMap<String, Date>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.put("start", cal.getTime());
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        date.put("end", cal.getTime());
        return date;
    }
    /**
     * TODO
     * @return
     */
    private long countRecordLimitWeek() {
        Map<String, Date> date = getThisWeekDate();
        
      return  queryAllCardRecord().stream().filter(record -> record.getUsetime().after(date.get("start"))&&
                record.getUsetime().before(date.get("end"))).count();
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
