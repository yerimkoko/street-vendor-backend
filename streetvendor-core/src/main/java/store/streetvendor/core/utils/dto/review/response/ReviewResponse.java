package store.streetvendor.core.utils.dto.review.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.utils.dto.order.response.OrderHistoryMenuResponse;
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

    private List<OrderHistoryMenuResponse> orderHistoryMenuResponses;

    @Builder
    public ReviewResponse(Long storeId, Long reviewId, String userNickName, int rate, List<OrderHistoryMenuResponse> orderMenuResponses) {
        this.storeId = storeId;
        this.reviewId = reviewId;
        this.userNickName = userNickName;
        this.rate = rate;
        this.orderHistoryMenuResponses = orderMenuResponses;
    }

    public static ReviewResponse of(Review review) {
        return ReviewResponse.builder()
            .orderMenuResponses(review.getOrder().getMenus().stream()
                .map(OrderHistoryMenuResponse::of)
                .collect(Collectors.toList()))
            .reviewId(review.getId())
            .storeId(review.getOrder().getStoreInfo().getStoreId())
            .userNickName(review.getMember().getNickName())
            .rate(review.getRate().getValue())
            .build();
    }
}
