package store.streetvendor.core.service.utils.dto.order_history.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderMenu;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.order_history.OrderHistoryMenu;
import store.streetvendor.core.domain.store.menu.Menu;

@Getter
@NoArgsConstructor
public class OrderHistoryMenusRequest {

    private String menuName;

    private int price;

    private int count;

    private int totalCount;

    private String pictureUrl;

    @Builder
    public OrderHistoryMenusRequest(Menu menu, OrderMenu orderMenu) {
        this.menuName = menu.getName();
        this.count = menu.getMenuCount();
        this.price = menu.getPrice();
        this.totalCount = orderMenu.getCount();
        this.pictureUrl = menu.getPictureUrl();
    }

    public OrderHistoryMenu toEntity(OrderHistory orderHistory) {
        return new OrderHistoryMenu(orderHistory, menuName, count, price, pictureUrl);
    }
}
