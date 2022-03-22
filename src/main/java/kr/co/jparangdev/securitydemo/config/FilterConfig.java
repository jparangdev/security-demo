package kr.co.jparangdev.securitydemo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.jparangdev.securitydemo.filter.MyFilter1;
import kr.co.jparangdev.securitydemo.filter.MyFilter2;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<MyFilter1> filter1() {
		FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
		bean.addUrlPatterns("/*"); // Url패턴 추가
		bean.setOrder(0); // 낮은번호 필터 중에서 가장 먼저 실행
		return bean;
	}

	@Bean
	public FilterRegistrationBean<MyFilter2> filter2() {
		FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
		bean.addUrlPatterns("/*"); // Url패턴 추가
		bean.setOrder(1); // 낮은번호 필터 중에서 가장 먼저 실행
		return bean;
	}
}
