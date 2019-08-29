package com.wanding.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanding.model.Orders;
import com.wanding.model.UserCardInfo;
import com.wanding.model.UserInfo;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.CardService;
import com.wanding.service.OrderService;
import com.wanding.service.UserInfoCheckService;

@RestController
@RequestMapping("order")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private CardService cardService;

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserInfoCheckService checkService;

	@PostMapping("create")
	public Map<String, Object> createOrder(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {

		LOGGER.info("create order start ... param : " + param);

		// 创建订单
		// 调用支付
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserInfo user = (UserInfo) req.getSession().getAttribute("user");

			UserInfoCheck checkInfo = checkService.findByUserId(user.getId());
			if ("0".equals(checkInfo.getStatus())) {
				LOGGER.info("Personal information is checking");
				result.put("return_code", "00");
				result.put("return_msg", "个人信息审核中");
				result.put("result_code", "00");
			} else if ("1".equals(checkInfo.getStatus())) {
				Orders order = orderService.create(param, user);
				LOGGER.info("applet pay start ...");
				result = orderService.doPayWithWD(order, param);
				LOGGER.info("order create finish , waiting notify ...");
			} else if ("3".equals(checkInfo.getStatus())) {
				LOGGER.info("Personal information check refused");
				result.put("return_code", "03");
				result.put("return_msg", "个人信息审核拒绝");
				result.put("result_code", "03");
			}

		} catch (Exception e) {
			LOGGER.error("create Order error", e);
			result.put("return_code", "02");
			result.put("return_msg", "交易失败");
			result.put("result_code", "02");
		}
		return result;
	}

	@PostMapping("update")
	public void updateOrder(HttpServletRequest req, HttpServletResponse res, @RequestBody() Map<String, Object> param) {

		LOGGER.info("update order status start ... param : " + param.toString());
		try {

			if ("SUCCESS".equals(param.get("return_code"))) {
				LOGGER.info("applet pay success");
				UserCardInfo createCard = cardService.createCard(param);
				param.put("cardId", createCard.getAddition2());
				LOGGER.info("create huimin card success");
			} else if ("FAIL".equals(param.get("return_code"))) {
				LOGGER.error(param.get("return_msg").toString());
			} else {
				LOGGER.error("unknow error >>>>>");
			}
			orderService.updateOrder(param);
			LOGGER.error("update order finish ...");
		} catch (Exception e) {
			LOGGER.error("errors in update order or create card ", e);
		}

	}
}
