package store.streetvendor.service.order_history.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryMenu;
import store.streetvendor.domain.domain.store.Store;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AddNewOrderHistoryRequest {

    private Long orderId;

    @NotBlank
    private Long storeId;

    private List<OrderHistoryMenusRequest> menus;

    @Builder
    public AddNewOrderHistoryRequest(Long storeId, List<OrderHistoryMenusRequest> menus, Long orderId) {
        this.storeId = storeId;
        this.menus = menus;
        this.orderId = orderId;
    }

    public OrderHistory toEntity(Store store, Long memberId, Long orderId) {
        OrderHistory orderHistory = OrderHistory.newHistory(store, memberId, orderId);
        List<OrderHistoryMenu> historyMenus = this.menus.stream()
            .map(menu -> menu.toEntity(orderHistory))
            .collect(Collectors.toList());
        orderHistory.addMenus(historyMenus);
        return orderHistory;

    }

}
