package com.wanding.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wanding.dao.CardUseRecordInfoMapper;
import com.wanding.model.CardUseRecordInfo;
import com.wanding.service.CardRecordService;
import com.wanding.service.PageService;

/**
 * cardRecord 实现类
 * 
 * @author wang.dong
 *
 */
@Service
public class CardRecordServiceImpl extends PageService implements CardRecordService {

    @Autowired
    private CardUseRecordInfoMapper cardRecordMapper;


    @Override
    public List<CardUseRecordInfo> queryCardRecordByCardId(int cardId) {

        return cardRecordMapper.queryCardRecordByCardId(cardId);
    }

    public List<CardUseRecordInfo> queryAllCardRecord() {

        return cardRecordMapper.queryAllCardRecord();
    }

    @Override
    public Map<String, Object> queryCardRecordByPaging(int pageNum, int pageSize) {

        return this.selectPage(pageNum, pageSize, () -> queryAllCardRecord()).getResponseInfo();
    }

}
