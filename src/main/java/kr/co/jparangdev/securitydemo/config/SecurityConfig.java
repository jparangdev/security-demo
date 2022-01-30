package kr.co.jparangdev.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.co.jparangdev.securitydemo.config.oauth.PrincipalOauth2UserService;
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
			.defaultSuccessUrl("/") // 로그인 성공시 진행될 Url
			.and()
			.oauth2Login()
			.loginPage("/loginForm")
			.userInfoEndpoint()
			.userService(principalOauth2UserService)// 구글 인증후 후처리가 필요함 Tip. 코드X(액세스토큰+사용자프로필정보 O)
		;
	}

	@Bean // 해당 메소드로 리턴되는 오브젝트를 컨테이너에 빈으로 등록
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
