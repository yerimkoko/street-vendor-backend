package store.streetvendor.service.order;

import store.streetvendor.domain.domain.order.OrderRepository;
import store.streetvendor.domain.domain.order.Orders;

public class OrderServiceUtils {
    OrderServiceUtils() {
    }
    public static Orders findByOrderId(OrderRepository orderRepository, Long orderId) {
        Orders order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException(String.format("(%s)에 해당하는 주문이 없습니다.", orderId));
        }
        return order;
    }
}
