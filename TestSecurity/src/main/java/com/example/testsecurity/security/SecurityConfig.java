package com.example.testsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Spring Security가 커스텀한 설정 클래스를 인식하게 하는 어노테이션 -  이 파일에서 정한 인가 규칙대로 동작하도록 함
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //HttpSecurity : 어떤 요청 경로에 어떤 인증/인가 정책에 적용할지
        http
                //authorizeHttpRequests : HttpSecurity 객체 안에서 요청별로 인가 규칙을 정의하는 빌더 매서드
                .authorizeHttpRequests((auth) -> auth //.requestMatchers : 경로에 특정한 작업을 진행하고 싶을때 설정
                        .requestMatchers("/","/login").permitAll() //permitAll : 어떤 사용자도 접근할 . 있음
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated() //다른 경로는 로그인만하면 접근 가능하게
                );
        //admin 이나 로그인하면 접근 가능하게 설정해두고, 만약 로그인이 안된 사용자다 하면 로그인폼으로 리다렉트되게 설정
        http
                .formLogin((auth)-> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll());
        //csrf 토큰을 보내지않으면 로그인 안되서 일단은 disable
        http
                .csrf((auth)->auth.disable() );
        return http.build();

    }


}
