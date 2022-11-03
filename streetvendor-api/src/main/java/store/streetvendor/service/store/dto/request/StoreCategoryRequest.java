package store.streetvendor.service.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.StoreCategory;
import store.streetvendor.core.domain.store.StoreSalesStatus;
import store.streetvendor.core.domain.store.StoreStatus;

@Getter
@NoArgsConstructor
public class StoreCategoryRequest {

    private static final double DISTANCE_LIMIT = 2;

    private StoreSalesStatus salesStatus;

    private StoreStatus status;

    private Double latitude;

    private Double longitude;

    private Double distance;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public StoreCategoryRequest(StoreSalesStatus salesStatus, StoreStatus status, Double latitude, Double longitude, Double distance) {
        this.salesStatus = salesStatus;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

}
