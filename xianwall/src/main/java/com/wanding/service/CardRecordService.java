package com.wanding.service;

import java.util.List;
import java.util.Map;
import com.wanding.model.CardUseRecordInfo;

/**
 * service 会员卡使用记录
 * @author wang.dong
 *
 */
public interface CardRecordService {
    
    /**
     * cardRecord 记录查询
     * @param cardId 会员卡ID
     * @return 卡记录list
     */
    List<CardUseRecordInfo> queryCardRecordByCardId(int cardId); 
    
    /**
     * paging query
     * @param pageNum
     * @param pageSize
     * @return map
     */
    Map<String,Object> queryCardRecordByPaging(int pageNum,int pageSize);
    
    /**
     * query all
     * @return recordList
     */
    List<CardUseRecordInfo> queryAllCardRecord();
   
}
