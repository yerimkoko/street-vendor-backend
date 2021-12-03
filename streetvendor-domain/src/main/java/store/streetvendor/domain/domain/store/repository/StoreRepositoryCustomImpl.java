package store.streetvendor.domain.domain.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.order.repository.projection.OrderMenusProjection;
import store.streetvendor.domain.domain.order.repository.projection.QOrderMenusProjection;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreStatus;

import static store.streetvendor.domain.domain.store.QMenu.menu;
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
    public Store findStoreByStoreIdAndMemberId(Long id, Long memberId) {
        return jpaQueryFactory.selectFrom(store)
            .where(
                store.id.eq(id),
                store.memberId.eq(memberId),
                store.status.eq(StoreStatus.ACTIVE)
            ).fetchOne();
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
    public OrderMenusProjection findMenuInfoByStoreIdAndMemberId(Long storeId, Long memberId) {
        return jpaQueryFactory.select(new QOrderMenusProjection(menu.id, menu.name, menu.price))
            .from(menu)
            .innerJoin(store)
            .on(store.id.eq(menu.store.id))
            .fetchOne();
    }

}
