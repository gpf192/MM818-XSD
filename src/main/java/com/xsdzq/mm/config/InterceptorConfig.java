package com.xsdzq.mm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.xsdzq.mm.interceptor.AuthenticationInterceptor;
import com.xsdzq.mm.interceptor.RefererInterceptor;

@Configuration

public class InterceptorConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(refererInterceptor());
		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
	}

	@Bean
	public AuthenticationInterceptor authenticationInterceptor() {
		return new AuthenticationInterceptor();
	}

	@Bean
	public RefererInterceptor refererInterceptor() {
		return new RefererInterceptor();
	}

}
