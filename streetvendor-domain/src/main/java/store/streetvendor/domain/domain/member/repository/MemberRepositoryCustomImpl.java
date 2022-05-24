package store.streetvendor.domain.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberStatus;

import static store.streetvendor.domain.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findMemberById(Long memberId) {
        return queryFactory.selectFrom(member)
            .where(
                member.id.eq(memberId)
            ).fetchOne();
    }

    @Override
    public Member findMemberIdByNickName(String nickName) {
        return queryFactory.selectFrom(member)
            .where(
                member.nickName.eq(nickName),
                member.status.eq(MemberStatus.ACTIVE)
            ).fetchOne();
    }

    @Override
    public Member findMemberIdByEmail(String email) {
        return queryFactory.selectFrom(member)
            .where(
                member.email.eq(email),
                member.status.eq(MemberStatus.ACTIVE)
            ).fetchOne();
    }

    @Override
    public Member findActiveMemberIdByEmail(String email) {
        return queryFactory.selectFrom(member)
            .where(
                member.email.eq(email),
                member.status.eq(MemberStatus.ACTIVE)
            ).fetchOne();
    }

}
