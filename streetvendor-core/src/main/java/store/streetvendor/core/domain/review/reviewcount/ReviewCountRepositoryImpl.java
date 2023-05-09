package store.streetvendor.core.domain.review.reviewcount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.streetvendor.core.redis.StringRedisRepository;


@RequiredArgsConstructor
@Repository
public class ReviewCountRepositoryImpl implements ReviewCountRepository {

    private final StringRedisRepository<ReviewCountKey, Long> reviewRedisRepository;

    @Override
    public void incrByCount(Long storeId) {
        ReviewCountKey key = ReviewCountKey.builder()
            .storeId(storeId)
            .build();
        reviewRedisRepository.incr(key);

    }

    @Override
    public long getValueByKey(Long storeId) {
        ReviewCountKey key = ReviewCountKey.builder()
            .storeId(storeId)
            .build();
        return reviewRedisRepository.get(key);
    }


}
