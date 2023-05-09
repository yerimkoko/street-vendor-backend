package store.streetvendor.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;
import store.streetvendor.core.domain.order_history.OrderHistoryMenuRepository;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;
import store.streetvendor.core.domain.storemenuordercount.StoreMenuOrderCountRepository;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.domain.store.StoreRepository;
import store.streetvendor.core.utils.service.OrderServiceUtils;
import store.streetvendor.core.utils.service.StoreServiceUtils;
import store.streetvendor.core.utils.dto.AddNewOrderRequest;
import store.streetvendor.core.utils.dto.order_history.response.OrderAndHistoryResponse;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderHistoryMenuRepository orderHistoryMenuRepository;

    private final StoreRepository storeRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    private final StoreMenuOrderCountRepository storeMenuOrderCountRepository;


    @Transactional
    public void addNewOrder(AddNewOrderRequest request, Long memberId) {
        Store store = storeRepository.findOpenedStoreByStoreIdAndLocationAndDistanceLessThan(request.getStoreId(), request.getLocation().getLatitude(),
            request.getLocation().getLongitude(),
            request.getDistance());

        if (store == null) {
            throw new NotFoundException(String.format("거리 내에 찾으시는 가게 (%s)가 없거나 영업중인 가게가 아닙니다.", request.getStoreId()));
        }

        orderRepository.save(request.toEntity(store, memberId, request.getPaymentMethod()));

        request.getMenus()
            .forEach(r -> storeMenuOrderCountRepository
                .increaseByCount(store.getId(), r.getMenuId(), r.getCount()));

    }



    @Transactional
    public void cancelOrderByUser(Long orderId, Long memberId) {
        Orders order = orderRepository.findByOrderAndMemberId(orderId, memberId);
        OrderServiceUtils.validateOrder(order, orderId);
        order.validateUserCan();

        Store store = storeRepository.findStoreByStoreId(order.getStore().getId());
        StoreServiceUtils.validateStore(store, order.getStore().getId());

        OrderHistory orderHistory = OrderHistory.cancel(order, store);

        orderHistoryRepository.save(orderHistory);

        orderHistoryMenuRepository.saveAll(order.getOrderMenus()
            .stream()
            .map(menu -> OrderHistoryMenu.of(orderHistory, menu.getMenu().getName(), menu.getCount(), menu.getTotalPrice(), menu.getPictureUrl()))
            .collect(Collectors.toList()));

        orderRepository.delete(order);

        order.getOrderMenus()
            .forEach(r -> storeMenuOrderCountRepository
                .decreaseByCount(store.getId(), r.getMenu().getId(), r.getCount()));

    }

    @Transactional
    public List<OrderAndHistoryResponse> getMemberAllOrders(Long memberId) {

        List<Orders> orders = orderRepository.findOrdersByMemberId(memberId);

        List<OrderHistory> orderHistories = orderHistoryRepository.findOrderHistoryByMemberId(memberId);

        List<OrderAndHistoryResponse> onOrders = orders.stream()
            .map(OrderAndHistoryResponse::onOrder)
            .collect(Collectors.toList());

        List<OrderAndHistoryResponse> complete = orderHistories.stream()
            .map(OrderAndHistoryResponse::completedOrder)
            .collect(Collectors.toList());

        Set<OrderAndHistoryResponse> allOrderToBoss = new HashSet<>();
        allOrderToBoss.addAll(onOrders);
        allOrderToBoss.addAll(complete);

        return allOrderToBoss.stream()
            .sorted(Comparator.comparing(OrderAndHistoryResponse::getOrderId).reversed())
            .collect(Collectors.toList());

    }


}
