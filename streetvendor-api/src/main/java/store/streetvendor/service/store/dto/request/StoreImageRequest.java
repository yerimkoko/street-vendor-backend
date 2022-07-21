package store.streetvendor.service.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Store;
import store.streetvendor.domain.domain.store.StoreImage;

@NoArgsConstructor
@Getter
public class StoreImageRequest {

    private Store store;

    private boolean isThumbNail;

    private String imageUrl;

    @Builder
    public StoreImageRequest(Store store, boolean isThumbNail, String imageUrl) {
        this.store = store;
        this.isThumbNail = isThumbNail;
        this.imageUrl = imageUrl;
    }

    public StoreImage toEntity(Store store) {
        return StoreImage.of(store, isThumbNail, imageUrl);
    }

    public static StoreImageRequest testInstance(Store store, boolean isThumbNail, String imageUrl ) {
        return new StoreImageRequest(store, isThumbNail, imageUrl);
    }


}
