package store.streetvendor.service.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order.Order;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.Store;

@Getter
@NoArgsConstructor
public class OrderMenusRequest {

    private long menuId;

    private int totalCount;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public OrderMenusRequest(long menuId, int totalCount) {
        this.menuId = menuId;
        this.totalCount = totalCount;
    }

    public OrderMenu toEntity(Store store, Order order) {
        Menu menu = store.findMenu(menuId);
        return new OrderMenu(order, menu.getId(), menu.getCount(), menu.getName(), menu.getPrice(), totalCount);
    }

}
