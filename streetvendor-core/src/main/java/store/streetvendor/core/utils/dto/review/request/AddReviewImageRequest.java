package store.streetvendor.core.utils.dto.review.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.review.ReviewImage;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class AddReviewImageRequest {

    @NotNull
    private Long reviewId;

    @Builder
    private AddReviewImageRequest(Long reviewId) {
        this.reviewId = reviewId;
    }

    public ReviewImage toEntity(Long memberId, String imageUrl) {
        return ReviewImage.of(reviewId, memberId, imageUrl);
    }


}
