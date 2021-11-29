package store.streetvendor.domain.domain.store.repository;

import store.streetvendor.domain.domain.order.repository.projection.OrderMenusProjection;
import store.streetvendor.domain.domain.store.Store;

import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreByStoreId(Long id);

    Store findStoreByStoreIdAndMemberId(Long id, Long memberId);

    List<Store> findStoreByBossId(Long bossId);

    OrderMenusProjection findMenuInfoByStoreIdAndMemberId(Long storeId, Long memberId);



}
