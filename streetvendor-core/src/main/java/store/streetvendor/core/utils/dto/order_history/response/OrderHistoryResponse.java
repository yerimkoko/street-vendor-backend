package store.streetvendor.core.utils.dto.order_history.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class OrderHistoryResponse {

    private Long orderId;

    private OrderHistoryMemberResponse member;

    private OrderHistoryStoreResponse store;

    private List<OrderHistoryMenuResponse> menus;

    @Builder
    public OrderHistoryResponse(Long orderId, List<OrderHistoryMenuResponse> menus, OrderHistoryMemberResponse member, OrderHistoryStoreResponse store) {
        this.orderId = orderId;
        this.menus = menus;
        this.member = member;
        this.store = store;
    }

    public static OrderHistoryResponse of(OrderHistory orderHistory, OrderHistoryStoreResponse storeResponse, Member member) {

        List<OrderHistoryMenu> orderHistoryMenus = orderHistory.getMenus();

        return OrderHistoryResponse.builder()
            .orderId(orderHistory.getId())
            .store(storeResponse)
            .member(OrderHistoryMemberResponse.of(member))
            .menus(orderHistoryMenus.stream()
                .map(OrderHistoryMenuResponse::of)
                .collect(Collectors.toList()))
            .build();
    }
}
