package store.streetvendor.core.utils.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.exception.NotFoundException;
import store.streetvendor.core.domain.order.Orders;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceUtils {

    public static void validateOrderHistoryIsEmpty(List<OrderHistory> orderHistories, Long orderId) {
        if (orderHistories.isEmpty()) {
            throw new NotFoundException(String.format("[%s]에 해당하는 주문은 존재하지 않습니다.", orderId));
        }
    }

    public static void validateOrder(Orders order, Long orderId) {
        if (order == null) {
            throw new NotFoundException((String.format("(%s)에 해당하는 주문이 없습니다.", orderId)));
        }
    }

}
