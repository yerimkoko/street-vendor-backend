package store.streetvendor.core.utils.dto.store.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.utils.ConvertUtil;

@NoArgsConstructor
@Getter
public class PopularStoresResponse {

    private Long storeId;

    private String storeName;

    private StoreCategory storeCategory;

    private String storeRates;

    @Builder
    public PopularStoresResponse(Long storeId, String storeName, StoreCategory storeCategory, String storeRates) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeCategory = storeCategory;
        this.storeRates = storeRates;
    }

    public static PopularStoresResponse of(Store store) {
        return PopularStoresResponse.builder()
            .storeId(store.getId())
            .storeCategory(store.getCategory())
            .storeRates(ConvertUtil.getAverageSpoon(store))
            .build();
    }
}
