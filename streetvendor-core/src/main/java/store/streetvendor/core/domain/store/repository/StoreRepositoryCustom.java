package store.streetvendor.core.domain.store.repository;

import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreSalesStatus;

import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreByStoreId(Long id);

    Store findStoreByStoreIdAndMemberId(Long id, Long memberId);

    Store findStoreByMemberIdAndSalesStatusStore(Long memberId, StoreSalesStatus salesStatus);

    List<Store> findStoreByBossId(Long bossId);

    List<Store> findAllStoreBySizeAndLastId(int size, long lastId);

}