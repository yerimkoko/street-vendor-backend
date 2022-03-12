package store.streetvendor.service.order_history.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order_history.OrderHistory;
import store.streetvendor.domain.domain.order_history.OrderHistoryMenu;
import store.streetvendor.domain.domain.store.Menu;

@Getter
@NoArgsConstructor
public class OrderHistoryMenusRequest {

    private String menuName;

    private int price;

    private int count;

    private int totalCount;

    @Builder
    public OrderHistoryMenusRequest(Menu menu, OrderMenu orderMenu) {
        this.menuName = menu.getName();
        this.count = menu.getCount();
        this.price = menu.getPrice();
        this.totalCount = orderMenu.getCount();
    }

    public OrderHistoryMenu toEntity(OrderHistory orderHistory) {
        return new OrderHistoryMenu(orderHistory, menuName, count, price);
    }
}
