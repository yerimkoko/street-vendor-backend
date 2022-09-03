package store.streetvendor.service.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.store.Review;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class StoreReviewResponse {

    private Long storeId;

    private List<StoreReviewList> reviewList;

    @Builder
    public StoreReviewResponse(Long storeId, List<StoreReviewList> reviewList) {
        this.storeId = storeId;
        this.reviewList = reviewList;
    }

    public static StoreReviewResponse of(Long storeId, List<Review> reviews) {
        return StoreReviewResponse.builder()
            .storeId(storeId)
            .reviewList(reviews.stream()
                .map(StoreReviewList::of)
                .collect(Collectors.toList()))
            .build();
    }
}
