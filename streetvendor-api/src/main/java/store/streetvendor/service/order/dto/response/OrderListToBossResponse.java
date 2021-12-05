package store.streetvendor.service.order.dto.response;

import lombok.Builder;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.OrderStatus;
import store.streetvendor.domain.domain.order.repository.projection.OrdersToBossProjection;

import java.time.LocalDateTime;
import java.util.List;

public class OrderListToBossResponse {

    private Long memberId;

    private Long orderId;

    private OrderStatus orderStatus;

    private List<OrderMenu> orderMenus;

    private int totalPrice;

    private LocalDateTime createTime;

    @Builder
    public OrderListToBossResponse(Long memberId, Long orderId, OrderStatus orderStatus, List<OrderMenu> orderMenus, LocalDateTime createTime, int totalPrice) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderMenus = orderMenus;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
    }

    public static OrderListToBossResponse of(OrdersToBossProjection orders) {
        return OrderListToBossResponse.builder()
            .memberId(orders.getMemberId())
            .orderMenus(orders.getOrderMenus())
            .orderId(orders.getOrderId())
            .orderStatus(orders.getOrderStatus())
            .createTime(orders.getCreateTime())
            .totalPrice(orders.getOrderMenus().stream().mapToInt(OrderMenu::getTotalPrice).sum())
            .build();
    }
}
