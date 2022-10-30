package store.streetvendor.core.service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.model.exception.NotFoundException;
import store.streetvendor.core.domain.order.OrderRepository;
import store.streetvendor.core.domain.order.Orders;


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

    private static void validateOrder(Orders order, Long orderId) {
        if (order == null) {
            throw new NotFoundException((String.format("(%s)에 해당하는 주문이 없습니다.", orderId)));
        }
    }

}
