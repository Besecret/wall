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

import com.wanding.service.CardService;

@RestController
@RequestMapping("card")
public class CardManagerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CardManagerController.class);
	@Autowired
	private CardService cardService;

	@PostMapping("activate")
	public Map<String, String> activateCard(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {
		LOGGER.info("activate huimin card start ");

		Map<String, String> result = cardService.activateCard(param);

		return result;
	}

	@PostMapping("useRecord")
	public Map<String, String> useRecord(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {
		LOGGER.info("useRecord query start ");

		Map<String, String> result = cardService.useRecord(param);

		return result;
	}

	@PostMapping("useCard")
	public Map<String, String> useCard(HttpServletRequest req, HttpServletResponse res,
			@RequestBody() Map<String, Object> param) {
		LOGGER.info("useCard start ...  param : " + param);
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			boolean canUse = cardService.isActive(param);
			if(canUse){
				result = cardService.createCardRecord(param);
			}else{
				result.put("returnCode", "fail");
				result.put("returnMsg", "card is not active");
			}
		} catch (Exception e) {
			LOGGER.info("use Card error",e);
			result.put("returnCode", "fail");
			result.put("returnMsg", "create card record error");
		}
		return result;
	}
}
