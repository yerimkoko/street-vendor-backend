package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import store.streetvendor.domain.domain.store.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class StoreResponseDto {

    private Long storeId;

    private Long bossId;

    private String name;

    private String pictureUrl;

    private Location location;

    private String description;

    private List<StoreBusinessDayResponse> businessHours;

    private StoreCategory category;

    private StoreSalesStatus salesStatus;

    private List<PaymentMethod> paymentMethods;

    private List<MenuResponse> menus;


    @Builder
    public StoreResponseDto(Long storeId, Long bossId, String name, String pictureUrl, Location location, String description,
                            List<StoreBusinessDayResponse> businessHours, StoreCategory category, StoreSalesStatus salesStatus,
                            List<PaymentMethod> paymentMethods, List<MenuResponse> menus) {
        this.storeId = storeId;
        this.bossId = bossId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
        this.businessHours = businessHours;
        this.category = category;
        this.paymentMethods = paymentMethods;
        this.menus = menus;
        this.salesStatus = salesStatus;
    }

    public static StoreResponseDto of(Store store) {
        List<BusinessHours> businessHours = store.getBusinessDays().stream()
            .map(business -> BusinessHours.of(store, business.getDays(), business.getOpeningTime().getStartTime(),
                business.getOpeningTime().getEndTime()))
            .collect(Collectors.toList());

        return StoreResponseDto.builder()
            .storeId(store.getId())
            .bossId(store.getMemberId())
            .name(store.getName())
            .pictureUrl(store.getPictureUrl())
            .location(store.getLocation())
            .description(store.getStoreDescription())
            .category(store.getCategory())
            .paymentMethods(store.getPaymentMethods().stream().map(Payment::getPaymentMethod).collect(Collectors.toList()))
            .menus(getMenuList(store))
            .salesStatus(store.getSalesStatus())
            .businessHours(businessHours.stream().map(StoreBusinessDayResponse::of).collect(Collectors.toList()))
            .build();
    }

    private static List<MenuResponse> getMenuList(Store store) {
        return store.getMenus().stream()
            .map(MenuResponse::of)
            .collect(Collectors.toList());
    }

}
