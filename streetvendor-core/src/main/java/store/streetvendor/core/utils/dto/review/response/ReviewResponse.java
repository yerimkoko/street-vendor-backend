package store.streetvendor.core.utils.dto.review.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import store.streetvendor.core.domain.review.Review;
import store.streetvendor.core.utils.dto.order.response.OrderHistoryMenuResponse;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ReviewResponse {

    private Long storeId;

    private Long reviewId;

    @Value("${cloud.s3.baseUrl}")
    private String baseUrl;

    private String userNickName;

    private int rate;

    private List<OrderHistoryMenuResponse> orderHistoryMenuResponses;

    private List<ReviewImageResponse> reviewImageResponses;

    @Builder
    public ReviewResponse(Long storeId, Long reviewId, String baseUrl, String userNickName, int rate, List<OrderHistoryMenuResponse> orderMenuResponses, List<ReviewImageResponse> reviewImageResponses) {
        this.storeId = storeId;
        this.reviewId = reviewId;
        this.baseUrl = baseUrl;
        this.userNickName = userNickName;
        this.rate = rate;
        this.orderHistoryMenuResponses = orderMenuResponses;
        this.reviewImageResponses = reviewImageResponses;
    }

    public static ReviewResponse of(Review review, String baseUrl) {
        return ReviewResponse.builder()
            .orderMenuResponses(review.getOrder().getMenus().stream()
                .map(OrderHistoryMenuResponse::of)
                .collect(Collectors.toList()))
            .reviewId(review.getId())
            .storeId(review.getOrder().getStoreInfo().getStoreId())
            .userNickName(review.getMember().getNickName())
            .rate(review.getRate().getValue())
            .reviewImageResponses(review.getReviewImages().stream()
                .map(image -> ReviewImageResponse.of(image, baseUrl))
                .collect(Collectors.toList()))
            .build();
    }
}
