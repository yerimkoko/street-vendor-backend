package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.storeimage.StoreImage;

@Getter
@NoArgsConstructor
public class StoreImageResponse {
    private Long id;

    private Boolean isThumbNail;

    private String pictureUrl;

    @Builder
    public StoreImageResponse(Long id, Boolean isThumbNail, String pictureUrl) {
        this.id = id;
        this.isThumbNail = isThumbNail;
        this.pictureUrl = pictureUrl;
    }

    public static StoreImageResponse of(StoreImage storeImage) {
        return StoreImageResponse
            .builder()
            .id(storeImage.getId())
            .isThumbNail(storeImage.getIsThumbNail())
            .pictureUrl(storeImage.getPictureUrl())
            .build();
    }

}
