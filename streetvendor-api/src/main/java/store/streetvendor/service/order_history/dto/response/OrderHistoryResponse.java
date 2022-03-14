package store.streetvendor.service.order_history.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Builder(access = AccessLevel.PRIVATE)
    public OrderHistoryResponse(Long memberId, Long orderId, Long storeId, List<OrderHistoryMenuResponse> menus) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.storeId = storeId;
        this.menus = menus;
    }

    public static OrderHistoryResponse toEntity(Long memberId, Long storeId, Long orderId, List<OrderHistoryMenuResponse> historyMenus, OrderHistoryMenu historyMenu) {
        return OrderHistoryResponse.builder()
            .orderId(orderId)
            .storeId(storeId)
            .memberId(memberId)
            .menus(historyMenus.stream()
                .map(menu -> OrderHistoryMenuResponse
                    .toEntity(historyMenu, storeId))
                .collect(Collectors.toList()))
            .build();
    }
}
