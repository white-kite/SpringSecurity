package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

//시큐리티가 /login주소 요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인 진행이 완료 되면 시큐리티 session을 만들어준다. (Security ContextHolder)
//오브젝트 => Authentication 타입 객체
//Authentication안에 User정보가 있어야됨
//User오브젝트타입 => UserDetails 타입 객체

//Security Session => Authentication => UserDetails

public class PrincipalDetails implements UserDetails{

	private User user; //컴포지션
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//해당 User의 권한을 리턴하는 곳!!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
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

	//사용하지 않을 것은 true로 변경해준다.
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		//우리 사이트 1년동안 로그인 안하면 휴면 계정으로 돌리겠다. 
		//이걸 해주려면 User에 private Timestamp loginDate;를 넣어줘야한다.
		//로그인하는 날짜를 받는것이다.
		//user.getLoginDate으로
		//현재시간 - 로그인시간 => 1년 초과시 return false;
		return true;
	}
	
}
