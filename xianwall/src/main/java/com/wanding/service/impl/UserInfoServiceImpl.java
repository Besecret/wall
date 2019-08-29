package com.wanding.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanding.dao.UserInfoMapper;
import com.wanding.model.UserInfo;
import com.wanding.service.PageService;
import com.wanding.service.UserInfoService;
import com.wanding.util.queryhelper.QueryExecutor;

@Service(value = "userInfoSerice")
public class UserInfoServiceImpl extends PageService  implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int addUserInfo(UserInfo userInfo) {
        return userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public Object findAllUserInfo(int pageNum, int pageSize) {
    	  return this.selectPage(pageNum, pageSize, new QueryExecutor<UserInfo>() {
              @Override
              public List<UserInfo> execute() {
                  return userInfoMapper.findAllUserInfo();
              }
          }).getResponseInfo();
    }


    @Override
    public List<UserInfo> queryAllUserInfo() {
      
        return userInfoMapper.findAllUserInfo();
    }


    @Override
	public UserInfo findUserInfoByOpenId(String openId) {
		
		return userInfoMapper.findUserInfoByOpenId(openId);
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		return userInfoMapper.updateUserInfo(userInfo);
	}


	@Override
	public UserInfo findUserByUsername(String username) {
		return userInfoMapper.findUserByUsername(username);
	}

	@Override
	public Integer modifyPwd(UserInfo user) {
		return userInfoMapper.modifyPwd(user);
		
	}

    @Override
    public Map<String, Object> queryUserInfoByPaging(int pageNum, int pageSize) {
        return this.selectPage(pageNum, pageSize, ()->queryAllUserInfo()).getResponseInfo();
    }

}
