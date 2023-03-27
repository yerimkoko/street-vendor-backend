package store.streetvendor.core.domain.review;

import java.util.List;

public interface ReviewRepositoryCustom {
    Review findByReviewIdAndMemberId(Long memberId, Long reviewId);

    List<Review> findByStoreId(Long storeId, Long cursor, int size);
}
