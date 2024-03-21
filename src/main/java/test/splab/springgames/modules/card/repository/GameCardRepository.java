package test.splab.springgames.modules.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.splab.springgames.modules.card.GameCard;
import test.splab.springgames.modules.member.Member;

public interface GameCardRepository extends JpaRepository<GameCard, Long> {

    @Query("SELECT " +
            "   CASE " +
            "       WHEN (COUNT(gc)>0) THEN true " +
            "       ELSE false " +
            "   END " +
            "FROM GameCard gc " +
            "WHERE gc.serialNumber = :serialNumber " +
            "AND gc.game.name = :game")
    boolean existsGameCardWithSerialNumberByGame(@Param("game") String game,
                                                 @Param("serialNumber") Long serialNumber);

    void deleteAllByMember(Member member);
}
