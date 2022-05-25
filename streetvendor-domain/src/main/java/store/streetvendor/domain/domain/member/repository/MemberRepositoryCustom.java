package store.streetvendor.domain.domain.member.repository;

import store.streetvendor.domain.domain.member.Member;

public interface MemberRepositoryCustom {

    Member findMemberById(Long memberId);

    Member findActiveMemberIdByNickName(String nickName);

    Member findActiveMemberIdByEmail(String email);

}
