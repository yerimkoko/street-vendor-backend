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
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.model.exception.NotFoundException;
import store.streetvendor.core.domain.store.StoreRepository;
import store.streetvendor.core.service.utils.OrderServiceUtils;
import store.streetvendor.core.service.utils.dto.AddNewOrderRequest;
import store.streetvendor.core.service.utils.StoreServiceUtils;
import store.streetvendor.core.service.utils.dto.order_history.dto.response.OrderAndHistoryResponse;

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

    private final OrderHistoryRepository historyRepository;

    @Transactional
    public void addNewOrder(AddNewOrderRequest request, Long memberId) {
        Store store = storeRepository.findOpenedStoreByStoreIdAndLocationAndDistanceLessThan(request.getStoreId(), request.getLocation().getLatitude(),
            request.getLocation().getLongitude(),
            request.getDistance());

        if (store == null) {
            throw new NotFoundException(String.format("거리 내에 찾으시는 가게 (%s)가 없습니다.", request.getStoreId()));
        }

        orderRepository.save(request.toEntity(store, memberId, request.getPaymentMethod()));
    }



    @Transactional
    public void cancelOrderByUser(Long orderId, Long memberId) {
        Orders order = OrderServiceUtils.findMyOrderByOrderIdAndMemberId(orderRepository, orderId, memberId);

        order.validateUserCan();

        Store store = StoreServiceUtils.findByStoreId(storeRepository, order.getStore().getId());

        OrderHistory orderHistory = OrderHistory.cancel(order, store);

        orderHistoryMenuRepository.saveAll(order.getOrderMenus()
            .stream()
            .map(menu -> OrderHistoryMenu.of(menu.getMenu().getName(), menu.getCount(), menu.getTotalPrice()))
            .collect(Collectors.toList()));

        historyRepository.save(orderHistory);

        orderRepository.delete(order);

    }

    @Transactional
    public List<OrderAndHistoryResponse> getMemberAllOrders(Long memberId) {

        List<Orders> orders = orderRepository.findOrdersByMemberId(memberId);

        List<OrderHistory> orderHistories = historyRepository.findOrderHistoryByMemberId(memberId);

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
