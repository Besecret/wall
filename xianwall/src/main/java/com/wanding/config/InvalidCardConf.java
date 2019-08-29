package com.wanding.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wanding.dao.UserCardInfoMapper;

@Component
public class InvalidCardConf {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvalidCardConf.class);

	@Autowired
	private UserCardInfoMapper cardDao;

	@Scheduled(cron = "00 0 0 * * ?")
	private void invalidCard() {
		LOGGER.info("start to check huimin card ...");
		int invalidCard = cardDao.invalidCard();

		LOGGER.info("check huimin card end... result : " + invalidCard);
	}
}
