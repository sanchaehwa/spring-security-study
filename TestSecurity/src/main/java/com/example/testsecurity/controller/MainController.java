package com.example.testsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String rootRedirect() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 익명 사용자면 로그인 안 한 것
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }

        // 로그인된 사용자면 성공 페이지로 이동
        return "redirect:/loginSuccess";
    }
}
