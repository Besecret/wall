package com.wanding.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.wanding.model.UserCardInfo;
import com.wanding.service.CardInfoService;
import com.wanding.util.JsonResponseGenerator;

/**
 * member info
 * @author wang.dong
 *
 */
@Controller
@RequestMapping("/member")
public class CarController {
    
    @Autowired
    private CardInfoService cardService;
    
    private static final Logger log = LoggerFactory.getLogger(CarController.class);
    /**
     * cardInfo 会员卡信息
     */
    @PostMapping("cardInfo")
    @ResponseBody
    public Object queryCardInfo(@RequestBody() Map<String, Object> param) {

        log.info(" --- collect cardInfo ---");
        try {

            @SuppressWarnings("unchecked")
            List<UserCardInfo> cardList = (List<UserCardInfo>) cardService.queryCradInfoByPaging(
                    Integer.parseInt(param.get("pageNum").toString()),
                    Integer.parseInt(param.get("pageSize").toString())).get("data");
            // filter type,createDate
            cardList = filterCardInfoByType(param.get("cardType").toString(), cardList);
            cardList = filterCardInfoByDate(param.get("createDate").toString(), cardList); 
            
            JSONObject json = new JSONObject();
            json.put("cardList", cardList);
            return JsonResponseGenerator.success(json);
        } catch (Exception e) {
            log.error("collect cardInfo fail ! ");
            return JsonResponseGenerator.fail(" check all user fail");
        }
    }
    
    /**
     * filter card by cardType
     * 
     * @param cardType
     * @param cardList
     * @return cardList
     */
    private List<UserCardInfo> filterCardInfoByType(String cardType, List<UserCardInfo> cardList) {

        if (StringUtils.isEmpty(cardType)) {
            return cardList;
        }
        return cardList.stream().filter(card -> card.getCardtype().equals(cardType))
                .collect(Collectors.toList());
    }


    /**
     * filter card by createDate
     * 
     * @param createDate
     * @param cardList
     * @return
     */
    private List<UserCardInfo> filterCardInfoByDate(String createDate, List<UserCardInfo> cardList) {

        if (StringUtils.isEmpty(createDate)) {
            return cardList;
        }
      
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
           
            Date date = dateFormat.parse(createDate);
            return cardList.stream().filter(card -> card.getCreatedtime().equals(date))
                    .collect(Collectors.toList());
        } catch (ParseException e) {
          
            log.error("trans time fail !");
            return cardList;
        }
    }
}
