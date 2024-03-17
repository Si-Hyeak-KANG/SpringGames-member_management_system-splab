package test.splab.springgames.modules.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import test.splab.springgames.modules.member.dto.EnrollFormDto;
import test.splab.springgames.modules.member.dto.MemberDetailResultDto;
import test.splab.springgames.modules.member.dto.validator.EnrollFormValidator;
import test.splab.springgames.modules.member.service.MemberService;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final EnrollFormValidator enrollFormValidator;
    private final MemberService memberService;

    @InitBinder("enrollFormDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(enrollFormValidator);
    }

    @GetMapping("/enroll")
    public String getMemberEnrollPage(Model model) {
        model.addAttribute("enrollFormDto", new EnrollFormDto());
        return "member/enroll";
    }

    @PostMapping("/enroll")
    public String enrollMemberForm(@Valid EnrollFormDto enrollFormDto,
                                   Errors errors, Model model,RedirectAttributes attributes) {

        if (errors.hasErrors()) {
            errors.getAllErrors()
                    .forEach(e ->
                            log.error("REASON={}", Objects.requireNonNull(e.getDefaultMessage())));
            return "member/enroll";
        }
        memberService.saveNewMember(enrollFormDto);
        attributes.addFlashAttribute("message", "회원을 성공적으로 등록했습니다.");
        return "redirect:/";
    }

    @GetMapping("/detail/{member-id}")
    public String getMemberDetailPage(@PathVariable("member-id") Long id, Model model) {
        MemberDetailResultDto memberDetail = memberService.getMemberDetailById(id);
        model.addAttribute("memberDetail",memberDetail);
        return "member/detail";
    }
}
