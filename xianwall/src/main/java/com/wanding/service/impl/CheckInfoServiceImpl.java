package com.wanding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wanding.config.ImageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wanding.dao.UserInfoCheckMapper;
import com.wanding.model.UserCardInfo;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.CheckInfoService;
import com.wanding.service.PageService;
import com.wanding.util.CommonUtil;

/**
 * checkImpl
 * 
 * @author wang.dong
 *
 */
@Service
public class CheckInfoServiceImpl implements CheckInfoService {

    @Autowired
    private UserInfoCheckMapper userCheckMapper;

    @Autowired
    private ImageProperties imageProperties;

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
    public UserInfoCheck findByUserId(int userId) {

        return userCheckMapper.findByUserId(userId);
    }

    @Override
    public List<UserInfoCheck>  queryCheckInfoByPaging() {

        return userCheckMapper.queryAllCheckInfo();

    }

    @Override
    public UserInfoCheck findById(int id) {
        return userCheckMapper.findById(id);
    }

    /**
     * 转换信息
     * 
     * @param listInfo
     * @return
     */
    @Override
    public List<UserInfoCheck> transformList(List<UserInfoCheck> checkList) {
        List<UserInfoCheck> list = new ArrayList<UserInfoCheck>();
        checkList.stream().forEach(check -> {
            check.getUser().setGender(check.getUser().getGender()==null?"":check.getUser().getGender().equals("1") ? "男" : "女");
            check.getUser().setUserrole(CommonUtil.getUserNameById(check.getUser().getUserrole()));
            check.setAttachdoc(check.getAttachdoc()==null?"": imageProperties.getImagereadpath()+check.getAttachdoc());
            check.setIdcardfrontdoc(check.getIdcardfrontdoc()==null?"": imageProperties.getImagereadpath()+check.getIdcardfrontdoc());
            check.setIdcardbackdoc(check.getIdcardbackdoc()==null?"": imageProperties.getImagereadpath()+check.getIdcardbackdoc());
            list.add(check);
        });
        return list;
    }


}
