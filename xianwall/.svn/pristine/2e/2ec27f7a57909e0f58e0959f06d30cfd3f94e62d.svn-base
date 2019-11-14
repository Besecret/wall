package com.wanding.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Component;

import com.wanding.model.UserCardInfo;
/**
 * @author
 *
 */
@Component
public interface UserCardInfoMapper {
    
    int deleteByPrimaryKey(Integer id);

    int insert(UserCardInfo record);

    int insertSelective(UserCardInfo record);

    UserCardInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCardInfo record);

    int updateByPrimaryKey(UserCardInfo record);

    int invalidCard();
    
    /**
     * get all CardInfo
     * @return 会员卡信息集合
     */
    List<UserCardInfo> queryAllCardInfo();

	UserCardInfo isActive(@Param("id")Integer valueOf);

}