package com.wanding.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:WebInterceptor.properties")
public class WebInterceptorConf {

	@Value("${exclude}")
	private String exclude;

	@Value("${add}")
	private String add;

	public List<String> addList() {

		List<String> list = new ArrayList<String>();

		list.addAll(Arrays.asList(add.split(",")));

		return list;
	}

	public List<String> excludeList() {
		List<String> list = new ArrayList<String>();
		if (add != null && !add.isEmpty()) {
			list.addAll(Arrays.asList(exclude.split(",")));
		}
		return list;
	}

}
