package store.streetvendor.core.utils.dto.order_history.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.utils.dto.order_history.response.OrderHistoryMenuResponse;
import store.streetvendor.core.utils.dto.order.response.OrderMenuResponse;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class OrdersAndOrderHistoryRequest {

    private Long memberId;

    private Long orderId;

    private Long storeId;

    private int totalPrice;

    private OrderStatus orderStatus;

    private OrderStatusCanceled statusCanceled;

    private List<OrderMenuResponse> orderMenuResponses;

    private List<OrderHistoryMenuResponse> orderHistoryMenuResponses;

    private LocalDateTime orderTime;


    @Builder
    public OrdersAndOrderHistoryRequest(Long memberId, Long orderId, Long storeId, int totalPrice, OrderStatus orderStatus, OrderStatusCanceled statusCanceled,
                                        List<OrderMenuResponse> orderMenuResponses, List<OrderHistoryMenuResponse> orderHistoryMenuResponses, LocalDateTime orderTime) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.storeId = storeId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.statusCanceled = statusCanceled;
        this.orderMenuResponses = orderMenuResponses;
        this.orderHistoryMenuResponses = orderHistoryMenuResponses;
        this.orderTime = orderTime;
    }

}
