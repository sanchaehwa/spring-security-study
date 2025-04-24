package com.example.testsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {
    @GetMapping("/")
    public String mainP(Model model) {
        //SecurityContextHolder -> getContext : 현재 로그인된 사용자 정보 getAuth-: 사용자정보 ->,getName() 해서 사용자의 아이디를 가져옴
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //사용자 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //권한 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("username",username);
        model.addAttribute("role",role);
        return "main";
    }
}
