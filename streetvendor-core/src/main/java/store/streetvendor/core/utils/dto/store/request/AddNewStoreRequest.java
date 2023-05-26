package store.streetvendor.core.utils.dto.store.request;

import lombok.*;
import store.streetvendor.core.domain.store.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddNewStoreRequest {

    @NotBlank
    private String name;

    @NotNull
    private Location location;

    @NotBlank
    private String storeDescription;

    @NotBlank
    private String locationDescription;

    @NotNull
    private BankInfo bankInfo;

    @NotNull
    private StoreCategory category;

    private List<BusinessHourRequest> businessHours;

    private List<MenuRequest> menus;

    private List<PaymentMethod> paymentMethods;

    @Size(max = 3)
    private List<StoreImageRequest> storeImages;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public AddNewStoreRequest(@NotBlank String name, Location location, String storeDescription, String locationDescription,
                              StoreCategory category, List<MenuRequest> menus, List<PaymentMethod> paymentMethods, List<BusinessHourRequest> businessHours,
                              List<StoreImageRequest> storeImages,
                              BankInfo bankInfo) {
        this.name = name;
        this.location = location;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.menus = menus;
        this.category = category;
        this.paymentMethods = paymentMethods;
        this.businessHours = businessHours;
        this.storeImages = storeImages;
        this.bankInfo = bankInfo;
    }

    public Store toEntity(Long bossId) {
        Store store = Store.newInstance(bossId, name, location, storeDescription, locationDescription, category, bankInfo);
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
