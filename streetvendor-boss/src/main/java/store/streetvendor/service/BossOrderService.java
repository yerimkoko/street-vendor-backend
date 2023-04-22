package store.streetvendor.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.boss.BossRepository;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreRepository;
import store.streetvendor.core.utils.service.BossServiceUtil;
import store.streetvendor.core.utils.service.OrderServiceUtils;
import store.streetvendor.core.utils.service.StoreServiceUtils;
import store.streetvendor.core.utils.dto.order_history.request.AddNewOrderHistoryRequest;
import store.streetvendor.core.utils.dto.order_history.MemberOrderHistoryResponse;
import store.streetvendor.core.utils.dto.order.response.OrderListToBossResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BossOrderService {

    private final OrderHistoryRepository orderHistoryRepository;

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository historyRepository;

    private final StoreRepository storeRepository;

    private final BossRepository bossRepository;


    @Transactional
    public List<MemberOrderHistoryResponse> getOrderHistory(Long storeId, Long bossId) {

        List<OrderHistory> orderHistoryList = orderHistoryRepository.findOrderHistoryByStoreId(storeId, bossId);

        return orderHistoryList.stream()
            .map(MemberOrderHistoryResponse::historyOf)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberOrderHistoryResponse> getOrders(OrderStatus status,
                                                      Long storeId,
                                                      Long bossId) {
        List<Orders> getOrders = orderRepository.findOrdersByStoreIdAndBossIdAndStatus(storeId, bossId, status);
        return getOrders.stream()
            .map(MemberOrderHistoryResponse::orderOf)
            .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<OrderListToBossResponse> getAllOrders(Long storeId, Long bossId, OrderStatus orderStatus) {
        Boss boss = bossRepository.findByBossId(bossId);
        BossServiceUtil.validateBoss(boss, bossId);

        List<Orders> orders = orderRepository.findOrdersByStoreIdAndBossIdAndStatus(storeId, bossId, orderStatus);
        return orders.stream()
            .map(order -> OrderListToBossResponse.of(order, boss))
            .collect(Collectors.toList());
    }

    @Transactional
    public void changeStatusToPreparing(Long storeId, Long memberId, Long orderId) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId, memberId);
        Orders order = orderRepository.findByOrderId(orderId);
        OrderServiceUtils.validateOrder(order, orderId);
        order.changeStatusToPreparing();
    }

    @Transactional
    public void changeStatusToReadyToPickUp(Long memberId, AddNewOrderHistoryRequest request) {
        StoreServiceUtils.validateExistsStore(storeRepository, request.getStoreId(), memberId);
        Orders order = orderRepository.findByOrderId(request.getOrderId());
        OrderServiceUtils.validateOrder(order, order.getId());

        order.changeStatusToReadyToPickUp();
        addToCompletedOrder(request, memberId);
    }

    @Transactional
    public void cancelOrderByBoss(Long storeId, Long orderId, Long bossId) {
        Store store = storeRepository.findStoreByStoreIdAndBossId(storeId, bossId);
        StoreServiceUtils.validateStore(store, storeId);

        Orders order = orderRepository.findByOrderId(orderId);
        OrderServiceUtils.validateOrder(order, orderId);

        historyRepository.save(OrderHistory.cancel(order, store));
        orderRepository.delete(order);

    }

    @Transactional
    public void addToCompletedOrder(AddNewOrderHistoryRequest request, Long bossId) {
        Store store = storeRepository.findStoreByStoreIdAndBossId(request.getStoreId(), bossId);
        StoreServiceUtils.validateStore(store, request.getStoreId());

        Orders order = orderRepository.findByOrderId(request.getOrderId());
        OrderServiceUtils.validateOrder(order, order.getId());

        historyRepository.save(request.toEntity(store, order, order.getId(), OrderStatusCanceled.ACTIVE));
        orderRepository.delete(order);
    }


}
