package store.streetvendor.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.service.order.dto.request.AddNewOrderRequest;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.service.order.dto.response.OrderListToBossResponse;
import store.streetvendor.service.store.StoreServiceUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final StoreRepository storeRepository;

    @Transactional
    public void addNewOrder(AddNewOrderRequest request, Long memberId) {
        Store store = StoreServiceUtils
            .findStoreByStoreIdAndMemberId(storeRepository, request.getStoreId(), memberId);
        orderRepository.save(request.toEntity(store, memberId));
    }

    // 사장님 기준 (주문을 받았을 때) -> 주문을 확인하는 로직
    @Transactional
    public List<OrderListToBossResponse> allOrderList(Long storeId, Long memberId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, memberId);
        List<Orders> orders = orderRepository.findOrdersByStoreId(store.getId());
        return orders.stream().map(OrderListToBossResponse::of).collect(Collectors.toList());
    }


    @Transactional
    public void changeStatusToReady() {

    }

}
