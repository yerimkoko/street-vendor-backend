package store.streetvendor.service.order_history.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Location;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreCategory;

@NoArgsConstructor
@Getter
public class OrderHistoryStoreResponse {

    private Long storeId;

    private String name;

    private String pictureUrl;

    private Location location;

    private String storeDescription;

    private String locationDescription;

    private StoreCategory category;

    @Builder
    public OrderHistoryStoreResponse(Long storeId, String name, String pictureUrl, Location location, String storeDescription, String locationDescription, StoreCategory category) {
        this.storeId = storeId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.storeDescription = storeDescription;
        this.locationDescription = locationDescription;
        this.category = category;
    }

    public static OrderHistoryStoreResponse of(Store store) {
        return OrderHistoryStoreResponse.builder()
            .storeId(store.getId())
            .name(store.getName())
            .pictureUrl(store.getPictureUrl())
            .location(store.getLocation())
            .storeDescription(store.getStoreDescription())
            .locationDescription(store.getLocationDescription())
            .category(store.getCategory())
            .build();
    }
}
