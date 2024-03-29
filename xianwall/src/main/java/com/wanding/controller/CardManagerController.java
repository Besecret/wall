package com.wanding.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.wanding.model.CardUseRecordInfo;
import com.wanding.model.UserInfo;
import com.wanding.service.CardService;
import com.wanding.util.JsonResponse;

@RestController
@RequestMapping("card")
public class CardManagerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CardManagerController.class);
	@Autowired
	private CardService cardService;

	@PostMapping("activate")
	public Map<String, Object> activateCard(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {
		LOGGER.info("activate huimin card start ");

		Map<String, Object> result = cardService.activateCard(param);

		return result;
	}

	@PostMapping("useRecord")
	public Map<String, Object> useRecord(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {
		LOGGER.info("useRecord query start ");
		UserInfo user = (UserInfo) req.getSession().getAttribute("user");
		param.put("userId", user.getId());
		Map<String, Object> result = cardService.useRecord(param);

		return result;
	}

	@PostMapping("useCard")
	public Map<String, Object> useCard(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {
		LOGGER.info("useCard start ...  param : " + param);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			boolean canUse = cardService.isActive(param);
			if (canUse) {
				result = cardService.createCardRecord(param);
			} else {
				result.put("returnCode", "fail");
				result.put("returnMsg", "card is not active");
				return JsonResponse.failure("card is not active");

			}
		} catch (Exception e) {
			LOGGER.info("use Card error", e);
			return JsonResponse.failure("create card record error");

		}
		return result;
	}

	@PostMapping("queryCard")
	public Map<String, Object> queryCard(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {

		LOGGER.info("queryCard start ...  param : " + param);
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo user = (UserInfo) req.getSession().getAttribute("user");
		try {
			Map<String, Object> queryCardInfo = cardService.queryCardInfo(user.getId());
			if (queryCardInfo == null) {
				LOGGER.info("query Card result is null");
				return JsonResponse.failure("没有惠民卡");
			}
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date InvalidTime = (Date) queryCardInfo.get("InvalidTime");
			if (InvalidTime != null) {
				queryCardInfo.put("InvalidTime", s.format(InvalidTime));
			}
			Date CreatedTime = (Date) queryCardInfo.get("CreatedTime");
			if (CreatedTime != null) {
				queryCardInfo.put("CreatedTime", s.format(CreatedTime));
			}
			Date FirstUseTime = (Date) queryCardInfo.get("FirstUseTime");
			if (FirstUseTime != null) {
				queryCardInfo.put("FirstUseTime", s.format(FirstUseTime));
			}
			result.putAll(queryCardInfo);
			result.put("user", user);
		} catch (Exception e) {
			LOGGER.info("query Card error", e);
			return JsonResponse.failure("查询惠民卡异常");
		}

		return JsonResponse.success("查询惠民卡成功", result);
	}

	@PostMapping("generateTicket")
	public Map<String, Object> generateTicket(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {
 		UserInfo user = (UserInfo) req.getSession().getAttribute("user");
		HashMap<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> useCard = useCard(req, res, param);
		CardUseRecordInfo record = (CardUseRecordInfo) useCard.get("data");
		
		Map<String, Object> queryCardInfo = cardService.queryCardInfo(user.getId());
		result.put("user", user);
		result.put("record", record);
		result.put("card", queryCardInfo);
		return JsonResponse.success("生成电子票成功", result);
	}
}
