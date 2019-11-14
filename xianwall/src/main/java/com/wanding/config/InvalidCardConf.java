package com.wanding.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wanding.dao.UserCardInfoMapper;
import com.wanding.dao.UserInfoMapper;

@Component
public class InvalidCardConf {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvalidCardConf.class);

	@Autowired
	private UserCardInfoMapper cardDao;
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Scheduled(cron = "00 0 * * * ?")
	private void invalidCard() {
		LOGGER.info("start to check huimin card ...");
		int invalidCard = cardDao.invalidCard();

		LOGGER.info("check huimin card end... result : " + invalidCard);
	}
	
	//清空过期session
	@Scheduled(cron = "00 0/5 * * * ?")
	private void invalidSession() {
		System.out.println("清空过期session");
		LOGGER.info("start to check check session expiresTime ...");
	    int count =  userInfoMapper.clearExpiresSession();
		LOGGER.info("check session expiresTime and clear data : " + count);
	}
	
	
}
