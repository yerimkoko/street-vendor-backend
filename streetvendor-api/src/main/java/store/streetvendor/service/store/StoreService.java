package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.boss.BossRepository;
import store.streetvendor.core.domain.store.star.Star;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.exception.DuplicatedException;
import store.streetvendor.core.domain.store.menu.MenuSalesStatus;
import store.streetvendor.core.domain.store.review.Review;
import store.streetvendor.core.domain.store.star.StarRepository;
import store.streetvendor.core.redis.storecount.StoreCountKey;
import store.streetvendor.core.redis.storecount.StoreCountRepository;
import store.streetvendor.core.utils.dto.store.request.*;
import store.streetvendor.core.utils.dto.store.response.*;
import store.streetvendor.core.utils.service.BossServiceUtil;
import store.streetvendor.core.utils.service.StoreServiceUtils;
import store.streetvendor.core.utils.dto.store.response.projection.StoreAndMemberAndStarResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final static double distance = 2.0;

    private static final int minStoreHits = 10;

    private final StoreRepository storeRepository;

    private final BossRepository bossRepository;

    private final StarRepository starRepository;

    private final StoreCountRepository storeCountRepository;


    @Transactional(readOnly = true)
    public List<MyStoreInfo> getMyStores(Long memberId) {
        List<Store> stores = storeRepository.findStoreByBossId(memberId);
        return getMyStores(stores);
    }

    @Transactional
    public void updateMyStore(Long memberId, Long storeId, StoreUpdateRequest request) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        store.updateStoreInfo(request.getName(), request.getDescription(), request.getLocation(), request.getCategory());
        store.updateMenus(request.toMenus(store));
        store.updatePayments(request.getPaymentMethods());
        store.updateBusinessDaysInfo(request.toBusinessHours(store));
        store.updateStoreImages(request.toStoreImages(store));
    }

    @Transactional
    public void deleteMyStore(Long memberId, Long storeId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        if (store.getSalesStatus() == StoreSalesStatus.OPEN) {
            throw new DuplicatedException(String.format("<%s>의 가게는 열려있습니다. 먼저 영업을 종료해주세요.", store.getId()));
        }
        store.delete();
    }


    @Transactional(readOnly = true)
    public StoreDetailResponse getStoreDetail(Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        Boss boss = BossServiceUtil.findBossById(bossRepository, store.getBossId());
        storeCountRepository.incrByCount(store.getId());
        return StoreDetailResponse.of(store, boss);
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

    @Transactional
    public void storeOpen(Long memberId, Long storeId) {
        StoreSalesStatus open = StoreSalesStatus.OPEN;
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberIdAndSalesStatus(storeRepository, storeId, memberId, open);
        Store findAlreadyOpenedStore = storeRepository.findStoreByMemberIdAndSalesStatusStore(memberId, open);
        StoreServiceUtils.findStoreOpenedAndNotSameStatus(store, findAlreadyOpenedStore);
        store.changeSalesStatus(open);
    }

    @Transactional
    public void storeClose(Long memberId, Long storeId) {
        StoreSalesStatus closed = StoreSalesStatus.CLOSED;
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberIdAndSalesStatus(storeRepository, storeId, memberId, closed);
        store.changeSalesStatus(closed);
    }

    @Transactional(readOnly = true)
    public List<StoreResponse> getStoresByCategoryAndLocationAndStoreStatus(StoreCategoryRequest request, StoreCategory category) {
        return storeRepository.findAllStoresByLocationAndDistanceLessThan(request.getLatitude(), request.getLongitude(), request.getDistance())
            .stream()
            .map(StoreResponse::of)
            .filter(store -> store.hasCategory(category)
                && store.isSalesStatus(store.getSalesStatus()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void changeMenuStatus(Long storeId, Long bossId, Long menuId, MenuSalesStatus salesStatus) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, bossId);
        store.changeMenuSalesStatus(menuId, salesStatus);

    }

    private List<MyStoreInfo> getMyStores(List<Store> stores) {
        return stores.stream()
            .map(MyStoreInfo::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void addEvaluation(Long memberId, Long storeId, AddStoreReviewRequest request) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        store.addReview(Review.of(store, memberId, request.getRate(), request.getComment()));
        storeRepository.save(store);
    }

    @Transactional
    public void updateReview(Long memberId, Long storeId, UpdateStoreReviewRequest request) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        store.updateReview(request.getReviewId(), Review.of(store, memberId, request.getRate(), request.getComment()), memberId);
    }

    @Transactional
    public void deleteReview(Long memberId, Long storeId, Long reviewId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        store.deleteReview(reviewId, memberId);
    }

    @Transactional(readOnly = true)
    public StoreReviewResponse getStoreReviews(Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        return StoreReviewResponse.of(store.getId(), store.getReviews());
    }

    @Transactional(readOnly = true)
    public StoreInfoResponse getStoreInfo(Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        return StoreInfoResponse.of(store);
    }

    @Transactional
    public void addStar(Long memberId, Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        store.addStar(Star.of(store, memberId));
        storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    public List<StoreAndMemberAndStarResponse> getMyStars(Long memberId) {
        List<Star> stars = starRepository.findMyStars(memberId);
        return stars.stream()
            .map(StoreAndMemberAndStarResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteStar(Long memberId, Long starId) {
        Star star = starRepository.findByStarIdAndMemberId(starId, memberId);
        if (star == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 가게는 존재하지 않습니다.", starId));
        }
        star.delete();
    }

    // TODO: 인기 음식들 가져오기
    @Transactional(readOnly = true)
    public PopularStoresAndMenusResponse popularStoresAndMenus(double latitude, double longitude) {

        List<Store> stores = storeRepository.findAllStoresByLocationAndDistanceLessThan(latitude, longitude, distance);

        List<PopularStoresResponse> popularStoresResponses = stores.stream()
            .filter(store -> storeCountRepository.getValueByKey(StoreCountKey.of(store.getId())) >= minStoreHits)
            .map(PopularStoresResponse::of)
            .collect(Collectors.toList());

        return new PopularStoresAndMenusResponse(popularStoresResponses, null);

    }

    @Transactional(readOnly = true)
    public List<StoreDevResponse> getDevStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
            .map(StoreDevResponse::of)
            .collect(Collectors.toList());
    }

}
