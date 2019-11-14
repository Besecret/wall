package com.wanding.service;

import java.util.List;
import java.util.Map;
import com.wanding.model.UserInfoCheck;

/**
 * check 审核信息
 * @author wang.dong
 *
 */
public interface CheckInfoService {

    /**
     * checkIno  查询所有的审核信息
     * @return UserInfoCheck List
     */
    List<UserInfoCheck> queryAllCheckInfo(); 
    
    /**
     * page query
     * @param pageNum 
     * @param pageSize
     * @return 查询结果集
     */
    List<UserInfoCheck> queryCheckInfoByPaging();
    
    /**
     *  update check
     * @param checkInfo 
     * @return int 操作行数
     */
    int updateCheckInfo(UserInfoCheck checkInfo);
    
    /**
     * insert check
     * @param checkInfo
     * @return int 操作行数
     */
    int insertCheckInfo(UserInfoCheck checkInfo);
    
    /**
     * delete checkInfo
     * @param userId 
     * @return 数据库操作记录
     */
    int deleteCheckInfoByUserId(int userId);
    
    /**
     * query checkInfo by  userId
     * @param userId
     * @return checkInfo 
     */
    UserInfoCheck findByUserId(int userId);
    
    
    UserInfoCheck findById(int id);

    List<UserInfoCheck> transformList(List<UserInfoCheck> checkList);
     
}
