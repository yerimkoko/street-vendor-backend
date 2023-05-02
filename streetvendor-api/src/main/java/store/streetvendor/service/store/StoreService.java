package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.review.reviewcount.ReviewCountRepositoryImpl;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.exception.ConflictException;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.redis.storecount.StoreCountRepository;
import store.streetvendor.core.utils.dto.store.MemberLikeStoreListResponse;
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.core.utils.dto.store.response.*;
import store.streetvendor.core.utils.service.StoreServiceUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final static double distance = 2.0;

    private static final int minStoreHits = 10;

    private final StoreRepository storeRepository;

    private final StoreCountRepository storeCountRepository;

    private final MemberLikeStoreRepository memberLikeStoreRepository;

    private final ReviewCountRepositoryImpl reviewCountRepository;


    @Transactional(readOnly = true)
    public StoreDetailResponse getStoreDetail(Long storeId, String baseUrl) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        storeCountRepository.incrByCount(store.getId());
        return StoreDetailResponse.of(store, baseUrl);
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
    public List<StoreInfoResponse> getStoresByCategoryAndLocationAndStoreStatus(StoreCategory category, String baseUrl, double longitude, double latitude, Integer cursor, int size) {

        return storeRepository.findAllStoresByLocationAndDistanceLessThan(latitude, longitude, distance)
            .stream()
            .map(store -> StoreInfoResponse.of(store, baseUrl, getReviewCount(store.getId()), longitude, latitude))
            .filter(store -> store.hasCategory(category)
                && store.isSalesStatus(store.getSalesStatus()))
            .collect(Collectors.toList());
    }



    @Transactional(readOnly = true)
    public List<StoreDevResponse> getDevStores(String baseUrl) {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
            .map(store -> StoreDevResponse.of(store, baseUrl))
            .collect(Collectors.toList());
    }

    @Transactional
    public void addMemberLikeStore(@NotNull Long memberId, Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        MemberLikeStore memberLikeStore = memberLikeStoreRepository.findLikeStoreByMemberIdAndStoreId(memberId, store.getId());
        if (memberLikeStore != null) {
            throw new ConflictException("이미 좋아요를 했습니다. 좋아요는 한 번만 할 수 있습니다.");
        }

        memberLikeStoreRepository.save(MemberLikeStore.newInstance(memberId, store));
    }

    @Transactional(readOnly = true)
    public List<MemberLikeStoreListResponse> getMemberLikeStore(Long memberId, double currentLatitude, double currentLongitude, Integer cursor, int size) {
        List<MemberLikeStore> memberLikeStores = memberLikeStoreRepository.findByMemberId(memberId, cursor, size);
        return memberLikeStores.stream()
            .map(memberLikeStore -> MemberLikeStoreListResponse.of(memberLikeStore.getStore(), currentLatitude, currentLongitude, getReviewCount(memberLikeStore.getStore().getId())))
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteLikeStore(Long memberId, Long storeId) {
        MemberLikeStore memberLikeStore = memberLikeStoreRepository.findLikeStoreByMemberIdAndStoreId(memberId, storeId);
        if (memberLikeStore == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 가게는 없습니다.", storeId));
        }
        memberLikeStore.delete();
    }

    private long getReviewCount(Long storeId) {
        return reviewCountRepository.getValueByKey(storeId);
    }

}
