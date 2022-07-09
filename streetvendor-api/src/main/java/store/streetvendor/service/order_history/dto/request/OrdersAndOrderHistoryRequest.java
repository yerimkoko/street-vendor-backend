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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static OrdersAndOrderHistoryRequest onOrder(Orders orders, List<OrderMenu> orderMenus) {
        List<OrderMenuResponse> orderMenusResponse = orderMenus.stream()
            .map(OrderMenuResponse::of)
            .collect(Collectors.toList());

        int total = orderMenusResponse.stream().mapToInt(OrderMenuResponse::getPrice).sum();

        return OrdersAndOrderHistoryRequest.builder()
            .memberId(orders.getMemberId())
            .orderId(orders.getId())
            .storeId(orders.getStoreId())
            .orderStatus(orders.getOrderStatus())
            .statusCanceled(orders.getOrderStatusCanceled())
            .orderMenuResponses(orderMenusResponse)
            .totalPrice(total)
            .orderTime(orders.getCreatedAt())
            .build();
    }

    public static OrdersAndOrderHistoryRequest completedOrder(OrderHistory orderHistory) {

        List<OrderHistoryMenu> orderHistoryMenus = orderHistory.getMenus();

        return OrdersAndOrderHistoryRequest.builder()
            .memberId(orderHistory.getMemberId())
            .orderStatus(null)
            .orderHistoryMenuResponses(orderHistoryMenus.stream()
                .map(OrderHistoryMenuResponse::of)
                .collect(Collectors.toList()))
            .orderTime(orderHistory.getCreatedAt())
            .build();
    }

}
