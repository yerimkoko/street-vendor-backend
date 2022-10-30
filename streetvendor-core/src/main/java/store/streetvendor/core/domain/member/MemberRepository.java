package store.streetvendor.core.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import store.streetvendor.core.domain.member.repository.MemberRepositoryCustom;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}

