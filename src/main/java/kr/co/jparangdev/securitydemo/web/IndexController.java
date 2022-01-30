package kr.co.jparangdev.securitydemo.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.jparangdev.securitydemo.model.User;
import kr.co.jparangdev.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	@GetMapping({"","/"})
	public String index() {
		return "index";
	}

	@GetMapping("/user")
	public @ResponseBody String user(){
		return "user";
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
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return "redirect:/loginForm";
	}

}

