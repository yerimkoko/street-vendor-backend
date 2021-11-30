package store.streetvendor.domain.domain.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import store.streetvendor.domain.domain.order.repository.projection.OrderMenusProjection;
import store.streetvendor.domain.domain.order.repository.projection.QOrderMenusProjection;
import store.streetvendor.domain.domain.store.Store;

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
                store.id.eq(id)
            ).fetchOne();
    }

    @Override
    public Store findStoreByStoreIdAndMemberId(Long id, Long memberId) {
        return jpaQueryFactory.selectFrom(store)
            .innerJoin(store.menus, menu).fetchJoin()
            .where(
                store.id.eq(id),
                store.memberId.eq(memberId)
            ).fetchOne();
    }

    @Override
    public List<Store> findStoreByBossId(Long memberId) {
        return jpaQueryFactory.selectFrom(store)
            .where(
                store.memberId.eq(memberId)
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
