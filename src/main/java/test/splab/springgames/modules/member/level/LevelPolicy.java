package test.splab.springgames.modules.member.level;

import test.splab.springgames.modules.card.GameCard;

import java.util.List;

public class LevelPolicy {

    /**
     * 카드목록이 Silver 레벨에 해당하는지 체크합니다.
     * - 유효카드가 1장 이상
     * @param list
     * @return boolean
     */
    public static boolean isSilver(List<GameCard> list) {
        return list.stream().anyMatch(card -> card.getPrice() > 0);
    }

    /**
     * 카드목록이 Gold 레벨에 해당하는지 체크합니다.
     * - 유효카드가 4장이거나, 2~3 장의 유효카드가 합이 100달러 이상
     * - 유효카드 중 서로 다른 게임이 2개 이상 존재
     * @param list
     * @return boolean
     */
    public static boolean isGold(List<GameCard> list) {
        return isMoreThanTwoDistinctGames(list)
                && (isFourOrMorePaidCards(list)
                        || isTwoCardsSumOver100Dollars(list)
                        || isThreeCardsSumOver100Dollars(list));
    }


    // 게임 카드 목록 중 유효카드 개수가 4개 이상인지 확인
    private static boolean isFourOrMorePaidCards(List<GameCard> list) {
        return list.stream().filter(card -> card.getPrice() > 0).count() >= 4;
    }

    // 유효카드 중에 서로 다른 게임이 2개 이상 존재하는지 확인
    private static boolean isMoreThanTwoDistinctGames(List<GameCard> list) {
        return list.stream()
                .filter(card -> card.getPrice() > 0)
                .map(card -> card.getGame().getName())
                .distinct()
                .count() >= 2;
    }

    // 2장의 유료카드의 합이 100달러 이상인지 확인
    private static boolean isTwoCardsSumOver100Dollars(List<GameCard> list) {
        return list.stream()
            .filter(card -> card.getPrice() > 0)
            .mapToDouble(GameCard::getPrice)
            .sorted()
            .limit(2)
            .sum() >= 100;
    }

    // 3장의 유료카드의 합이 100달러 이상인지 확인
    private static boolean isThreeCardsSumOver100Dollars(List<GameCard> list) {
        return list.stream()
                .filter(card -> card.getPrice() > 0)
                .mapToDouble(GameCard::getPrice)
                .sorted()
                .limit(3)
                .sum() >= 100;
    }

}
