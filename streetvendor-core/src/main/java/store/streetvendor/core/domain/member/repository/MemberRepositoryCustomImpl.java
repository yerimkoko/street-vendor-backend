package store.streetvendor.core.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberType;

import static store.streetvendor.core.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findUserByUserId(Long userId) {
        return queryFactory.selectFrom(member)
            .where(
                member.id.eq(userId),
                member.memberType.eq(MemberType.USER)
            ).fetchOne();
    }


    @Override
    public Member findMemberByNickName(String nickName) {
        return queryFactory.selectFrom(member)
            .where(
                member.nickName.eq(nickName)
            ).fetchOne();
    }

    @Override
    public Member findMemberIdByEmail(String email) {
        return queryFactory.selectFrom(member)
            .where(
                member.email.eq(email)
            ).fetchOne();
    }

    @Override
    public Member findBossByBossId(Long memberId) {
        return queryFactory.selectFrom(member)
            .where(
                member.id.eq(memberId),
                member.memberType.eq(MemberType.BOSS)
            ).fetchOne();
    }


}
