package store.streetvendor.core.utils.dto.store.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Store;
import store.streetvendor.core.domain.store.storeimage.StoreImage;

@NoArgsConstructor
@Getter
public class StoreImageRequest {

    private Boolean isThumbNail;

    private String imageUrl;

    @Builder
    public StoreImageRequest(Boolean isThumbNail, String imageUrl) {
        this.isThumbNail = isThumbNail;
        this.imageUrl = imageUrl;
    }

    public StoreImage toEntity(Store store) {
        return StoreImage.of(store, isThumbNail, imageUrl);
    }

    public static StoreImageRequest testInstance(boolean isThumbNail, String imageUrl ) {
        return new StoreImageRequest(isThumbNail, imageUrl);
    }


}
