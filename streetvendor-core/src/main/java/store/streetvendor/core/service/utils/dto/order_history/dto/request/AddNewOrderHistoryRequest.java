package store.streetvendor.core.service.utils.dto.order_history.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderStatusCanceled;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;
import store.streetvendor.core.domain.store.Store;

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

    public OrderHistory toEntity(Store store, Orders orders, Long memberId, OrderStatusCanceled orderStatusCanceled) {
        OrderHistory orderHistory = OrderHistory.newHistory(store, orders, memberId, orderStatusCanceled);
        List<OrderHistoryMenu> historyMenus = this.menus.stream()
            .map(menu -> menu.toEntity(orderHistory))
            .collect(Collectors.toList());
        orderHistory.addMenus(historyMenus);
        return orderHistory;

    }

}
