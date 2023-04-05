package store.streetvendor.core.utils.dto.review.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.review.ReviewImage;

@NoArgsConstructor
@Getter
public class ReviewImageResponse {

    private Long imageId;

    private String baseUrl;


    public ReviewImageResponse(Long imageId, String imageUrl) {
        this.imageId = imageId;
        this.baseUrl = imageUrl;

    }

    public static ReviewImageResponse of(ReviewImage image, String baseUrl) {
        return new ReviewImageResponse(image.getId(), baseUrl + image.getImageUrl());
    }
}
