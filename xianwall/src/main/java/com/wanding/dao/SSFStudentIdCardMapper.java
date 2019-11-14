package com.wanding.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wanding.model.SSFStudentIdCard;
@Component
public interface SSFStudentIdCardMapper {
   
	Integer findstudentCountByIdCard(String  idCard);
	
	void insertBatch(List<SSFStudentIdCard> list);
 

}