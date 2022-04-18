package store.streetvendor.service.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Location;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreCategory;

@NoArgsConstructor
@Getter
public class AllStoresResponse {

    private Long storeId;

    private String storeName;

    private Location location;

    private StoreCategory category;

    private String profileUrl;

    public AllStoresResponse(Long storeId, String storeName, Location location, StoreCategory category, String profileUrl) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.location = location;
        this.category = category;
        this.profileUrl = profileUrl;
    }

    public static AllStoresResponse of(Store store) {
        return new AllStoresResponse(store.getId(), store.getName(), store.getLocation(), store.getCategory(), store.getPictureUrl());
    }

}
