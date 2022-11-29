package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.store.Rate;
import store.streetvendor.core.domain.store.review.Review;

@NoArgsConstructor
@Getter
public class StoreReviewList {

    private Long memberId;

    private Rate rate;

    private String comment;

    @Builder
    public StoreReviewList(Long memberId, Rate rate, String comment) {
        this.memberId = memberId;
        this.rate = rate;
        this.comment = comment;
    }

    public static StoreReviewList of(Review review) {
        return StoreReviewList.builder()
            .comment(review.getComment())
            .rate(review.getRate())
            .memberId(review.getMemberId())
            .build();
    }
}
