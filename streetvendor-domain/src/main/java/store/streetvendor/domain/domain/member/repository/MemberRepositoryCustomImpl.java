package store.streetvendor.domain.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.member.Member;

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

}
