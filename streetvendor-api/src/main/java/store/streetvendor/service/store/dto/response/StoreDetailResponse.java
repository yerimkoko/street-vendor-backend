package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.store.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class StoreDetailResponse {

    private Long storeId;

    private String storeName;

    private String bossNumber;

    private String description;

    private String pictureUrl;

    private String locationDescription;

    private String storeDescription;

    private Location location;

    private StoreSalesStatus status;

    private StoreCategory category;

    private List<PaymentMethod> payments;

    private List<MenuDetailResponse> menuList;

    private List<StoreBusinessDayResponse> businessHours;

    @Builder
    public StoreDetailResponse(Long storeId, String storeName, String bossNumber, String description, String pictureUrl, String locationDescription, String storeDescription, Location location, StoreSalesStatus status, StoreCategory category, List<PaymentMethod> payments, List<MenuDetailResponse> menuList, List<StoreBusinessDayResponse> businessHours) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.bossNumber = bossNumber;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.locationDescription = locationDescription;
        this.storeDescription = storeDescription;
        this.location = location;
        this.status = status;
        this.category = category;
        this.payments = payments;
        this.menuList = menuList;
        this.businessHours = businessHours;
    }

    public static StoreDetailResponse of(Store store, Member member) {
        List<MenuDetailResponse> menuDetailResponse = store.getMenus().stream()
            .map(MenuDetailResponse::of)
            .collect(Collectors.toList());

        List<BusinessHours> businessHours = store.getBusinessDays().stream()
            .map(business -> BusinessHours.of(store, business.getDays(), business.getOpeningTime().getStartTime(), business.getOpeningTime().getEndTime()))
            .collect(Collectors.toList());

        return StoreDetailResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .bossNumber(member.getPhoneNumber())
            .category(store.getCategory())
            .pictureUrl(store.getPictureUrl())
            .description(store.getStoreDescription())
            .menuList(menuDetailResponse)
            .location(store.getLocation())
            .status(store.getSalesStatus())
            .storeDescription(store.getStoreDescription())
            .locationDescription(store.getLocationDescription())
            .payments(store.getPaymentMethods().stream().map(Payment::getPaymentMethod).collect(Collectors.toList()))
            .businessHours(businessHours.stream()
                .map(StoreBusinessDayResponse::of)
                .collect(Collectors.toList()))
            .build();
    }
}
