package store.streetvendor.core.utils.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.Orders;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceUtils {

    public static Orders findByOrderId(OrderRepository orderRepository, Long orderId) {
        Orders order = orderRepository.findByOrderId(orderId);
        validateOrder(order, orderId);
        return order;
    }

    public static Orders findMyOrderByOrderIdAndMemberId(OrderRepository orderRepository, Long orderId, Long memberId) {
        Orders order = orderRepository.findByOrderAndMemberId(orderId, memberId);
        validateOrder(order, orderId);
        return order;
    }

    public static OrderHistory findOrderHistoryByOrderId(OrderHistoryRepository orderHistoryRepository, Long orderId) {
        OrderHistory orderHistory = orderHistoryRepository.findOrderHistoryByOrderId(orderId);
        validateOrderHistory(orderHistory, orderId);
        return orderHistory;
    }

    public static List<OrderHistory> findOrderHistoryByOrderIdAndMemberId(OrderHistoryRepository orderHistoryRepository, Long orderId, Long memberId) {
        List<OrderHistory> histories = orderHistoryRepository.findOrderHistoryByOrderIdAndMemberId(orderId, memberId);
        if (histories.isEmpty()) {
            throw new NotFoundException(String.format("[%s]에 해당하는 주문은 존재하지 않습니다.", orderId));
        }
        return histories;
    }

    private static void validateOrder(Orders order, Long orderId) {
        if (order == null) {
            throw new NotFoundException((String.format("(%s)에 해당하는 주문이 없습니다.", orderId)));
        }
    }

    private static void validateOrderHistory(OrderHistory orderHistory, Long orderId) {
        if (orderHistory == null) {
            throw new NotFoundException(String.format("[%s]에 해당하는 주문은 존재하지 않습니다.", orderId));
        }
    }

}
