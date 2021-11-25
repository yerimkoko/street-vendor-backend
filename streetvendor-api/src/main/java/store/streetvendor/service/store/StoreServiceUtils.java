package store.streetvendor.service.store;

import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;

@NoArgsConstructor
public class StoreServiceUtils {

    public static Store findStoreByStoreIdAndMemberId(StoreRepository storeRepository, Long memberId, Long storeId) {
        Store store = storeRepository.findStoreByStoreIdAndMemberId(storeId, memberId);
        validateStore(store);
        return store;
    }

    private static void validateStore(Store store) {
        if(store == null) {
            throw new IllegalArgumentException(String.format("해당하는 (%s) 상점이 존재하지 않습니다.", store.getId()));
        }
    }

}
