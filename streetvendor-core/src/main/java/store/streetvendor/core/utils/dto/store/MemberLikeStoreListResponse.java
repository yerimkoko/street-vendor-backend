package store.streetvendor.core.utils.dto.store;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;

@Getter
@NoArgsConstructor
public class MemberLikeStoreListResponse {

    private static final int EARTH_RADIUS = 6371;

    private String storeName;

    private String category;

    private String storeDescription;

    private String locationDescription;

    private String storeStatus;

    private long reviewCount;

    private double distance;

    private double spoon;

    @Builder
    public MemberLikeStoreListResponse(String storeName, String category, String storeDescription, String locationDescription, String storeStatus, long reviewCount, double distance, double spoon) {
        this.storeName = storeName;
        this.category = category;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.storeStatus = storeStatus;
        this.reviewCount = reviewCount;
        this.distance = distance;
        this.spoon = spoon;
    }

    public static MemberLikeStoreListResponse of(Store store, double latitude, double longitude, long reviewCount) {
        return MemberLikeStoreListResponse.builder()
            .storeName(store.getName())
            .category(store.getCategory().getDescription())
            .storeDescription(store.getStoreDescription())
            .locationDescription(store.getLocationDescription())
            .storeStatus(store.getSalesStatus().getDescription())
            .reviewCount(reviewCount)
            .spoon(store.getReviewAverageValue())
            .distance(getDistance(store.getLocation().getLatitude(), latitude, store.getLocation().getLongitude(), longitude))
            .build();
    }

    public static double getDistance(double storeLatitude, double currentLatitude, double storeLongitude, double currentLongitude) {
        double distanceLatitude = Math.toRadians(storeLatitude - currentLatitude);
        double distanceLongitude = Math.toRadians(storeLongitude - currentLongitude);
        double a = Math.sin(distanceLatitude / 2) * Math.sin(distanceLatitude / 2) +
            Math.cos(Math.toRadians(currentLatitude)) * Math.cos(Math.toRadians(storeLatitude)) *
                Math.sin(distanceLongitude / 2) * Math.sin(distanceLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c * 1000;
    }
}
