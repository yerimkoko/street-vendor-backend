package store.streetvendor.service;


import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.boss.BossRepository;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberRepository;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreRepository;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.utils.service.BossServiceUtil;
import store.streetvendor.core.utils.service.MemberServiceUtils;
import store.streetvendor.core.utils.service.OrderServiceUtils;
import store.streetvendor.core.utils.service.StoreServiceUtils;
import store.streetvendor.core.utils.dto.order_history.request.AddNewOrderHistoryRequest;
import store.streetvendor.core.utils.dto.response.MemberOrderHistoryResponse;
import store.streetvendor.core.utils.dto.response.OrderListToBossResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BossOrderService {

    private final OrderHistoryRepository orderHistoryRepository;

    private final OrderRepository orderRepository;

    private final OrderHistoryRepository historyRepository;

    private final MemberRepository memberRepository;

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
    public List<OrderListToBossResponse> getAllOrders(Long storeId, Long memberId, OrderStatus orderStatus) {
        Member member = MemberServiceUtils.findByMemberId(memberRepository, memberId);
        List<Orders> orders = orderRepository.findOrdersByStoreIdAndBossIdAndStatus(storeId, memberId, orderStatus);
        return orders.stream()
            .map(order -> OrderListToBossResponse.of(order, member))
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
