package test.splab.springgames.modules.card.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.splab.springgames.exception.BusinessLogicException;
import test.splab.springgames.exception.ExceptionCode;
import test.splab.springgames.modules.card.GameCard;
import test.splab.springgames.modules.card.dto.CardEnrollFormDto;
import test.splab.springgames.modules.card.repository.GameCardRepository;
import test.splab.springgames.modules.game.Game;
import test.splab.springgames.modules.game.repository.GameRepository;
import test.splab.springgames.modules.member.Member;
import test.splab.springgames.modules.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GameCardServiceImpl implements GameCardService {

    private final GameCardRepository gameCardRepository;
    private final MemberRepository memberRepository;
    private final GameRepository gameRepository;

    @Transactional
    @Override
    public void saveNewCard(CardEnrollFormDto cardEnrollFormDto) {
        Member member = getMemberByMemberId(cardEnrollFormDto.getMemberId());
        Game game = getGameByGameName(cardEnrollFormDto.getGameType());
        GameCard gameCard = cardEnrollFormDto.toEntity();
        gameCard.addMember(member);
        gameCard.addGame(game);
        gameCardRepository.save(gameCard);

        // 사용자 소유 총 카드 관련 정보 갱신
        member.updateCardTotalCountAndPrice();
        // TODO 레벨 변경 및 slack 알림 전송
        boolean result = member.isChangeLevelAccordingToPolicy();
        if (result) log.info("level changed!!! {}", member.getLevel());
    }

    @Transactional
    @Override
    public void removeGameCardById(Long cardId) {
        GameCard card = checkValidCardId(cardId);
        Member member = card.getMember();
        gameCardRepository.deleteById(cardId);
        member.getGameCardList().remove(card);
        member.updateCardTotalCountAndPrice();
        boolean result = member.isChangeLevelAccordingToPolicy();
        if (result) log.info("level changed!!! {}", member.getLevel());
    }

    private GameCard checkValidCardId(Long cardId) {
        return gameCardRepository.findById(cardId)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.CARD_NOT_FOUND));
    }

    private Game getGameByGameName(String name) {
        return gameRepository.findGameByName(name)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.GAME_NOT_FOUND));
    }

    private Member getMemberByMemberId(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
