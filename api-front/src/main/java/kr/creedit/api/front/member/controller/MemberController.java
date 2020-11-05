package kr.creedit.api.front.member.controller;

import kr.creedit.api.front.member.SessionOauthMember;
import kr.creedit.api.front.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final HttpSession httpSession;

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("save", MemberDto.Save.builder().build());
        return "member/sign-up";
    }

    @GetMapping("/test")
    public String test(Model model) {

        SessionOauthMember oauthMember = (SessionOauthMember) httpSession.getAttribute("oauthMember");

        if(oauthMember != null) {
            model.addAttribute("userName", oauthMember.getName());
        }

        return "test";
    }
}
