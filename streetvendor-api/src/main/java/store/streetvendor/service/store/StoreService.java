package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.exception.model.NotFoundException;
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
        Member member = memberRepository.findMemberById(memberId);
        if (member.getBossName() == null || member.getPhoneNumber() == null) {
            throw new NotFoundException("사장님 등록을 먼저 해 주세요.");
        }
        storeRepository.save(request.toEntity(memberId));
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getMyStoreList(Long memberId) {
        List<Store> stores = storeRepository.findStoreByBossId(memberId);
        return getStores(stores);
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
        Store store = storeRepository.findStoreByStoreId(storeId);
        Member member = memberRepository.findMemberById(store.getMemberId());
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

    @Transactional
    public List<StoreResponseDto> getStoreByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("distance")Double distance) {
        List<Store> stores = storeRepository.findByLocationAndDistanceLessThan(latitude, longitude, distance);
        return getStores(stores);
    }

}
