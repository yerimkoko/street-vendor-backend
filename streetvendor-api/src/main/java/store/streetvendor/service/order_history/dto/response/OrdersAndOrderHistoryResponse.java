package store.streetvendor.service.order_history.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.service.order_history.dto.request.OrdersAndOrderHistoryRequest;


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
