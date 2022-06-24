package store.streetvendor.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.StoreSalesStatus;

@Getter
@NoArgsConstructor
public class UpdateStoreRequest {

    private Long storeId;

    private Long adminId;

    private StoreSalesStatus salesStatus;

    public UpdateStoreRequest(Long storeId, Long adminId, StoreSalesStatus salesStatus) {
        this.storeId = storeId;
        this.adminId = adminId;
        this.salesStatus = salesStatus;
    }
}
