package store.streetvendor.core.utils.dto.store.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.domain.store.StoreSalesStatus;
import store.streetvendor.core.utils.DistanceUtils;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
public class StoreInfoResponse {

    private Long storeId;

    private String storeName;

    private StoreCategory storeCategory;

    private String locationDescription;

    private String pictureUrl;

    private Double spoon;

    private StoreSalesStatus salesStatus;

    private long reviews;

    private double distance;

    private String badge;


    @Builder
    public StoreInfoResponse(Long storeId, String storeName, StoreCategory storeCategory, String locationDescription, String pictureUrl, Double spoon, StoreSalesStatus salesStatus, long reviews, double distance, String badge) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeCategory = storeCategory;
        this.locationDescription = locationDescription;
        this.pictureUrl = pictureUrl;
        this.spoon = spoon;
        this.salesStatus = salesStatus;
        this.reviews = reviews;
        this.distance = distance;
        this.badge = badge;
    }

    public static StoreInfoResponse of(Store store, String baseUrl, long reviewCount, double longitude, double latitude) {
        return StoreInfoResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .storeCategory(store.getCategory())
            .pictureUrl(findMainUrl(store, baseUrl))
            .locationDescription(store.getStoreDescription())
            .spoon(store.getAverageValue())
            .reviews(reviewCount)
            .distance(DistanceUtils.getDistance(store.getLocation().getLatitude(), latitude, store.getLocation().getLongitude(), longitude))
            .salesStatus(store.getSalesStatus())
            .badge(getBadge(store))
            .build();

    }


    private static String findMainUrl(Store store, String baseUrl) {
        if (store.getStoreImages().isEmpty()) {
            return null;
        }
        return baseUrl + store.findMainImage().getPictureUrl();

    }

    public boolean hasCategory(StoreCategory category) {
        return this.getStoreCategory().equals(category);
    }

    public boolean isSalesStatus(StoreSalesStatus salesStatus) {
        return this.getSalesStatus().equals(salesStatus);
    }

    private static String getBadge(Store store) {
        if (LocalDate.now().isBefore(store.getCreatedAt().toLocalDate().plusDays(14))) {
            return null;
        }
        return "신규";
    }
}
