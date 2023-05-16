package store.streetvendor.service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderMenu;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class OrderDetailViewResponse {

    private Long orderId;

    private Long storeId;

    private String storeName;

    private String orderMenu;

    private LocalDateTime orderTime;

    private String paymentMethod;

    private int totalOrderAmount;

    private String orderStatus;

    @Builder
    public OrderDetailViewResponse(Long orderId, Long storeId, String storeName, String orderMenu, LocalDateTime orderTime, String paymentMethod, int totalOrderAmount, String orderStatus) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.orderMenu = orderMenu;
        this.orderTime = orderTime;
        this.paymentMethod = paymentMethod;
        this.totalOrderAmount = totalOrderAmount;
        this.orderStatus = orderStatus;
    }

    public static OrderDetailViewResponse of(OrderHistory orderHistory) {
        return OrderDetailViewResponse.builder()
            .orderId(orderHistory.getOrderId())
            .storeId(orderHistory.getStoreInfo().getStoreId())
            .storeName(orderHistory.getStoreInfo().getName())
            .orderMenu(getOrderHistoryMenuName(orderHistory))
            .orderTime(orderHistory.getOrderCreateTime())
            .paymentMethod(orderHistory.getPaymentMethod().name())
            .totalOrderAmount(orderHistory.getMenus().stream()
                .mapToInt(OrderHistoryMenu::getPrice)
                .sum())
            .orderStatus(orderHistory.getOrderCanceledStatus().name())
            .build();
    }

    public static OrderDetailViewResponse orderOf(Orders order) {
        return OrderDetailViewResponse.builder()
            .orderId(order.getId())
            .storeId(order.getStore().getId())
            .storeName(order.getStore().getName())
            .orderMenu(getOrderMenuName(order))
            .orderTime(order.getCreatedAt())
            .paymentMethod(order.getPaymentMethod().name())
            .totalOrderAmount(order.getOrderMenus().stream()
                .mapToInt(OrderMenu::getTotalPrice)
                .sum())
            .orderStatus(order.getOrderStatus().name())
            .build();
    }

    private static String getOrderMenuName(Orders orders) {
        if (orders.getOrderMenus().isEmpty()) {
            return null;
        }
        return orders.getOrderMenus().get(0).getMenu().getName();
    }

    private static String getOrderHistoryMenuName(OrderHistory orderHistory) {
        if (orderHistory.getMenus().isEmpty()) {
            return null;
        }
        return orderHistory.getMenus().get(0).getMenuName();
    }
}
