package store.streetvendor.service.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.order.OrderMenu;
import store.streetvendor.domain.domain.order.Orders;
import store.streetvendor.domain.domain.store.Location;
import store.streetvendor.domain.domain.store.Store;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AddNewOrderRequest {

    private double distance;

    private List<OrderMenusRequest> menus;

    private Location location;

    private long storeId;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public AddNewOrderRequest(@NotBlank long storeId, List<OrderMenusRequest> menus, Location location) {
        this.storeId = storeId;
        this.location = location;
        this.menus = menus;
        this.distance = 2;
    }

    public Orders toEntity(Store store, Long memberId) {
        Orders order = Orders.newOrder(store.getId(), memberId);
        List<OrderMenu> orderMenus = this.menus.stream()
            .map(orderMenu -> orderMenu.toEntity(order, store))
            .collect(Collectors.toList());
        order.addMenus(orderMenus);
        return order;
    }

}
