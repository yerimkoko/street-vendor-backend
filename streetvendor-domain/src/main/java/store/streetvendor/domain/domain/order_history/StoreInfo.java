package store.streetvendor.domain.domain.order_history;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Store;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Embeddable
public class StoreInfo {

    private Long storeId;

    private Long bossId;

    private String name;

    private String description;

    private String locationDescription;

    @Builder
    public StoreInfo(Long storeId, Long bossId, String name, String description, String locationDescription) {
        this.storeId = storeId;
        this.bossId = bossId;
        this.name = name;
        this.description = description;
        this.locationDescription = locationDescription;
    }


    public static StoreInfo of(Store store) {
        return StoreInfo.builder()
            .storeId(store.getId())
            .description(store.getStoreDescription())
            .locationDescription(store.getLocationDescription())
            .bossId(store.getMemberId())
            .build();
    }
}
