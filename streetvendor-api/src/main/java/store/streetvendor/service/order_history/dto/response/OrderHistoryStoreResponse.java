package store.streetvendor.service.order_history.dto.response;

import lombok.Builder;
import store.streetvendor.domain.domain.store.Store;

public class OrderHistoryStoreResponse {

    private Long storeId;

    private String name;

    private String pictureUrl;

    private String location;

    private String description;

    @Builder
    public OrderHistoryStoreResponse(Long storeId, String name, String pictureUrl, String location, String description) {
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
            .build();
    }
}
