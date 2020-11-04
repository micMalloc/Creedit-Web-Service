package kr.creedit.api.front.member.controller;

import kr.creedit.api.front.member.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("save", MemberDto.Save.builder().build());
        return "member/sign-up";
    }
}
