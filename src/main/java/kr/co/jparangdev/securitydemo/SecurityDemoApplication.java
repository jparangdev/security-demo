package kr.co.jparangdev.securitydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SecurityDemoApplication {
	@Bean // 해당 메소드로 리턴되는 오브젝트를 컨테이너에 빈으로 등록
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}

}
