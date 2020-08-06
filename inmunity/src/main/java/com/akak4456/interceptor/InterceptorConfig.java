package com.akak4456.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	private LoginInterceptor loginInterceptor;
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor).addPathPatterns("/login");
		registry.addInterceptor(localeChangeInterceptor);
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
