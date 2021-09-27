package com.pankaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pankaj.filter.HeaderFilter;


@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public FilterRegistrationBean filter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		
		registrationBean.setFilter(new HeaderFilter());
		registrationBean.addUrlPatterns("/api/*");
		
		return registrationBean;
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/*").allowedOrigins("http://localhost:3000");
			}
		};
	}
	
}
