package com.wanding.config;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wanding.dao.UserInfoMapper;
import com.wanding.model.UserInfo;
import com.wanding.util.JsonResponse;

@Component
public class WebInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebInterceptor.class);

	@Autowired
	private UserInfoMapper userMapper;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		String sessionKey = httpServletRequest.getHeader("sessionKey");
		LOGGER.info(httpServletRequest.getRequestURI());
		if (sessionKey == null || sessionKey.trim().isEmpty()) {
			String failure = JsonResponse.timeOut("session time out！");
			ServletOutputStream os = httpServletResponse.getOutputStream();
			os.write(failure.toString().getBytes("UTF-8"));
			os.flush();
			os.close();
//			httpServletResponse.sendRedirect("/login");
			return false;
		} else {
			UserInfo user = userMapper.findBySession(sessionKey);
			if (user == null) {
				String failure = JsonResponse.timeOut("session time out！");
				ServletOutputStream os = httpServletResponse.getOutputStream();
				os.write(failure.toString().getBytes("UTF-8"));
				os.flush();
				os.close();
//				httpServletResponse.sendRedirect("/login");
				return false;
			}
			httpServletRequest.getSession().setAttribute("user", user);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {

	}
}
