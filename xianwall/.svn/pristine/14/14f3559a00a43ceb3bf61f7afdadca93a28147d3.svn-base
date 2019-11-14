package com.wanding.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wanding.dao.UserCardInfoMapper;
import com.wanding.model.UserCardInfo;
import com.wanding.model.UserInfo;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.CardInfoService;
import com.wanding.service.PageService;
import com.wanding.util.queryhelper.QueryExecutor;

/**
 * cardInfo service实现
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
    public Map<String, Object> queryCradInfoByPaging(int pageNum, int pageSize) {

      
        return this.selectPage(pageNum, pageSize, () -> queryAllCardInfo()).getResponseInfo();
    }

}
