package kr.co.jparangdev.securitydemo.config.oauth;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.co.jparangdev.securitydemo.config.auth.PrincipalDetails;
import kr.co.jparangdev.securitydemo.config.oauth.provider.GoogleUserInfo;
import kr.co.jparangdev.securitydemo.config.oauth.provider.NaverUserInfo;
import kr.co.jparangdev.securitydemo.config.oauth.provider.OAuth2UserInfo;
import kr.co.jparangdev.securitydemo.model.User;
import kr.co.jparangdev.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // 빈으로 설정하면 오류가난다.
	private final UserRepository userRepository;

	/**
	 * 구글로부터 받은 userRequest 데이터에 후처리가 되는 함수
	 * super.loadUser(userRequest) == 구글로그인버튼 -> 구글 로그인 -> 로그인완료 -> code를 리턴(OAuthClient) -> AccessToken을 요청해서 받은 정보가
	 * userReqeust이다. 이를 회원 프로필을 받아야하는데 이때 쓰이는 함수가 loadUser함수이다. 이를 통해 구글로부터 회원프로필을 받을 수 있다.
	*/
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest =>"+ userRequest.getClientRegistration()+"/"+userRequest.getAccessToken().getTokenValue()+"/"+userRequest.getAdditionalParameters());

		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println("super=>"+oAuth2User.getAttributes());

		OAuth2UserInfo oAuth2UserInfo = null;
		String provider = userRequest.getClientRegistration().getRegistrationId();

		if("google".equals(provider)) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if("naver".equals(provider)) {
			oAuth2UserInfo = new NaverUserInfo((Map<String, Object>)oAuth2User.getAttributes().get("response"));
		}


		String providerId = oAuth2UserInfo.getProviderId();
		String username = provider+"_"+providerId;
		String password = encoder.encode("임시암호");
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";

		User findUser = userRepository.findByUsername(username);
		if(findUser == null) {
			// 회원가입 진행
			findUser = User.builder()
				.username(username)
				.password(password)
				.email(email)
				.roles(role)
				.provider(provider)
				.providerId(providerId)
				.build();
			userRepository.save(findUser);
		} else {
			// 수정사항이 있으면 만들듯
		}

		return new PrincipalDetails(findUser, oAuth2User.getAttributes());
	}
}
