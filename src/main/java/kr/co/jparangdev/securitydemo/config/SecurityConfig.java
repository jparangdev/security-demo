package kr.co.jparangdev.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

import kr.co.jparangdev.securitydemo.config.oauth.PrincipalOauth2UserService;
import kr.co.jparangdev.securitydemo.filter.MyFilter1;
import kr.co.jparangdev.securitydemo.filter.MyFilter3;
import lombok.RequiredArgsConstructor;

/**
 * 1. 코드받기(인증)
 * 2. 액세스토큰(권한)
 * 3. 사용자 프로필정보를 가져옴
 * 4-1. 구글로 받은 내용이 충분하다면 자동으로 회원가입 진행
 * 4-2. 추가정보가 필요하다면 회원가입으로 이동
*/

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 필터 체인에 등록이 됩니다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션을 활성화 한다., PreAuthorize, PostAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final PrincipalOauth2UserService principalOauth2UserService;

	private final CorsFilter corsFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class); // 시큐리티 필터 체인이 일반적인 필터체인보다 일찍돈다.
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션사용안함
			.and()
			.addFilter(corsFilter) // @CroossOrigin(인증X), 시큐리티에 필터 인증을 하는게 좋다.
			.formLogin().disable() // 폼로그인 사용안함
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers("/api/v1/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest()
			.permitAll()
		; // 세션을 사용하지 않겠다는 설정
	}

	@Bean // 해당 메소드로 리턴되는 오브젝트를 컨테이너에 빈으로 등록
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
