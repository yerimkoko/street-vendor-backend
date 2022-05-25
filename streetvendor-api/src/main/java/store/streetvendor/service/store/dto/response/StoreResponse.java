package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreCategory;
import store.streetvendor.domain.domain.store.StoreSalesStatus;

@NoArgsConstructor
@Getter
public class StoreResponse {

    private long storeId;

    private String name;

    private StoreSalesStatus salesStatus;

    private StoreCategory category;

    @Builder
    public StoreResponse(long storeId, String name, StoreSalesStatus salesStatus, StoreCategory category) {
        this.storeId = storeId;
        this.name = name;
        this.salesStatus = salesStatus;
        this.category = category;
    }

    public static StoreResponse of(Store store) {
        return StoreResponse.builder()
            .storeId(store.getId())
            .category(store.getCategory())
            .name(store.getName())
            .salesStatus(store.getSalesStatus())
            .build();
    }
}
