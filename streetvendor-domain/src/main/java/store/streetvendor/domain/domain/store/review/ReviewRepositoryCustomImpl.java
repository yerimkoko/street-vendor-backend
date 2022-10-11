package store.streetvendor.domain.domain.store.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static store.streetvendor.domain.domain.store.review.QReview.review;


@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Review findByReviewIdAndMemberId(Long memberId, Long reviewId) {
        return jpaQueryFactory.selectFrom(review)
            .where(review.memberId.eq(memberId), review.id.eq(reviewId))
            .fetchOne();
    }
}
