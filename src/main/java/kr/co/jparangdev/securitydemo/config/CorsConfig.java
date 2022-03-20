package kr.co.jparangdev.securitydemo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 내서버가 응답을 할때 json을 자바스크립틍서 처리할 수 있게 할지를 설정하는 것??
		config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용한다
		config.addAllowedHeader("*");	// 모든 헤더의 응답을 허용한다.
		config.addAllowedMethod("*");	// 모든 메소드의 요청을 허용하겠다.
		source.registerCorsConfiguration("/api/**",config);
		return new CorsFilter(source);
	}
}
