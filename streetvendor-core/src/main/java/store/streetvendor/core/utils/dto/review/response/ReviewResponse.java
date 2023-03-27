package store.streetvendor.core.utils.dto.review.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.utils.dto.order.response.OrderMenuResponse;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ReviewResponse {

    private Long storeId;

    private Long reviewId;

    private String userNickName;

    private int rate;

    private List<OrderMenuResponse> orderMenuResponses;

    @Builder
    public ReviewResponse(Long storeId, Long reviewId, String userNickName, int rate, List<OrderMenuResponse> orderMenuResponses) {
        this.storeId = storeId;
        this.reviewId = reviewId;
        this.userNickName = userNickName;
        this.rate = rate;
        this.orderMenuResponses = orderMenuResponses;
    }

    public static ReviewResponse of(Review review) {
        return ReviewResponse.builder()
            .orderMenuResponses(review.getOrder().getOrderMenus().stream()
                .map(OrderMenuResponse::of)
                .collect(Collectors.toList()))
            .reviewId(review.getId())
            .storeId(review.getOrder().getStore().getId())
            .userNickName(review.getMember().getNickName())
            .rate(review.getRate().getValue())
            .build();
    }
}
