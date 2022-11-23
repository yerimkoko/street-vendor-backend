package store.streetvendor.core.redis.storecount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.streetvendor.core.redis.StringRedisRepository;

@RequiredArgsConstructor
@Repository
public class StoreCountRepositoryImpl implements StoreCountRepository {

    private final StringRedisRepository<StoreCountKey, Long> stringRedisRepository;

    @Override
    public void incrByCount(Long storeId) {
        StoreCountKey key = StoreCountKey.builder()
            .storeId(storeId)
            .build();
        stringRedisRepository.incr(key);

    }

    @Override
    public Long getValueByKey(StoreCountKey key) {
        return stringRedisRepository.get(key);
    }


}
