package test.splab.springgames.modules.main;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.splab.springgames.modules.member.Level;
import test.splab.springgames.modules.member.dto.MemberListResultDto;
import test.splab.springgames.modules.member.service.MemberService;

import java.util.List;

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
    public String home(@RequestParam(value = "level", defaultValue = "ALL") String level,
                       @RequestParam(value = "name", required = false) String name,
                       Model model) {
        List<MemberListResultDto> memberList = memberService.getMemberList(Level.of(level), name);
        model.addAttribute("searchName", name);
        model.addAttribute("currentLevel", level);
        model.addAttribute("memberList", memberList);
        return "index";
    }
}
