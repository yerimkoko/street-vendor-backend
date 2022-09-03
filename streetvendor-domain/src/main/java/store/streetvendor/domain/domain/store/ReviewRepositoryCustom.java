package store.streetvendor.domain.domain.store;

public interface ReviewRepositoryCustom {
    Review findByReviewIdAndMemberId(Long memberId, Long reviewId);
}
