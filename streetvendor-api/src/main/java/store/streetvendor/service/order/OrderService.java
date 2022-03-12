package store.streetvendor.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.order_history.OrderHistoryRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.service.order.dto.request.AddNewOrderRequest;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.service.order.dto.response.OrderListToBossResponse;
import store.streetvendor.service.order_history.dto.AddNewOrderHistoryRequest;
import store.streetvendor.service.store.StoreServiceUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final StoreRepository storeRepository;

    private final OrderHistoryRepository historyRepository;

    @Transactional
    public void addNewOrder(AddNewOrderRequest request, Long memberId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, request.getStoreId(), memberId);
        orderRepository.save(request.toEntity(store, memberId));
    }

    // 사장님 기준 (주문을 받았을 때) -> 주문을 확인하는 로직
    @Transactional(readOnly = true)
    public List<OrderListToBossResponse> getAllOrders(Long storeId, Long memberId) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId, memberId);
        List<Orders> orders = orderRepository.findOrdersByStoreId(storeId);
        return orders.stream()
            .map(OrderListToBossResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void changeStatusToReady(Long storeId, Long memberId, Long orderId) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId, memberId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, orderId);
        order.changeStatusToReady();
    }

    @Transactional
    public void changeStatusToComplete(Long storeId, Long memberId, Long orderId, AddNewOrderHistoryRequest request) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId, memberId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, orderId);
        order.changeStatusToComplete();
        addToCompletedOrder(request, memberId);
    }

    @Transactional
    public void cancelOrderByBoss(Long storeId, Long orderId, Long bossId) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId, bossId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, orderId);
        order.cancelOrderByBoss();
    }

    @Transactional
    public void cancelOrderByUser(Long orderId, Long memberId) {
        Orders order = OrderServiceUtils.findMyOrderByOrderIdAndMemberId(orderRepository, orderId, memberId);
        order.cancelOrderByUser();
    }

    @Transactional
    public void addToCompletedOrder(AddNewOrderHistoryRequest request, Long memberId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, request.getStoreId(), memberId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, request.getOrderId());
        orderRepository.delete(order);
        historyRepository.save(request.toEntity(store.getId(), store.getMemberId()));
    }

}
