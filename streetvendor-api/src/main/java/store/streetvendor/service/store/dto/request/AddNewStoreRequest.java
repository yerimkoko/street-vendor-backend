package store.streetvendor.service.store.dto.request;

import lombok.*;
import store.streetvendor.domain.domain.store.Location;
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

    private Location location;

    private String storeDescription;

    private String locationDescription;

    private StoreCategory category;

    private List<BusinessHourRequest> businessHours;

    private List<MenuRequest> menus;

    private List<PaymentMethod> paymentMethods;

    private List<StoreImageRequest> storeImages;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public AddNewStoreRequest(@NotBlank String name, Location location, String storeDescription, String locationDescription,
                              StoreCategory category, List<MenuRequest> menus, List<PaymentMethod> paymentMethods, List<BusinessHourRequest> businessHours,
                              List<StoreImageRequest> storeImages) {
        this.name = name;
        this.location = location;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.menus = menus;
        this.category = category;
        this.paymentMethods = paymentMethods;
        this.businessHours = businessHours;
        this.storeImages = storeImages;
    }

    public Store toEntity(Long memberId) {
        Store store = Store.newInstance(memberId, name, location, storeDescription, locationDescription, category);
        store.addMenus(this.menus.stream()
            .map(menu -> menu.toEntity(store))
            .collect(Collectors.toList()));
        store.addPayments(paymentMethods);
        store.addBusinessDays(businessHours.stream()
            .map(businessHour -> businessHour.toEntity(store))
            .collect(Collectors.toList()));
        store.addStoreImages(storeImages.stream()
            .map(image -> image.toEntity(store))
            .collect(Collectors.toList()));

        return store;
    }

}
