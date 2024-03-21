package test.splab.springgames.modules.card.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import test.splab.springgames.modules.card.dto.CardEnrollFormDto;
import test.splab.springgames.modules.card.dto.CardRemoveRequestDto;
import test.splab.springgames.modules.card.dto.validator.CardEnrollFormValidator;
import test.splab.springgames.modules.card.service.GameCardService;
import test.splab.springgames.modules.game.service.GameService;
import test.splab.springgames.modules.member.service.MemberService;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/card")
@Controller
public class GameCardController {

    private final CardEnrollFormValidator cardEnrollFormValidator;
    private final MemberService memberService;
    private final GameService gameService;
    private final GameCardService gameCardService;

    public static final String CARD_ENROLL_SUCCESS_MESSAGE = "카드를 성공적으로 추가하였습니다.";

    @InitBinder("cardEnrollFormDto")
    public void enrollFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(cardEnrollFormValidator);
    }

    @GetMapping("/enroll/member/{member-id}")
    public String getCardEnrollPage(@PathVariable("member-id") Long id, Model model) {
        memberService.isExistedMemberById(id);
        CardEnrollFormDto cardEnrollFormDto = new CardEnrollFormDto();
        cardEnrollFormDto.setMemberId(id);
        model.addAttribute(cardEnrollFormDto);
        model.addAttribute("gameList",gameService.getGameNameList());
        return "card/enroll";
    }

    @PostMapping("/enroll")
    public String enrollCardForm(@Valid CardEnrollFormDto cardEnrollFormDto,
                                 Errors errors, Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            errors.getAllErrors()
                    .forEach(e ->
                            log.error("REASON={}", Objects.requireNonNull(e.getDefaultMessage())));
            model.addAttribute("memberId",cardEnrollFormDto.getMemberId());
            model.addAttribute("gameList",gameService.getGameNameList());
            return "card/enroll";
        }
        gameCardService.saveNewCard(cardEnrollFormDto);
        attributes.addFlashAttribute("message", CARD_ENROLL_SUCCESS_MESSAGE);
        return "redirect:/member/detail/"+cardEnrollFormDto.getMemberId();
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity removeCard(@RequestBody @Valid CardRemoveRequestDto cardRemoveRequestDto) {
        gameCardService.removeGameCardById(cardRemoveRequestDto.getCardId());
        return ResponseEntity.ok().build();
    }
}
