package store.streetvendor.core.domain.store;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static store.streetvendor.core.domain.store.QMemberLikeStore.memberLikeStore;

@RequiredArgsConstructor
public class MemberLikeStoreCustomImpl implements MemberLikeStoreCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MemberLikeStore> findByMemberId(Long memberId, Integer cursor, int size) {
        return jpaQueryFactory.selectFrom(memberLikeStore)
            .where(memberLikeStore.memberId.eq(memberId),
                memberLikeStore.store.status.eq(StoreStatus.ACTIVE),
                getCursor(cursor),
                memberLikeStore.status.eq(MemberLikeStoreStatus.ACTIVE))
            .limit(size)
            .fetch();
    }

    @Override
    public MemberLikeStore findLikeStoreByMemberIdAndStoreId(Long memberId, Long storeId) {
        return jpaQueryFactory.selectFrom(memberLikeStore)
            .where(memberLikeStore.memberId.eq(memberId),
                memberLikeStore.store.id.eq(storeId),
                memberLikeStore.status.eq(MemberLikeStoreStatus.ACTIVE))
            .fetchOne();
    }


    private BooleanExpression getCursor(Integer cursor) {
        if (cursor == null) {
            return null;
        }
        return memberLikeStore.store.id.lt(cursor);
    }

}
