package store.streetvendor.service.store;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.exception.model.NotFoundException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreServiceUtils {

    public static Store findStoreByStoreIdAndMemberId(StoreRepository storeRepository, Long storeId, Long memberId) {
        Store store = storeRepository.findStoreByStoreIdAndMemberId(storeId, memberId);
        validateStore(store, storeId);
        return store;
    }

    public static Store findByStoreId(StoreRepository storeRepository, Long storeId) {
        Store store = storeRepository.findStoreByStoreId(storeId);
        validateStore(store, storeId);
        return store;
    }

    public static void validateExistsStore(StoreRepository storeRepository, Long storeId, Long memberId) {
        Store store = storeRepository.findStoreByStoreIdAndMemberId(storeId, memberId);
        validateStore(store, storeId);
    }

    private static void validateStore(Store store, Long storeId) {
        if (store == null) {
            throw new NotFoundException(String.format("해당하는 (%s) 상점이 존재하지 않습니다.", storeId));
        }
    }

}
