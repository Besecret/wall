package com.wanding.service;

import java.util.List;
import java.util.Map;
import com.wanding.model.UserCardInfo;

/**
 * interface 会员卡接口类
 * @author wang.dong
 *
 */
public interface CardInfoService {

    /**
     * query 查询所有卡信息
     * @return 卡信息集合 
     */
    List<UserCardInfo> queryAllCardInfo();
    
    /**
     * add 新增惠民卡
     * @param cardInfo
     * @return 数据库受影响行
     */
    int addCardInfo(UserCardInfo cardInfo);
    
    
    /**
     * page query
     * @param pageNum 
     * @param pageSize
     * @return 查询结果集
     */
    Map<String, Object> queryCradInfoByPaging(int pageNum,int pageSize);
    
}
