package com.wanding.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wanding.dao.UserInfoCheckMapper;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.CheckInfoService;
import com.wanding.service.PageService;
/**
 * checkImpl 
 * @author wang.dong
 *
 */
@Service
public class CheckInfoServiceImpl extends PageService implements CheckInfoService{

    @Autowired
    private UserInfoCheckMapper userCheckMapper ;
    
    @Override
    public List<UserInfoCheck> queryAllCheckInfo() {
        
        return userCheckMapper.queryAllCheckInfo();
    }

    @Override
    public int updateCheckInfo(UserInfoCheck checkInfo) {
        
        return userCheckMapper.updateByPrimaryKey(checkInfo);
    }

    @Override
    public int insertCheckInfo(UserInfoCheck checkInfo) {

        return userCheckMapper.insert(checkInfo);
    }

    @Override
    public int deleteCheckInfoByUserId(int userId) {
        
        return userCheckMapper.deleteCheckInfoByUserId(userId);
    }

    @Override
    public Map<String, Object> queryCheckInfoByPaging(int pageNum, int pageSize) {
        
        return this.selectPage(pageNum, pageSize, () -> queryAllCheckInfo()).getResponseInfo();
    }

    @Override
    public UserInfoCheck findByUserId(int userId) {
       
        return userCheckMapper.findByUserId(userId);
    }

}
