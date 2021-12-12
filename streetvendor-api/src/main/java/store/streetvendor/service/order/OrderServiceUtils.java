package store.streetvendor.service.order;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.order.OrderStatus;
import store.streetvendor.domain.domain.order.Orders;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceUtils {

    public static Orders findByOrderId(OrderRepository orderRepository, Long orderId) {
        Orders order = orderRepository.findByOrderId(orderId);
        validateOrder(order, orderId);
        return order;
    }

    public static List<Orders> findByOrderIdAndMemberId(OrderRepository orderRepository, Long orderId, Long memberId) {
        List<Orders> orders = orderRepository.findOrdersByOrderIdAndMemberId(orderId, memberId);
        return orders.stream()
            .filter(order -> order.getOrderStatus().equals(OrderStatus.REQUEST))
            .collect(Collectors.toList());
    }

    public static Orders findMyOrderByOrderIdAndMemberId(OrderRepository orderRepository, Long orderId, Long memberId) {
        Orders order = orderRepository.findByOrderAndMemberId(orderId, memberId);
        validateOrder(order, orderId);

        return order;
    }

    private static void validateOrder(Orders order, Long orderId) {
        if (order == null) {
            throw new IllegalArgumentException(String.format("(%s)에 해당하는 주문이 없습니다.", orderId));
        }
    }

}
