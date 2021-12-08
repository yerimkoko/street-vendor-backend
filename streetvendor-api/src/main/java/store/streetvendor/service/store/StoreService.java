package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.service.store.dto.response.StoreResponseDto;
import store.streetvendor.service.store.dto.request.StoreUpdateRequest;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.service.member.MemberServiceUtils;
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
        storeRepository.save(request.toEntity(memberId));
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getMyStoreList(Long memberId) {
        List<Store> stores = storeRepository.findStoreByBossId(memberId);
        return getStores(stores);
    }

    private List<StoreResponseDto> getStores(List<Store> stores) {
        return stores.stream()
            .map(StoreResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateMyStore(Long memberId, Long storeId, StoreUpdateRequest updateRequest) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, member.getId());
        store.updateStoreInfo(updateRequest.getName(), updateRequest.getDescription(), updateRequest.getPictureUrl(),
            updateRequest.getLocation(), updateRequest.getStartTime(), updateRequest.getEndTime());
        store.updateMenus(updateRequest.getMenus());
        store.updatePayments(updateRequest.getPaymentMethods());
    }

    @Transactional
    public void deleteMyStore(Long memberId, Long storeId) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        Store store = storeRepository.findStoreByStoreIdAndMemberId(storeId, member.getId());
        if (store == null) {
            throw new IllegalArgumentException(String.format("해당하는 (%s) 상점이 존재하지 않습니다.", storeId));
        }
        store.delete();
    }

    @Transactional
    public void changeStatusToReady(Long memberId, Long storeId) {
        
    }
}
