package com.wanding.service;

import java.util.Map;

import com.wanding.model.UserCardInfo;

public interface CardService {

	UserCardInfo createCard(Map<String, Object> param);

	Map<String, String> activateCard(Map<String, Object> param);

	Map<String, String> useRecord(Map<String, Object> param);

	Map<String, String> createCardRecord(Map<String, Object> param);

	boolean isActive(Map<String, Object> param);

}
