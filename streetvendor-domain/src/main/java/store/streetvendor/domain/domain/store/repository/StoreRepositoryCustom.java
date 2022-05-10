package store.streetvendor.domain.domain.store.repository;

import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreCategory;

import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreByStoreId(Long id);

    Store findStoreByStoreIdAndMemberId(Long id, Long memberId);

    List<Store> findStoreByBossId(Long bossId);

    List<Store> findAllStoreBySizeAndLastId(int size, long lastId);

    List<Store> findStoresByMemberIdAndSalesStatus(Long memberId);

    List<Store> findStoreByCategory(StoreCategory category);

    Store findStoreBySalesStore(Long storeId);

}
