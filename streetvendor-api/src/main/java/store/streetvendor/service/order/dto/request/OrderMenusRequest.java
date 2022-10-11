package store.streetvendor.service.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.store.menu.Menu;
import store.streetvendor.domain.domain.store.Store;

@Getter
@NoArgsConstructor
public class OrderMenusRequest {

    private Long menuId;

    private int count;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public OrderMenusRequest(Menu menu, int count) {
        this.menuId = menu.getId();
        this.count = count;
    }

    public OrderMenu toEntity(Orders order, Store store) {
        return new OrderMenu(order, store.findMenu(menuId), count);
    }

}
