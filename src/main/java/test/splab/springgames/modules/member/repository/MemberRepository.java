package test.splab.springgames.modules.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.splab.springgames.modules.member.level.Level;
import test.splab.springgames.modules.member.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    @EntityGraph("Member.withGameCardList")
    Optional<Member> findMemberWithGameCardListByMemberId(Long id);

    Optional<Member> findMemberByEmail(String email);

    Page<Member> findAllByLevelAndNameContaining(Level level, String name, Pageable pageable);

    Page<Member> findAllByLevel(Level level, Pageable pageable);

    Page<Member> findAllByNameContaining(String name, Pageable pageable);
}
