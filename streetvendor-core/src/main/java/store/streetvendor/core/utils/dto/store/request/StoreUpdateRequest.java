package store.streetvendor.core.utils.dto.store.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.domain.store.menu.Menu;
import store.streetvendor.core.domain.store.storeimage.StoreImage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StoreUpdateRequest {
    private String name;

    private Location location;

    private String description;

    private StoreCategory category;

    private List<MenuRequest> menus;

    private List<PaymentMethod> paymentMethods;

    private List<BusinessHourRequest> businessHours;

    private List<StoreImageRequest> storeImages;


    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public StoreUpdateRequest(String name, Location location, String description,
                              List<MenuRequest> menus, List<PaymentMethod> paymentMethods, List<BusinessHourRequest> businessHours,
                              StoreCategory category, List<StoreImageRequest> storeImages) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.menus = menus;
        this.paymentMethods = paymentMethods;
        this.businessHours = businessHours;
        this.category = category;
        this.storeImages = storeImages;
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

    public List<StoreImage> toStoreImages(Store store) {
        return this.storeImages.stream()
            .map(storeImage -> storeImage.toEntity(store))
            .collect(Collectors.toList());

    }

}
