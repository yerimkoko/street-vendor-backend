package store.streetvendor.core.aws.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ImageUrlResponse {

    private String imageUrl;

    public ImageUrlResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ImageUrlResponse of(String imageUrl) {
        return new ImageUrlResponse(imageUrl);
    }
}
