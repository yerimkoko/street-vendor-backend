package store.streetvendor.service.order_history.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryMenu;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class OrderHistoryResponse {

    private Long memberId;

    private Long orderId;

    private Long storeId;

    private List<OrderHistoryMenuResponse> menus;

    @Builder
    public OrderHistoryResponse(Long memberId, Long orderId, Long storeId, List<OrderHistoryMenuResponse> menus) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.storeId = storeId;
        this.menus = menus;
    }

    public static OrderHistoryResponse of(OrderHistory orderHistory) {
        List<OrderHistoryMenu> orderHistoryMenus = orderHistory.getMenus();
        return OrderHistoryResponse.builder()
            .orderId(orderHistory.getId())
            .storeId(orderHistory.getStoreId())
            .memberId(orderHistory.getMemberId())
            .menus(orderHistoryMenus.stream()
                .map(OrderHistoryMenuResponse::of)
                .collect(Collectors.toList()))
            .build();
    }
}
