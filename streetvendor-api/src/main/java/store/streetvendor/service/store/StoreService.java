package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return stores.stream()
            .map(StoreResponseDto::of)
            .collect(Collectors.toList());
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

//    @Transactional
//    public ApiResponse<List<StoreResponseDto>> pagingResponse(Long lastId, int size) {
//        List<Store> stores = storeRepository.findAll();
//        Page<Store> storePage = fetchPages(lastId, size, stores);
//        return
//    }

    private Page<Store> fetchPages(Long lastId, int size, List<Store> stores) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return storeRepository.findAll(pageRequest);

    }

}
