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

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class OrdersAndOrderHistoryRequest {

    private Long memberId;

    private Long orderId;

    private Long storeId;

    private OrderStatus orderStatus;

    private OrderStatusCanceled statusCanceled;

    private List<OrderMenuResponse> orderMenuResponses;

    private List<OrderHistoryMenuResponse> orderHistoryMenuResponses;


    @Builder
    public OrdersAndOrderHistoryRequest(Long memberId, Long orderId, Long storeId, OrderStatus orderStatus, OrderStatusCanceled statusCanceled,
                                        List<OrderMenuResponse> orderMenuResponses, List<OrderHistoryMenuResponse> orderHistoryMenuResponses) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.storeId = storeId;
        this.orderStatus = orderStatus;
        this.statusCanceled = statusCanceled;
        this.orderMenuResponses = orderMenuResponses;
        this.orderHistoryMenuResponses = orderHistoryMenuResponses;
    }

    public static OrdersAndOrderHistoryRequest onOrder(Orders orders, List<OrderMenu> orderMenus) {
        List<OrderMenuResponse> orderMenusResponse = orderMenus.stream()
            .map(OrderMenuResponse::of)
            .collect(Collectors.toList());

        return OrdersAndOrderHistoryRequest.builder()
            .memberId(orders.getMemberId())
            .orderId(orders.getId())
            .storeId(orders.getStoreId())
            .orderStatus(orders.getOrderStatus())
            .statusCanceled(orders.getOrderStatusCanceled())
            .orderMenuResponses(orderMenusResponse)
            .build();
    }

    public static OrdersAndOrderHistoryRequest completedOrder(OrderHistory orderHistory) {
        List<OrderHistoryMenu> orderHistoryMenus = orderHistory.getMenus();
        return OrdersAndOrderHistoryRequest.builder()
            .memberId(orderHistory.getMemberId())
            .orderStatus(null)
            .orderHistoryMenuResponses(orderHistoryMenus.stream().map(OrderHistoryMenuResponse::of).collect(Collectors.toList()))
            .build();
    }

}
