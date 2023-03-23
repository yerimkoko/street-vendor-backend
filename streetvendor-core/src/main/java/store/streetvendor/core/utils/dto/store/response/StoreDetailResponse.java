package store.streetvendor.core.utils.dto.store.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.*;
import store.streetvendor.core.utils.ConvertUtil;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class StoreDetailResponse {

    private Long storeId;

    private String storeName;

    private String locationDescription;

    private String storeDescription;

    private Location location;

    private StoreSalesStatus salesStatus;

    private String category;

    private List<MenuDetailResponse> menuList;

    private List<StoreBusinessDayResponse> businessHours;

    private List<StoreImageResponse> storeImageResponses;

    private String reviewAverage;

    private Integer reviewCount;

    @Builder
    public StoreDetailResponse(Long storeId, String storeName, String locationDescription, String storeDescription, Location location, StoreSalesStatus salesStatus, String category, List<MenuDetailResponse> menuList, List<StoreBusinessDayResponse> businessHours, List<StoreImageResponse> storeImageResponses, String reviewAverage, Integer reviewCount) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.locationDescription = locationDescription;
        this.storeDescription = storeDescription;
        this.location = location;
        this.salesStatus = salesStatus;
        this.category = category;
        this.menuList = menuList;
        this.businessHours = businessHours;
        this.storeImageResponses = storeImageResponses;
        this.reviewAverage = reviewAverage;
        this.reviewCount = reviewCount;
    }


    public static StoreDetailResponse of(Store store) {
        List<MenuDetailResponse> menuDetailResponse = store.getMenus().stream()
            .map(MenuDetailResponse::of)
            .collect(Collectors.toList());

        List<BusinessHours> businessHours = store.getBusinessDays().stream()
            .map(business -> BusinessHours.of(store, business.getDays(), business.getOpeningTime().getStartTime(), business.getOpeningTime().getEndTime()))
            .collect(Collectors.toList());

        List<StoreImageResponse> imageResponses = store.getStoreImages().stream()
            .map(StoreImageResponse::of)
            .collect(Collectors.toList());

        return StoreDetailResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .category(store.getCategory().getDescription())
            .menuList(menuDetailResponse)
            .location(store.getLocation())
            .salesStatus(store.getSalesStatus())
            .storeDescription(store.getStoreDescription())
            .locationDescription(store.getLocationDescription())
            .businessHours(businessHours.stream()
                .map(StoreBusinessDayResponse::of)
                .collect(Collectors.toList()))
            .storeImageResponses(imageResponses)
            .build();
    }
}
