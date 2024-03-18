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
import test.splab.springgames.modules.member.dto.EditFormDto;
import test.splab.springgames.modules.member.dto.EnrollFormDto;
import test.splab.springgames.modules.member.dto.MemberDetailResultDto;
import test.splab.springgames.modules.member.dto.validator.EditFormValidator;
import test.splab.springgames.modules.member.dto.validator.EnrollFormValidator;
import test.splab.springgames.modules.member.service.MemberService;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final EnrollFormValidator enrollFormValidator;
    private final EditFormValidator editFormValidator;
    private final MemberService memberService;

    @InitBinder("enrollFormDto")
    public void enrollFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(enrollFormValidator);
    }

    @InitBinder("editFormDto")
    public void editFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(editFormValidator);
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

    @GetMapping("/edit/{member-id}")
    public String getMemberEditPage(@PathVariable("member-id") Long id, Model model) {
        model.addAttribute("editFormDto",memberService.getMemberEditFormById(id));
        return "member/edit";
    }

    @PostMapping("/edit")
    public String updateMemberInfo(@Valid EditFormDto editFormDto, Errors errors,
                                   Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            errors.getAllErrors()
                    .forEach(e ->
                            log.error("REASON={}", Objects.requireNonNull(e.getDefaultMessage())));
            model.addAttribute(editFormDto);
            return "member/edit";
        }
        memberService.updateMemberFromEditForm(editFormDto);
        attributes.addFlashAttribute("editMessage", "회원 정보를 성공적으로 수정하였습니다.");
        return "redirect:/member/detail/"+editFormDto.getId();
    }
}
