package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Store;

@Getter
@NoArgsConstructor
public class MyStoreInfo {

    private Long storeId;

    private String storeName;

    private String locationDescription;

    @Builder
    public MyStoreInfo(Long storeId, String storeName, String locationDescription) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.locationDescription = locationDescription;
    }

    public static MyStoreInfo of(Store store) {
        return MyStoreInfo.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .locationDescription(store.getLocationDescription())
            .build();
    }
}
