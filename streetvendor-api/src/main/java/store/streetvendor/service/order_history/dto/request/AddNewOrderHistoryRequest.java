package store.streetvendor.service.order_history.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryMenu;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AddNewOrderHistoryRequest {

    private Long orderId;

    private Long storeId;

    private List<OrderHistoryMenusRequest> menus;

    @Builder
    public AddNewOrderHistoryRequest(Long orderId, @NotBlank Long storeId, List<OrderHistoryMenusRequest> menus) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.menus = menus;
    }

    public OrderHistory toEntity(Long storeId, Long memberId) {
        OrderHistory orderHistory = OrderHistory.newHistory(storeId, memberId);
        List<OrderHistoryMenu> historyMenus = this.menus.stream()
            .map(menu -> menu.toEntity(orderHistory))
            .collect(Collectors.toList());
        orderHistory.addMenus(historyMenus);
        return orderHistory;

    }

}
