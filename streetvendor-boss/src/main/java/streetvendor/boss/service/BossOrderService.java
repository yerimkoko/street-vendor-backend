package streetvendor.boss.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.order.OrderStatus;
import store.streetvendor.domain.domain.order.OrderStatusCanceled;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.domain.service.utils.MemberServiceUtils;
import store.streetvendor.domain.service.utils.OrderServiceUtils;
import store.streetvendor.domain.service.utils.StoreServiceUtils;
import store.streetvendor.service.order.dto.response.OrderListToBossResponse;
import store.streetvendor.service.order_history.dto.request.AddNewOrderHistoryRequest;
import store.streetvendor.service.order_history.dto.response.MemberOrderHistoryResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BossOrderService {

    private final OrderHistoryRepository orderHistoryRepository;

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository historyRepository;

    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;


    @Transactional
    public List<MemberOrderHistoryResponse> getOrderHistory(Long storeId, Long bossId) {

        List<OrderHistory> orderHistoryList = orderHistoryRepository.findOrderHistoryByStoreId(storeId, bossId);

        return orderHistoryList.stream()
            .map(MemberOrderHistoryResponse::historyOf)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<MemberOrderHistoryResponse> getOrders(OrderStatus status,
                                                      Long storeId,
                                                      Long bossId) {
        List<Orders> getOrders = orderRepository.findOrdersByStoreIdAndBossIdAndStatus(storeId, bossId, status);
        return getOrders.stream()
            .map(MemberOrderHistoryResponse::orderOf)
            .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<OrderListToBossResponse> getAllOrders(Long storeId, Long memberId, OrderStatus orderStatus) {
        Member boss = MemberServiceUtils.findByBossId(memberRepository, memberId);
        List<Orders> orders = orderRepository.findOrdersByStoreIdAndBossIdAndStatus(storeId, memberId, orderStatus);
        return orders.stream()
            .map(order -> OrderListToBossResponse.of(order, boss))
            .collect(Collectors.toList());
    }

    @Transactional
    public void changeStatusToPreparing(Long storeId, Long memberId, Long orderId) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId, memberId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, orderId);
        order.changeStatusToPreparing();
    }

    @Transactional
    public void changeStatusToReadyToPickUp(Long memberId, AddNewOrderHistoryRequest request) {
        StoreServiceUtils.validateExistsStore(storeRepository, request.getStoreId(), memberId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, request.getOrderId());
        order.changeStatusToReadyToPickUp();
        addToCompletedOrder(request, memberId);
    }

    @Transactional
    public void cancelOrderByBoss(Long storeId, Long orderId, Long bossId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, storeId, bossId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, orderId);
        historyRepository.save(OrderHistory.cancel(order, store));
        orderRepository.delete(order);

    }

    @Transactional
    public void addToCompletedOrder(AddNewOrderHistoryRequest request, Long memberId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, request.getStoreId(), memberId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, request.getOrderId());
        historyRepository.save(request.toEntity(store, order, order.getId(), OrderStatusCanceled.ACTIVE));
        orderRepository.delete(order);
    }


}
