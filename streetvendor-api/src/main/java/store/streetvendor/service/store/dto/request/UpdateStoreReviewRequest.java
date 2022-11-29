package store.streetvendor.service.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Rate;

@NoArgsConstructor
@Getter
public class UpdateStoreReviewRequest {

    private Long reviewId;

    private String comment;

    private Rate rate;

    @Builder
    public UpdateStoreReviewRequest(Long reviewId, String comment, Rate rate) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.rate = rate;
    }
}
