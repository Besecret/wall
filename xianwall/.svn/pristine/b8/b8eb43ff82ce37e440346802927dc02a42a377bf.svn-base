package com.wanding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wanding.util.WebInterceptor;

@SuppressWarnings("deprecation")
@SpringBootConfiguration
public class WebMvnConfigurer extends WebMvcConfigurerAdapter {
	@Autowired
	private WebInterceptor webInterceptor;

	@Autowired
	private WebInterceptorConf conf;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(webInterceptor).addPathPatterns(conf.addList()).excludePathPatterns(conf.excludeList());
		super.addInterceptors(registry);
	}

}
