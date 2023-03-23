package store.streetvendor.core.domain.review;

public interface ReviewRepositoryCustom {
    Review findByReviewIdAndMemberId(Long memberId, Long reviewId);
}
