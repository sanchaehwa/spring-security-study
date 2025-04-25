package com.example.testsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class loginController {

    @GetMapping("/login")
    public String loginP(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }
    //loginProc는 이미 스프링 시큐리티에서 처리하고 있기에 GET 매핑해주면 안됨

    //로그인 성공시
    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "loginSuccess"; // 로그인 성공 후 화면
    }

}
