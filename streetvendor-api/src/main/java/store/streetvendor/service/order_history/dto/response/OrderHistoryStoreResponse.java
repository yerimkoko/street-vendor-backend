package store.streetvendor.service.order_history.dto.response;

import lombok.Builder;
import store.streetvendor.domain.domain.store.Location;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreCategory;

public class OrderHistoryStoreResponse {

    private Long storeId;

    private String name;

    private String pictureUrl;

    private Location location;

    private String description;

    private StoreCategory category;

    @Builder
    public OrderHistoryStoreResponse(Long storeId, String name, String pictureUrl, Location location, String description, StoreCategory category) {
        this.storeId = storeId;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.description = description;
    }

    public static OrderHistoryStoreResponse of(Store store) {
        return OrderHistoryStoreResponse.builder()
            .storeId(store.getId())
            .name(store.getName())
            .pictureUrl(store.getPictureUrl())
            .location(store.getLocation())
            .description(store.getDescription())
            .category(store.getCategory())
            .build();
    }
}
