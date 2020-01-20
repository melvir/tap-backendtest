package sg.gov.csit.entity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import sg.gov.csit.entity.interceptor.LoginInterceptor;

/**
 * Web configuration for Spring Boot application
 * Currently only used to implement Interceptors
 * @author Seo Wee Kiat
 *
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer
{
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		//registry.addInterceptor(loginInterceptor()).excludePathPatterns("/login/**", "/info/**", "/error", "/test/**", "/api/v1/server/**");
	}

	/*
	@Bean
	public LoginInterceptor loginInterceptor()
	{
		return new LoginInterceptor();
	}
	 */
}
