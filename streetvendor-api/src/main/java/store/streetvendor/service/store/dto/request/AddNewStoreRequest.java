package store.streetvendor.service.store.dto.request;

import lombok.*;
import store.streetvendor.domain.domain.store.PaymentMethod;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreCategory;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddNewStoreRequest {

    @NotBlank
    private String name;

    private String pictureUrl;

    private String location;

    private String description;

    private StoreCategory category;

    private List<BusinessHourRequest> businessHours;

    private List<MenuRequest> menus;

    private List<PaymentMethod> paymentMethods;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public AddNewStoreRequest(@NotBlank String name, String pictureUrl, String location, String description,
                              StoreCategory category, List<MenuRequest> menus, List<PaymentMethod> paymentMethods, List<BusinessHourRequest> businessHours) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.menus = menus;
        this.category = category;
        this.paymentMethods = paymentMethods;
        this.businessHours = businessHours;
    }

    public Store toEntity(Long memberId) {
        Store store = Store.newInstance(memberId, name, pictureUrl, location, description, category);
        store.addMenus(this.menus.stream()
            .map(menu -> menu.toEntity(store))
            .collect(Collectors.toList()));
        store.addPayments(paymentMethods);
        store.addBusinessDays(businessHours.stream()
            .map(businessHour -> businessHour.toEntity(store))
            .collect(Collectors.toList()));

        return store;
    }

}
