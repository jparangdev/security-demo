package kr.co.jparangdev.securitydemo.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.jparangdev.securitydemo.config.auth.PrincipalDetails;
import kr.co.jparangdev.securitydemo.model.User;
import kr.co.jparangdev.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
		System.out.println("/test/login ================");
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal(); // 다운캐스팅을 통해서도 정보를 가질수있다.
		System.out.println("authentication => "+principalDetails.getUser().toString());

		System.out.println("userDetails: "+userDetails.getUser().toString()); // 어노테이션을 통해 정보를 찾을 수 있다.
		return "세션정보확인하기";
	}

	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User user) {
		System.out.println("/test/login ================");
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal(); // 다운캐스팅을 통해서도 정보를 가질수있다.
		System.out.println("authentication => "+oAuth2User.getAttributes());

		System.out.println("User => "+user.getAttributes()); // Oauth정보도 이렇게 전달받는게 가능하다.
		return "Oauth 세션정보확인하기";
	}

	@GetMapping({"","/"})
	public String index() {
		return "index";
	}

	@GetMapping("/user")
	public @ResponseBody PrincipalDetails user(@AuthenticationPrincipal PrincipalDetails details){
		return details;
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	@GetMapping("/loginForm")
	public String login() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user.toString());
		user.setRole("ROLE_USER");
		user.setPassword(encoder.encode(user.getPassword())); // 암호화하지 않는다면 시큐리티에서 로그인 처리를 하지않는다.
		userRepository.save(user);
		return "redirect:/loginForm";
	}

	@Secured("ROLE_ADMIN") // 권한 직접명시 (메소드별로 따로 사용하고 싶을 때)
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}

	// @PostAuthorize() // 사용할 일은 별로 없다.
	@PreAuthorize("hasRole('ROLE_MANAGER')") // 해당 메소드 접근전에 인증해야할 로직 (다양한 인증방식을 걸고 싶을때)
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터";
	}

}

