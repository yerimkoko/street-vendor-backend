package store.streetvendor.core.domain.store.repository;

import store.streetvendor.core.domain.store.Store;

import java.util.List;

public interface StoreRepositoryCustom {

    Store findStoreByStoreId(Long id);

    Store findStoreByStoreIdAndBossId(Long id, Long bossId);

    List<Store> findAllStoreBySizeAndLastId(int size, long lastId);

    List<Store> findStoreByMemberLike(Long memberId, Integer cursor, int size);

}
