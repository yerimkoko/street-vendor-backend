package store.streetvendor.service.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Grade;

@NoArgsConstructor
@Getter
public class UpdateStoreReviewRequest {

    private Long reviewId;

    private String comment;

    private Grade grade;

    @Builder
    public UpdateStoreReviewRequest(Long reviewId, String comment, Grade grade) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.grade = grade;
    }
}
