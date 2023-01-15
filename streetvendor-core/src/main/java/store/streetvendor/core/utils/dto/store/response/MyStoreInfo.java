package store.streetvendor.core.utils.dto.store.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.StoreSalesStatus;

@Getter
@NoArgsConstructor
public class MyStoreInfo {

    private Long storeId;

    private String storeName;

    private String locationDescription;

    private StoreSalesStatus salesStatus;

    @Builder
    public MyStoreInfo(Long storeId, String storeName, String locationDescription, StoreSalesStatus salesStatus) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.locationDescription = locationDescription;
        this.salesStatus = salesStatus;
    }

    public static MyStoreInfo of(Store store) {
        return MyStoreInfo.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .locationDescription(store.getLocationDescription())
            .salesStatus(store.getSalesStatus())
            .build();
    }
}
