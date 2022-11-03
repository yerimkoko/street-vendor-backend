package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Location;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.domain.store.StoreSalesStatus;


@NoArgsConstructor
@Getter
public class StoreResponse {

    private Long storeId;

    private String storeName;

    private StoreSalesStatus salesStatus;

    private StoreCategory category;

    private Location location;

    @Builder
    public StoreResponse(Long storeId, String storeName, StoreSalesStatus salesStatus, StoreCategory category, Location location) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.salesStatus = salesStatus;
        this.category = category;
        this.location = location;
    }

    public static StoreResponse of(Store store) {
        return StoreResponse.builder()
            .storeId(store.getId())
            .category(store.getCategory())
            .storeName(store.getName())
            .salesStatus(store.getSalesStatus())
            .location(store.getLocation())
            .build();
    }

    public boolean hasCategory(StoreCategory category) {
        return this.getCategory().equals(category);
    }

    public boolean isSalesStatus(StoreSalesStatus salesStatus) {
        return this.getSalesStatus().equals(salesStatus);
    }
}
