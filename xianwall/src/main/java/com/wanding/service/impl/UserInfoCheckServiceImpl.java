package com.wanding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanding.dao.UserInfoCheckMapper;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.PageService;
import com.wanding.service.UserInfoCheckService;
import com.wanding.util.queryhelper.QueryExecutor;

@Service(value = "userInfoCheckService")
public class UserInfoCheckServiceImpl extends PageService implements UserInfoCheckService {

    @Autowired
    private UserInfoCheckMapper userInfoCheckMapper;

	@Override
	public UserInfoCheck findByUserId(Integer  userId) {
		return userInfoCheckMapper.findByUserId(userId);
	}

	@Override
	public Object findAllUserInfoCheck(int pageNum, int pageSize, UserInfoCheck record) {
		 return this.selectPage(pageNum, pageSize, new QueryExecutor<UserInfoCheck>() {
             @Override
             public List<UserInfoCheck> execute() {
                 return userInfoCheckMapper.findAllUserInfoCheck(record);
             }
          }).getResponseInfo();
	}

	@Override
	public int addUserInfoCheck(UserInfoCheck record) {
		return userInfoCheckMapper.insertSelective(record);
	}

	@Override
	public int updateUserInfoCheck(UserInfoCheck record) {
		return userInfoCheckMapper.updateByPrimaryKeySelective(record);
	}

    
}
