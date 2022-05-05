package kr.co.jparangdev.securitydemo.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * 스프링 시큐리티에서 존재하는 UsernamePasswordAuthenticationFilter
 * 해당필터는 폼로그인에서 기본적으로 작동한다. /login에서 username, password를 post로 전송하면 작동한다.
 * 하지만 지금 formLogin을 disable()때문에 다시 재 지정해서 securit config에 등록한다.
*/

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	// 로그인 시도시 실행되는 메소드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		System.out.println("JwtAuthenticationFilter 로그인 시도중");
		// 1. username, password 받음
		// 2. 정상인지 아닌지 로그인 시도를 진행 -> authenticationManager 이걸 통해서 로그인 시도를하면 PrincipalDetailsService loadUserByUsername()함수를 실행한다.
		// 3. PrincipalDetails 를 세션에 담는다 (권한을 관리하기위해 담는다)
		return super.attemptAuthentication(request, response);
	}
}
