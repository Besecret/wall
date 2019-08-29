package com.wanding.service;

import java.util.List;
import java.util.Map;
import com.wanding.model.UserInfo;

public interface UserInfoService {

	
	
    int addUserInfo(UserInfo userInfo);
    
    UserInfo findUserInfoByOpenId(String openId);
    
    int updateUserInfo(UserInfo userInfo);

    Object findAllUserInfo(int pageNum, int pageSize);


	UserInfo findUserByUsername(String username);

	Integer modifyPwd(UserInfo user);

    
    /**
     * all query
     * @return all userInfo
     */
    List<UserInfo> queryAllUserInfo();
    
    /**
     * paging query
     * @param pageNum pageNum
     * @param pageSize size
     * @return userList  by page
     */
    Map<String, Object> queryUserInfoByPaging(int pageNum,int pageSize);


}
