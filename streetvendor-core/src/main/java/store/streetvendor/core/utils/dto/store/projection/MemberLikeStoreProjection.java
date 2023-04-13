package store.streetvendor.core.utils.dto.store.projection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.domain.store.StoreSalesStatus;

@Getter
@ToString
public class MemberLikeStoreProjection {

    private final String storeName;

    private final StoreCategory category;

    private final String storeDescription;

    private final String locationDescription;

    private final StoreSalesStatus storeSalesStatus;

    private final long reviewCount;

    private final double distance;

    private final double spoon;

    @QueryProjection
    public MemberLikeStoreProjection(String storeName, StoreCategory category, String storeDescription, String locationDescription, StoreSalesStatus storeSalesStatus, long reviewCount, double distance, double spoon) {
        this.storeName = storeName;
        this.category = category;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.storeSalesStatus = storeSalesStatus;
        this.reviewCount = reviewCount;
        this.distance = distance;
        this.spoon = spoon;
    }
}
