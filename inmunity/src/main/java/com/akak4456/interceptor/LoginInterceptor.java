package com.akak4456.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.java.Log;
@Component
@Log
public class LoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
		
		String dest = request.getParameter("dest");
		
		if(dest != null) {
			request.getSession().setAttribute("dest", dest);
		}
		log.info("prehandle:"+dest);
		return super.preHandle(request,response,handler);
	}
	
}
