package com.wanding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanding.dao.UserCardInfoMapper;
import com.wanding.model.UserCardInfo;
import com.wanding.service.CardInfoService;
import com.wanding.service.PageService;
import com.wanding.util.CommonUtil;


/**
 * cardInfo service实现
 * 
 * @author Administrator
 *
 */
@Service
public class CardInfoServiceImpl extends PageService implements CardInfoService {

    @Autowired
    private UserCardInfoMapper userCardInfoMapper;


    @Override
    public List<UserCardInfo> queryAllCardInfo() {

        return userCardInfoMapper.queryAllCardInfo();
    }


    @Override
    public int addCardInfo(UserCardInfo cardInfo) {

        return userCardInfoMapper.insert(cardInfo);
    }


    @Override
    public List<UserCardInfo> queryCradInfoByPaging() {
        
        return  userCardInfoMapper.queryAllCardInfo();

    }


    /**
     * 转换信息
     * 
     * @param cardList
     * @return
     */
    @Override
    public List<UserCardInfo> transformList(List<UserCardInfo> cardList) {
        List<UserCardInfo> list = new ArrayList<UserCardInfo>();
        cardList.stream().forEach(card -> {
            card.getUser().setGender(card.getUser().getGender()==null?"":card.getUser().getGender().equals("1") ? "男" : "女");
            card.getUser().setUserrole(CommonUtil.getUserNameById(card.getUser().getUserrole()));
            list.add(card);
        });
        return list;
    }


}
