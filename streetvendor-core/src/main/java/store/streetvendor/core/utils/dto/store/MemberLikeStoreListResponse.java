package store.streetvendor.core.utils.dto.store;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.storeimage.StoreImage;
import store.streetvendor.core.utils.DistanceUtils;

@Getter
@NoArgsConstructor
public class MemberLikeStoreListResponse {

    private String storeName;

    private String category;

    private String storeDescription;

    private String locationDescription;

    private String storeStatus;

    private String thumbNail;

    private long reviewCount;

    private double distance;

    private double spoon;

    @Builder
    public MemberLikeStoreListResponse(String storeName, String category, String thumbNail, String storeDescription, String locationDescription, String storeStatus, long reviewCount, double distance, double spoon) {
        this.storeName = storeName;
        this.category = category;
        this.storeDescription = storeDescription;
        this.thumbNail = thumbNail;
        this.locationDescription = locationDescription;
        this.storeStatus = storeStatus;
        this.reviewCount = reviewCount;
        this.distance = distance;
        this.spoon = spoon;
    }

    public static MemberLikeStoreListResponse of(Store store, double latitude, double longitude) {
        return MemberLikeStoreListResponse.builder()
            .storeName(store.getName())
            .category(store.getCategory().getDescription())
            .storeDescription(store.getStoreDescription())
            .thumbNail(getThumbNail(store.findMainImage()))
            .locationDescription(store.getLocationDescription())
            .storeStatus(store.getSalesStatus().getDescription())
            .spoon(store.getAverageValue())
            .distance(DistanceUtils.getDistance(store.getLocation().getLatitude(), latitude, store.getLocation().getLongitude(), longitude))
            .build();
    }

    private static String getThumbNail(StoreImage image) {
        if (image == null) {
            return null;
        }
        return image.getPictureUrl();
    }
}
