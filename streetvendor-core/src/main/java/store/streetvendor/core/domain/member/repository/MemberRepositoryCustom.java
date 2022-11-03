package store.streetvendor.core.domain.member.repository;

import store.streetvendor.core.domain.member.Member;

public interface MemberRepositoryCustom {

    Member findMemberById(Long memberId);

    Member findMemberByNickName(String nickName);

    Member findMemberIdByEmail(String email);

}
