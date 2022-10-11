package store.streetvendor.domain.domain.store.review;

public interface ReviewRepositoryCustom {
    Review findByReviewIdAndMemberId(Long memberId, Long reviewId);
}
