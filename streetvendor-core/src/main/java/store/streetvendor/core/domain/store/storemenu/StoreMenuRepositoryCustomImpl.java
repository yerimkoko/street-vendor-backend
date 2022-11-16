package store.streetvendor.core.domain.store.storemenu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static store.streetvendor.core.domain.store.storemenu.QStoreMenu.storeMenu;

@RequiredArgsConstructor
public class StoreMenuRepositoryCustomImpl implements StoreMenuRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public StoreMenu findStoreMenuById(Long storeMenuId) {
        return jpaQueryFactory.selectFrom(storeMenu)
            .where(storeMenu.id.eq(storeMenuId))
            .fetchOne();
    }

}
