package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.redis.storecount.StoreCountRepository;
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.core.utils.dto.store.response.*;
import store.streetvendor.core.utils.service.StoreServiceUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final static double distance = 2.0;

    private static final int minStoreHits = 10;

    private final StoreRepository storeRepository;


    private final StoreCountRepository storeCountRepository;



    @Transactional(readOnly = true)
    public StoreDetailResponse getStoreDetail(Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        // storeCountRepository.incrByCount(store.getId());
        return StoreDetailResponse.of(store);
    }

    @Transactional(readOnly = true)
    public List<StoreSimpleResponse> getAllStoreList(int size, long lastId) {
        List<Store> stores = storeRepository.findAllStoreBySizeAndLastId(size, lastId);
        return stores.stream()
            .map(StoreSimpleResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StoreResponse> getOpenedStoresByLocation(StoreDistanceRequest request) {
        List<Store> stores = storeRepository
            .findOpenedStoreByLocationAndDistanceLessThan(request.getLatitude(), request.getLongitude(), request.getDistance());
        return stores.stream()
            .map(StoreResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StoreResponse> getAllStoresByLocation(StoreDistanceRequest request) {
        List<Store> stores = storeRepository
            .findAllStoresByLocationAndDistanceLessThan(request.getLatitude(), request.getLongitude(), request.getDistance());
        return stores.stream()
            .map(StoreResponse::of)
            .collect(Collectors.toList());
    }

//    @Transactional
//    public void storeOpen(Long memberId, Long storeId) {
//        StoreSalesStatus open = StoreSalesStatus.OPEN;
//        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberIdAndSalesStatus(storeRepository, storeId, memberId, open);
//        Store findAlreadyOpenedStore = storeRepository.findStoreByMemberIdAndSalesStatusStore(memberId, open);
//        StoreServiceUtils.findStoreOpenedAndNotSameStatus(store, findAlreadyOpenedStore);
//        store.changeSalesStatus(open);
//    }
//
//    @Transactional
//    public void storeClose(Long memberId, Long storeId) {
//        StoreSalesStatus closed = StoreSalesStatus.CLOSED;
//        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberIdAndSalesStatus(storeRepository, storeId, memberId, closed);
//        store.changeSalesStatus(closed);
//    }

    @Transactional(readOnly = true)
    public List<StoreResponse> getStoresByCategoryAndLocationAndStoreStatus(StoreCategoryRequest request, StoreCategory category) {
        return storeRepository.findAllStoresByLocationAndDistanceLessThan(request.getLatitude(), request.getLongitude(), request.getDistance())
            .stream()
            .map(StoreResponse::of)
            .filter(store -> store.hasCategory(category)
                && store.isSalesStatus(store.getSalesStatus()))
            .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public StoreInfoResponse getStoreInfo(Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        return StoreInfoResponse.of(store);
    }


    @Transactional(readOnly = true)
    public List<StoreDevResponse> getDevStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
            .map(StoreDevResponse::of)
            .collect(Collectors.toList());
    }

}
