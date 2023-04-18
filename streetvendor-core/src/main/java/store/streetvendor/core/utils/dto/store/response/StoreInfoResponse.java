package store.streetvendor.core.utils.dto.store.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.domain.store.StoreSalesStatus;


@NoArgsConstructor
@Getter
public class StoreInfoResponse {

    private Long storeId;

    private String storeName;

    private StoreCategory storeCategory;

    private String locationDescription;

    private String pictureUrl;

    private String spoon;

    private StoreSalesStatus salesStatus;

    private long reviews;

    @Builder
    public StoreInfoResponse(Long storeId, String storeName, StoreCategory storeCategory, String locationDescription, String pictureUrl, String spoon, StoreSalesStatus salesStatus, long reviews) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeCategory = storeCategory;
        this.locationDescription = locationDescription;
        this.pictureUrl = pictureUrl;
        this.spoon = spoon;
        this.salesStatus = salesStatus;
        this.reviews = reviews;
    }

    public static StoreInfoResponse of(Store store) {
        return StoreInfoResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .storeCategory(store.getCategory())
            .pictureUrl(findMainUrl(store))
            .locationDescription(store.getStoreDescription())
            .salesStatus(store.getSalesStatus())
            .build();

    }


    private static String findMainUrl(Store store) {
        if (store.getStoreImages().isEmpty()) {
            return null;
        }
        return store.findMainImage().getPictureUrl();

    }

    public boolean hasCategory(StoreCategory category) {
        return this.getStoreCategory().equals(category);
    }

    public boolean isSalesStatus(StoreSalesStatus salesStatus) {
        return this.getSalesStatus().equals(salesStatus);
    }
}
