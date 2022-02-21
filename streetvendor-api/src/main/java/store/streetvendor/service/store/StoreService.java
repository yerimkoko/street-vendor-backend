package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void addNewStore(AddNewStoreRequest request, Long memberId) {
        storeRepository.save(request.toEntity(memberId));
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getMyStoreList(Long memberId) {
        List<Store> stores = storeRepository.findStoreByBossId(memberId);
        return stores.stream()
            .map(StoreResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateMyStore(Long memberId, Long storeId, StoreUpdateRequest request) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        store.updateStoreInfo(request.getName(), request.getDescription(), request.getPictureUrl(), request.getLocation());
        store.updateMenus(request.toMenus(store));
        store.updatePayments(request.getPaymentMethods());
        store.updateBusinessDaysInfo(request.toBusinessHours(store));
    }

    @Transactional
    public void deleteMyStore(Long memberId, Long storeId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        store.delete();
    }

}
