package store.streetvendor.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.service.order.dto.request.AddNewOrderRequest;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.service.store.StoreServiceUtils;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final StoreRepository storeRepository;

    @Transactional
    public void addNewOrder(AddNewOrderRequest request, Long memberId) {
        Store store = StoreServiceUtils
            .findStoreByStoreIdAndMemberId(storeRepository, request.getStoreId());
        orderRepository.save(request.toEntity(store, memberId));
    }

}
