package com.cos.security1.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.config.auth.PrincipalDetailsService;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //view를 리턴하겠다는 뜻
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private PrincipalDetailsService Pservice;
	
	//localhost:8080
	@GetMapping({"","/"})
	public @ResponseBody String index() {
		//머스테치 기본폴더 src/main/resources/
		//뷰리풀버 설정 : templates(prefix),mustache(suffix)생략가능!!
		return "index"; // src/main/resources/templates/index.mustache
	}
	
	@GetMapping("/user") 
	public String whois(Principal principal, Model model){
		//https://m.boostcourse.org/web326/lecture/60592?isDesc=false
		//사용자가 로그인을 한 상태라면 스프링 MVC는 컨트롤러 메소드에 회원정보를 저장하고 있는 Principal 객체를 파라미터로 받을 수 있습니다.
		/*String userid = principal.getName();
		User user = (user) Pservice.loadUserByUsername();
		model.addAttribute("user",user);*/
//		https://m.boostcourse.org/web326/lecture/60592?isDesc=false
		return "user";
	}
	
	@GetMapping("/admin") 
	public @ResponseBody String admin(){
		return "admin";
	}
	
	@GetMapping("/manager") 
	public @ResponseBody String manager(){
		return "manager";
	}
	
	//스프링 시큐리티가 해당 주소를 낚아채는 것 - SecurityConfig파일 생성 후 작동 안함
	@GetMapping("/loginForm") 
	public String loginForm(){
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join") 
	public String join(User user){
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user); //회원가입 잘됨, 비밀번호 1234 => 시큐리티로 로그인 할 수 없음, 이유는 패스원드 암호화가 안되어 있기 때문
		return "redirect:/loginForm";
	}
	
	/*@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료됨";
	}*/
	
	@Secured("ROLE_ADMIN") //하나만 걸고 싶을때
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	//CONFIG파일에서 걸면 글로벌로 거는 것이고 특정한 것에만 권한을 걸 수 있다. @PostAuthorize 도 사용 가능
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //데이터 메소드 실행 직전에 실행되는 어노테이션, 하나 또는 두개 이상 걸고 싶을때
 	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}
}
