package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Grade;
import store.streetvendor.domain.domain.store.review.Review;

@NoArgsConstructor
@Getter
public class StoreReviewList {

    private Long memberId;

    private Grade grade;

    private String comment;

    @Builder
    public StoreReviewList(Long memberId, Grade grade, String comment) {
        this.memberId = memberId;
        this.grade = grade;
        this.comment = comment;
    }

    public static StoreReviewList of(Review review) {
        return StoreReviewList.builder()
            .comment(review.getComment())
            .grade(review.getGrade())
            .memberId(review.getMemberId())
            .build();
    }
}
