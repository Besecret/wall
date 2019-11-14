package com.wanding.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanding.dao.SSFStudentIdCardMapper;
import com.wanding.service.SSFStudentIdCardService;
@Service(value = "sSFStudentIdCardService")
public class SSFStudentIdCardServiceImpl   implements SSFStudentIdCardService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SSFStudentIdCardServiceImpl.class);
    @Autowired
    private SSFStudentIdCardMapper sSFStudentIdCardMapper;
	@Override
	public boolean hasfindstudentByIdCard(String idCard) {
		int  count =sSFStudentIdCardMapper.findstudentCountByIdCard(idCard);
		if(count>0) {
			return  true;

		}else {
			return  false;

		}
	}
    
	

}
