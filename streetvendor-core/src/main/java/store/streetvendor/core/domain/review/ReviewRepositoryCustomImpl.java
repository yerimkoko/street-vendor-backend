package store.streetvendor.core.domain.review;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static store.streetvendor.core.domain.review.QReview.review;


@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Review findByReviewIdAndMemberId(Long memberId, Long reviewId) {
        return jpaQueryFactory.selectFrom(review)
            .where(review.member.id.eq(memberId), review.id.eq(reviewId))
            .fetchOne();
    }

    @Override
    public List<Review> findByStoreId(Long storeId, Long cursor, int size) {
        return jpaQueryFactory.selectFrom(review)
            .where(review.order.storeInfo.storeId.eq(storeId),
                existedCursor(cursor))
            .orderBy(review.id.desc())
            .limit(size)
            .fetch();
    }

    private BooleanExpression existedCursor(Long cursor) {
        if(cursor == null) {
            return null;
        }
        return review.id.lt(cursor);
    }
}
