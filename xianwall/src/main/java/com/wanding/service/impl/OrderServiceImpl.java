package com.wanding.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wanding.comm.Constant;
import com.wanding.dao.OrdersMapper;
import com.wanding.model.MiniUnifiedOrderRequest;
import com.wanding.model.Orders;
import com.wanding.model.UserInfo;
import com.wanding.service.OrderService;
import com.wanding.service.SSFStudentIdCardService;
import com.wanding.util.HttpPostUtil;
import com.wanding.util.JsonResponse;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	@SuppressWarnings("unused")
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");

	@Value("${server.notifyUrl}")
	public String host;

	@Autowired
	private SSFStudentIdCardService stuService;

	@Autowired
	private OrdersMapper orderDao;

	public Orders create(Map<String, Object> param, UserInfo user) {
		Orders order = new Orders();
		order.setCreatedtime(new Date());
		order.setAuthNo(String.valueOf(param.get("authNo")));
		order.setAddition1(host + "/order/update");// notify_url
		// order.setAddition2("addition2");
		// order.setAddition3("addition3");
		order.setCreatedtime(new Date());
		order.setOrderBody("西安城墙惠民卡购买");
//		order.setOrderBody(String.format("惠民卡  userid:{} , userrole:{}", user.getId(), user.getUserrole()));
		order.setPayType(String.valueOf(param.get("payType")));
		order.setOutTradeNo(getSerialNum());
		order.setMerchantNo(Constant.MERCHANTNO);
		order.setTerminalId(Constant.TERMINALID);
		// order.setMerchantNo(merchantNo);商户号
		// order.setTerminalId(terminalId);'终端号';
		// order.setTerminalTime(terminalTime);'终端交易流水';
		// order.setTerminalTrace(terminalTrace);'终端交易时间
		BigDecimal totalFee = new BigDecimal(String.valueOf(param.get("totalFee")));
		order.setTotalFee(totalFee);
		order.setUserId(user.getId());

		orderDao.insert(order);
		return order;
	}

	public static String getSerialNum() {
		String randomString = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
				+ getRandomStringByLength(15);
		return randomString;
	}

	public static String getRandomStringByLength(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public Map<String, Object> doPayWithWD(Orders order, Map<String, Object> param) {
		MiniUnifiedOrderRequest mini = new MiniUnifiedOrderRequest();

		UserInfo user = (UserInfo) param.get("user");

		// FIXME
		mini.setAppid(user.getAppId());
		mini.setBody(order.getOrderBody());

		mini.setMch_no(Constant.MERCHANTNO);// 88888888
		mini.setTerm_no(Constant.TERMINALID);

		// mini.setMch_no(String.valueOf(param.get("merchantNo")));// 88888888
		// mini.setTerm_no(String.valueOf(param.get("terminalId")));

		mini.setOpenid(String.valueOf(param.get("openId")));
		mini.setTrade_type(String.valueOf(param.get("tradeType")));
		// TODO
		// mini.setNotify_url("http://fdeb1db6.ngrok.io/order/update");
		mini.setNotify_url(host + "/order/update");

		mini.setNonce_str(String.valueOf(System.currentTimeMillis()));
		mini.setOut_trade_no(order.getOutTradeNo());
		mini.setPayMode("010");
		mini.setSign("");
		mini.setSign_type("");
		mini.setTotal_fee(order.getTotalFee().toString());

		// FIXME 确定用户类型，是否可使用优惠券
		if(stuService.hasfindstudentByIdCard(user.getIdcard())){
			mini.setGoods_tag("SXSFDX");
		}

		HttpPostUtil post = new HttpPostUtil();

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LOGGER.info("calling applet pay start ... ");
			String doPostWithJson = post.doPostWithJson(Constant.PAY_DOMAIN + "/pay/api/mini/unifiedorder", mini,
					"application/json");

			result = JSON.parseObject(doPostWithJson);

			LOGGER.info("calling applet pay end ... result : " + result);
			LOGGER.info("order create finish , waiting notify ...");
		} catch (Exception e) {
			LOGGER.error("Calling the applet to pay for an exception", e);
			result = JsonResponse.failure("交易失败");
		}
		return result;
	}

	@Override
	public Orders updateOrder(Map<String, Object> param) {

		Orders order = new Orders();

		try {
			// order.setId(Integer.valueOf(String.valueOf(param.get("orderId"))));
			order.setOutTradeNo(String.valueOf(param.get("terminal_trace")));
			order.setTerminalTrace(String.valueOf(param.get("channel_trade_no")));
			order.setReturnCode(String.valueOf(param.get("return_code")));
			order.setResultCode(String.valueOf(param.get("result_code")));
			order.setReturnMsg(String.valueOf(param.get("return_msg")));
			order.setUpdatedtime(new Date());
			order.setAddition2(String.valueOf(param.get("out_trade_no")));
			order.setAddition3(String.valueOf(param.get("cardId")));
			
			orderDao.updateByPrimaryKeySelective(order);
		} catch (RuntimeException e) {
			LOGGER.error("update order pay status error", e);
		}

		return order;
	}

}
