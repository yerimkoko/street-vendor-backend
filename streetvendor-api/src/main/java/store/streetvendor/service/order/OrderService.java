package store.streetvendor.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberRepository;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.order.OrderStatusCanceled;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryRepository;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.model.exception.NotFoundException;
import store.streetvendor.domain.domain.store.StoreRepository;
import store.streetvendor.domain.service.utils.OrderServiceUtils;
import store.streetvendor.service.order.dto.request.AddNewOrderRequest;
import store.streetvendor.service.order.dto.response.OrderListToBossResponse;
import store.streetvendor.service.order_history.dto.request.AddNewOrderHistoryRequest;
import store.streetvendor.service.order_history.dto.response.OrderAndHistoryResponse;
import store.streetvendor.domain.service.utils.StoreServiceUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;

    private final OrderHistoryRepository historyRepository;

    @Transactional
    public void addNewOrder(AddNewOrderRequest request, Long memberId) {
        StoreServiceUtils.findByStoreId(storeRepository, request.getStoreId());
        Store store = storeRepository.findOpenedStoreByStoreIdAndLocationAndDistanceLessThan(request.getStoreId(), request.getLocation().getLatitude(),
            request.getLocation().getLongitude(),
            request.getDistance());

        if (store == null) {
            throw new NotFoundException(String.format("찾으시는 가게 (%s)가 없습니다.", request.getStoreId()));
        }

        orderRepository.save(request.toEntity(store, memberId));
    }

    // 사장님 기준 (주문을 받았을 때) -> 주문을 확인하는 로직
    @Transactional(readOnly = true)
    public List<OrderListToBossResponse> getAllOrders(Long storeId, Long memberId) {
        StoreServiceUtils.validateExistsStore(storeRepository, storeId, memberId);
        List<Orders> orders = orderRepository.findOrdersByStoreId(storeId);
        Member member = memberRepository.findMemberById(memberId);
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
    public void cancelOrderByUser(Long orderId, Long memberId) {
        Orders order = OrderServiceUtils.findMyOrderByOrderIdAndMemberId(orderRepository, orderId, memberId);

        order.validateByUser();

        Store store = StoreServiceUtils.findByStoreId(storeRepository, order.getStoreId());

        OrderHistory orderHistory = OrderHistory.cancel(order, store);

        historyRepository.save(orderHistory);

        orderRepository.delete(order);

    }

    @Transactional
    public void addToCompletedOrder(AddNewOrderHistoryRequest request, Long memberId) {
        Store store = StoreServiceUtils.findStoreByStoreIdAndMemberId(storeRepository, request.getStoreId(), memberId);
        Orders order = OrderServiceUtils.findByOrderId(orderRepository, request.getOrderId());
        historyRepository.save(request.toEntity(store, store.getMemberId(), order.getId(), OrderStatusCanceled.ACTIVE));
        orderRepository.delete(order);
    }

    @Transactional
    public List<OrderAndHistoryResponse> memberOrders(Long memberId) {

        List<Orders> orders = orderRepository.findOrdersByMemberId(memberId);

        List<OrderHistory> orderHistories = historyRepository.findOrderHistoryByMemberId(memberId);

        List<OrderAndHistoryResponse> onOrders = orders.stream()
            .map(OrderAndHistoryResponse::onOrder
            ).collect(Collectors.toList());

        List<OrderAndHistoryResponse> complete = orderHistories.stream()
            .map(OrderAndHistoryResponse::completedOrder)
            .collect(Collectors.toList());

        List<OrderAndHistoryResponse> allOrders = Stream.concat(onOrders.stream(), complete.stream())
            .sorted(Comparator.comparing(OrderAndHistoryResponse::getOrderId))
            .collect(Collectors.toList());

        return allOrders;

    }

}
