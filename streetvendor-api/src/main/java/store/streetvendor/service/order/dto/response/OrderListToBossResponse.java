package store.streetvendor.service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.OrderStatus;
import store.streetvendor.domain.domain.order.Orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderListToBossResponse {

    private Long memberId;

    private Long orderId;

    private OrderStatus orderStatus;

    private List<OrderMenuResponse> orderMenus;

    private int totalPrice;

    private LocalDateTime createTime;

    @Builder
    public OrderListToBossResponse(Long memberId, Long orderId, OrderStatus orderStatus, List<OrderMenuResponse> orderMenus, LocalDateTime createTime, int totalPrice) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
        this.orderMenus = orderMenus;
    }

    public static OrderListToBossResponse of(Orders orders) {
        return OrderListToBossResponse.builder()
            .memberId(orders.getMemberId())
            .orderMenus(orders.getOrderMenus().stream()
                .map(OrderMenuResponse::of)
                .collect(Collectors.toList())
            )
            .orderId(orders.getId())
            .orderStatus(orders.getOrderStatus())
            .createTime(orders.getCreatedAt())
            .totalPrice(getTotalPrice(orders.getOrderMenus()))
            .build();
    }

    public static int getTotalPrice(List<OrderMenu> orderMenus) {
        return orderMenus.stream().mapToInt(OrderMenu::getTotalPrice).sum();
    }
}
