package com.wanding.dao;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.wanding.model.UserInfo;

@Component
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("record")UserInfo record);

    int insertSelective(UserInfo record);

    int updateUserInfo(UserInfo record);
    
    UserInfo findUserInfoByOpenId(String  openId);
    
    int invalidCard();
    
    List<UserInfo> findAllUserInfo();

    List<UserInfo> findAllManageInfo();

	UserInfo findUserByUsername(String username);

    int findUserByRealName(String username);

    UserInfo findUserByUserId(int userId);


    Integer modifyPwd(UserInfo user);

	UserInfo findBySession(String session);

	int clearExpiresSession();
	
	int updateUserInfobysession(UserInfo record);
	
    int updateByPrimaryKey(UserInfo userInfo);


}