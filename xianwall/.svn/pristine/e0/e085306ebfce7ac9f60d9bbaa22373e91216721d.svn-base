package com.wanding.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.wanding.model.Orders;
@Component
public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

	Orders selectByOutTradeNo(@Param("outNo")String out_trade_no);
}