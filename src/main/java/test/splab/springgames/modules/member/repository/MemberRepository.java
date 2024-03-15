package test.splab.springgames.modules.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.splab.springgames.modules.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
