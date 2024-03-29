package com.wanding.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanding.dao.CardUseRecordInfoMapper;
import com.wanding.dao.OrdersMapper;
import com.wanding.dao.UserCardInfoMapper;
import com.wanding.model.CardUseRecordInfo;
import com.wanding.model.Orders;
import com.wanding.model.UserCardInfo;
import com.wanding.service.CardService;
import com.wanding.util.JsonResponse;

@Service
public class CardServiceImpl implements CardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

	@Autowired
	private UserCardInfoMapper cardDao;
	@Autowired
	private CardUseRecordInfoMapper recordDao;

	@Autowired
	private OrdersMapper orderDao;

	@Override
	public UserCardInfo createCard(Map<String, Object> param) {

		String out_trade_no = String.valueOf(param.get("terminal_trace"));

		Orders order = orderDao.selectByOutTradeNo(out_trade_no);

		UserCardInfo card = new UserCardInfo();

		card.setCreatedtime(new Date());
		card.setUserid(order.getUserId());
		String str = (System.currentTimeMillis() + "").substring(7);
		card.setAddition1(str);
		card.setAddition2(UUID.randomUUID().toString());
		card.setAddition3("0");// 0 失效   1 有效

		cardDao.insert(card);
		return card;
	}

	@Override
	public Map<String, Object> activateCard(Map<String, Object> param) {

		LOGGER.info("begin to activate card");
		try {
			Calendar instance = Calendar.getInstance();

			Date now = instance.getTime();

			instance.add(1, 1);

			Date later = instance.getTime();

			UserCardInfo card = new UserCardInfo();

			card.setFirstusetime(now);
			card.setInvalidtime(later);
			card.setId(Integer.valueOf(param.get("id").toString()));
			card.setAddition3("1");
			
			int i = cardDao.updateByPrimaryKeySelective(card);

			LOGGER.info("activate card end ,result : " + i);
			if (i == 1) {
//				res.put("resultCode", "01");
//				res.put("resultMsg", "success");
//				res.put("resultBody", "Successful activation");
				return JsonResponse.success("success");
			} else {
//				res.put("resultCode", "02");
//				res.put("resultMsg", "fail");
//				res.put("resultBody", "Activation fails");
				return JsonResponse.failure("fail");

			}
		} catch (RuntimeException e) {
			LOGGER.error("Activate card error", e);
//			res.put("resultCode", "03");
//			res.put("resultMsg", "error");
//			res.put("resultBody", "Activation error");
			return JsonResponse.failure("error");
		}
	}

	@Override
	public Map<String, Object> useRecord(Map<String, Object> param) {

		List<Map<String, String>> record = new ArrayList<>();
		try {
			param.put("start", (Integer.valueOf(param.get("page").toString()) - 1)*Integer.valueOf(param.get("size").toString()));
			record = recordDao.queryRecordByUser(param);
		} catch (Exception e) {
			LOGGER.error("query card use record fail", e);
			return JsonResponse.failure("查询失败 ");

		}

		return JsonResponse.success("查询成功 ",record);
	}

	@Override
	public Map<String, Object> createCardRecord(Map<String, Object> param) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			CardUseRecordInfo info = new CardUseRecordInfo();

			info.setCardid(Integer.valueOf(String.valueOf(param.get("cardId"))));
			info.setUsetime(new Date());
			info.setUuid(UUID.randomUUID().toString());
			int insert = recordDao.insert(info);

			if (insert == 1) {
				LOGGER.info("insert card record success");
//				result.put("returnCode", "success");
//				result.put("returnMsg", "create card record success");
				return JsonResponse.success("create card record success",info);
			} else {
				LOGGER.error("insert card record fail , insert : " + insert);
				result.put("returnCode", "fail");
				result.put("returnMsg", "create card record fail");
				return JsonResponse.failure("create card record fail ");
			}
		} catch (RuntimeException e) {
			LOGGER.error("insert card record fail", e);
			return JsonResponse.failure("create card record error ");
		}
	}

	@Override
	public boolean isActive(Map<String, Object> param) {

		UserCardInfo card = cardDao.isActive(Integer.valueOf(String.valueOf(param.get("cardId"))));
		if (card != null) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> queryCardInfo(Integer id) {
		
		return cardDao.queryCardByUser(id);
	}

	@Override
	public boolean hasCard(Integer id) {
		int num = cardDao.queryCardByUserUse(id);
		if(num == 0){
			return true;
		}
		return false;
	}

}
