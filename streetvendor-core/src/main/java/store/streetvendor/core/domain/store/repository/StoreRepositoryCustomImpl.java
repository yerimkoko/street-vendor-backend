package store.streetvendor.core.domain.store.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreStatus;

import static store.streetvendor.core.domain.store.QMemberLikeStore.memberLikeStore;
import static store.streetvendor.core.domain.store.QStore.store;

import java.util.List;

@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Store findStoreByStoreId(Long id) {
        return jpaQueryFactory.selectFrom(store)
            .where(
                store.id.eq(id),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchOne();
    }

    @Override
    public Store findStoreByStoreIdAndBossId(Long id, Long bossId) {
        return jpaQueryFactory.selectFrom(store)
            .where(
                store.id.eq(id),
                store.bossId.eq(bossId),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchOne();
    }


    @Override
    public List<Store> findAllStoreBySizeAndLastId(int size, long lastId) {
        return jpaQueryFactory
            .selectFrom(store)
            .where(store.id.lt(lastId), store.status.eq(StoreStatus.ACTIVE))
            .orderBy(store.id.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<Store> findStoreByMemberLike(Long memberId, Integer cursor, int size) {
        return jpaQueryFactory.selectFrom(store)
            .where(store.id.eq(memberLikeStore.store.id),
                getCursor(cursor),
                store.status.eq(StoreStatus.ACTIVE))
            .innerJoin(memberLikeStore)
            .on(memberLikeStore.memberId.eq(memberId))
            .limit(size)
            .fetch();
    }

    private BooleanExpression getCursor(Integer cursor) {
        if (cursor == null) {
            return null;
        }
        return store.id.lt(cursor);
    }

}
