package kr.co.jparangdev.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 필터 체인에 등록이 됩니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() // 인증받아야 들어갈 수 있는 주소
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 인증뿐만아니라 어드민과 매니저권한이 있어야하낟.
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 어드민권한이 있어야한다.
			.anyRequest().permitAll() // 이외 모든페이지 권한 허용?
			.and()
			.formLogin()
			.loginPage("/loginForm")
			// .usernameParameter("username2") // username을 따로 변경해줄 때 사용
			.loginProcessingUrl("/login") // login이 호출되면 시큐리티가 알아서 로그인을 진행해준다.
			.defaultSuccessUrl("/"); // 로그인 성공시 진행될 Url
	}

	@Bean // 해당 메소드로 리턴되는 오브젝트를 컨테이너에 빈으로 등록
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
