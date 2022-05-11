package store.streetvendor.service.store;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.domain.domain.store.StoreSalesStatus;
import store.streetvendor.exception.model.AlreadyExistedException;
import store.streetvendor.exception.model.NotFoundException;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreServiceUtils {

    public static Store findStoreByStoreIdAndMemberId(StoreRepository storeRepository, Long storeId, Long memberId) {
        Store store = storeRepository.findStoreByStoreIdAndMemberId(storeId, memberId);
        validateStore(store, storeId);
        return store;
    }

    public static Store findStoreByStoreIdAndMemberIdAndSalesStatus(StoreRepository storeRepository, Long storeId, Long memberId, StoreSalesStatus salesStatus) {
        Store store = storeRepository.findStoreByStoreIdAndMemberId(storeId, memberId);
        validateStore(store, storeId);
        validateSalesStatus(store, salesStatus);
        return store;
    }

    public static Store findByStoreId(StoreRepository storeRepository, Long storeId) {
        Store store = storeRepository.findStoreByStoreId(storeId);
        validateStore(store, storeId);
        return store;
    }

    public static List<Store> findByStoresBySalesStatus(StoreRepository storeRepository, Long memberId, StoreSalesStatus salesStatus) {
        List<Store> stores = storeRepository.findStoreByBossIdAndSalesStatusStore(memberId, salesStatus);
        return stores;
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

    private static void validateSalesStatus(Store store, StoreSalesStatus salesStatus) {
        if (store.getSalesStatus() == salesStatus) {
            throw new AlreadyExistedException(String.format("(%s)는 이미 (%s) 상태입니다.", store.getId(), salesStatus));
        }
    }
}
