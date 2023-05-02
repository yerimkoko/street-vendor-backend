package store.streetvendor.core.utils.dto.order_history;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderMenu;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;
import store.streetvendor.core.domain.store.PaymentMethod;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class MemberOrderHistoryResponse {

    private Long storeId;

    private Long orderId;

    private String storeName;

    private LocalDateTime orderCreateAt;

    private OrderStatus orderStatus;

    private OrderStatusCanceled status;

    private String firstMenuName;

    private int totalMenuCount;

    private int totalAmount;

    private PaymentMethod payment;

    @Builder
    public MemberOrderHistoryResponse(Long storeId, Long orderId, String storeName, LocalDateTime orderCreateAt, OrderStatus orderStatus, OrderStatusCanceled status, String firstMenuName, int totalMenuCount, int totalAmount, PaymentMethod payment) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.storeName = storeName;
        this.orderCreateAt = orderCreateAt;
        this.orderStatus = orderStatus;
        this.status = status;
        this.firstMenuName = firstMenuName;
        this.totalMenuCount = totalMenuCount;
        this.totalAmount = totalAmount;
        this.payment = payment;
    }



    public static MemberOrderHistoryResponse historyOf(OrderHistory orderHistory) {
        return MemberOrderHistoryResponse.builder()
            .firstMenuName(validateMenus(orderHistory))
            .storeId(orderHistory.getStoreInfo().getStoreId())
            .orderId(orderHistory.getOrderId())
            .status(orderHistory.getOrderCanceledStatus())
            .storeName(orderHistory.getStoreInfo().getName())
            .orderCreateAt(orderHistory.getOrderCreateTime())
            .totalMenuCount(orderHistory.getMenus().size())
            .totalAmount(orderHistory.getMenus().stream().mapToInt(OrderHistoryMenu::getPrice).sum())
            .payment(orderHistory.getPaymentMethod())
            .build();
    }

    public static MemberOrderHistoryResponse orderOf(Orders orders) {

        return MemberOrderHistoryResponse.builder()
            .payment(orders.getPaymentMethod())
            .storeId(orders.getStore().getId())
            .orderId(orders.getId())
            .orderStatus(orders.getOrderStatus())
            .orderCreateAt(orders.getCreatedAt())
            .storeName(orders.getStore().getName())
            .totalAmount(orders.getOrderMenus().stream()
                .mapToInt(OrderMenu::getTotalPrice).sum())
            .totalMenuCount(orders.getOrderMenus().size())
            .payment(orders.getPaymentMethod())
            .build();

    }

    private static String validateMenus(OrderHistory orderHistory) {
        if (orderHistory.getMenus().isEmpty()) {
            return null;
        }
        return orderHistory.getMenus().get(0).getMenuName();

    }

}
