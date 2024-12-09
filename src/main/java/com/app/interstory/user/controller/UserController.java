package com.app.interstory.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.interstory.user.domain.CustomUserDetails;
import com.app.interstory.user.domain.entity.User;
import com.app.interstory.user.dto.vo.KakaoAPI;
import com.app.interstory.user.service.AuthenticationService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final KakaoAPI kakaoAPI;
    private final AuthenticationService authenticationService;

    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    //회원 가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    //kakao 로그인
    @GetMapping("/login/kakao")
    public String kakaoLogin() {
        System.out.println("유알엘 연결확인!!!!!!!!!!!!!!");

        return "redirect:" + kakaoAPI.getAuthorizationUrl();
    }

    //kakao callback
    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam String code,
                                HttpSession session) {
        User socialLoginUser = authenticationService.kakaoLogin(code);
        CustomUserDetails customUserDetails = authenticationService.authenticateKakao(socialLoginUser);
        log.info("customUserDetails:SocialLogin :::{}",customUserDetails);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/";
    }

    //회원탈퇴 페이지
    @GetMapping("/withdrawal")
    public String withdrawal(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        Model model
    ) {
        model.addAttribute("user", customUserDetails);


        return "user/withdrawal";
    }

}
