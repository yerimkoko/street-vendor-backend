package store.streetvendor.core.utils;

import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryRepository;

public class OrderHistoryServiceUtils {

    private OrderHistoryServiceUtils() {

    }
    public static OrderHistory findOrderHistoryByOrderId(OrderHistoryRepository orderHistoryRepository, Long orderId) {
        OrderHistory orderHistory = orderHistoryRepository.findOrderHistoryByOrderId(orderId);
        if (orderHistory == null) {
            throw new NotFoundException(String.format("<%s>의 해당하는 주문이 존재하지 않습니다.", orderId));
        }
        return orderHistory;
    }
}
