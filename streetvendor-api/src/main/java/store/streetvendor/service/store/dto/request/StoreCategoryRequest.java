package store.streetvendor.service.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.StoreCategory;
import store.streetvendor.domain.domain.store.StoreSalesStatus;

@Getter
@NoArgsConstructor
public class StoreCategoryRequest {

    private StoreCategory category;

    private StoreSalesStatus salesStatus;

    public StoreCategoryRequest(StoreCategory category, StoreSalesStatus salesStatus) {
        this.category = category;
        this.salesStatus = salesStatus;
    }
}
