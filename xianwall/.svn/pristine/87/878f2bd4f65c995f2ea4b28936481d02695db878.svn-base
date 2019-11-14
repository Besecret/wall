package com.wanding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class StaticResourceConfig extends WebMvcConfigurationSupport {
	@Autowired
	private WebInterceptor webInterceptor;

	@Autowired
	private WebInterceptorConf conf;
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }  
    
        @Override
    	public void addInterceptors(InterceptorRegistry registry) {
        	registry.addInterceptor(webInterceptor).addPathPatterns(conf.addList()).excludePathPatterns(conf.excludeList());
    	}
}
