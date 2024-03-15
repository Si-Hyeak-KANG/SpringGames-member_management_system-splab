package test.splab.springgames.modules.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import test.splab.springgames.modules.member.Level;
import test.splab.springgames.modules.member.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    /**
     * 처음 페이지에 접속했을 때 보이는 메인 Home 페이지입니다.
     * 회원 목록을 제공합니다.
     * @return index-page
     */
    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("memberList", memberService.getMemberList());
        return "index";
    }
}
