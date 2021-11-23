package store.streetvendor.domain.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.domain.domain.member.repository.MemberRepositoryCustom;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}

