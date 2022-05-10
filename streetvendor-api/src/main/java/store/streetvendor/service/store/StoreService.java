package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.store.StoreCategory;
import store.streetvendor.domain.domain.store.StoreSalesStatus;
import store.streetvendor.exception.model.AlreadyExistedException;
import store.streetvendor.service.member.MemberServiceUtils;
import store.streetvendor.service.store.dto.request.StoreDistanceRequest;
import store.streetvendor.service.store.dto.response.MyStoreInfo;
import store.streetvendor.service.store.dto.response.StoreDetailResponse;
import store.streetvendor.service.store.dto.response.StoreResponseDto;
import store.streetvendor.service.store.dto.request.StoreUpdateRequest;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
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

    @Transactional
    public List<StoreResponseDto> getAllStoreList(int size, long lastId) {
        List<Store> stores = storeRepository.findAllStoreBySizeAndLastId(size, lastId);
        return getStores(stores);
    }

    private List<StoreResponseDto> getStores(List<Store> stores) {
        return stores.stream()
            .map(StoreResponseDto::of)
            .collect(Collectors.toList());
    }

    private List<MyStoreInfo> getMyStores(List<Store> stores) {
        return stores.stream()
            .map(MyStoreInfo::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<StoreResponseDto> getStoreByLocation(StoreDistanceRequest request) {
        List<Store> stores = storeRepository
            .findByLocationAndDistanceLessThan(request.getLatitude(), request.getLongitude(), request.getDistance());
        return getStores(stores);
    }

    @Transactional
    public void changeSalesStatus(Long memberId, Long storeId, StoreSalesStatus status) {
        List<Store> stores = storeRepository.findStoresByMemberIdAndSalesStatus(memberId);
        if (!stores.isEmpty()) {
            throw new AlreadyExistedException(String.format("이미 영업중인 가게 (%s)가 있습니다. 가게를 종료해주세요.", stores.get(0).getId()));
        }
         Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
         store.changeSalesStatus(status);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStoreByCategory(StoreCategory category) {
        List<Store> stores = storeRepository.findStoreByCategory(category);
        return getStores(stores);
    }

}
