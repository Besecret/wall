package com.wanding.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
/**
* druid 配置.
* 这样的方式不需要添加注解：@ServletComponentScan
* websphere低版本 不支持注解配置     更改为web.xml配置
* @author Administrator
*/

@Configuration
public class DruidConfiguration {

  /**
   * 注册一个StatViewServlet
   * @return
   */
  @Bean
  public ServletRegistrationBean DruidStatViewServle(){
     //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
     ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
     //添加初始化参数：initParams
     //白名单：
     servletRegistrationBean.addInitParameter("allow","127.0.0.1");
     //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
     servletRegistrationBean.addInitParameter("deny","192.168.1.73");
     //登录查看信息的账号密码.
     servletRegistrationBean.addInitParameter("loginUsername","admin");
     servletRegistrationBean.addInitParameter("loginPassword","123456");
     //是否能够重置数据.
     servletRegistrationBean.addInitParameter("resetEnable","false");
     return servletRegistrationBean;
  }

  /**
   * 注册一个：filterRegistrationBean
   * @return
   */
  @Bean
  public FilterRegistrationBean druidStatFilter(){
     FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
     //添加过滤规则.
     filterRegistrationBean.addUrlPatterns("/druid/*");
     //添加不需要忽略的格式信息.
     filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
     filterRegistrationBean.addInitParameter("profileEnable", "true");
     filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
     filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");

     return filterRegistrationBean;
  }



}