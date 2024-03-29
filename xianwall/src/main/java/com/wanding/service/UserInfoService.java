package com.wanding.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wanding.model.UserInfo;
import com.wanding.model.UserInfoVo;


public interface UserInfoService {

	
	
    int addUserInfo(UserInfo userInfo);
    
    UserInfo findUserInfoByOpenId(String openId);
    
    int updateUserInfo(UserInfo userInfo);

    Object findAllUserInfo(int pageNum, int pageSize);


	UserInfo findUserByUsername(String username);


	int findUserByRealName(String username);

    UserInfo findUserByUserId(int useId);

	Integer modifyPwd(UserInfo user);

    
    /**
     * all query
     * @return all userInfo
     */
    List<UserInfo> queryAllUserInfo();


    /**
     * all query
     * @return all userInfo
     */
    List<UserInfo> findAllManageInfo();
    
    /**
     * paging query
     * @param pageNum pageNum
     * @param pageSize size
     * @return userList  by page
     */
    List<UserInfo> queryUserInfoByPaging();

	UserInfo jscodeToSession(String param);

	Map<String, Object> miniFans(UserInfoVo userinfovo);
	
    UserInfo findUserInfoBySession(String session);
    
	int updateUserInfobysession(UserInfo record);

	Map<String, Object> importUser(HttpServletRequest request);

	int updateByPrimaryKey(UserInfo userInfo);

    List<UserInfo> transformList(List<UserInfo> listInfo);


}
