package store.streetvendor.service.order_history.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.OrderStatus;
import store.streetvendor.domain.domain.order.OrderStatusCanceled;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryMenu;
import store.streetvendor.service.order.dto.response.OrderMenuResponse;
import store.streetvendor.service.order_history.dto.response.OrderHistoryMenuResponse;

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
