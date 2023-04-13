package store.streetvendor.core.domain.store.projection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.domain.store.StoreStatus;

@ToString
@Getter
public class MemberLikeStoreAndReviewCountProjection {

    private final String storeName;

    private final StoreCategory category;

    private final String storeDescription;

    private final String locationDescription;

    private final StoreStatus storeStatus;

    private final long reviewCount;

    @QueryProjection
    public MemberLikeStoreAndReviewCountProjection(String storeName, StoreCategory category, String storeDescription, String locationDescription, StoreStatus storeStatus, long reviewCount) {
        this.storeName = storeName;
        this.category = category;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.storeStatus = storeStatus;
        this.reviewCount = reviewCount;
    }
}
