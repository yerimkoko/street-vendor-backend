package store.streetvendor.service.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Location;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreCategory;
import store.streetvendor.domain.domain.store.storeimage.StoreImage;

import java.util.List;

@NoArgsConstructor
@Getter
public class AllStoresResponse {

    private Long storeId;

    private String storeName;

    private Location location;

    private StoreCategory category;

    private List<StoreImage> storeImages;

    public AllStoresResponse(Long storeId, String storeName, Location location, StoreCategory category, List<StoreImage> storeImages) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.location = location;
        this.category = category;
        this.storeImages = storeImages;
    }

    public static AllStoresResponse of(Store store) {
        return new AllStoresResponse(store.getId(), store.getName(), store.getLocation(), store.getCategory(), store.getStoreImages());
    }

}
