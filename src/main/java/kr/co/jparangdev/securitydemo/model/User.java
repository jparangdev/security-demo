package kr.co.jparangdev.securitydemo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	private String email;
	private String roles; // USER, ADMIN

	private String provider; // 로그인 제공자
	private String providerId; //

	@CreationTimestamp
	private LocalDateTime createDate;

	public List<String> getRoleList(){
		if(this.roles.length() > 0) {
			return Arrays.asList(roles.split(","));
		}
		return new ArrayList<>();
	}

}
