package store.streetvendor.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.service.store.dto.request.AddNewStoreRequest;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public void addNewStore(AddNewStoreRequest request, Long memberId) {
        storeRepository.save(request.toEntity(memberId));
    }

}
