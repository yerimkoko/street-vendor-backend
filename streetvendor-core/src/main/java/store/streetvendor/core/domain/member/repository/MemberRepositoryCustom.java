package store.streetvendor.core.domain.member.repository;

import store.streetvendor.core.domain.member.Member;

public interface MemberRepositoryCustom {

    Member findUserByUserId(Long userId);

    Member findMemberByNickName(String nickName);

    Member findMemberIdByEmail(String email);

    Member findBossByBossId(Long bossId);

}
