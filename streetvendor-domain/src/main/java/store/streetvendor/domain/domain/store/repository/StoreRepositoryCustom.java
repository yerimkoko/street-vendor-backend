package store.streetvendor.domain.domain.store.repository;

import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreSalesStatus;

import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreByStoreId(Long id);

    Store findStoreByStoreIdAndMemberId(Long id, Long memberId);

    Store findStoreByMemberIdAndSalesStatusStore(Long memberId, StoreSalesStatus salesStatus);

    // List<Store> findByLocationAndDistanceLessThanStore(Double latitude, Double longitude, Double distance);

    List<Store> findStoreByBossId(Long bossId);

    List<Store> findAllStoreBySizeAndLastId(int size, long lastId);

}
