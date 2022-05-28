package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.store.*;
import store.streetvendor.exception.model.AlreadyExistedException;
import store.streetvendor.service.member.MemberServiceUtils;
import store.streetvendor.service.store.dto.request.StoreCategoryRequest;
import store.streetvendor.service.store.dto.request.StoreDistanceRequest;
import store.streetvendor.service.store.dto.response.*;
import store.streetvendor.service.store.dto.request.StoreUpdateRequest;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void addNewStore(AddNewStoreRequest request, Long memberId) {
        MemberServiceUtils.findByBossId(memberRepository, memberId);
        storeRepository.save(request.toEntity(memberId));
    }

    @Transactional(readOnly = true)
    public List<MyStoreInfo> getMyStores(Long memberId) {
        List<Store> stores = storeRepository.findStoreByBossId(memberId);
        return getMyStores(stores);
    }

    @Transactional
    public void updateMyStore(Long memberId, Long storeId, StoreUpdateRequest request) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        store.updateStoreInfo(request.getName(), request.getDescription(), request.getPictureUrl(), request.getLocation(), request.getCategory());
        store.updateMenus(request.toMenus(store));
        store.updatePayments(request.getPaymentMethods());
        store.updateBusinessDaysInfo(request.toBusinessHours(store));
    }

    @Transactional
    public void deleteMyStore(Long memberId, Long storeId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        store.delete();
    }

    @Transactional(readOnly = true)
    public StoreDetailResponse getStoreDetail(Long storeId) {
        Store store = StoreServiceUtils.findByStoreId(storeRepository, storeId);
        Member member = MemberServiceUtils.findByMemberId(memberRepository, store.getMemberId());
        return StoreDetailResponse.of(store, member);
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
        if (findAlreadyOpenedStore != null) {
            if (findAlreadyOpenedStore != store) {
                throw new AlreadyExistedException(String.format("이미 영업중인 가게 (%s)가 있습니다. 가게를 종료해주세요.", findAlreadyOpenedStore.getId()));
            }
        }
        store.changeSalesStatus(open);
    }

    @Transactional
    public void storeClose(Long memberId, Long storeId) {
        StoreSalesStatus closed = StoreSalesStatus.CLOSED;
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberIdAndSalesStatus(storeRepository, storeId, memberId, closed);
        store.changeSalesStatus(closed);
    }

    @Transactional(readOnly = true)
    public List<StoreResponse> getStoresByCategoryAndLocationAndStoreStatus(StoreCategoryRequest request) {
        return storeRepository.findAllStoresByLocationAndDistanceLessThan(request.getLatitude(), request.getLongitude(), request.getDistance()).stream()
            .map(StoreResponse::of)
            .filter(store -> store.getCategory().equals(request.getCategory()) &&
                store.getSalesStatus().equals(request.getSalesStatus()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void changeMenuStatus(Long storeId, Long bossId, Long menuId, MenuSalesStatus salesStatus) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, bossId);
        Menu menu = store.findMenuByMenuId(menuId);
        menu.changeMenuStatus(salesStatus);
    }

    private List<MyStoreInfo> getMyStores(List<Store> stores) {
        return stores.stream()
            .map(MyStoreInfo::of)
            .collect(Collectors.toList());
    }

}
