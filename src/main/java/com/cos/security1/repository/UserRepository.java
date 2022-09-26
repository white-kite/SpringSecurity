package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

//CRUD 함수를 JpaRepository가 들고 있음
//@Repository라는 어노테이션이 없어도 IoC되는데 이유는 JpaRepository를 상속했기 때문이다.
public interface UserRepository extends JpaRepository<User, Integer>{

	//findBy규칙 -> Username문법
	//select * from usere where username = 1? //파라미터값이 들어간다.
	public User findByUsername(String username); //Jpa Query Method
	
}
