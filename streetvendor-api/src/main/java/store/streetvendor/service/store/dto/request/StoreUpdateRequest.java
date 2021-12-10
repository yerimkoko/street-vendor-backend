package store.streetvendor.service.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Menu;
import store.streetvendor.domain.domain.store.PaymentMethod;
import store.streetvendor.domain.domain.store.Store;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StoreUpdateRequest {
    private String name;

    private String pictureUrl;

    private String location;

    private String description;

    private List<MenuRequest> menus;

    private List<PaymentMethod> paymentMethods;

    private List<BusinessHourRequest> businessHours;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public StoreUpdateRequest(String name, String pictureUrl, String location, String description,
                              List<MenuRequest> menus, List<PaymentMethod> paymentMethods, List<BusinessHourRequest> businessHours) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.menus = menus;
        this.paymentMethods = paymentMethods;
        this.businessHours = businessHours;
    }

    public List<Menu> toMenus(Store store) {
        return this.menus.stream()
            .map(menu -> menu.toEntity(store))
            .collect(Collectors.toList());
    }

}
