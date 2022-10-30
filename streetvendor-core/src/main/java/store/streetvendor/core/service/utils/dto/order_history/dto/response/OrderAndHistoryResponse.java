package store.streetvendor.core.service.utils.dto.order_history.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;
import store.streetvendor.core.service.utils.dto.response.OrderMenuResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class OrderAndHistoryResponse {

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
    public OrderAndHistoryResponse(Long memberId, Long orderId, Long storeId, int totalPrice, OrderStatus orderStatus, OrderStatusCanceled statusCanceled,
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

    public static OrderAndHistoryResponse onOrder(Orders orders) {
        List<OrderMenuResponse> orderMenusResponse = orders.getOrderMenus().stream()
            .map(OrderMenuResponse::of)
            .collect(Collectors.toList());

        int total = orderMenusResponse.stream()
            .mapToInt(OrderMenuResponse::getPrice)
            .sum();

        return OrderAndHistoryResponse.builder()
            .memberId(orders.getMemberId())
            .orderId(orders.getId())
            .storeId(orders.getStore().getId())
            .orderStatus(orders.getOrderStatus())
            .orderMenuResponses(orderMenusResponse)
            .totalPrice(total)
            .orderTime(orders.getCreatedAt())
            .build();
    }

    public static OrderAndHistoryResponse completedOrder(OrderHistory orderHistory) {
        List<OrderHistoryMenuResponse> responses = orderHistory.getMenus().stream()
            .map(OrderHistoryMenuResponse::of)
            .collect(Collectors.toList());

        int total = responses.stream()
            .mapToInt(OrderHistoryMenuResponse::getPrice)
            .sum();

        List<OrderHistoryMenu> orderHistoryMenus = orderHistory.getMenus();

        return OrderAndHistoryResponse.builder()
            .orderId(orderHistory.getOrderId())
            .storeId(orderHistory.getStoreInfo().getStoreId())
            .totalPrice(total)
            .statusCanceled(orderHistory.getOrderCanceledStatus())
            .memberId(orderHistory.getMemberId())
            .orderHistoryMenuResponses(orderHistoryMenus.stream()
                .map(OrderHistoryMenuResponse::of)
                .collect(Collectors.toList()))
            .orderTime(orderHistory.getCreatedAt())
            .build();
    }

}
