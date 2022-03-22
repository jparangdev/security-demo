package kr.co.jparangdev.securitydemo.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter3 implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		// 토큰 : cos를 만들어줘야함 id와 pw가 정상적으로 들어와서 로그인이 완료될때 토큰을 만들고 이를 응답으로 보내줌
		// 그럼 클라이언트는 이를 받아서 요청시마다 토큰을 포함해서 보내준다
		// 그때 토큰이 넘어오면 내가 만든토큰이 맞는지만 확인하고 검증만하면된다 .(RSA, HS256)
		System.out.println("필터3");
		// 토큰 : 코스 - 토큰을 얻어내서 토큰이 있다면 필터를 진행하고 아니면 못하게
		if(req.getMethod().equals("POST")) {
			String headerAuth = req.getHeader("Authorization");
			System.out.println(headerAuth);

			if(headerAuth.equals("cos")) {
				chain.doFilter(request,response);
			} else {
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
			}
		}
		// 필터 3는 시큐리티가 작동되기 전에 동작되어야하기 때문에 SecurityConfig에서 설정해줘야한다.
	}
}
