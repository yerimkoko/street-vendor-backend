package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Location;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreCategory;

@Getter
@NoArgsConstructor
public class StoreSimpleResponse {

    private Long storeId;

    private String storeName;

    private String storeDescription;

    private String locationDescription;

    private Location location;

    private StoreCategory category;

    @Builder
    public StoreSimpleResponse(Long storeId, String storeName, String storeDescription, String locationDescription, Location location, StoreCategory category) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.location = location;
        this.category = category;
    }

    public static StoreSimpleResponse of(Store store) {
        return StoreSimpleResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .category(store.getCategory())
            .storeDescription(store.getStoreDescription())
            .locationDescription(store.getLocationDescription())
            .location(store.getLocation())
            .build();
    }

}
