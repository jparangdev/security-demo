package kr.co.jparangdev.securitydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.jparangdev.securitydemo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
