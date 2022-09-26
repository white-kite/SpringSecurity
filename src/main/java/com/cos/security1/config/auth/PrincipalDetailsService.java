package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;


//시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service //다른곳에 안띄운 이유는 나중에 강제로 띄울것이기 때문이다.
public class PrincipalDetailsService implements UserDetailsService {
//https://kitty-geno.tistory.com/131
	//UserDetailsService 인터페이스를 implements하여 Spring Security에서 유저의 정보를 가져온다.
	@Autowired
	private UserRepository userRepository;
	
	//시큐리티 session에 Authentication 타입있고 이 안에 UserDetails 있다.
	//시큐리티 session{Authentication{UserDetails}}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity); //UserDetails 값을 Authentication에 넣어주고 return하면
			//시큐리티 Session에 loadUserByUsername 메소드가 UserDetails 반환값으로 가지고 간다.
			//이렇게 하면 로그인이 완료가 된다.
		}
		return null;
	}
	
}
