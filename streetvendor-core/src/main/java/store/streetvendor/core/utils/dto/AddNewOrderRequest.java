package store.streetvendor.core.utils.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.order.OrderMenu;
import store.streetvendor.core.domain.order.Orders;
import store.streetvendor.core.domain.store.Location;
import store.streetvendor.core.domain.store.PaymentMethod;
import store.streetvendor.core.domain.store.Store;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AddNewOrderRequest {

    private Double distance;

    @NotNull
    private List<OrderMenusRequest> menus;

    @NotNull
    private Location location;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private long storeId;

    @NotBlank
    private String memberPhoneNumber;

    @NotNull
    private LocalDateTime pickUpTime;


    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public AddNewOrderRequest(@NotBlank long storeId, PaymentMethod paymentMethod, List<OrderMenusRequest> menus, Location location, String memberPhoneNumber, LocalDateTime pickUpTime) {
        this.storeId = storeId;
        this.location = location;
        this.paymentMethod = paymentMethod;
        this.menus = menus;
        this.distance = 2.0;
        this.memberPhoneNumber = memberPhoneNumber;
        this.pickUpTime = pickUpTime;
    }

    public Orders toEntity(Store store, Long memberId, PaymentMethod paymentMethod) {
        Orders order = Orders.newOrder(store, memberId, paymentMethod, pickUpTime);

        List<OrderMenu> orderMenus = this.menus.stream()
            .map(orderMenu -> orderMenu.toEntity(order, store))
            .collect(Collectors.toList());

        order.addMenus(orderMenus);
        return order;
    }

}
