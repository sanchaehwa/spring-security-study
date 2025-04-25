package com.example.testsecurity.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Spring Security가 커스텀한 설정 클래스를 인식하게 하는 어노테이션 -  이 파일에서 정한 인가 규칙대로 동작하도록 함
public class SecurityConfig {
    @Bean
    //BCrypt 암호화 매서드
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
//계층 권한 설정
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //HttpSecurity : 어떤 요청 경로에 어떤 인증/인가 정책에 적용할지
        http
                //authorizeHttpRequests : HttpSecurity 객체 안에서 요청별로 인가 규칙을 정의하는 빌더 매서드
                .authorizeHttpRequests((auth) -> auth //.requestMatchers : 경로에 특정한 작업을 진행하고 싶을때 설정
                        .requestMatchers("/login","/join").permitAll() //permitAll : 어떤 사용자도 접근할 . 있음
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**","/loginSuccess").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated() //다른 경로는 로그인만하면 접근 가능하게
                );
        //admin 이나 로그인하면 접근 가능하게 설정해두고, 만약 로그인이 안된 사용자다 하면 로그인폼으로 리다렉트되게 설정
        http
                .formLogin((auth )-> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/loginSuccess", true) // 로그인 성공 시 이동 경로
                        .failureUrl("/login?error=true") // 실패 시 이 경로로 이동
                        .permitAll());
        //csrf 토큰을 보내지않으면 로그인 안되서 일단은 disable
        http
                .csrf((auth)->auth.disable() );
        //동일한 아이디로 다중 로그인을 진행할 경우에 세션 통제
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) //다중 로그인 허용 개수
                        .maxSessionsPreventsLogin(true)); //설정한 1 이라는 값을 초과하면 true는 새로운 로그인 차단 False로 하면 초과시 기존 세션 하나 삭제
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()); //로그인시 세션 새로 만듬
        return http.build();

    }


    }




