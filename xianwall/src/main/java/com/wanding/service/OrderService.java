package com.wanding.service;

import java.util.Map;

import com.wanding.model.Orders;
import com.wanding.model.UserInfo;

public interface OrderService {
	Orders create(Map<String, Object> param, UserInfo user);

	Map<String, Object> doPayWithWD(Orders order, Map<String, Object> param);

	Orders updateOrder(Map<String, Object> param);

}
