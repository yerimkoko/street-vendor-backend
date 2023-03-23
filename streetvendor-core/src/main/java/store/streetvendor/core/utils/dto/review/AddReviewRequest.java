package store.streetvendor.core.utils.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.domain.store.Rate;
import store.streetvendor.core.domain.store.Store;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class AddReviewRequest {

    @NotNull
    @Max(value = 5, message = "0 ~ 5사이의 값을 입력해주세요.")
    private int rate;

    @NotBlank
    private String comment;

    @Builder
    public AddReviewRequest(int rate, String comment) {
        this.rate = rate;
        this.comment = comment;
    }

    public Review toEntity(Long memberId, Store store) {
        return Review.newInstance(store, memberId, Rate.of(rate), comment);
    }

}
