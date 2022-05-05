package kr.co.jparangdev.securitydemo.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.jparangdev.securitydemo.model.User;
import kr.co.jparangdev.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * 시큐리티 설정에서 loginProcessUrl로 설정된 요청이 오면 자동으로 UserDetailsService 타입으로 되어있는
 * loadUserByUsername 함수가 실행된다.
*/
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	// Security Session => Authentication = UserDetails
	//
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User findUser = userRepository.findByUsername(username);
		System.out.println(findUser.toString());
		return new PrincipalDetails(findUser); // Authentication 객체로 들어간다.
	}
}
