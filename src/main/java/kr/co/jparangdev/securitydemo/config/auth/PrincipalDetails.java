package kr.co.jparangdev.securitydemo.config.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import kr.co.jparangdev.securitydemo.model.User;

/**
 * 시큐리티가 /login을 낚아채서 로그인을 진행한다.
 * 로그인이 진행이 완료가되면 시큐리티 session을 만들어준다. -> Security ContextHolder에 세션정보(인증정보)를 저장해둔다
 * 오브젝테 Authentication 타입의 객체를 저장한다.
 * Authentication안에 User정보는 UserDetails타입 객체여야한다.
 *
 * Security Session => Authentication => UserDetails
*/
public class PrincipalDetails implements UserDetails, OAuth2User, Serializable {

	private User user; // 콤포지션?
	private Map<String, Object> attributes;

	public PrincipalDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes; // OAuth로그인시 주는 정보드를 저장해야한다.
	}

	@Override
	public String getName() {
		return null; // 별로 중요하지안핟고함
	}

	// 해당 User의 권한을 리턴하는 곳
	// 다만 타입변경이 필요한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoleList().forEach(role->{
			authorities.add(()->role);
		});
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {

		// 휴면계정으로 전환하기 위한 내용 만약 휴면으로 바꾼다면 로그인 마지막시간과 비교해서 결과 반환

		return true;
	}
}
