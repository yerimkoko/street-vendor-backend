package store.streetvendor.core.utils.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderMenu;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.Store;

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
        return new OrderMenu(order, store.findMenu(menuId), count, store.findMenu(menuId).getPictureUrl());
    }

}
