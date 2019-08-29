package com.wanding.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

//启用自动配置
@Configuration
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})//exclude表示自动配置时不包括Multipart配置
@ComponentScan 
public class InitServletService extends SpringBootServletInitializer {
	private static final Logger logger = LoggerFactory.getLogger(InitServletService.class);

 

      //显示声明CommonsMultipartResolver为mutipartResolver
       @Bean(name = "multipartResolver")
       public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(40960);
        resolver.setMaxUploadSize(5*1024*1024);//上传文件大小 5M 50*1024*1024
        return resolver;
    }

    
}
