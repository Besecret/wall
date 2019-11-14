package com.wanding.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wanding.model.UserInfoCheck;
@Component
public interface UserInfoCheckMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoCheck record);

    int insertSelective(UserInfoCheck record);

    UserInfoCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoCheck record);
    
    /**
     * update checkInfo by ID
     * @param checkInfo
     * @return 操作行数
     */
    int updateByPrimaryKey(UserInfoCheck record);
    
    UserInfoCheck findByUserId(Integer userId);

	List<UserInfoCheck> findAllUserInfoCheck(UserInfoCheck record);
    
	

    List<UserInfoCheck> queryAllCheckInfo();
    
    /**
     * delete checkInfo by userId
     * @param userId userid
     * @return 操作行数
     */
    int deleteCheckInfoByUserId(int userId);

}