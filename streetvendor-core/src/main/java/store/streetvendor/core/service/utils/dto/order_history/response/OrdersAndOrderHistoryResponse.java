package store.streetvendor.core.service.utils.dto.order_history.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.service.utils.dto.order_history.request.OrdersAndOrderHistoryRequest;


@NoArgsConstructor
@Getter
public class OrdersAndOrderHistoryResponse {

    private Long orderId;

    private OrdersAndOrderHistoryRequest request;

    public OrdersAndOrderHistoryResponse(OrdersAndOrderHistoryRequest request) {
        this.orderId = request.getOrderId();
        this.request = request;
    }

    public static OrdersAndOrderHistoryResponse of(OrdersAndOrderHistoryRequest request) {
        return new OrdersAndOrderHistoryResponse(request);
    }
}
