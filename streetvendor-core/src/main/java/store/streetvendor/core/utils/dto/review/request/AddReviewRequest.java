package store.streetvendor.core.utils.dto.review.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import store.streetvendor.core.aws.response.ImageUrlResponse;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.order_history.OrderHistory;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.domain.store.Rate;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class AddReviewRequest {

    @NotNull
    @Max(value = 5, message = "0 ~ 5사이의 값을 입력해주세요.")
    private int rate;

    @NotBlank
    private String comment;

    @NotNull
    private Long orderId;

    @Nullable
    private List<ImageUrlResponse> reviewImages;


    @Builder
    public AddReviewRequest(int rate, String comment, Long orderId, @Nullable List<ImageUrlResponse> reviewImages) {
        this.rate = rate;
        this.comment = comment;
        this.orderId = orderId;
        this.reviewImages = reviewImages;
    }

    public Review toEntity(Member member, OrderHistory order) {
        return Review.newInstance(order, member, Rate.of(rate), comment);
    }

}
