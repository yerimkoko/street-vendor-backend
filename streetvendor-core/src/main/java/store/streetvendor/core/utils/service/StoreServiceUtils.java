package store.streetvendor.core.utils.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreRepository;
import store.streetvendor.core.exception.NotFoundException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreServiceUtils {

    public static Store findStoreByStoreIdAndMemberId(StoreRepository storeRepository, Long storeId, Long bossId) {
        Store store = storeRepository.findStoreByStoreIdAndBossId(storeId, bossId);
        validateStore(store, storeId);
        return store;
    }


    public static Store findByStoreId(StoreRepository storeRepository, Long storeId) {
        Store store = storeRepository.findStoreByStoreId(storeId);
        validateStore(store, storeId);
        return store;
    }

    public static void validateExistsStore(StoreRepository storeRepository, Long storeId, Long memberId) {
        Store store = storeRepository.findStoreByStoreIdAndBossId(storeId, memberId);
        validateStore(store, storeId);
    }


    public static void validateStore(Store store, Long storeId) {
        if (store == null) {
            throw new NotFoundException(String.format("해당하는 (%s) 상점이 존재하지 않습니다.", storeId));
        }
    }


}
