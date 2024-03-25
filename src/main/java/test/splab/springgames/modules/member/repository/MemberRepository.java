package test.splab.springgames.modules.member.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.splab.springgames.modules.member.Level;
import test.splab.springgames.modules.member.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    @EntityGraph("Member.withGameCardList")
    Optional<Member> findMemberWithGameCardListByMemberId(Long id);

    Optional<Member> findMemberByEmail(String email);

    List<Member> findAllByLevel(Level level, Sort sort);
}
