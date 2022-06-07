package store.streetvendor.service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.OrderStatus;
import store.streetvendor.domain.domain.order.Orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderListToBossResponse {

    private String nickName;

    private Long orderId;

    private OrderStatus orderStatus;

    private List<OrderMenuResponse> orderMenus;

    private int totalPrice;

    private LocalDateTime createTime;

    @Builder
    public OrderListToBossResponse(Long orderId, String nickName, OrderStatus orderStatus, List<OrderMenuResponse> orderMenus, LocalDateTime createTime, int totalPrice) {
        this.orderId = orderId;
        this.nickName = nickName;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
        this.orderMenus = orderMenus;
    }

    public static OrderListToBossResponse of(Orders orders, Member member) {
        return OrderListToBossResponse.builder()
            .orderMenus(orders.getOrderMenus().stream()
                .map(OrderMenuResponse::of)
                .collect(Collectors.toList())
            )
            .nickName(member.getNickName())
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
