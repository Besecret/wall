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

	UserInfo findUserByUsername(String username);

	Integer modifyPwd(UserInfo user);
}