package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreRepository;
import store.streetvendor.core.domain.store.StoreSalesStatus;
import store.streetvendor.core.exception.DuplicatedException;
import store.streetvendor.core.utils.dto.store.request.AddNewStoreRequest;
import store.streetvendor.core.utils.dto.store.request.StoreUpdateRequest;
import store.streetvendor.core.utils.service.MemberServiceUtils;
import store.streetvendor.core.utils.service.StoreServiceUtils;

@RequiredArgsConstructor
@Service
public class BossStoreService {

    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void addNewStore(AddNewStoreRequest request, Long bossId) {
        MemberServiceUtils.findBossByBossId(memberRepository, bossId);
        storeRepository.save(request.toEntity(bossId));
    }

    @Transactional
    public void updateMyStore(Long memberId, Long storeId, StoreUpdateRequest request) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        store.updateStoreInfo(request.getName(), request.getDescription(), request.getLocation(), request.getCategory());
        store.updateMenus(request.toMenus(store));
        store.updatePayments(request.getPaymentMethods());
        store.updateBusinessDaysInfo(request.toBusinessHours(store));
    }

    @Transactional
    public void deleteMyStore(Long memberId, Long storeId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        if (store.getSalesStatus() == StoreSalesStatus.OPEN) {
            throw new DuplicatedException(String.format("<%s>의 가게는 열려있습니다. 먼저 영업을 종료해주세요.", store.getId()));
        }
        store.delete();
    }

}
