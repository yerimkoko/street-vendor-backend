package store.streetvendor.core.domain.store.review;

public interface ReviewRepositoryCustom {
    Review findByReviewIdAndMemberId(Long memberId, Long reviewId);
}
