package store.streetvendor.core.utils.dto.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderStatus;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.utils.dto.OrderMenusRequest;

import java.util.List;

@NoArgsConstructor
@Getter
public class OrderListToMemberResponse {

    private Long orderId;

    private Long memberId;

    private Store store;

    private OrderStatus orderStatus;

    private OrderStatusCanceled active;

    private List<OrderMenusRequest> requests;

    @Builder
    public OrderListToMemberResponse(Long orderId, Long memberId, Store store, OrderStatus orderStatus, OrderStatusCanceled active, List<OrderMenusRequest> requests) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.store = store;
        this.orderStatus = orderStatus;
        this.active = active;
        this.requests = requests;
    }

    public static OrderListToMemberResponse of(Orders order, Store store) {
        return OrderListToMemberResponse.builder()
            .memberId(order.getMemberId())
            .store(store)
            .orderId(order.getId())
            .orderStatus(order.getOrderStatus())
            .build();
    }
}
