package test.splab.springgames.modules.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.splab.springgames.modules.member.level.Level;
import test.splab.springgames.modules.member.dto.GetMemberListResultDto;
import test.splab.springgames.modules.member.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    /**
     * 처음 페이지에 접속했을 때 보이는 메인 Home 페이지입니다.
     * 회원 목록을 제공합니다.
     * 페이지 정보 : 한 페이지당 10명의 회원이 노출됩니다.
     * @return (view) index
     */
    @GetMapping("/")
    public String home(
            @RequestParam(value="page",defaultValue = "1") int page,
            @RequestParam(value = "level", defaultValue = "ALL") String level,
            @RequestParam(value = "name", required = false) String name,
            Model model) {
        PageRequest pageInfo = PageRequest.of(page-1, 10, Sort.by("joinAt", "memberId").descending());
        GetMemberListResultDto data = memberService.getMemberListByParamAndPage(Level.of(level), name, pageInfo);
        model.addAttribute("searchName", name);
        model.addAttribute("currentLevel", level);
        model.addAttribute("data", data);
        return "index";
    }
}
