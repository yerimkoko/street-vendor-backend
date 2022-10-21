package store.streetvendor.domain.domain.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreSalesStatus;
import store.streetvendor.domain.domain.store.StoreStatus;

import static store.streetvendor.domain.domain.store.QStore.store;

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
    public Store findStoreByStoreIdAndMemberId(Long id, Long bossId) {
        return jpaQueryFactory.selectFrom(store)
            .where(
                store.id.eq(id),
                store.memberId.eq(bossId),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchOne();
    }

    @Override
    public Store findStoreByMemberIdAndSalesStatusStore(Long memberId, StoreSalesStatus salesStatus) {
        return jpaQueryFactory.selectFrom(store)
            .where(
                store.memberId.eq(memberId),
                store.salesStatus.eq(salesStatus),
                store.status.eq(StoreStatus.ACTIVE)
            )
            .fetchOne();
    }


    @Override
    public List<Store> findStoreByBossId(Long memberId) {
        return jpaQueryFactory.selectFrom(store)
            .where(
                store.memberId.eq(memberId),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetch();
    }

    @Override
    public List<Store> findAllStoreBySizeAndLastId(int size, long lastId) {
        return jpaQueryFactory
            .selectFrom(store)
            .limit(size)
            .where(store.id.lt(lastId), store.status.eq(StoreStatus.ACTIVE))
            .orderBy(store.id.desc())
            .fetch();
    }

}
