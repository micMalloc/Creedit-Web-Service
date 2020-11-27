package kr.creedit.api.front.member.controller;

import kr.creedit.api.front.config.LoginMember;
import kr.creedit.api.front.member.SessionMember;
import kr.creedit.api.front.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("save", MemberDto.Save.builder().build());
        return "member/sign-up";
    }

    @GetMapping("/has-role")
    public String hasRole(@LoginMember SessionMember sessionMember) {
        if(sessionMember == null) {
            return "member/no-role";
        }

        return "member/has-role";
    }
}
