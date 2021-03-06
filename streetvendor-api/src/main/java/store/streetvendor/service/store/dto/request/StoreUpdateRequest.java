package store.streetvendor.service.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StoreUpdateRequest {
    private String name;

    private String pictureUrl;

    private Location location;

    private String description;

    private List<MenuRequest> menus;

    private List<PaymentMethod> paymentMethods;

    private List<BusinessHourRequest> businessHours;

    private StoreCategory category;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public StoreUpdateRequest(String name, String pictureUrl, Location location, String description,
                              List<MenuRequest> menus, List<PaymentMethod> paymentMethods, List<BusinessHourRequest> businessHours, StoreCategory category) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.menus = menus;
        this.paymentMethods = paymentMethods;
        this.businessHours = businessHours;
        this.category = category;
    }

    public List<Menu> toMenus(Store store) {
        return this.menus.stream()
            .map(menu -> menu.toEntity(store))
            .collect(Collectors.toList());
    }

    public List<BusinessHours> toBusinessHours(Store store) {
        return this.businessHours.stream()
            .map(businessHours -> businessHours.toEntity(store))
            .collect(Collectors.toList());
    }

}
