package com.wanding.dao;


import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;



import org.springframework.stereotype.Component;
import com.wanding.model.CardUseRecordInfo;


@Component
public interface CardUseRecordInfoMapper {

    
    int deleteByPrimaryKey(Integer id);


	int insert(@Param("record")CardUseRecordInfo record);

	int insertSelective(CardUseRecordInfo record);

	CardUseRecordInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CardUseRecordInfo record);

	int updateByPrimaryKey(CardUseRecordInfo record);

	List<Map<String, String>> queryRecordByUser(Map<String,Object> userId);

    List<CardUseRecordInfo> queryAllCardRecord();
    
    /**
     * query 会员卡使用记录
     * @return 使用记录集合
     */
    List<CardUseRecordInfo> queryCardRecordByCardId(@Param("CardId") int cardId);
}
